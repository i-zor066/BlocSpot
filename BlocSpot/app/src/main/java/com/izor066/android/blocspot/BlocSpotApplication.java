package com.izor066.android.blocspot;

import android.app.Application;

import com.izor066.android.blocspot.api.DataSource;

/**
 * Created by igor on 22/9/15.
 */
public class BlocSpotApplication  extends Application {

    private static BlocSpotApplication sharedInstance;
    private DataSource dataSource;

    public static BlocSpotApplication getSharedInstance() {
        return sharedInstance;
    }

    public static DataSource getSharedDataSource() {
        return BlocSpotApplication.getSharedInstance().getDataSource();
    }



    @Override
    public void onCreate() {
        super.onCreate();
        sharedInstance = this;
        dataSource = new DataSource(this);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
