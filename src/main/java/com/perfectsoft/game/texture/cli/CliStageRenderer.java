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

    private final Texture stageScreenTexture;
    private final Supplier<Texture> stageMenuTextureSupplier;
    private final int animationDelayMillis;
    private final Queue<CliStageScreen> screensToRender;
    private final ScheduledExecutorService renderSchedule;

    public CliStageRenderer(Texture stageScreenTexture, Supplier<Texture> stageMenuTextureSupplier,
                            int animationDelayMillis) {

        this.stageScreenTexture = stageScreenTexture;
        this.stageMenuTextureSupplier = stageMenuTextureSupplier;
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
        } while(rendered);
    }

    void printStageScreen(Texture screenTexture) {

        try(OutputStream cliOutStream = new BufferedOutputStream(System.out)) {

            for (Byte aByte : screenTexture.iterator()) {
                cliOutStream.write(aByte);
            }
            cliOutStream.flush();

        } catch (IOException e) {
            throw new RuntimeException("Failed to print stage screen", e);
        }

    }
}
