package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public final class NewUserScene extends BaseScene {
    
    public static final String NEW_USER_BACK = "Back";
    public static final String NEW_USER_REGISTER = "Register";

    // The labels
    private Label titleLbl;
    private Label usernameLbl;
    private Label passwordLbl;
    private Label emailLbl;

    // The buttons
    private Button backBtn;
    private Button registerBtn;

    // The text fields
    private TextField usernameField;
    private TextField passwordField;
    private TextField emailField;

    public NewUserScene(int width, int height) {
        super(width, height);        
    }
    
    public String getUsername() {
        return this.usernameField.getText();
    }
    
    public String getPassword() {
        return this.passwordField.getText();
    }
    
    public String getEmail() {
        return this.emailField.getText();
    }
    
    @Override
    public void setButtonHandler(EventHandler<ActionEvent> buttonHandler) {
        this.backBtn.setOnAction(buttonHandler);
        this.registerBtn.setOnAction(buttonHandler);
    }

    @Override
    public void clear() {
        this.emailField.clear();
        this.usernameField.clear();
        this.passwordField.clear();
    }

    @Override
    protected void initHeaderPane(GridPane headerPane) {
        this.titleLbl = new Label("New User Registration");
        this.titleLbl.setFont(Font.font(this.titleLbl.getFont().getFamily(), 20));
        
        headerPane.add(this.titleLbl, 0, 0);
    }

    @Override
    protected void initBodyPane(GridPane bodyPane) {
        // Initialize labels
        this.usernameLbl = new Label("Username:");
        this.passwordLbl = new Label("Password:");
        this.emailLbl = new Label("Email:");

        // Initialize text fields
        this.usernameField = new TextField();
        this.passwordField = new TextField();
        this.emailField = new TextField();
        
        // Add components
        bodyPane.add(this.usernameLbl,   0, 0);
        bodyPane.add(this.usernameField, 1, 0);
        bodyPane.add(this.passwordLbl,   0, 1);
        bodyPane.add(this.passwordField, 1, 1);
        bodyPane.add(this.emailLbl,      0, 2);
        bodyPane.add(this.emailField,    1, 2);
    }

    @Override
    protected void initFooterPane(GridPane footerPane) {
        // Initialize buttons
        this.backBtn = new Button(NEW_USER_BACK);
        this.registerBtn = new Button(NEW_USER_REGISTER);
        
        footerPane.add(this.registerBtn, 0, 0);
        footerPane.add(this.backBtn,     1, 0);
    }
}
