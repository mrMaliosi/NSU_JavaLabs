package ru.nsu.ccfit.malinovskii.Controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.ccfit.malinovskii.Model.Grid;
import ru.nsu.ccfit.malinovskii.Model.Tetramino.Block;
import ru.nsu.ccfit.malinovskii.Model.Tetramino.Tetramino;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

public class Game extends Application {
    public static Stage stage;
    static GameController gameController;
    public static FXMLLoader loader = new FXMLLoader();

    private Grid[][] grid = new Grid[20][10];
    private Tetramino tetramino;

    Boolean isGameFinished = false;
    Boolean isTetraminoExist = false;
    Boolean isCanceled = false;

    static Thread gameThread;
    Timer timer;

    static String nickname = "Oleg";
    static int score = 0;

    static final Block tetraminoIsReadyFlag = new Block(-1, -1);

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL xmlUrl = getClass().getResource("/ru.nsu.ccfit.malinovskii/views/menu-view.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Game");
        stage.show();
    }

    class doYouWannaPlay extends Thread {
        @Override
        public void run() {
            stage.getScene().setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case LEFT:
                        goDirection('l', -1, 0);
                        break;
                    case RIGHT:
                        goDirection('r',1, 0);
                        break;
                    case Z:
                        goRotation(1);
                        break;
                    case X:
                        goRotation(0);
                        break;
                    case DOWN:
                        goDirection('d', 0, 1);
                        break;
                    case SPACE:
                        goSpace();
                        break;
                }
            });

            gridSet();
            final int[] speed = {300};
            final ScheduledFuture<?>[] futureTask = {null};
            gridFree();
            isGameFinished = false;
            isCanceled = false;
            score = 0;

            while (!isGameFinished){
                if (!isTetraminoExist){
                    tetramino = new Tetramino();
                    if(!gridCheck()){
                        addScore();
                        Platform.runLater(() -> {
                            gameController.endScene();
                        });
                        break;
                    }
                    Platform.runLater(() -> {
                        gameController.gridUpdate(grid);
                    });
                    isTetraminoExist = true;
                }

                timer = new Timer();
                TimerTask tetraminoDown = new TimerTask() {
                    @Override
                    public void run() {
                        if (isTetraminoExist){
                            erasePreviousPosition();
                            tetramino.changePos(0, 1);
                            gridCheck();
                            Platform.runLater(() -> {
                                gameController.gridUpdate(grid);
                            });
                            if (!isTetraminoExist){
                                synchronized (tetraminoIsReadyFlag) {
                                    tetraminoIsReadyFlag.notifyAll();
                                }
                            }
                        } else{
                            try {
                                wait(1);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }

                    public void erasePreviousPosition(){
                        int x;
                        int y;
                        for (int i = 0; i < 4; ++i){
                            x = tetramino.getX(i);
                            y = tetramino.getY(i);
                            System.out.println("x: " + x + " y: " + y);
                            if ((y < 20)&&(grid[y][x].getState() == 2)){
                                grid[y][x].setState(0);
                                grid[y][x].setColor(-1);
                            }
                        }
                    }

                    public Boolean gridCheck(){
                        int x;
                        int y;
                        for (int i = 0; i < 4; ++i){
                            x = tetramino.getX(i);
                            y = tetramino.getY(i);
                            if ((y >= 20) || (grid[y][x].getState() == 1)){
                                isTetraminoExist = false;
                                erasePreviousPosition();
                                tetramino.changePos(0, -1);
                                tetraminoSet();
                                return false;
                            }
                            else{
                                grid[y][x].setState(2);
                                grid[y][x].setColor(tetramino.getColor());
                            }
                        }
                        return true;
                    }

                    public void tetraminoSet(){
                        int x;
                        int y;
                        for (int i = 0; i < 4; ++i){
                            x = tetramino.getX(i);
                            y = tetramino.getY(i);
                            grid[y][x].setState(1);
                            grid[y][x].setColor(tetramino.getColor());
                        }
                    }
                };

                timer.scheduleAtFixedRate(tetraminoDown, speed[0], speed[0]);


                while (isTetraminoExist){
                    synchronized (tetraminoIsReadyFlag) {
                        try {
                            tetraminoIsReadyFlag.wait();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

                if((!isTetraminoExist)&&(!isCanceled)){
                    timer.cancel();
                }
                gridDemolish();
                isCanceled = false;
            }
        }

        public void gridSet(){
            for (int y = 0; y < 20; ++y){
                for (int x = 0; x < 10; ++x){
                    grid[y][x] = new Grid();
                }
            }
        }

        public void gridFree(){
            for (int y = 0; y < 20; ++y){
                for (int x = 0; x < 10; ++x){
                    grid[y][x].setState(0);
                    grid[y][x].setColor(0);
                }
            }
        }

        public Boolean gridCheck(){
            int x;
            int y;
            for (int i = 0; i < 4; ++i){
                x = tetramino.getX(i);
                y = tetramino.getY(i);
                if (grid[y][x].getState() != 1){
                    grid[y][x].setState(2);
                    grid[y][x].setColor(tetramino.getColor());
                }
                else{
                    isGameFinished = true; //Закончить игру
                    return false;
                }
            }
            return true;
        }


        public void gridDemolish(){
            for (int y = 19; y >= 0; --y){
                int flag = 1;
                for (int x = 0; x < 10; ++x){
                    if (grid[y][x].getState() != 1){
                        flag = 0;
                        break;
                    }
                }

                if (flag == 1)
                {
                    score += 100;
                    gridTearDown(y - 1);
                    ++y;
                }
            }

            Platform.runLater(() -> {
                gameController.scoreUpdate();
            });
        }

        public void gridTearDown(int ys){
            for (int y = ys; y >= 0; --y){
                for (int x = 0; x < 10; ++x){
                    grid[y + 1][x].setState(grid[y][x].getState());
                    grid[y + 1][x].setColor(grid[y][x].getColor());
                }
            }
        }

        public void addScore(){
            String fileName = "score.txt";

            try {
                // Читаем данные из файла
                List<String> lines = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                    lines.add(nickname + " " + score);
                }

                // Сортируем данные по убыванию числового значения
                lines.sort((line1, line2) -> {
                    String[] parts1 = line1.split(" ");
                    String[] parts2 = line2.split(" ");
                    int num1 = Integer.parseInt(parts1[1].trim());
                    int num2 = Integer.parseInt(parts2[1].trim());
                    return Integer.compare(num2, num1); // Обратный порядок для сортировки по убыванию
                });

                // Записываем отсортированные данные обратно в файл
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) { // false для обнуления файла
                    for (String line : lines) {
                        writer.println(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void goDirection (char direction, int x, int y) {
            if (isTetraminoExist) {
                erasePreviousPositionX();
                tetramino.changePos(x, y);
                if (gridCheckX(direction)){
                    Platform.runLater(() -> {
                        gameController.gridUpdate(grid);
                    });
                }

            }
        }

        public void goSpace () {
            timer.cancel();
            while (isTetraminoExist) {
                erasePreviousPositionX();
                tetramino.changePos(0, 1);
                gridCheckY();
            }

            isCanceled = true;
            Platform.runLater(() -> {
                gameController.gridUpdate(grid);
            });

            synchronized (tetraminoIsReadyFlag) {
                tetraminoIsReadyFlag.notifyAll();
            }
        }

        public void goRotation (int direction) {
            if (isTetraminoExist) {
                erasePreviousPositionX();
                tetramino.rotate(direction);
                char rotate = '0';
                if (direction == 1){
                    rotate = 'x';
                }
                if (direction == 1){
                    rotate = 'z';
                }
                if (gridCheckX(rotate)){
                    Platform.runLater(() -> {
                        gameController.gridUpdate(grid);
                    });
                }

            }
        }

        public void erasePreviousPositionX(){
            int x;
            int y;
            for (int i = 0; i < 4; ++i){
                x = tetramino.getX(i);
                y = tetramino.getY(i);
                System.out.println("x: " + x + " y: " + y);
                if ((x >= 0) && (x < 10) && (y >= 0) && (y < 20) && (grid[y][x].getState() == 2)){
                    grid[y][x].setState(0);
                    grid[y][x].setColor(-1);
                }
            }
        }

        public Boolean gridCheckX(char direction){
            int x;
            int y;
            for (int i = 0; i < 4; ++i){
                x = tetramino.getX(i);
                y = tetramino.getY(i);
                if ((x < 0) || (x >= 10) || (y < 0) || (y >= 20) || (grid[y][x].getState() == 1)){
                    erasePreviousPositionX();
                    if (direction == 'l'){
                        tetramino.changePos(1, 0);
                    }
                    else if (direction == 'r'){
                        tetramino.changePos(-1, 0);
                    }
                    else if (direction == 'd'){
                        tetramino.changePos(0, -1);
                    }
                    return false;
                }
                else{
                    grid[y][x].setState(2);
                    grid[y][x].setColor(tetramino.getColor());
                }
            }
            return true;
        }

        public Boolean gridCheckY(){
            int x;
            int y;
            for (int i = 0; i < 4; ++i){
                x = tetramino.getX(i);
                y = tetramino.getY(i);
                if ((y >= 20) || (grid[y][x].getState() == 1)){
                    erasePreviousPositionX();
                    tetramino.changePos(0, -1);
                    tetraminoSetY();
                    isTetraminoExist = false;
                    return false;
                }
                else{
                    grid[y][x].setState(2);
                    grid[y][x].setColor(tetramino.getColor());
                }
            }
            return true;
        }

        public void tetraminoSetY(){
            int x;
            int y;
            for (int i = 0; i < 4; ++i){
                x = tetramino.getX(i);
                y = tetramino.getY(i);
                grid[y][x].setState(1);
                grid[y][x].setColor(tetramino.getColor());
            }
        }
    }






    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
