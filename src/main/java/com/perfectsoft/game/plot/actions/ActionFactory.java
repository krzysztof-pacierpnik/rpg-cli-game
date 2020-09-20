package com.perfectsoft.game.plot.actions;

import com.perfectsoft.game.plot.PlotCharacter;

public class ActionFactory {

    private final static ActionFactory actionFactory = new ActionFactory();

    private final DoNothingAction doNothingAction = new DoNothingAction();
    private final FinishStageAction finishStageAction = new FinishStageAction();
    private final HeroDiedAction heroDiedAction = new HeroDiedAction();
    private final RunStageAction runStageAction = new RunStageAction();

    private ActionFactory() {}

    public static ActionFactory getInstance() {
        return actionFactory;
    }

    public CharacterKilledCharacterAction characterKilledCharacterAction(PlotCharacter killer, PlotCharacter victim) {
        return new CharacterKilledCharacterAction(killer, victim);
    }

    public  DoNothingAction getDoNothingAction() {
        return doNothingAction;
    }

    public FinishStageAction finishStageAction() {
        return finishStageAction;
    }

    public HeroDiedAction heroDiedAction() {
        return heroDiedAction;
    }

    public RunStageAction runStageAction() {
        return runStageAction;
    }
}
