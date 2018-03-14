/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

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
public class FXML_BordUIPlayerController implements Initializable {

    @FXML
    private AnchorPane root;
    
    @FXML
    private GridPane grid;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grid.prefWidthProperty().bind(root.widthProperty());
    }    
    
}
