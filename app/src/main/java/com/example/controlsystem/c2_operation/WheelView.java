package com.example.controlsystem.c2_operation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 12969 on 2018/7/27.
 */

public class WheelView extends View implements View.OnTouchListener{

    private double centerX = 0.0D;
    private double centerY = 0.0D;

    private int radius;

    private Paint mainCircle;
    private Paint secondatyCircle;


    public WheelView(Context context) {
        super(context);
        initWheelView();
    }

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWheelView();
    }

    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWheelView();
    }

    private void initWheelView() {

        this.mainCircle = new Paint(1);
        this.mainCircle.setColor(0x6400ffff);
        this.mainCircle.setStrokeWidth(3.0f);
        this.mainCircle.setStyle(Paint.Style.FILL);

        this.secondatyCircle = new Paint();
        this.secondatyCircle.setColor(0xaa00ffff);
        this.secondatyCircle.setStrokeWidth(3.0f);
        this.secondatyCircle.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = Math.min(measure(widthMeasureSpec),measure(heightMeasureSpec));
        setMeasuredDimension(i,i);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measure(int paramInt) {
        int i = View.MeasureSpec.getMode(paramInt);
        int j = View.MeasureSpec.getSize(paramInt);
        if(i == 0){
            return 200;
        }
        return j;
    }
    /** 手指点击的位置X坐标 */
    private int position_X = 0;
    /** 手指点击的位置Y坐标 */
    private int position_Y = 0;
    /** 当前手指位置所在的角度 */
    private int lastAngle = 0;
    /** 当前手指位置与中心的距离 */
    private int lastDistance = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        this.centerX = getWidth()/2;
//        Log.i("iiiiii",getWidth()+"");
//        Log.i("iiiiii",getHeight()+"");
        this.centerY = getHeight()/2;
        this.radius = Math.min((int)getWidth()/3,getHeight()/3)-3;

        canvas.drawCircle((getWidth() /2),
                getHeight()/2,
                this.radius,
                this.mainCircle);
        if (position_X != 0) {
            canvas.drawCircle(this.position_X,
                    this.position_Y,
                    this.radius / 3,
                    this.secondatyCircle);
        }

    }


    private OnWheelViewMoveListener onWheelViewMoveListener;

    /**
     * 为接口设置监听器
     * @param listener	回调
     * @param paramLong	监听时间间隔
     */
    public void setOnWheelViewMoveListener(
            OnWheelViewMoveListener listener, long paramLong) {
        this.onWheelViewMoveListener = listener;
        WheelView.loopInterval = paramLong;
    }

    /**
     * 为接口设置监听器,默认时间间隔
     * @param listener	回调
     */
    public void setOnWheelViewMoveListener(
            OnWheelViewMoveListener listener) {
        this.onWheelViewMoveListener = listener;
    }

    /** 实现事件监听 */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
		/* 处理(消费掉)这个控件的事件监听 */
        return true;
    }

    /** 处理监听事件 */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取手触碰的坐标位置
        this.position_X = (int) event.getX();
        this.position_Y = (int) event.getY();
        /** 手机点击的位置与控件中心的距离 */
        double d = Math.sqrt(//X坐标的平方+Y坐标的平方再开平方
                Math.pow(this.position_X - this.centerX, 2) +
                        Math.pow(this.position_Y - this.centerY, 2));

        if (d > this.radius) {
            this.position_X = (int) ((this.position_X - this.centerX)
                    * this.radius / d + this.centerX);
            this.position_Y = (int) ((this.position_Y - this.centerY)
                    * this.radius / d + this.centerY);
        }
        this.invalidate();//再重新绘制
        if ((this.onWheelViewMoveListener != null) &&
                (event.getAction() == MotionEvent.ACTION_UP)) {
            this.position_X = (int) this.centerX;
            this.position_Y = (int) this.centerY;
            this.thread.interrupt();//手指抬起时中断，并做一次位置和方向的监听

            if (this.onWheelViewMoveListener != null)
                this.onWheelViewMoveListener.onValueChanged(getAngle(), getDistance());
        }
        if ((this.onWheelViewMoveListener != null) &&
                (event.getAction() == MotionEvent.ACTION_DOWN)) {

            if ((this.thread != null) && (this.thread.isAlive()))
                this.thread.interrupt();
            this.thread = new Thread(runnable);
            this.thread.start();//手指按下开始
            if (this.onWheelViewMoveListener != null)
                //自定义接口处理触摸事件
                this.onWheelViewMoveListener.onValueChanged(getAngle(), getDistance());
            return true;
        }
        return super.onTouchEvent(event);
    }

    /** 根据所处的位置计算得到角度 */
    private int getAngle() {
        if (this.position_X > this.centerX) {
            if (this.position_Y < this.centerY) {//右上角，第一象限
                int m = (int) (90.0D + 57.295779500000002D * Math
                        .atan((this.position_Y - this.centerY)
                                / (this.position_X - this.centerX)));
                this.lastAngle = m;
                return m;
            }
            if (this.position_Y > this.centerY) {//右下角，第二象限
                int k = 90 + (int) (57.295779500000002D * Math
                        .atan((this.position_Y - this.centerY)
                                / (this.position_X - this.centerX)));
                this.lastAngle = k;
                return k;
            }
            this.lastAngle = 90;
            return 90;
        }
        if (this.position_X < this.centerX) {
            if (this.position_Y < this.centerY) {//左上角，第三象限
                int j = (int) (57.295779500000002D * Math
                        .atan((this.position_Y - this.centerY)
                                / (this.position_X - this.centerX)) - 90.0D);
                this.lastAngle = j;
                return j;
            }
            if (this.position_Y > this.centerY) {//左下角，第四象限
                int i = -90	+ (int) (57.295779500000002D * Math
                        .atan((this.position_Y - this.centerY)
                                / (this.position_X - this.centerX)));
                this.lastAngle = i;
                return i;
            }
            this.lastAngle = -90;
            return -90;
        }
        if (this.position_Y <= this.centerY) {
            this.lastAngle = 0;
            return 0;
        }
        if (this.lastAngle < 0) {
            this.lastAngle = -180;
            return -180;
        }
        this.lastAngle = 180;
        return 180;
    }

    /** 得到所处位置与中心的距离(百分比) */
    private int getDistance() {
        this.lastDistance=(int) (100.0D * Math.sqrt(
                Math.pow(this.position_X - this.centerX, 2) +
                        Math.pow(this.position_Y - this.centerY, 2)
        ) / this.radius);
        return lastDistance;
    }

    /** 监听时间间隔，默认为100毫秒 */
    private static long loopInterval = 100L;
    /** 手指按下之后的循环监听线程 */
    private Thread thread;
    /** 事件监听线程的实现 */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (Thread.interrupted())
                    return;
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (WheelView.this.onWheelViewMoveListener != null)
                            WheelView.this.onWheelViewMoveListener.onValueChanged(
                                    WheelView.this.getAngle(),
                                    WheelView.this.getDistance());
                    }
                });

                try {
                    Thread.sleep(WheelView.loopInterval);
                } catch (InterruptedException localInterruptedException) {
                    return;
                }
            }
        }
    };

    /**
     * @version	1.0.0
     * @date	2017年6月9日 上午10:57:49
     * @author 	_Andy
     * @类说明	自定义事件监听的接口
     */
    public abstract interface OnWheelViewMoveListener {
        /**
         * 接口回调函数
         * @param angle		角度
         * @param distance	距离
         */
        public abstract void onValueChanged(int angle, int distance);
    }

}

