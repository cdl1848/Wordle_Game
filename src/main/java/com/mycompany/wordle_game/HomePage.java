package com.mycompany.wordle_game;

/**
 * @author cdl1848
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage extends Application {

    @Override
    public void start(Stage primaryStage) {

        Text title = new Text("WORDLE");
        title.setFont(Font.font(36));

        // Play button function to our GameScreen
        Button playButton = new Button("Play");
        playButton.setPrefSize(120, 40);

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, playButton);

        Scene homeScene = new Scene(layout, 350, 400);
        
        playButton.setOnAction(e -> {
            GameScreen gameScreen = new GameScreen();
            // Pass homeScene to GameScreen so it can return back to the home page
            gameScreen.show(primaryStage, homeScene);
        });

        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Wordle Home");
        primaryStage.show();
    }
}
