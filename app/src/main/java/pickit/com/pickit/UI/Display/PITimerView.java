package pickit.com.pickit.UI.Display;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

import pickit.com.pickit.R;

/**
 * Created by Tal on 25/03/2017.
 */

public class PITimerView extends View {

    //prameters:
    private Paint mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mEmptyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mMiddlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mArc = new RectF();
    private Point mCenter = new Point();

    private Canvas mCanvas;
    private Bitmap mCanvasBitmap;
    private Matrix mMatrix = new Matrix();

    private float mRingRadius = 100;
    private float mRingThickness = 3;
    private float mDotRadius = 6;

    private long mTimeRemaining = 0;
    private float mTextPadding = 16;
    private Rect mTextBounds = new Rect();
    private CharSequence mText = null;

    private int mEmptyRingColor = Color.WHITE;
    private int mFillRingColor = Color.rgb(98,14,142);
    private int mMiddleColor = Color.TRANSPARENT;
    private int mTextColor = Color.rgb(98,14,142);;
    private float mTextSize = 80;

    private boolean mCounterClockwise = false;
    private boolean mAutoFitText = true;

    private OnTickListener mTickListener;

    private final int DURATION_MINUTE = 0;
    private final int DURATION_TOTAL = 1;
    private int mCircleDuration = DURATION_MINUTE;
    private Calendar mStartTime = null;
    private Calendar mEndTime = null;

    //constructors:
    public PITimerView(Context context) {
        super(context);
        init(context, null);
    }

    public PITimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PITimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    //constractor for lolipop version
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PITimerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //the method getDisplayMetrics returns general information about display. the density is the density of the screen.
        // we need the density because we need the drawing will be drawn the same no matter the screen size.
        float multi = context.getResources().getDisplayMetrics().density;

        mRingRadius *= multi;// the radius of the ring.
        mRingThickness *= multi; // the thickness of the ring
        mDotRadius *= multi;// the do radius
        mTextPadding *= multi;

        //getting all the arrtributes from R.styleable.
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PITimerView, 0, 0);
        try {
            mEmptyRingColor = ta.getColor(R.styleable.PITimerView_tickEmptyRingColor, mEmptyRingColor);
            mFillRingColor = ta.getColor(R.styleable.PITimerView_tickFillRingColor, mFillRingColor);
            mMiddleColor = ta.getColor(R.styleable.PITimerView_tickMiddleColor, mMiddleColor);
            mTextColor = ta.getColor(R.styleable.PITimerView_tickTextColor, mTextColor);

            mRingThickness = ta.getDimension(R.styleable.PITimerView_tickRingThickness, mRingThickness);
            mDotRadius = ta.getDimension(R.styleable.PITimerView_tickDotRadius, mDotRadius);
            mTextSize = ta.getDimension(R.styleable.PITimerView_tickTextSize, mTextSize);

            mText = ta.getText(R.styleable.PITimerView_tickText);
            mAutoFitText = ta.getBoolean(R.styleable.PITimerView_tickAutoFitText, mAutoFitText);

            mCounterClockwise = ta.getBoolean(R.styleable.PITimerView_tickMoveCounterClockwise, mCounterClockwise);
            mCircleDuration = ta.getInt(R.styleable.PITimerView_tickCircleDuration, mCircleDuration);
        } finally {
            ta.recycle();//beacause we going to reuse ta, we need to do recycle, It is used to make the data associated with "ta" ready for garbage collection so memory/data is not inefficiently bound to "ta" when it doesn't need to be
        }

        // setting the attrs from the layoutfile and if there is not definition in this file it's take the default.
        mEmptyPaint.setColor(mEmptyRingColor);
        mFillPaint.setColor(mFillRingColor);

        mFillPaint.setStrokeWidth(mRingThickness);
        mEmptyPaint.setStrokeWidth(mRingThickness);

        mMiddlePaint.setColor(mMiddleColor);
        if (mMiddleColor == Color.TRANSPARENT) {
            mMiddlePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));// set the middle area clear.
        }

        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        if (isInEditMode()) {//checks if the view is in edit mode, this situation could be while drawing on view.
            fitText(mText);
        }

        calculateEverything();
    }

    private void calculateEverything() {
        calculateCenter();
        calculateArc();
    }

    private void drawInitialCircle() {
        //Checks that canvas not null, to draw on canvas we need 3 components, a bitMap that hold the pixels, a Canvas to hold the draw,and
        // apaint to contorol the colors.
        if (mCanvas == null || mCanvasBitmap.getWidth() != getWidth() || mCanvasBitmap.getHeight() != getHeight()) {
            if (mCanvasBitmap != null) {
                mCanvas = null;
                mCanvasBitmap.recycle();
                mCanvasBitmap = null;
            }
            mCanvasBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);// create a bit map that eatch pixel is stored 4 bytes.
            mCanvas = new Canvas(mCanvasBitmap);
        }

        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//starts the canvas with clear color
        mCanvas.drawCircle(mCenter.x, mCenter.y, mRingRadius, mEmptyPaint);//draw a circle.
    }

    private void calculateCenter() {
        mCenter.x = ((getWidth() + getPaddingLeft()) - getPaddingRight()) / 2;// gets the x of the center.
        mCenter.y = ((getHeight() + getPaddingTop()) - getPaddingBottom()) / 2;//gets the y of the center
    }

    private void calculateArc() {
        mArc.left = mCenter.x - mRingRadius;// calculate the left arc
        mArc.top = mCenter.y - mRingRadius;// calculates the arc top
        mArc.right = mCenter.x + mRingRadius;//calculate the arc right
        mArc.bottom = mCenter.y + mRingRadius;//claculates the arc bottom.
    }

    /*
    Called when this view should assign a size and position to all of its children
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            calculateEverything();
        }
    }
    /*
    This is called during layout when the size of this view has changed
    */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // getmeasuredWidth gives the width of the view want to be in its parent
        mRingRadius = ((getMeasuredWidth() - getPaddingLeft()) / 2) - mDotRadius;
        if (!TextUtils.isEmpty(mText)) {
            fitText(mText);
        }
    }

    //called when the view rendered.
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawInitialCircle();

        calculateArc();
        //the time returns in millis thats why we need to do module 6000.
        long ms = isInEditMode() ? System.currentTimeMillis() % 60000 : mTimeRemaining % 60000;
        float angle = (float) (ms * 0.006);

        if (mCircleDuration == DURATION_TOTAL && mStartTime != null) {
            long totalTime = mEndTime.getTimeInMillis() - mStartTime.getTimeInMillis();//calculate the totaltime in mills
            float percentage = (((float) mTimeRemaining) / ((float) totalTime));//calculate the percentage of the current time from the total time.
            angle = 360f * percentage;// 360 beacause it's a circle.
        }

        if (isInEditMode()) {
            angle *= -1;
        }

        //The fill
        if (!mCounterClockwise) {
            mCanvas.drawArc(mArc, 270, angle, true, mFillPaint);
        } else {
            mCanvas.drawArc(mArc, 270, 360-angle, true, mFillPaint);
        }

        //Clear the centre
        mCanvas.drawCircle(mCenter.x, mCenter.y, mRingRadius - mRingThickness, mMiddlePaint);

        drawDot();
        if (!mCounterClockwise) {
            mMatrix.setRotate(-angle, mCanvas.getWidth() / 2, mCanvas.getHeight() / 2);
        } else {
            mMatrix.setRotate(angle, mCanvas.getWidth() / 2, mCanvas.getHeight() / 2);
        }

        canvas.drawBitmap(mCanvasBitmap, mMatrix, null);
        if (isInEditMode()) {
            mTimeRemaining = System.currentTimeMillis();
        }

        if (!TextUtils.isEmpty(mText)) {
            canvas.drawText(mText.toString(), getWidth() / 2 - mTextBounds.width() / 2, getHeight() / 2 + mTextBounds.height() / 2, mTextPaint);
        }
    }

    private void drawDot() {
        float centerX = (mCanvas.getWidth() / 2);
        float centerY = mDotRadius + mRingThickness / 2;
        mCanvas.drawCircle(centerX, centerY, mDotRadius, mFillPaint);
    }

    /**
     *
     * @param endTime Calendar the time to count down till.
     */
    public void start(Calendar endTime) {
        if (endTime == null || endTime.before(Calendar.getInstance())) {
            throw new IllegalArgumentException("endTime cannot be null and must be in the future");
        }

        //Fix for issue #1 where hot deploying would result in text being rendered incorrectly.
        if (!TextUtils.isEmpty(mText) && (mTextBounds.width() == 0 || mTextBounds.height() == 0) && mTextSize > 0) {
            //When code is hot deployed,
            fitText(mText);
        }

        mEndTime = endTime;

        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new CountDownTimer(endTime.getTimeInMillis() - System.currentTimeMillis(), 16) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeRemaining = millisUntilFinished;
                updateText(mTimeRemaining);
                invalidate();
            }

            @Override
            public void onFinish() {
                updateText(0);
                invalidate();
            }
        }.start();
    }

    /**
     * Only for use with with tickCircleDuration = total_time
     *
     * @param startTime Calendar the start time which represents starting point for the circle.
     * @param endTime Calendar the end time which represents a full circle
     */
    public void start(Calendar startTime, Calendar endTime) {
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            throw new IllegalArgumentException("startTime and endTime cannot be null and startTime must be before endTIme.");
        }
        mStartTime = startTime;
        start(endTime);
    }

    private void updateText(long timeRemaining) {
        if (mTickListener != null) {
            String text = mTickListener.getText(timeRemaining);
            if (!TextUtils.isEmpty(text)) {
                mText = text;
            }
        }
    }

    /**
     * Remove stop the timer.
     */
    public void stop() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mStartTime = null;
        mEndTime = null;
    }

    private void fitText(CharSequence text) {
        //case there is not text.
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (mAutoFitText) {//if auto fit text is on
            float textWidth = mFillPaint.measureText(text.toString());//returns the width of the text.
            float multi = ((mRingRadius * 2) - mTextPadding * 2) / textWidth;
            mTextPaint.setTextSize(mFillPaint.getTextSize() * multi);
        }
        mTextPaint.getTextBounds(mText.toString(), 0, mText.length(), mTextBounds);
    }


    @Override
    protected void onDetachedFromWindow() {
        if (mTimer != null) {

        }
        super.onDetachedFromWindow();
    }

    /**
     * Listen for tick events.
     *
     * @param l OnTickListener
     */
    public void setOnTickListener(OnTickListener l) {
        mTickListener = l;
    }

    private CountDownTimer mTimer = null;

    public interface OnTickListener {
        String getText(long timeRemainingInMillis);
    }

}
