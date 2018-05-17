/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Jeremy
 */
public interface BoardModelInterface extends Remote {

 
    /**
     * Searches for the cells adjacent to another along cardinal directions
     * @param cellModel - cell to deal with
     * @param direction - direction to check
     * @return the adjacent cell if it exists, null otherwise
     */
    public CellModelInterface adjacentCell(CellModelInterface cellModel, Direction direction) throws RemoteException;
    
    /**
     * Searches for the cells adjacent to another along cardinal directions
     * @param cellModel - cell to deal with
     * @param direction - direction to check
     * @param step - the number of cells to "jump" with respect to cellModel:
     * if step = 1, the adjacent cell is the first one directly touching cellModel
     * if step = 2, the adjacent cell is the second one, and so on
     * @return the adjacent cell if it exists, null otherwise
     */
    public CellModelInterface adjacentCell(CellModelInterface cellModel, Direction direction, int step) throws RemoteException;
    

    
    /**
     * @return the position of a cell in the board
     * @param cellModel - the cell to deal with
     */
    public Coord2D cellCoords(CellModelInterface cellModel)throws RemoteException;
    
    /**
     * Displays board's contents
     */
    public void display() throws RemoteException;
    
    /**
     * @return the first cell of a specific type stored in this, null otherwise
     * @param cellType - type to deal with
     */
    public CellModelInterface findFirstCellOfType(CellType cellType)throws RemoteException;
    
    
    /**
     * Checks whether a cell is of a given type
     * @param cellModel - cell to deal with
     * @param cellType - the type to compare with the cell's
     * @return true if cellModel's type is the same as cellType
     */
    public Boolean isCellOfType(CellModelInterface cellModel,
                                CellType cellType)throws RemoteException ;

    /**
     * @return true if a specific kind of cell is currenlty located on the board
     * @param cellType - type of the cell to test
     */
    public Boolean isCellTypeInside(CellType cellType)throws RemoteException ;
    
    /**
     * @return a randomly selected cell
     * @param cellType - type of the cell to search
     * @param isCellType - determines whether the type of the cell to search for is equal to cellType or not
     */
    public CellModelInterface randomCell(CellType cellType, Boolean isCellType)throws RemoteException;
    
    /**
     * Replaces a set of cell types with another one
     * @param oldCellType - type to replace
     * @param newCellType - new type to set 
     */
    public void replaceAll(CellType oldCellType,
                            CellType newCellType)throws RemoteException;
    
    /**
     * Resets board's contents to default value
     * @param cellType - default value
     */
    public void reset(CellType cellType)throws RemoteException ;
    
     
    /*
     * Getters / Setters
     */    
       
    public CellModelInterface getCellModel(int row, int column)throws RemoteException;
    
    public CellType getDefaultCellType()throws RemoteException;
    
    public Fleet getFleet() throws RemoteException;


    
   
    

}
