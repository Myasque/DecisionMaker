package com.example.decisionmaker;

import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class LuckyView extends View {

    int width, hight, mRadius, centerX, centerY, startAngle = 0, size=6;
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint bgIndcator = new Paint(Paint.ANTI_ALIAS_FLAG);

    float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15f, getResources().getDisplayMetrics());
    int[] mArcColors = {Color.CYAN, Color.LTGRAY, Color.CYAN, Color.LTGRAY, Color.CYAN, Color.LTGRAY};
    String[] titles = {"黄焖鸡", "面条", "麻辣烫", "沙拉", "米饭", "烧烤"};
    RectF mRectf = new RectF();
    private boolean isStart;
    double speed = 0;
    private int sweepAngle;
    private int tagetCenter;


    public LuckyView(Context context) {
        this(context, null);
    }

    public LuckyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化绘制圆盘
    private void init() {

        mTextPaint.setDither(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(Color.WHITE);

        mArcPaint.setDither(true);
        //指针
        bgIndcator.setColor(Color.BLACK);
        bgIndcator.setStrokeCap(Paint.Cap.ROUND);
        bgIndcator.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        //圆边框
        bgPaint.setColor(Color.BLACK);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        hight = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            hight = width;
        }
        mRadius = (width - getPaddingLeft() - getPaddingRight()) / 2;
        centerX = width / 2;
        centerY = hight / 2;
        mRectf.set(getPaddingLeft(), getPaddingTop(),
                width - getPaddingRight(),
                hight - getPaddingBottom());
        setMeasuredDimension(width, hight);
    }

    @Override
    protected void onDraw(Canvas canvas) {//处理canvas
        super.onDraw(canvas);
        canvas.save();
        canvas.restore();
        if ( mArcColors != null && mArcColors.length > 0) {
            drowBG(canvas);
            luckydraw(canvas);
            drawIndecator(canvas);
        }
    }

    //转动
    public void start(int index) {
        System.out.println("start!!");
        if (index <= 0 || index > size) {
            return;
        }
        int tagetFrom = 360 + 270 - index * sweepAngle;
        tagetCenter = tagetFrom + sweepAngle / 2;
        speed = (float) (Math.sqrt(1 * 1 + 8 * 1 * tagetCenter) - 1) / 2;//控制转速
        startAngle = 0;
        if (isStart) {
            isStart = false;
            handler.sendEmptyMessage(2);
        } else {
            isStart = true;
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    //绘制扇区
    private void luckydraw(Canvas canvas) {
        sweepAngle = 360 / size;//每个扇区角度
        int tempAngle = startAngle;
        for (int i = 0; i < size; i++) {
            mArcPaint.setColor(mArcColors[i % mArcColors.length]);//颜色
            canvas.drawArc(mRectf, tempAngle, sweepAngle, true, mArcPaint);
            drawText(canvas, titles[i], tempAngle);
            tempAngle += sweepAngle;
        }
    }

    //绘制指针
    private void drawIndecator(Canvas canvas) {
        canvas.drawLine(centerX, centerY, centerX, centerY / 2, bgIndcator);//线
        canvas.drawCircle(centerX, centerY, mRadius / 8, bgIndcator);//中间的圆
    }

    //按照path绘制文本
    private void drawText(Canvas canvas, String title, int tempAngle) {
        Path mPath = new Path();
        mPath.addArc(mRectf, tempAngle, sweepAngle);//调整角度
        float w = mTextPaint.measureText(title);
        int h = (int) ((2 * Math.PI * mRadius / size - w) / 2);//用圆弧的一半减文本的一半
        canvas.drawTextOnPath(title, mPath, h, mRadius / 8, mTextPaint);
    }

    private void drowBG(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, mRadius, bgPaint);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (isStart) {//已经开始转动
                        System.out.println("handler:isstart");
                        startAngle += speed;
                        handler.sendEmptyMessageDelayed(1, 100);
                    }
                    break;
                case 2:
                    System.out.println("handler:case2");
                    if (isStart) return false;
                    startAngle += speed;
                    if (speed > 0) {
                        speed--;
                        if (startAngle < tagetCenter) {
                            if (speed < 2) {
                                speed = 1;
                            }
                        }
                    }
                    if (speed <= 0) {
                        speed = 0;
                        isStart = false;
                        return isStart;
                    }
                    handler.sendEmptyMessageDelayed(2, 100);
                    break;
                default:
            }
            invalidate();

            return false;
        }
    });
}
