package com.rafaelfiume.prosciutto.adviser;

import com.rafaelfiume.prosciutto.adviser.domain.Product;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import java.util.List;

import static com.rafaelfiume.prosciutto.adviser.ProductAdviserParserTest.ProductMatcher.isAProductNamed;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse;
import static java.lang.String.format;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ProductAdviserParserTest {

    private final ProductAdviserParser parser = new ProductAdviserParser();

    @Test
    public void shouldParseAdviceXmlIntoListOfSuggestedProducts() {
        // when
        List<Product> suggestedProducts = parser.parse(supplierAdviceForExpertResponse());

        // then
        assertThat(suggestedProducts, hasSize(2));

        assertThat(firstOf(suggestedProducts),
                isAProductNamed("(Traditional Less Expensive) Salume",
                        withVariety("Chorizo"),
                        costing("EUR 41,60"),
                        regardedAs("traditional"),
                        withFatPercentageOf("37,00"),
                        withDescriptionUrl("https://it.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles=Chorizo")));

        assertThat(secondOf(suggestedProducts),
                isAProductNamed("(Traditional More Expensive) Premium",
                        withVariety("Chorizo"),
                        costing("EUR 73,23"),
                        regardedAs("traditional"),
                        withFatPercentageOf("38,00"),
                        withDescriptionUrl("https://it.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles=Chorizo")));
    }

    private String withVariety(String s)         { return s; }
    private String costing(String s)             { return s; }
    private String regardedAs(String s)          { return s; }
    private String withFatPercentageOf(String s) { return s; }
    private String withDescriptionUrl(String s) { return s; }

    private Product firstOf(List<Product> suggestedProducts)  { return suggestedProducts.get(0); }
    private Product secondOf(List<Product> suggestedProducts) { return suggestedProducts.get(1); }

    public static class ProductMatcher extends TypeSafeMatcher<Product> {

        private final String name;
        private final String variety;
        private final String price;
        private final String reputation;
        private final String fatPercentage;
        private final String descriptionUrl;

        public ProductMatcher(String name, String variety, String price, String reputation, String fatPercentage, String descriptionUrl) {
            this.name = name;
            this.variety = variety;
            this.price = price;
            this.reputation = reputation;
            this.fatPercentage = fatPercentage;
            this.descriptionUrl = descriptionUrl;
        }

        static Matcher<? super Product> isAProductNamed(
                String name, String variety, String price, String reputation, String fatPercentage, String descriptionUrl) {

            return new ProductMatcher(name, variety, price, reputation, fatPercentage, descriptionUrl);
        }

        @Override
        protected boolean matchesSafely(Product actualProduct) {
            return name.equals(actualProduct.name())
                    && variety.equals(actualProduct.variety())
                    && price.equals(actualProduct.price())
                    && reputation.equals(actualProduct.reputation())
                    && fatPercentage.equals(actualProduct.fatPercentage())
                    && descriptionUrl.equals(actualProduct.descriptionUrl());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(format(
                    "a product with: name \"%s\"; variety \"%s\"; price \"%s\"; reputation \"%s\"; fatPercentage \"%s\"; descriptionUrl \"%s\"",
                    name, variety, price, reputation, fatPercentage, descriptionUrl
            ));
        }

        @Override
        protected void describeMismatchSafely(Product actual, Description mismatchDescription) {
            mismatchDescription.appendText(format(
                    "product had name \"%s\"; variety \"%s\"; price \"%s\"; reputation \"%s\"; fatPercentage \"%s\"; descriptionUrl \"%s\"",
                    actual.name(), actual.variety(), actual.price(), actual.reputation(), actual.fatPercentage(), actual.descriptionUrl()
            ));
        }
    }
}