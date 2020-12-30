package com.winchem.log.common.s2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class MathCal {
/*
    private static double EARTH_RADIUS = 6378.137;// 单位千米

    *//**
     * 角度弧度计算公式 rad:(). <br/>
     *
     * 360度=2π π=Math.PI
     *
     * x度 = x*π/360 弧度
     *
     * @author chiwei
     * @param degree
     * @return
     * @since JDK 1.6
     *//*
    private static double getRadian(double degree) {
        return degree * Math.PI / 180.0;
    }

    *//**
     * 依据经纬度计算两点之间的距离 GetDistance:(). <br/>
     *
     * @author chiwei
     * @param lat1
     *            1点的纬度
     * @param lon1
     *            1点的经度
     * @param lat2
     *            2点的纬度
     * @param lon2
     *            2点的经度
     * @return 距离 单位 米
     * @since JDK 1.6
     *//*
    public static double getDistance(double lat1, double lon1, double lat2,  double lon2) {
        double radLat1 = getRadian(lat1);
        double radLat2 = getRadian(lat2);
        double a = radLat1 - radLat2;// 两点纬度差
        double b = getRadian(lon1) - getRadian(lon2);// 两点的经度差
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s * 1000;
    }*/

    private static final double EARTH_RADIUS = 6371393; // 平均半径,单位：m；不是赤道半径。赤道为6378左右

    /**
     * @描述 反余弦进行计算
     * @参数  [lat1, lng1, lat2, lng2]
     * @返回值  double
     * @创建人  Young
     * @创建时间  2019/3/13 20:31
     **/
    public static double getDistance(Double lat1,Double lng1,Double lat2,Double lng2) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(lng1); // A经弧度
        double radiansAY = Math.toRadians(lat1); // A纬弧度
        double radiansBX = Math.toRadians(lng2); // B经弧度
        double radiansBY = Math.toRadians(lat2); // B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
//        System.out.println("cos = " + cos); // 值域[-1,1]
        double acos = Math.acos(cos); // 反余弦值
//        System.out.println("acos = " + acos); // 值域[0,π]
//        System.out.println("∠AOB = " + Math.toDegrees(acos)); // 球心角 值域[0,180]
        return EARTH_RADIUS * acos; // 最终结果
    }

    public static void main(String[] args) {
        double lat1 = 34.281276, lng1 = 108.947217, lat2 = 34.292204, lng2 = 108.947217;
        double[] dou1 = LngLonUtil.bd09_To_gps84(lat1, lng1);
        double[] dou2 = LngLonUtil.bd09_To_gps84(lat2,lng2);
        //1.距离计算
        double dis = getDistance(lat1, lng1,lat2,lng2);
        double dis2 = TestDemo.get2PointDistance(dou1[0], dou1[1], dou2[0], dou2[1]);
        System.out.println(dis);
        System.out.println(dis2);

        //2.距离计算效率

//        timeTestMath(lat1, lng1,lat2,lng2);
        timeTestS2(dou1[0], dou1[1], dou2[0], dou2[1]);
    }

    private static final Logger logger = LoggerFactory.getLogger(TestDemo.class);

    public static void timeTestMath(double lat1, double lng1, double lat2,  double lng2) {

        StopWatch watch = new StopWatch("timeTestMath测试运行时间");
        watch.start("timeTestMath测试运行时间");
        for (int i = 0; i < 100000; i++) {
            double dis = getDistance(lat1, lng1, lat2, lng2);
        }

        watch.stop();
        logger.info(watch.prettyPrint());
    }

    public static void timeTestS2(double lat1, double lng1, double lat2,  double lng2) {

        StopWatch watch = new StopWatch("timeTestS2测试运行时间");
        watch.start("timeTestS2测试运行时间");
        for (int i = 0; i < 100000; i++) {
            double dis2 = TestDemo.get2PointDistance(lat1, lng1, lat2,lng2);
        }
        watch.stop();
        logger.info(watch.prettyPrint());
    }

}
