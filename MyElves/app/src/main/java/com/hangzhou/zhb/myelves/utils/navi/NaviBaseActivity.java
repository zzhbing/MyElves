package com.hangzhou.zhb.myelves.utils.navi;

import android.os.Bundle;
import android.util.Log;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.CheckPermissionsActivity;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhb on 2017/8/22.
 * 导航基础类
 */

public class NaviBaseActivity extends CheckPermissionsActivity implements AMapNaviListener, AMapNaviViewListener {

	protected AMapNaviView mAMapNaviView;
	protected AMapNavi mAMapNavi;
	protected NaviLatLng mEndLatlng = new NaviLatLng(39.925846, 116.432765);
	protected NaviLatLng mStartLatlng = new NaviLatLng(39.925041, 116.437901);
	protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
	protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
	protected List<NaviLatLng> mWayPointList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAMapNavi = AMapNavi.getInstance(getApplicationContext());
		mAMapNavi.addAMapNaviListener(this);

//		//设置模拟导航的行车速度
//		mAMapNavi.setEmulatorNaviSpeed(75);
//		sList.add(mStartLatlng);
//		eList.add(mEndLatlng);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mAMapNaviView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mAMapNaviView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mAMapNaviView.onDestroy();
		//since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
		mAMapNavi.stopNavi();
		mAMapNavi.destroy();
	}

	@Override
	public void onInitNaviFailure() {
		Log.i("bb","导航初始化失败时的回调函数。");
	}

	@Override
	public void onInitNaviSuccess() {
		Log.i("bb","导航初始化成功时的回调函数。");
	}

	@Override
	public void onStartNavi(int i) {
		Log.i("bb","启动导航后的回调函数");
	}

	@Override
	public void onTrafficStatusUpdate() {
		Log.i("bb","当前方路况光柱信息有更新时回调函数。");
	}

	@Override
	public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
		Log.i("bb","当GPS位置有更新时的回调函数。");
	}

	@Override
	public void onGetNavigationText(int i, String s) {
//		Log.i("bb","已过时。onGetNavigationText = " + s);
	}

	@Override
	public void onGetNavigationText(String s) {
		Log.i("bb","导航播报信息回调函数。onGetNavigationText = " + s);
	}

	@Override
	public void onEndEmulatorNavi() {
		Log.i("bb","模拟导航停止后回调函数。");
	}

	@Override
	public void onArriveDestination() {
		Log.i("bb","到达目的地后回调函数。。");
	}

	@Override
	public void onCalculateRouteFailure(int i) {
		Log.i("bb","步行或者驾车路径规划失败后的回调函数。。");
	}

	@Override
	public void onReCalculateRouteForYaw() {
		Log.i("bb","偏航后准备重新规划的通知回调。。");
	}

	@Override
	public void onReCalculateRouteForTrafficJam() {
		Log.i("bb","驾车导航时，如果前方遇到拥堵时需要重新计算路径的回调。。");
	}

	@Override
	public void onArrivedWayPoint(int i) {
		Log.i("bb","驾车路径导航到达某个途经点的回调函数。。");
	}

	@Override
	public void onGpsOpenStatus(boolean b) {
		Log.i("bb","用户手机GPS设置是否开启的回调函数。。");
	}

	@Override
	public void onNaviInfoUpdate(NaviInfo naviInfo) {
		Log.i("bb","导航引导信息回调 naviinfo 是导航信息类。");
	}

	@Override
	public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {
//		Log.i("bb","onNaviInfoUpdated已过时。。");
	}

	@Override
	public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
		Log.i("bb","导航过程中的摄像头信息回调函数。");
	}

	@Override
	public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

	}

	@Override
	public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {
		Log.i("bb","服务区信息回调函数。");
	}

	@Override
	public void showCross(AMapNaviCross aMapNaviCross) {
		Log.i("bb","显示路口放大图回调。。");
	}

	@Override
	public void hideCross() {
		Log.i("bb","关闭路口放大图回调。。");
	}

	@Override
	public void showModeCross(AMapModelCross aMapModelCross) {

	}

	@Override
	public void hideModeCross() {

	}

	@Override
	public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
		Log.i("bb","显示道路信息回调。。");
	}

	@Override
	public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

	}

	@Override
	public void hideLaneInfo() {
		Log.i("bb","关闭道路信息回调。。");
	}

	@Override
	public void onCalculateRouteSuccess(int[] ints) {
		Log.i("bb","算路成功回调。");
	}

	@Override
	public void notifyParallelRoad(int i) {
		Log.i("bb","通知当前是否显示平行路切换。");
	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
//		Log.i("bb","OnUpdateTrafficFacility 已过时。  。");
	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
		Log.i("bb","巡航模式（无路线规划）下，道路设施信息更新回调。");
	}

	@Override
	public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
//		Log.i("bb","OnUpdateTrafficFacility 已过时。");
	}

	@Override
	public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
		Log.i("bb","巡航模式（无路线规划）下，统计信息更新回调 连续5个点大于15km/h后开始回调。");
	}

	@Override
	public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
		Log.i("bb","巡航模式（无路线规划）下，统计信息更新回调 当拥堵长度大于500米且拥堵时间大于5分钟时回调.。");
	}

	@Override
	public void onPlayRing(int i) {
		Log.i("bb","回调各种类型的提示音，类似高德导航\"叮\".。");
	}

	@Override
	public void onNaviSetting() {
		Log.i("bb","界面右下角功能设置按钮的回调接口。。");
	}

	@Override
	public void onNaviCancel() {
		Log.i("bb","导航页面左下角返回按钮点击后弹出的『退出导航对话框』中选择『确定』后的回调接口。。");
	}

	@Override
	public boolean onNaviBackClick() {
		Log.i("bb","导航页面左下角返回按钮的回调接口 false-由SDK主动弹出『退出导航』对话框，true-SDK不主动弹出『退出导航对话框』，由用户自定义。");
		return false;
	}

	@Override
	public void onNaviMapMode(int i) {
		Log.i("bb","导航界面地图状态的回调。。");
	}

	@Override
	public void onNaviTurnClick() {
		Log.i("bb","界面左上角转向操作的点击回调。。");
	}

	@Override
	public void onNextRoadClick() {
		Log.i("bb","界面下一道路名称的点击回调。。");
	}

	@Override
	public void onScanViewButtonClick() {
		Log.i("bb","界面全览按钮的点击回调。。");
	}

	@Override
	public void onLockMap(boolean b) {
		Log.i("bb","是否锁定地图的回调。。");
	}

	@Override
	public void onNaviViewLoaded() {
		Log.i("bb","导航view加载完成回调。。");
	}

	@Override
	public void onPointerCaptureChanged(boolean hasCapture) {
		Log.i("bb","onPointerCaptureChanged。");
	}
}
