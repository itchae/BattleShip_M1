/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.ui.fxml.FXML_BattleShip2D;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author etienne
 */
public class FXML_TestController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
    }
    
    @FXML
    private void win(){
	System.out.println("toto");
    }
    
    @FXML
    private void lose(){
	System.out.println("titi");
    }
    
}
