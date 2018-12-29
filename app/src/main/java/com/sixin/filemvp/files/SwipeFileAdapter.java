package com.sixin.filemvp.files;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.sixin.filemvp.R;
import com.sixin.filemvp.utils.FileUtils;
import com.sixin.filemvp.utils.FormatUtils;
import com.sixin.filemvp.utils.LogUtils;

import java.io.File;
import java.util.List;

public class SwipeFileAdapter extends RecyclerSwipeAdapter<SwipeFileAdapter.SwipeViewHolder> {

    private Context mContext;
    private List<File> mData;

    public SwipeFileAdapter(Context context, List<File> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public SwipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_file, parent, false);
        return new SwipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SwipeViewHolder viewHolder, int position) {
        File file = mData.get(position);
        viewHolder.mImgFileIcon.setImageResource(FormatUtils.getFileIcon(file));
        viewHolder.mTvFileName.setText(file.getName());
        viewHolder.mTvFileSize.setText(FileUtils.getFileSize(file));
        viewHolder.mSlFile.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.mSlFile.addSwipeListener(new SimpleSwipeListener(){
            @Override
            public void onOpen(SwipeLayout layout) {
                LogUtils.d("onOpen");
            }
        });
        viewHolder.mSlFile.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {

            }
        });
        mItemManger.bindView(viewHolder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.sl_file;
    }

    class SwipeViewHolder extends RecyclerView.ViewHolder{

        SwipeLayout mSlFile;
        ImageView mImgFileIcon;
        TextView mTvFileName;
        TextView mTvFileSize;

        private SwipeViewHolder(@NonNull View itemView) {
            super(itemView);
            mSlFile = itemView.findViewById(R.id.sl_file);
            mImgFileIcon = itemView.findViewById(R.id.img_file_icon);
            mTvFileName = itemView.findViewById(R.id.tv_file_name);
            mTvFileSize = itemView.findViewById(R.id.tv_file_size);
        }
    }

}
