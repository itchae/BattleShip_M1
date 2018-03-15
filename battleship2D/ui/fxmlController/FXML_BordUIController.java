/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.model.BoardModel;
import battleship2D.ui.CellUI;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * FXML Controller class
 *
 * @author Jeremy
 */
public abstract class FXML_BordUIController  {
    
    
    /** Related board model */
    protected  BoardModel boardModel;
    /** Board name */
    private  String name;
    
    /** Listeners management */
    protected final PropertyChangeSupport pcsListeners = new PropertyChangeSupport(this);  
    
    /** This class listens for each of its cells */
    protected PropertyChangeListener propertyChangeListener;
    

    public void construct(String name, BoardModel boardModel, Boolean isBound){
        this.boardModel = boardModel;
        this.name = name;
        for (int row = 0; row < BoardModel.BOARD_SIZE; row++) {        
            for (int column = 0; column < BoardModel.BOARD_SIZE; column++) {
                CellUI cellUI = new CellUI(this.boardModel.getCellModel(row, column), isBound);                
                this.add(cellUI, row, column);
                //cellUI.addPropertyChangeListener(this.propertyChangeListener);
            }
        }
    }
    
    protected abstract void add (CellUI c , int row , int column);
    
    public BoardModel getBoardModel(){
        return this.boardModel;
    }
    
}
