package com.perfectsoft.game.plot.engine;

import com.perfectsoft.game.plot.PlotActionChannel;
import com.perfectsoft.game.plot.PlotStage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class PlotEngineActionChannel implements PlotActionChannel {

    private final LinkedList<Consumer<PlotStage>> actionQueue = new LinkedList<>();
    private final List<Consumer<Consumer<PlotStage>>> subscribers = new ArrayList<>();

    @Override
    public void subscribe(Consumer<Consumer<PlotStage>> plotSubscriber) {
        subscribers.add(plotSubscriber);
    }

    @Override
    public void unsubscribe(Consumer<Consumer<PlotStage>> plotSubscriber) {
        subscribers.remove(plotSubscriber);
    }

    @Override
    public void publish(Consumer<PlotStage> plotAction) {
        actionQueue.addLast(plotAction);
    }

    @Override
    public void executeActions() {
        subscribers.forEach(actionQueue::forEach);
        actionQueue.clear();
    }
}
