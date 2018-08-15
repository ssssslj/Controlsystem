package com.example.controlsystem.b1.banner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.controlsystem.R;
import com.example.controlsystem.util.DLog;
import com.example.controlsystem.util.OsUtil;

import java.util.List;

/**
 * Created by RyanLee on 2017/12/7.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    private Context mContext;
    private List<Integer> mDatas;
    private ViewGroup mParent;



    public RecyclerAdapter(Context mContext, List<Integer> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        DLog.d(b1_MainActivity.TAG, "RecyclerAdapter onAttachedToRecyclerView");
        this.mParent = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DLog.d(b1_MainActivity.TAG, "RecyclerAdapter onCreateViewHolder" + " width = " + parent.getWidth());
        Log.i("00000000", "RecyclerAdapter onCreateViewHolder" + " width = " + parent.getWidth());

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.a1_item_gallery, parent, false);


        Log.i("3333333333333333333",itemView.getLayoutParams().width+"");
        Log.i("5555555", "RecyclerAdapter onCreateViewHolder" + " width = " + parent.getWidth());
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        DLog.d(b1_MainActivity.TAG, "RecyclerAdapter onBindViewHolder" + "--> position = " + position);
        // 需要增加此代码修改每一页的宽高
        //GalleryAdapterHelper.newInstance().setItemLayoutParams(mParent, holder.itemView, position, getItemCount());
        holder.mView.setImageResource(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public final ImageView mView;

        public MyHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.iv_photo);
        }
    }

    /**
     * 获取position位置的resId
     * @param position
     * @return
     */
    public int getResId(int position) {
        return mDatas == null ? 0 : mDatas.get(position);
    }
}
