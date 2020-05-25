package core;


import icon.BaseIcon;
import icon.HouseIcon;
import icon.ScvIcon;
import tile.Headquarter;
import tile.House;
import tile.Mine;
import tile.Tile;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceManager {

    private GridMap gridMap;

    public static ResourceManager resourceManager;

    public static Map<String, Image> IMAGE_CACHE = new HashMap<>();

    public static Image loadImage(String fileName){

        if (!IMAGE_CACHE.containsKey(fileName)) {
            IMAGE_CACHE.put(fileName,new ImageIcon(ResourceManager.class.getResource("/images/"+fileName)).getImage());
        }
        return IMAGE_CACHE.get(fileName);
    }

    public static GridMapRender load(int type, List<Integer> types){

        resourceManager = new ResourceManager(type,types);

        return resourceManager.gridMap.getTileMapRender();
    }

    public GridMap getGridMap(){
        return gridMap;
    }

    private Image initMapBg(){

        BufferedImage buffer = new BufferedImage(128,128,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();

        for (int y = 0; y < gridMap.getHeight(); ++y){
            for (int x = 0; x < gridMap.getWidth(); ++x){
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.setToTranslation(x * 10,y * 5);
                affineTransform.scale(0.2, 0.2);
                g2.drawImage(Constant.IMAGE_MAP_BG,affineTransform,null);
            }
        }
        return buffer;
    }

    private ResourceManager(int type, List<Integer> types){
        Constant.load();
        gridMap = loadMap(type,types);
        Constant.IMAGE_BG = initMapBg();
        Constant.progress = Constant.TOTAL;
    }

    private GridMap loadMap(int type, List<Integer> types){

        List<String> list = new ArrayList<>();
        int width = readMap(list, "startmap1.map");
        GridMap map = new GridMap(width, list.size());
        GridMapRender mapRender = new GridMapRender(map);
        mapRender.type = type;
        map.setTileMapRender(mapRender);
        map.setIconMap(Constant.ICON_MAP);

        for (int y = 0; y < list.size(); ++y){
            String s = list.get(y);
            for (int x = 0; x < s.length(); ++x){
                int code = (int)s.charAt(x) - 40;
                if (code < 0 || code > Constant.TILE_TABLE.length){
                    continue;
                }
                if (code < 80 || !types.contains((code/20))){
                    continue;
                }

                Tile tile = Constant.TILE_TABLE[code].clone(x, y, map);
                tile.setHealth(1.0f);
                map.add(tile);

                if (tile.getType() == type && tile instanceof Headquarter){

                    int hqX = GridMapRender.tileXToPx(tile.getTileX());
                    int hqY = GridMapRender.tileYToPx(tile.getTileY());
                    Point size = tile.getSize();
                    int centerX = (800 -  GridMapRender.tileXToPx(size.x)) / 2;
                    int centerY = (600 - GridMapRender.tileYToPx(size.y))  / 2;

                    mapRender.setOffset(Math.max(hqX - centerX, 0), Math.max(hqY - centerY, 0));
                }
            }
        }
        return map;
    }

    private static int readMap(List<String> list, String fileName){

        BufferedReader br = null;

        int width = 0;

        try{
            br = new BufferedReader(new InputStreamReader(ResourceManager.class.getResourceAsStream("/map/" + fileName)));
            String line;

            while ((line = br.readLine()) != null){
                if (line.startsWith("#")){
                    continue;
                }
                list.add(line);
                width = Math.max(width, line.length());
            }
            return width;
        }catch (IOException e){
            e.printStackTrace();
            return  -1;
        }finally {
            try {
                if (br != null){
                    br.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    /**
    * @author hyx
    * @description 常量类
    *
    **/
    public static class Constant{

        public static String IP;

        public static Color GREEN;

        public static Color RED = Color.red;

        //背景 background
        static Image IMAGE_BG;
        //控制台
        static Image IMAGE_CONTROL;
        //光照效果
        public static Image SCV_SPARK;
        //矿产
        static Image ICON_MINE;
        //人口
        static Image ICON_MAN;
        //矿产错误信息
        static String MINE_ERROR = "no mine resource!";
        //人口错误信息
        static String MAN_ERROR = "man need resource!";
        //建造错误
        static String BUILD_ERROR = "can not build here";
        //背景
        public static Image IMAGE_MAP_BG;
        //ICON
        static final BaseIcon[][] SCV_ICONS = new BaseIcon[2][3];
        static final BaseIcon[][] HQ_ICONS = new BaseIcon[2][3];
        static final BaseIcon[][] BACK_ICONS = new BaseIcon[2][3];
        //ICON 资源表
        public static final Map<Integer,BaseIcon[][]> ICON_TABLE = new HashMap<>();
        //TILE 资源表
        static final Tile[] TILE_TABLE = new Tile[82];
        //Type的意思？
        private static final int TYPE = 4;
        //?
        public static final int TYPE_SIZE = 20;

        private static float progress;

        private static final float TOTAL = 19;

        static void load(){
            IMAGE_MAP_BG = loadImage("bg1.gif");
            IMAGE_CONTROL = loadImage("panel/main.gif");
            SCV_SPARK = loadImage("unit/0_scv_spark.gif");
            ICON_MINE = loadImage("panel/mine.gif");
            ICON_MAN = loadImage("panel/man.gif");
            GREEN = new Color(16,252,24);

            initTile();
        }

        private static final Map<String, BaseIcon> ICON_MAP = new HashMap<>();

        private static int count = 0;

        private static BaseIcon createIcon(BaseIcon icon){
            ICON_MAP.put("icon"+(++count),icon);
            return icon;
        }

        private static void initTile(){
            Image scv = loadImage("unit/0_scv_red.gif");
            Image marine  = loadImage("unit/0_marine_red.gif");
            Image marineFight = loadImage("unit/0_fight_marine_red.png");

            Image tank = loadImage("unit/0_tank.gif");
            Image supply = loadImage("build/0_supply_red.gif");
            Image barrack = loadImage("build/0_barrack_red.gif");
            Image base = loadImage("build/0_hq_red.gif");
            Image mine = loadImage("block/mine.gif");

            //转换成buffer
            BufferedImage scv_buffer = imageToBuffer(scv);
            BufferedImage marine_buffer = imageToBuffer(marine);
            BufferedImage marineFight_buffer = imageToBuffer(marineFight);
            BufferedImage supply_buffer = imageToBuffer(supply);
            BufferedImage barrack_buffer = imageToBuffer(barrack);
            BufferedImage base_buffer = imageToBuffer(base);

            //初始化矿产资源
            TILE_TABLE[80] = new Mine(mine, 80);

            //ICON初始化

            HQ_ICONS[0][0] = createIcon(new HouseIcon(loadImage("ico/0_scv_gif")));
            SCV_ICONS[0][0] = createIcon(new ScvIcon(loadImage("ico/0_supply.gif")));
            SCV_ICONS[0][1] = createIcon(new ScvIcon(loadImage("ico/0_barrack.gif")));
            BACK_ICONS[0][0] = createIcon(new HouseIcon(loadImage("ico/0_marine.gif")));


            //初始化比较复杂的Tile
            for (int i = 0; i < TYPE; ++i) {

                //农民
                TILE_TABLE[TYPE_SIZE * i] = createScv(scv_buffer, TYPE_SIZE * i);
                ICON_TABLE.put(TILE_TABLE[TYPE_SIZE * i].getId(), SCV_ICONS);
                scv_buffer = imageToBuffer(createImage(scv, i));

                //步兵
                TILE_TABLE[TYPE_SIZE * i + 1] = createMarine(marine_buffer, marineFight_buffer, TYPE_SIZE * i + 1);
                marine_buffer = imageToBuffer(createImage(marine, i));
                marineFight_buffer = imageToBuffer(createImage(marineFight, i));

                //喷火兵
//				TILE_TABLE[TYPE_SIZE*i+02] = createMarine(marine_buffer,TYPE_SIZE*i+02);
//				marine_buffer =  imageToBuffer(createImage(marine, i));

                //坦克
//				TILE_TABLE[TYPE_SIZE*i+04] = createTank(tank_buffer,TYPE_SIZE*i+04);
//				tank_buffer =  imageToBuffer(createImage(tank, i));

                //人口
                TILE_TABLE[TYPE_SIZE * i + 10] = createSupply(supply_buffer, TYPE_SIZE * i + 10);
                supply_buffer = imageToBuffer(createImage(supply, i));

                //基地
                TILE_TABLE[TYPE_SIZE * i + 11] = createBase(base_buffer, TYPE_SIZE * i + 11);
                ICON_TABLE.put(TILE_TABLE[TYPE_SIZE * i + 11].getId(), HQ_ICONS);
                base_buffer = imageToBuffer(createImage(base, i));

                //兵营
                TILE_TABLE[TYPE_SIZE * i + 12] = createBarrack(barrack_buffer, TYPE_SIZE * i + 12);
                ICON_TABLE.put(TILE_TABLE[TYPE_SIZE * i + 12].getId(), BACK_ICONS);
                barrack_buffer = imageToBuffer(createImage(barrack, i));
            }

        }

        private static House createSupply(BufferedImage buffer, int id){

        }

        private static BufferedImage imageToBuffer(Image image){

            int w = image.getWidth(null);
            int h = image.getHeight(null);

            BufferedImage buffer = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);

            Graphics gh = buffer.getGraphics();
            gh.drawImage(image, 0, 0, null);
            gh.dispose();
            return buffer;
        }


        private static Image loadImage(String fileName){
            if (!IMAGE_CACHE.containsKey(fileName)){
                IMAGE_CACHE.put(fileName,new ImageIcon(ResourceManager.class.getResource("/images/"+ fileName)).getImage());
            }
            return IMAGE_CACHE.get(fileName);
        }
    }

}
