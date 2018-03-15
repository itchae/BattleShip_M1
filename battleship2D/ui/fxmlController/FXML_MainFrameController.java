/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.model.BoardModel;
import battleship2D.model.CellType;
import battleship2D.model.SkillLevel;
import battleship2D.ui.Config;
import battleship2D.ui.GameStages;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Jeremy
 */
public class FXML_MainFrameController implements Initializable {

    @FXML
    private AnchorPane root;
    
    @FXML
    private HBox center;
    
    @FXML
    private VBox bottom;
    @FXML
    private FXML_ShipInsertionController shipInsertionController;
    
    @FXML
    private AnchorPane player;
    @FXML
    private FXML_BordUIPlayerController playerController;
    
    @FXML
    private AnchorPane computer;
    @FXML
    private FXML_BordUIComputerController computerController;
    
    
    /** Current game stage */
    private GameStages gameStage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        center.prefWidthProperty().bind(root.widthProperty());
        bottom.prefWidthProperty().bind(root.widthProperty());
        bottom.maxWidthProperty().bind(root.widthProperty());
        player.prefWidthProperty().bind(root.widthProperty().divide(2));
        computer.prefWidthProperty().bind(root.widthProperty().divide(2));
        
        //construct
        playerController.construct("Player", new BoardModel(CellType.OCEAN), true);
        this.shipInsertionController.construct(this.playerController.getBoardModel().getFleet());
        this.computerController.construct("Computer", new BoardModel(CellType.UNKNOWN), 
                false, Config.level);
    } 
    
    /**
     * Updates the stage of the game
     * @param gameStage new game stage
     */
    public void changeState(GameStages gameStage) {
        setGameStage(gameStage);
        
       /* switch(this.gameStage) {
            case PLACE_SHIPS_ON_PLAYER_BOARD:
                // The main interface is created and the player board is displayed.                
                break;
                
            case INIT_COMPUTER_BOARD:
                initComputerBoard();                   
                break;
                
            case PLAY:
                if (this.turn == Turn.PLAYER) {
                    // Enables player interactivity 
                    this.borderPane.setMouseTransparent(false);
                    
                    // A cell is randomly chosen to be the missile source. 
                    //    The source may be any ship of the player board.
                    CellModel sourceCellModel = null;
                    
                    // Search for the first ship that has not been completely destroyed yet. 
                    for (ShipType shipType : ShipType.values()) {
                        sourceCellModel = this.playerBoard.getBoardModel().findFirstCellOfType(CellType.shipTypeToCellType(shipType));
                        if (sourceCellModel != null) {
                            break;
                        }
                    }
                    if (sourceCellModel != null) {
                        CellUI cellUI = this.playerBoard.findCellUIFromModel(sourceCellModel);
                        if (cellUI != null) {
                            this.playerBoard.setMissileSource(cellUI);
                        }
                    }
                    this.messages.append("[Player] Please choose a target on the computer board.\n");                    
                    
                } 
                else {                   
                    this.borderPane.setMouseTransparent(true);
                    this.messages.append("[Computer] Playing...\n");
                    
                    // A cell is randomly chosen to be the missile source. 
                    //    The source may be any cell tagged as "UNKNOWN" in the computer board,
                    //    to avoid the player discovering a computer's ship.
                    CellModel sourceCellModel = this.computerBoard.getBoardModel().randomCell(CellType.UNKNOWN, Boolean.TRUE);
                    if (sourceCellModel != null) {
                        CellUI cellUI = this.computerBoard.findCellUIFromModel(sourceCellModel);
                        if (cellUI != null) {
                            this.computerBoard.setMissileSource(cellUI);
                        }
                    }
                    
                    // A cell is randomly chosen to be the destination of the computer missile.                     
                    Coord2D cellCoords = this.computerBoard.findMissileDestinationCell();
                    CellModel destCellModel = this.playerBoard.getBoardModel().getCellModel(cellCoords.getRow(), cellCoords.getColumn());

                    if (destCellModel != null) {
                        CellUI cellUI = this.playerBoard.findCellUIFromModel(destCellModel);
                        if (cellUI != null) {
                            this.playerBoard.setMissileDestination(cellUI);
                            launchMissiles(this.playerBoard, this.computerBoard);
                            
                            // The computer board keeps a copy of the type of the targeted cell in the player board.
                            this.computerBoard.getLastCellTargeted().setCellType(cellUI.getCellModel().getCellType());
                            
                        }
                    }
                }
                break;
                
            default:
                break;
        }*/
    }
    
    public void setGameStage(GameStages gameStage) {
        this.gameStage = gameStage;
    }
    
    
}
