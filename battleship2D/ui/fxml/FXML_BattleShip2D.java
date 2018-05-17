/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxml;

import battleship2D.ui.Config;
import battleship2D.ui.GameStages;
import battleship2D.ui.fxmlController.FXML_MainFrameController;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jeremy
 */
public class FXML_BattleShip2D extends Application   {
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    /** Game manager */    
    private static Pane root;
   
    private static boolean playerWin;
    
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    public static void setPlayerWin(boolean bool){ FXML_BattleShip2D.playerWin = bool; }
    
    public static boolean getPlayerWin(){ return FXML_BattleShip2D.playerWin; }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
         //choix du theme
        Stage choixTheme = new Stage();
        choixTheme.initModality(Modality.APPLICATION_MODAL);
        choixTheme.initOwner(primaryStage);
        ObservableList<String> options = 
    FXCollections.observableArrayList(
        "default",
            "etienne"
    );
        ComboBox choix = new ComboBox(options);
        choix.setValue("default");
        choixTheme.setScene(new Scene(choix,100,100));
        choixTheme.showAndWait();
        Config.dossier = (String)choix.getValue();
        
        //chargement du jeu
	try {
	  final URL url = getClass().getResource("FXML_MainMenu.fxml");
	  final FXMLLoader fxmlLoader = new FXMLLoader(url);
	  root = (Pane) fxmlLoader.load();
          if(!Config.dossier.equals("default") ){
              Media media = new Media(getClass().getResource("/battleship2D/pictures/"+Config.dossier+"/song.mp3").toExternalForm());
              MediaPlayer mp = new MediaPlayer(media);
              mp.setVolume(0.25);
              mp.setCycleCount(MediaPlayer.INDEFINITE);
              mp.play();
          }
	} catch (IOException ex) {
	  System.err.println("Erreur au chargement: " + ex);
	}   
        
       if (root != null){
        Scene scene = new Scene(FXML_BattleShip2D.root, 1280,720);
        primaryStage.setScene(scene);
        primaryStage.show();  
       }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        launch(args);
    }
    
}
