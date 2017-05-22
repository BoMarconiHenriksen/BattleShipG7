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
    private boolean shipHit = false;
    boolean validShot = false;
    private Position shot;

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
        boardTest = new int[sizeY][sizeX];
        hitmap = new int[sizeY][sizeX];
        hitmap = fillArray();

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                boardTest[y][x] = 0; // Empty position
            }
        }

        for (int i = fleet.getNumberOfShips() - 1; i >= 0; i--) {
            //Avoid checking the same random position (r,c) more then once when attempting to place a ship
            Ship s = fleet.getShip(i);

            boolean placed = false;
            while (placed == false) {
                boolean vertical = rnd.nextBoolean();
                Position pos;
                int col = rnd.nextInt(sizeX);
                int row = rnd.nextInt(sizeY);
                pos = new Position(col, row);
                if (boardTest[row][col] == 0) {
                    if (canPlace(s, row, col, vertical)) {
                        place(s, row, col, vertical);
                        placed = true;
                        board.placeShip(pos, s, vertical);
                    }

                }
            }

        }
        if (DEBUG == true) { // SHOWS PLACEMENT MAP FOR SHIPS
            for (int r = boardTest.length - 1; r >= 0; r--) {
                System.out.println("");
                for (int c = 0; c < boardTest[0].length; c++) {
                    System.out.print(" " + boardTest[r][c]);
                }
            }
            System.out.println("");
        }
    }

//places the passed ship on the board starting at position (x,y) and going towards the passed direction
    public void place(Ship s, int row, int col, boolean vertical) {
        int size = s.size() - 1;

        if (vertical) {
            for (int i = row; i <= row + size; i++) {
                boardTest[i][col] = 1;
                //1 = placed ship
            }
        } else {
            for (int i = col; i <= col + size; i++) {
                boardTest[row][i] = 1;
            }
        }
    }

    //returns true if the passed ship can be placed on the board starting at position (x,y) and going towards the passed direction
    public boolean canPlace(Ship s, int row, int col, boolean vertical) {
        int size = s.size() - 1;
        boolean thereIsRoom = true;

        if (vertical) {
            // North
            if (row + size > 9 || row - size < 0) {
                thereIsRoom = false;
            } else {
                for (int i = row; i <= row + size && thereIsRoom; i++) {
                    thereIsRoom = thereIsRoom & (boardTest[i][col] == 0);
                }
            }

        } else { // East
            if (col + size > 9 || col - size < 0) {
                thereIsRoom = false;
            } else {
                for (int i = col; i <= col + size && thereIsRoom; i++) {
                    thereIsRoom = thereIsRoom & (boardTest[row][i] == 0);
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
//
////Target mode
//        if (shipHit == true) {
//            //firstHit = shot;
//            int hitX = shot.x;
//            int hitY = shot.y;
//            do {
//                // Runs through N S E W, checks if the fields are not shot at yet
//                for (int i = 0; i < targetModeX.length - 1; i++) {
//                    if (hitmap[hitX + targetModeX[i]][hitY + targetModeY[i]] == 0 || hitmap[hitX + targetModeX[i]][hitY + targetModeY[i]] == 1) {
//                        shot = new Position(hitX + targetModeX[i], hitY + targetModeY[i]);
//                        validShot = true;
//
//                        // needs fixing, if surounded with fields that are not 0 or 1.
//                    }
//                }
//                validShot = true;      // temporary fix for upper standing problem.
//                shipHit = false;       // temporary fix for upper standing problem.
//
//            } while (validShot == false);
//        }

//Random Hunter mode

        do{
            int x = rnd.nextInt(sizeX);
            int y = rnd.nextInt(sizeY);
            //shot = new Position(x, y); // midlertidig random shooter skal fjernes igen.
            // Checker om tallene er rigtige iforhold til Statistisk Parity meteode for skydning

            if ((y % 2 == 0 && x % 2 > 0 && hitmap[x][y] == 0) || (x % 2 == 0 && y % 2 > 0 && hitmap[x][y] == 0)) {
                shot = new Position(x, y);
                validShot = true;
            }
         }while (validShot == false);
        
        
        
        
        
        if (DEBUG == true) {
            //Print hitmap for Debug
            for (int y1 = hitmap.length - 1; y1 >= 0; y1--) {
                System.out.println("");
                for (int x1 = 0; x1 < hitmap.length; x1++) {
                    System.out.print(" " + hitmap[x1][y1]);
                }
            }
            System.out.println("");
        }

        return shot;
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

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {

        if (hit == true) {
            shipHit = true;
            hitmap[shot.x][shot.y] = 2; // HIT
        } else {
            hitmap[shot.x][shot.y] = 5; // Missed shot          
/// skal have lavet et statement der fortsætter med at skyde så længe skibet ikke er sunket, 
// så den ikke vender tilbage til Hunt mode
            shipHit = false;
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endMatch(int won, int lost, int draw
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
