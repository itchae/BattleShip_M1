package battleship2D.ui;

import battleship2D.model.SkillLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Game configuration properties
 * @author xskapin
 */
public class Config {
    /*=========================================================================*/
    /* Members                                                                 */
    /*=========================================================================*/

    /** Default computer skill level */
    public static final SkillLevel level = SkillLevel.EXPERT;
    
     public static String dossier = "default";
     
     public static void configStyle(Stage primaryStage){
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
     }
    
    /**
     * Constructor
     */
    public Config() {
        
    }
}
