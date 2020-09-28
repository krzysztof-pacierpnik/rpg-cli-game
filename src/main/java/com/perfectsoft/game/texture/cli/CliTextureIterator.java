package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.texture.Texture;

import java.util.Iterator;

public class CliTextureIterator implements Iterable<Byte> {

    private final Texture texture;

    private CliPoint currPos;
    private boolean printedNewLine = false;

    public CliTextureIterator(Texture texture) {
        this.texture = texture;
        currPos = new CliPoint(-1, 0);
    }

    private boolean hasMorePoints()  {
        return currPos.compareTo(texture.getLast()) < 0;
    }

    private Byte getAsciiCharForNextPoint() {

        final int newX = currPos.getX() + 1;
        if (newX > texture.getLast().getX()) {
            if (!printedNewLine) {
                printedNewLine = true;
                return '\n';
            } else {
                printedNewLine = false;
                final int newY = currPos.getY() + 1;
                if (newY > texture.getLast().getY()) {
                    throw new IndexOutOfBoundsException(String.format("Out of texture bounds x: %d, y: %d", newX, newY));
                }
                currPos = new CliPoint(texture.getFirst().getX(), newY);
            }
        } else {
            currPos = new CliPoint(newX, currPos.getY());
        }
        return texture.getCharAtPoint(currPos).orElse(CliTexture.TRANSPARENT_CHARACTER);
    }

    @Override
    public Iterator<Byte> iterator() {

        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return hasMorePoints();
            }

            @Override
            public Byte next() {
                return getAsciiCharForNextPoint();
            }
        };
    }
}
