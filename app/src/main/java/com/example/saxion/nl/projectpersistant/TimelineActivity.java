package com.example.saxion.nl.projectpersistant;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimelineActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private TimeLineAdapter mTimeLineAdapter;

    private List<TimeLineModel> mDataList = new ArrayList<>();

    private GradientDrawable.Orientation mOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
   //     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

  //      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Vertical TimeLine");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);


        initView();
    }

    private LinearLayoutManager getLinearLayoutManager() {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            return linearLayoutManager;

    }

    private void initView() {

        for(int i = 0;i <20;i++) {
            TimeLineModel model = new TimeLineModel();
            model.setName("Random"+i);
            model.setAge(i);
            mDataList.add(model);
        }

        mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
