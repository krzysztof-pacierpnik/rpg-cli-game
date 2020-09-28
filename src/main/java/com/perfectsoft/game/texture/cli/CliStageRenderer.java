package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.render.cli.CliStageScreen;
import com.perfectsoft.game.texture.StageRenderer;
import com.perfectsoft.game.texture.Texture;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class CliStageRenderer implements StageRenderer {

    static final String RETURN_CURSOR_SEQ = "\033[H";
    static final String CLEAR_CONSOLE_UNDER_CURSOR_SEQ = "\033[2J";
    static final String RETURN_CLEAR_SEQ = RETURN_CURSOR_SEQ + CLEAR_CONSOLE_UNDER_CURSOR_SEQ;

    private final Texture stageScreenTexture;
    private final Supplier<Texture> stageMenuTextureSupplier;
    private final OutputStream renderStream;
    private final int animationDelayMillis;
    private final Queue<CliStageScreen> screensToRender;
    private final ScheduledExecutorService renderSchedule;

    public CliStageRenderer(Texture stageScreenTexture, Supplier<Texture> stageMenuTextureSupplier,
                            OutputStream renderStream, int animationDelayMillis) {

        this.stageScreenTexture = stageScreenTexture;
        this.stageMenuTextureSupplier = stageMenuTextureSupplier;
        this.renderStream = renderStream;
        this.animationDelayMillis = animationDelayMillis;

        screensToRender = new ConcurrentLinkedQueue<>();
        renderSchedule = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void renderStage(Supplier<Texture> stageTextureSupplier) {

        CliStageScreen screen = new CliStageScreen(stageScreenTexture, stageTextureSupplier, stageMenuTextureSupplier);
        printStageScreen(screen.get());
    }

    @Override
    public ScheduledFuture<?> delayStageRendering(Supplier<Texture> stageTextureSupplier) {

        ScheduledFuture<?> schedule = renderSchedule.schedule(this::pollPrintScreen, animationDelayMillis,
                TimeUnit.MILLISECONDS);
        CliStageScreen screen = new CliStageScreen(stageScreenTexture, stageTextureSupplier, stageMenuTextureSupplier);
        screensToRender.add(screen);
        return schedule;
    }

    private void pollPrintScreen() {

        boolean rendered = false;
        do {
            CliStageScreen screen = screensToRender.poll();
            if (screen != null) {
                printStageScreen(screen.get());
                rendered = true;
            }
        } while (!rendered);
    }

    void printStageScreen(Texture screenTexture) {
        try {
            renderStream.write(RETURN_CLEAR_SEQ.getBytes());
            for (Byte aByte : screenTexture.iterator()) {
                renderStream.write(aByte);
            }
            renderStream.write('\n');
            renderStream.write('\r');
            renderStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to render stream", e);
        }
    }
}
