package ru.gelin.android.weather.notification.skin;

import ru.gelin.android.weather.Weather;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

/**
 *  Broadcast receiver which receives the weather updates.
 *  <p>
 *  The receiver should:
 *  <ul>
 *  <li>display the Weather passed in the start intent extra using {@link NotificationManager}</li>
 *  <li>hide the weather notification if necessary</li>
 *  </ul>
 *  The intent, passed to the receiver, has action {@link #ACTION_WEATHER_UPDATE}.
 *  The Weather Notification finds the broadcast receivers which accepts this intent type 
 *  as weather notification skins.
 *  <p>
 *  The intent contains the extras:
 *  <ul>
 *  <li>{@link #EXTRA_WEATHER} holds updated {@link Weather}</li>
 *  <li>{@link #EXTRA_ENABLE_NOTIFICATION} holds boolean flag about notification state,
 *      if false the weather notification should be hidden.</li>
 *  </ul>
 *   The intent is sent to the receiver each time the weather notification 
 *   should be updated or cleared. This can happen not on weather update, 
 *   but also when the specified skin is enabled or disabled.
 */
public abstract class WeatherNotificationReceiver extends BroadcastReceiver {

    /** Intent action which should be accepted by the receiver */ 
    public static final String ACTION_WEATHER_UPDATE =
        WeatherNotificationReceiver.class.getPackage().getName() + ".ACTION_WEATHER_UPDATE";
    /** Intent extra which contains {@link Weather} */ 
    public static final String EXTRA_WEATHER =
        WeatherNotificationReceiver.class.getPackage().getName() + ".EXTRA_WEATHER";
    /** Intent extra which contains boolean flag */ 
    public static final String EXTRA_ENABLE_NOTIFICATION =
        WeatherNotificationReceiver.class.getPackage().getName() + ".EXTRA_ENABLE_NOTIFICATION";
    
    /**
     *  Verifies the intent, extracts extras, calls {@link #notify} or {@link #cancel} methods.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        if (!ACTION_WEATHER_UPDATE.equals(intent.getAction())) {
            return;
        }
        boolean enabled = intent.getBooleanExtra(EXTRA_ENABLE_NOTIFICATION, true);
        if (enabled) {
            Parcelable weather = intent.getParcelableExtra(EXTRA_WEATHER);
            if (!(weather instanceof Weather)) {
                return;
            }
            notify(context, (Weather)weather);
        } else {
            cancel(context);
        }
    }
    
    /**
     *  Is called when a new weather value is received.
     */
    protected abstract void notify(Context context, Weather weather);
    
    /**
     *  Is called when a weather notification should be canceled.
     */
    protected abstract void cancel(Context context);

}