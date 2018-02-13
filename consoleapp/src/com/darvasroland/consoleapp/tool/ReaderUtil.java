package com.darvasroland.consoleapp.tool;

import java.util.List;
import java.util.Scanner;

public class ReaderUtil {

    private static Scanner scanner = new Scanner(System.in);

    private static int readInXCoordinate(int height) {
        System.out.println("First the X Coordinate!");
        int x;
        do {
            System.out.println("Give a valid X coordinate:");
            x = Integer.parseInt(scanner.next());
        } while (!(x <= height) || (x <= 0));
        return x;
    }

    private static int readInYCoordinate(int width) {
        System.out.println("Now Y Coordinate!");
        int y;
        do {
            System.out.println("Give a valid Y coordinate:");
            y = Integer.parseInt(scanner.next());
        }
        while (!(y <= width) || (y <= 0));
        return y;
    }

    private static boolean validateCoordinates(int x, int y, int[] playerOnePosition, int[] playerTwoPosition) {
        System.out.println("Validating coordinates...if they are not valid, have to input them again!");
        return (x == playerOnePosition[0] && playerOnePosition[1] == y) || (x == playerTwoPosition[0] && y == playerTwoPosition[1]);
    }

    public static String chooseAction(List<String> actions) {
        String action = scanner.next().toUpperCase();
        while (!actions.contains(action)) {
            System.out.println("Wrong action! Choose properly!");
            action = scanner.next().toUpperCase();
        }
        return action;
    }

    public static int setRound() {
        System.out.println("How long should the game last? Set the number of rounds!");
        int rounds = 0;
        do {
            rounds = Integer.parseInt(scanner.next());
        } while (!(rounds >= 0));
        return rounds;
    }

    public static int validateAndReadPlayersNumbers(int size) {
        int number = Integer.parseInt(scanner.next());
        while (!(number <= size && number > 0)) {
            System.out.println("Choose a proper number!");
            number = Integer.parseInt(scanner.next());
        }
        return number;
    }

    public static String readInClassLocation() {
        System.out.println("We're going to load classes from a folder!");
        System.out.println("Specify the relative location of the folder!(press '0' " +
                "to use default location)");
        String classLocation = scanner.next();
        return classLocation;
    }

    public static double readInBoardParameter() {
        double size;
        do {
            System.out.println("This parameter should be bigger than 1!");
            size = Double.parseDouble(scanner.next());
        } while (size < 2.0);
        return size;
    }

    public static int[] readCoordinates(int height, int width, int[] playerOnePosition, int[] playerTwoPosition) {
        System.out.println("Choose two coordinates where your character want to move!");
        int x, y = 0;
        do {
            x = readInXCoordinate(height);
            y = readInYCoordinate(width);
        } while (validateCoordinates(x, y, playerOnePosition, playerTwoPosition));
        return new int[]{x, y};
    }

    public static String readInAttackDirections(List<String> directions) {
        System.out.println("Choose a direction to attack!");
        System.out.println("(N, W, E, S)");
        String direction;
        do {
            direction = scanner.next();
        } while(!directions.contains(direction));
        return direction;
    }


    public static void closeScanner() {
        scanner.close();
    }


}


