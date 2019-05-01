package com.example.administrator.accounapp;



import android.content.Context;
import android.icu.util.ValueIterator;
import android.media.Image;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class CategoryRecycleAdapter extends RecyclerView.Adapter<CategoryViewHolder>{

    private static final String TAG = "CategoryRecycleAdapter";
    private LayoutInflater mInflater;
    private Context mContext;

    private LinkedList<CategoryResBean> cellList = GlobalUtil.getInstance().costRes;

    private String selected = "";

    private OnCategoryClickListen onCategoryClickListen;

    public String getSelected() {
        return selected;
    }

    public void setOnCategoryClickListen(OnCategoryClickListen onCategoryClickListen) {
        this.onCategoryClickListen = onCategoryClickListen;
    }

    public CategoryRecycleAdapter(Context Content) {
        this.mContext = Content;
        mInflater = LayoutInflater.from(Content);
        selected = cellList.get(0).title;
        Log.i(TAG,"" + cellList.size());

    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = mInflater.inflate(R.layout.cell_category,viewGroup,false);
        CategoryViewHolder myViewHolder = new CategoryViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        final CategoryResBean res = cellList.get(i);

        categoryViewHolder.imageView.setImageResource(res.resBlack);
        categoryViewHolder.textView.setText(res.title);
        categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = res.title;
                notifyDataSetChanged();

                if(onCategoryClickListen != null){
                    onCategoryClickListen.onClick(res.title);
                }
            }
        });

        if(categoryViewHolder.textView.getText().toString().equals(selected)){
            categoryViewHolder.background.setBackgroundResource(R.drawable.bg_edit_text);
        }else {
            categoryViewHolder.background.setBackgroundResource(R.color.colorPrimary);
        }

    }

    @Override
    public int getItemCount() {
        return cellList.size();
    }



    public interface OnCategoryClickListen{
        void onClick(String categoty);
    }

    public void changeType(RecordBean.RecordType type){
        if(type == RecordBean.RecordType.RECORD_TYPE_EXPENSE){
            cellList = GlobalUtil.getInstance().costRes;
        }else {
            cellList = GlobalUtil.getInstance().earnRes;
        }
        selected = cellList.get(0).title;
        notifyDataSetChanged();
    }

}

class CategoryViewHolder extends RecyclerView.ViewHolder{

    RelativeLayout background;
    ImageView imageView;
    TextView textView;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        background = (RelativeLayout) itemView.findViewById(R.id.cell_background);
        imageView = (ImageView)itemView.findViewById(R.id.imageView_category);
        textView = (TextView)itemView.findViewById(R.id.textView_category);

    }
}