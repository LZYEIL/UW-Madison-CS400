import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Random;

public class ButtonGame extends Application {

    private Random rand = new Random();
    private int score = 0;
    private Label scoreLabel;
    private Button exitButton;

    @Override
    public void start(final Stage stage) {
        BorderPane root = new BorderPane();

        scoreLabel = new Label("Score: 0");
        root.setTop(scoreLabel);

        exitButton = new Button("Exit");
        exitButton.addEventHandler(ActionEvent.ACTION, event -> Platform.exit());
        root.setBottom(exitButton);

        Pane centerPane = new Pane();
        root.setCenter(centerPane);

        Button[] buttons = new Button[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new Button(i == 0 ? "Click me!" : "Click me?");
            centerPane.getChildren().add(buttons[i]);

            final Button currentButton = buttons[i];
            currentButton.addEventHandler(ActionEvent.ACTION, event -> {
                if (currentButton.getText().equals("Click me!")) {
                    score++;
                } else {
                    score--;
                }
                scoreLabel.setText("Score: " + score);
                scrambleButtons(rand, buttons);
                exitButton.requestFocus();
            });
        }

        scrambleButtons(rand, buttons);
        exitButton.requestFocus();

        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("ButtonGame");
        stage.setScene(scene);
        stage.show();
    }

    private void scrambleButtons(Random rand, Button[] buttons) {
        for (Button button : buttons) {
            double x = rand.nextDouble() * 500;
            double y = rand.nextDouble() * 400;
            button.setLayoutX(x);
            button.setLayoutY(y);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

