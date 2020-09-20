package com.perfectsoft.game.dao.properties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public final class FilesUtils {

    private FilesUtils() {}

    public static Properties loadProperties(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(FilesUtils.class.getResourceAsStream(path));
        return properties;
    }

    public static Properties readTemplate(String templateGroup, String templateName) throws IOException {

        String path = String.format("/template/%s/%s.properties", templateGroup, templateName);
        return loadProperties(path);
    }

    public static byte[][] readTexture(String templateGroup, String textureName) throws IOException, URISyntaxException {

        String path = String.format("/template/%s/%s.txt", templateGroup, textureName);
        Path pictureFile = Paths.get(FilesUtils.class.getResource(path).toURI().getPath());
        List<String> lines = Files.readAllLines(pictureFile, StandardCharsets.US_ASCII);
        byte[][] rawContent = lines.stream().map(String::getBytes).toArray(byte[][]::new);
        int maxLineLength = Arrays.stream(rawContent).map(row -> row.length).max(Integer::compareTo).orElse(0);
        return Arrays.stream(rawContent).map(row -> {
            byte[] normalizedRow = row;
            if (row.length < maxLineLength) {
                normalizedRow = new byte[maxLineLength];
                System.arraycopy(row, 0, normalizedRow, 0, row.length);
                byte space = ' ';
                Arrays.fill(normalizedRow, row.length, normalizedRow.length, space);
            }
            return normalizedRow;
        }).toArray(byte[][]::new);
    }
}
