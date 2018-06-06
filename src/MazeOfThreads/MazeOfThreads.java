package MazeOfThreads;

import domain.Block;
import domain.CharacterSmart;
import domain.CharacterFast;
import domain.CharacterFurious;
import domain.Player;
import java.awt.image.BufferedImage;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hansel
 */
public class MazeOfThreads extends Application implements Runnable {

    private TableView table = new TableView();
    private final int WIDTH = 1350;
    private final int HEIGHT = 720;
    private final int WIDTHM = 1200;
    private final int HEIGHTM = 650;
    private int pixelSize = 70;
    private Block maze[][];
    private BufferedImage image;
    private Canvas canvas, canvas2;
    private Pane pane;
    private GraphicsContext graphicsContext, gc2;
    private boolean val = false;
    private Thread play;
    private CharacterFast player;
    private CharacterFurious playerFurious;
    private CharacterSmart playerSmart;
    private CharacterFast[] fastList = new CharacterFast[0];
    private CharacterFurious[] furiousList = new CharacterFurious[0];
    private CharacterSmart[] smarList = new CharacterSmart[0];
    private TextArea area;
    Chronometer chronometer = new Chronometer();
    private CharacterFast[] fastList2 = new CharacterFast[0];
    private CharacterFurious[] furiousList2 = new CharacterFurious[0];
    private CharacterSmart[] smarList2 = new CharacterSmart[0];
    private Player plerList[] = new Player[2];
    private int aux;
    private String tipoMatriz;
    int[][] matrizEasy = {{1, 1, 0, 0, 0, 0, 0, 0, 0},
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

    private int[][] matrizMedium = {
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

    private int[][] matrizHard = {
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

//        getDificult(2);
        this.maze = new Block[15][9];
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("MAZE RUNNER");
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
        canvas = new Canvas(WIDTHM, HEIGHTM);
        canvas2 = new Canvas(WIDTHM, HEIGHTM);
        Button run = new Button("RUN");
        //  run.setMinSize(100, 30);
     //   run.relocate(480, 670);
        run.relocate(580,670 );

        Button reStar = new Button("PAUSE");
        reStar.relocate(480, 670);

        Button easy = new Button("EASY");
        //easy.setMinSize(100, 30);
        easy.relocate(80, 670);

        Button medium = new Button("MEDIUM");
        // medium.setMinSize(100, 30);
        medium.relocate(180, 670);

        Button hard = new Button("HARD");
        //hard.setMinSize(100, 30);
        hard.relocate(280, 670);

        Button pause = new Button("STOP");
        pause.relocate(380, 670);

        Label labelAmount = new Label("Quantity");
        // labelAmount.setMinSize(100, 45);
        labelAmount.relocate(1250, 45);
        //  labelAmount.setVisible(true);


        TextField texfieldAmount = new TextField();
        //  texfieldAmount.setMinSize(100, 30);
        texfieldAmount.relocate(1210, 70);

        Button accept = new Button("jugador 1");
        // accept.setMinSize(100, 30);
        accept.relocate(1150, 200);

       Label nombre = new Label("name player");
        nombre.relocate(1250, 140);
        TextField txteto = new TextField();
        txteto.relocate(1250, 160);

        TextField txt = new TextField();
        txt.relocate(1100, 160);

        Button bot = new Button("jugador 2");
        bot.relocate(1250, 200);

        ObservableList<String> listType = FXCollections.observableArrayList();//para el combobox
        listType.addAll("Fast", "Furious", "Smart");//opciones del combobox
        ComboBox<String> cbx = new ComboBox<>(listType);//
        cbx.setPromptText("Type of character");
        cbx.relocate(1200, 10);

        bot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int amount = 0;
                amount = Integer.parseInt(texfieldAmount.getText());

                if (cbx.getValue().equalsIgnoreCase("Fast")) {
                    fastList2 = new CharacterFast[amount];

                } else if (cbx.getValue().equalsIgnoreCase("Smart")) {
                    smarList2 = new CharacterSmart[amount];

                } else if (cbx.getValue().equalsIgnoreCase("Furious")) {
                    furiousList2 = new CharacterFurious[amount];

                }
                texfieldAmount.setText("");
            }
        });

        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
//        ObservableList<Chronometer> data = FXCollections.observableArrayList(
//                new Chronometer(chrono.getTime(), chrono.getTime()));
        area = new TextArea();
        area.relocate(1200, 500);

//        table.setEditable(true);
//        table.setMaxSize(160, 160);
//        table.relocate(1200, 500);
//        TableColumn playerName = new TableColumn("Player");
// 
//        playerName.setCellValueFactory(new PropertyValueFactory<Chronometer, String>("player"));
//        playerName.setPrefWidth(80);
//        TableColumn timeUp = new TableColumn("time up");
//        timeUp.setCellValueFactory(new PropertyValueFactory<Chronometer, String>("time up"));
//        timeUp.setPrefWidth(80);
////        table.setItems(data);
//        table.getColumns().addAll(playerName, timeUp);
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        run.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String nombre = txt.getText();
                    String nombre2 = txteto.getText();
                    if (smarList.length != 0) {

                        for (int i = 0; i < smarList.length; i++) {
                            smarList[i] = new CharacterSmart(getPixelSize(), initPlayer());

                            smarList[i].setNombre(nombre);
                            smarList[i].start();
//                                     smarList[i].sleep(1000);
//                            smarList[i].sleep(1000);
                            val = true;
                        }
                    }
                    if (furiousList.length != 0) {
                        for (int i = 0; i < furiousList.length; i++) {
                            furiousList[i] = new CharacterFurious(getPixelSize(), initPlayer());
                            furiousList[i].setNombre(nombre);
                            furiousList[i].start();
                            val = true;
                        }
                    }
                    if (fastList.length != 0) {
                        for (int i = 0; i < fastList.length; i++) {

                            fastList[i] = new CharacterFast(getPixelSize(), initPlayer());
                            fastList[i].setNombre(nombre);
//                                        if(verificaColicion(fastList[i],i)==false){     
                            fastList[i].start();
                            val = true;

//                        }
                        }
                    }

                    if (smarList2.length != 0) {

                        for (int i = 0; i < smarList2.length; i++) {
                            smarList2[i] = new CharacterSmart(getPixelSize(), initPlayer());
                            smarList2[i].setNombre(nombre2);
                            smarList2[i].start();
//                smarList2[i].n;
                            val = true;
                        }
                    }
                    if (furiousList2.length != 0) {
                        for (int i = 0; i < furiousList2.length; i++) {
                            furiousList2[i] = new CharacterFurious(getPixelSize(), initPlayer());
                            furiousList2[i].setNombre(nombre2);
                            furiousList2[i].start();
                            val = true;
                        }
                    }
                    if (fastList2.length != 0) {
                        for (int i = 0; i < fastList2.length; i++) {

                            fastList2[i] = new CharacterFast(getPixelSize(), initPlayer());
//                                        if(verificaColicion(fastList[i],i)==false){    
                            fastList2[i].setNombre(nombre2);
                            fastList2[i].start();
                            val = true;

//                        }
                        }
                    }

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MazeOfThreads.class.getName()).log(Level.SEVERE, null, ex);
                }

//                chronometer.setVisible(true);
                chronometer.setLocation(1200, 10);
                chronometer.initChrono();
            }
        });

        easy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                pixelSize = 120;
                createMaze(pixelSize, matrizEasy);
                drawMaze(graphicsContext, pixelSize);
                tipoMatriz = "easy";

            }
        });

        medium.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createMaze(pixelSize, matrizMedium);
                drawMaze(graphicsContext, pixelSize);
                tipoMatriz = "medium";
            }
        });

        hard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                createMaze(pixelSize, matrizHard);
                drawMaze(graphicsContext, pixelSize);
                tipoMatriz = "hard";
            }
        });

        accept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int size = 0;
                int amount = 0;
                amount = Integer.parseInt(texfieldAmount.getText());

                if (cbx.getValue().equalsIgnoreCase("Fast")) {
                    fastList = new CharacterFast[amount];
                } else if (cbx.getValue().equalsIgnoreCase("Smart")) {
                    smarList = new CharacterSmart[amount];

                } else if (cbx.getValue().equalsIgnoreCase("Furious")) {
                    furiousList = new CharacterFurious[amount];

                }
                texfieldAmount.setText("");

            }
        });

        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                boolean pau=true;
                int cont = 0;
                if (cont == 0) {
//                    for(int i=0;i<smarList.length;i++){
//                    
//                        smarList[i].parar();
                    val = false;
//                        smarList[i].parar2();
                    //   cont=1;

//                    }
                }
            }
        });
        reStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //                   for(int i=0;i<smarList.length;i++){
//
//                 smarList[i].seguir();
//                    val=true;
//                
////               smarList[i].seguir2();
                    play.sleep(5000);

//                }
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
                            changeType((int) event.getX(), (int) event.getY(), graphicsContext);
                            ///  printType((int) event.getX(), (int) event.getY());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MazeOfThreads.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (event.getButton() == MouseButton.SECONDARY) {
                    if (event.getSource() == canvas) {
                        try {
                            changeType2((int) event.getX(), (int) event.getY(), graphicsContext);
                            //   printType((int) event.getX(), (int) event.getY());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MazeOfThreads.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        pane.getChildren().add(reStar);
        pane.getChildren().add(bot);
        pane.getChildren().add(txt);
        pane.getChildren().add(txteto);
        pane.getChildren().add(run);
        pane.getChildren().add(medium);
        pane.getChildren().add(easy);
        pane.getChildren().add(hard);
        pane.getChildren().add(labelAmount);
        pane.getChildren().add(texfieldAmount);
        pane.getChildren().add(cbx);
        pane.getChildren().add(accept);
        pane.getChildren().add(nombre);
        pane.getChildren().add(pause);
        pane.setBackground(Background.EMPTY);
        pane.getChildren().add(area);
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
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
                if (val) {
                    draw(gc2, pixelSize);
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

        gc.clearRect(0, 0, WIDTHM, HEIGHTM);
        for (int i = 0; i < smarList.length; i++) {
//            if(smarList[i])
            metaSmartt(smarList[i]);
            if (smarList[i].isCrash2() == true) {

                gc.setFill(Color.FUCHSIA);

                gc.drawImage(smarList[i].getImage(), smarList[i].getX(), smarList[i].getY(), 30, 30);
                gc.strokeText(smarList[i].getNombre(), smarList[i].getX(), smarList[i].getY());
                // gc.fillOval(smarList[i].getX(), smarList[i].getY(), 10, 10);
            }
        }
        for (int i = 0; i < furiousList.length; i++) {
            metaFurious(furiousList[i]);
            if (furiousList[i].isCrash2() == true) {
                saberFruta(furiousList[i]);
                gc.setFill(Color.FUCHSIA);

                gc.drawImage(furiousList[i].getImage(), furiousList[i].getX(), furiousList[i].getY(), 30, 30);
                gc.strokeText(furiousList[i].getNombre(), furiousList[i].getX(), furiousList[i].getY());
                // gc.fillOval(furiousList[i].getX(), furiousList[i].getY(), 10, 10);
            }
        }
        for (int i = 0; i < fastList.length; i++) {
//                        if(verificaColicion(fastList[i],i)==false){
            metaFast(fastList[i]);
            if (fastList[i].isCrash2() == true) {
                gc.setFill(Color.FUCHSIA);

                gc.drawImage(fastList[i].getImage(), fastList[i].getX(), fastList[i].getY(), 30, 30);
                gc.strokeText(fastList[i].getNombre(), fastList[i].getX(), fastList[i].getY());
//                gc.fillOval(fastList[i].getX(), fastList[i].getY(), 10, 10);

            }

        }

//           gc.clearRect(0, 0, WIDTHM, HEIGHTM);
        for (int i = 0; i < smarList2.length; i++) {
            metaSmartt(smarList2[i]);
            if (smarList2[i].isCrash2() == true) {

                gc.drawImage(smarList2[i].getImage(), smarList2[i].getX(), smarList2[i].getY(), 30, 30);
                gc.strokeText(smarList2[i].getNombre(), smarList2[i].getX(), smarList2[i].getY());
            }

        }
        for (int i = 0; i < furiousList2.length; i++) {
            metaFurious(furiousList2[i]);
            saberFruta(furiousList2[i]);
            if (furiousList2[i].isCrash2() == true) {
                gc.drawImage(furiousList2[i].getImage(), furiousList2[i].getX(), furiousList2[i].getY(), 30, 30);
                gc.strokeText(furiousList2[i].getNombre(), furiousList2[i].getX(), furiousList2[i].getY());
            }

        }
        for (int i = 0; i < fastList2.length; i++) {
//                        if(verificaColicion(fastList[i],i)==false){
            metaFast(fastList2[i]);
            if (fastList2[i].isCrash2() == true) {
                gc.drawImage(fastList2[i].getImage(), fastList2[i].getX(), fastList2[i].getY(), 30, 30);
                gc.strokeText(fastList2[i].getNombre(), fastList2[i].getX(), fastList2[i].getY());
            }

        }
    }

    public boolean verificaColicion(CharacterFast character, int num) {
        for (int i = 0; i < fastList.length; i++) {
            if (num != i || num != 0) {
                if (character.getX() == fastList[i].getX() && character.getY() == fastList[i].getY()) {

                    return true;

                }
            }
        }
        return false;
    }

    public void metaFast(CharacterFast fast) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (fast.getCurrentBlock().getX() == maze[i][j].getX() && fast.getCurrentBlock().getY() == maze[i][j].getY()) {
                    if (fast.getCurrentBlock().getType() == "start") {
                        if (fast.isCrash2() == true) {
                            area.setText(area.getText() + "\n" + fast.getNombre() + chronometer.getTimeObtained());
                        }
                        fast.parar();
//                        area.setText(area.getText() + "\n" + fast.getNombre());
//                           fast.setCrash2(false);
                    }
                }

            }
        }
    }

    public void metaSmartt(CharacterSmart smart) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (smart.getCurrentBlock().getX() == maze[i][j].getX() && smart.getCurrentBlock().getY() == maze[i][j].getY()) {
                    if (smart.getCurrentBlock().getType() == "start") {
//                        val = false;
                        if (smart.isCrash2() == true) {
                            area.setText(area.getText() + "\n" + smart.getNombre() + chronometer.getTimeObtained());
                        }
                        smart.parar();

//                        smart.setCrash2(false);
                    }
                }

            }
        }
    }

    public void metaFurious(CharacterFurious furious) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (furious.getCurrentBlock().getX() == maze[i][j].getX() && furious.getCurrentBlock().getY() == maze[i][j].getY()) {
                    if (furious.getCurrentBlock().getType() == "start") {
//                        furious.setCrash2(false);
                        if (furious.isCrash2() == true) {
                            area.setText(area.getText() + "\n" + furious.getNombre() + chronometer.getTimeObtained());
                        }
                        furious.parar();
//                        area.setText(area.getText() + "\n" + furious.getNombre());
                    }
                }

            }
        }
    }

    public void saberFruta(CharacterFurious furious) throws FileNotFoundException {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (furious.getCurrentBlock().getX() == maze[i][j].getX() && furious.getCurrentBlock().getY() == maze[i][j].getY()) {
                    if (maze[i][j].getType() == "fruta") {
                        maze[i][j].setType("floor");
//                        drawCuadro(gc2, pixelSize, maze[i][j].getX(), maze[i][j].getY());
                        graphicsContext.setFill(Color.DARKORANGE);
                        // gc2.fillRect(i * pixelSize, j* pixelSize, pixelSize, pixelSize);
                        graphicsContext.clearRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
                    }

                }

            }
        }
    }

    public Block initPlayer() {
        if (tipoMatriz == "easy") {
            return this.maze[0][0];
        } else if (tipoMatriz == "medium") {

            return devolver();
        }
        return devolver();
    }

    public Block devolver() {
        int direction = (int) (Math.random() * (3 - 1) + 1);
        if (direction == 1) {
            return this.maze[0][6];
        }
        return this.maze[0][0];
    }

    public void changeType2(int x, int y, GraphicsContext gc) throws FileNotFoundException {

        int x1;
        int y2;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (this.maze[i][j].pressMouse(x, y)) {
                    if (this.maze[i][j].getType().equals("wall")) {
                        this.maze[i][j].setType("fruta");
                        drawCuadro(gc, pixelSize, i, j);
                    } else if (this.maze[i][j].getType().equals("floor")) {
                        this.maze[i][j].setType("fruta");
                        drawCuadro(gc, pixelSize, i, j);
                    }

                    break;
                }
            }
        }

        searchNewRoads();
    }

    public void changeType(int x, int y, GraphicsContext gc) throws FileNotFoundException {
        int x1;
        int y2;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (this.maze[i][j].pressMouse(x, y)) {
                    if (this.maze[i][j].getType().equals("wall")) {
                        this.maze[i][j].setType("floor");
                        drawCuadro(gc, pixelSize, i, j);
                    } else {
                        this.maze[i][j].setType("wall");
                        drawCuadro(gc, pixelSize, i, j);
                    }

                    break;
                }
            }
        }

        searchNewRoads();
    }

    public int getPixelSize() {
        return this.pixelSize;
    }

    public void printType(int x, int y) {
        for (int f = 0; f < maze.length; f++) {
            for (int c = 0; c < maze[0].length; c++) {
                if (maze[f][c].pressMouse(x, y)) {
                    System.out.println(maze[f][c].getType());
                }
            }
        }
    }

    public void createMaze(int pixelSize, int[][] matriz) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (matriz[i][j] == 0) {
                    maze[i][j] = new Block(i, j, pixelSize, "wall");
                } else if (matriz[i][j] == 2) {

                    maze[i][j] = new Block(i, j, pixelSize, "fruta");
                } else if (matriz[i][j] == 3) {

                    maze[i][j] = new Block(i, j, pixelSize, "start");
                } else {
                    maze[i][j] = new Block(i, j, pixelSize, "floor");
                }
            }
        }
        searchNewRoads();
    }

    public void drawCuadro(GraphicsContext gc, int pixelSize, int x, int y) throws FileNotFoundException {
        Image imagenFruta = new Image(new FileInputStream("src/Assets/stop.png"));
        if (maze[x][y].getType().equals("wall")) {
            gc.setFill(Color.BLACK);
            gc.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);

        } else if (maze[x][y].getType().equals("fruta")) {
            gc.drawImage(imagenFruta, x * pixelSize, y * pixelSize, 20, 20);
        } else {
            gc.clearRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
        }

    }

    public void drawMaze(GraphicsContext gc, int pixelSize) {

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j].getType().equals("wall")) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);

                } else if (maze[i][j].getType().equals("fruta")) {
                    gc.setFill(Color.DARKORANGE);
                    gc.fillRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
                } else if (maze[i][j].getType().equals("start")) {
                    gc.setFill(Color.DARKORANGE);
                    gc.fillRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
                } else {
                    gc.clearRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);

                }

            }

        }

    }

    /*comopara los bloques de al rededor de donde esta el personaje y guarda en el array si es un camino*/
    private ArrayList<Block> ArrayCamino(int x, int y) {
        ArrayList<Block> next = new ArrayList<>();
        /*maze.length para no comparar una posicion que no exista y la otra comparacion para guardar en el arrays todo los bloques de tipo
         floor */
        if (x + 1 < maze.length && maze[x + 1][y].getType().equals("floor") || x + 1 < maze.length && maze[x + 1][y].getType().equals("fruta")
                || x + 1 < maze.length && maze[x + 1][y].getType().equals("start")) {

            next.add(maze[x + 1][y]);

        }
        if (x - 1 >= 0 && maze[x - 1][y].getType().equals("floor") || x - 1 >= 0 && maze[x - 1][y].getType().equals("fruta")
                || x - 1 >= 0 && maze[x - 1][y].getType().equals("start")) {

            next.add(maze[x - 1][y]);
        }
        if (y + 1 < maze[0].length && maze[x][y + 1].getType().equals("floor") || y + 1 < maze[0].length && maze[x][y + 1].getType().equals("fruta")
                || y + 1 < maze[0].length && maze[x][y + 1].getType().equals("start")) {

            next.add(maze[x][y + 1]);
        }
        if (y - 1 >= 0 && maze[x][y - 1].getType().equals("floor") || y - 1 >= 0 && maze[x][y - 1].getType().equals("fruta")
                || y - 1 >= 0 && maze[x][y - 1].getType().equals("start")) {

            next.add(maze[x][y - 1]);
        }

        return next;
    }

    private void searchNewRoads() {
        ArrayList<Block> next = new ArrayList<>();
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[0].length; y++) {
                maze[x][y].setNext(ArrayCamino(x, y));

            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    } // main
}
