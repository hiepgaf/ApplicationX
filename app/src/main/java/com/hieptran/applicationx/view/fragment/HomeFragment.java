package com.hieptran.applicationx.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hieptran.applicationx.R;
import com.hieptran.applicationx.control.kenit.KenitControl;
import com.hieptran.applicationx.model.KenITPost;
import com.hieptran.applicationx.view.adapter.KenITAdapter;

import java.util.ArrayList;


/**
 * Created by hiepth on 27/04/2016.
 */
public class HomeFragment extends Fragment {

    RecyclerView mPostContainer;
    KenITAdapter mKenITAdapter;
    ArrayList<KenITPost> mKenITPosts;
    private SwipeRefreshLayout swipeRefreshLayout;
private LinearLayoutManager layoutManager;
    KenitControl mKenitControl;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPostContainer = (RecyclerView) view.findViewById(R.id.list_news_container);
        layoutManager = new LinearLayoutManager(view.getContext());
        mPostContainer.setLayoutManager(layoutManager);
        mKenITAdapter = new KenITAdapter(getContext(),KenitControl.getmFeedArrayList());
mKenitControl = new KenitControl(mKenITAdapter);
        mKenitControl.execute();
        mKenITAdapter.notifyDataSetChanged();


        Log.d("HiepTHb","Test size"+KenitControl.getmFeedArrayList().size()+"");
        mPostContainer.setAdapter(mKenITAdapter);
        mKenITAdapter.notifyDataSetChanged();

        //refreshNews();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
       // swipeRefreshLayout.setColorSchemeColors(own_color);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNews();
            }
        });
        mKenITAdapter.notifyDataSetChanged();

    }
    public void refreshNews() {
       new KenitControl(mKenITAdapter).execute();
      //  mKenITAdapter = new KenITAdapter(getContext(),KenitControl.getmFeedArrayList());

        Log.d("HiepTHb", "Test size" + KenitControl.getmFeedArrayList().size() + "");
    //    mPostContainer.setAdapter(mKenITAdapter);
        mKenITAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_posts_fragment, container, false);    }
}
