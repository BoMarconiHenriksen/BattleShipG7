/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g7;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stanislav
 */
public class PlayerTest {
    
    public PlayerTest() {
    }
    
    @Before
    public void setUp() {
    }



    @Test
    public void testPlace() {
        System.out.println("place");
        Ship s = null;
        int row = 0;
        int col = 0;
        boolean vertical = false;
        Player instance = new Player();
        instance.place(s, row, col, vertical);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCanPlace() {
        System.out.println("canPlace");
        Ship s = null;
        int row = 0;
        int col = 0;
        boolean vertical = false;
        Player instance = new Player();
        boolean expResult = false;
        boolean result = instance.canPlace(s, row, col, vertical);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
