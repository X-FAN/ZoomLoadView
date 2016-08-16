package com.xf.zoomloadview.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.xf.zoomloadview.R;


/**
 * Created by X-FAN on 2016/8/12.
 */
public class ZoomLoadView extends View {
    private int mHeight;
    private int mWidth;
    private int mViewHeight;//可见的高度
    private int mViewWidth;//可见的宽度
    private float mScale;
    private EndListener mEndListener;

    private Drawable mZoomDrawable;
    private ValueAnimator mAnimator;


    public ZoomLoadView(Context context) {
        this(context, null);
    }

    public ZoomLoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            throw new NullPointerException("zoomDrawable  attr is needed");
        }
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZoomLoadView);
        mZoomDrawable = a.getDrawable(R.styleable.ZoomLoadView_zoomDrawable);
        if (mZoomDrawable == null) {
            throw new NullPointerException("zoom drawable is null");
        }
        int duration = a.getInt(R.styleable.ZoomLoadView_animatorDuration, 3000);
        float scale = a.getFloat(R.styleable.ZoomLoadView_zoomScale, 0.3f);
        a.recycle();
        mHeight = mZoomDrawable.getIntrinsicHeight();
        mWidth = mZoomDrawable.getIntrinsicWidth();
        initAnimator(duration, scale);
    }

    private void initAnimator(int duration, float scale) {
        mAnimator = ValueAnimator.ofFloat(1.0f, 1.0f + scale);
        mAnimator.setDuration(duration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScale = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mEndListener.onEndListener();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        int scaleHeight;//根据width按图片原始宽高比例所需要的高度


        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(mWidth, widthSize);
        } else {
            width = mWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(mHeight, heightSize);
        } else {
            height = mHeight;
        }
        mViewWidth = width;
        mViewHeight = height;
        scaleHeight = mHeight * width / mWidth;
        float scale = (float) height / (float) scaleHeight;
        if (scale >= 1) {
            mWidth = (int) (width * scale);
            mHeight = (int) (scaleHeight * scale);
        } else {
            mWidth = width;
            mHeight = scaleHeight;
        }
        //将绘制的drawable的中心位置设置在view的中心位置,即展示drawable的对称中心区域
        mZoomDrawable.setBounds((mViewWidth - mWidth) / 2, (mViewHeight - mHeight) / 2, (mViewWidth + mWidth) / 2, (mViewHeight + mHeight) / 2);
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mEndListener != null) {
            super.onDraw(canvas);
            canvas.save();
            canvas.scale(mScale, mScale, mViewWidth / 2, mViewHeight / 2);
            mZoomDrawable.draw(canvas);
            canvas.restore();
        }
    }


    public void setEndListener(EndListener endListener) {
        mEndListener = endListener;
        invalidate();
        mAnimator.start();
    }

    public interface EndListener {
        void onEndListener();
    }


}
