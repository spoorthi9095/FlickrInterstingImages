package com.spoorthi.assignmentflickr.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spoorthi.assignmentflickr.adapters.RecyclerAdapter;
import com.spoorthi.assignmentflickr.apiCalls.GetProjectsListAPI;
import com.spoorthi.assignmentflickr.R;
import com.spoorthi.assignmentflickr.data_classes.ImageData;
import com.spoorthi.assignmentflickr.data_classes.InterestingThingsPojo;
import com.spoorthi.assignmentflickr.layout_manager.AutoFitGridLayoutManager;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ProgressDialog progressDialog;

    Gson gson;

    String FlickrQuery_url = "https://api.flickr.com/services/rest/?method=flickr.interestingness.getList";
    String FlickrQuery_per_page = "&per_page=30";
    String FlickrQuery_page = "&page=";
    String FlickrQuery_nojsoncallback = "&nojsoncallback=1";
    String FlickrQuery_format = "&format=json";
    String FlickrQuery_key = "&api_key=";
    String FlickrPage = "1";

    // Flickr API key:
    String FlickrApiKey = "9f89151d82e427401680cd48dd2d5cf5";

    //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
    String photo_url = "https://farm";
    String photo_staticflickr= ".staticflickr.com/";
    String seperator = "/";
    String photo_extension = ".jpg";

    RelativeLayout errorLayout,container;
    RelativeLayout progressView;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    LinearLayout linerForSearch;
    SearchView searchProject;

    Toolbar mTopToolbar;

    ArrayList<ImageData> itemDataModelList = new ArrayList<ImageData>();
    public ArrayList<ImageData> apiResult = new ArrayList<>();

    int currentPage=0,totalItems, showedItems=0;
    int nextPage;
    int totalPages;

    Boolean isSearchResult=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.flickr_logo);

        initViews();
        checkForInternet();

    }

    //Initializing the views
    private void initViews() {

        recyclerView=(RecyclerView)findViewById(R.id.projectLIst);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        linerForSearch=(LinearLayout)findViewById(R.id.linerForSearch);
        searchProject = (SearchView)findViewById(R.id.searchProduce);
        searchProject.setQueryHint(getResources().getString(R.string.serach_for_produce));
        searchProject.setIconifiedByDefault(false);
        searchProject.setOnQueryTextListener(this);

        progressView=(RelativeLayout)findViewById(R.id.progressView);

        errorLayout=(RelativeLayout)findViewById(R.id.errorLayout);

        container=(RelativeLayout)findViewById(R.id.container);

    }

    //Checking for connectivity,depending on result show the views
    public void checkForInternet()
    {
        if (!isNetworkConnected()) {//No Internet
            errorLayout.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
        }
        else
        {
            errorLayout.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);

            callgetProjectApi();
            controlScroller();

        }

    }

        //Making a call to the API
    private void callgetProjectApi()
    {
        String qString =
                FlickrQuery_url
                        + FlickrQuery_key + FlickrApiKey
                        + FlickrQuery_per_page
                        +FlickrQuery_page+FlickrPage
                        + FlickrQuery_format
                        + FlickrQuery_nojsoncallback;

        // Log.e("qString1",""+qString);


        progressDialog = new ProgressDialog(MainActivity.this);
        if (progressDialog != null) {
            progressDialog.setMessage(getResources().getString(R.string.loading_txt));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        new GetProjectsListAPI()
        {
            @Override
            public void onPostExecute(String result)
            {
                if (result!=null)
                {
                    populateList(result);
                    // Log.e("Result",""+result);
                }

                if (progressDialog.isShowing() && progressDialog != null) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }

        }.execute(qString);

    }

    //Checking for internet availability
    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    //Parsing the response from API
    private void populateList(String result)
    {
        String responsestr = new String(result);
        gson = new Gson();

        Type collectionType = new TypeToken<InterestingThingsPojo>(){}.getType();
        InterestingThingsPojo enums = gson.fromJson(responsestr, collectionType);

        List<InterestingThingsPojo.PhotosBean.PhotoBean> photoBeanList = new ArrayList<>();

        photoBeanList = enums.getPhotos().getPhoto();

        totalItems = photoBeanList.size();

        Toast.makeText(MainActivity.this, "Total Projects "+totalItems, Toast.LENGTH_SHORT).show();
        totalPages=(int) Math.ceil((double)photoBeanList.size() / 5);//Total no of pages needed to show the contents

        //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg

        // Log.e("photoBeanList",""+photoBeanList);
        for(int i=0;i<totalItems;i++)
        {
            InterestingThingsPojo.PhotosBean.PhotoBean bean = photoBeanList.get(i);
            String photoQuery = photo_url+bean.getFarm()
                                +photo_staticflickr+bean.getServer()
                                +seperator+bean.getId()+"_"+bean.getSecret()
                                +photo_extension;

            apiResult.add(new ImageData(photoQuery,bean.getTitle()));
            // Log.e("photoQuery",""+photoQuery);
            // Log.e("bean.getTitle()",""+bean.getTitle());

        }

        populateFirstPage();

    }

    private void populateFirstPage()
    {
        if (totalItems>5)
        {
//            currentPage=1;
//            int i = showedItems;
//            int limit = i+5;
//            for(i=showedItems;i<limit;i++)
//            {
//                itemDataModelList.add(apiResult.get(i));
//            }
            currentPage=1;
            for(int i=0;i<5;i++)
            {
                itemDataModelList.add(apiResult.get(i));
            }
        }
        else {
            //If there are less than 5 items
            for(int i=0;i<apiResult.size();i++)
            {
                itemDataModelList.add(apiResult.get(i));
            }
        }
        //populating recycler view

        if(itemDataModelList.size()!=0)
        {
            recyclerAdapter = new RecyclerAdapter(itemDataModelList, this);
            recyclerView.setAdapter(recyclerAdapter);

            GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);

//            AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 200);
//            recyclerView.setLayoutManager(layoutManager);
        }
    }

    //Acrolling Listener to Recycler View
    private void controlScroller()
    {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

//             Log.e("last vsible n total"," "+lastVisible+" "+totalItemCount);
                boolean endHasBeenReached = lastVisible+1  >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached)
                {
                    //you have reached to the bottom of your recycler view

                    if (nextPage!=totalPages && !isSearchResult) {//Checking for last page and performing search

                        progressView.setVisibility(View.VISIBLE);
                        showpages(currentPage);
                    }
//                    else {
//                        int page_no = Integer.parseInt(FlickrPage)+1;
//                        FlickrPage = String.valueOf(page_no);
//                        if(page_no<6)
//                        {
//                            checkForInternet();
//                        }
//
//                    }

                }
            }
        });
    }

    //Show next page
    private void showpages(int currentPageNO) {

        showedItems=currentPage*5;//Already showed items
        nextPage=currentPageNO+1;
         Log.e("page","no"+nextPage);
        Toast.makeText(MainActivity.this, "Page No-"+nextPage, Toast.LENGTH_SHORT).show();//Showing page no to ensure the pagination
        if ((nextPage<totalPages))//Are there pages to show stil?
        {
            for (int i=showedItems;i<showedItems+5;i++)//show next 20 items
            {
                itemDataModelList.add(apiResult.get(i));
            }
            recyclerAdapter.notifyDataSetChanged();//populate the new items to Recycler view
            currentPage=nextPage;
            hideProgressbar();
        }

        //show last page items
        else if (nextPage==totalPages){
            for (int i=showedItems;i<apiResult.size();i++)//loop till the last elelement
            {
                itemDataModelList.add(apiResult.get(i));
            }
            recyclerAdapter.notifyDataSetChanged();//populate the new items to Recycler view
            hideProgressbar();
        }

    }
    //Since the data is already fetched,giving 1 sec delay to show that the next page is fetching
    public void hideProgressbar()
    {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                progressView.setVisibility(View.GONE);
            }

        }, 1000);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        if ( TextUtils.isEmpty ( newText ) )
        {
            recyclerAdapter.getFilter().filter("");
        }
        else {
            recyclerAdapter.getFilter().filter(newText);

            Log.e("New text",newText);
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.searchIcon)
        {
            if (errorLayout.getVisibility()==View.GONE) {
                searchProjects();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Handling visibility of search view
    private void searchProjects()
    {
        if (linerForSearch.getVisibility()==View.VISIBLE)
        {
            linerForSearch.setVisibility(View.GONE);
            isSearchResult=false;
        }
        else {
            isSearchResult=true;
            linerForSearch.setVisibility(View.VISIBLE);
        }

    }
}

