package pokemap;

import com.company.networking.TrainerData;
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
    Image treeImage = new Image("Assets/MapImages/tile_0001.png");
    Image emptyTileImage =new Image("Assets/MapImages/tile_0041.png");
    public int tileSize ;

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

    public String[] possibleEncounters;
    public TrainerData[] trainerDatas;


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
        this.possibleEncounters = map.possibleEncounters;
        this.trainerDatas = map.trainerDatas;
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
        int row =getRow(position.getY() + dy+Entity.entityImageSize/2);
        int col = getCol(position.getX() + dx+Entity.entityImageSize/2);
       // System.out.println("Row="+row+"  Col="+col);
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
            //System.out.println("In map");
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
                Image tileImage;
                if(mapAra[row][col]=='T')
                    tileImage = treeImage;
                else if(mapAra[row][col]=='X')
                   tileImage = emptyTileImage;
                else
                    tileImage=null;
                imageView.setImage(tileImage);
                imageView.relocate(col*tileSize,row*tileSize);
                group.getChildren().add(imageView);
            }
        }
        return group;
    }
}
