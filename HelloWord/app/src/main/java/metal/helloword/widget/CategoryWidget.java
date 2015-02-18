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

import metal.helloword.MainActivity;
import metal.helloword.R;
import metal.helloword.data.AppContext;
import metal.helloword.views.CategoriesViewService;

/**
 * Created by Ленуська on 05.02.2015.
 */
public class CategoryWidget extends AppWidgetProvider {

    final String LOG_TAG = "myLogs";

    // String to be sent on Broadcast as soon as Data is Fetched
    // should be included on WidgetProvider manifest intent action
    // to be recognized by this WidgetProvider to receive broadcast
    public static final String DATA_FETCHED = "mypackage.RSS.DATA_FETCHED";
    public static final String EXTRA_LIST_VIEW_ROW_NUMBER = "mypackage.EXTRA_LIST_VIEW_ROW_NUMBER";

    public static final String CATEGORY_EXTRA_NAME = "METALIDEA.DIALYIDEA.CATEGORY_EXTRA_NAME";

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
        Log.d(LOG_TAG, "onReceive");
        //if item on list was clicked
        if (intent.getAction().equals(EXTRA_LIST_VIEW_ROW_NUMBER)) {
            int appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);

            //get position on listview which was clicked
            int viewIndex = intent.getIntExtra(EXTRA_LIST_VIEW_ROW_NUMBER, 0);

            Toast.makeText(context, "Clicked on position :" + viewIndex + " WidgetID " + appWidgetId,
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
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.categoriesView, startActivityPendingIntent);
    }
}