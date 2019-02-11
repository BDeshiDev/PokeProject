package ui_elements;

import com.company.RealTime.MoveCardData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class cards {

    @FXML
    private ImageView cardImage;

    @FXML
    private Label attackLabel;

    @FXML
    private Label damageLabel;

    public void setCard(MoveCardData moveCardData) {
        try {
            cardImage.setImage(new Image(new FileInputStream(moveCardData.iconName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        attackLabel.setText("Attack: "+moveCardData.attackName);
        damageLabel.setText("Power: "+moveCardData.baseDamage);
    }

}
