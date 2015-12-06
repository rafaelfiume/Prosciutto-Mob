package com.rafaelfiume.prosciutto.adviser;

import android.content.Context;
import android.view.View;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ProductAdapterTest {

    private Product aFatSalume = new Product("A Fat Salume", "", "", "59,00");
    private ProductAdapter adapter;

    @Before
    public void setUp() {
        // Roboletric is blowing up right here ...
        // java.lang.IllegalStateException: You need to use a Theme.AppCompat theme (or descendant) with this activity.
        // TODO RF 05/12/15 Come back and address this issue
        final Context context1 = Robolectric.setupActivity(AdviserActivity.class);
        this.adapter = new ProductAdapter(context1){{
            add(aFatSalume);
        }};
    }

    @Ignore
    @Test
    public void shouldShowProductDetailsWhenUserClicksOnItem() {
        final View view = adapter.getView(0, null, null);

        // when
        view.performClick();

        // then
        //verify(context).startActivity(captor.capture());
        //assertThat(captor.getValue().getStringExtra(EXTRA_MESSAGE), is("59,00"));
    }

}
