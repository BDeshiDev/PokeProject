package pokemap;

import com.company.SaveData;
import com.company.pcTrainer;
import javafx.scene.image.ImageView;

public class PlayerEntity extends Entity {

    private pcTrainer pcTrainer;

    public com.company.pcTrainer getPcTrainer() {
        return pcTrainer;
    }

    public PlayerEntity(SaveData currentSave, ImageView imageOfEntity) {
        this(currentSave.position,imageOfEntity,currentSave.pcTrainer);
        System.out.println("save pos "  + currentSave.position);
    }

    public PlayerEntity(Position entityPosition, ImageView imageOfEntity, pcTrainer pcTrainer) {
        super(entityPosition, imageOfEntity);
        this.pcTrainer = pcTrainer;
    }
}
