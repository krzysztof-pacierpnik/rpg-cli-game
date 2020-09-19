package com.perfectsoft.game.physics.fromabove;

import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.physics.PhysicsStage;

import java.util.*;
import java.util.stream.Collectors;

public class FromAbovePhysicsStage implements PhysicsStage {

    private final LinkedList<FromAbovePhysicsCharacter> moveQueue;

    public FromAbovePhysicsStage(List<FromAbovePhysicsCharacter> characters) {

         moveQueue = characters.stream()
                 .sorted(Comparator.comparingInt(FromAbovePhysicsCharacter::getSpeed))
                 .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public PhysicsCharacter getMovingCharacter() {

        return moveQueue.getLast();
    }

    public void movingCharacterTurnEnd() {

        FromAbovePhysicsCharacter movingCharacter = moveQueue.removeLast();
        moveQueue.addFirst(movingCharacter);
    }
}
