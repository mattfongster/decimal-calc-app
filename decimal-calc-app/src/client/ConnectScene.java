package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public final class ConnectScene extends BaseScene {

    // Button commands
    public static final String EXIT    = "Exit";
    public static final String CONNECT = "Connect";    
        
    // Labels
    private Label hostLbl;
    private Label portLbl;
    private Label titleLbl;

    // The buttons
    private Button exitBtn;
    private Button connectBtn;

    // The text fields
    private TextField hostField;
    private TextField portField;

    public ConnectScene(int width, int height) {
        super(width, height);
    }

    public String getHost() {
        return ConnectScene.this.hostField.getText();
    }
    
    public int getPort() {
        return Integer.parseInt(ConnectScene.this.portField.getText());
    }

    @Override
    public void setButtonHandler(EventHandler<ActionEvent> buttonHandler) {
        this.exitBtn.setOnAction(buttonHandler);
        this.connectBtn.setOnAction(buttonHandler);
    }

    @Override
    public void clear() {
        this.hostField.clear();
        this.portField.clear();
    }

    @Override
    protected void initHeaderPane(GridPane headerPane) {
        this.titleLbl = new Label("Connect to Server");
        this.titleLbl.setFont(Font.font(this.titleLbl.getFont().getFamily(), 20));
        
        headerPane.add(this.titleLbl, 0, 0);
    }

    @Override
    protected void initBodyPane(GridPane bodyPane) {
        // Initialize labels
        this.hostLbl = new Label("Host:");
        this.portLbl = new Label("Port:");

        // Initialize text fields
        this.hostField = new TextField();
        this.portField = new TextField();
        
        // Add components
        bodyPane.add(this.hostLbl,   0, 0);
        bodyPane.add(this.hostField, 1, 0);
        bodyPane.add(this.portLbl,   0, 1);
        bodyPane.add(this.portField, 1, 1);
    }

    @Override
    protected void initFooterPane(GridPane footerPane) {
        // Initialize buttons
        this.exitBtn    = new Button(EXIT);
        this.connectBtn = new Button(CONNECT);
        
        // Add components to footer pane
        footerPane.add(this.connectBtn, 0, 0);
        footerPane.add(this.exitBtn,    1, 0);
    }
}
