
package com.reactlibrary;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import javax.annotation.Nonnull;

public class RNHeadLessJsModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  private static final int SERVICE_NOTIFICATION_ID = 12345;
  private static final String CHANNEL_ID = "HEADLESSJS";
  int PROGRESS_MAX = 151;
  int PROGRESS_CURRENT = 0;

  public RNHeadLessJsModule(@Nonnull ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "HeadLessJs";
  }

  @ReactMethod
  public void startService() {
    this.reactContext.startService(new Intent(this.reactContext, HeadLessJsService.class));
  }

  @ReactMethod
  public void stopService() {
    this.reactContext.stopService(new Intent(this.reactContext, HeadLessJsService.class));
  }

  private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      int importance = NotificationManager.IMPORTANCE_LOW;
      NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "HEARTBEAT", importance);
      channel.setSound(null,null);
      NotificationManager notificationManager = this.reactContext.getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }
  }

  @ReactMethod
  public void incrementProgress(int progress, final Promise promise) {
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getReactApplicationContext());
    createNotificationChannel();
    Intent notificationIntent = new Intent(getReactApplicationContext(), MainActivity.class);
    PendingIntent contentIntent = PendingIntent.getActivity(getReactApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    NotificationCompat.Builder notification = new NotificationCompat.Builder(getReactApplicationContext(), CHANNEL_ID)
            .setContentTitle("Fazendo download das imagens de Palmas")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(contentIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOnlyAlertOnce(true)
            .setProgress(PROGRESS_MAX, progress, false);
    notificationManager.notify(SERVICE_NOTIFICATION_ID, notification.build());
    promise.resolve(null);
  }

}
