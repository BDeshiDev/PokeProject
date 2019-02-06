package com.company.minigames;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;


import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane p =new Pane();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(p, 300, 275));
        primaryStage.show();

        startMinigame();

    }


    public static void main(String[] args) {
        launch(args);
    }

    public void startMinigame(){
        Dialog<String> testDialog = new Dialog<>();
        Rectangle r = new Rectangle(20,80, Color.RED);
        new AnimationTimer(){
            int sign =1;
            int speed = 5;
            Optional<String> result;
            String retMessage = "fail";

            @Override
            public void start() {
                super.start();
                VBox vb = new VBox();
                Button hitButton = new Button("click me when the time is right");
                vb.getChildren().addAll(new Pane(r),hitButton);
                hitButton.setOnAction( e->{
                    double boxPos = r.getLayoutX();
                    System.out.println("box at "+ boxPos);
                    if(boxPos>100)
                        boxPos =  100 - (boxPos -100);
                    if((100- boxPos) <10)
                        retMessage = "prefect";
                    else if((100- boxPos) <30)
                        retMessage = "great";
                    else if((100- boxPos) <50)
                        retMessage = "okay";
                    else
                        retMessage = "epic fail";
                });

                testDialog.getDialogPane().setContent(vb);

                testDialog.setTitle("minigame of life and death");
                testDialog.getDialogPane().setPrefSize(300,300);
                testDialog.getDialogPane().getButtonTypes().add(new ButtonType("okay", ButtonBar.ButtonData.OK_DONE));

                testDialog.setResultConverter(param -> {return  retMessage;});
                result =testDialog.showAndWait();
                if(result.isPresent())
                    System.out.println(result.get());
            }

            @Override
            public void handle(long now) {
                r.setLayoutX(r.getLayoutX()+sign * speed);
                if(r.getLayoutX() >200 || r.getLayoutX() <=0)
                    sign *=-1;
                if(result != null )
                    stop();
            }
        }.start();
    }
}
