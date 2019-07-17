package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

/**
 * A base class for scenes.
 */
public abstract class BaseScene extends Scene {
    
    private final GridPane rootPane;
    
    @SuppressWarnings("")
    public BaseScene(int width, int height) {
        super(new GridPane(), width, height);

        // Set root pane
        this.rootPane = (GridPane) super.getRoot();
        this.rootPane.setHgap(10);
        this.rootPane.setVgap(10);
        this.rootPane.setHgap(10);
        this.rootPane.setPadding(new Insets(10, 10, 10, 10));
        this.rootPane.setAlignment(Pos.CENTER);
        this.rootPane.setPrefSize(width, height);
        
        // Set panes
        GridPane headerPane = createPane();
        GridPane bodyPane   = createPane();
        GridPane footerPane = createPane();
        
        // Initialize panes
        this.initHeaderPane(headerPane);
        this.initBodyPane(bodyPane);
        this.initFooterPane(footerPane);
                
        // Initialize root pane
        this.rootPane.add(headerPane, 0, 0);
        this.rootPane.add(bodyPane,   0, 1);
        this.rootPane.add(footerPane, 0, 2);
    }
    
    private static GridPane createPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setAlignment(Pos.CENTER);
        return pane;
    }
    
    public abstract void clear();
    
    public abstract void setButtonHandler(EventHandler<ActionEvent> buttonHandler);    
    
    protected abstract void initHeaderPane(GridPane headerPane);
    
    protected abstract void initBodyPane(GridPane bodyPane);
    
    protected abstract void initFooterPane(GridPane footerPane);
}
