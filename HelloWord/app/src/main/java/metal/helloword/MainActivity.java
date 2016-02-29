package metal.helloword;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import metal.helloword.data.AppContext;
import metal.helloword.views.BuyFragment;
import metal.helloword.views.CoastsFragment;


public class MainActivity extends FragmentActivity {

    String LOG_TAG = "MainActivity";

    private FragmentTabHost tabHost;

    private Bundle parameters;

    /**
     * Запуск приложения.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "onCreate");

        // Устанавливаем вьюху
        setContentView(R.layout.activity_main);

        // Получаем входящие параметры
        this.parameters = getIntent().getExtras();

        // Создание таб
        createTabs();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onStop(){
        // Закрытие контекста базы
        AppContext.Current.close();

        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");

        // Создание контекста базы
        AppContext.Current = new AppContext(this);
    }

    /**
     * Создание меню
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Получение ид нажатого меню
        int id = item.getItemId();

        // Переход к меню
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Создание таб
     */
    private void createTabs() {

        tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tabHost.addTab(tabHost.newTabSpec("source1").setIndicator("Траты"),
               CoastsFragment.class, this.parameters);

        tabHost.addTab(tabHost.newTabSpec("source2").setIndicator("План"),
                BuyFragment.class, this.parameters);
    }
}
