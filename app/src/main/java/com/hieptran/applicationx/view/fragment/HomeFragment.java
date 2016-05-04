package com.hieptran.applicationx.view.fragment;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hieptran.applicationx.R;
import com.hieptran.applicationx.control.kenit.KenitControl;
import com.hieptran.applicationx.model.HomePost;
import com.hieptran.applicationx.model.KenITPost;
import com.hieptran.applicationx.view.adapter.KenITAdapter;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;


/**
 * Created by hiepth on 27/04/2016.
 */
public class HomeFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    RecyclerView mPostContainer;
    KenITAdapter mKenITAdapter;
    ArrayList<HomePost> mKenITPosts;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    KenitControl mKenitControl;
    AppBarLayout mAppBarLayout;
    private CircularProgressBar mProgressBar;
    public HomeFragment(AppBarLayout mAppBarLayout) {
        this.mAppBarLayout = mAppBarLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPostContainer = (RecyclerView) view.findViewById(R.id.list_news_container);
        layoutManager = new LinearLayoutManager(view.getContext());
        mPostContainer.setLayoutManager(layoutManager);
        mProgressBar = (CircularProgressBar) view.findViewById(R.id.progressBar);



        //refreshNews();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        // swipeRefreshLayout.setColorSchemeColors(own_color);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNews();
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        mKenITAdapter = new KenITAdapter(getContext(), KenitControl.getmFeedArrayList());
       // mKenitControl = new KenitControl(mKenITAdapter);
        new KenitControl().execute();
        mKenITAdapter.notifyDataSetChanged();
        //Log.d("HiepTHb","Test size"+KenitControl.getmFeedArrayList().size()+"");
        mPostContainer.setAdapter(mKenITAdapter);
        mKenITAdapter.notifyDataSetChanged();



        //  mKenITAdapter.notifyDataSetChanged();

        new AsyncRefresh().execute();
    }

    public void refreshNews() {
        new KenitControl().execute();
        mKenITAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_posts_fragment, container, false);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            swipeRefreshLayout.setEnabled(true);
        } else {
            swipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAppBarLayout.removeOnOffsetChangedListener(this);

    }
    class AsyncRefresh extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            Log.d("HiepTHb","onPre Ref");
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);

            new KenitControl().execute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mKenITAdapter.notifyDataSetChanged();

            swipeRefreshLayout.setRefreshing(false);
            mProgressBar.setVisibility(View.GONE);

        }

        @Override
        protected Void doInBackground(Void... params) {

            Log.d("HiepTHb", "duin Ref");

            return null;
        }
    }



}
