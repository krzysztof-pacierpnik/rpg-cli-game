package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.texture.Point;
import com.perfectsoft.game.texture.Texture;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextureContainer implements Texture {

    private final Texture mainTexture;
    private final SortedSet<PointTexture> xSortedPointTextures;
    private final SortedSet<PointTexture> ySortedPointTextures;

    public TextureContainer(Texture mainTexture, Collection<Texture> nestedTextures) {

        this.mainTexture = mainTexture;

        final Comparator<PointTexture> xComp = Comparator.comparingInt(pt -> pt.getPoint().getX());
        final Comparator<PointTexture> yComp = Comparator.comparingInt(pt -> pt.getPoint().getY());
        xSortedPointTextures = indexTexturesByComparator(nestedTextures, xComp);
        ySortedPointTextures = indexTexturesByComparator(nestedTextures, yComp);
    }

    private SortedSet<PointTexture> indexTexturesByComparator(Collection<Texture> nestedTextures,
                                                              Comparator<PointTexture> comp) {

        final SortedSet<PointTexture> xSortedPointTextures;
        xSortedPointTextures = Stream
                .concat(nestedTextures.parallelStream().map(tex -> new PointTexture(tex.getFirst(), List.of(tex))),
                        nestedTextures.parallelStream().map(tex -> new PointTexture(tex.getLast(), List.of(tex))))
                .collect(Collectors.groupingBy(PointTexture::getPoint)).entrySet().stream()
                .map(entry -> {
                    final Point key = entry.getKey();
                    final List<PointTexture> value = entry.getValue();
                    final List<Texture> textures = value
                            .stream()
                            .flatMap(pointTexture -> pointTexture.getTextures().stream())
                            .collect(Collectors.toList());
                    return new PointTexture(key, textures);
                })
                .collect(Collectors.toCollection(() -> new TreeSet<>(comp)));
        return xSortedPointTextures;
    }

    @Override
    public Optional<Byte> getCharAtPoint(final Point point) {

        final Optional<Texture> nestedTextureOpt = findNestedTextureAtPoint(point);
        if (nestedTextureOpt.isPresent()) {

            final Texture texture = nestedTextureOpt.get();
            Optional<Byte> characterOpt = texture.getCharAtPoint(point.subtract(mainTexture.getUpperLeftCornerPosition()));
            if (characterOpt.isPresent()) {
                if (texture.isTransparentBackground() && CliTexture.TRANSPARENT_CHARACTER == characterOpt.get()) {
                    return mainTexture.getCharAtPoint(point);
                }
                return characterOpt;
            }
        }
        return mainTexture.getCharAtPoint(point);
    }

    private Optional<Texture> findNestedTextureAtPoint(final Point point) {

        final SortedSet<Texture> yLessSet = ySortedPointTextures
                .headSet(new PointTexture(point.add(new CliPoint(0, 1)), null))
                .parallelStream()
                .flatMap(pt -> pt.getTextures().stream())
                .collect(Collectors.toCollection(TreeSet::new));

        final SortedSet<Texture> yMoreSet = ySortedPointTextures
                .tailSet(new PointTexture(point.add(new CliPoint(0, -1)), null))
                .parallelStream()
                .flatMap(pt -> pt.getTextures().stream())
                .collect(Collectors.toCollection(TreeSet::new));

        final SortedSet<Texture> xLessSet = xSortedPointTextures
                .headSet(new PointTexture(point.add(new CliPoint(1, 0)), null))
                .parallelStream()
                .flatMap(pt -> pt.getTextures().stream())
                .collect(Collectors.toCollection(TreeSet::new));

        final SortedSet<Texture> xMoreSet = xSortedPointTextures
                .tailSet(new PointTexture(point.add(new CliPoint(-1, 0)), null))
                .parallelStream()
                .flatMap(pt -> pt.getTextures().stream())
                .collect(Collectors.toCollection(TreeSet::new));

        //when surrounding points exists and are owned by the same Texture we are inside it
        yLessSet.retainAll(yMoreSet);
        yLessSet.retainAll(xLessSet);
        yLessSet.retainAll(xMoreSet);

        SortedSet<Texture> zIndexSortedSurrTex = new TreeSet<>(Comparator.comparingInt(Texture::getZIndex));
        zIndexSortedSurrTex.addAll(yLessSet);

        if (!zIndexSortedSurrTex.isEmpty()) {
            return Optional.of(zIndexSortedSurrTex.last());
        }
        return Optional.empty();
    }

    @Override
    public Point getSize() {
        return mainTexture.getSize();
    }

    @Override
    public Point getMiddlePosition() {
        return mainTexture.getMiddlePosition();
    }

    @Override
    public Point getUpperLeftCornerPosition() {
        return mainTexture.getUpperLeftCornerPosition();
    }

    @Override
    public Point getFirst() {
        return mainTexture.getFirst();
    }

    @Override
    public Point getLast() {
        return mainTexture.getLast();
    }

    @Override
    public int getZIndex() {
        return mainTexture.getZIndex();
    }

    @Override
    public Iterable<Byte> iterator() {
        return new CliTextureIterator(this);
    }

    @Override
    public UUID getUuid() {
        return mainTexture.getUuid();
    }

    @Override
    public boolean isTransparentBackground() {
        return mainTexture.isTransparentBackground();
    }

    @Override
    public int compareTo(Texture o) {
        return mainTexture.compareTo(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextureContainer that = (TextureContainer) o;
        return Objects.equals(mainTexture, that.mainTexture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainTexture);
    }
}
