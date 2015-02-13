package metal.helloword.views;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import metal.helloword.R;
import metal.helloword.data.AppContext;
import metal.helloword.data.database.Categories;
import metal.helloword.data.database.Category;

/**
 * Created by smetalnikov on 12.02.2015.
 */
public class CategoriesFactory implements RemoteViewsFactory {

    ArrayList<Category> data;
    Context context;

    int widgetID;

    CategoriesFactory(Context ctx, Intent intent) {
        context = ctx;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        data = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rView = new RemoteViews(context.getPackageName(),
                R.layout.category_item);
        rView.setTextViewText(R.id.tvItemText, data.get(position).Name);
        return rView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {

        Categories categories = new Categories();

        data = categories.GetCategories();
    }

    @Override
    public void onDestroy() {

    }

}