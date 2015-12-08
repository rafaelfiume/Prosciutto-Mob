package com.rafaelfiume.prosciutto.adviser;

import android.app.Activity;
import android.view.View;

// Use an interface when having more factories like this one?
public class OnSuggestedProductClickListenerFactory {

    private final Activity callingActivity;

    public OnSuggestedProductClickListenerFactory(Activity callingActivity) {
        this.callingActivity = callingActivity;
    }

    public View.OnClickListener newOnClickListenerFor(final Product product) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAdvisedProductDetails.navigate(callingActivity, product);
            }
        };
    }
}
