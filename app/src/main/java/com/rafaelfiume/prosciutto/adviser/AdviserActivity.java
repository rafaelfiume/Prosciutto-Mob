package com.rafaelfiume.prosciutto.adviser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery;

import java.util.List;

import static com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.EXPERT;
import static com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.GOURMET;
import static com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.HEALTHY;
import static com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.MAGIC;

public class AdviserActivity extends AppCompatActivity {

    private ProductAdviserQuery query;

    private ListView listView;

    public void onMagicRadioButtonClicked(View v) { this.query = MAGIC; }
    public void onHealthyRadioButtonClicked(View v) { this.query = HEALTHY; }
    public void onExpertRadioButtonClicked(View v) { this.query = EXPERT; }
    public void onGourmetRadioButtonClicked(View v) { this.query = GOURMET; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adviser);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.listView = (ListView) findViewById(R.id.products_list);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Requesting advice...", Snackbar.LENGTH_LONG).show();
                new GetProductAdvice().execute();
            }
        });

        setMagicQuerySelectedByDefault();
    }

    private void setMagicQuerySelectedByDefault() {
        final RadioGroup options = (RadioGroup) findViewById(R.id.profile_options);
        options.check(R.id.magic_option);

        this.query = MAGIC;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adviser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateSuggestedProductsList(List<Product> products) {
        listView.setAdapter(new ProductAdapter(this, products));
    }

    class GetProductAdvice extends AsyncTask<Void, Void, List<Product>> {

        @Override
        protected List<Product> doInBackground(Void... nothing) {
            try {
                return query.suggestedProducts();
            } catch (Exception e) {
                // Sad path not played yet
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            updateSuggestedProductsList(products);
        }
    }

}
