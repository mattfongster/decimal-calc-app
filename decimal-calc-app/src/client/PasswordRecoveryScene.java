package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public final class PasswordRecoveryScene extends BaseScene {
    
    public static final String PASSWORD_BACK = "Back";
    public static final String PASSWORD_SUBMIT = "Submit";

    private Label titleLbl;
    
    private Button backBtn;
    private Button submitBtn;

    private Label prompt;

    private TextField tbox;

    public PasswordRecoveryScene(int width, int height) {
        super(width, height);        
    }
    
    public String getUsername() {
        return tbox.getText();
    }
    
    @Override
    public void setButtonHandler(EventHandler<ActionEvent> buttonHandler) {
        this.backBtn.setOnAction(buttonHandler);
        this.submitBtn.setOnAction(buttonHandler);
    }

    @Override
    public void clear() {
        this.tbox.clear();
    }

    @Override
    protected void initHeaderPane(GridPane headerPane) {
        this.titleLbl = new Label("Password Recovery");
        this.titleLbl.setFont(Font.font(this.titleLbl.getFont().getFamily(), 20));
        
        headerPane.add(this.titleLbl, 0, 0);
    }

    @Override
    protected void initBodyPane(GridPane bodyPane) {
        this.tbox = new TextField();

        this.prompt = new Label();
        this.prompt.setText("Enter your username to retrieve your password.");

        bodyPane.add(this.prompt,    0, 0);
        bodyPane.add(this.tbox,      0, 1);
    }

    @Override
    protected void initFooterPane(GridPane footerPane) {
        this.backBtn = new Button(PASSWORD_BACK);
        this.submitBtn = new Button(PASSWORD_SUBMIT);

        footerPane.add(this.submitBtn, 0, 0);
        footerPane.add(this.backBtn,   1, 0);
    }
}
