package com.rafaelfiume.prosciutto.adviser.test;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.rafaelfiume.prosciutto.adviser.ProductAdviserParser;
import com.rafaelfiume.prosciutto.adviser.R;
import com.rafaelfiume.prosciutto.adviser.ShowAdvisedProductDetailsActivity;
import com.rafaelfiume.prosciutto.adviser.domain.Product;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse;
import static org.hamcrest.Matchers.containsString;

public class ShowAdvisedProductDetailsActivityTest {

    @Rule
    public ActivityTestRule<ShowAdvisedProductDetailsActivity> mActivityRule =
            new ActivityTestRule<ShowAdvisedProductDetailsActivity>(ShowAdvisedProductDetailsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    return ShowAdvisedProductDetailsActivity.Companion.newIntent(targetContext, mainSuggestion());
                }
            };

    @Test // TODO Check also the labels
    public void appDisplaysProductDetailsWhenReceivingThemFromAnIntent() throws InterruptedException {
        // when activity received a product with an intent (check priming above)

        // then the main suggested product is...
        onView(withId(R.id.p_detail_name)).check(matches(withText(mainSuggestion().name())));
        onView(withId(R.id.p_detail_price)).check(matches(withText(mainSuggestion().price())));
        onView(withId(R.id.p_detail_reputation)).check(matches(withText(mainSuggestion().reputation())));
        onView(withId(R.id.p_detail_fat)).check(matches(withText(mainSuggestion().fatPercentage())));

        // then show product description...
        onView(withId(R.id.description_label)).check(matches(withText(containsString("About the 'Nduja Variety:"))));
        onView(withId(R.id.p_detail_description)).check(matches(withText(containsString("La 'nduja è un salume Calabrese di consistenza morbida e dal gusto particolarmente piccante."))));
    }

    private Product mainSuggestion() {
        return allSuggestedProductsForCustomer().get(0);
    }

    private List<Product> allSuggestedProductsForCustomer() {
        return new ArrayList<Product>() {{
                add(new Product("Salame Colonial", "'Nduja", "EUR 49,23", "traditional", "29,00", "http://image.url", "https://it.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles='Nduja"));
                add(new Product("Salame da Fazenda", "Chorizo", "EUR 48,45", "special", "27,00", "http://image.url", ""));
                add(new Product("Salame da Embolorado Não Faz Mal", "Salame Brianza D.O.P.", "EUR 33,33", "special", "27,00", "http://image.url", ""));
                addAll(new ProductAdviserParser().parse(supplierAdviceForExpertResponse()));
            }};
    }

}
