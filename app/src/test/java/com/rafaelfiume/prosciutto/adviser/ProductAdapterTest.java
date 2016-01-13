package com.rafaelfiume.prosciutto.adviser;

import android.content.Context;
import android.view.View;

import com.rafaelfiume.prosciutto.adviser.domain.Product;

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

    private Product aFatSalume = new Product("A Fat Salume", "Chorizo", "", "", "59,00", "http://image.url", "http://description-url");
    private ProductAdapter adapter;

    @Before
    public void setUp() {
        // Roboletric is blowing up right here ...
        // java.lang.IllegalStateException: You need to use a Theme.AppCompat theme (or descendant) with this activity.
        // TODO RF 05/12/15 Come back and address this issue
        final Context context = Robolectric.setupActivity(AdviserActivity.class);
        this.adapter = new ProductAdapter(context, null){{
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
        //assertThat(captor.getValue().getStringExtra(EXTRA_SUGGESTED_PRODUCT), is("59,00"));
    }

}
