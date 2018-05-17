package battleship2D.ui;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
//import org.scenicview.ScenicView;

/**
 * Main application
 * @author xaviator
 */
public class BattleShip2D extends Application {

    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    /** Game manager */    
    private static MainFrame mainFrame;
   
    
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    
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
        BattleShip2D.mainFrame = new MainFrame();            
        BattleShip2D.mainFrame.changeState(GameStages.PLACE_SHIPS_ON_PLAYER_BOARD);               
        
        Scene scene = new Scene(BattleShip2D.mainFrame.getRoot(), 900,600);
        primaryStage.setScene(scene);
        /* ScenicView.show(scene); // If ScenicView is used */
        primaryStage.show();        
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        launch(args);
    }
    
}
