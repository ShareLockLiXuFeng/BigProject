package com.sanbafule.bigproject.biz.net.connection.biz;

import android.content.Context;

import com.sanbafule.bigproject.biz.net.connection.netinterface.TestNetListenner;
import com.sanbafule.bigproject.biz.net.connection.uitl.TestConnectionUitl;

/**
 * Created by Administrator on 2016/8/1.
 */
public class netbiz implements  TestNetListenner  {

    public void testNetBiz( Context context,TestNetListenner listenner){

        if(TestConnectionUitl.checkNetworkState(context)){
            listenner.doYourSelfThing();
        }else {
            listenner.settingNet();
        }

    }


    
    @Override
    public void settingNet() {

    }

    @Override
    public void doYourSelfThing() {

    }
}
