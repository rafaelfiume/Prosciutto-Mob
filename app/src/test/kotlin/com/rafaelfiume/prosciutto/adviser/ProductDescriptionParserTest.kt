package com.rafaelfiume.prosciutto.adviser

import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class ProductDescriptionParserTest {

    private val description = """
            |La lunghezza è di 15 cm e il diametro di 4 cm; la stagionatura dura 20/25 giorni.
            |Bastardei, su Italia a Tavola. URL consultato il 26/3/2015.
            |Regione Lombardia, Atlante dei prodotti tipici e tradizionali, Regione Lombardia, 2014.
            """.trimMargin()

    private val underTest = ProductDescriptionParser()

    @Test
    fun shouldParseDescriptionXmlFromWikipedia() {
        // when
        val desc = underTest.parse(xmlFromWikipediaWithDescription(description))

        // then
        assertThat(desc.value, `is`(description))
    }

    private fun xmlFromWikipediaWithDescription(description: String): String {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<api batchcomplete=\"\">\n" +
                "   <query>\n" +
                "      <pages>\n" +
                "         <page _idx=\"99688\" pageid=\"99688\" ns=\"0\" title=\"'Nduja\">\n" +
                "            <extract xml:space=\"preserve\">$description</extract>\n" +
                "         </page>\n" +
                "      </pages>\n" +
                "   </query>\n" +
                "</api>"
    }

}
