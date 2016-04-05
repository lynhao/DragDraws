package com.bignerdranch.android.draganddraw;

import android.graphics.PointF;

/**
 * Created by linhao on 16/4/5.
 */
public class Box {
    private PointF mOrigin; //初始位置
    private  PointF mCurrent;//当前位置
    private int mAngle;

    public int getAngle() {
        return mAngle;
    }

    public void setAngle(int angle) {
        this.mAngle = angle;
    }

    public Box(PointF origin){
        mOrigin = mCurrent = origin;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF mCurrent) {
        this.mCurrent = mCurrent;
    }
    public PointF getCenter(){
        float middleX = (mCurrent.x + mOrigin.x) / 2.0f;
        float middleY = (mCurrent.y + mOrigin.y) / 2.0f;
        return new PointF(middleX, middleY);
    }
}
