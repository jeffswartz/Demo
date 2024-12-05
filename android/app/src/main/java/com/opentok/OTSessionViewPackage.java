package com.opentok;

import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.ViewManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OTSessionViewPackage extends TurboReactPackage {
  @Override
  public List<ViewManager<?, ?>> createViewManagers(ReactApplicationContext reactContext) {
    return Collections.singletonList(new OTSessionViewManager(reactContext));
  }

  @Override
  public NativeModule getModule(String s, ReactApplicationContext reactApplicationContext) {
    if (OTSessionViewManager.REACT_CLASS.equals(s)) {
      return new OTSessionViewManager(reactApplicationContext);
    }
    return null;
  }

  @Override
  public ReactModuleInfoProvider getReactModuleInfoProvider() {
    return new ReactModuleInfoProvider() {
      @Override
      public Map<String, ReactModuleInfo> get() {
        Map<String, ReactModuleInfo> map = new HashMap<>();
        map.put(OTSessionViewManager.REACT_CLASS, new ReactModuleInfo(
                OTSessionViewManager.REACT_CLASS, // name
                OTSessionViewManager.REACT_CLASS, // className
                false,                           // canOverrideExistingModule
                false,                           // needsEagerInit
                false,                           // isCxxModule
                true                             // isTurboModule
        ));
        return map;
      }
    };
  }
}
