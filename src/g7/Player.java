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
    private ArrayList<Position> hitmapLige;
    private ArrayList<Position> hitmapUlige;
    

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
        hitmapLige = new ArrayList<>();
        hitmapUlige = new ArrayList<>();
        
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
        if(DEBUG == true){ // SHOWS PLACEMENT MAP FOR SHIPS
        for (int r = boardTest.length-1; r>=0; r--) {
            System.out.println("");
            for (int c = 0; c < boardTest[0].length ;c++) {
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
    public void incoming(Position pos
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        
        //Random lige shot
        
        
        
        Position shot;
        int x = rnd.nextInt(sizeX);
        int y = rnd.nextInt(sizeY);
        shot = new Position(x, y);
        
        return shot;
    }
    
    public int[][] fillArray() {
        
        Position pos;
        
        //Udfylder map med 1 og 2 taller
        for (int x = 0; x < sizeX; x++ ) {
            
            for (int y = 0; y < sizeY; y++) {
                if(y % 2 == 0 && x % 2 > 0 || x % 2 == 0 && y % 2 > 0){
                hitmap[x][y] = 0; // nummer for hvert felt
                pos = new Position(x, y);
                hitmapLige.add(pos);
                } else {
                    hitmap[x][y] = 1;
                    pos = new Position(x, y);
                    hitmapUlige.add(pos);
                }
                
            }
        }
        
        
        
        //Print 
        for (int r = 0; r < boardTest.length; r++) {
            System.out.println("");
            for (int c = 0; c < boardTest[0].length; c++) {
                System.out.print(" " + hitmap[r][c]);
            }
        }
        return hitmap;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
