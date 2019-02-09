package pokemap;


import com.company.networking.TrainerData;
import javafx.scene.image.ImageView;
import javafx.scene.shape.TriangleMesh;

import java.util.Random;

public class EnemyEntity extends Entity {
    TrainerData trainerData;
    Directions dir;

    public EnemyEntity(TrainerData trainerData, Position entityPosition, ImageView imageOfEntity) {
        super(entityPosition, imageOfEntity);
        this.trainerData=trainerData;
        this.dir=Directions.RIGHT;
    }

    public void setRandomDirection() {
        Random random=new Random();
        int randomInt=random.nextInt(5);
        if (randomInt==0) dir=Directions.RIGHT;
        else if (randomInt==1) dir=Directions.LEFT;
        else if (randomInt==2) dir=Directions.UP;
        else dir=Directions.DOWN;
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
