/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.model.BoardModel;
import battleship2D.model.CellType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Jeremy
 */
public class FXML_MainFrameController implements Initializable {

    @FXML
    private AnchorPane root;
    
    @FXML
    private HBox center;
    
    @FXML
    private VBox bottom;
    @FXML
    private FXML_ShipInsertionController shipInsertionController;
    
    @FXML
    private AnchorPane player;
    @FXML
    private FXML_BordUIPlayerController playerController;
    
    @FXML
    private AnchorPane computer;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        center.prefWidthProperty().bind(root.widthProperty());
        bottom.prefWidthProperty().bind(root.widthProperty());
        bottom.maxWidthProperty().bind(root.widthProperty());
        player.prefWidthProperty().bind(root.widthProperty().divide(2));
        computer.prefWidthProperty().bind(root.widthProperty().divide(2));
        
        //construct
        playerController.construct("Player", new BoardModel(CellType.OCEAN), true);
        this.shipInsertionController.construct(this.playerController.getBoardModel().getFleet());
    } 
    
    
    
}
