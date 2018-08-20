package com.hangzhou.zhb.myelves.utils.navi;

import android.os.Bundle;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.hangzhou.zhb.myelves.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhb on 2017/8/22.
 * 单路径导航
 */

public class SingleRouteCalculateActivity extends NaviBaseActivity {
	/**终点位置*/
	public double endLantitude = 0;// 纬度
	public double endLongitude = 0;// 经度
	/**起点位置*/
	public double startLantitude = 0;// 纬度
	public double startLongitude = 0;// 经度

	protected final List<NaviLatLng> sL = new ArrayList<NaviLatLng>();
	protected final List<NaviLatLng> eL = new ArrayList<NaviLatLng>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_navi);
		mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
		mAMapNaviView.onCreate(savedInstanceState);
		mAMapNaviView.setAMapNaviViewListener(this);

		mAMapNaviView.setTrafficLine(false);
		AMapNaviViewOptions aMapNaviViewOptions = new AMapNaviViewOptions();
		//设置路况光柱条是否显示（只适用于驾车导航，需要联网）。
		aMapNaviViewOptions.setTrafficBarEnabled(false);
		//设置是否绘制显示交通路况的线路（彩虹线），拥堵-红色，畅通-绿色，缓慢-黄色，未知-蓝色。
		aMapNaviViewOptions.setTrafficLine(false);
		//设置[实时交通图层开关按钮]是否显示（只适用于驾车导航，需要联网）。
		aMapNaviViewOptions.setTrafficLayerEnabled(false);
		//设置交通播报是否打开（只适用于驾车导航，需要联网）。
		aMapNaviViewOptions.setTrafficInfoUpdateEnabled(false);
		mAMapNaviView.setViewOptions(aMapNaviViewOptions);

		initData();
	}

	/**
	 * 初始化信息
	 */
	private void initData() {
		Bundle Bundle = getIntent().getExtras();
		endLantitude = Double.valueOf(Bundle.getString("eLantitude"));
		endLongitude = Double.valueOf(Bundle.getString("eLongitude"));
		startLantitude = Double.valueOf(Bundle.getString("sLantitude"));
		startLongitude = Double.valueOf(Bundle.getString("sLongitude"));
	}

	@Override
	public void onInitNaviSuccess() {
		super.onInitNaviSuccess();
	/**
	 * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
	 *
	 * @congestion 躲避拥堵
	 * @avoidhightspeed 不走高速
	 * @cost 避免收费
	 * @hightspeed 高速优先
	 * @multipleroute 多路径
	 *
	 *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
	 *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
	 */
		int strategy = 0;
		try {
			//再次强调，最后一个参数为true时代表多路径，否则代表单路径
			strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(startLantitude != 0 && startLongitude != 0 && endLantitude != 0 && endLongitude != 0) {
			mStartLatlng = new NaviLatLng(startLantitude, startLongitude);
			mEndLatlng = new NaviLatLng(endLantitude, endLongitude);
			sL.add(mStartLatlng);
			eL.add(mEndLatlng);
			//驾车
//			mAMapNavi.calculateDriveRoute(sL, eL, mWayPointList, strategy);
			//骑车
//			mAMapNavi.calculateRideRoute(mStartLatlng, mEndLatlng);
			//步行
			mAMapNavi.calculateWalkRoute(mStartLatlng, mEndLatlng);
		}

	}

	@Override
	public void onCalculateRouteSuccess(int[] ids) {
		super.onCalculateRouteSuccess(ids);
		mAMapNavi.startNavi(NaviType.GPS);
	}
}
