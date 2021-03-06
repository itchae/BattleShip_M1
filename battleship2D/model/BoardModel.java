package battleship2D.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

/**
 * Global board for manipulating cells
 * @author xaviator
 */
public class BoardModel extends UnicastRemoteObject implements BoardModelInterface,Serializable  {
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    /** Game board size */
    public static int BOARD_SIZE = 10;
    
    /** 2D board */
    protected final CellModel[][] board;
    
    /** Cell identifier */
    private Integer cellId;

    /** Default cell type for board cells */
    private final CellType defaultCellType;
    
    /** Fleet of ships to place on the board */
    protected final Fleet fleet;
        
    
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    
    /**
     * Constructor
     * @param cellType - default cell type for every board cell
     */
    public BoardModel(CellType cellType) throws RemoteException {
        this.cellId = 0;
        this.fleet = new Fleet();
        this.defaultCellType = cellType;
        
        this.board = new CellModel[BOARD_SIZE][BOARD_SIZE];
        init(this.defaultCellType);        
    }
 
    /**
     * Searches for the cells adjacent to another along cardinal directions
     * @param cellModel - cell to deal with
     * @param direction - direction to check
     * @return the adjacent cell if it exists, null otherwise
     */
    @Override
    public CellModelInterface adjacentCell(CellModelInterface cellModel, Direction direction) throws RemoteException {
        /* Which cellModel's location? */
        Coord2D coord2D = cellCoords(cellModel);
        int row = coord2D.getRow();
        int column = coord2D.getColumn();
        
        /* Internal array data are stored in up-bottom and left-right order:
            rows correspond here to left-right direction
            columns correspond here to up-bottom directions */
        switch (direction) {
            case NORTH:
                column = column - 1;
                break;  
            case WEST: 
                row = row - 1;
                break;  
            case SOUTH:
                column = column + 1;
                break;  
            case EAST: 
                row = row + 1;
                break;     
            default:
                break;
        }
        if (areCoordinatesInside(row, column)) {
            return this.board[row][column];
        }
        else {
            return null;
        }
    }
    
    /**
     * Searches for the cells adjacent to another along cardinal directions
     * @param cellModel - cell to deal with
     * @param direction - direction to check
     * @param step - the number of cells to "jump" with respect to cellModel:
     * if step = 1, the adjacent cell is the first one directly touching cellModel
     * if step = 2, the adjacent cell is the second one, and so on
     * @return the adjacent cell if it exists, null otherwise
     */
    @Override
    public CellModelInterface adjacentCell(CellModelInterface cellModel, Direction direction, int step) throws RemoteException {
        /* Which cellModel's location? */
        Coord2D coord2D = cellCoords(cellModel);
        int row = coord2D.getRow();
        int column = coord2D.getColumn();
        
        /* Internal array data are stored in up-bottom and left-right order:
            rows correspond here to left-right direction
            columns correspond here to up-bottom directions */
        switch (direction) {
            case NORTH:
                column = column - step;
                break;  
            case WEST: 
                row = row - step;
                break;  
            case SOUTH:
                column = column + step;
                break;  
            case EAST: 
                row = row + step;
                break;     
            default:
                break;
        }
        if (areCoordinatesInside(row, column)) {
            return this.board[row][column];
        }
        else {
            return null;
        }
    }
    
    /**
     * @return true if all elements of a cell set are of the same kind
     * @param cellModels - set of cells
     * @param cellType - type to check
     */
    public static boolean areAllCellsOfType(ArrayList<CellModelInterface>cellModels,
            CellType cellType)  throws RemoteException{
        boolean result = true;
        for (CellModelInterface cellModel : cellModels) {
            if (cellModel != null && cellModel.getCellType() != cellType) {
                result = false;
                break;
            }
        }
        return result;
    }
    
    /**
     * @return the position of a cell in the board
     * @param cellModel - the cell to deal with
     */
    @Override
    public Coord2D cellCoords(CellModelInterface cellModel) throws RemoteException {
        int row = cellModel.getId() / BOARD_SIZE;
        int column = cellModel.getId() % BOARD_SIZE;
        
        return new Coord2D(row, column);
    }
    
    /**
     * Displays board's contents
     */
    @Override
    public void display()  throws RemoteException{
        for (int row = 0; row < BOARD_SIZE; row++) {          
            for (int column = 0; column < BOARD_SIZE; column++) {
                System.out.print(this.board[row][column].display() + ", ");                
            }
            System.out.println();
        }
    }
    
    /**
     * @return the first cell of a specific type stored in this, null otherwise
     * @param cellType - type to deal with
     */
    @Override
    public CellModelInterface findFirstCellOfType(CellType cellType)  throws RemoteException{
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (isCellOfType(this.board[row][column], cellType)) {
                    return this.board[row][column];
                }
            }     
        }
        return null;
    }
    
    /**
     * Determine whether there is at least one element of a cell set with a given type
     * @param cellModels - set of cells
     * @param cellType - type to check
     * @return the first cell found of type cellType, null if no cell has been found
     */
    public static CellModelInterface findFirstCellOfType(ArrayList<CellModelInterface>cellModels,
            CellType cellType) throws RemoteException {
        CellModelInterface result = null;
        for (CellModelInterface cellModel : cellModels) {
            if (cellModel != null && cellModel.getCellType() == cellType) {
                result = cellModel;
                break;
            }
        }
        return result;
    }
    
    /**
     * Checks whether a cell is of a given type
     * @param cellModel - cell to deal with
     * @param cellType - the type to compare with the cell's
     * @return true if cellModel's type is the same as cellType
     */
    @Override
    public Boolean isCellOfType(CellModelInterface cellModel,
                                CellType cellType) throws RemoteException {        
            return cellModel.getCellType() == cellType;
    }

    /**
     * @return true if a specific kind of cell is currenlty located on the board
     * @param cellType - type of the cell to test
     */
    @Override
    public Boolean isCellTypeInside(CellType cellType) throws RemoteException {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (this.board[row][column].getCellType() == cellType) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * @return a randomly selected cell
     * @param cellType - type of the cell to search
     * @param isCellType - determines whether the type of the cell to search for is equal to cellType or not
     */
    @Override
    public CellModelInterface randomCell(CellType cellType, Boolean isCellType) throws RemoteException {
        Random generator = new Random();        
        Integer randomId;
        CellModelInterface cellModel;
        
        while(true){            
            /* this.cellId is the cell global counter */   
            randomId = generator.nextInt(this.cellId);
            
            cellModel = findCellWithId(randomId);                     
            if (cellModel != null) {
                if (isCellType && isCellOfType(cellModel, cellType)) {
                    return cellModel;                    
                }
                else if ((!isCellType) && (!isCellOfType(cellModel, cellType))) {
                    return cellModel;
                }
            }
        }
    }
    
    /**
     * Replaces a set of cell types with another one
     * @param oldCellType - type to replace
     * @param newCellType - new type to set 
     */
    @Override
    public void replaceAll(CellType oldCellType,
                            CellType newCellType) throws RemoteException {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (this.board[row][column].getCellType() == oldCellType) {
                    this.board[row][column].setCellType(newCellType);                
                }
            }
        }
    }
    
    /**
     * Resets board's contents to default value
     * @param cellType - default value
     */
    @Override
    public void reset(CellType cellType)  throws RemoteException{
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                this.board[row][column].setCellType(cellType);                
            }
        }
    }
    
     
    /*
     * Getters / Setters
     */    
     @Override  
    public CellModelInterface getCellModel(int row, int column) throws RemoteException {
        return this.board[row][column];
    }
    @Override
    public CellType getDefaultCellType() throws RemoteException {
        return this.defaultCellType;
    }
    @Override
    public Fleet getFleet() throws RemoteException {
        return this.fleet;
    }


    /*=========================================================================*/
    /* Private methods                                                         */       
    /*=========================================================================*/
    
    /**
     * Checks whether a pair of coordinates is located inside the board
     * @param row - row coordinate
     * @param column - column coordinate
     * @return true if coordinates are inside, null otherwise
     * @see adjacentCell()
     */
    private Boolean areCoordinatesInside(int row, int column) {
        return (row >= 0 && row < BOARD_SIZE 
                && column >= 0 && column < BOARD_SIZE);
    }
    
    /**
     * Searches for a cell, knowing its id
     * @param cellId - identifier to deal with
     * @return the cell matching cellId, null otherwise
     * @see isCellOfType()
     */
    private CellModelInterface findCellWithId(Integer cellId)  throws RemoteException{
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (this.board[row][column].getId() == cellId) {
                    return this.board[row][column];
                }
            }     
        }
        return null;
    }    
    
    /**
     * Initializes board's contents     
     * @param cellType - default cell type for every cell 
     * @see BoardModel()
     */
    private void init(CellType cellType) throws RemoteException {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {                
                this.board[row][column] = new CellModel(cellType, this.cellId++);                   
            }
        }
    } 
}
