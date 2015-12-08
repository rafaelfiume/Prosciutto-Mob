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

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse;

public class ShowAdvisedProductDetailsTest {

    @Rule
    public ActivityTestRule<ShowAdvisedProductDetails> mActivityRule =
            new ActivityTestRule<ShowAdvisedProductDetails>(ShowAdvisedProductDetails.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    return ShowAdvisedProductDetails.newIntent(targetContext, mainSuggestion());
                }
            };

    @Test
    public void appDisplaysProductDetailsWhenReceivingItFromAnIntent() {
        // when activity received a product with an intent (check priming above)

        // then
        onView(withId(R.id.p_detail_name)).check(matches(withText(mainSuggestion().name())));
        onView(withId(R.id.p_detail_price)).check(matches(withText(mainSuggestion().price())));
        onView(withId(R.id.p_detail_reputation)).check(matches(withText(mainSuggestion().reputation())));
        onView(withId(R.id.p_detail_fat)).check(matches(withText(mainSuggestion().fatPercentage())));
    }

    private Product mainSuggestion() {
        return allSuggestedProductsForCustomer().get(0);
    }

    private List<Product> allSuggestedProductsForCustomer() {
        final List<Product> suggestions;
        try {
            suggestions = new ProductAdviserParser().parse(supplierAdviceForExpertResponse());
        } catch (Exception e) {
            throw new RuntimeException("test setup failed", e);
        }
        suggestions.add(new Product("Salame Colonial", "EUR 49,23", "Artesanal", "29,00"));
        suggestions.add(new Product("Salame da Fazenda", "EUR 48,45", "Artesanal", "27,00"));
        return suggestions;
    }

}
