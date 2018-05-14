package battleship2D.ui.fxmlController;

import battleship2D.ui.fxml.FXML_BattleShip2D;
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
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 */
public class FXML_EndGameController implements Initializable {
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    /** Animation images */    
    //private final ImageView winnerImage, winsImage;

    /** Lapse time between two frame iterations */
    private  int duration;
    
    /** Dynamic scaling ration to make image appear */
    private  SimpleDoubleProperty scaleRatioProparty;
    
    /** Animation player */
    private  Timeline timeline;
    
    
    
    @FXML
    private VBox winnerDisplay;
    
    @FXML
    private ImageView win;
    
    @FXML
    private ImageView winnerName;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (FXML_BattleShip2D.getPlayerWin()) {
            this.winnerName.setImage(new Image("/battleship2D/pictures/player.png"));
        }
        else {
            this.winnerName.setImage(new Image("/battleship2D/pictures/computer.png"));
        }
        
        initWinner();    
        
        initWins();
    }

     /**
     * Constructor
     */
    public FXML_EndGameController() {    
        super();
        this.duration = 80;
        this.scaleRatioProparty = new SimpleDoubleProperty();
        
        this.timeline = new Timeline();
        initTimeline();
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
	if (FXML_BattleShip2D.getPlayerWin()) {
	    Image img = new Image("/battleship2D/pictures/player.png");
            this.winnerName.setImage(img);
        }
        else {
            this.winnerName.setImage(new Image("/battleship2D/pictures/computer.png"));
        }
        this.winnerName.setFitHeight(0);
        this.winnerName.setFitWidth(0);
        /*this.winnerImage.fitHeightProperty().bind(this.prefHeightProperty().divide(3));
        this.winnerImage.fitWidthProperty().bind(this.prefWidthProperty().divide(1.5));
        this.winnerImage.setFocusTraversable(false); 
        this.winnerImage.scaleXProperty().bind(this.scaleRatioProparty);
        this.winnerImage.scaleYProperty().bind(this.scaleRatioProparty);
        getChildren().add(this.winnerImage);*/
    }
    
    /** 
     * Initializes winsImage's properties 
     * @see EndGame()
     */
    private void initWins() {
        this.win.setFitHeight(0);
        this.win.setFitWidth(0);
        /*this.winsImage.fitHeightProperty().bind(this.prefHeightProperty().divide(3));
        this.winsImage.fitWidthProperty().bind(this.prefWidthProperty().divide(2));
        this.winsImage.setFocusTraversable(false);     
        this.winsImage.scaleXProperty().bind(this.scaleRatioProparty);
        this.winsImage.scaleYProperty().bind(this.scaleRatioProparty);
        getChildren().add(this.winsImage);*/
    }
    
    /**
     * The animation moves images at each step
     * @see initTimeline()
     */
    private void runAnimation() {                
        this.winnerName.setTranslateX(this.winnerName.getFitWidth()/2 - this.winnerName.getFitWidth()/2);
        this.winnerName.setTranslateY(this.winnerName.getFitHeight()*0.3 - this.winnerName.getFitHeight()/2);
        
        
        this.win.setTranslateX(this.win.getFitWidth()/2 - this.win.getFitWidth()/2);
        this.win.setTranslateY(this.win.getFitHeight()*0.75 - this.win.getFitHeight()/2);
        
        if (this.scaleRatioProparty.getValue() <= 1.0) {
            this.scaleRatioProparty.setValue(this.scaleRatioProparty.getValue() + 0.1);
        }
    }
    
}
