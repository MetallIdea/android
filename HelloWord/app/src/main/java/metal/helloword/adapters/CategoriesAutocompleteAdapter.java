package metal.helloword.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import metal.helloword.R;
import metal.helloword.data.database.Categories;
import metal.helloword.data.database.Category;

public class CategoriesAutocompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;

    private final Context context;
    private List<Category> results;

    public CategoriesAutocompleteAdapter(Context context) {
        this.context = context;
        results = new ArrayList<Category>();
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Category getItem(int index) {
        return results.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.category_item, parent, false);
        }
        Category category = getItem(position);
        ((TextView) convertView.findViewById(R.id.tvItemText)).setText(category.Name);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<Category> categories = findBooks(constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = categories;
                    filterResults.count = categories.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    CategoriesAutocompleteAdapter.this.results = (List<Category>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};

        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
    private List<Category> findBooks(String name) {
        Categories categories = new Categories();

        return categories.GetByName(name);
    }
}