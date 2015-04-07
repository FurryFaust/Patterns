package com.furryfaust.patterns;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;

public class GameManager {

    public int[][] tiles;
    public int[][] winningTiles;
    public Tasks.shiftTask shiftTask;

    public void prepare(int size, int difficulty) {
        tiles = new int[size][size];
        populate(tiles);
        winningTiles = new int[size][size];
        populate(winningTiles);
        scramble(difficulty);
        printTiles();
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

    public void printTiles() {
        for (int i = 0; i != tiles.length; i++) {
            for (int j = 0; j != tiles.length; j++) {
                System.out.print(tiles[j][i] + (tiles[j][i] >= 10 ? " " : "  "));
            }
            System.out.println("");
        }
    }

    public void scramble(int chaos) {
        if (chaos != 0) {
            Random random = new Random();
            for (int i = 0; i != chaos; i++) {
                shiftTile(random.nextInt(4));
            }
        }
    }

    public void prepareShift(int direction, int length, float speed) {
        if (shiftTask == null || !shiftTask.isScheduled()) {
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


    public void shiftTile(int direction) {
        Vector2 emptySlot = getEmptySlot();
        switch (direction) {
            case 0:
                if (emptySlot.y != tiles.length - 1) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x][(int) emptySlot.y + 1];
                    tiles[(int) emptySlot.x][(int) emptySlot.y + 1] = 0;
                    checkBoard();
                }
                break;
            case 1:
                if (emptySlot.y != 0) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x][(int) emptySlot.y - 1];
                    tiles[(int) emptySlot.x][(int) emptySlot.y - 1] = 0;
                    checkBoard();
                }
                break;
            case 2:
                if (emptySlot.x != 0) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x - 1][(int) emptySlot.y];
                    tiles[(int) emptySlot.x - 1][(int) emptySlot.y] = 0;
                    checkBoard();
                }
                break;
            case 3:
                if (emptySlot.x != tiles.length - 1) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x + 1][(int) emptySlot.y];
                    tiles[(int) emptySlot.x + 1][(int) emptySlot.y] = 0;
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
                System.out.println(winningTiles[i][j] + " " + tiles[i][j]);
                if (winningTiles[i][j] != tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

}
