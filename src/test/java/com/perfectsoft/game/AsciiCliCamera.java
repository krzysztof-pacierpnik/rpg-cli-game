package com.perfectsoft.game;

import com.perfectsoft.game.physics.PhysicsStage;
import com.perfectsoft.game.render.Camera;

import java.io.Console;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsciiCliCamera implements Camera {

    byte[][] stage = new byte[100][100];
    byte[][] character = readPicture("C:/Users/Krzysiek/Downloads/monster frog");
    byte[][] buff = new byte[100][100];
    int xPos = 0;
    int yPos = 0;

    public AsciiCliCamera() throws IOException {
        byte space = ' ';
        Arrays.stream(stage).forEach(row -> Arrays.fill(row, space));
    }

    public static void main(String[] args) throws IOException {

        AsciiCliCamera camera = new AsciiCliCamera();
        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();

        System.out.print("\033[H\033[2J");
        System.out.flush();


        es.scheduleAtFixedRate(camera::render, 0,1000, TimeUnit.MILLISECONDS);
        es.scheduleAtFixedRate(() -> camera.moveCharacter(1,1), 1000, 500, TimeUnit.MILLISECONDS);

        Console console = System.console();
        char cmd;
        do {
            cmd = (char) console.reader().read();
            switch (cmd) {
                case '8':
                    camera.moveCharacter(0,1);
                    break;
                case '5':
                    camera.moveCharacter(0, -1);
                    break;
                case '6':
                    camera.moveCharacter(1, 0);
                    break;
                case '4':
                    camera.moveCharacter(-1, 0);
                    break;
                case 'r':
                    camera.rotateCharacter();
            }

        } while (cmd != 'q');
    }

    public void moveCharacter(final int x, final int y) {
        xPos += x;
        yPos += y;
    }

    public void rotateCharacter() {
        character = rotatePicture(character);
    }

    public void render() {
        for (int y = 0; y < buff.length; y++) {
            for (int x = 0; x < buff.length; x ++) {
                int xBeg = xPos;
                int xEnd = xPos + character[0].length - 1;
                int yBeg = yPos;
                int yEnd = yPos + character.length - 1;
                if ((x >= xBeg && x <= xEnd) && (y >= yBeg && y <= yEnd)) {
                    buff[y][x] = character[y-yBeg][x-xBeg];
                } else {
                    buff[y][x] = stage[y][x];
                }
            }
        }

        System.out.print("\033[H");
        Arrays.stream(buff).map(String::new).forEach(System.out::println);
        System.out.flush();
    }

    @Override
    public void render(PhysicsStage physicsStage) {
        for (int y = 0; y < buff.length; y++) {
            for (int x = 0; x < buff.length; x ++) {
                int xBeg = xPos;
                int xEnd = xPos + character[0].length - 1;
                int yBeg = yPos;
                int yEnd = yPos + character.length - 1;
                if ((x >= xBeg && x <= xEnd) && (y >= yBeg && y <= yEnd)) {
                    buff[y][x] = character[y-yBeg][x-xBeg];
                } else {
                    buff[y][x] = stage[y][x];
                }
            }
        }

        Arrays.stream(buff).map(String::new).forEach(System.out::println);
        System.out.flush();
    }

    private byte[][] rotatePicture(byte[][] picture) {
        byte[][] rotatedPicture = new byte [picture[0].length][];
        for (int r = 0; r < picture.length; r++) {
            for (int c = 0; c < picture[r].length; c++) {
                int rotRowIdx = picture[0].length - c - 1;
                if (rotatedPicture[rotRowIdx] == null) {
                    rotatedPicture[rotRowIdx] = new byte[picture.length];
                }
                rotatedPicture[rotRowIdx][r] = picture[r][c];
            }
        }
        return rotatedPicture;
    }

    private byte[][] readPicture(final String filePath) throws IOException {

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
