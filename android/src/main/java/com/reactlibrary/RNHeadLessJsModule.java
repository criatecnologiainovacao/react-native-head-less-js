
package com.reactlibrary;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import javax.annotation.Nonnull;

public class RNHeadLessJsModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

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

}
