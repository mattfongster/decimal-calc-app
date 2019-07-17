package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public final class LoginScene extends BaseScene {
    
    // Button commands
    public static final String BACK  = "Back";
    public static final String LOGIN = "Login";
    public static final String NEW_USER = "New User?";
    public static final String FORGOT_PASSWORD = "Forgot Password?";
    
    // Labels
    private Label titleLbl;
    private Label usernameLbl;
    private Label passwordLbl;

    // Buttons
    private Button backBtn;
    private Button loginBtn;
    private Button newUserBtn;
    private Button passRecBtn;

    // Text fields
    private TextField usernameField;
    private PasswordField passwordField;

    public LoginScene(int width, int height) {
        super(width, height);
    }
    
    public String getUsername() {
        return this.usernameField.getText();
    }
    
    public String getPassword() {
        return this.passwordField.getText();
    }
    
    @Override
    public void setButtonHandler(EventHandler<ActionEvent> buttonHandler) {
        this.backBtn.setOnAction(buttonHandler);
        this.loginBtn.setOnAction(buttonHandler);
        this.newUserBtn.setOnAction(buttonHandler);
        this.passRecBtn.setOnAction(buttonHandler);
    }

    @Override
    public void clear() {
        this.usernameField.clear();
        this.passwordField.clear();
    }

    @Override
    protected void initHeaderPane(GridPane headerPane) {
        this.titleLbl = new Label("Login");
        this.titleLbl.setFont(Font.font(this.titleLbl.getFont().getFamily(), 20));
        
        headerPane.add(this.titleLbl, 0, 0);
    }

    @Override
    protected void initBodyPane(GridPane bodyPane) {
        // Initialize labels
        this.usernameLbl = new Label("Username:");
        this.passwordLbl = new Label("Password:");
        
        // Initialize text fields
        this.usernameField = new TextField();
        this.passwordField = new PasswordField();
        
        bodyPane.add(this.usernameLbl,    0, 0);
        bodyPane.add(this.usernameField,  1, 0);
        bodyPane.add(this.passwordLbl,    0, 1);
        bodyPane.add(this.passwordField,  1, 1);
    }

    @Override
    protected void initFooterPane(GridPane footerPane) {
        // Initialize buttons
        this.backBtn    = new Button(BACK);
        this.loginBtn   = new Button(LOGIN);
        this.newUserBtn = new Button(NEW_USER);
        this.passRecBtn = new Button(FORGOT_PASSWORD);   
        
        footerPane.add(this.loginBtn,       0, 0);
        footerPane.add(this.newUserBtn,     1, 0);
        footerPane.add(this.passRecBtn,     2, 0);
        footerPane.add(this.backBtn,        3, 0);
    }
}
