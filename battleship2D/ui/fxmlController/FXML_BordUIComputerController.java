/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.model.BoardModel;
import battleship2D.model.SkillLevel;
import battleship2D.ui.CellUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Jeremy
 */
public class FXML_BordUIComputerController extends FXML_BordUIController implements Initializable {

    @FXML
    private GridPane root;
    
    
     /** Skill level for the computer */
    private  SkillLevel skillLevel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.prefWidthProperty().bind(root.widthProperty());
    }    
    
    public void construct(String name, BoardModel boardModel, Boolean isBound,
            SkillLevel skillLevel){
        super.construct(name, boardModel, isBound);
        this.skillLevel = skillLevel;
    }
    
       @Override
    protected  void add (CellUI c , int row , int column){
        root.add(c,row,column);
    }
}
