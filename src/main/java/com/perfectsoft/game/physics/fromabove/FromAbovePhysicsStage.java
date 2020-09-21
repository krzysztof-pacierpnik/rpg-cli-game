package com.perfectsoft.game.physics.fromabove;

import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.physics.PhysicsStage;
import com.perfectsoft.game.physics.Position;

import java.util.*;
import java.util.stream.Collectors;

public class FromAbovePhysicsStage implements PhysicsStage {

    private final LinkedList<FromAbovePhysicsCharacter> moveQueue;

    private int fieldSize;
    private int xSize;
    private int ySize;

    public FromAbovePhysicsStage(List<FromAbovePhysicsCharacter> characters) {

         moveQueue = characters.stream()
                 .sorted(Comparator.comparingInt(FromAbovePhysicsCharacter::getSpeed))
                 .collect(Collectors.toCollection(LinkedList::new));
         characters.forEach(character -> {
             character.setPhysicsStage(this);
             character.initForStage();
         });
    }

    @Override
    public PhysicsCharacter getMovingCharacter() {

        return moveQueue.getLast();
    }

    public boolean isOutOfBound(FromAbovePhysicsCharacter character) {

        Position pos = character.getNewPosition();
        return pos.getX() >= xSize || pos.getX() < 0 || pos.getY() >= ySize || pos.getY() < 0;
    }

    public Optional<FromAbovePhysicsCharacter> detectCollision(FromAbovePhysicsCharacter character) {
        return moveQueue.stream().
                filter(otherChar -> !otherChar.equals(character) && otherChar.getPosition().equals(character.getNewPosition()))
                .findAny();
    }

    public void movingCharacterTurnEnd() {

        FromAbovePhysicsCharacter movingCharacter = moveQueue.removeLast();
        moveQueue.addFirst(movingCharacter);
    }


}
