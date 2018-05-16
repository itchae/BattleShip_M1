/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;


/**
 *
 * @author lucille
 */
public class FXML_MissileController implements Initializable {

  
    
    private ParallelTransition parallelTransition;
    
    
    private final PropertyChangeSupport pcsListeners = new PropertyChangeSupport(this);   

    @FXML
    private Group root;    
 
    
    TranslateTransition translateTransition;
    
    public FXML_MissileController(){
	//init();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	System.out.println("init missile");
       init();
    }
    
    public void play() {    
        unhideAll();
        this.parallelTransition.play();
    }
    
    public void unhideAll() {
        this.root.setVisible(true);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        this.pcsListeners.addPropertyChangeListener(propertyChangeListener);
    }
    
    public void removePropertyChangeListener (PropertyChangeListener propertyChangeListener) {
        this.pcsListeners.removePropertyChangeListener(propertyChangeListener);
    }

    public TranslateTransition getTranslateTransition() {
        return this.translateTransition;
    }
    
    public Group getRoot() {
        return this.root;
    }
    
    public void setRoot(Group group){
	this.root = group;
    }
    /*=========================================================================*/
    /* Private methods                                                         */       
    /*=========================================================================*/
    
    private void hideAll() {
        this.root.setVisible(false);
    }
    
    private void init() {
	Sphere sphere = new Sphere(10);
	initSphere(sphere);
        this.root.getChildren().addAll(sphere);

	ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1));       
	initScaleTransition(scaleTransition);							      
	
        this.translateTransition = new TranslateTransition(Duration.seconds(2));
	initTranslateTransition();

	this.parallelTransition = new ParallelTransition(sphere, translateTransition, scaleTransition);
	initParallelTransition();
    }

    private void initParallelTransition() {
        this.parallelTransition.setOnFinished((ActionEvent ae) -> {
            hideAll();
            this.pcsListeners.firePropertyChange("missileTargetReached", null, null);
        });
    }

    private void initScaleTransition(ScaleTransition scaleTransition) {
        scaleTransition.setFromX(1f);
        scaleTransition.setFromY(1f);
        scaleTransition.setToX(3f);
        scaleTransition.setToY(3f);
        /* Enables scaling in both directions */
        scaleTransition.setAutoReverse(true);     
        /* First cycle for scaling up, second cycle for scaling down */
        scaleTransition.setCycleCount(2);
    }

    private void initSphere(Sphere sphere) {        
        sphere.setMaterial(new PhongMaterial(Color.ORANGE));
        sphere.translateXProperty().setValue(0);
    }

    private void initTranslateTransition() {        
        this.translateTransition.setFromX(0);
        this.translateTransition.setFromY(0);
        this.translateTransition.setToX(0);
        this.translateTransition.setToY(0);
        this.translateTransition.setCycleCount(1);
        this.translateTransition.setAutoReverse(true);       
    }
}

    
