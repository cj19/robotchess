package com.darvasroland.consoleapp.tool;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.darvasroland.consoleapp.abtract.Robot;
import com.darvasroland.consoleapp.dao.BoardInit;
import com.darvasroland.consoleapp.dao.action.Action;
import com.darvasroland.consoleapp.dao.action.Direction;
import com.darvasroland.consoleapp.util.FileClassLoader;

public class Game {

    private final File defaultLocation = Paths.get("./src/com/darvasroland/consoleapp/player").toFile();

    private int rounds;

    private Board board;

    private Robot[] players = new Robot[2];

    private FileClassLoader fileClassLoader;

    private static Game game;

    private List<String> actions = Arrays.asList("OP", "EP", "AS", "OA", " EA", "M", "A", "D", "S");

    private List<String> directions = Arrays.asList("N", "W", "E", "S");

    private Game() {
        //private constructor
    }

    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }

        return game;
    }

    public void startGame() {
        System.out.println("Welcome to Robot Chess");
        loadClasses();
        choosePlayers();
        setRound();
        fillBoard();
        startRounds();
    }

    private void startRounds() {
        System.out.println("The Game has begun!");
        int i = 1;
        do {
            System.out.println("\n" + i + ". Round!");
            listPlayersAndBoardState();
            firstPlayerRound();
            secondPlayerRound();
            calculateAttackAndDefense();
            updateBoard();
            resetPlayersState();
            i++;
        } while (i <= rounds && checkPlayersArmor());
        endGameResolution();
    }


    private void firstPlayerRound() {
        System.out.println("\n'A' robot's round!");
        System.out.println(players[0].getClassName());
        takeTurn(1);
    }

    private void secondPlayerRound() {
        System.out.println("\n'B' robot's round!");
        System.out.println(players[1].getClassName());
        takeTurn(2);
    }

    private void takeTurn(int playerNumber) {
        System.out.println("\nWhat's gonna be your next move?");
        availableActions();
        String action = chooseAction();
        switch (action) {
            case "OP":
                System.out.println(players[playerNumber - 1].getPositionInString());
                break;
            case "EP":
                System.out.println(players[playerNumber - 1].getEnemyPositionInString());
                break;
            case "AS":
                System.out.println(players[playerNumber - 1].getArenaSizeInString());
                break;
            case "OA":
                System.out.println(players[playerNumber - 1].getArmorInString());
                break;
            case "EA":
                System.out.println(players[playerNumber - 1].getEnemyArmorInString());
                break;
            case "M":
                createMoveAction(playerNumber);
                break;
            case "A":
                createAttackAction(playerNumber);
                break;
            case "D":
                createDefendAction(playerNumber);
                break;
            case "S":
                skipTurn(playerNumber);
                break;
        }
    }


    private void endGameResolution() {
        System.out.println("\nThe Game has ended!\n");
        System.out.println("Results: ");
        if (players[0].getArmor() > players[1].getArmor()) {
            System.out.println("Player One is the winner!");
        } else if (players[0].getArmor() < players[1].getArmor()) {
            System.out.println("Player Two is the winner!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    private void calculateAttackAndDefense() {
        System.out.println("Calculating defense and attack moves...");
        if (players[0].getAttackingDirection() != Direction.X) {
            if (isNotOppositeDirection(0, 1)) {
                players[1].setArmor(players[1].getArmor() - 1);
            }
        }
        if (players[1].getAttackingDirection() != Direction.X) {
            if (isNotOppositeDirection(1, 0)) {
                players[0].setArmor(players[0].getArmor() - 1);
            }
        }
    }

    private boolean isNotOppositeDirection(int i, int j) {
        return isNorthSouthDirection(i, j) || (isEastWestDirection(i, j));
    }


    private boolean isNorthSouthDirection(int i, int j) {
        return (players[i].getAttackingDirection().equals(Direction.N) && !players[j].getDefendingDirection().equals(Direction.S) &&
                players[i].getPosition()[1] > players[j].getPosition()[1]) || (players[i].getAttackingDirection().equals(Direction.S) &&
                !players[j].getDefendingDirection().equals(Direction.N) && players[i].getPosition()[1] < players[j].getPosition()[1]);
    }

    private boolean isEastWestDirection(int i, int j) {
        return (players[i].getAttackingDirection().equals(Direction.E) && !players[j].getDefendingDirection().equals(Direction.W) &&
                players[i].getPosition()[0] < players[j].getPosition()[0]) ||
                (players[i].getDefendingDirection().equals(Direction.W) && !players[j].getDefendingDirection().equals(Direction.E) &&
                        players[i].getPosition()[0] > players[j].getPosition()[0]);
    }

    private void resetPlayersState() {
        players[0].setAttackingDirection(Direction.X);
        players[0].setDefendingDirection(Direction.X);
        players[1].setAttackingDirection(Direction.X);
        players[1].setDefendingDirection(Direction.X);
    }

    private boolean checkPlayersArmor() {
        return players[0].getArmor() > 0 || players[1].getArmor() > 0;
    }

    private void skipTurn(int playerNumber) {
        players[playerNumber - 1].skip(playerNumber);
    }

    private void createDefendAction(int playerNumber) {
        String direction = ReaderUtil.readInDirections(directions);
        Action defend = new Action();
        defend.setDefend(direction);
        players[playerNumber - 1].defend(defend);
    }

    private void createAttackAction(int playerNumber) {
        String direction = ReaderUtil.readInDirections(directions);
        Action attack = new Action();
        attack.setAttack(direction);
        players[playerNumber - 1].attack(attack);
    }


    private void createMoveAction(int playerNumber) {
        int[] movement = ReaderUtil.readCoordinates(board.getHeight(), board.getWidth(),
                board.getPlayerOnePosition(), board.getPlayerTwoPosition());
        Action move = new Action();
        move.setMove(movement);
        players[playerNumber - 1].move(move);

    }

    private String chooseAction() {
        System.out.println("\nChoose an action by it's short name!");
        return ReaderUtil.chooseAction(actions);
    }

    private void availableActions() {
        System.out.println("Check Own Position: 'OP'");
        System.out.println("Check Enemy Position: 'EP'");
        System.out.println("Check The Size of Arena: 'AS'");
        System.out.println("Check Own Armor: OA");
        System.out.println("Check Enemy Armor: 'EA'");
        System.out.println("Also, you can Move('M'), Attack('A'), Defend('D') and Skip Turn(S)");
    }

    private void setRound() {
        rounds = ReaderUtil.setRound();
    }

    private void listPlayersAndBoardState() {
        int j = 1;
        board.getBoardState();
        for (Robot player : players) {
            System.out.println("");
            System.out.println(j + ". " + (j == 1 ? "'A'" : "'B'") + " robot: " + player.getClassName());
            System.out.println("\t" + player.getArmorInString());
            j++;
        }
    }

    private void choosePlayers() {
        System.out.println("Choose two classes by their number! Every class has the same attributes!");
        System.out.println("Pick a class for player one: ");
        int playerOneNumber = ReaderUtil.validateAndReadPlayersNumbers(fileClassLoader.getClasses().size());
        System.out.println("Pick a class for player two: ");
        int playerTwoNumber = ReaderUtil.validateAndReadPlayersNumbers(fileClassLoader.getClasses().size());
        players[0] = classInstantiation(playerOneNumber);
        players[1] = classInstantiation(playerTwoNumber);
    }

    private <T extends Robot> T classInstantiation(int playerNumber) {
        Object instance = null;
        try {
            Class clazz = Class.forName(fileClassLoader.getClasses().get(playerNumber - 1).getName());
            Constructor constructor = clazz.getConstructor(int.class, int.class);

            instance = constructor.newInstance(10, 10);
        } catch (ClassNotFoundException e) {
            System.out.println("There is no such class in the package: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println("There is no such constructor in that class: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("Could not instantiate the class: " + e.getMessage());
        } catch (InvocationTargetException e) {
            System.out.println("Target could not get invoked: " + e.getMessage());
        }
        return (T) instance;
    }

    private void loadClasses() {
        String classLocation = ReaderUtil.readInClassLocation();
        if (classLocation.equals("0")) {
            defineLocation(defaultLocation);
        } else {
            File desiredPath = new File(classLocation);
            defineLocation(desiredPath);
        }
    }

    private void updateBoard() {
        board.updateBoard(players[0].getPosition(), players[1].getPosition());
    }

    private void fillBoard() {
        System.out.println("Let's create the Board!");
        System.out.println("This amount determines how wide the board will be..:");
        double width = ReaderUtil.readInBoardParameter();
        System.out.println("This amount determines how high the board will be..:");
        double height = ReaderUtil.readInBoardParameter();
        int[] playerOnePosition = setPlayerOnePositionAndName(width, height);
        int[] playerTwoPosition = setPlayerTwoPositionAndName(width, height);
        BoardInit boardInit = new BoardInit((int) width, (int) height, playerOnePosition, playerTwoPosition);
        board = new Board(boardInit);
    }

    private int[] setPlayerOnePositionAndName(double width, double height) {
        int[] playerOnePosition = new int[2];
        playerOnePosition[1] = 1;
        playerOnePosition[0] = (int) Math.ceil(width / 2.0);
        players[0].setPosition(playerOnePosition[0], playerOnePosition[1]);
        players[0].setArenaSize((int) height, (int) width);
        players[0].setEnemyPosition((int) Math.ceil(width / 2.0), (int) height);
        players[0].setClassName(createClassName(players[0].getClass().getName()));
        return playerOnePosition;
    }

    private int[] setPlayerTwoPositionAndName(double width, double height) {
        int[] playerTwoPosition = new int[2];
        playerTwoPosition[1] = (int) height;
        playerTwoPosition[0] = (int) Math.ceil(width / 2.0);
        players[1].setPosition(playerTwoPosition[0], playerTwoPosition[1]);
        players[1].setArenaSize((int) height, (int) width);
        players[1].setEnemyPosition((int) Math.ceil(width / 2.0), 1);
        players[1].setClassName(createClassName(players[1].getClass().getName()));
        return playerTwoPosition;
    }

    private void defineLocation(File defaultLocation) {
        fileClassLoader = new FileClassLoader.FileClassLoaderBuilder().setClassLoaderAndFile(defaultLocation).loadClassesFromDirectory().loadClasses().build();
        fileClassLoader.listAvailableClasses();
    }

    private String createClassName(String classname) {
        return classname.substring(classname.lastIndexOf('.') + 1, classname.length()) + ".class";
    }
}
