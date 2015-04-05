package com.furryfaust.patterns;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class GameManager {

    public int[][] tiles;

    public void prepare(int size, int difficulty) {
        tiles = new int[size][size];
        populate();
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

    public void populate() {
        final int SQUARE_LENGTH = tiles.length;
        int pointerX = 0;
        int pointerY = 0;
        int direction = 0;
        for (int i = 0; i != SQUARE_LENGTH * SQUARE_LENGTH; i++) {
            boolean set = false;
            while (!set) {
                if (tiles[pointerX][pointerY] == 0) {
                    tiles[pointerX][pointerY] = i;
                    set = true;
                } else {
                    switch (direction) {
                        case 0:
                            if (pointerX - 1 < 0 || tiles[pointerX - 1][pointerY] != 0) {
                                direction++;
                            } else {
                                pointerX--;
                                break;
                            }
                        case 1:
                            if (pointerX + 1 > SQUARE_LENGTH - 1 || tiles[pointerX + 1][pointerY] != 0) {
                                direction++;
                            } else {
                                pointerX++;
                                break;
                            }
                        case 2:
                            if (pointerY - 1 < 0 || tiles[pointerX][pointerY - 1] != 0) {
                                direction++;
                            } else {
                                pointerY--;
                                break;
                            }
                        case 3:
                            if (pointerY + 1 > SQUARE_LENGTH - 1 || tiles[pointerX][pointerY + 1] != 0) {
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
        Random random = new Random();
        for (int i = 0; i != chaos; i++) {
            shiftTile(random.nextInt(4));
        }
    }


    public boolean shiftTile(int direction) {
        Vector2 emptySlot = getEmptySlot();
        switch (direction) {
            case 0:
                if (emptySlot.y != tiles.length - 1) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x][(int) emptySlot.y + 1];
                    tiles[(int) emptySlot.x][(int) emptySlot.y + 1] = 0;
                    return true;
                }
                break;
            case 1:
                if (emptySlot.y != 0) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x][(int) emptySlot.y - 1];
                    tiles[(int) emptySlot.x][(int) emptySlot.y - 1] = 0;
                    return true;
                }
                break;
            case 2:
                if (emptySlot.x != 0) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x - 1][(int) emptySlot.y];
                    tiles[(int) emptySlot.x - 1][(int) emptySlot.y] = 0;
                    return true;
                }
                break;
            case 3:
                if (emptySlot.x != tiles.length - 1) {
                    tiles[(int) emptySlot.x][(int) emptySlot.y] = tiles[(int) emptySlot.x + 1][(int) emptySlot.y];
                    tiles[(int) emptySlot.x + 1][(int) emptySlot.y] = 0;
                    return true;
                }
                break;
        }
        return false;
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

}
