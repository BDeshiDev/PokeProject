package pokemap;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Entity {
    private Position entityPosition;
    private transient ImageView imageOfEntity;
    public static int entityImageSize=16;
    private transient  Image front,back,left,right;
    private StackPane stackPane;
    private double live;


    private String frontImageName="Assets/MapImages/herodown.png",
            backImageName = "Assets/MapImages/heroup.png",
            leftImageName = "Assets/MapImages/heroleft.png",
            rightImageName ="Assets/MapImages/heroright.png";

    public Entity(Position entityPosition, ImageView imageOfEntity) {
        this.entityPosition = entityPosition;
        this.imageOfEntity = imageOfEntity;
        front = new Image(frontImageName);
        back = new Image(backImageName);
        left = new Image(leftImageName);
        right = new Image(rightImageName);
        imageOfEntity.setImage(back);
        updateImagePosition();
        try {
            stackPane = new FXMLLoader(getClass().getResource("progress_bar.fxml")).load();
            stackPane.setLayoutX(imageOfEntity.getLayoutX());
            stackPane.setLayoutY(imageOfEntity.getLayoutY()-4);
        } catch (IOException e) {
            System.out.println("cant load progressbar");
        }
        live=1;
        ProgressBar progressBar= (ProgressBar) stackPane.getChildren().get(0);
        progressBar.setProgress(live);
    }

    public StackPane getProgress(){
        return stackPane;
    }

    public Position getEntityPosition() {
        return entityPosition;
    }

    public void setEntityPosition(Position entityPosition) {
        this.entityPosition = entityPosition;
    }

    public void updateImagePosition(){
        if(imageOfEntity != null)
            imageOfEntity.relocate(entityPosition.getX(),entityPosition.getY());
    }

    public ImageView getImageOfEntity() {
        return imageOfEntity;
    }

    public void setImageOfEntity(ImageView imageOfEntity) {
        this.imageOfEntity = imageOfEntity;
    }

    public void Shift(Map map,int dx, int dy,Directions dir)
    {
        if(dx==0&&dy==0) return;
        int posX=this.getEntityPosition().getX();
        int posY=this.getEntityPosition().getY();

        Image direction;

        if(dir==Directions.RIGHT) direction=right;
        else if(dir==Directions.LEFT) direction=left;
        else if(dir==Directions.UP) direction=front;
        else direction=back;

        this.getImageOfEntity().setImage(direction);
        if(map.isMoveValid(this.getEntityPosition(),dx,dy)&&
        map.isInMap(new Position(posX+dx,posY+dy))
        &&posX+dx>0&&posY+dy>0) {
            this.getImageOfEntity().relocate(posX + dx, posY + dy);
//            System.out.println(posX+dx+" "+posY+dy);
            this.setEntityPosition(new Position(posX+dx,posY+dy));
            System.out.println(this.gettingPokemonProbability(map));
            int row=map.getRow(this.getEntityPosition().getY()+8);
            int col=map.getCol(this.getEntityPosition().getX()+8);

            if(map.getMapAra()[row][col]==002 &&flag==false) {
                flag=true;
                this.prevCol=col;
                this.prevRow=row;
            }

            if(map.getMapAra()[this.prevRow][this.prevCol]==002 && flag==true) {
                this.probability+=.01;
            }

            if (map.getMapAra()[row][col]==002&&flag==true){
                this.prevCol=col;
                this.prevRow=row;
            }

            if(map.getMapAra()[row][col]!=002) {
                flag=false;
                this.probability=0;
            }
        }
    }

    private double probability=0;
    private int grassStartRow;
    private int grassStartCol;
    private int prevRow;
    private int prevCol;
    private boolean flag=false;

    public void resetProbablity(){
        probability = 0;
    }

    public double gettingPokemonProbability(Map map){
        stackPane.setLayoutX(entityPosition.getX());
        stackPane.setLayoutY(entityPosition.getY()-4);

        return this.probability;

    }


}

