package core;


import icon.BaseIcon;
import tile.Mine;
import tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

    private GridMap gridMap;

    public static Map<String, Image> IMAGE_CACHE = new HashMap<>();







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
        static final BaseIcon[][] SCV_CIONS = new BaseIcon[2][3];
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
                IMAGE_CACHE.put(fileName,new ImageIcon(ResourceManager.class.getResource("/images/"+filename)).getImage());
            }
            return IMAGE_CACHE.get(fileName);
        }
    }

}
