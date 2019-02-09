package ui_elements;

import com.company.RealTime.MoveCardData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
        damageLabel.setText("Power: "+moveCardData.damagePerHit);
    }

}
