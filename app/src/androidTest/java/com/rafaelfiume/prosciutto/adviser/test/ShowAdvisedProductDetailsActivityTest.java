package com.rafaelfiume.prosciutto.adviser.test;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.rafaelfiume.prosciutto.adviser.domain.Product;
import com.rafaelfiume.prosciutto.adviser.ProductAdviserParser;
import com.rafaelfiume.prosciutto.adviser.R;
import com.rafaelfiume.prosciutto.adviser.ShowAdvisedProductDetailsActivity;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse;

public class ShowAdvisedProductDetailsActivityTest {

    @Rule
    public ActivityTestRule<ShowAdvisedProductDetailsActivity> mActivityRule =
            new ActivityTestRule<ShowAdvisedProductDetailsActivity>(ShowAdvisedProductDetailsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    return ShowAdvisedProductDetailsActivity.newIntent(targetContext, mainSuggestion());
                }
            };

    @Test
    public void appDisplaysProductDetailsWhenReceivingThemFromAnIntent() {
        // when activity received a product with an intent (check priming above)

        // then the main suggested product is...
        onView(withId(R.id.p_detail_name)).check(matches(withText(mainSuggestion().name())));
        onView(withId(R.id.p_detail_price)).check(matches(withText(mainSuggestion().price())));
        onView(withId(R.id.p_detail_reputation)).check(matches(withText(mainSuggestion().reputation())));
        onView(withId(R.id.p_detail_fat)).check(matches(withText(mainSuggestion().fatPercentage())));
        
        // then show product description...

    }

    private Product mainSuggestion() {
        return allSuggestedProductsForCustomer().get(0);
    }

    private List<Product> allSuggestedProductsForCustomer() {
        return new ArrayList<Product>() {{
                add(new Product("Salame Colonial", "EUR 49,23", "Artesanal", "29,00"));
                add(new Product("Salame da Fazenda", "EUR 48,45", "Artesanal", "27,00"));
                add(new Product("Salame da Embolorado Não Faz Mal", "EUR 33,33", "Artesanal", "27,00"));
                addAll(new ProductAdviserParser().parse(supplierAdviceForExpertResponse()));
            }};
    }

}
