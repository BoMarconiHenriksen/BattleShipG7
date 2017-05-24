/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g7;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Stanislav
 */
public class Player implements BattleshipsPlayer {

    private final boolean DEBUG = true;
    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;
    private int[][] boardTest;
    private int[][] hitmap;
    private final int[] targetModeX = {0, 0, 1, -1};
    private final int[] targetModeY = {1, -1, 0, 0};
    private Position shot;
    boolean targetMode = false;
    private int numberEnemyShipsBefore;
    private ArrayList<Position> targetModeList;
    boolean ship = false;
    private int totalActiveShips = 0;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
    }

    @Override
    public void startRound(int round) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        boardTest = new int[sizeX][sizeY];
        hitmap = new int[sizeX][sizeY];
        hitmap = fillArray();
        targetModeList = new ArrayList<>();

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                boardTest[x][y] = 0; // Empty position
            }
        }

        for (int i = fleet.getNumberOfShips() - 1; i >= 0; i--) {
            //Avoid checking the same random position (x,y) more then once when attempting to place a ship
            Ship s = fleet.getShip(i);

            boolean placed = false;
            while (placed == false) {
                boolean vertical = rnd.nextBoolean();
                Position pos;
                int x = rnd.nextInt(sizeX);
                int y = rnd.nextInt(sizeY);
                pos = new Position(x, y);
                if (boardTest[x][y] == 0) {
                    if (canPlace(s, x, y, vertical)) {
                        place(s, x, y, vertical);
                        placed = true;
                        board.placeShip(pos, s, vertical);
                    }

                }
            }

        }
        if (DEBUG == true) { // SHOWS PLACEMENT MAP FOR SHIPS
            for (int y = boardTest.length - 1; y >= 0; y--) {
                System.out.println("");
                for (int x = 0; x < boardTest[0].length; x++) {
                    System.out.print(" " + boardTest[x][y]);
                }
            }
            System.out.println("");
        }
    }

//places the passed ship on the board starting at position (x,y) and going towards the passed direction
    public void place(Ship s, int x, int y, boolean vertical) {
        int size = s.size() - 1;

        if (vertical) {
            for (int i = y; i <= y + size; i++) {
                boardTest[x][i] = 1;
                //1 = placed ship
            }
        } else {
            for (int i = x; i <= x + size; i++) {
                boardTest[i][y] = 1;
            }
        }
    }

    //returns true if the passed ship can be placed on the board starting at position (x,y) and going towards the passed direction
    public boolean canPlace(Ship s, int x, int y, boolean vertical) {
        int size = s.size() - 1;
        boolean thereIsRoom = true;

        if (vertical) {
            // North
            if (y + size > 9 || y - size < 0) {
                thereIsRoom = false;
            } else {
                for (int i = y; i <= y + size && thereIsRoom; i++) {
                    thereIsRoom = thereIsRoom & (boardTest[x][i] == 0);
                }
            }

        } else { // East
            if (x + size > 9 || x - size < 0) {
                thereIsRoom = false;
            } else {
                for (int i = x; i <= x + size && thereIsRoom; i++) {
                    thereIsRoom = thereIsRoom & (boardTest[i][y] == 0);
                }
            }
        }
        return thereIsRoom;
    }

    @Override
    public void incoming(Position pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        boolean validShot = false;
        numberEnemyShipsBefore = enemyShips.getNumberOfShips();
        totalActiveShips = enemyShipCount(enemyShips);
        
        ////Target mode
        if (targetMode == true) {
            System.out.println("TARGETMODE");
            shot = validShot();
        }
        
        //Random Hunter mode
        if (targetMode == false) {
            System.out.println("HUNTMODE");
            do {

                int x = rnd.nextInt(sizeX);
                int y = rnd.nextInt(sizeY);
///////////                shot = new Position(x, y); // midlertidig random shooter skal fjernes igen.
                // Checker om tallene er rigtige iforhold til Statistisk Parity meteode for skydning

                if ((y % 2 == 0 && x % 2 > 0 && hitmap[x][y] == 0) || (x % 2 == 0 && y % 2 > 0 && hitmap[x][y] == 0)) {
                    shot = new Position(x, y);
                    validShot = true;
                }

            } while (validShot == false);
        }

        if (DEBUG
                == true) {
            //Print hitmap for Debug
            for (int y1 = hitmap.length - 1; y1 >= 0; y1--) {
                System.out.println("");
                for (int x1 = 0; x1 < hitmap.length; x1++) {
                    System.out.print(" " + hitmap[x1][y1]);
                }
            }
            System.out.println("");
            System.out.println("fired shot: " + shot.toString());
        }

        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips
    ) {
        int remainingEnemyShipsCount = 0;
        if (hit == true && numberEnemyShipsBefore == enemyShips.getNumberOfShips()) {
            hitmap[shot.x][shot.y] = 2; // HIT BUT NO SINK
            targetModeList.add(shot);
            targetMode = true;
            ship = true;

        } else if (ship == true && hit == false) {
            hitmap[shot.x][shot.y] = 5; // MISS BUT NO SINK
            shot = targetModeList.get(targetModeList.size() - 1);
            targetMode = true;

        } else if (hit == true && numberEnemyShipsBefore > enemyShips.getNumberOfShips()) {
            targetModeList.add(shot);
            hitmap[shot.x][shot.y] = 2; // HIT SUNK SHIP
            remainingEnemyShipsCount = enemyShipCount(enemyShips);
            totalActiveShips -= remainingEnemyShipsCount;

            if (totalActiveShips < targetModeList.size()) {
                targetMode = true;
            } else {
                System.out.println("abe");
                totalActiveShips = enemyShipCount(enemyShips);
                targetModeList.clear();
                targetMode = false;
                ship = false;
            }
        } else {
            hitmap[shot.x][shot.y] = 5; // MISS
            targetMode = false;
        }
    }

    public int enemyShipCount(Fleet enemyShips) {
        int totalCount = 0;
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            totalCount += enemyShips.getShip(i).size();
        }

        return totalCount;
    }

    public Position validShot() {
        Position validShot = null;

        for (Position shot : targetModeList) {
            int x = shot.x;
            int y = shot.y;

            //North
            if (y + 1 < sizeY && hitmap[x][y + 1] <= 1) {
                validShot = new Position(x, y + 1);
            }
            //East
            if (x + 1 < sizeX && hitmap[x + 1][y] <= 1) {
                validShot = new Position(x + 1, y);
            }
            //West
            if (x - 1 >= 0 && hitmap[x - 1][y] <= 1) {
                validShot = new Position(x - 1, y);
            }
            //South
            if (y - 1 >=0 && hitmap[x][y - 1] <= 1) {
                validShot = new Position(x, y - 1);
            }

        }
        return validShot;
    }

    @Override
    public void endRound(int round, int points, int enemyPoints
    ) {
        targetMode = false;
        ship = false;
        shot = null;
    }

    @Override
    public void endMatch(int won, int lost, int draw
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int[][] fillArray() {
        //Udfylder map med 0 og 1 tal
        for (int y = 0; y < sizeY; y++) {
            for (int x = sizeX - 1; x >= 0; x--) {
                if (y % 2 == 0 && x % 2 > 0 || x % 2 == 0 && y % 2 > 0) {
                    hitmap[x][y] = 0; // nummer for hvert felt
                } else {
                    hitmap[x][y] = 1;
                }
            }
        }

        //Print
        for (int y = 0; y < hitmap.length - 1; y++) {
            System.out.println("");
            for (int x = 0; x < hitmap[0].length; x++) {
                System.out.print(" " + hitmap[x][y]);
            }
        }
        System.out.println("");
        return hitmap;
    }

}
