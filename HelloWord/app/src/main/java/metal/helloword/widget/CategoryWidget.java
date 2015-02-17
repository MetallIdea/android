package metal.helloword.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Arrays;

import metal.helloword.R;
import metal.helloword.data.AppContext;
import metal.helloword.views.CategoriesViewService;

/**
 * Created by Ленуська on 05.02.2015.
 */
public class CategoryWidget extends AppWidgetProvider {

    final String LOG_TAG = "myLogs";

    final String ACTION_ON_CLICK = "ru.startandroid.develop.p1211listwidget.itemonclick";

    final static String ITEM_POSITION = "item_position";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

        Log.d(LOG_TAG, "onEnabled");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        if(AppContext.Current == null){
            AppContext.Current = new AppContext(context);
        }

        for (int id : appWidgetIds) {
            updateWidget(context, appWidgetManager, id);
        }

        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(LOG_TAG, "onDisabled");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equalsIgnoreCase(ACTION_ON_CLICK)) {
            Toast.makeText(context, "Clicked on item ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    void updateWidget(Context context, AppWidgetManager appWidgetManager,
                      int appWidgetId) {
        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.widget);

        setList(rv, context, appWidgetId);

        setListClick(rv, context, appWidgetId);

        appWidgetManager.updateAppWidget(appWidgetId, rv);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,
                R.id.categoriesView);
    }

    void setList(RemoteViews rv, Context context, int appWidgetId) {
        Intent adapter = new Intent(context, CategoriesViewService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        rv.setRemoteAdapter(R.id.categoriesView, adapter);
    }

    void setListClick(RemoteViews rv, Context context, int appWidgetId) {
        Intent listClickIntent = new Intent(context, CategoryWidget.class);
        listClickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        listClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                new int[] { appWidgetId });
        listClickIntent.putExtra(ACTION_ON_CLICK, "");
        PendingIntent listClickPIntent = PendingIntent.getBroadcast(context, appWidgetId,
                listClickIntent, 0);
        //rv.setPendingIntentTemplate(R.id.categoryNew, listClickPIntent);
    }
}