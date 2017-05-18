/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g7;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

/**
 *
 * @author Stanislav
 */
public class G7 implements PlayerFactory<BattleshipsPlayer>{

    public G7()
    {}
    
    @Override
    public BattleshipsPlayer getNewInstance() {
        return new Player();
    }

    @Override
    public String getID() {
        return "G7";
    }

    @Override
    public String getName() {
        return "Bo & Stanislav";
    }

    @Override
    public String[] getAuthors() {
        String[] authors = {"Stanislav Novitski", "Bo Henriksen"};
        return authors;
    }
    
}
