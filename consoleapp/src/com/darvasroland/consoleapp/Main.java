package com.darvasroland.consoleapp;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Scanner;

import com.darvasroland.consoleapp.dao.BoardInit;
import com.darvasroland.consoleapp.tool.Board;
import com.darvasroland.consoleapp.tool.Game;
import com.darvasroland.consoleapp.util.FileClassLoader;

/**
 * @author darvasr
 */
public class Main {

    public static void main(String[] args) {
        Game.getInstance().startGame();
    }
}
