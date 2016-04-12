package com.bj58.socialsystem;

import org.junit.Test;

import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Rectangle;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月12日
 */
public class SpatialContextTest {
	
	/**
	 * SELECT id, name
	 * FROM customer
	 * WHERE (lng BETWEEN ? AND ?) AND (lat BETWEEN ? AND ?);
	 * 
	 * INDEX `idx_lon_lat` (`lon`, `lat`)
	 */
	@Test
	public void test() {
		double lon = 116.312528, lat = 39.983733;// 移动设备经纬度
		int radius = 1;// 千米

		SpatialContext geo = SpatialContext.GEO;
		Rectangle rectangle = geo.getDistCalc().calcBoxByDistFromPt(
		        geo.makePoint(lon, lat), radius * DistanceUtils.KM_TO_DEG, geo, null);
		System.out.println(rectangle.getMinX() + "-" + rectangle.getMaxX());// 经度范围
		System.out.println(rectangle.getMinY() + "-" + rectangle.getMaxY());// 纬度范围
	
	
	}
	
}
