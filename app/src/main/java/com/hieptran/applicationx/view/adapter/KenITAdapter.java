package com.hieptran.applicationx.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hieptran.applicationx.R;
import com.hieptran.applicationx.model.KenITPost;
import com.hieptran.applicationx.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hiepth on 27/04/2016.
 */
public class KenITAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<KenITPost> mKenITPosts;

    public class VIEW_TYPES {
        public static final int ITEM = 0; //Danh cho khi co item
        public static final int PROGRESS = 1; //Khi loading item
        public static final int PADDING = 2; //padding giua cac item
    }

    public KenITAdapter(Context mContext, ArrayList<KenITPost> mKenITPosts) {
        this.mContext = mContext;
        this.mKenITPosts = mKenITPosts;
        Log.d("HiepTHb","KenITAdapter");
    }

/*    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (viewType) {
            case VIEW_TYPES.PROGRESS:
                break;
            View v = inflater.inflate(R.layout.progress, parent);
            return new RecyclerViewHolder(v);
            case VIEW_TYPES.PADDING:
                break;
            View v1 = inflater.inflate(R.layout.padding, parent);
            return new RecyclerViewHolder(v1);
            break;
            default:
                View v2 = inflater.inflate(R.layout.kenit_post, parent);
                return new KenITPostViewHolder(v2);
            break;
        }

    }*/
@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    Log.d("HiepTHb","onCreateViewHolder");

    switch (viewType) {
        case VIEW_TYPES.PROGRESS:
            return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress, viewGroup, false));
        case VIEW_TYPES.PADDING:
            return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.padding, viewGroup, false));
        default:
            return new KenITPostViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kenit_post, viewGroup, false));
    }
}


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("HiepTH", "onBindViewHolder");
        //Xu ly cac tham so cua view nhu trong getView
        //mKenITPosts.clear();
        if(holder instanceof KenITPostViewHolder) {
            Log.d("HiepTH", "xyly view");

            final KenITPost item = mKenITPosts.get(position);
            KenITPostViewHolder kenITPostViewHolder = (KenITPostViewHolder) holder;
            kenITPostViewHolder.title.setText(/*item.getTitle()*/"Đây là tiêu đề");
            kenITPostViewHolder.description.setText(item.getDescription()+"\n"+"Đây là ví dụ về nội dung\nĐây là ví dụ về nội dung\nĐây là ví dụ về nội dung\nĐây là ví dụ về nội dung");
            kenITPostViewHolder.timestamp.setText(Utils.getTimeString(item.getTimeStamp()));
            kenITPostViewHolder.author.setText(item.getTitle());
            //kenITPostViewHolder.post_image.setImageResource(item.getPostImageId());
            if (URLUtil.isValidUrl(item.getPostImageUrl())) {
                //Picasso.with(mContext).load(R.drawable.logo).into(kenITPostViewHolder.post_image);
            /* try {
                 new mAsyn(kenITPostViewHolder.post_image).execute(new URL(item.getPostImageUrl()));
             } catch (Exception e){}*/

                Picasso.with(mContext).load(item.getPostImageUrl()).resize(Utils.getScreenWidth(mContext), (int) 300).centerCrop().placeholder(R.drawable.placeholder).into(kenITPostViewHolder.post_image);
            } else {
                Picasso.with(mContext).load(R.drawable.logo).into(kenITPostViewHolder.post_image);            }

        }

    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mKenITPosts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mKenITPosts.get(position)!=null?VIEW_TYPES.ITEM : VIEW_TYPES.PROGRESS;
    }

    /**
     * ViewHolder for item view of list
     */

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }

    }

    public class KenITPostViewHolder extends RecyclerView.ViewHolder {
        protected ImageView  post_image;
        protected TextView title, description, timestamp, author;
        protected View parent;
        public KenITPostViewHolder(View itemView) {
            super(itemView);
            //Khai bao View nhu getView trong ListView
            this.parent = itemView;
            this.post_image = (ImageView) itemView.findViewById(R.id.source_icon);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.description = (TextView) itemView.findViewById(R.id.description);
            this.timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            this.author = (TextView) itemView.findViewById(R.id.author);
        }


        public View gePostImage() {
            return post_image;
        }

        public View getTitle() {
            return title;
        }

        public View getTimestamp() {
            return timestamp;
        }
        }

}
