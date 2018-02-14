package com.darvasroland.consoleapp.abtract;

import com.darvasroland.consoleapp.dao.action.Action;
import com.darvasroland.consoleapp.dao.action.Direction;
import com.darvasroland.consoleapp.iface.IRobotBehaviour;

/**
 * @author darvasr
 */
public abstract class Robot implements IRobotBehaviour {

    private int[] position = new int[2];
    private int[] enemyPosition = new int[2];
    private int[] arenaSize = new int[2];
    private int armor;
    private int enemyArmor;
    private Direction defendingDirection;
    private Direction attackingDirection;
    private String className;

    public Robot(int armor, int enemyArmor) {
        this.armor = armor;
        this.enemyArmor = enemyArmor;
        this.attackingDirection = Direction.X;
        this.defendingDirection = Direction.X;
    }

    public String getPositionInString() {
        return className + "'s position: " + position[0] + "," + position[1];
    }

    public String getEnemyPositionInString() {
        return className + "'s enemy's position: " + enemyPosition[0] + "," + enemyPosition[1];
    }

    public String getArenaSizeInString() {
        return "Arena size: " + arenaSize[0] + "," + arenaSize[1];
    }

    public String getArmorInString() {
        return "Armor: " + armor + "/10";
    }

    public int getArmor() {
        return armor;
    }

    public String getEnemyArmorInString() {
        return "Enemy Armor: "+enemyArmor+"/10";
    }

    public int[] getPosition() {
        return position;
    }

    public Direction getDefendingDirection() {
        return defendingDirection;
    }

    public void setDefendingDirection(Direction defendingDirection) {
        this.defendingDirection = defendingDirection;
    }

    public Direction getAttackingDirection() {
        return attackingDirection;
    }

    public void setAttackingDirection(Direction attackingDirection) {
        this.attackingDirection = attackingDirection;
    }

    public String getClassName() {
        return className;
    }

    public void setPosition(int a, int b) {
        this.position[0] = a;
        this.position[1] = b;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setEnemyArmor(int enemyArmor) {
        this.enemyArmor = enemyArmor;
    }

    public void setEnemyPosition(int a, int b) {
        this.enemyPosition[0] = a;
        this.enemyPosition[1] = b;
    }

    public void setArenaSize(int a, int b) {
        this.arenaSize[0] = a;
        this.arenaSize[1] = b;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public void move(Action action) {
        setPosition(action.getMove().getX(), action.getMove().getY());
    }

    @Override
    public void attack(Action action) {
        setAttackingDirection(action.getAttack().getDirection());
        setDefendingDirection(Direction.X);
    }

    @Override
    public void defend(Action action) {
        setDefendingDirection(action.getDefend().getDirection());
        setAttackingDirection(Direction.X);
    }

    @Override
    public void skip(int playerNumber) {
        System.out.println("\n" + playerNumber + ". player skipping the turn!\n");
    }
}
