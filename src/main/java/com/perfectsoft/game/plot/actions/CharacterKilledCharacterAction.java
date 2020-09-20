package com.perfectsoft.game.plot.actions;

import com.perfectsoft.game.plot.PlotCharacter;
import com.perfectsoft.game.plot.PlotStage;

import java.util.Objects;
import java.util.function.Consumer;

public class CharacterKilledCharacterAction implements Consumer<PlotStage> {

    private final PlotCharacter killer;
    private final PlotCharacter victim;

    CharacterKilledCharacterAction(PlotCharacter killer, PlotCharacter victim) {
        this.killer = killer;
        this.victim = victim;
    }

    @Override
    public void accept(PlotStage plotStage) {
        plotStage.characterKilledCharacter(killer, victim);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterKilledCharacterAction that = (CharacterKilledCharacterAction) o;
        return Objects.equals(killer, that.killer) &&
                Objects.equals(victim, that.victim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(killer, victim);
    }
}
