package com.sanbafule.bigproject.biz.net.connection.uitl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/8/1.
 */
public class TestConnectionUitl {
    private static ConnectivityManager manager;

    /**
     * 检测网络是否连接
     * @return
     */
    public static boolean checkNetworkState(Context context ) {
        boolean flag = false;
        //得到网络连接信息
        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }


    /**
     * 网络已经连接，然后去判断是wifi连接还是GPRS连接
     * 设置一些自己的逻辑调用
     */
    private static boolean isNetworkAvailable(){
        boolean isWifi=false;
        NetworkInfo.State gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(gprs == NetworkInfo.State.CONNECTED || gprs == NetworkInfo.State.CONNECTING){
//            Toast.makeText(this, "wifi is open! gprs", Toast.LENGTH_SHORT).show();
        }
        //判断为wifi状态下才加载广告，如果是GPRS手机网络则不加载！
        if(wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING){
//            Toast.makeText(this, "wifi is open! wifi", Toast.LENGTH_SHORT).show();
//            loadAdmob();
            isWifi=true;
        }

        return isWifi;
    }
}
