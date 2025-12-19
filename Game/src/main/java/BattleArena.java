import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BattleArena extends Application {

    private Stage window;
    private Pane gameRoot;
    private Scene menuScene, gameScene;
    private Character player1;
    private Character player2;
    private int p1Direction = 1;
    private int p2Direction = -1;
    private List<Projectile> projectiles = new ArrayList<>();
    private Set<KeyCode> activeKeys = new HashSet<>();
    private AnimationTimer gameLoop;
    private ProgressBar p1HealthBar, p2HealthBar;
    private Label winnerLabel;
    private Label p1WeaponLabel, p2WeaponLabel;
    private ComboBox<String> p1ClassSelect, p1WeaponSelect;
    private ComboBox<String> p2ClassSelect, p2WeaponSelect;
    private ComboBox<String> gameModeSelect;
    private boolean singlePlayer = false;
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Battle Arena-THE GAME");
        createMenuScene();
        window.setScene(menuScene);
        window.show();
    }
    //Done by Marwan Tamer 2304015
    private void createMenuScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-image: url('3858.jpg'); -fx-background-size: cover;");
        Label title = new Label("BATTLE ARENA");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        gameModeSelect = new ComboBox<>();
        gameModeSelect.getItems().addAll("Two Players", "Single Player");
        gameModeSelect.setValue("Two Players");
        HBox p1Setup = new HBox(10);
        p1Setup.setAlignment(Pos.CENTER);
        p1ClassSelect = new ComboBox<>();
        p1ClassSelect.getItems().addAll("Knight", "Archer", "Lava Thrower");
        p1ClassSelect.setValue("Archer");
        p1WeaponSelect = new ComboBox<>();
        p1WeaponSelect.getItems().addAll("Bow", "Sword", "Flame Thrower");
        p1WeaponSelect.setValue("Bow");
        p1Setup.getChildren().addAll(new Label("PLAYER 1:"), p1ClassSelect, p1WeaponSelect);
        HBox p2Setup = new HBox(10);
        p2Setup.setAlignment(Pos.CENTER);
        p2ClassSelect = new ComboBox<>();
        p2ClassSelect.getItems().addAll("Knight", "Archer", "Lava Thrower");
        p2ClassSelect.setValue("Knight");
        p2WeaponSelect = new ComboBox<>();
        p2WeaponSelect.getItems().addAll("Bow", "Sword", "Flame Thrower");
        p2WeaponSelect.setValue("Sword");
        p2Setup.getChildren().addAll(new Label("PLAYER 2:"), p2ClassSelect, p2WeaponSelect);
        Button startBtn = new Button("START MATCH");
        startBtn.setStyle("-fx-font-size: 18px; -fx-base: #4CAF50;");
        startBtn.setOnAction(e -> startGame());
        layout.getChildren().addAll(title, gameModeSelect, p1Setup, p2Setup, startBtn);
        menuScene = new Scene(layout, 800, 600);
    }
    //Done by Ahmed Sameh 2304062
    private void startGame() {
        singlePlayer = gameModeSelect.getValue().equals("Single Player");

        gameRoot = new Pane();
        gameRoot.setPrefSize(800, 600);
        gameRoot.setStyle("-fx-background-color: limegreen;");

        Rectangle divider = new Rectangle(398, 0, 4, 600);
        divider.setFill(Color.LIGHTGRAY);
        gameRoot.getChildren().add(divider);

        String p1Class = p1ClassSelect.getValue();
        if (p1Class.equals("Knight")) player1 = new Knight("P1", 100, 300, Color.BLUE);
        else if (p1Class.equals("Lava Thrower")) player1 = new LavaThrower("PLAYER 1", 100, 300, Color.BLUE);
        else player1 = new Archer("PLAYER 1", 100, 300, Color.BLUE);
        player1.equipWeapon(createWeapon(p1WeaponSelect.getValue()));

        String p2Class = p2ClassSelect.getValue();
        if (p2Class.equals("Knight")) player2 = new Knight("P2", 650, 300, Color.RED);
        else if (p2Class.equals("Lava Thrower")) player2 = new LavaThrower("PLAYER 2", 650, 300, Color.RED);
        else player2 = new Archer("PLAYER 2", 650, 300, Color.RED);
        player2.equipWeapon(createWeapon(p2WeaponSelect.getValue()));

        createGameUI();
        gameRoot.getChildren().addAll(player1.getSprite(), player2.getSprite());

        gameScene = new Scene(gameRoot, 800, 600);
        setupInput();
        window.setScene(gameScene);
        gameRoot.requestFocus();

        projectiles.clear();
        p1Direction = 1;
        p2Direction = -1;

        startGameLoop();
    }
//Done by Mohamed Ashraf 2304059
    private Weapons createWeapon(String type) {
        if (type.contains("Sword")) return new Sword();
        if (type.contains("Flame")) return new FlameThrower();
        return new Bow();
    }
//Done by Mohamed Aber 2304260
    private void createGameUI() {
        HBox ui = new HBox(20);
        ui.setAlignment(Pos.CENTER);
        ui.setLayoutX(50);
        ui.setLayoutY(20);
        ui.setPrefWidth(700);

        p1HealthBar = new ProgressBar(1.0);
        p1HealthBar.setStyle("-fx-accent: blue;");
        p2HealthBar = new ProgressBar(1.0);
        p2HealthBar.setStyle("-fx-accent: red;");

        p1WeaponLabel = new Label("W: " + player1.getWeapon().getType());
        p2WeaponLabel = new Label("W: " + player2.getWeapon().getType());

        ui.getChildren().addAll(
                new Label("PLAYER 1 HP:"), p1HealthBar, p1WeaponLabel,
                new Label("     "),
                new Label("PLAYER 2 HP:"), p2HealthBar, p2WeaponLabel
        );

        winnerLabel = new Label("");
        winnerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        winnerLabel.setLayoutX(200);
        winnerLabel.setLayoutY(250);

        gameRoot.getChildren().addAll(ui, winnerLabel);
    }
//Done by Mohamed Elragal 2304246
    private void setupInput() {
        gameScene.setOnKeyPressed(e -> {
            activeKeys.add(e.getCode());
            if (e.getCode() == KeyCode.C) {
                player1.switchWeapon();
                p1WeaponLabel.setText("W: " + player1.getWeapon().getType());
            }
            if (e.getCode() == KeyCode.M) {
                player2.switchWeapon();
                p2WeaponLabel.setText("W: " + player2.getWeapon().getType());
            }
        });
        gameScene.setOnKeyReleased(e -> activeKeys.remove(e.getCode()));
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();
    }

    private void update() {
        if (player1.isDead() || player2.isDead()) return;

        if (activeKeys.contains(KeyCode.W)) player1.move(0, -5);
        if (activeKeys.contains(KeyCode.S)) player1.move(0, 5);
        if (activeKeys.contains(KeyCode.A)) {
            if (player1.getX() > 0) player1.move(-5, 0);
            p1Direction = -1;
            player1.getSprite().setRotate(180);
        }
        if (activeKeys.contains(KeyCode.D)) {
            if (player1.getX() < 760) player1.move(5, 0);
            p1Direction = 1;
            player1.getSprite().setRotate(0);
        }
        if (activeKeys.contains(KeyCode.SPACE)) shoot(player1, p1Direction);

        if (!singlePlayer) {
            if (activeKeys.contains(KeyCode.UP)) player2.move(0, -5);
            if (activeKeys.contains(KeyCode.DOWN)) player2.move(0, 5);
            if (activeKeys.contains(KeyCode.LEFT)) {
                if (player2.getX() > 0) player2.move(-5, 0);
                p2Direction = -1;
                player2.getSprite().setRotate(180);
            }
            if (activeKeys.contains(KeyCode.RIGHT)) {
                if (player2.getX() < 760) player2.move(5, 0);
                p2Direction = 1;
                player2.getSprite().setRotate(0);
            }
            if (activeKeys.contains(KeyCode.ENTER)) shoot(player2, p2Direction);
        } else {
            updateAI();
        }

        updateProjectiles();
    }

    private void updateAI() {
        if (player2.isDead()) return;

        if (player1.getY() < player2.getY()) player2.move(0, -2);
        else if (player1.getY() > player2.getY()) player2.move(0, 2);

        if (player1.getX() < player2.getX()) {
            if (player2.getX() > 0) player2.move(-2, 0);
            p2Direction = -1;
            player2.getSprite().setRotate(180);
        } else {
            if (player2.getX() < 760) player2.move(2, 0);
            p2Direction = 1;
            player2.getSprite().setRotate(0);
        }

        if (Math.random() < 0.02) shoot(player2, p2Direction);
    }

    private void shoot(Character shooter, int direction) {
        if (shooter.getWeapon().canShoot()) {
            double startX = (direction == 1) ? shooter.getX() + 50 : shooter.getX() - 15;
            double startY = shooter.getY() + 30;
            Projectile p = shooter.getWeapon().shoot(startX, startY);
            p.setDirection(direction);
            projectiles.add(p);
            gameRoot.getChildren().add(p.getSprite());
        }
    }
//Done by Marwan Tamer 2304015
    private void updateProjectiles() {
        List<Projectile> toRemove = new ArrayList<>();

        for (Projectile p : projectiles) {
            p.move();

            if (p.getSprite().getBoundsInParent().intersects(player2.getSprite().getBoundsInParent())) {
                player2.takeDamage(p.getDamage());
                toRemove.add(p);
            } else if (p.getSprite().getBoundsInParent().intersects(player1.getSprite().getBoundsInParent())) {
                player1.takeDamage(p.getDamage());
                toRemove.add(p);
            }

            if (p.getX() < 0 || p.getX() > 800) toRemove.add(p);
        }

        for (Projectile p : toRemove) {
            gameRoot.getChildren().remove(p.getSprite());
            projectiles.remove(p);
        }

        p1HealthBar.setProgress((double) player1.getCurrentHealth() / player1.getMaxHealth());
        p2HealthBar.setProgress((double) player2.getCurrentHealth() / player2.getMaxHealth());

        checkWinner();
    }
//done by mohamed elragal 2304246
    private void checkWinner() {
        if (player1.isDead() || player2.isDead()) {
            gameLoop.stop();

            if (player1.isDead()) {
                winnerLabel.setText("PLAYER 2 WINS!");
                winnerLabel.setTextFill(Color.RED);
            } else {
                winnerLabel.setText("PLAYER 1 WINS!");
                winnerLabel.setTextFill(Color.BLUE);
            }

            Button returnBtn = new Button("BACK TO MENU");
            returnBtn.setLayoutX(330);
            returnBtn.setLayoutY(320);
            returnBtn.setStyle("-fx-font-size: 18px; -fx-base: #FFC107;");
            returnBtn.setOnAction(e -> window.setScene(menuScene));

            gameRoot.getChildren().removeIf(node -> node instanceof Button);
            gameRoot.getChildren().add(returnBtn);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
