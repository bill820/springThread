package com.winchem.log.common.s2;

import com.google.common.geometry.*;
import com.winchem.log.common.math.DoubleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class TestDemo {

    private static final Logger logger = LoggerFactory.getLogger(TestDemo.class);

    /**
     * 计算地球上两个点之间的距离
     */
    public static double get2PointDistance(double lat1, double lng1, double lat2, double lng2) {

        S2LatLng s1 = S2LatLng.fromDegrees(lat1, lng1);
        S2LatLng s2 = S2LatLng.fromDegrees(lat2, lng2);
        //单位为m
        double earthDistance = s1.getEarthDistance(s2);

        //可以用于计算两点之间的弧度距离
//        logger.info("计算两点之间的弧度距离 : " +earthDistance);

        return earthDistance;
    }

    /**
     * 经纬度 转 CellId
     *
     * @param latRadians 纬度
     * @param lngRadians 经度
     */
    public static String jwdToCellId(double latRadians, double lngRadians) {
        S2LatLng s2LatLng = S2LatLng.fromDegrees(latRadians, lngRadians);
        String cellIdStr = S2CellId.fromLatLng(s2LatLng).toString();
        logger.info("cellIdStr : " + cellIdStr);
        return cellIdStr;
    }

    /**
     * 判断点是否在圆形区域内部
     *
     * @param circle  圆形区域
     * @param s2Point 点
     * @return
     */
    public static Boolean isInCircle(S2Cap circle, S2Point s2Point) {
        Boolean bool = false;
      /*  double lat2 = 22.629164;
        double lgt2 =114.025514 ;
        S2LatLng s2LatLng = S2LatLng.fromDegrees(lat2, lng2);*/
        boolean isContain = circle.contains(s2Point);
        logger.info("判断点是否在圆形区域内部 : " + isContain);
        return isContain;
    }

    /**
     * 构造圆形区域
     *
     * @param latRadians 纬度
     * @param lngRadians 经度
     * @param capHeight  半径
     * @return
     */
    public static S2Cap makeCircle(double latRadians, double lngRadians, double capHeight) {
        S2Cap s2Cap = null;
       /* double lng = 112.030500;
        double lat = 27.970271;
        double capHeight = 600.5; //半径*/
        S2LatLng s2LatLng = S2LatLng.fromDegrees(latRadians, lngRadians);
        s2Cap = S2Cap.fromAxisHeight(s2LatLng.toPoint(), capHeight);
        return s2Cap;
    }

    public void CellIdToJwd() {

    }

    private static int minLevel = 4;
    private static int maxLevel = 16;
    private static int maxCells = 30;

    private static final S2RegionCoverer coverer = new S2RegionCoverer();
    static List<gps> gpsList = new ArrayList<gps>();
    static List<gps> isIncludeList = new ArrayList<>();
    static double lat = 34.267832, lng = 108.953556;
    static double wgsLat = 34.263156, wgsLng = 108.942468;

    static {
        coverer.setMinLevel(minLevel);
        coverer.setMaxLevel(maxLevel);
        coverer.setMaxCells(maxCells);

        //初始化 百度经纬度
        for (int i = 1; i <= 1000; i++) {
            gps g = new TestDemo().new gps();
            g.setLat(DoubleUtil.add(lat, 0.0001 * i));
            g.setLng(DoubleUtil.add(lng, 0.0001 * i));
            gpsList.add(g);
        }
        logger.info("gpsList.size: " +gpsList.size() + " and first: " +gpsList.get(0).toString());
        //初始化wgs经纬度
        for (gps wg : gpsList) {
            gps g = new TestDemo().new gps();
            double[] wgdou = LngLonUtil.bd09_To_gps84(wg.getLat(), wg.getLng());
            g.setLat(wgdou[0]);
            g.setLng(wgdou[1]);
            isIncludeList.add(g);
        }
        logger.info("isIncludeList.size: " +isIncludeList.size() + " and first: " +isIncludeList.get(0).toString());
    }

    public static S2Region getS2RegionByCircle(double lat, double lon, double radius) {
        double capHeight = (2 * S2.M_PI) * (radius / 40075017);
        S2Cap cap = S2Cap.fromAxisHeight(S2LatLng.fromDegrees(lat, lon).toPoint(), capHeight * capHeight / 2);
//        logger.info("半径距离为：" +(capHeight * capHeight / 2));
        S2CellUnion s2CellUnion = coverer.getCovering(cap);

        return cap;
    }


    public static boolean contains(S2Region region, double lat, double lon) {
        double[] dou = LngLonUtil.bd09_To_gps84(lat, lon);
        S2LatLng s2LatLng = S2LatLng.fromDegrees(dou[0], dou[1]);

        return region.contains(new S2Cell(s2LatLng));
    }


    public static void main(String[] args) {

        //3.判断点是否在圆形区域内部
        //3.1构造圆形
        double lat = 27.970271;
        double lng = 112.030500;
        //半径
        double capHeight = 600.5;
        S2Cap s2Cap = TestDemo.makeCircle(lat, lng, capHeight);

        //3.2 获取坐标点
        double lat2 = 22.629164;
        double lng2 = 114.025514;
        S2LatLng s2LatLng = S2LatLng.fromDegrees(lat2, lng2);
        S2Point s2Point = s2LatLng.toPoint();

        //4.距离效率测试
        TestDemo.timeMathTest();
        TestDemo.timeS2Test();

    }

    public static double[] getDis(double lat1, double lng1, double lat2, double lng2) {
        double distance = TestDemo.get2PointDistance(lat1, lng1, lat2, lng2);
        double distance2 = MathCal.getDistance(lat1, lng1, lat2, lng2);
        logger.info("S2: " + distance);
        logger.info("math: " + distance2);
        return new double[]{distance, distance2};
    }

    public static void isInCircle(double lat1, double lng1, double lat2, double lng2, double radius) {
        S2Region cap = getS2RegionByCircle(lat1, lng1, radius);
        double[] dou = LngLonUtil.bd09_To_gps84(lat2, lng2);
        logger.info("lat2 是否在, lat1为中心 " + radius + " 为半径的圆中 ：" + contains(cap, dou[0], dou[1]));
    }

    public static void pressureTestMath() {
        for (gps ig : gpsList) {
            double distance = MathCal.getDistance(ig.getLat(), ig.getLng(), lat, lng);
//            ig.setDistance(distance);
        }
    }

    public static void pressureTestS2() {

//        获取cap 判断是否在半径范围内
        S2Region cap = getS2RegionByCircle(lat, lng, 600);
        for (gps g : gpsList) {
            if (contains(cap, g.getLng(), g.getLat())) {
                isIncludeList.add(g);
            }
        }

        for (gps ig : isIncludeList) {
            double distance = TestDemo.get2PointDistance(ig.getLat(), ig.getLng(), wgsLat, wgsLng);
//            ig.setDistance(distance);
        }
//        isIncludeList.sort((a, b) -> (int) (a.getDistance() - b.getDistance()));

    }

    public static void timeMathTest() {
        StopWatch watch = new StopWatch("timeMathTest测试运行时间");
        watch.start("timeMathTest");
        for (int i = 0; i < 10000; i++) {
            TestDemo.pressureTestMath();
        }
        watch.stop();
        logger.info(watch.prettyPrint());
    }

    public static void timeS2Test() {
        StopWatch watch = new StopWatch("timeS2Test测试运行时间");
        watch.start("timeS2Test");
        for (int i = 0; i < 10000; i++) {
            TestDemo.pressureTestS2();
        }

        watch.stop();
        logger.info(watch.prettyPrint());
    }

    class gps {
        double lat;
        double lng;
        double distance;

        public gps() {
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "gps{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    '}';
        }
    }
}
