package com.winchem.log.common.s2;

import com.google.common.geometry.*;
import org.springframework.stereotype.Component;

@Component
public class Gs2Utils {


    private static final int MINLEVEL = 4;
    private static final int MAXLEVEL = 16;
    private static final int MAXCELLS = 30;
    private static final S2RegionCoverer COVERER = new S2RegionCoverer();
    static {
        COVERER.setMinLevel(MINLEVEL);
        COVERER.setMaxLevel(MAXLEVEL);
        COVERER.setMaxCells(MAXCELLS);
    }

    /**
    *@Description: 判断是否在指定圆心，指定半径内
    *@Param:  * @param gpsLat gps纬度， gpsLng：gps经度，radius：半径:
    *@return:  * @return : null
    *@Author: zhanglb
    *@date: 2020/11/5 11:21
    */
    public static boolean isInCircle(Double cgpsLat, Double cgpsLng, Double gpsLat, Double gpsLng, double radius) {
        //获取cap 判断是否在半径范围内
        S2Region cap = getS2RegionByCircle(cgpsLat, cgpsLng, radius);
        return contains(cap, gpsLat, gpsLng);
    }

    /**
    *@Description: 根据 中心点经纬度 和 半径构造cap
    *@Param:  * @param  lat gps纬度， lng：gps经度，radius：半径: radius
    *@return:  * @return : null
    *@Author: zhanglb
    *@date: 2020/11/5 14:31
    */
    public static S2Region getS2RegionByCircle(double lat, double lng, double radius) {
        double capHeight = (2 * S2.M_PI) * (radius / 40075017);
        S2Cap cap = S2Cap.fromAxisHeight(S2LatLng.fromDegrees(lat, lng).toPoint(), capHeight * capHeight / 2);
        /*S2CellUnion s2CellUnion = coverer.getCovering(cap);*/
        return cap;
    }

    /**
    *@Description: 判断是否经纬度dian 是否在圆形区域内
    *@Param:  * @param region : 圆形， lat gps纬度， lng：gps经度，radius：半径: radius
    *@return:  * @return : null
    *@Author: zhanglb
    *@date: 2020/11/5 14:31
    */
    public static boolean contains(S2Region region, double lat, double lng) {
        /*double[] dou = LngLonUtil.bd09_To_gps84(lat, lon);*/
        S2LatLng s2LatLng = S2LatLng.fromDegrees(lat, lng);
        return region.contains(new S2Cell(s2LatLng));
    }

    /**
    *@Description: 计算地球上两个点之间的距离
    *@Param:  * @param null :
    *@return:  * @return : null
    *@Author: zhanglb
    *@date: 2020/11/11 11:16
    */
    public static Double get2PointDistance(Double lat1, Double lng1, Double lat2, Double lng2) {
         if (null == lat1 || null == lng1 || null == lat2 || null == lng2) {
             return null;
         }
        S2LatLng s1 = S2LatLng.fromDegrees(lat1, lng1);
        S2LatLng s2 = S2LatLng.fromDegrees(lat2, lng2);
        //单位为m
        return s1.getEarthDistance(s2);
    }
}
