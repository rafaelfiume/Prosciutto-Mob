package com.rafaelfiume.prosciutto.adviser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery;

import java.util.ArrayList;
import java.util.List;

import static com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.EXPERT;
import static com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.GOURMET;
import static com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.HEALTHY;
import static com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.MAGIC;

public class AdviserActivity extends AppCompatActivity {

    private static final String LIST_OF_RECOMENDED_PRODUCTS = "recommended_products";

    private ProductAdviserQuery query;

    private ProductAdapter adapter;

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
        setTitle(R.string.activity_title);

        final ListView listView = (ListView) findViewById(R.id.products_list);
        this.adapter = new ProductAdapter(this, new OnSuggestedProductClickListenerFactory(this));
        listView.setAdapter(this.adapter);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetProductAdvice().execute();
            }
        });

        setMagicOptionSelectedByDefault();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adviser, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList(LIST_OF_RECOMENDED_PRODUCTS, adapter.content());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        this.adapter.addAll(savedInstanceState.<Product>getParcelableArrayList(LIST_OF_RECOMENDED_PRODUCTS));
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

    private void setMagicOptionSelectedByDefault() {
        final RadioGroup options = (RadioGroup) findViewById(R.id.profile_options);
        options.check(R.id.magic_option);

        this.query = MAGIC;
    }

    class GetProductAdvice extends AsyncTask<Void, Void, List<Product>> {

        private final Snackbar requestingAdviceMessage;
        private final Snackbar failedMessage;

        private boolean taskFailed = false;

        GetProductAdvice() {
            this.requestingAdviceMessage = Snackbar.make(findViewById(R.id.fab), "Asking for advice...", Snackbar.LENGTH_INDEFINITE);
            this.failedMessage = Snackbar
                    .make(findViewById(R.id.fab), "Failed", Snackbar.LENGTH_LONG)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new GetProductAdvice().execute();
                        }
                    }); // display option for user to retry

            //this.requestingAdviceMessage.setCallback(new Snackbar.Callback() {
            //  @Override
            //  public void onDismissed(Snackbar snackbar, int event) {
            //      if (taskFailed) {
            //          failedMessage.show();
            //      }
            //  }
            //});
        }

        @Override
        protected void onPreExecute() {
            cleanSuggestedProductsList();
            requestingAdviceMessage.show();
        }

        @Override
        protected List<Product> doInBackground(Void... nothing) {
            try {
                return query.suggestedProducts();

            } catch (Exception e) {
                Log.e(AdviserActivity.class.getName(), "error when querying salume supplier", e);
                this.taskFailed = true;
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            requestingAdviceMessage.dismiss();
            updateSuggestedProductsList(products);
            if (taskFailed) {
                failedMessage.show();
            }
        }

        private void updateSuggestedProductsList(List<Product> products) {
            cleanSuggestedProductsList();
            adapter.addAll(products);
        }

        private void cleanSuggestedProductsList() {
            adapter.clear();
        }

    }

}
