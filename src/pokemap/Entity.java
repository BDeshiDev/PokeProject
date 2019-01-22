package pokemap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Entity {
    private Position entityPosition;
    private ImageView imageOfEntity;
    private Image front,back,left,right;

    public Entity(Position entityPosition, ImageView imageOfEntity) {
        this.entityPosition = entityPosition;
        this.imageOfEntity = imageOfEntity;
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

        if(dir==Directions.RIGHT) direction=right;
        else if(dir==Directions.LEFT) direction=left;
        else if(dir==Directions.UP) direction=front;
        else direction=back;

        //this.getImageOfEntity().setImage(direction);
        if(map.isMoveValid(this.getEntityPosition(),dx,dy)&&
        map.isInMap(new Position(posX+dx,posY+dy))) {
            this.getImageOfEntity().relocate(posX + dx, posY + dy);
//            System.out.println(posX+dx+" "+posY+dy);
            this.setEntityPosition(new Position(posX+dx,posY+dy));
        }
    }



}

