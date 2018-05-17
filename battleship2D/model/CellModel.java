package battleship2D.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javafx.beans.property.SimpleStringProperty;

/**
 * Board model element
 * @author xaviator
 */
public class CellModel extends UnicastRemoteObject implements CellModelInterface,Serializable {
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    /** Related type */
    private CellType cellType;
    
    /** Store the associated color */
    private final SimpleStringProperty colorProperty;
    
    /** Unique identifier */
    private Integer id;
    
    
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    
    /**
     * Constructor
     * @param cellType - associated cell type
     * @param id - identifier
     */
    
    public CellModel(CellType cellType, Integer id) throws RemoteException {
        this.colorProperty = new SimpleStringProperty();
        
        setCellType(cellType);
        setId(id);
    }
    
    /**
     * Displays informations
     * @return a brief description
     */ 
    @Override
    public String display()throws RemoteException {
        return "[" + this.id + " - " + this.cellType.getDescription() + " - " 
                + this.cellType.getAppearance() + "]";
    }
    
    /*
     * Getters / Setters
     */
     @Override
    public CellType getCellType() throws RemoteException{
        return this.cellType;
    }
     @Override
    public final void setCellType (CellType cellType) throws RemoteException{
        this.cellType = cellType;
        this.colorProperty.setValue(this.cellType.getAppearance());
    }
     @Override
    public SimpleStringProperty getColorProperty() throws RemoteException{
        return this.colorProperty;
    }
     @Override
    public Integer getId() throws RemoteException{
        return this.id;
    }
    
    @Override
    public final void setId(Integer id) throws RemoteException{
        this.id = id;
    }
}
