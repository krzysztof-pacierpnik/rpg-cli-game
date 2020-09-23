package com.perfectsoft.game.physics.fromabove;

import com.perfectsoft.game.physics.PhysicsCharacter;
import com.perfectsoft.game.physics.PhysicsStage;
import com.perfectsoft.game.physics.Position;
import com.perfectsoft.game.render.RenderStage;

import java.util.*;
import java.util.stream.Collectors;

public class FromAbovePhysicsStage implements PhysicsStage {

    private final RenderStage renderStage;
    private final Collection<FromAbovePhysicsCharacter> characters;

    private Position size;
    private LinkedList<FromAbovePhysicsCharacter> moveQueue;

    public FromAbovePhysicsStage(RenderStage renderStage, Collection<FromAbovePhysicsCharacter> characters) {

        this.renderStage = renderStage;
        this.characters = characters;
        this.renderStage.setPhysicsStage(this);
    }

    @Override
    public void initStage() {

        moveQueue = characters.stream()
                .filter(FromAbovePhysicsCharacter::isAlive)
                .sorted(Comparator.comparingInt(FromAbovePhysicsCharacter::getSpeed))
                .collect(Collectors.toCollection(LinkedList::new));
        characters.forEach(character -> {
            character.setPhysicsStage(this);
            character.initForStage();
        });
        renderStage.initStage();
    }

    @Override
    public PhysicsCharacter getMovingCharacter() {

        return moveQueue.getLast();
    }

    public boolean isInBound(Position pos) {

        int sizeX = size.getX();
        int sizeY = size.getY();
        int posX = pos.getX();
        int posY = pos.getY();
        return posX < sizeX && posX >= 0 && posY < sizeY && posY >= 0;
    }

    public Optional<FromAbovePhysicsCharacter> detectCollision(FromAbovePhysicsCharacter character) {

        return moveQueue.stream().
                filter(otherChar -> !otherChar.equals(character) && otherChar.getPosition().equals(character.getNewPosition()))
                .findAny();
    }

    public void movingCharacterTurnEnd() {

        FromAbovePhysicsCharacter movingCharacter = moveQueue.removeLast();
        moveQueue.addFirst(movingCharacter);
        FromAbovePhysicsCharacter nextMovingCharacter = moveQueue.getLast();
        nextMovingCharacter.startTurn();
    }

    public void characterKilled(FromAbovePhysicsCharacter physicsCharacter) {
        moveQueue.remove(physicsCharacter);
    }

    @Override
    public void setSize(Position size) {
        this.size = size;
    }
}
