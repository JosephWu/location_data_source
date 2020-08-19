package com.here.android.example.basicpositioningsolution;

import android.app.Activity;
import android.location.Location;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.here.android.mpa.common.LocationDataSource;
import com.here.android.mpa.common.PositioningManager;

public class LocationDataSourceFake extends LocationDataSource {

    private Activity mActivity;
    private Location mLocation = null;
    private static double DIFF = 0.000005;
    private int counter = 0;

    Handler m_Handler = new Handler();
    Runnable mRunnable = new Runnable(){
        @Override
        public void run() {
//            mLocation = getLastKnownLocation();
            if(mLocation!= null) {
                double offset = counter * DIFF;
                mLocation.setLatitude(25.0118 + offset);
                mLocation.setLongitude(121.5108 + offset);
                counter += 1;
            }
            LocationDataSourceFake.super.onLocationUpdated(PositioningManager.LocationMethod.GPS_NETWORK, mLocation);
            m_Handler.postDelayed(mRunnable, 1000);
        }
    };

    // Singleton
    private static LocationDataSourceFake INSTANCE = null;

    public LocationDataSourceFake() {
        mLocation = new Location("fakeProvider");
        mRunnable.run();
    }

    public LocationDataSourceFake(Activity activity) {
        this.mActivity = activity;
        mLocation = new Location("fakeProvider");
        mRunnable.run();
    }

    public static LocationDataSourceFake getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocationDataSourceFake();
        }
        return (INSTANCE);
    }

    @Override
    public boolean start(PositioningManager.LocationMethod locationMethod) {
        mRunnable.run();
        return true;
    }

    @Override
    public void stop() {
    }

    @Override
    public int getGpsStatus() {
        return 0;
    }

    @Override
    public int getNetworkStatus() {
        return 0;
    }

    @Override
    public int getIndoorStatus() {
        return 0;
    }

    @Nullable
    @Override
    public Location getLastKnownLocation() {
        return mLocation;
    }
}
