package com.luocj.ai.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5/005.
 */

public class Model implements MultiItemEntity {
    public static int TYPE_LEFT = 0;
    public int TYPE_RIGHT = 1;

    //    private List<RightModel> rightModels;
    private List<LeftModel> leftModels;
    private int type;

    public Model(List<LeftModel> leftModels, int type) {
        this.leftModels = leftModels;
        this.type = type;
    }

    public List<LeftModel> getLeftModels() {
        return leftModels;
    }

    public void setLeftModels(List<LeftModel> leftModels) {
        this.leftModels = leftModels;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
