package pokemap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapTransitionEntity extends  Entity {
    String mapName;
    Position playerStartPos;

    public MapTransitionEntity(Position entityPosition, String mapName) {
        super(entityPosition, new ImageView());
        this.mapName = mapName;

        getImageOfEntity().setImage(null);
    }
}
