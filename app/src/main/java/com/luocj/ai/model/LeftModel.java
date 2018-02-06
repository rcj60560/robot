package com.luocj.ai.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/5/005.
 */

public class LeftModel implements Serializable, MultiItemEntity {

    public static final int TYPE_LEFT = 0;
    public static final int TYPE_RIGHT = 1;

    private int code;
    private String text;
    private int type = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "LeftModel{" +
                "code=" + code +
                ", text='" + text + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public int getItemType() {
        return type;
    }
}
