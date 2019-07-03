package com.bytedance.clockapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

public class Clock extends View {

    private final static String TAG = Clock.class.getSimpleName();

    private static final int FULL_ANGLE = 360;
    private static final int START_CLOCK = 1000;

    private static final int CUSTOM_ALPHA = 140;
    private static final int FULL_ALPHA = 255;

    private static final int DEFAULT_PRIMARY_COLOR = Color.WHITE;
    private static final int DEFAULT_SECONDARY_COLOR = Color.LTGRAY;

    private static final float DEFAULT_DEGREE_STROKE_WIDTH = 0.010f;

    public final static int AM = 0;

    private static final int RIGHT_ANGLE = 90;

    private int mWidth, mCenterX, mCenterY, mRadius;

    /**
     * properties
     */
    private int centerInnerColor;
    private int centerOuterColor;

    private int secondsNeedleColor;
    private int hoursNeedleColor;
    private int minutesNeedleColor;

    private int degreesColor;

    private int hoursValuesColor;

    private int numbersColor;

    private boolean mShowAnalog = true;

    public Clock(Context context) {
        super(context);
        init(context, null);
    }

    public Clock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Clock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        if (widthWithoutPadding > heightWithoutPadding) {
            size = heightWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    private void init(Context context, AttributeSet attrs) {

        this.centerInnerColor = Color.LTGRAY;
        this.centerOuterColor = DEFAULT_PRIMARY_COLOR;

        this.secondsNeedleColor = DEFAULT_SECONDARY_COLOR;
        this.hoursNeedleColor = DEFAULT_PRIMARY_COLOR;
        this.minutesNeedleColor = DEFAULT_PRIMARY_COLOR;

        this.degreesColor = DEFAULT_PRIMARY_COLOR;

        this.hoursValuesColor = DEFAULT_PRIMARY_COLOR;

        numbersColor = Color.WHITE;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getHeight() > getWidth() ? getWidth() : getHeight();

        int halfWidth = mWidth / 2;
        mCenterX = halfWidth;
        mCenterY = halfWidth;
        mRadius = halfWidth;

        if (mShowAnalog) {
            drawDegrees(canvas);
            drawHoursValues(canvas);
            drawNeedles(canvas);
            drawCenter(canvas);
        } else {
            drawNumbers(canvas);
        }

        handler.sendEmptyMessage(START_CLOCK);

//        postInvalidateDelayed(1000);
    }

    private void drawDegrees(Canvas canvas) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH);
        paint.setColor(degreesColor);

        int rPadded = mCenterX - (int) (mWidth * 0.01f);
        int rEnd = mCenterX - (int) (mWidth * 0.05f);

        for (int i = 0; i < FULL_ANGLE; i += 6 /* Step */) {

            if ((i % RIGHT_ANGLE) != 0 && (i % 15) != 0)
                paint.setAlpha(CUSTOM_ALPHA);
            else {
                paint.setAlpha(FULL_ALPHA);
            }

            int startX = (int) (mCenterX + rPadded * Math.cos(Math.toRadians(i)));
            int startY = (int) (mCenterX - rPadded * Math.sin(Math.toRadians(i)));

            int stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(i)));
            int stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(i)));

            canvas.drawLine(startX, startY, stopX, stopY, paint);

        }
    }

    /**
     * @param canvas
     */
    private void drawNumbers(Canvas canvas) {

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(mWidth * 0.2f);
        textPaint.setColor(numbersColor);
        textPaint.setColor(numbersColor);
        textPaint.setAntiAlias(true);

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int amPm = calendar.get(Calendar.AM_PM);

        String time = String.format("%s:%s:%s%s",
                String.format(Locale.getDefault(), "%02d", hour),
                String.format(Locale.getDefault(), "%02d", minute),
                String.format(Locale.getDefault(), "%02d", second),
                amPm == AM ? "AM" : "PM");

        SpannableStringBuilder spannableString = new SpannableStringBuilder(time);
        spannableString.setSpan(new RelativeSizeSpan(0.3f), spannableString.toString().length() - 2, spannableString.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // se superscript percent

        StaticLayout layout = new StaticLayout(spannableString, textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1, 1, true);
        canvas.translate(mCenterX - layout.getWidth() / 2f, mCenterY - layout.getHeight() / 2f);
        layout.draw(canvas);
    }

    /**
     * Draw Hour Text Values, such as 1 2 3 ...
     *
     * @param canvas
     */
    private void drawHoursValues(Canvas canvas) {
        int i;
        Paint textpaint = new Paint();
        textpaint.setColor(hoursValuesColor);
        textpaint.setTextSize(80);
        textpaint.setStrokeWidth(20f);
        textpaint.setTextAlign(Paint.Align.CENTER);
        textpaint.setStyle(Paint.Style.FILL);
        int point1 = mCenterX - (int) (mWidth * 0.10f);
        Paint.FontMetrics fontMetrics = textpaint.getFontMetrics();
        float Top = fontMetrics.top;
        float Bottom = fontMetrics.bottom;

        for (i=30;i<=FULL_ANGLE;i+=30){
            canvas.drawText(""+i/30,((int)(mCenterX+point1*Math.sin(Math.toRadians(i)))),(((int)(mCenterY-point1*Math.cos(Math.toRadians(i)))-Top/2-Bottom/2)),textpaint);
        }
        // Default Color:
        // - hoursValuesColor


    }

    /**
     * Draw hours, minutes needles
     * Draw progress that indicates hours needle disposition.
     *
     * @param canvas
     */
    private void drawNeedles(final Canvas canvas) {
        Paint paint1 = new Paint();
        paint1.setColor(hoursNeedleColor);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setStrokeWidth(15f);

        Paint paint2 = new Paint();
        paint2.setColor(minutesNeedleColor);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeWidth(12f);

        Paint paint3 = new Paint();
        paint3.setColor(secondsNeedleColor);
        paint3.setStyle(Paint.Style.FILL);
        paint3.setStrokeWidth(7f);

        int seconds1 = mCenterX - (int) (mWidth * 0.15f);
        int hours1 = mCenterX - (int) (mWidth * 0.30f);
        int minutes1 = mCenterX - (int) (mWidth * 0.20f);
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int seconds = calendar.get(Calendar.SECOND);
        int minutes = calendar.get(Calendar.MINUTE);

        int secondX = (int)(mCenterX+seconds1*Math.sin(Math.toRadians(seconds*6)));
        int secondY = (int)(mCenterY-seconds1*Math.cos(Math.toRadians(seconds*6)));

        int hourX = (int)(mCenterX+hours1*Math.sin(Math.toRadians(hours*30)));
        int hourY = (int)(mCenterY-hours1*Math.cos(Math.toRadians(hours*30)));

        int minutesX = (int)(mCenterX+minutes1*Math.sin(Math.toRadians(minutes*6)));
        int minutesY = (int)(mCenterY-minutes1*Math.cos(Math.toRadians(minutes*6)));

        canvas.drawLine(mCenterX,mCenterY,secondX,secondY,paint3);
        canvas.drawLine(mCenterX,mCenterY,hourX,hourY,paint1);
        canvas.drawLine(mCenterX,mCenterY,minutesX,minutesY,paint2);

        // Default Color:
        // - secondsNeedleColor
        // - hoursNeedleColor
        // - minutesNeedleColor

    }

    /**
     * Draw Center Dot
     *
     * @param canvas
     */
    private void drawCenter(Canvas canvas) {
        Paint paint1 = new Paint();
        paint1.setColor(centerInnerColor);
        paint1.setStrokeWidth(40);

        Paint paint2 = new Paint();
        paint2.setColor(centerOuterColor);
        paint2.setStrokeWidth(40);

        paint2.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mCenterX,mCenterY,5,paint2);

        paint1.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX,mCenterY,17,paint1);


        // Default Color:
        // - centerInnerColor
        // - centerOuterColor

    }

    public void setShowAnalog(boolean showAnalog) {
        mShowAnalog = showAnalog;
        invalidate();
//        Log.d("------", "setShowAnalog: "+showAnalog);

    }




    public boolean isShowAnalog() {
        return mShowAnalog;
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message message){
            super.handleMessage(message);
            switch (message.what){
                case START_CLOCK:
                    invalidate();
                    handler.sendEmptyMessageDelayed(START_CLOCK,1000);
                    break;
            }
        }
    };

}