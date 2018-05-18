package battleship2D.ui;

import battleship2D.model.SkillLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
        
        Button dialogCloseButton = new Button("comfirmer");
        dialogCloseButton.setAlignment(Pos.CENTER);
        dialogCloseButton.prefWidthProperty().bind(choixTheme.widthProperty());
        dialogCloseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent t) {
               choixTheme.close();
           }
       });
        BorderPane layout = new BorderPane();
        layout.setCenter(choix);
        layout.setBottom(dialogCloseButton);
        
        choixTheme.setScene(new Scene(layout,100,100));
        choixTheme.showAndWait();
        Config.dossier = (String)choix.getValue();
     }
    
    /**
     * Constructor
     */
    public Config() {
        
    }
}
