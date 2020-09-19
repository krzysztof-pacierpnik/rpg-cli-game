package com.perfectsoft.game;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class AsciiPictureRotationTest {

    @Test
    public void rotatePicture() throws IOException {
        byte[][] picture = readPicture("C:/Users/Krzysiek/Downloads/monster frog");
        byte[][] rotatedPicture = rotatePicture(picture);
        savePicture(rotatedPicture, "C:/Users/Krzysiek/Downloads/rotated monster frog");
        Arrays.stream(picture).map(String::new).forEach(System.out::println);
        System.out.println("==================================================================");
        Arrays.stream(rotatedPicture).map(String::new).forEach(System.out::println);
    }

    @Test
    public void consolePosition() throws UnsupportedEncodingException {

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new
                FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 512);


        System.out.print("==================================================================\n");
        char escCode = 0x1B;
        System.out.print(String.format("%c[1A",escCode));
        System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
        System.out.print("########");
    }

    private void savePicture(byte[][] picture, String filePath) {

        Path pictureFile = Paths.get(filePath);
        Arrays.stream(picture).reduce((t1, t2) -> {
            byte[] res = new byte[t1.length + t2.length + 1];
            System.arraycopy(t1, 0, res, 0, t1.length);
            System.arraycopy(t2, 0, res, t1.length, t2.length);
            res[res.length - 1] = '\n';
            return res;
        }).ifPresent(pictureFileContent -> {
            try {
                Files.write(pictureFile, pictureFileContent);
            } catch (IOException e) {
                throw new RuntimeException("Can't write file", e);
            }
        });
    }

    private byte[][] rotatePicture(byte[][] picture) {
        byte[][] rotatedPicture = new byte [picture[0].length][];
        for (int r = 0; r < picture.length; r++) {
            for (int c = 0; c < picture[r].length; c++) {
                int rotRowIdx = picture[0].length - c - 1;
                int rotColIdx = r;
                if (rotatedPicture[rotRowIdx] == null) {
                    rotatedPicture[rotRowIdx] = new byte[picture.length];
                }
                rotatedPicture[rotRowIdx][rotColIdx] = picture[r][c];
            }
        }
        return rotatedPicture;
    }

    private byte[][] readPicture(String filePath) throws IOException {
        Path pictureFile = Paths.get(filePath);
        List<String> lines = Files.readAllLines(pictureFile, StandardCharsets.US_ASCII);
        byte[][] rawContent = lines.stream().map(String::getBytes).toArray(byte[][]::new);
        int maxLineLength = Arrays.stream(rawContent).map(row -> row.length).max(Integer::compareTo).orElse(0);
        byte[][] normalizedContent = Arrays.stream(rawContent).map(row -> {
            byte[] normalizedRow = row;
            if (row.length < maxLineLength) {
                normalizedRow = new byte[maxLineLength];
                System.arraycopy(row, 0, normalizedRow, 0, row.length);
                byte space = ' ';
                Arrays.fill(normalizedRow, row.length, normalizedRow.length, space);
            }
            return normalizedRow;
        }).toArray(byte[][]::new);
        return normalizedContent;
    }

}