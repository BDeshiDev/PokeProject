package pokemap;

import com.company.Settings;
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

public class ExicuteMap extends Application {
    Map forestMap=new Map(new File("C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\pokemap\\ForestMap.txt"));
    Entity player=new Entity(new Position(36,36),
            new ImageView("Assets/MapImages/heroleft.png"));

    boolean run,up,down,left,right;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group=forestMap.setMap();
        group.getChildren().add(player.getImageOfEntity());
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
                case UP: {up=true; break;}
                case DOWN:{ down=true; break;}
                case LEFT:{ left=true; break;}
                case RIGHT:{ right=true; break;}
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

        AnimationTimer timer=new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx=0,dy=0;
                if (up) dy-=1;
                if (down) dy += 1;
                if (left) dx -= 1;
                if (right) dx += 1;
                if (run) {
                    dx *= 3;
                    dy *= 3;
                }

            player.Shift(forestMap,dx,dy,Directions.UP);
            }
        };

        timer.start();
    }
    public static void main(String[] args) {

        System.out.println("help us");
        launch(args);
    }
}