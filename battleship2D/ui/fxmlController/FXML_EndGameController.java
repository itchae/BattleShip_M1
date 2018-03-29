package battleship2D.ui.fxmlController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 */
public class FXML_EndGameController extends Region implements Initializable {
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    /** Animation images */    
    private final ImageView winnerImage, winsImage;

    /** Lapse time between two frame iterations */
    private final int duration;
    
    /** Dynamic scaling ration to make image appear */
    private final SimpleDoubleProperty scaleRatioProparty;
    
    /** Animation player */
    private final Timeline timeline;
    
    
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
        //Get the images to put on the final screen
        Image winner_img = new Image("battleship2D/pictures/player.png");
        Image win_logo = new Image("battleship2D/pictures/wins.png");
        Image bckground_pane_img = new Image("battleship2D/pictures/battleship-endgame.png");
        
        this.player.setImage(winner_img);
        this.win.setImage(win_logo);
        
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

        //Set the battleship-endgame.png picture as the background of the window
        this.borderPane.setBackground(new Background(new BackgroundImage(bckground_pane_img,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            bSize)));

        this.borderPane.setCenter(this.vbox);
    }

     /**
     * Constructor
     * @param playerWins - Determines whether the winner image to display represents
     * the player (true) or the computer (false)
     */
    public FXML_EndGameController(boolean playerWins) {    
        super();
        this.duration = 80;
        this.scaleRatioProparty = new SimpleDoubleProperty();
        this.winnerImage = new ImageView();
                
        if (playerWins) {
            this.player.setImage(new Image("battleship2D/pictures/player.png"));
        }
        else {
            this.player.setImage(new Image("battleship2D/pictures/computer.png"));
        }
        initWinner();    
        
        this.winsImage = new ImageView(getClass().getResource("/battleship2D/pictures/wins.png").toExternalForm());                
        initWins();
        
        this.timeline = new Timeline();
        initTimeline();
        
        //this.setVisible(false);
    }
    
     /**
     * Plays the animation
     */    
    public void start() {
        this.scaleRatioProparty.setValue(0.0);
        //this.setVisible(true);
        this.timeline.play(); 
    }
    
    /**
     * Stops the animation
     */
    public void stop() { 
        this.timeline.stop(); 
        //this.setVisible(false);
    }
    
        /** 
     * Initializes timeline properties 
     * @see EndGame
     */
    private void initTimeline() {        
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        
        KeyFrame keyFrame = new KeyFrame(new Duration(this.duration), (ActionEvent event) -> {            
                runAnimation();
        });
        timeline.getKeyFrames().add(keyFrame);
    }
    
    /** 
     * Initializes winnerImage's properties 
     * @see EndGame()
     */
    private void initWinner() {        
        this.winnerImage.fitHeightProperty().bind(this.prefHeightProperty().divide(3));
        this.winnerImage.fitWidthProperty().bind(this.prefWidthProperty().divide(1.5));
        this.winnerImage.setFocusTraversable(false); 
        this.winnerImage.scaleXProperty().bind(this.scaleRatioProparty);
        this.winnerImage.scaleYProperty().bind(this.scaleRatioProparty);
        getChildren().add(this.winnerImage);
    }
    
    /** 
     * Initializes winsImage's properties 
     * @see EndGame()
     */
    private void initWins() {        
        this.winsImage.fitHeightProperty().bind(this.prefHeightProperty().divide(3));
        this.winsImage.fitWidthProperty().bind(this.prefWidthProperty().divide(2));
        this.winsImage.setFocusTraversable(false);     
        this.winsImage.scaleXProperty().bind(this.scaleRatioProparty);
        this.winsImage.scaleYProperty().bind(this.scaleRatioProparty);
        getChildren().add(this.winsImage);
    }
    
    /**
     * The animation moves images at each step
     * @see initTimeline()
     */
    private void runAnimation() {                
        this.winnerImage.setTranslateX(this.getWidth()/2 - this.winnerImage.getFitWidth()/2);
        this.winnerImage.setTranslateY(this.getHeight()*0.3 - this.winnerImage.getFitHeight()/2);
        
        
        this.winsImage.setTranslateX(this.getWidth()/2 - this.winsImage.getFitWidth()/2);
        this.winsImage.setTranslateY(this.getHeight()*0.75 - this.winsImage.getFitHeight()/2);
        
        if (this.scaleRatioProparty.getValue() <= 1.0) {
            this.scaleRatioProparty.setValue(this.scaleRatioProparty.getValue() + 0.1);
        }
    }
    
}
