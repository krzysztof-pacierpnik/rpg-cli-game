package com.perfectsoft.game.plot;

import java.util.function.Consumer;

public interface PlotActionChannel {

    void subscribe(Consumer<Consumer<PlotStage>> plotSubscriber);

    void unsubscribe(Consumer<Consumer<PlotStage>> plotSubscriber);

    void publish(Consumer<PlotStage> plotAction);

    void executeActions();
}
