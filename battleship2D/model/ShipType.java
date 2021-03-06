package battleship2D.model;

import battleship2D.ui.Config;

/**
 * Ship types
 * @author xaviator
 */
public enum ShipType {
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    BATTLESHIP("Battleship", "-fx-background-image: url(\"battleship2D/pictures/"+Config.dossier+"/battleship.jpg\"); -fx-background-size: contain;"),
    CARRIER("Carrier", "-fx-background-image: url(\"battleship2D/pictures/"+Config.dossier+"/carrier.jpg\"); -fx-background-size: contain;"),
    CRUISER("Cruiser", "-fx-background-image: url(\"battleship2D/pictures/"+Config.dossier+"/cruiser.jpg\"); -fx-background-size: contain;"),
    DESTROYER("Destroyer", "-fx-background-image: url(\"battleship2D/pictures/"+Config.dossier+"/destroyer.jpg\"); -fx-background-size: contain;"),
    SUBMARINE("Submarine", "-fx-background-image: url(\"battleship2D/pictures/"+Config.dossier+"/submarine.jpeg\"); -fx-background-size: contain;");

    /** Rendering either as a color or an image */
    private final String appearance;

    /** Short description */    
    private final String description;
    
    
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    
    /*
     * Getters / Setters     
     */
    
    /**
     * @return the description field
     */
    public String getDescription() {
        return this.description;    
    }
    
    /**
     * 
     * @return the appearance field
     */
    public String getAppearance() {
        return this.appearance;
    }
    
    
    /*=========================================================================*/
    /* Private methods                                                         */       
    /*=========================================================================*/
    
    /**
     * Constructor
     * @param description - short description
     * @param color - related color
     */
    private ShipType (final String description, final String appearance) {
        this.description = description;
        this.appearance = appearance;
    }
}
