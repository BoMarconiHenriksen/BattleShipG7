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
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Stanislav
 */
public class Player implements BattleshipsPlayer {

    private Random rnd = new Random();
    private final boolean DEBUG = false;
    private int sizeX;
    private int sizeY;
    private int[][] boardTest;
    private Position shot;
    boolean targetMode = false;
    private int numberEnemyShipsBefore = 0;
    private ArrayList<Position> targetModeList;
    boolean ship = false;
    private int totalActiveShips = 0;
    private int[][] hitmapStat = null;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
    }

    @Override
    public void startRound(int round) {

    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        boardTest = new int[sizeX][sizeY];
        targetModeList = new ArrayList<>();

        emptyPositions();

        randomPatternShipplacement(fleet, board);
    }

    public void randomPatternShipplacement(Fleet fleet, Board board) {

        int x = rnd.nextInt(6);

        switch (x) {
            case 1:
                sprederVenstreSide(fleet, board);
                break;
            case 2:
                shipVenstreSide(fleet, board);
                break;
            case 3:
                shipTopHalvdel(fleet, board);
                break;
            case 4:
                randomPlaceShip(fleet, board);
                break;
            case 5:
                shipPlacementRightCornerTop(fleet, board);
                break;
            case 6:
                shipHøjreSide(fleet, board);
                break;
            default:
                randomPlaceShip(fleet, board);
        }
    }

    public void sprederVenstreSide(Fleet fleet, Board board) {
        for (int i = fleet.getNumberOfShips() - 1; i >= 0; i--) {
            //Avoid checking the same random position (x,y) more then once when attempting to place a ship
            Ship s = fleet.getShip(i);

            boolean placed = false;
            while (placed == false) {
                boolean vertical = rnd.nextBoolean();
                Position pos;
                int x = rnd.nextInt(5);
                int y = rnd.nextInt(sizeY);
                pos = new Position(x, y);
                if (boardTest[x][y] == 1) {
                    if (canPlace(s.size(), x, y, vertical, boardTest)) {
                        place(s, x, y, vertical);
                        placed = true;
                        board.placeShip(pos, s, vertical);
                    }

                }
            }

        }
        if (DEBUG == true) { // SHOWS PLACEMENT MAP FOR SHIPS
            printBoard();
        }
    }

    public void shipPlacementRightCornerTop(Fleet fleet, Board board) {

        for (int i = fleet.getNumberOfShips() - 1; i >= 0; i--) {
            //Avoid checking the same random position (x,y) more then once when attempting to place a ship
            Ship s = fleet.getShip(i);

            boolean placed = false;
            while (placed == false) {
                boolean vertical = rnd.nextBoolean();
                Position pos;
                int x = rnd.nextInt(5) + 5;
                int y = rnd.nextInt(5) + 5;
                pos = new Position(x, y);
                if (boardTest[x][y] == 1) {
                    if (canPlace(s.size(), x, y, vertical, boardTest)) {
                        place(s, x, y, vertical);
                        placed = true;
                        board.placeShip(pos, s, vertical);
                    }

                }
            }

        }
        if (DEBUG == true) { // SHOWS PLACEMENT MAP FOR SHIPS
            printBoard();
        }
    }

    //Venstre halvdel af boardet
    public void shipVenstreSide(Fleet fleet, Board board) {

        for (int i = fleet.getNumberOfShips() - 1; i >= 0; i--) {
            //Avoid checking the same random position (x,y) more then once when attempting to place a ship
            Ship s = fleet.getShip(i);

            boolean placed = false;
            while (placed == false) {
                boolean vertical = rnd.nextBoolean();
                Position pos;
                int x = rnd.nextInt(5);
                int y = rnd.nextInt(5);
                pos = new Position(x, y);
                if (boardTest[x][y] == 1) {
                    if (canPlace(s.size(), x, y, vertical, boardTest)) {
                        place(s, x, y, vertical);
                        placed = true;
                        board.placeShip(pos, s, vertical);
                    }

                }
            }

        }
        if (DEBUG == true) { // SHOWS PLACEMENT MAP FOR SHIPS
            printBoard();
        }
    }

    public void shipTopHalvdel(Fleet fleet, Board board) {

        for (int i = fleet.getNumberOfShips() - 1; i >= 0; i--) {
            //Avoid checking the same random position (x,y) more then once when attempting to place a ship
            Ship s = fleet.getShip(i);

            boolean placed = false;
            while (placed == false) {
                boolean vertical = rnd.nextBoolean();
                Position pos;
                int x = rnd.nextInt(5);
                int y = rnd.nextInt(5) + 5;
                pos = new Position(x, y);
                if (boardTest[x][y] == 1) {
                    if (canPlace(s.size(), x, y, vertical, boardTest)) {
                        place(s, x, y, vertical);
                        placed = true;
                        board.placeShip(pos, s, vertical);
                    }

                }
            }

        }
        if (DEBUG == true) { // SHOWS PLACEMENT MAP FOR SHIPS
            printBoard();
        }
    }

    public void shipHøjreSide(Fleet fleet, Board board) {

        for (int i = fleet.getNumberOfShips() - 1; i >= 0; i--) {
            //Avoid checking the same random position (x,y) more then once when attempting to place a ship
            Ship s = fleet.getShip(i);

            boolean placed = false;
            while (placed == false) {
                boolean vertical = rnd.nextBoolean();
                Position pos;
                int x = rnd.nextInt(5) + 5;
                int y = rnd.nextInt(5);
                pos = new Position(x, y);
                if (boardTest[x][y] == 1) {
                    if (canPlace(s.size(), x, y, vertical, boardTest)) {
                        place(s, x, y, vertical);
                        placed = true;
                        board.placeShip(pos, s, vertical);
                    }

                }
            }

        }
        if (DEBUG == true) { // SHOWS PLACEMENT MAP FOR SHIPS
            printBoard();
        }
    }

    public void randomPlaceShip(Fleet fleet, Board board) {
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        boardTest = new int[sizeX][sizeY];
        targetModeList = new ArrayList<>();

        emptyPositions();

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
                if (boardTest[x][y] == 1) {
                    if (canPlace(s.size(), x, y, vertical, boardTest)) {
                        place(s, x, y, vertical);
                        placed = true;
                        board.placeShip(pos, s, vertical);
                    }

                }
            }

        }
        if (DEBUG == true) { // SHOWS PLACEMENT MAP FOR SHIPS
            printBoard();
        }
    }

    public void emptyPositions() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                boardTest[x][y] = 1; // Empty position
            }
        }
    }

    public void printBoard() {
        for (int y = boardTest.length - 1; y >= 0; y--) {
            System.out.println("");
            for (int x = 0; x < boardTest[0].length; x++) {
                System.out.print(" " + boardTest[x][y]);
            }
        }
    }

//places the passed ship on the board starting at position (x,y) and going towards the passed direction
    public void place(Ship s, int x, int y, boolean vertical) {
        int size = s.size();

        if (vertical) {
            for (int i = 0; i < size; i++) {
                boardTest[x][y + i] = 0;
                //0 = placed ship
            }
        } else {
            for (int i = 0; i < size; i++) {
                boardTest[x + i][y] = 0;
            }
        }
    }

    //returns true if the passed ship can be placed on the board starting at position (x,y) and going towards the passed direction
    public boolean canPlace(int shipSize, int x, int y, boolean vertical, int[][] board) {
        if (vertical) {
            // North
            if (y - 1 + shipSize > 9 || y < 0) {
                return false;
            } else {
                for (int i = 0; i < shipSize; i++) {
                    if (board[x][y + i] == 0) {
                        return false;
                    }
                }
            }

        } else { // East
            if (x - 1 + shipSize > 9 || x < 0) {
                return false;
            } else {
                for (int i = 0; i < shipSize; i++) {
                    if (board[x + i][y] == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void incoming(Position pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        numberEnemyShipsBefore = enemyShips.getNumberOfShips();
        int largestShip = enemyShips.getShip(numberEnemyShipsBefore - 1).size();

        if (hitmapStat == null) {
            fillHitmap();
            printHitmap();
        }

        //Target mode
        if (targetMode == true) {
            System.out.println("TARGETMODE");
            shot = validShot();
        } else {
            //Random Hunter mode
            System.out.println("HUNTMODE");
            printHitmap();
            hitmapStatFill(largestShip);
            shot = bestShot();
            totalActiveShips = enemyShipCount(enemyShips);
        }

        if (DEBUG == true) {
            //Print hitmap for Debug
            for (int y1 = hitmapStat.length - 1; y1 >= 0; y1--) {
                System.out.println("");
                for (int x1 = 0; x1 < hitmapStat.length; x1++) {
                    System.out.print(" " + hitmapStat[x1][y1]);
                }
            }
            System.out.println("");
            System.out.println("fired shot: " + shot.toString());
        }

        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        int totalSunkShips = totalActiveShips - enemyShipCount(enemyShips);

        if (hit == true && numberEnemyShipsBefore == enemyShips.getNumberOfShips()) {
            hitmapStat[shot.x][shot.y] = 0; // updates statistic map
            targetModeList.add(shot);
            targetMode = true;
            ship = true;

        } else if (hit == true && ship == true && numberEnemyShipsBefore > enemyShips.getNumberOfShips()) {
            targetModeList.add(shot);
            hitmapStat[shot.x][shot.y] = 0; // updates statistic map

            if (totalSunkShips < targetModeList.size()) {
                targetMode = true;
                ship = true;
            } else {
                System.out.println("abe");
                targetModeList.clear();
                targetMode = false;
                ship = false;
            }
        } else if (ship == true && hit == false) {
            hitmapStat[shot.x][shot.y] = 0; // updates statistic map
            targetMode = true;
        } else {
            hitmapStat[shot.x][shot.y] = 0; // updates statistic map
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
        int shot = 0;
        for (Position shotToValidate : targetModeList) {
            int x = shotToValidate.x;
            int y = shotToValidate.y;

            //North
            if (y + 1 < sizeY && hitmapStat[x][y + 1] > shot) {
                validShot = new Position(x, y + 1);
                shot = hitmapStat[x][y + 1];
            }
            //East
            if (x + 1 < sizeX && hitmapStat[x + 1][y] > shot) {
                validShot = new Position(x + 1, y);
                shot = hitmapStat[x + 1][y];
            }
            //West
            if (x - 1 >= 0 && hitmapStat[x - 1][y] > shot) {
                validShot = new Position(x - 1, y);
                shot = hitmapStat[x - 1][y];
            }
            //South
            if (y - 1 >= 0 && hitmapStat[x][y - 1] > shot) {
                validShot = new Position(x, y - 1);
                shot = hitmapStat[x][y - 1];
            }

        }
        return validShot;
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        targetMode = false;
        ship = false;
        shot = null;
        hitmapStat = null;
        targetModeList = null;
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Position bestShot() {
        Position bestShot = null;
        int shotStatistic = 0;
        for (int y = hitmapStat.length - 1; y >= 0; y--) {
            for (int x = 0; x < hitmapStat.length; x++) {
                // Checker om tallene er rigtige i forhold til Statistisk parity meteode for skydning 
                // og finder det sted med bedst sandsynlighed for et skib.
                if ((y % 2 == 0 && x % 2 > 0) && hitmapStat[x][y] > shotStatistic || (x % 2 == 0 && y % 2 > 0) && hitmapStat[x][y] > shotStatistic) {
                    shotStatistic = hitmapStat[x][y];
                    bestShot = new Position(x, y);
                }
            }
        }
        return bestShot;
    }

    public void hitmapStatFill(int largestShip) {
        boolean vertical = true;

        for (int y = hitmapStat.length - 1; y >= 0; y--) {
            for (int x = 0; x < hitmapStat.length; x++) {
                if (canPlace(largestShip, x, y, vertical, hitmapStat)) {
                    hitmapCounter(largestShip, x, y, vertical);
                }
            }
        }
        vertical = false;
        for (int y = hitmapStat.length - 1; y >= 0; y--) {
            for (int x = 0; x < hitmapStat.length; x++) {
                if (canPlace(largestShip, x, y, vertical, hitmapStat)) {
                    hitmapCounter(largestShip, x, y, vertical);
                }
            }
        }
    }

    public void hitmapCounter(int largestShipSize, int x, int y, boolean vertical) {

        if (vertical) {
            for (int i = 0; i < largestShipSize; i++) {
                hitmapStat[x][y + i] += 1;
            }
        } else {
            for (int i = 0; i < largestShipSize; i++) {
                hitmapStat[x + i][y] += 1;
            }
        }
    }

    public void fillHitmap() {
        hitmapStat = new int[sizeX][sizeY];

        for (int y = sizeY - 1; y >= 0; y--) {
            for (int x = 0; x < sizeX; x++) {
                hitmapStat[x][y] = 1;
            }
        }
    }

    public void printHitmap() {
        for (int y = sizeY - 1; y >= 0; y--) {
            System.out.println("");
            for (int x = 0; x < sizeX; x++) {
                System.out.print(hitmapStat[x][y] + " ");
            }
        }
    }
}
