package com.rafaelfiume.prosciutto.adviser;

import com.rafaelfiume.prosciutto.adviser.domain.ProductDescription;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProductDescriptionParserTest {

    private final String description = "La lunghezza è di 15 cm e il diametro di 4 cm; la stagionatura dura 20/25 giorni.\n" +
            "^ Bastardei, su Italia a Tavola. URL consultato il 26/3/2015.\n" +
            "^ a b Regione Lombardia, Atlante dei prodotti tipici e tradizionali, Regione Lombardia, 2014.";

    private final ProductDescriptionParser underTest = new ProductDescriptionParser();

    @Test
    public void shouldParseDescriptionXmlFromWikipedia() {
        // when
        ProductDescription desc = underTest.parse(xmlFromWikipediaWithDescription(description));

        // then
        assertThat(desc.value(), is(description));
    }

    private String xmlFromWikipediaWithDescription(String description) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<api batchcomplete=\"\">\n" +
                "   <query>\n" +
                "      <pages>\n" +
                "         <page _idx=\"99688\" pageid=\"99688\" ns=\"0\" title=\"'Nduja\">\n" +
                "            <extract xml:space=\"preserve\">" + description + "</extract>\n" +
                "         </page>\n" +
                "      </pages>\n" +
                "   </query>\n" +
                "</api>";
    }

}
