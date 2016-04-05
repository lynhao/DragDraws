package com.bignerdranch.android.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by linhao on 16/4/5.
 */
public class BoxDrawingView extends View {
    private static final String TAG = "BoxDrawingView";
    private static final String KEY = "key";

    private Box mCurrentBox;
    private ArrayList<Box> mBoxes = new ArrayList<>();

    private Paint mBoxPaint;
    private Paint mBackgroundPaint;

    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setId(655);
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }

    public BoxDrawingView(Context context) {
        super(context,null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将x，y坐标封装到PonitF
        PointF curr = new PointF(event.getX(),event.getY());
        Log.d(TAG, "x=" + curr.x + "," + "y=" + curr.y);
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mCurrentBox = new Box(curr);
                mBoxes.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentBox!=null){
                    mCurrentBox.setCurrent(curr);//更新坐标
                 //   int angle = (int) (Math.atan((event.getY(1) - event.getY(0)) / (event.getX(1) - event.getX(0))) * 180 / Math.PI);
               //     mCurrentBox.setAngle(angle);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                mCurrentBox = null;
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);

        for (Box box : mBoxes){
            float left = Math.min(box.getOrigin().x,box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y,box.getCurrent().y);
            canvas.save();
            canvas.rotate(box.getAngle(), box.getCenter().x, box.getCenter().y);
            canvas.drawRect(left, top, right, bottom, mBoxPaint);
            canvas.restore();
        }
        super.onDraw(canvas);

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY, super.onSaveInstanceState());
        bundle.putSerializable(KEY, mBoxes);
        return bundle;
    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle onSaveInstanceState = (Bundle) state;
        mBoxes = (ArrayList<Box>) onSaveInstanceState.getSerializable(KEY);
        super.onRestoreInstanceState(onSaveInstanceState.getParcelable(KEY));
        invalidate();

    }
}
