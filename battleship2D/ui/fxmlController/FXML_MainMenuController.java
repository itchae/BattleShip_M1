/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.ui.Config;
import battleship2D.ui.GameStages;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author etienne
 */
public class FXML_MainMenuController implements Initializable{

    @FXML
    private AnchorPane root;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
            root.setStyle(" -fx-background-image:url('/battleship2D/pictures/"+Config.dossier+"/background_menu.jpg'); -fx-background-size: contain;");
           
    }
    
    public void solo(){
	try {
	    final URL url = getClass().getResource("/battleship2D/ui/fxml/FXML_MainFrame.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(url);
	    AnchorPane rootSolo = (AnchorPane) fxmlLoader.load();
	    this.root.getChildren().clear();
	    this.root.getChildren().add(rootSolo);
	    
	    rootSolo.prefWidthProperty().bind(this.root.widthProperty()); 
	    rootSolo.prefHeightProperty().bind(this.root.heightProperty());
	    ((FXML_MainFrameController)fxmlLoader.getController()).changeState(GameStages.PLACE_SHIPS_ON_PLAYER_BOARD); 

	} catch (IOException ex) {
	    Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public void multi(){
	try {
	    final URL url = getClass().getResource("/battleship2D/ui/fxml/FXML_SelectRoom.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(url);
	    AnchorPane rootSolo = (AnchorPane) fxmlLoader.load();
	    this.root.getChildren().clear();
	    this.root.getChildren().add(rootSolo);
	    
	    rootSolo.prefWidthProperty().bind(this.root.widthProperty()); 
	    rootSolo.prefHeightProperty().bind(this.root.heightProperty());

	} catch (IOException ex) {
	    Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
}
