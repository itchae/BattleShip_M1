package battleship2D.ui.fxmlController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 */
public class FXML_EndGameController implements Initializable {

    @FXML
    private BorderPane borderPane;
    
    @FXML
    private VBox vbox;
    
    @FXML
    private ImageView win;
    
    @FXML
    private ImageView player;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image winner_img = new Image("battleship2D/pictures/player.png");
        Image win_logo = new Image("battleship2D/pictures/wins.png");
        
        Image bckground_pane_img = new Image("battleship2D/pictures/battleship-endgame.png");
        
        this.player.setImage(winner_img);
        this.win.setImage(win_logo);
        
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

        this.borderPane.setBackground(new Background(new BackgroundImage(bckground_pane_img,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            bSize)));
                
        this.vbox.getChildren().add(this.player);
        this.vbox.getChildren().add(this.win);

        this.borderPane.setCenter(this.vbox);
    }    
    
}
