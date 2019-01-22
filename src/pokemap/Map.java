package pokemap;

import com.google.gson.Gson;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static javafx.scene.input.KeyCode.T;

public class Map {
    Character mapAra[][];
    int tileSize;
    Position startPosition;

    public Character[][] getMapAra() {
        return mapAra;
    }

    public void setMapAra(Character[][] mapAra) {
        this.mapAra = mapAra;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    Map(File mapName)
    {
        FileReader reader=null;
        try {
            reader=new FileReader(mapName);
        } catch (FileNotFoundException e) {
            System.out.println("Load file failed");
        }

        Gson gson=new Gson();
        Map map=gson.fromJson(reader,Map.class);
        this.mapAra=map.mapAra;
        this.tileSize=map.tileSize;
        this.startPosition=map.startPosition;
    }

    public int getRow(int x){
        return (x/tileSize);
    }

    public int getCol(int y){
        return (y/tileSize);
    }

    public boolean isMoveValid(Position position,int dx,int dy){
        int row =getRow(position.getY() + dy+8);
        int col = getCol(position.getX() + dx+8);
        System.out.println("Row="+row+"  Col="+col);
        if(mapAra[row][col] == 'T'){
//            System.out.println("Row="+row+"  Col="+col);
            return false;
        }
        return true;
    }

    public boolean isInMap(Position position)
    {
        if ((mapAra.length-1)*tileSize>position.getY()&&
                (mapAra[0].length-1)*tileSize>position.getX()&&
        position.getY()>0&&position.getY()>0){
//            System.out.println(mapAra.length+" "+mapAra[0].length);
            System.out.println("In map");
            return true;
        }
        System.out.println("out of map");
        return false;
    }

    public Group setMap()
    {
        Group group=new Group();
        System.out.println(mapAra[0].length);
        for (int row = 0; row < (mapAra.length); row++) {
            for (int col = 0; col < (mapAra[0].length); col++) {
                ImageView imageView=new ImageView();
                String url;
                if(mapAra[row][col]=='T') {
                    url = "C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\Assets\\MapImages\\tile_0001.png";
                }
                else
                   url ="C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\Assets\\MapImages\\tile_0041.png";
                try {
                    imageView.setImage(new Image(new FileInputStream(url)));
                } catch (FileNotFoundException e) {
                    System.out.println("Load hero image fail");
                }
                imageView.relocate(col*tileSize,row*tileSize);
                group.getChildren().add(imageView);
            }
        }
        return group;
    }
}
