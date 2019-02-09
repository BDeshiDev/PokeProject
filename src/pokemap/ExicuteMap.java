package pokemap;

import com.company.Settings;
import com.company.networking.TrainerData;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.Timer;

public class ExicuteMap extends Application {
    Map forestMap=new Map(new File("C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\pokemap\\ForestMap.txt"));
    Entity player=new Entity(new Position(36,36),
            new ImageView("Assets/MapImages/heroleft.png"));
//    Entity mass1=new Entity(new Position(46,36),
//            new ImageView("Assets/MapImages/heroup.png"));
    EnemyEntity enemy=new EnemyEntity(new TrainerData("gfgfg","Carizard"),new Position(36,36),new ImageView("Assets/MapImages/heroleft.png"));

    boolean run,up,down,left,right;
    Directions direction;
    Directions direction1;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group=forestMap.setMap();
        group.getChildren().add(player.getImageOfEntity());
        group.getChildren().add(enemy.getImageOfEntity());
        PerspectiveCamera camera=new PerspectiveCamera(true);
        camera.layoutXProperty().bind(player.getImageOfEntity().layoutXProperty());
        camera.layoutYProperty().bind(player.getImageOfEntity().layoutYProperty());

        camera.setTranslateZ(-300);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);


        Scene scene=new Scene(group, Settings.windowWidth,
                Settings.windowLength, Color.FORESTGREEN);
        scene.setCamera(camera);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()){
                case R: run=true;  break;
                case UP: {up=true;direction=Directions.UP; break;}
                case DOWN:{ down=true;direction=Directions.DOWN; break;}
                case LEFT:{ left=true;direction=Directions.LEFT; break;}
                case RIGHT:{ right=true;direction=Directions.RIGHT; break;}
            }
//                System.out.println("key pressed");
        }
    });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case R: run=false; break;
                    case UP: up=false; break;
                    case DOWN: down=false; break;
                    case LEFT: left=false; break;
                    case RIGHT: right=false; break;
                }
//                System.out.println("Key released");
            }
        });


        primaryStage.setTitle("Player on Rush.");
        primaryStage.setScene(scene);
        primaryStage.show();
//        Random random=new Random();


        AnimationTimer timer=new AnimationTimer() {
            Long previousTime=System.currentTimeMillis();
            @Override
            public void handle(long now) {
                int dx=0,dy=0;
//                int dx1=0,dy1=0;

                if (up) dy-=1;
                if (down) dy += 1;
                if (left) dx -= 1;
                if (right) dx += 1;
                if (run) {
                    dx *= 3;
                    dy *= 3;
                }

                Long timeDelta=0l;
                Long timenow=System.currentTimeMillis();
                timeDelta=timenow-previousTime;

                if(timeDelta>=500L){
                    enemy.setRandomDirection();
                    previousTime=System.currentTimeMillis();
                }


             player.Shift(forestMap,dx,dy,direction);
             enemy.Shift(forestMap,enemy.getRandomDx(),enemy.getRandomDy(),enemy.getRandomDirection());
             if(Math.abs(player.getEntityPosition().getX()-enemy.getEntityPosition().getX())==1 ||
                     Math.abs(player.getEntityPosition().getY()-enemy.getEntityPosition().getY())==1)
                 System.out.println("Fight Fight");
            }
        };

        timer.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
