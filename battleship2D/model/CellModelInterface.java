/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jeremy
 */
public interface CellModelInterface {
    /**
     * Displays informations
     * @return a brief description
     */
    public String display();
    
    /*
     * Getters / Setters
     */
    
    public CellType getCellType() ;
    
    public  void setCellType (CellType cellType) ;
    
    public SimpleStringProperty getColorProperty();
    
    public Integer getId() ;
    
    public  void setId(Integer id);
}
