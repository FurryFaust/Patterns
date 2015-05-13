package com.furryfaust.patterns;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;

public class Manager {

    public int[][] tiles, winningTiles;
    public Tasks.shiftTask shiftTask;
    public Tasks.countTask countTask;
    public int movesPerformed, timePlayed;
    public int difficulty;

    public Manager() {
        countTask = new Tasks.countTask(this);
    }

    public void prepare(int size, int difficulty) {
        tiles = new int[size][size];
        populate(tiles);

        String s = "";
        for (int i = 0; i != 4; i++) {
            for (int j = 0; j != 4; j++) {
                s += tiles[i][j] + " ";
            }
        }
        System.out.println(s);
        System.out.println(s.length());

        winningTiles = new int[size][size];
        populate(winningTiles);
        scramble(difficulty);
        movesPerformed = 0;
        timePlayed = 0;
        this.difficulty = difficulty;
        countTask.cancel();
        Timer.schedule(countTask, 1F, 1F);
    }

                /*
            General Integer to Direction Key
                        0 - UP
                        1 - DOWN
                        2 - LEFT
                        3 - RIGHT
                */

    public void populate(int[][] arrays) {
        final int SQUARE_LENGTH = arrays.length;
        int pointerX = 0;
        int pointerY = 0;
        int direction = 0;
        for (int i = 0; i != SQUARE_LENGTH * SQUARE_LENGTH; i++) {
            boolean set = false;
            while (!set) {
                if (arrays[pointerX][pointerY] == 0) {
                    arrays[pointerX][pointerY] = i;
                    set = true;
                } else {
                    switch (direction) {
                        case 0:
                            if (pointerX - 1 < 0 || arrays[pointerX - 1][pointerY] != 0) {
                                direction++;
                            } else {
                                pointerX--;
                                break;
                            }
                        case 1:
                            if (pointerX + 1 > SQUARE_LENGTH - 1 || arrays[pointerX + 1][pointerY] != 0) {
                                direction++;
                            } else {
                                pointerX++;
                                break;
                            }
                        case 2:
                            if (pointerY - 1 < 0 || arrays[pointerX][pointerY - 1] != 0) {
                                direction++;
                            } else {
                                pointerY--;
                                break;
                            }
                        case 3:
                            if (pointerY + 1 > SQUARE_LENGTH - 1 || arrays[pointerX][pointerY + 1] != 0) {
                                direction = 0;
                            } else {
                                pointerY++;
                                break;
                            }
                    }
                }
            }
        }
    }

    public void scramble(int chaos) {
        if (chaos != 0) {
            Random random = new Random();
            for (int i = 0; i != chaos; i++) {
                shiftTile(random.nextInt(4), true);
            }
        }
    }

    public void prepareShift(int direction, int length, float speed) {
        if (!checkBoard() && (shiftTask == null || !shiftTask.isScheduled())) {
            int preparedNumber = 0;
            switch (direction) {
                case 0:
                    if (getEmptySlot().y != tiles.length - 1) {
                        preparedNumber = tiles[(int) getEmptySlot().x][(int) getEmptySlot().y + 1];
                    }
                    break;
                case 1:
                    if (getEmptySlot().y != 0) {
                        preparedNumber = tiles[(int) getEmptySlot().x][(int) getEmptySlot().y - 1];
                    }
                    break;
                case 2:
                    if (getEmptySlot().x != 0) {
                        preparedNumber = tiles[(int) getEmptySlot().x - 1][(int) getEmptySlot().y];
                    }
                    break;
                case 3:
                    if (getEmptySlot().x != tiles.length - 1) {
                        preparedNumber = tiles[(int) getEmptySlot().x + 1][(int) getEmptySlot().y];
                    }
                    break;
            }
            if (preparedNumber != 0) {
                shiftTask = new Tasks.shiftTask(this, preparedNumber, direction, length, speed);
                Timer.schedule(shiftTask, 0F, 0.025F);
            }
        }
    }


    public void shiftTile(int direction, boolean robot) {
        Vector2 emptySlot = getEmptySlot();
        switch (direction) {
            case 0:
                if (emptySlot.y != tiles.length - 1) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x][(int) emptySlot.y + 1];
                    tiles[(int) emptySlot.x][(int) emptySlot.y + 1] = 0;
                    if (!robot) {
                        movesPerformed++;
                    }
                    checkBoard();
                }
                break;
            case 1:
                if (emptySlot.y != 0) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x][(int) emptySlot.y - 1];
                    tiles[(int) emptySlot.x][(int) emptySlot.y - 1] = 0;
                    if (!robot) {
                        movesPerformed++;
                    }
                    checkBoard();
                }
                break;
            case 2:
                if (emptySlot.x != 0) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x - 1][(int) emptySlot.y];
                    tiles[(int) emptySlot.x - 1][(int) emptySlot.y] = 0;
                    if (!robot) {
                        movesPerformed++;
                    }
                    checkBoard();
                }
                break;
            case 3:
                if (emptySlot.x != tiles.length - 1) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x + 1][(int) emptySlot.y];
                    tiles[(int) emptySlot.x + 1][(int) emptySlot.y] = 0;
                    if (!robot) {
                        movesPerformed++;
                    }
                    checkBoard();
                }
                break;
        }
    }

    public Vector2 getEmptySlot() {
        for (int i = 0; i != tiles.length; i++) {
            for (int j = 0; j != tiles.length; j++) {
                if (tiles[i][j] == 0) {
                    return new Vector2(i, j);
                }
            }
        }
        return null;
    }

    public boolean checkBoard() {
        for (int i = 0; i != tiles.length; i++) {
            for (int j = 0; j != tiles.length; j++) {
                if (winningTiles[i][j] != tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getTimeElapsed() {
        return String.valueOf(timePlayed / 60) + ":" + ((timePlayed % 60) < 10
                ? "0" + String.valueOf(timePlayed % 60) : String.valueOf(timePlayed % 60));
    }

}
