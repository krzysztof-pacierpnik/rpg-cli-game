package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.texture.Point;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.TextureTemplate;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

public final class CliTextureReader {

    private CliTextureReader() {}

    public static TextureTemplate readTextureTemplate(String textureName) {
        return new CliTextureTemplate(readPoints(textureName, new CliPoint(0, 0)));
    }

    public static Texture readTexture(String textureName, Point transition) {
        return new CliTexture(readPoints(textureName, transition));
    }

    public static Collection<CliCharPoint> readPoints(String textureName, Point transition) {

        String path = String.format("/textures/%s.txt", textureName);
        InputStream inputStream = new BufferedInputStream(CliTextureReader.class.getResourceAsStream(path));
        return readPointsFromStream(textureName, transition, inputStream);
    }

    private static ArrayList<CliCharPoint> readPointsFromStream(String textureName, Point transition,
                                                                InputStream inputStream) {

        ArrayList<CliCharPoint> normalizedPoints = new ArrayList<>();
        try (inputStream) {

            int maxXSize = 0;
            int x = transition.getX();
            int y = transition.getY();
            int intChar;
            Map<Integer, Integer> xSizes = new HashMap<>();
            while ((intChar = inputStream.read()) != -1) {

                byte character = (byte) intChar;
                if (character == '\n') {
                    maxXSize = Integer.max(x, maxXSize);
                    xSizes.put(y, x);
                    y++;
                    x = transition.getX();
                } else if (character != '\r') {
                    normalizedPoints.add(new CliCharPoint(x, y, character));
                    x++;
                }
            }

            final int maxXSizeFin = maxXSize;
            xSizes.forEach((key, value) -> IntStream.range(value, maxXSizeFin).forEach(
                    ix -> normalizedPoints.add(new CliCharPoint(ix, key, CliTexture.TRANSPARENT_CHARACTER))));


        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to read texture: %s", textureName),e);
        }

        return normalizedPoints;
    }
}
