package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * 
 */
public final class CalculatorScene extends BaseScene {
    
    public static final String EXIT      = "Exit";
    public static final String GAME      = "Game";
    public static final String CALCULATE = "Calculate";
       
    // The menu bar
    private MenuBar menuBar;
    private MenuItem menuGame;
    private MenuItem menuExit;
    
    // Calculator display field
    private TextField displayField;
    
    // Numeric buttons
    private Button zeroButton;
    private Button oneButton;
    private Button twoButton;
    private Button threeButton;
    private Button fourButton;
    private Button fiveButton;
    private Button sixButton;
    private Button sevenButton;
    private Button eightButton;
    private Button nineButton;
    private Button decimalButton;
    
    // Operator buttons
    private Button addButton;
    private Button subtractButton;
    private Button multiplyButton;
    private Button divideButton;
    private Button modulusButton;
    private Button equalButton;
    
    // Calculator functionality buttons
    private Button acButton;
    private Button deleteButton;
    
    // Secret buttons
    private Button exitBtn;
    private Button gameBtn;
    private Button calculateBtn;
    
    public CalculatorScene(int width, int height) {
        super(width, height);
    } 
    
    private void initNumericButtons() {
        // Initialize numeric buttons
        this.zeroButton = new Button("0");
        this.oneButton = new Button("1");
        this.twoButton = new Button("2");
        this.threeButton = new Button("3");
        this.fourButton = new Button("4");
        this.fiveButton = new Button("5");
        this.sixButton = new Button("6");
        this.sevenButton = new Button("7");
        this.eightButton = new Button("8");
        this.nineButton = new Button("9");
        this.decimalButton = new Button(".");

        // Set button sizes
        this.zeroButton.setPrefSize(50, 50);
        this.oneButton.setPrefSize(50, 50);
        this.twoButton.setPrefSize(50, 50);
        this.threeButton.setPrefSize(50, 50);
        this.fourButton.setPrefSize(50, 50);
        this.fiveButton.setPrefSize(50, 50);
        this.sixButton.setPrefSize(50, 50);
        this.sevenButton.setPrefSize(50, 50);
        this.eightButton.setPrefSize(50, 50);
        this.nineButton.setPrefSize(50, 50);
        this.decimalButton.setPrefSize(50, 50);        
    }
    
    private void initOperatorButtons() {
        // Initialize operator buttons
        this.addButton = new Button("+");
        this.subtractButton = new Button("-");
        this.multiplyButton = new Button("*");
        this.divideButton = new Button("/");
        this.modulusButton = new Button("mod");
        this.equalButton = new Button("=");
        
        this.addButton.setPrefSize(50, 50);
        this.subtractButton.setPrefSize(50, 50);
        this.multiplyButton.setPrefSize(50, 50);
        this.divideButton.setPrefSize(50, 50);
        this.modulusButton.setPrefSize(50, 50);
        this.equalButton.setPrefSize(50, 50);        
    }
    
    private void initFunctionButtons() {
        // Initialize functionality buttons
        this.acButton = new Button("A/C");
        this.deleteButton = new Button("Del");
        
        this.acButton.setPrefSize(50, 50);
        this.deleteButton.setPrefSize(50, 50);
    }
    
    public String getDisplayText() {
        return this.displayField.getText();
    }
    
    public void updateDisplayText(String expr) {
        this.displayField.setText(expr);
    }

    @Override
    public void clear() {
        this.displayField.setText("");
    }

    @Override
    public void setButtonHandler(EventHandler<ActionEvent> buttonHandler) {
        this.calculateBtn.setOnAction(buttonHandler);
        this.exitBtn.setOnAction(buttonHandler);
        this.gameBtn.setOnAction(buttonHandler);
    }

    @Override
    protected void initHeaderPane(GridPane headerPane) {
        this.displayField = new TextField();
        this.displayField.setPrefSize(250, 120);
        this.displayField.setEditable(false);
        this.displayField.setFont(Font.font(this.displayField.getFont().getFamily(), 20));
        
        headerPane.add(this.displayField, 0, 0);
    }

    @Override
    protected void initBodyPane(GridPane bodyPane) {
        this.initNumericButtons();
        this.initOperatorButtons();
        this.initFunctionButtons();
        
        this.calculateBtn = new Button(CALCULATE);
        
        EventHandler<ActionEvent> calcHandler = (ActionEvent e) -> {
            String text = this.displayField.getText();
            Button button = (Button) e.getSource();
            switch (button.getText()) {
                case "0": case "1": case "2": case "3": case "4": case "5": 
                case "6": case "7": case "8": case "9": case ".":
                    this.displayField.setText(text + button.getText());
                    break;
                case "+" : case "-": case "*": case "/": case "mod":
                    this.displayField.setText(String.format("%s %s ", text, button.getText()));
                    break;
                case "=":
                    this.calculateBtn.fire();
                    break;
                case "A/C":
                    this.displayField.setText("");
                    break;
                case "Del":
                    this.displayField.setText(text.substring(0, text.length() - 1));
                    break;
            }
        };
        this.zeroButton.setOnAction(calcHandler);
        this.oneButton.setOnAction(calcHandler);
        this.twoButton.setOnAction(calcHandler);
        this.threeButton.setOnAction(calcHandler);
        this.fourButton.setOnAction(calcHandler);
        this.fiveButton.setOnAction(calcHandler);
        this.sixButton.setOnAction(calcHandler);
        this.sevenButton.setOnAction(calcHandler);
        this.eightButton.setOnAction(calcHandler);
        this.nineButton.setOnAction(calcHandler);
        this.decimalButton.setOnAction(calcHandler);
        this.addButton.setOnAction(calcHandler);
        this.subtractButton.setOnAction(calcHandler);
        this.multiplyButton.setOnAction(calcHandler);
        this.divideButton.setOnAction(calcHandler);
        this.modulusButton.setOnAction(calcHandler);
        this.equalButton.setOnAction(calcHandler);
        this.acButton.setOnAction(calcHandler);
        this.deleteButton.setOnAction(calcHandler);
        
        // Add components to rootPane
        bodyPane.add(this.acButton,       0, 0);
        bodyPane.add(this.deleteButton,   1, 0);
        bodyPane.add(this.modulusButton,  2, 0);
        bodyPane.add(this.divideButton,   3, 0);
        bodyPane.add(this.sevenButton,    0, 1);
        bodyPane.add(this.eightButton,    1, 1);
        bodyPane.add(this.nineButton,     2, 1);
        bodyPane.add(this.multiplyButton, 3, 1);
        bodyPane.add(this.fourButton,     0, 2);
        bodyPane.add(this.fiveButton,     1, 2);
        bodyPane.add(this.sixButton,      2, 2);
        bodyPane.add(this.subtractButton, 3, 2);
        bodyPane.add(this.oneButton,      0, 3);
        bodyPane.add(this.twoButton,      1, 3);
        bodyPane.add(this.threeButton,    2, 3);
        bodyPane.add(this.addButton,      3, 3);
        bodyPane.add(this.zeroButton,     1, 4);
        bodyPane.add(this.decimalButton,  2, 4);
        bodyPane.add(this.equalButton,    3, 4);
    }

    @Override
    protected void initFooterPane(GridPane footerPane) {
        this.exitBtn = new Button(EXIT);
        this.gameBtn = new Button(GAME);
        
        this.menuGame = new MenuItem("Game");
        this.menuExit = new MenuItem("Exit");
        this.menuGame.setOnAction((event) -> this.gameBtn.fire());
        this.menuExit.setOnAction((event) -> this.exitBtn.fire());
        
        Menu menu = new Menu("Options");  
        menu.getItems().addAll(this.menuGame, this.menuExit);        
        
        this.menuBar = new MenuBar();
        this.menuBar.getMenus().addAll(menu);
       
        footerPane.add(this.menuBar, 0, 0);
    } 
}
