package com.opentok;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ViewManager;

import java.util.HashMap;
import java.util.Map;

@ReactModule(name = OTSessionViewManager.REACT_CLASS)
class OTSessionViewManager extends SimpleViewManager<OTSessionView> {
  /*
  private final CustomWebViewManagerDelegate<OTSessionView, OTSessionViewManager> delegate =
          new CustomWebViewManagerDelegate<>(this);

  @Override
  public ViewManagerDelegate<OTSessionView> getDelegate() {
    return delegate;
  }
  */

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public OTSessionView createViewInstance(ThemedReactContext context) {
    return new OTSessionView(context);
  }

  @ReactProp(name = "apiKey")
  @Override
  public void setApiKey(OTSessionView view, String apiKey) {
    /*
    if (apiKey == null) {
      view.emitOnScriptLoaded(OTSessionView.OnScriptLoadedEventResult.error);
      return;
    }
    */
    // view.setString(apiKey);
  }

  @ReactProp(name = "sessionId")
  @Override
  public void setSessionId(OTSessionView view, String sessionId) {
    view.setString(sessionId);
  }

  @ReactProp(name = "token")
  @Override
  public void setToken(OTSessionView view, String token) {
    // view.setString(token);
  }

  public static final String REACT_CLASS = "CustomSessionView";
}
