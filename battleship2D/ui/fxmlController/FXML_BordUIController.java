/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.model.BoardModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Jeremy
 */
public class FXML_BordUIController implements Initializable {
    @FXML
    private GridPane root;
    @FXML
    private ArrayList<FXML_CellUIController> cellsController;
    
    
    /** Related board model */
    protected  BoardModel boardModel;
    /** Board name */
    private  String name;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {// Localisation du fichier FXML.
     
    }

    public void construct(String name, BoardModel boardModel, Boolean isBound){
        this.boardModel = boardModel;
        this.name = name;
    }
    
    public BoardModel getBoardModel(){
        return this.boardModel;
    }
    
}
