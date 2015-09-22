package com.izor066.android.blocspot;

import android.app.Application;

import com.izor066.android.blocspot.api.DataSource;

/**
 * Created by igor on 22/9/15.
 */
public class BlocSpotApplication  extends Application {

    public static BlocSpotApplication getSharedInstance() {
        return sharedInstance;
    }

    public static DataSource getSharedDataSource() {
        return BlocSpotApplication.getSharedInstance().getDataSource();
    }

    private static BlocSpotApplication sharedInstance;
    private DataSource dataSource;

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
