/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.model.BoardModel;
import battleship2D.model.CellType;
import battleship2D.model.ShipType;
import battleship2D.model.Turn;
import battleship2D.ui.CellUI;
import battleship2D.ui.Config;
import battleship2D.ui.GameStages;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import battleship2D.model.CellModelInterface;
import battleship2D.model.Coord2D;
import battleship2D.model.Direction;
import battleship2D.model.Fleet;
import battleship2D.model.Ship;
import battleship2D.ui.Explosion;
import battleship2D.ui.MainFrame;
import java.rmi.RemoteException;
import battleship2D.ui.fxml.FXML_BattleShip2D;import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author Jeremy
 */
public class FXML_MainFrameController implements Initializable {

    @FXML
    private BorderPane borderPane;
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private HBox center;
    
    @FXML
    private VBox bottom;
    
    @FXML
    private FXML_ShipInsertionController shipInsertionController;
    
    @FXML
    private GridPane player;
    
    @FXML
    private FXML_EndGameController endGameController;
  
    @FXML
    private FXML_BordUIPlayerController playerController;
    
    @FXML
    private GridPane computer;
    
    @FXML
    private FXML_BordUIComputerController computerController;
    
    @FXML
    private FXML_MessagesController msgController;
    
    
    /** Current game stage */
    private GameStages gameStage;
    
    /** This class listens for each of its boards and the missile class */
    private PropertyChangeListener propertyChangeListener;
    
    /** Opponents turns */
    private Turn turn;/** End game animation */
    //private final EndGame endGame;
    
    @FXML
    private BorderPane endGame;
    
    /** Missiles sent by boards */
    private FXML_MissileController missileController;
    private Group missile;
    
    /** Explosion image and animation */
    private  Explosion explosion; 
    
        /** Coordinates linking the (player-selected or computer-selected) target 
     * and the missile trajectory endpoints */
    private  SimpleDoubleProperty missileSourceX, missileSourceY, missileDestX, missileDestY;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            //construct
            playerController.construct("Player", new BoardModel(CellType.OCEAN), true);
        } catch (RemoteException ex) {
            Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.shipInsertionController.construct(this.playerController.getBoardModel().getFleet());
        } catch (RemoteException ex) {
            Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.computerController.construct("Computer", new BoardModel(CellType.UNKNOWN),
                    false, Config.level);
        } catch (RemoteException ex) {
            Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
	// Création du loader.
	final FXMLLoader fxmlLoader = new FXMLLoader(FXML_MissileController.class.getResource("/battleship2D/ui/fxml/FXML_Missile.fxml"));
	try {
	    this.missile = (Group) fxmlLoader.load();
	    this.root.getChildren().add(this.missile);
	} catch (IOException ex) {
	    Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
	}
	
        this.missileController = (FXML_MissileController) fxmlLoader.getController();
        this.explosion = new Explosion(20,20);         
        this.endGame = null;
        initRoot();
        initListener();
        initPlayerBoard();        
        initShipSelection(); 
        initExplosion();
        
        this.missileSourceX = new SimpleDoubleProperty(0);
        this.missileSourceY = new SimpleDoubleProperty(0);
        this.missileDestX = new SimpleDoubleProperty(0);
        this.missileDestY = new SimpleDoubleProperty(0);
        this.turn = Turn.PLAYER;
    }
    
    @FXML
    private void win(){
	FXML_BattleShip2D.setPlayerWin(true);
	initEndGame();
    }
    
    @FXML
    private void lose(){
	FXML_BattleShip2D.setPlayerWin(false);
	initEndGame();
    }
    //leurs codes
     /**
     * Updates the stage of the game
     * @param gameStage new game stage
     */
    public void changeState(GameStages gameStage) throws RemoteException {
        setGameStage(gameStage);
        
        switch(this.gameStage) {
            case PLACE_SHIPS_ON_PLAYER_BOARD:
                /* The main interface is created and the player board is displayed. */                
                break;
                
            case INIT_COMPUTER_BOARD:
                initComputerBoard();                   
                break;
                
            case PLAY:
                if (this.turn == Turn.PLAYER) {
                    /* Enables player interactivity */
                    this.borderPane.setMouseTransparent(false);
                    
                    /* A cell is randomly chosen to be the missile source. 
                        The source may be any ship of the player board. */
                    CellModelInterface sourceCellModel = null;
                    
                    /* Search for the first ship that has not been completely destroyed yet. */
                    for (ShipType shipType : ShipType.values()) {
                        sourceCellModel = this.playerController.getBoardModel().findFirstCellOfType(CellType.shipTypeToCellType(shipType));
                        if (sourceCellModel != null) {
                            break;
                        }
                    }
                    if (sourceCellModel != null) {
                        CellUI cellUI = this.playerController.findCellUIFromModel(sourceCellModel);
                        if (cellUI != null) {
                            this.playerController.setMissileSource(cellUI);
                        }
                    }
                    this.msgController.append("[Player] Please choose a target on the computer board.\n");                    
                    
                } 
                else {                   
                    this.borderPane.setMouseTransparent(true);
                    this.msgController.append("[Computer] Playing...\n");
                    
                    /* A cell is randomly chosen to be the missile source. 
                        The source may be any cell tagged as "UNKNOWN" in the computer board,
                        to avoid the player discovering a computer's ship.*/
                    CellModelInterface sourceCellModel = this.computerController.getBoardModel().randomCell(CellType.UNKNOWN, Boolean.TRUE);
                    if (sourceCellModel != null) {
                        CellUI cellUI = this.computerController.findCellUIFromModel(sourceCellModel);
                        if (cellUI != null) {
                            this.computerController.setMissileSource(cellUI);
                        }
                    }
                    
                    /* A cell is randomly chosen to be the destination of the computer missile. */                    
                    Coord2D cellCoords = this.computerController.findMissileDestinationCell();
                    CellModelInterface destCellModel = this.playerController.getBoardModel().getCellModel(cellCoords.getRow(), cellCoords.getColumn());

                    if (destCellModel != null) {
                        CellUI cellUI = this.playerController.findCellUIFromModel(destCellModel);
                        if (cellUI != null) {
                            this.playerController.setMissileDestination(cellUI);
                            launchMissiles(this.playerController, this.computerController);
                            
                            /* The computer board keeps a copy of the type of the targeted cell in the player board. */
                            this.computerController.getLastCellTargeted().setCellType(cellUI.getCellModel().getCellType());
                            
                        }
                    }
                }
                break;
                
            default:
                break;
        }
    }


    /* 
     * Getters/Setters 
     */
    
    public FXML_BordUIPlayerController getPlayerBoard() {
        return this.playerController;
    }

    public FXML_BordUIComputerController getComputerBoard() {
        return this.computerController;
    }
    
    public GameStages getGameStage() {
        return this.gameStage;
    }

    public void setGameStage(GameStages gameStage) {
        this.gameStage = gameStage;
    }
    
    public AnchorPane getRoot() {
        return this.root;
    }
    public FXML_ShipInsertionController getShipSelection() {
        return this.shipInsertionController;
    }


    /*=========================================================================*/
    /* Private methods                                                         */       
    /*=========================================================================*/

    /**
     * Displays information about the last ship hit by a missile
     * @param boardUI - hit board
     * @param shipType - the kind of ship that was hit
     * @see initListener()
     */
    private void displayLastHitInfo(FXML_BordUIController boardUI, ShipType shipType) throws RemoteException {
        Fleet fleet = boardUI.getBoardModel().getFleet();
        Ship hitShip = fleet.findShipFromType(shipType);        
        
        if (hitShip != null) {           
            this.msgController.append("[" + boardUI.getName() + "] A ship has been hit!\n");
            
            if (fleet.isLastHitShipDestroyed()) {
                this.msgController.append("[" + boardUI.getName() + "] " + hitShip.getDescription() + " has been destroyed!!\n");
                
                /* If the destroyed ship was part of the player's fleet, notifies the computer side */
                if (boardUI == this.playerController) {
                    this.computerController.updateInfoAboutAdverseDestroyedShip(hitShip.getDescription());
                }
                
                if (fleet.isFleetDestroyed()) {
                    this.msgController.append("[" + boardUI.getName() + "] " + "The whole fleet has been destroyed!!!\n");
                    if (boardUI == this.playerController) {
                        runEndGame(false);
                    }
                    else {
                        runEndGame(true);
                    }
                }
            }
        }        
    }
    
    /**
     * Shows different locations for placing the currently selected ship type
     * on the player board
     * @param cellModel - first cell of the span representing the ship
     * @see initListener()
     */    
    private void displayValidShipSites(CellModelInterface cellModel) throws RemoteException {          
        int selectedShipIndex = getShipSelection().selectedShipIndex();        
        Ship selectedShip = this.playerController.getBoardModel().getFleet().getShips().get(selectedShipIndex);        
        int shipSize = selectedShip.getSize();        
        
        /* Searches for horizontal and vertical sets of cells available to receive the ship */
        this.playerController.availableCellSpanByDirection(cellModel, Direction.NORTH, shipSize);                
        this.playerController.availableCellSpanByDirection(cellModel, Direction.WEST, shipSize);
        this.playerController.availableCellSpanByDirection(cellModel, Direction.SOUTH, shipSize);
        this.playerController.availableCellSpanByDirection(cellModel, Direction.EAST, shipSize);
    }
    
    /**
     * Initializes computer board's fleet and displays it
     * @see changeState()
     */
    private void initComputerBoard() throws RemoteException {
        /* No action from both the player board and the ship selection widgets are allowed anymore */
        this.playerController.removePropertyChangeListener(this.propertyChangeListener);
        this.shipInsertionController.removePropertyChangeListener(this.propertyChangeListener);

        /* The widgets for setting ships location are made invisible */
        this.shipInsertionController.hide();

        /* The computer board is displayed */        
        this.computerController.setVisible(true);
        
        /* And its ships are randomly placed on without any user action. */
        this.computerController.placeShipsOnBoardAtRandom();
        
        /* The computer board can now send signals to this. */
        this.computerController.addPropertyChangeListener(this.propertyChangeListener);
        
        /* The missile trajectory endpoints are bound up with cell coordinates */
        this.missileController.getTranslateTransition().fromXProperty().bind(this.missileSourceX);        
        this.missileController.getTranslateTransition().fromYProperty().bind(this.missileSourceY);
        this.missileController.getTranslateTransition().toXProperty().bind(this.missileDestX);        
        this.missileController.getTranslateTransition().toYProperty().bind(this.missileDestY);
        
        /* This MainFrame will be notified when the missile animation has finished */
        this.missileController.addPropertyChangeListener(this.propertyChangeListener);
        
        changeState(GameStages.PLAY);
    }
   
    /**
     * Initializes the animation played when the games ends
     * @see MainFrame()
     */
    private void initEndGame() {
	URL url = getClass().getResource("/battleship2D/ui/fxml/FXML_EndGame.fxml");
	FXMLLoader fxmlLoader = new FXMLLoader(url);
	try {
	    AnchorPane rootEnd = (AnchorPane) fxmlLoader.load();
	    this.borderPane.getChildren().clear();
	    this.borderPane.setCenter(rootEnd);
	    
	    rootEnd.prefWidthProperty().bind(this.borderPane.widthProperty()); 
	    rootEnd.prefHeightProperty().bind(this.borderPane.heightProperty());
	} catch (IOException ex) {
	    Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    /**
     * Initializes data related to ship explosion
     * @see MainFrame()
     */
    private void initExplosion() {
        this.explosion.setVisible(false);
    }
    
    
    /**
     * Initializes the boards listener
     * @see MainFrame()
     */
    private void initListener() {
        this.propertyChangeListener = (PropertyChangeEvent propertyChangeEvent) -> {
            
            if (this.gameStage ==  GameStages.PLACE_SHIPS_ON_PLAYER_BOARD) {
                try {
                    manageEventsPlaceShipsOnPlayerBoard(propertyChangeEvent);
                } catch (RemoteException ex) {
                    Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {  
                String property = propertyChangeEvent.getPropertyName();

                /* The player missile targets a cellUI of the computer board */
                if ("boardUIComputerUnknownOrShip".equals(property)) {                     
                    launchMissiles(this.computerController, this.playerController);
                }
 
               /* After the missile animation */
                if ("missileTargetReached".equals(property)) { 
                    if (this.turn == Turn.COMPUTER) {
                        CellUI destCellUI = this.playerController.getMissileDestination();
                        try {
                            destCellUI.updateAfterMissile();
                        } catch (RemoteException ex) {
                            Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (destCellUI.isLastHitAShip()) {
                            ShipType shipType = CellType.cellTypeToShipType(destCellUI.getLastHit());
                            try {
                                this.playerController.getBoardModel().getFleet().updateHits(shipType);
                            } catch (RemoteException ex) {
                                Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                showExplosion(this.playerController);
                            } catch (RemoteException ex) {
                                Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                displayLastHitInfo(this.playerController, shipType);
                            } catch (RemoteException ex) {
                                Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        this.turn = Turn.PLAYER;
                    }
                    else if (this.turn == Turn.PLAYER) {
                        CellUI destCellUI = this.computerController.getMissileDestination();
                        try {
                            destCellUI.updateAfterMissile();
                        } catch (RemoteException ex) {
                            Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (destCellUI.isLastHitAShip()) {
                            ShipType shipType = CellType.cellTypeToShipType(destCellUI.getLastHit());
                            try {
                                this.computerController.getBoardModel().getFleet().updateHits(shipType);
                            } catch (RemoteException ex) {
                                Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                showExplosion(computerController);
                            } catch (RemoteException ex) {
                                Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {                            
                                displayLastHitInfo(this.computerController, shipType);
                            } catch (RemoteException ex) {
                                Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        this.turn = Turn.COMPUTER;            
                    }
                    try {
                        changeState(GameStages.PLAY);
                    } catch (RemoteException ex) {
                        Logger.getLogger(FXML_MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        };
    }
    
    /**
     * Initializes player's board
     * @see MainFrame()
     */
    private void initPlayerBoard() {        
        this.playerController.addPropertyChangeListener(this.propertyChangeListener);        
    }
    
    /**
     * Starts the construction of all elements
     * @see MainFrame()
     */
    private void initRoot() {
        center.prefWidthProperty().bind(root.widthProperty());
        bottom.prefWidthProperty().bind(root.widthProperty());
        bottom.maxWidthProperty().bind(root.widthProperty());
        player.prefWidthProperty().bind(root.widthProperty().divide(2));
        computer.prefWidthProperty().bind(root.widthProperty().divide(2));
        //this.root.getChildren().addAll(this.missile.getRoot());
        this.root.getChildren().addAll(this.explosion);
    }
    
    /**
     * Initializes data for ship selection
     * @see MainFrame()
     */
    private void initShipSelection() {        
        this.shipInsertionController.addPropertyChangeListener(this.propertyChangeListener);
    }

    
    /**
     * Displays missile flight from a board to the other
     * @param destBoardUI - Trajectory destination board
     * @param sourceBoardUI - Trajectory source board
     * @see changeState()
     */    
    private void launchMissiles(FXML_BordUIController destBoardUI, FXML_BordUIController sourceBoardUI) { 
        this.explosion.stop();        
        CellUI destCellUI = destBoardUI.getMissileDestination();
        CellUI sourceCellUI = sourceBoardUI.getMissileSource();
        
        this.missileDestX.bind(destCellUI.layoutXProperty()
        .add(destCellUI.widthProperty().divide(2))
        .add(destBoardUI.layoutXProperty()).add(this.center.layoutXProperty()));
        
        this.missileDestY.bind(destCellUI.layoutYProperty()
        .add(destCellUI.heightProperty().divide(2))
        .add(destBoardUI.layoutYProperty()).add(this.center.layoutYProperty()));
        
        this.missileSourceX.bind(sourceCellUI.layoutXProperty()
        .add(sourceCellUI.widthProperty().divide(2))
        .add(sourceBoardUI.layoutXProperty()).add(this.center.layoutXProperty()));
        
        this.missileSourceY.bind(sourceCellUI.layoutYProperty()
        .add(sourceCellUI.heightProperty().divide(2))
        .add(sourceBoardUI.layoutYProperty()).add(this.center.layoutYProperty()));
        
        this.missileController.play();    
    }
    
    /**
     * Manages an event triggered during PLACE_SHIPS_ON_PLAYER_BOARD game stage
     * @param propertyChangeEvent - the event to deal with
     * @see initListener()
     */
    private void manageEventsPlaceShipsOnPlayerBoard(PropertyChangeEvent propertyChangeEvent) throws RemoteException {
        String property = propertyChangeEvent.getPropertyName();
            
        /* On the player board, canceling ship insertion may be triggered
            either with 3rd mouse button, or by clicking onan "ocean" cell */
        if (("boardUIPlayerCancel".equals(property) || ("boardUIPlayerOcean".equals(property)))) {  
            ShipType shipCellType = selectedShipType(this.playerController);
            this.playerController.eraseSelectedShipLocations(CellType.shipTypeToCellType(shipCellType));                
        }

        if ("boardUIPlayerOcean".equals(property)) {  
            /* Highlights the positions available to place a ship on the board */     
            CellModelInterface cellModel = (CellModelInterface) propertyChangeEvent.getNewValue();                    
            displayValidShipSites(cellModel);
        }


        /* Actually places a ship on the player board */
        if ("boardUIPlayerAvailableLocation".equals(property)) { 
                ShipType shipCellType = selectedShipType(this.playerController);
                this.playerController.placeShipOnBoard(CellType.shipTypeToCellType(shipCellType));

        }

        /* Checks whether all ships in the fleet have been placed */
        if ("shipInsertionAreAllShipsOnBoard".equals(property)) {             
            Boolean allShipsOnBoard = true;

            Fleet fleet = this.playerController.getBoardModel().getFleet();
            for (Ship ship : fleet.getShips()) {
                CellType cellType = CellType.shipTypeToCellType(ship.getShipType());
                if (! this.playerController.isShipOnBoard(cellType)) {
                    allShipsOnBoard = false;
                    break;
                }
            }
            if (! allShipsOnBoard) {                       
                this.msgController.append("[Player] At least one ship has not been placed on the board.\n");
            }
            else {
            /* The stage about placing ships has ended. Next stage begins.  */
            changeState(GameStages.INIT_COMPUTER_BOARD);  
            }
        }            
    }
   
    /**
     * Activate the end game animation
     * @param playerWins - true if the player has won the game, false otherwise
     */
    private void runEndGame(Boolean playerWins) {        
       // this.endGame = new EndGame(playerWins);
       FXML_BattleShip2D.setPlayerWin(playerWins);
        initEndGame();   
        
        /* Waits for 2.5s before displaying the end game animation, in order to see the last (piece of) ship explosion. */
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.borderPane.setVisible(false);
        
        this.endGameController.start();
    }
    
    /**
     * @return the type of the currently selected ship 
     * @param boardUI - board upon which the ship is located
     * @see initListener()
     */
    private ShipType selectedShipType(FXML_BordUIController boardUI) throws RemoteException {
        int selectedShipIndex = this.getShipSelection().selectedShipIndex();        
        
        Ship selectedShip = boardUI.getBoardModel().getFleet().getShips().get(selectedShipIndex);
        
        return selectedShip.getShipType();
    }

    /**
     * Places the explosion animation over the last cell representing a ship
     * hit by a missile and plays it
     * @param boardUI - the Board containing the cell
     * @see initListener()
     */
    private void showExplosion(FXML_BordUIController boardUI) throws RemoteException {
        /* missileDestX and missileDestY represent the center of the hit cell */
        /* To reach the top-left position of the animation, we subtract half its width and height */        
        CellUI cellUI = boardUI.getMissileDestination();
        if((cellUI != null) && (cellUI.getCellModel().getCellType() == CellType.HIT)) {            
            
            this.explosion.translateXProperty().bind(this.missileDestX.subtract(cellUI.widthProperty().divide(2)));                        
            this.explosion.translateYProperty().bind(this.missileDestY.subtract(cellUI.heightProperty().divide(2)));                
            
            this.explosion.prefWidthProperty().bind(cellUI.widthProperty()); // calls explosion.layoutChildren();
            this.explosion.prefHeightProperty().bind(cellUI.heightProperty()); // calls explosion.layoutChildren();
            
            this.explosion.start();
        }            
    }
   }
