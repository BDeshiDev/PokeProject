package pokemap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Entity {
    private Position entityPosition;
    private ImageView imageOfEntity;
    public static int entityImageSize=16;
    private Image front,back,left,right;

    public Entity(Position entityPosition, ImageView imageOfEntity) {
        this.entityPosition = entityPosition;
        this.imageOfEntity = imageOfEntity;
        this.imageOfEntity.relocate(entityPosition.getX(),entityPosition.getY());
    }

    public Position getEntityPosition() {
        return entityPosition;
    }



    public void setEntityPosition(Position entityPosition) {
        this.entityPosition = entityPosition;
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

        if(dir==Directions.RIGHT) direction=new Image("Assets/MapImages/heroright.png");
        else if(dir==Directions.LEFT) direction=new Image("Assets/MapImages/heroleft.png");
        else if(dir==Directions.UP) direction=new Image("Assets/MapImages/herodown.png");
        else direction=new Image("Assets/MapImages/heroup.png");

        this.getImageOfEntity().setImage(direction);
        if(map.isMoveValid(this.getEntityPosition(),dx,dy)&&
        map.isInMap(new Position(posX+dx,posY+dy))) {
            this.getImageOfEntity().relocate(posX + dx, posY + dy);
//            System.out.println(posX+dx+" "+posY+dy);
            this.setEntityPosition(new Position(posX+dx,posY+dy));
            System.out.println(this.gettingPokemonProbability(map));
        }
    }

    private double probability=0;
    private int grassStartRow;
    private int grassStartCol;
    private int prevRow;
    private int prevCol;
    private boolean flag=false;


    public double gettingPokemonProbability(Map map){
        int row=map.getRow(this.getEntityPosition().getY()+8);
        int col=map.getCol(this.getEntityPosition().getX()+8);
//
//        if(map.getMapAra()[row][col]=='G' && flag==false) {
//            flag=true;
//            this.probability+=1;
//        }
//
//        else if(map.getMapAra()[row][col]=='G'&&flag==true) {
//            this.probability+=(row+col-prevRow-prevCol);
//        }
//
//        if(map.getMapAra()[row][col]!='G') {
//            flag=false;
//            this.probability=0;
//        }
//
//        if (map.getMapAra()[row][col]=='G'&&flag==true){
//            prevCol=col;
//            prevRow=row;
//    }
//
//        return this.probability;

        if(map.getMapAra()[row][col]=='G' &&flag==false) {
            flag=true;
            this.prevCol=col;
            this.prevRow=row;
        }

        if(map.getMapAra()[this.prevRow][this.prevCol]=='G' && flag==true) {
            this.probability+=.2;
        }


        if (map.getMapAra()[row][col]=='G'&&flag==true){
            this.prevCol=col;
            this.prevRow=row;
        }

        if(map.getMapAra()[row][col]!='G') {
            flag=false;
            this.probability=0;
        }

        return this.probability;

    }


}

