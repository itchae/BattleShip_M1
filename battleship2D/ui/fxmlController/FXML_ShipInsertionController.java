/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.model.Fleet;
import battleship2D.model.Ship;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

/**
 * FXML Controller class
 *
 * @author Jeremy
 */
public class FXML_ShipInsertionController implements Initializable {

    @FXML
    private ChoiceBox choiceBox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void construct(Fleet fleet){
         for (Ship ship : fleet.getShips()) {
            this.choiceBox.getItems().add(ship.getDescription());            
        }
    }
}
