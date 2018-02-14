package com.darvasroland.consoleapp.iface;

import com.darvasroland.consoleapp.dao.action.Action;

/**
 * @author darvasr
 */
public interface IRobotBehaviour {

    void move(Action action);

    void attack(Action action);

    void defend(Action action);

    void skip(int playerNumber);
}
