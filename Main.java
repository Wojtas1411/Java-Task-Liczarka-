package sample;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Random;


class Menu{
    private Scene current_screen;
    private Stage tmp_stage;
    private DataInput myData;
    private Highscores myHighscores;
    public void loadScene(){
        VBox vBox = new VBox();
        vBox.setPrefWidth(100);

        Button btn1 = new Button("Singleplayer");
        btn1.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
            myData.setNum_of_players(1);
            myData.setQuick(false);
            myData.loadScene();
            current_screen = myData.getCurrent_scene();
            tmp_stage.setScene(current_screen);
        });
        Button btn2 = new Button("Multiplayer");
        btn2.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
            myData.setNum_of_players(2);
            myData.setQuick(false);
            myData.loadScene();
            current_screen = myData.getCurrent_scene();
            tmp_stage.setScene(current_screen);
        });
        Button btn3 = new Button("Quick Game");
        btn3.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
            myData.setNum_of_players(1);
            myData.setQuick(true);
            myData.loadScene();
            current_screen = myData.getCurrent_scene();
            tmp_stage.setScene(current_screen);
        });
        Button btn4 = new Button("Highscores");
        btn4.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
            myHighscores.loadScene();
            current_screen = myHighscores.getCurrent_scene();
            tmp_stage.setScene(current_screen);
        });
        Button btn5 = new Button("Exit");
        btn5.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
            System.exit(0);
        });

        btn1.setMinWidth(vBox.getPrefWidth());
        btn2.setMinWidth(vBox.getPrefWidth());
        btn3.setMinWidth(vBox.getPrefWidth());
        btn4.setMinWidth(vBox.getPrefWidth());
        btn5.setMinWidth(vBox.getPrefWidth());

        vBox.getChildren().addAll(btn1, btn2, btn3, btn4, btn5);
        GridPane myGrid = new GridPane();
        myGrid.setMinSize(500,500);
        myGrid.setPadding(new Insets(10,10,10,10));
        myGrid.setVgap(5);
        myGrid.setHgap(5);
        myGrid.setAlignment(Pos.CENTER);

        myGrid.add(vBox,0,0);

        current_screen = new Scene(myGrid,500,500);
    }
    public Scene getScene(){
        return current_screen;
    }
    Menu(Stage tmp_stage){
        this.tmp_stage = tmp_stage;
    }
    public void setNext(DataInput myData,Highscores myHighscores){
        this.myData = myData;
        this.myHighscores = myHighscores;
    }
}
class DataInput{
    private Stage tmp_stage;
    private int num_of_players;
    private int current_player;
    private Scene current_scene;
    private Game mygame;
    private boolean isQuick;
    private String players[];
    private int levels[];
    public void loadScene(){
        if(current_player==0){
            players = new String[num_of_players];
            levels = new int[num_of_players];
            for(int i=0;i<num_of_players;i++){
                levels[i]=1;
                players[i] = "";
            }
        }



        GridPane myGrid = new GridPane();
        myGrid.setMinSize(500,500);
        myGrid.setPadding(new Insets(10,10,10,10));
        myGrid.setVgap(5);
        myGrid.setHgap(5);
        myGrid.setAlignment(Pos.CENTER);

        Text mainLabel = new Text("Player "+String.valueOf(current_player+1));

        myGrid.add(mainLabel,0,0);

        Text nameLabel = new Text("Name:");
        TextField nameInput = new TextField();

        myGrid.add(nameLabel,0,1);
        myGrid.add(nameInput,1,1);

        ToggleGroup myGroup = new ToggleGroup();
        Text rbLabel = new Text("Choose difficulty");

        RadioButton rb1 = new RadioButton("Level 1");
        rb1.setSelected(true);
        rb1.setToggleGroup(myGroup);
        RadioButton rb2 = new RadioButton("Level 2");
        rb2.setToggleGroup(myGroup);
        RadioButton rb3 = new RadioButton("Level 3");
        rb3.setToggleGroup(myGroup);
        RadioButton rb4 = new RadioButton("Level 4");
        rb4.setToggleGroup(myGroup);

        myGrid.add(rbLabel,0,2);
        myGrid.add(rb1,0,3);
        myGrid.add(rb2,1,3);
        myGrid.add(rb3,0,4);
        myGrid.add(rb4,1,4);

        Button go_on = new Button("Start");

        myGrid.add(go_on,1,5);
        go_on.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
            if(current_player==0)System.out.println(levels[0]);
            players[current_player] = nameInput.getText();
            if(nameInput.getText().equals(""))players[current_player] = "Player "+String.valueOf((current_player+1));
            if(rb1.isSelected())levels[current_player] = 1;
            if(rb2.isSelected())levels[current_player] = 2;
            if(rb3.isSelected())levels[current_player] = 3;
            if(rb4.isSelected())levels[current_player] = 4;

            if(current_player<num_of_players-1){
                current_player++;
                this.loadScene();
                tmp_stage.setScene(current_scene);
            }
            else{
                mygame.setNum_of_players(num_of_players);
                mygame.setLevels(levels);
                mygame.setPlayers(players);
                mygame.setQuick(isQuick);
                mygame.loadScene();
                current_scene = mygame.getCurrent_scene();
                tmp_stage.setScene(current_scene);
            }
        });
        current_scene = new Scene(myGrid,500,500);
    }

    public Scene getCurrent_scene(){
        return current_scene;
    }

    DataInput(Stage tmp_stage,Game mygame){
        this.tmp_stage = tmp_stage;
        this.mygame = mygame;
    }

    public void setNum_of_players(int num_of_players){
        this.num_of_players = num_of_players;
    }

    public void setQuick(boolean isQuick){
        this.isQuick = isQuick;
    }

}
class Game  {
    private Stage tmp_stage;
    private int num_of_players;
    private int current_player;
    private Scene current_scene;
    private Score myScore;
    private String players[];
    private int levels[];
    private int current_question;
    private int goodAnswears[];
    private int score[];
    private boolean isQuick;
    private Circle kropka;
    private long timeStart;
    private long timeNow;
    private long timeMax = 180000;
    private GridPane myGrid;

    Game(Stage tmp_stage,Score myScore){
        this.tmp_stage = tmp_stage;
        this.myScore = myScore;
    }

    public void loadScene(){
        int tmpAnswear = 0;
        if(current_question == 0 && current_player == 0){
            goodAnswears = new int[num_of_players];
            score = new int[num_of_players];
            for(int i=0;i<num_of_players;i++){
                goodAnswears[i] = 0;
                score[i] = 0;
            }
            kropka = new Circle();
            kropka.setRadius(5);
            kropka.setVisible(false);
            if(isQuick){
                timeStart = System.currentTimeMillis();
            }
        }
        Random random = new Random();
        int n1 = random.nextInt(21);
        int n2 = random.nextInt(21);
        String tmp = "";
        int num;
        if(levels[current_player]==1){
            tmpAnswear = n1+n2;
            tmp = String.valueOf(n1)+"+"+String.valueOf(n2)+"=";
        }
        else{
            num = random.nextInt(levels[current_player])+1;
            if(num == 1){
                tmpAnswear = n1+n2;
                tmp = String.valueOf(n1)+"+"+String.valueOf(n2)+"=";
            }
            else if(num == 2){
                tmpAnswear = n1-n2;
                tmp = String.valueOf(n1)+"-"+String.valueOf(n2)+"=";
            }
            else if(num == 3){
                tmpAnswear = n1*n2;
                tmp = String.valueOf(n1)+"*"+String.valueOf(n2)+"=";
            }
            else if(num == 4){
                if(n1 == 0){
                    n1 = random.nextInt(20)+1;
                }
                tmpAnswear = n2;
                tmp = String.valueOf(n1*n2)+"/"+String.valueOf(n1)+"=";
            }
        }
        int tmp2 = tmpAnswear;

        myGrid = new GridPane();
        myGrid.setMinSize(500,500);
        myGrid.setPadding(new Insets(10,10,10,10));
        myGrid.setVgap(5);
        myGrid.setHgap(5);
        myGrid.setAlignment(Pos.CENTER);


        Text Header1 = new Text(players[0]+"\tScore: "+String.valueOf(score[0])+" "+String.valueOf(goodAnswears[0])+"/25");
        if(num_of_players>1)Header1.setText(players[0]+"\tScore: "+String.valueOf(score[0])+" "+String.valueOf(goodAnswears[0])+"/25\n"+players[1]+"\tScore: "+String.valueOf(score[1])+" "+String.valueOf(goodAnswears[1])+"/25");
        Text Header2 = new Text(players[current_player]+" Question "+String.valueOf(current_question+1)+"/25");
        Text equation = new Text(tmp);
        TextField answer = new TextField();
        Button next = new Button("Next");
        Text clock = new Text("0:00");
        clock.setVisible(isQuick);

        timeNow = System.currentTimeMillis();
        Task<Void> task = new Task<Void>() {
            {
                updateMessage("Initial text");
            }
            @Override
            public Void call() throws Exception {
                int i = (int)((timeNow-timeStart)/1000);
                while (true) {
                    if(i%60>=10)updateMessage(String.valueOf(i/60)+":"+String.valueOf(i%60));
                    else updateMessage(String.valueOf(i/60)+":0"+String.valueOf(i%60));
                    i++;
                    Thread.sleep(1000);
                }
            }
        };
        clock.textProperty().bind(task.messageProperty());
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        next.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
            if(isQuick){
                timeNow = System.currentTimeMillis();
                if(timeNow-timeStart>timeMax)current_question = 24;
            }

            if(answer.getText().equals("")){
                score[current_player]+=0;
                kropka.setVisible(false);
            }
            else if(Integer.valueOf(answer.getText()) == tmp2){
                goodAnswears[current_player]++;
                score[current_player]++;
                kropka.setFill(Color.GREEN);
                kropka.setVisible(true);
            }
            else if(Integer.valueOf(answer.getText()) != tmp2){
                score[current_player]--;
                kropka.setFill(Color.RED);
                kropka.setVisible(true);
            }

            if(current_player<num_of_players-1){
                current_player++;
                this.loadScene();
                tmp_stage.setScene(current_scene);
            }
            else if(current_player == num_of_players-1 && current_question<24){
                current_player = 0;
                current_question++;
                this.loadScene();
                tmp_stage.setScene(current_scene);
            }
            else if(current_player == num_of_players-1 && current_question == 24){
                myScore.setPlayers(players);
                myScore.setNum_of_players(num_of_players);
                myScore.setLevels(levels);
                myScore.setGoodAnswears(goodAnswears);
                myScore.setScores(score);
                if(isQuick){
                    myScore.setQuick(true);
                    myScore.setTime(clock.getText());
                    System.out.println(clock.getText());
                }
                else myScore.setQuick(false);
                myScore.loadScene();

                current_scene=myScore.getCurrent_scene();
                tmp_stage.setScene(current_scene);
            }
        });

        myGrid.add(Header1,0,0);
        myGrid.add(Header2,1,1);
        myGrid.add(equation,1,2);
        myGrid.add(answer,2,2);
        myGrid.add(next,2,3);
        myGrid.add(kropka,3,4);

        myGrid.add(clock,3,0);



        current_scene = new Scene(myGrid,500,500);
    }

    public Scene getCurrent_scene(){
        return current_scene;
    }

    public void setLevels(int[] levels) {
        this.levels = levels;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public void setNum_of_players(int num_of_players) {
        this.num_of_players = num_of_players;
        current_player = 0;
        current_question = 0;
    }

    public void setQuick(boolean quick) {
        isQuick = quick;
    }
}
class Score{
    private Stage tmp_stage;
    private int num_of_players;
    private Scene current_scene;
    private Menu myMenu;
    private Highscores myhighscores;
    private int levels[];
    private String players[];
    private int scores[];
    private int goodAnswears[];
    private String time;
    private boolean isQuick;

    public void loadScene(){
        GridPane myGrid = new GridPane();
        myGrid.setMinSize(500,500);
        myGrid.setPadding(new Insets(10,10,10,10));
        myGrid.setVgap(5);
        myGrid.setHgap(5);
        myGrid.setAlignment(Pos.CENTER);

        Text nameLabel = new Text("Name");
        Text levelLabel = new Text("Level");
        Text goodLabel = new Text("Good Answears");
        Text scoreLabel = new Text("Score");
        myGrid.add(nameLabel,0,0);
        myGrid.add(levelLabel,1,0);
        myGrid.add(goodLabel,2,0);
        myGrid.add(scoreLabel,3,0);
        if(isQuick){
            Text timeLabel= new Text("Time");
            myGrid.add(timeLabel,4,0);
        }

        for(int i=1;i<=num_of_players;i++){
            nameLabel = new Text(players[i-1]);
            levelLabel = new Text(String.valueOf(levels[i-1]));
            goodLabel = new Text(String.valueOf(goodAnswears[i-1])+"/25");
            scoreLabel = new Text(String.valueOf(scores[i-1]));
            myhighscores.addScore(levels[i-1],scores[i-1],players[i-1]);
            myGrid.add(nameLabel,0,i);
            myGrid.add(levelLabel,1,i);
            myGrid.add(goodLabel,2,i);
            myGrid.add(scoreLabel,3,i);
            if(isQuick){
                Text timeLabel2= new Text(time);
                myGrid.add(timeLabel2,4,i);
            }

        }

        Button back = new Button("Menu");
        back.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
            myMenu.loadScene();
            current_scene = myMenu.getScene();
            tmp_stage.setScene(current_scene);
        });
        myGrid.add(back,4,4);

        current_scene = new Scene(myGrid,500,500);
    }

    Score(Stage tmp_stage,Menu myMenu,Highscores myhighscores){
        this.tmp_stage = tmp_stage;
        this.myMenu = myMenu;
        this.myhighscores = myhighscores;
    }

    public void setNum_of_players(int num_of_players) {
        this.num_of_players = num_of_players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public void setLevels(int[] levels) {
        this.levels = levels;
    }

    public void setGoodAnswears(int[] goodAnswears) {
        this.goodAnswears = goodAnswears;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public void setTime(String times) {
        this.time = times;
    }

    public void setQuick(boolean quick) {
        isQuick = quick;
    }

    public Scene getCurrent_scene() {
        return current_scene;
    }
}
class Highscores{
    private Stage tmp_stage;
    private Scene current_scene;
    private Menu myMenu;
    private int scores[];
    private String players[];
    private int levels[];

    public Scene getCurrent_scene(){
        return current_scene;
    }

    public void loadScene(){
        GridPane myGrid = new GridPane();
        myGrid.setMinSize(500,500);
        myGrid.setPadding(new Insets(10,10,10,10));
        myGrid.setVgap(5);
        myGrid.setHgap(5);
        myGrid.setAlignment(Pos.CENTER);

        Text labelLevel = new Text("Level");
        Text labelName = new Text("Name");
        Text labelScore = new Text("Score");

        myGrid.add(labelLevel,0,0);
        myGrid.add(labelName,1,0);
        myGrid.add(labelScore,2,0);

        for(int i=1;i<5;i++){
            labelLevel = new Text(String.valueOf(levels[i-1]));
            labelName = new Text(players[i-1]);
            labelScore = new Text(String.valueOf(scores[i-1]));
            myGrid.add(labelLevel,0,i);
            myGrid.add(labelName,1,i);
            myGrid.add(labelScore,2,i);
        }
        Button menu = new Button("Go Back");
        menu.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
            myMenu.loadScene();
            current_scene = myMenu.getScene();
            tmp_stage.setScene(current_scene);
        });
        myGrid.add(menu,2,6);

        Button clear = new Button("Clear");
        clear.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
            players = new String[]{"","","",""};
            scores = new int[]{0,0,0,0};
            this.loadScene();
            tmp_stage.setScene(current_scene);
        });
        myGrid.add(clear,1,6);

        current_scene = new Scene(myGrid,500,500);
    }
    Highscores(Stage tmp_stage,Menu myMenu){
        this.tmp_stage = tmp_stage;
        this.myMenu = myMenu;
        this.levels = new int[]{1,2,3,4};
        this.scores = new int[]{0,0,0,0};
        this.players = new String[]{"","","",""};
    }
    public void addScore(int level, int score, String player){
        if(scores[level-1]<score){
            scores[level-1] = score;
            players[level-1] = player;
        }
    }
}

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene current;

        Menu menu = new Menu(primaryStage);
        Highscores highscores = new Highscores(primaryStage,menu);
        Score score = new Score(primaryStage,menu,highscores);
        Game game = new Game(primaryStage,score);
        DataInput dataInput = new DataInput(primaryStage,game);
        menu.setNext(dataInput,highscores);

        menu.loadScene();
        current = menu.getScene();
        primaryStage.setTitle("Liczarka");
        primaryStage.setScene(current);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
