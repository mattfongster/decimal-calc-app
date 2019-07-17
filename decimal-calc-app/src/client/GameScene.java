package client;

import java.util.ArrayList;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Matt
 */
public final class GameScene extends BaseScene {

    public static final String SUBMIT = "Submit";
    private static final String GENERATE = "Generate New Problem";

    private Label titleLbl;
    private Label problemLbl;
    private Label answerLbl;

    private TextField problemField;
    private TextField answerField;

    //Secret Buttons
    private Button submitBtn;
    private Button generateBtn;

    public GameScene(int width, int height) {
        super(width, height);
    }
    
    private static String randomNumberGenerator() {
        ArrayList<String> operations = new ArrayList<>();
        operations.add("+");
        operations.add("-");
        operations.add("*");
        operations.add("/");
        operations.add("mod");
        Random random = new Random();
        if (random.nextInt(2) == 0) {
            String problem = Math.round(Math.random() * 100) + " " + operations.get(random.nextInt(5)) + " " + Math.round(Math.random() * 100);
            return problem;
        } 
        else {
            String problem = Math.round(Math.random() * 100) + " " + operations.get(random.nextInt(5)) + " " + Math.round(Math.random() * 100)
                    + " " + operations.get(random.nextInt(5)) + " " + Math.round(Math.random() * 100);
            return problem;
        }
    }
    
    public String getProblem() {
        return this.problemField.getText();
    }
    
    public String getAnswer() {
        return this.answerField.getText();
    }
    
    @Override
    public void clear() {
        this.problemField.setText("");
        this.answerField.setText("");
    }

    @Override
    public void setButtonHandler(EventHandler<ActionEvent> buttonHandler) {
        this.submitBtn.setOnAction(buttonHandler);
    }

    @Override
    protected void initHeaderPane(GridPane headerPane) {
        this.titleLbl = new Label("Game");
        headerPane.add(this.titleLbl, 0, 0);
    }

    @Override
    protected void initBodyPane(GridPane bodyPane) {
        this.problemLbl = new Label("Problem:");
        this.problemField = new TextField();
        this.problemField.setEditable(false);
        
        this.answerLbl = new Label("Answer:");
        this.answerField = new TextField();
        
        bodyPane.add(this.problemLbl, 0, 0);
        bodyPane.add(this.problemField, 1, 0);
        bodyPane.add(this.answerLbl, 0, 1);
        bodyPane.add(this.answerField, 1, 1);
    }

    @Override
    protected void initFooterPane(GridPane footerPane) {
        this.submitBtn = new Button(SUBMIT);
        this.generateBtn = new Button(GENERATE);
        
        this.generateBtn.setOnAction(event -> {
            this.problemField.setText(randomNumberGenerator());
        });
        
        footerPane.add(this.generateBtn, 0, 0);
        footerPane.add(this.submitBtn, 0, 1);
    }
}
