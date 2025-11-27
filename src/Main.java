import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;

public class Main extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        ImageView titleImage = new ImageView(new Image("file:assets/images/logo.png"));
        titleImage.setFitWidth(500);
        titleImage.setPreserveRatio(true);
        Button startBtn = new Button("START");
        startBtn.setPrefSize(200, 50);
        Button loadBtn = new Button("LOAD");
        loadBtn.setPrefSize(200, 50);
        startBtn.setOnAction(e -> startGame(false));
        loadBtn.setOnAction(e -> loadGame());
        menu.getChildren().addAll(titleImage, startBtn, loadBtn);
        Scene menuScene = new Scene(menu, 1200, 800);
        window.setScene(menuScene);
        window.setTitle("");
        window.show();
    }
    private void startGame(boolean v) {
        Game game = new Game(1200, 800,v);
        StackPane root = new StackPane(game.getCanvas());
        Scene scene = new Scene(root);
        game.setupInput(scene);
        window.setScene(scene);
        game.start();
    }
    private void loadGame() {
        startGame(true);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
