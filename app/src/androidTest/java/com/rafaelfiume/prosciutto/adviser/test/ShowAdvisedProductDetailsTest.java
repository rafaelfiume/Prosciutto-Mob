package com.rafaelfiume.prosciutto.adviser.test;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.rafaelfiume.prosciutto.adviser.Product;
import com.rafaelfiume.prosciutto.adviser.ProductAdviserParser;
import com.rafaelfiume.prosciutto.adviser.R;
import com.rafaelfiume.prosciutto.adviser.ShowAdvisedProductDetails;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rafaelfiume.prosciutto.adviser.ProductAdapter.EXTRA_MESSAGE;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse;

public class ShowAdvisedProductDetailsTest {

    private final Product advisedProduct;

    @Rule
    public ActivityTestRule<ShowAdvisedProductDetails> mActivityRule =
            new ActivityTestRule(ShowAdvisedProductDetails.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, ShowAdvisedProductDetails.class);
                    result.putExtra(EXTRA_MESSAGE, advisedProduct);
                    return result;
                }
            };

    public ShowAdvisedProductDetailsTest() throws Exception {
        this.advisedProduct = new ProductAdviserParser().parse(supplierAdviceForExpertResponse()).get(0);
    }

    @Test
    public void appDisplaysProductDetailsWhenReceivingItFromAnIntent() {
        // when activity received a product with an intent (check priming above)

        // then
        onView(withId(R.id.p_detail_name)).check(matches(withText(advisedProduct.name())));
        onView(withId(R.id.p_detail_price)).check(matches(withText(advisedProduct.price())));
        onView(withId(R.id.p_detail_reputation)).check(matches(withText(advisedProduct.reputation())));
        onView(withId(R.id.p_detail_fat)).check(matches(withText(advisedProduct.fatPercentage())));
    }

}
