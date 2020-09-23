package com.perfectsoft.game.texture;

import java.util.concurrent.ScheduledFuture;
import java.util.function.Supplier;

public interface StageRenderer {

    void renderStage(Supplier<Texture> stageTextureSupplier);

    ScheduledFuture<?> delayStageRendering(Supplier<Texture> stageTextureSupplier);
}
