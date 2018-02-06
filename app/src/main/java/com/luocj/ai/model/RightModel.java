package com.luocj.ai.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/5/005.
 */

public class RightModel implements Serializable {
    private String info;
    private int type = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
