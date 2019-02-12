package pokemap;


import com.company.networking.TrainerData;
import javafx.scene.image.ImageView;
import javafx.scene.shape.TriangleMesh;

import java.util.Random;

public class EnemyEntity extends Entity {
    TrainerData trainerData;
    Directions dir;
    int dirChangeTimer = 0;
    int dirChangeCooldown = 600;


    public EnemyEntity(TrainerData trainerData, Position entityPosition, ImageView imageOfEntity) {
        super(entityPosition, imageOfEntity);
        this.trainerData=trainerData;
        this.dir=Directions.RIGHT;
    }

    public void setRandomDirection() {
        Random random=new Random();
        int randomInt=random.nextInt(8);
        if (randomInt==0) dir=Directions.RIGHT;
        else if (randomInt==1) dir=Directions.LEFT;
        else if (randomInt==2) dir=Directions.UP;
        else if(randomInt==3) dir=Directions.DOWN;
        else dir=null;
    }

    public void tryDirChange(){
        dirChangeTimer++;
        if(dirChangeTimer >= dirChangeCooldown){
            dirChangeTimer = 0;
            setRandomDirection();
        }
    }

    public void randomShift(Map forestMap){
        Shift(forestMap,getRandomDx(),getRandomDy(),getRandomDirection());
    }

    public int getRandomDx() {

        if (dir==Directions.RIGHT) return 1;
        else if (dir==Directions.LEFT) return -1;
        else return 0;
    }

    public Directions getRandomDirection() {
        return dir;
    }

    public int getRandomDy() {

        if (dir==Directions.UP) return -1;
        else if (dir==Directions.DOWN) return 1;
        else return 0;
    }
}
