package com.pixys.android.systemui;

import android.content.Context;

import com.pixys.android.systemui.dagger.PixysGlobalRootComponent;
import com.pixys.android.systemui.dagger.DaggerPixysGlobalRootComponent;

import com.android.systemui.SystemUIFactory;
import com.android.systemui.dagger.GlobalRootComponent;

public class PixysSystemUIFactory extends SystemUIFactory {
    @Override
    protected GlobalRootComponent buildGlobalRootComponent(Context context) {
        return DaggerPixysGlobalRootComponent.builder()
                .context(context)
                .build();
    }
}
