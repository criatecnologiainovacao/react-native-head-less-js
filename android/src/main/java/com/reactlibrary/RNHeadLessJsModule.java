
package com.reactlibrary;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
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

  private NetworkChangeReceiver reciver;

  public RNHeadLessJsModule(@Nonnull ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    createNotificationChannel();
  }

  @Override
  public String getName() {
    return "HeadLessJs";
  }

  @ReactMethod
  public void startService(int idMunicipio) {
    reciver = new NetworkChangeReceiver();
    IntentFilter filter = new IntentFilter();
    filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    this.reactContext.registerReceiver(reciver, filter);
  }

  @ReactMethod
  public void stopService() {
    this.reactContext.stopService(new Intent(this.reactContext, HeadLessJsService.class));
  }

  @ReactMethod
  public void stopReceiver() {
    this.reactContext.stopService(new Intent(this.reactContext, HeadLessJsService.class));
  }

  private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      int importance = NotificationManager.IMPORTANCE_LOW;
      NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "", importance);
      NotificationManager notificationManager = this.reactContext.getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }
  }

  @ReactMethod
  public void incrementProgress(String contentText, int progress, int lengthFile) {
    incrementProgressNotification(contentText, progress, lengthFile);
  }

  private void incrementProgressNotification(String contentText, int progress, int lengthFile) {
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getReactApplicationContext());
    Integer percent = (progress * 100) / lengthFile;
    Intent notificationIntent = new Intent(getReactApplicationContext(), MainActivity.class);
    PendingIntent contentIntent = PendingIntent.getActivity(getReactApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    NotificationCompat.Builder notification = new NotificationCompat.Builder(getReactApplicationContext(), CHANNEL_ID)
      .setContentTitle("Baixando")
      .setContentText(contentText)
      .setLargeIcon(BitmapFactory.decodeResource(reactContext.getResources(), R.drawable.ic_launcher_round))
      .setSmallIcon(android.R.drawable.stat_sys_download)
      .setContentIntent(contentIntent)
      .setPriority(NotificationCompat.PRIORITY_LOW)
      .setOnlyAlertOnce(true)
      .setAutoCancel(true)
      .setProgress(lengthFile, progress, false);
    if (percent < 99 ) {
      notificationManager.notify(SERVICE_NOTIFICATION_ID, notification.build());
    }
  }

}
