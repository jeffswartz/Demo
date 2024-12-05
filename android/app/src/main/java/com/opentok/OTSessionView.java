package com.opentok;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView; 

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.events.Event;

public class OTSessionView extends TextView {
  public OTSessionView(Context context) {
    super(context);
    configureComponent();
  }

  private void configureComponent() {
    // this.setText(R.string.user_greeting);
  }

}
