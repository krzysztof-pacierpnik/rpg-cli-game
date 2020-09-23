package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.texture.Point;
import com.perfectsoft.game.texture.Texture;

import java.util.Collection;
import java.util.Objects;

public class PointTexture {

    private final Point point;
    private final Collection<Texture> textures;

    public PointTexture(Point point, Collection<Texture> textures) {
        this.point = point;
        this.textures = textures;
    }

    public Point getPoint() {
        return point;
    }

    public Collection<Texture> getTextures() {
        return textures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointTexture that = (PointTexture) o;
        return Objects.equals(point, that.point) &&
                Objects.equals(textures, that.textures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, textures);
    }
}
