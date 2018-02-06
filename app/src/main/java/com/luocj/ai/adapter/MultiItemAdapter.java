package com.luocj.ai.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luocj.ai.R;
import com.luocj.ai.model.LeftModel;
import com.luocj.ai.model.Model;

import java.util.ArrayList;
import java.util.List;


public class MultiItemAdapter extends BaseMultiItemQuickAdapter<LeftModel, BaseViewHolder> {

    private Context mContext;
    private List<LeftModel> models;

    public MultiItemAdapter(Context context, List<LeftModel> data) {
        super(data);
        this.mContext = context;
        this.models = data;

        addItemType(LeftModel.TYPE_LEFT, R.layout.item_left);
        addItemType(LeftModel.TYPE_RIGHT, R.layout.item_right);
    }

    @Override
    protected void convert(BaseViewHolder holder, LeftModel item) {
        int itemViewType = holder.getItemViewType();
        switch (itemViewType) {
            case LeftModel.TYPE_LEFT:
                holder.setText(R.id.tv_left, item.getText());
                break;
            case LeftModel.TYPE_RIGHT:
                holder.setText(R.id.tv_right, item.getText());
                //
                break;
        }
    }

    public void upDate(ArrayList<LeftModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }
}
