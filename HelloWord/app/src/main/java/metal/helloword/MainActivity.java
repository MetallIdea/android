package metal.helloword;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import metal.helloword.data.AppContext;
import metal.helloword.widget.CategoryWidget;


public class MainActivity extends FragmentActivity {

    String LOG_TAG = "MainActivity";

    private FragmentTabHost tabHost;

    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "onCreate");

        setContentView(R.layout.activity_main);

        Bundle parameters = getIntent().getExtras();

        if(parameters != null) {
            categoryName = parameters.getString(CategoryWidget.CATEGORY_EXTRA_NAME);
        }

        Log.d(LOG_TAG, "recaive categoryName = " + categoryName);

        createTabs();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onStop(){
        AppContext.Current.close();

        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");

        AppContext.Current = new AppContext(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    private void createTabs() {

        tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Bundle params = new Bundle();
        params.putString(CategoryWidget.CATEGORY_EXTRA_NAME, categoryName);

        tabHost.addTab(tabHost.newTabSpec("source1").setIndicator("Нал"),
               metal.helloword.views.SourceActivity.class, params);

        tabHost.addTab(tabHost.newTabSpec("source2").setIndicator("Карта"),
                metal.helloword.views.SourceActivity.class, params);
    }
}
