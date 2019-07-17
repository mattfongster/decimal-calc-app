package client;

import com.Command;
import com.Result;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author kevin
 */
public class GUI extends Application {
    
    public static final int WINDOW_WIDTH  = 500;
    public static final int WINDOW_HEIGHT = 500;
                    
    // The scenes of the GUI
    private ConnectScene connectScene;
    private LoginScene loginScene;
    private NewUserScene newUserScene;
    private PasswordRecoveryScene passwordRecScene;
    private CalculatorScene calculatorScene;
    private GameScene gameScene;
    
    // The main stage that the scenes will be on
    private Stage primaryStage;
    
    // A client connection
    private Client client;
    
    /**************** CLIENT ******************/
    
    private void connectToServer() {
        // Obtain server information
        String host = this.connectScene.getHost();
        int port = this.connectScene.getPort();
        
        // Create client connection
        this.client = new Client(host, port);

        // Attempt to connect to server
        if (this.client.connect()) {
            this.changeScene(this.loginScene);
        }
        else {
            this.createAlert("Connect to Server", "Server Connection Error", "Server is not available.");
        }
    }
    
    private void login() {
        // Obtain login information
        String username = this.loginScene.getUsername();
        String password = this.loginScene.getPassword();
        
        if (username.equals("") || password.equals("")) {
            return;
        }
        
        // Attempt to login 
        String output = this.client.sendMessage(Command.loginCommand(username, password));
        switch (output) {
            case Result.LOGIN_SUCCESS:
                this.changeScene(this.calculatorScene);
                break;
            case Result.LOGIN_BAD_USERNAME:
                this.createAlert("Login", "Bad Username", "The username does not exist.");
                break;
            case Result.LOGIN_BAD_PASSWORD:
                this.createAlert("Login", "Bad Password", "The password is incorrect.");
                break;
            case Result.LOGIN_TOO_MANY_ATTEMPTS:
                this.createAlert("Login", "Too Many Attempts to Login", "There were too many login attempts made for this account.");
                this.changeScene(this.passwordRecScene);
                break;
        }
    }
    
    private void register() {
        // Obtain login information
        String username = this.newUserScene.getUsername();
        String password = this.newUserScene.getPassword();
        String emailAddress = this.newUserScene.getEmail();
        
        if (username.equals("") || password.equals("")) {
            return;
        }
        // Check the login information
        if (password.length() < 8) {
            this.createAlert("New User Registration", "Bad Password", "The password is not long enough.");
            return;
        }
        else if (!emailAddress.matches("[a-zA-Z][a-zA-Z0-9]+@[a-zA-Z]+['.'][a-z]+")) {
            this.createAlert("New User Registration", "Bad Email", "The email address is not of the proper format.");
            return;
        }
        
        // Attempt to register
        String output = this.client.sendMessage(Command.registerCommand(username, password, emailAddress));
        switch (output) {
            case Result.REGISTER_SUCCESS:
                this.createAlert("New User Registration", "Success", "The registration was successful.");
                break;
            case Result.REGISTER_DUPLICATE_USER:
                this.createAlert("New User Registration", "Duplicate Account", "An account with the same username exists in the server.");
                break;
        }
    }
    
    private void recoverPassword() {
        String username = this.passwordRecScene.getUsername();
        String output = this.client.sendMessage(Command.passwordRecCommand(username));
        switch (output) {
            case Result.PASSWORD_REC_BAD_USERNAME:
                this.createAlert("Password Recovery", "Bad Username", "The username does not exist");
                break;
            case Result.PASSWORD_REC_SUCCESS:
                this.createAlert("Password Recovery", "Success", "An email with your new password has been sent.");
                break;
        }
    }
    
    private void calculate() {
        String expr = this.calculatorScene.getDisplayText();
        String output = this.client.sendMessage(Command.calculateCommand(expr));
        switch (output) {
            case Result.CALC_BAD_EXPR:
                this.calculatorScene.updateDisplayText("ERROR");
                break;
            default:
                this.calculatorScene.updateDisplayText(output);
                break;
        }
    }
    
    private void submitAnswer() {
        String problem = this.gameScene.getProblem();
        String output = this.client.sendMessage(Command.gameCommand(problem));
        
        if (output.equals(this.gameScene.getAnswer())) {
            createAlert("Game", "Correct!", "You got the correct answer.");
        }
        else {
            createAlert("Game", "Incorrect!", "You got the wrong answer.");
        }
    }
    
    /**************** GUI *******************/
    
    private void createAlert(String title, String header, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, body, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.show();
    }
    
    /**
     * Change stage's current scene.
     * 
     * @param scene the new scene.
     */
    private void changeScene(BaseScene scene) {
        // Change title of window
        switch (scene.getClass().getSimpleName()) {
            case "ConnectScene":
                this.primaryStage.setTitle("Connect to Server");
                break;
            case "LoginScene":
                this.primaryStage.setTitle("Login");
                break;
            case "NewUserScene":
                this.primaryStage.setTitle("New User Registration");
                break;
            case "PasswordRecScene":
                this.primaryStage.setTitle("Password Recovery");
                break;
            case "CalculatorScene":
                this.primaryStage.setTitle("Calculator");
                break;
            default:
                break;
        }
        
        // Clear scene's previous contents from user
        scene.clear();
        // Change scene
        this.primaryStage.setScene(scene);
    }
    
    /**
     * Initialize scene for allowing the user to connect to the server.
     */
    private void initConnectScene() {
        this.connectScene = new ConnectScene(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.connectScene.setButtonHandler((ActionEvent e) -> {
            Button button = (Button) e.getSource();
            switch (button.getText()) {
                case ConnectScene.CONNECT:
                    this.connectToServer();
                    break;
                case ConnectScene.EXIT:
                    System.exit(0);
                    break;
            }
        });
    }
    
    /**
     * Initialize scene for allowing the user to log into the calculator application.
     */
    private void initLoginScene() {
        this.loginScene = new LoginScene(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.loginScene.setButtonHandler((ActionEvent e) -> {
            Button button = (Button) e.getSource();
            switch (button.getText()) {
                case LoginScene.BACK:
                    // Disconnect from server
                    this.client.disconnect();
                    // Go back to server connection scene
                    this.changeScene(this.connectScene);
                    break;
                case LoginScene.FORGOT_PASSWORD:
                    this.changeScene(this.passwordRecScene);
                    break;
                case LoginScene.LOGIN:
                    this.login();
                    break;
                case LoginScene.NEW_USER:
                    this.changeScene(this.newUserScene);
                    break;
            }
        });
    }
    
    /**
     * Initialize scene for allowing the user to create a new account.
     */
    private void initNewUserScene() {
        this.newUserScene = new NewUserScene(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.newUserScene.setButtonHandler((ActionEvent e) -> {
            Button button = (Button) e.getSource();
            switch (button.getText()) { 
                case NewUserScene.NEW_USER_BACK:
                    this.changeScene(this.loginScene);
                    break;
                case NewUserScene.NEW_USER_REGISTER:
                    this.register();
                    break;
            }
        });
    }
    
    /**
     * Initialize scene for allowing the user to recover his/her password.
     */
    private void initPasswordRecScene() {
        this.passwordRecScene = new PasswordRecoveryScene(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.passwordRecScene.setButtonHandler((ActionEvent e) -> {
            Button button = (Button) e.getSource();
            switch (button.getText()) {
                case PasswordRecoveryScene.PASSWORD_SUBMIT:
                    this.recoverPassword();
                    break;
                case PasswordRecoveryScene.PASSWORD_BACK:
                    this.changeScene(this.loginScene);
                    break;
            }
        });
    }
    
    /**
     * Initialize scene for allowing the user to use the calculator.
     */
    private void initCalculatorScene() {
        this.calculatorScene = new CalculatorScene(WINDOW_WIDTH - 100, WINDOW_HEIGHT);
        this.calculatorScene.setButtonHandler((ActionEvent e) -> {
            Button button = (Button) e.getSource();
            switch (button.getText()) {
                case CalculatorScene.EXIT:
                    this.changeScene(this.loginScene);
                    break;
                case CalculatorScene.GAME:
                    Stage gameStage = new Stage();
                    gameStage.setScene(this.gameScene);
                    gameStage.show();
                    break;
                case CalculatorScene.CALCULATE:
                    this.calculate();
                    break;
            }
        });
    }
    
    private void initGameScene() {
        this.gameScene = new GameScene(300, 300);
        this.gameScene.setButtonHandler((ActionEvent e) -> {
            Button button = (Button) e.getSource();
            switch (button.getText()) {
                case GameScene.SUBMIT:
                    this.submitAnswer();
                    break;
            }
        });
    }
    
    @Override
    public void start(Stage stage) {
        // Initialize scenes
        this.initConnectScene();
        this.initLoginScene();
        this.initNewUserScene();
        this.initPasswordRecScene();
        this.initCalculatorScene();
        this.initGameScene();
        
        // Initialize primary stage and its window listener
        this.primaryStage = stage;
        this.primaryStage.setOnCloseRequest((WindowEvent event) -> {
            // If the client was initialized and is still connected, disconnect it
            // before closing window
            if (this.client != null && this.client.isConnected()) {
                this.client.disconnect();
            }
        });
        
        // Show primary stage
        this.changeScene(this.connectScene);
        this.primaryStage.show();        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
