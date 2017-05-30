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
    private Random rnd = new Random();
    private final boolean DEBUG = false;
    private int sizeX;
    private int sizeY;
    private int[][] boardTest;
    private int[][] parityMap;
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
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        boardTest = new int[sizeX][sizeY];
        parityMap = new int[sizeX][sizeY];
        parityMap = fillParityArray();
        targetModeList = new ArrayList<>();

        emptyPositions();

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
            printBoard();
        }
    }

    public void shipPlacementRightCornerTop(Fleet fleet, Board board) {
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        boardTest = new int[sizeX][sizeY];
        parityMap = new int[sizeX][sizeY];
        parityMap = fillParityArray();
        targetModeList = new ArrayList<>();

        emptyPositions();

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
            printBoard();
        }
    }

    //Venstre halvdel af boardet
    public void shipVenstreSide(Fleet fleet, Board board) {
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        boardTest = new int[sizeX][sizeY];
        parityMap = new int[sizeX][sizeY];
        parityMap = fillParityArray();
        targetModeList = new ArrayList<>();

        emptyPositions();

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
            printBoard();
        }
    }

    public void shipTopHalvdel(Fleet fleet, Board board) {
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        boardTest = new int[sizeX][sizeY];
        parityMap = new int[sizeX][sizeY];
        parityMap = fillParityArray();
        targetModeList = new ArrayList<>();

        emptyPositions();

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
            printBoard();
        }
    }

    public void shipHøjreSide(Fleet fleet, Board board) {
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        boardTest = new int[sizeX][sizeY];
        parityMap = new int[sizeX][sizeY];
        parityMap = fillParityArray();
        targetModeList = new ArrayList<>();

        emptyPositions();

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
            printBoard();
        }
    }

    public void randomPlaceShip(Fleet fleet, Board board) {
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        boardTest = new int[sizeX][sizeY];
        parityMap = new int[sizeX][sizeY];
        parityMap = fillParityArray();
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
            printBoard();
        }
    }

    public void emptyPositions() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                boardTest[x][y] = 0; // Empty position
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
            hitmapStatFill(largestShip);
            printHitmap();
            shot = bestShot();
            totalActiveShips = enemyShipCount(enemyShips);
        }

        if (DEBUG == true) {
            //Print hitmap for Debug
            for (int y1 = parityMap.length - 1; y1 >= 0; y1--) {
                System.out.println("");
                for (int x1 = 0; x1 < parityMap.length; x1++) {
                    System.out.print(" " + parityMap[x1][y1]);
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
            parityMap[shot.x][shot.y] = 2; // HIT BUT NO SINK
            hitmapStat[shot.x][shot.y] = 0; // updates statistic map
            targetModeList.add(shot);
            targetMode = true;
            ship = true;

        } else if (hit == true && ship == true && numberEnemyShipsBefore > enemyShips.getNumberOfShips()) {
            targetModeList.add(shot);
            parityMap[shot.x][shot.y] = 2; // HIT SUNK SHIP
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
            parityMap[shot.x][shot.y] = 5; // MISS BUT NO SINK
            hitmapStat[shot.x][shot.y] = 0; // updates statistic map
            targetMode = true;
        } else {
            parityMap[shot.x][shot.y] = 5; // MISS
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

        for (Position shotToValidate : targetModeList) {
            int x = shotToValidate.x;
            int y = shotToValidate.y;

            //North
            if (y + 1 < sizeY && parityMap[x][y + 1] <= 1) {
                validShot = new Position(x, y + 1);
            }
            //East
            if (x + 1 < sizeX && parityMap[x + 1][y] <= 1) {
                validShot = new Position(x + 1, y);
            }
            //West
            if (x - 1 >= 0 && parityMap[x - 1][y] <= 1) {
                validShot = new Position(x - 1, y);
            }
            //South
            if (y - 1 >= 0 && parityMap[x][y - 1] <= 1) {
                validShot = new Position(x, y - 1);
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
                if (hitmapCanPlace(largestShip, x, y, vertical)) {
                    hitmapCounter(largestShip, x, y, vertical);
                }
            }
        }
        vertical = false;
        for (int y = hitmapStat.length - 1; y >= 0; y--) {
            for (int x = 0; x < hitmapStat.length; x++) {
                if (hitmapCanPlace(largestShip, x, y, vertical)) {
                    hitmapCounter(largestShip, x, y, vertical);
                }
            }
        }
    }

    public boolean hitmapCanPlace(int largestShipSize, int x, int y, boolean vertical) {
        boolean thereIsRoom = true;

        if (vertical) {
            // North
            if (y + largestShipSize > 9 || y - largestShipSize < 0) {
                thereIsRoom = false;
            } else {
                for (int i = y; i <= y + largestShipSize && thereIsRoom; i++) {
                    thereIsRoom = thereIsRoom & (hitmapStat[x][i] > 0);
                }
            }

        } else { // East
            if (x + largestShipSize > 9 || x - largestShipSize < 0) {
                thereIsRoom = false;
            } else {
                for (int i = x; i <= x + largestShipSize && thereIsRoom; i++) {
                    thereIsRoom = thereIsRoom & (hitmapStat[i][y] > 0);
                }
            }
        }
        return thereIsRoom;
    }

    public void hitmapCounter(int largestShipSize, int x, int y, boolean vertical) {

        if (vertical) {
            for (int i = y; i <= y + largestShipSize; i++) {
                hitmapStat[x][i] += 1;
            }
        } else {
            for (int i = x; i <= x + largestShipSize; i++) {
                hitmapStat[i][y] += 1;
            }
        }
    }

    public int[][] fillParityArray() {
        //Udfylder map med 0 og 1 tal
        for (int y = 0; y < sizeY; y++) {
            for (int x = sizeX - 1; x >= 0; x--) {
                if (y % 2 == 0 && x % 2 > 0 || x % 2 == 0 && y % 2 > 0) {
                    parityMap[x][y] = 0; // nummer for hvert felt
                } else {
                    parityMap[x][y] = 1;
                }
            }
        }
        return parityMap;
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
