/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jeremy
 */
public interface CellModelInterface extends Remote {
    /**
     * Displays informations
     * @return a brief description
     */
    public String display()throws RemoteException;
    
    /*
     * Getters / Setters
     */
    
    public CellType getCellType()throws RemoteException ;
    
    public  void setCellType (CellType cellType)throws RemoteException ;
    
    public SimpleStringProperty getColorProperty()throws RemoteException;
    
    public Integer getId() throws RemoteException;
    
    public  void setId(Integer id)throws RemoteException;
}
