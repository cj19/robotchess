package com.darvasroland.consoleapp.dao.action;

import com.darvasroland.consoleapp.dao.action.Move;

public class Action {

        private Move move;

        private Attack attack;

        private Defend defend;

        private boolean skipTurn;

    public Move getMove() {
        return move;
    }

    public Attack getAttack() {
        return attack;
    }

    public Defend getDefend() {
        return defend;
    }

    public boolean isSkipTurn() {
        return skipTurn;
    }

    public void setMove(int[] coordinates) {
        this.move = new Move();
        this.move.setX(coordinates[0]);
        this.move.setY(coordinates[1]);
    }

    public void setAttack(String direction) {
        this.attack = new Attack();
        this.attack.setDirection(Direction.valueOf(direction));
    }
}
