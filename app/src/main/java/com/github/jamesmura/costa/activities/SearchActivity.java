package com.github.jamesmura.costa.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.github.jamesmura.costa.R;
import com.github.jamesmura.costa.adapters.SearchResultAdapter;
import com.github.jamesmura.costa.api.ItunesEndPoint;
import com.github.jamesmura.costa.models.ApiResponse;
import com.github.jamesmura.costa.models.Result;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class SearchActivity extends Activity implements Callback<ApiResponse> {

    public static final String TAG = "COSTA";
    private ListView listViewResults;
    private SearchResultAdapter adapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupWindow();
        setContentView(R.layout.activity_search);

        setProgressVisibility(false);

        final EditText editTextSearch = (EditText) findViewById(R.id.editTextSearch);

        listViewResults = (ListView) findViewById(R.id.listViewResults);

        setupAdapter();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://itunes.apple.com/")
                .build();


        final ItunesEndPoint service = restAdapter.create(ItunesEndPoint.class);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setProgressVisibility(true);
                animateEditTextSearch(editTextSearch);
                service.search(s.toString(), SearchActivity.this);
            }
        });
    }

    private void setupWindow() {
        CalligraphyConfig.initDefault("Quicksand-Regular.otf", R.attr.fontPath);

        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    }

    private void setupAdapter() {
        adapter = new SearchResultAdapter(getApplicationContext(), R.layout.search_item, new ArrayList<Result>());

        listViewResults.setAdapter(adapter);
    }

    private void animateEditTextSearch(EditText editTextSearch) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(

                RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        editTextSearch.setLayoutParams(layoutParams);
    }

    private void setProgressVisibility(boolean visible) {
        setProgressBarIndeterminateVisibility(visible);
        setProgressBarVisibility(visible);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.costs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void success(ApiResponse apiResponse, Response response) {
        setProgressVisibility(false);
        Log.e(TAG, response.getUrl());
        Log.e(TAG, String.valueOf(response.getStatus()));
        adapter.clear();
        adapter.addAll(apiResponse.getResults());
    }

    @Override
    public void failure(RetrofitError error) {
        setProgressVisibility(false);
        Log.e(TAG, error.getMessage());
    }
}
