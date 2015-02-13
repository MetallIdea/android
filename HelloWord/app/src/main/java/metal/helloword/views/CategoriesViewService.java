package metal.helloword.views;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by smetalnikov on 12.02.2015.
 */
public class CategoriesViewService  extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CategoriesFactory(getApplicationContext(), intent);
    }

}