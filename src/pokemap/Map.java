package pokemap;

import com.company.networking.TrainerData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Random;

public class Map {

    Integer mapAra[][];
    Image treeImage = new Image("Assets/MapImages/Temp/smallTree.png");
    Image grassTile = new Image("Assets/MapImages/Temp/grassTile.png");
    public int tileSize ;
    private static HashMap<Integer,String> imageMap;
    Image emptyTileImage =new Image("Assets/MapImages/Temp/emptyTile.png");

    Position startPosition;

    public Integer[][] getMapAra() {
        return mapAra;
    }

    public void setMapAra(Integer[][] mapAra) {
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

    static {
        FileReader reader=null;
        try {
            reader=new FileReader("src/pokemap/imageHashMap.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Load file failed");
        }

        Gson gson=new Gson();
        Type mapType = new TypeToken<HashMap<Integer,String>>(){}.getType();
        imageMap=gson.fromJson(reader,mapType);
    }



    Map(File mapName)
    {
        FileReader reader=null;
        try {
            System.out.println(mapName);
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
        if(mapAra[row][col]%10 == 1||mapAra[row][col] == 999||mapAra[row][col]%10==6||mapAra[row][col]%10 == 4){
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
                if(mapAra[row][col]==999) continue;
                String tileImage="C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\pokemap\\tiles\\generic-rpg-tile02.png";
                if(mapAra[row][col]==002||mapAra[row][col]==001) {
                    ImageView backGroundLayer =new ImageView(emptyTileImage);
                    backGroundLayer.relocate(col*tileSize,row*tileSize);
                    group.getChildren().add(backGroundLayer);
                    tileImage = imageMap.get(mapAra[row][col]);
                }
                else
                    tileImage=imageMap.get(mapAra[row][col]);

                System.out.println(mapAra[row][col]);


                ImageView imageView= null;
                imageView = new ImageView(tileImage);

                imageView.relocate(col*tileSize,row*tileSize);
                group.getChildren().add(imageView);
                if(mapAra[row][col]==005){
                    ImageView grass=new ImageView("pokemap/tiles/generic-rpg-grass01.png");
                    grass.relocate(col*tileSize,row*tileSize);
                    if(new Random().nextInt(5)>2) group.getChildren().add(grass);
                }

            }
        }
        return group;
    }
}
