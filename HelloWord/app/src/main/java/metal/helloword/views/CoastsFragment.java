package metal.helloword.views;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import metal.helloword.R;
import metal.helloword.adapters.CategoriesAutocompleteAdapter;
import metal.helloword.controls.DelayAutoCompleteTextView;
import metal.helloword.data.database.Category;
import metal.helloword.data.database.Coast;
import metal.helloword.data.database.Coasts;
import metal.helloword.widget.CategoryWidget;

/**
 * Created by smetalnikov on 03.02.2015.
 */
public class CoastsFragment extends Fragment {

    public static final SimpleDateFormat DATA_FORMAT_VIEW = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private String baseCategoryName;

    protected CoastsLoad coastLoadTask;

    protected SimpleAdapter adapter;

    protected ListView listView;

    protected EditText editText;

    protected DelayAutoCompleteTextView autocompleteCategoryText;

    /**
     * Инициализирует элементы. Загружает занчения.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Загружает фрагмент во вью
        View myFragmentView = inflater.inflate(R.layout.coasts,
                container, false);

        // Получаем параметры
        Bundle parameters = getArguments();

        if(parameters != null) {
            baseCategoryName = parameters.getString(CategoryWidget.CATEGORY_EXTRA_NAME);
        }

        listView = (ListView)myFragmentView.findViewById(R.id.listView);
        listView.setTextFilterEnabled(true);

        editText = (EditText)myFragmentView.findViewById(R.id.editText);

        autocompleteCategoryText = (DelayAutoCompleteTextView) myFragmentView.findViewById(R.id.autocompleteCategory);

        autocompleteCategoryText.setText(baseCategoryName);

        autocompleteCategoryText.setThreshold(4);
        autocompleteCategoryText.setAdapter(new CategoriesAutocompleteAdapter(myFragmentView.getContext()));
        autocompleteCategoryText.setLoadingIndicator((ProgressBar) myFragmentView.findViewById(R.id.progress_bar));
        autocompleteCategoryText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Category category = (Category) adapterView.getItemAtPosition(position);
                autocompleteCategoryText.setText(category.Name);
            }
        });


        // Обработка нажатия клавиши Done (Готово). После которой происходит отсылка результатов.
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String val = editText.getText().toString();

                    String categoryName = autocompleteCategoryText.getText().toString();

                    if(!val.isEmpty()){

                        saveValue(Double.parseDouble(val), categoryName);

                        loadData();

                        listView.setAdapter(adapter);

                        editText.setText("");

                        if(baseCategoryName != null){
                            Toast.makeText(getActivity().getApplicationContext(), categoryName + ": " + val,
                                    Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }else {
                            editText.requestFocus();
                        }
                    }
                }

                return true;
            }
        });

        return myFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        coastLoadTask = new CoastsLoad();
        coastLoadTask.execute();
    }

    @Override
    public void onResume() {
        super.onResume();

        editText.requestFocus();
    }

    private void loadData() {
        try {
            Coasts coasts = new Coasts();

            Cursor cursor = coasts.Get();

            ArrayList coastList = new ArrayList();


            while (cursor.moveToNext()){

                HashMap coastMap = new HashMap();

                Coast coast = new Coast(cursor);

                coastMap.put("Value", new SimpleDateFormat("dd.MM", Locale.getDefault()).format(coast.DateCoast) + " " + coast.Sum);
                coastMap.put("Category", new SimpleDateFormat("HH:mm", Locale.getDefault()).format(coast.DateCoast) + " " + coast.Category);

                coastList.add(coastMap);
            }

            cursor.close();

            adapter = new SimpleAdapter(getActivity().getApplicationContext(), coastList,
                    android.R.layout.simple_list_item_2,
                    new String[]{"Value", "Category"},
                    new int[]{android.R.id.text1, android.R.id.text2});


        }catch (Exception e){
            Log.w("LOG_TAG", e.getMessage());
        }
    }

    private void saveValue(double value, String categoryName)
    {
        Coast coast = new Coast();
        coast.Sum = value;
        coast.Category = categoryName;
        coast.DateCoast = new Date();

        Coasts coasts = new Coasts();

        coasts.Insert(coast);
    }

    class CoastsLoad extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                loadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            listView.setAdapter(adapter);
        }
    }
}