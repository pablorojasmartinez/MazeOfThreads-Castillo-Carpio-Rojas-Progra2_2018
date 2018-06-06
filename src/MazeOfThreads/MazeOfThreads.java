package MazeOfThreads;

import domain.Chronometer;
import domain.Brick;
import domain.SmartCharacter;
import domain.FastCharacter;
import domain.FuriousCharacter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author hansel
 */
public class MazeOfThreads extends Application implements Runnable {

    private TableView table = new TableView();
    private final int width1 = 1350;
    private final int height1 = 720;
    private final int width2 = 1200;
    private final int height2 = 650;
    private int pixel = 70;

    private boolean flag = false;

    private Brick maze[][];

    private Canvas canvas, canvas2;
    private Pane pane;
    private GraphicsContext graphicsContext, gc2;

    private Thread play;

    private FastCharacter player;
    private FuriousCharacter playerFurious;
    private SmartCharacter playerSmart;

    private FastCharacter[] fastList = new FastCharacter[0];
    private FuriousCharacter[] furiousList = new FuriousCharacter[0];
    private SmartCharacter[] smarList = new SmartCharacter[0];

    private TextArea textArea;

    private String matrixType;

    Chronometer chronometer = new Chronometer();

    private FastCharacter[] fastList2 = new FastCharacter[0];
    private FuriousCharacter[] furiousList2 = new FuriousCharacter[0];
    private SmartCharacter[] smarList2 = new SmartCharacter[0];

    int[][] easyMatrix = {{1, 1, 0, 0, 0, 0, 0, 0, 0},
    {0, 1, 0, 0, 0, 0, 0, 0, 0},
    {0, 1, 1, 1, 1, 1, 1, 0, 0},
    {0, 0, 1, 0, 0, 0, 0, 0, 0},
    {0, 0, 1, 0, 0, 0, 0, 0, 0},
    {0, 0, 1, 0, 0, 0, 0, 0, 0},
    {1, 1, 1, 0, 0, 0, 0, 0, 0},
    {0, 0, 1, 1, 1, 1, 1, 0, 0},
    {0, 0, 0, 1, 0, 0, 1, 1, 0},
    {0, 0, 0, 1, 0, 0, 0, 1, 0},
    {0, 0, 1, 1, 0, 0, 0, 1, 0},
    {0, 0, 0, 0, 0, 0, 0, 1, 0},
    {0, 0, 0, 0, 0, 0, 0, 1, 1},
    {0, 0, 0, 0, 0, 0, 0, 0, 1},
    {0, 0, 0, 0, 0, 0, 0, 0, 3}};

    private int[][] mediumMatrix = {
        {1, 1, 0, 0, 0, 0, 1, 0, 0},
        {0, 1, 0, 0, 0, 0, 1, 0, 0},
        {0, 1, 1, 1, 1, 1, 1, 0, 0},
        {0, 0, 1, 0, 0, 1, 0, 0, 0},
        {0, 0, 1, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 1, 1, 0},
        {1, 1, 1, 0, 1, 0, 0, 0, 0},
        {1, 0, 1, 1, 1, 1, 1, 0, 0},
        {1, 0, 0, 1, 0, 0, 1, 1, 0},
        {1, 0, 0, 1, 0, 0, 0, 1, 0},
        {0, 0, 0, 1, 0, 1, 1, 1, 0},
        {0, 1, 1, 1, 0, 1, 0, 1, 0},
        {0, 1, 0, 0, 0, 1, 0, 1, 1},
        {0, 1, 0, 0, 1, 1, 0, 0, 1},
        {0, 0, 0, 0, 3, 0, 0, 0, 3}
    };

    private int[][] hardMatrix = {
        {1, 1, 0, 0, 0, 1, 1, 1, 1},
        {0, 1, 0, 0, 0, 1, 0, 0, 1},
        {0, 1, 1, 1, 1, 1, 1, 0, 1},
        {0, 0, 1, 0, 1, 0, 1, 0, 0},
        {1, 0, 1, 0, 1, 0, 1, 0, 0},
        {1, 0, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 0, 0, 0, 0, 0, 1},
        {0, 0, 1, 1, 1, 1, 1, 0, 0},
        {0, 0, 0, 1, 0, 0, 1, 1, 0},
        {1, 1, 0, 1, 0, 0, 0, 1, 0},
        {0, 1, 1, 1, 0, 1, 1, 1, 0},
        {0, 0, 1, 0, 0, 1, 0, 1, 0},
        {1, 1, 1, 0, 0, 0, 0, 1, 1},
        {1, 0, 1, 1, 1, 0, 0, 0, 1},
        {0, 0, 0, 1, 3, 0, 0, 0, 3}};

    public MazeOfThreads() {
        this.maze = new Brick[15][9];
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Maze of Threads");
        init(primaryStage);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        primaryStage.resizableProperty().set(false);
        primaryStage.show();
    } // start

    public void init(Stage primaryStage) {

        play = new Thread(this);
        play.start();

        canvas = new Canvas(width2, height2);
        canvas2 = new Canvas(width2, height2);

        Button run = new Button("RUN");
        run.relocate(580, 670);

        Button pause = new Button("PAUSE");
        pause.relocate(480, 670);

        Button easy = new Button("EASY");
        easy.relocate(80, 670);

        Button medium = new Button("MEDIUM");
        medium.relocate(180, 670);

        Button hard = new Button("HARD");
        hard.relocate(280, 670);

        Button stop = new Button("STOP");
        stop.relocate(380, 670);

        Label labelAmount = new Label("QUANTITY");
        labelAmount.relocate(1250, 45);

        TextField texfieldAmount = new TextField();
        texfieldAmount.relocate(1210, 70);

        Button buttonPlayer1 = new Button("PLAYER 1");
        buttonPlayer1.relocate(1150, 200);

        Label labelName = new Label("PLAYER'S NAME");
        labelName.relocate(1210, 140);

        TextField text = new TextField();
        text.relocate(1250, 160);

        TextField txt = new TextField();
        txt.relocate(1100, 160);

        Button buttonPlayer2 = new Button("PLAYER 2");
        buttonPlayer2.relocate(1250, 200);

        ObservableList<String> kindOfCharacter = FXCollections.observableArrayList();
        kindOfCharacter.addAll("FAST", "FURIOUS", "SMART");
        ComboBox<String> comboBox = new ComboBox<>(kindOfCharacter);
        comboBox.setPromptText("KIND OF CHARACTER");
        comboBox.relocate(1200, 10);

        buttonPlayer2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int amount = 0;
                amount = Integer.parseInt(texfieldAmount.getText());

                if (comboBox.getValue().equalsIgnoreCase("FAST")) {
                    fastList2 = new FastCharacter[amount];

                } else if (comboBox.getValue().equalsIgnoreCase("SMART")) {
                    smarList2 = new SmartCharacter[amount];

                } else if (comboBox.getValue().equalsIgnoreCase("FURIOUS")) {
                    furiousList2 = new FuriousCharacter[amount];

                }
                texfieldAmount.setText("");
            }
        });

        textArea = new TextArea();
        textArea.relocate(1053, 500);
        textArea.setEditable(false);

        run.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    String name = txt.getText();
                    String name2 = text.getText();

                    if (smarList.length != 0) {

                        for (int i = 0; i < smarList.length; i++) {
                            smarList[i] = new SmartCharacter(getPixelSize(), getBrickMaze());

                            smarList[i].setPlayerName(name);
                            smarList[i].start();
                            flag = true;
                        }
                    }
                    if (furiousList.length != 0) {
                        for (int i = 0; i < furiousList.length; i++) {
                            furiousList[i] = new FuriousCharacter(getPixelSize(), getBrickMaze());
                            furiousList[i].setPlayerName(name);
                            furiousList[i].start();
                            flag = true;
                        }
                    }
                    if (fastList.length != 0) {
                        for (int i = 0; i < fastList.length; i++) {

                            fastList[i] = new FastCharacter(getPixelSize(), getBrickMaze());
                            fastList[i].setPlayerName(name);
                            fastList[i].start();
                            flag = true;

//                        }
                        }
                    }

                    if (smarList2.length != 0) {

                        for (int i = 0; i < smarList2.length; i++) {
                            smarList2[i] = new SmartCharacter(getPixelSize(), getBrickMaze());
                            smarList2[i].setPlayerName(name2);
                            smarList2[i].start();
                            flag = true;
                        }
                    }
                    if (furiousList2.length != 0) {
                        for (int i = 0; i < furiousList2.length; i++) {
                            furiousList2[i] = new FuriousCharacter(getPixelSize(), getBrickMaze());
                            furiousList2[i].setPlayerName(name2);
                            furiousList2[i].start();
                            flag = true;
                        }
                    }
                    if (fastList2.length != 0) {
                        for (int i = 0; i < fastList2.length; i++) {

                            fastList2[i] = new FastCharacter(getPixelSize(), getBrickMaze());
                            fastList2[i].setPlayerName(name2);
                            fastList2[i].start();
                            flag = true;

//                        }
                        }
                    }

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MazeOfThreads.class.getName()).log(Level.SEVERE, null, ex);
                }

                chronometer.setLocation(1200, 10);
                chronometer.initChrono();

            }
        });

        easy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createMaze(pixel, easyMatrix);
                drawMaze(graphicsContext, pixel);
                matrixType = "easy";

            }
        });

        medium.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createMaze(pixel, mediumMatrix);
                drawMaze(graphicsContext, pixel);
                matrixType = "medium";
            }
        });

        hard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                createMaze(pixel, hardMatrix);
                drawMaze(graphicsContext, pixel);
                matrixType = "hard";
            }
        });

        buttonPlayer1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int size = 0;
                int amount = 0;
                amount = Integer.parseInt(texfieldAmount.getText());

                if (comboBox.getValue().equalsIgnoreCase("FAST")) {
                    fastList = new FastCharacter[amount];
                } else if (comboBox.getValue().equalsIgnoreCase("SMART")) {
                    smarList = new SmartCharacter[amount];

                } else if (comboBox.getValue().equalsIgnoreCase("FURIOUS")) {
                    furiousList = new FuriousCharacter[amount];

                }
                texfieldAmount.setText("");

            }
        });

        stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int cont = 0;
                if (cont == 0) {
                    flag = false;
                }
            }
        });
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    play.sleep(5000);

                } catch (InterruptedException ex) {
                    Logger.getLogger(MazeOfThreads.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        graphicsContext = canvas.getGraphicsContext2D();
        gc2 = canvas2.getGraphicsContext2D();

        pane = new Pane();
        pane.getChildren().add(canvas2);
        pane.getChildren().add(canvas);

        this.canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (event.getSource() == canvas) {
                        try {
                            convertMaze1((int) event.getX(), (int) event.getY(), graphicsContext);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MazeOfThreads.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (event.getButton() == MouseButton.SECONDARY) {
                    if (event.getSource() == canvas) {
                        try {
                            convertMaze2((int) event.getX(), (int) event.getY(), graphicsContext);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MazeOfThreads.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        pane.getChildren().add(pause);
        pane.getChildren().add(buttonPlayer2);
        pane.getChildren().add(txt);
        pane.getChildren().add(text);
        pane.getChildren().add(run);
        pane.getChildren().add(medium);
        pane.getChildren().add(easy);
        pane.getChildren().add(hard);
        pane.getChildren().add(labelAmount);
        pane.getChildren().add(texfieldAmount);
        pane.getChildren().add(comboBox);
        pane.getChildren().add(buttonPlayer1);
        pane.getChildren().add(labelName);
        pane.getChildren().add(stop);
        pane.setBackground(Background.EMPTY);
        pane.getChildren().add(textArea);
        Scene scene = new Scene(pane, width1, height1);
        scene.setFill(Color.DARKTURQUOISE);
        primaryStage.setScene(scene);
    } // init

    @Override
    public void run() {
        long start;
        long elapsed;
        long wait;
        int fps = 60;
        long time = 1000 / fps;
        try {
            while (true) {

                start = System.nanoTime();
                elapsed = System.nanoTime() - start;
                wait = time - elapsed / 1000000;
                if (flag) {
                    draw(gc2, pixel);
                }

                Thread.sleep(wait);
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(MazeOfThreads.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MazeOfThreads.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void draw(GraphicsContext gc, int pixelSize) throws InterruptedException, FileNotFoundException {

        gc.clearRect(0, 0, width2, height2);
        for (int i = 0; i < smarList.length; i++) {
            finishSmart(smarList[i]);
            if (smarList[i].isCrash2() == true) {

                gc.setFill(Color.FUCHSIA);

                gc.drawImage(smarList[i].getImage(), smarList[i].getX(), smarList[i].getY(), 30, 30);
                gc.strokeText(smarList[i].getPlayerName(), smarList[i].getX(), smarList[i].getY());
            }
        }
        for (int i = 0; i < furiousList.length; i++) {
            finishFurious(furiousList[i]);
            if (furiousList[i].isCrash2() == true) {
                checkForItem(furiousList[i]);
                gc.setFill(Color.FUCHSIA);

                gc.drawImage(furiousList[i].getImage(), furiousList[i].getX(), furiousList[i].getY(), 30, 30);
                gc.strokeText(furiousList[i].getPlayerName(), furiousList[i].getX(), furiousList[i].getY());
            }
        }
        for (int i = 0; i < fastList.length; i++) {
            finishFast(fastList[i]);
            if (fastList[i].isCrash2() == true) {
                gc.setFill(Color.FUCHSIA);

                gc.drawImage(fastList[i].getImage(), fastList[i].getX(), fastList[i].getY(), 30, 30);
                gc.strokeText(fastList[i].getPlayerName(), fastList[i].getX(), fastList[i].getY());

            }

        }

        for (int i = 0; i < smarList2.length; i++) {
            finishSmart(smarList2[i]);
            if (smarList2[i].isCrash2() == true) {
                gc.drawImage(smarList2[i].getImage(), smarList2[i].getX(), smarList2[i].getY(), 30, 30);

                gc.strokeText(smarList2[i].getPlayerName(), smarList2[i].getX(), smarList2[i].getY());
            }

        }
        for (int i = 0; i < furiousList2.length; i++) {
            finishFurious(furiousList2[i]);
            checkForItem(furiousList2[i]);
            if (furiousList2[i].isCrash2() == true) {
                gc.drawImage(furiousList2[i].getImage(), furiousList2[i].getX(), furiousList2[i].getY(), 30, 30);
                gc.strokeText(furiousList2[i].getPlayerName(), furiousList2[i].getX(), furiousList2[i].getY());
            }

        }
        for (int i = 0; i < fastList2.length; i++) {
            finishFast(fastList2[i]);
            if (fastList2[i].isCrash2() == true) {
                gc.drawImage(fastList2[i].getImage(), fastList2[i].getX(), fastList2[i].getY(), 30, 30);
                gc.strokeText(fastList2[i].getPlayerName(), fastList2[i].getX(), fastList2[i].getY());
            }

        }
    }

    public void finishFast(FastCharacter fast) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (fast.getCurrentBrick().getX() == maze[i][j].getX() && fast.getCurrentBrick().getY() == maze[i][j].getY()) {
                    if (fast.getCurrentBrick().getBrickType() == "start") {
                        if (fast.isCrash2() == true) {
                            textArea.setText(textArea.getText() + "\n" + "Player: " + fast.getPlayerName() + " Thread name: " + fast.getName() + " Time: " + chronometer.getTimeObtained());
                        }
                        fast.stopCrash();
                    }
                }

            }
        }
    }

    public void finishSmart(SmartCharacter smart) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (smart.getCurrentBrick().getX() == maze[i][j].getX() && smart.getCurrentBrick().getY() == maze[i][j].getY()) {
                    if (smart.getCurrentBrick().getBrickType() == "start") {
                        if (smart.isCrash2() == true) {
                            textArea.setText(textArea.getText() + "\n" + "Player: " + smart.getPlayerName() + " Thread name: " + smart.getName() + " Time: " + chronometer.getTimeObtained());
                        }
                        smart.stopCrash();
                    }
                }

            }
        }
    }

    public void finishFurious(FuriousCharacter furious) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (furious.getCurrentBrick().getX() == maze[i][j].getX() && furious.getCurrentBrick().getY() == maze[i][j].getY()) {
                    if (furious.getCurrentBrick().getBrickType() == "start") {
                        if (furious.isCrash2() == true) {
                            textArea.setText(textArea.getText() + "\n" + "Player: " + furious.getPlayerName() + " Thread name: " + furious.getName() + " Time: " + chronometer.getTimeObtained());
                        }
                        furious.stopCrash();
                    }
                }

            }
        }
    }

    public void checkForItem(FuriousCharacter furious) throws FileNotFoundException {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (furious.getCurrentBrick().getX() == maze[i][j].getX() && furious.getCurrentBrick().getY() == maze[i][j].getY()) {
                    if (maze[i][j].getBrickType() == "fruta") {
                        maze[i][j].setBrickType("floor");
                        graphicsContext.setFill(Color.DARKORANGE);
                        graphicsContext.clearRect(i * pixel, j * pixel, pixel, pixel);
                    }

                }

            }
        }
    }

    public Brick getBrickMaze() {
        if (matrixType == "easy") {
            return this.maze[0][0];
        } else if (matrixType == "medium") {

            return getBackMatrix();
        }
        return getBackMatrix();
    }

    public Brick getBackMatrix() {
        int direction = (int) (Math.random() * (3 - 1) + 1);
        if (direction == 1) {
            return this.maze[0][6];
        }
        return this.maze[0][0];
    }

    public void convertMaze2(int x, int y, GraphicsContext gc) throws FileNotFoundException {

        int x1;
        int y2;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (this.maze[i][j].getMouseValue(x, y)) {
                    if (this.maze[i][j].getBrickType().equals("wall")) {
                        this.maze[i][j].setBrickType("fruta");
                        drawItem(gc, pixel, i, j);
                    } else if (this.maze[i][j].getBrickType().equals("floor")) {
                        this.maze[i][j].setBrickType("fruta");
                        drawItem(gc, pixel, i, j);
                    }

                    break;
                }
            }
        }

        lookForFloor();
    }

    public void convertMaze1(int x, int y, GraphicsContext gc) throws FileNotFoundException {
        int x1;
        int y2;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (this.maze[i][j].getMouseValue(x, y)) {
                    if (this.maze[i][j].getBrickType().equals("wall")) {
                        this.maze[i][j].setBrickType("floor");
                        drawItem(gc, pixel, i, j);
                    } else {
                        this.maze[i][j].setBrickType("wall");
                        drawItem(gc, pixel, i, j);
                    }

                    break;
                }
            }
        }

        lookForFloor();
    }

    public int getPixelSize() {
        return this.pixel;
    }

    public void createMaze(int pixel, int[][] matrix) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (matrix[i][j] == 0) {
                    maze[i][j] = new Brick(i, j, pixel, "wall");
                } else if (matrix[i][j] == 2) {

                    maze[i][j] = new Brick(i, j, pixel, "fruta");
                } else if (matrix[i][j] == 3) {

                    maze[i][j] = new Brick(i, j, pixel, "start");
                } else {
                    maze[i][j] = new Brick(i, j, pixel, "floor");
                }
            }
        }
        lookForFloor();
    }

    public void drawItem(GraphicsContext gc, int pixelSize, int x, int y) throws FileNotFoundException {
        Image itemImage = new Image(new FileInputStream("src/Assets/stop.png"));
        if (maze[x][y].getBrickType().equals("wall")) {
            gc.setFill(Color.BLACK);
            gc.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);

        } else if (maze[x][y].getBrickType().equals("fruta")) {
            gc.drawImage(itemImage, x * pixelSize, y * pixelSize, 20, 20);
        } else {
            gc.clearRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
        }

    }

    public void drawMaze(GraphicsContext gc, int pixel) {

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j].getBrickType().equals("wall")) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(i * pixel, j * pixel, pixel, pixel);

                } else if (maze[i][j].getBrickType().equals("fruta")) {
                    gc.setFill(Color.DARKORANGE);
                    gc.fillRect(i * pixel, j * pixel, pixel, pixel);
                } else if (maze[i][j].getBrickType().equals("start")) {
                    gc.setFill(Color.DARKORANGE);
                    gc.fillRect(i * pixel, j * pixel, pixel, pixel);
                } else {
                    gc.clearRect(i * pixel, j * pixel, pixel, pixel);

                }

            }

        }

    }

    private ArrayList<Brick> floorArray(int x, int y) {
        ArrayList<Brick> nextFloor = new ArrayList<>();
        if (x + 1 < maze.length && maze[x + 1][y].getBrickType().equals("floor") || x + 1 < maze.length && maze[x + 1][y].getBrickType().equals("fruta")
                || x + 1 < maze.length && maze[x + 1][y].getBrickType().equals("start")) {

            nextFloor.add(maze[x + 1][y]);

        }
        if (x - 1 >= 0 && maze[x - 1][y].getBrickType().equals("floor") || x - 1 >= 0 && maze[x - 1][y].getBrickType().equals("fruta")
                || x - 1 >= 0 && maze[x - 1][y].getBrickType().equals("start")) {

            nextFloor.add(maze[x - 1][y]);
        }
        if (y + 1 < maze[0].length && maze[x][y + 1].getBrickType().equals("floor") || y + 1 < maze[0].length && maze[x][y + 1].getBrickType().equals("fruta")
                || y + 1 < maze[0].length && maze[x][y + 1].getBrickType().equals("start")) {

            nextFloor.add(maze[x][y + 1]);
        }
        if (y - 1 >= 0 && maze[x][y - 1].getBrickType().equals("floor") || y - 1 >= 0 && maze[x][y - 1].getBrickType().equals("fruta")
                || y - 1 >= 0 && maze[x][y - 1].getBrickType().equals("start")) {

            nextFloor.add(maze[x][y - 1]);
        }

        return nextFloor;
    }

    private void lookForFloor() {
        ArrayList<Brick> nextFloor = new ArrayList<>();
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[0].length; y++) {
                maze[x][y].setBrickArray(floorArray(x, y));

            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    } // main
}
