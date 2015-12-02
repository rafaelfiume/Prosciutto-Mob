package com.rafaelfiume.prosciutto.adviser;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import java.util.List;

import static com.rafaelfiume.prosciutto.adviser.ProductAdiviserParserTest.ProductMatcher.isAProductNamed;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceResponse;
import static java.lang.String.format;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ProductAdiviserParserTest {

    private final ProductAdiviserParser parser = new ProductAdiviserParser();

    @Test
    public void additionIsCorrect() throws Exception {
        // when
        List<Product> suggestedProducts = parser.parse(supplierAdviceResponse());

        // then
        assertThat(suggestedProducts, hasSize(2));

        assertThat(firstOf(suggestedProducts),
                isAProductNamed("Traditional Salume", costing("EUR 41,60"), regardedAs("traditional"), withFatPercentageOf("37,00")));

        assertThat(secondOf(suggestedProducts),
                isAProductNamed("Premium Salume", costing("EUR 73,23"), regardedAs("traditional"), withFatPercentageOf("38,00")));
    }

    private String costing(String s) {
        return s;
    }

    private String regardedAs(String s) {
        return s;
    }

    private String withFatPercentageOf(String s) {
        return s;
    }

    private Product firstOf(List<Product> suggestedProducts) {
        return suggestedProducts.get(0);
    }

    private Product secondOf(List<Product> suggestedProducts) {
        return suggestedProducts.get(1);
    }

    public static class ProductMatcher extends TypeSafeMatcher<Product> {

        private final String name;
        private final String price;
        private final String reputation;
        private final String fatPercentage;

        public ProductMatcher(String name, String price, String reputation, String fatPercentage) {
            this.name = name;
            this.price = price;
            this.reputation = reputation;
            this.fatPercentage = fatPercentage;
        }

        static Matcher<? super Product> isAProductNamed(String name, String price, String reputation, String fatPercentage) {
            return new ProductMatcher(name, price, reputation, fatPercentage);
        }

        @Override
        protected boolean matchesSafely(Product actualProduct) {
            return name.equals(actualProduct.name())
                    && price.equals(actualProduct.price())
                    && reputation.equals(actualProduct.reputation())
                    && fatPercentage.equals(actualProduct.fatPercentage());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(format(
                    "a product with: name \"%s\"; price \"%s\"; reputation \"%s\"; fatPercentage \"%s\"",
                    name, price, reputation, fatPercentage
            ));
        }

        @Override
        protected void describeMismatchSafely(Product actual, Description mismatchDescription) {
            mismatchDescription.appendText(format(
                    "product had name \"%s\"; price \"%s\"; reputation \"%s\"; fatPercentage \"%s\"",
                    actual.name(), actual.price(), actual.reputation(), actual.fatPercentage()
            ));
        }
    }
}