package com.charboard.www.charboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


/**
 * TODO: document your custom view class.
 */
public class CharboardView extends View {

    //custom variable
    private Board board;
    private Charm charm;

    private String mExampleString; //TODO: use a default from R.string...
    private int mExampleColor = Color.WHITE; //TODO: use a default from R.color...
    private float mExampleDimension = 0; //TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;


    public CharboardView(Context context) {
        super(context);
        init(null, 0,context);
    }

    public CharboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0,context);
    }

    public CharboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle,context);
    }

    //initialize local attributes and variables
    private void init(AttributeSet attrs, int defStyle, Context context) {
        //set up local variable
        board = new Board();
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CharboardView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.CharboardView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.CharboardView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.CharboardView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.CharboardView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.CharboardView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
//        canvas.drawText(mExampleString,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint);


        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }

        //************************START OF GAME GRAPHICS***********************

        canvas.drawText("Score: "+ Integer.toString(board.score),
                getWidth()*2/10,
                getHeight()*8/10,
                mTextPaint);
        canvas.drawText("Highest: " + Integer.toString(board.highestScore),
                getWidth()*2/10,
                getHeight()*9/10,
                mTextPaint);

        //Draw on top of background
        if(board!=null){
            for(int j=0;j<5;j++) {
                for (int i=0;i<5;i++){
                    putBoard(board.get(i,j),i,j,canvas);
                }
            }
        }
        if(charm!=null){
            for(int i=0;i<7;i++){
                putCharm(charm.getCharm(i),i,canvas);
            }
        }

        //************************END OF GAME GRAPHICS***************************
    }

    public int getId(int num){
        if(num==1)
            return R.drawable.charm_a;
        else if(num==2)
            return R.drawable.charm_purple;
        else if(num==3)
            return R.drawable.charm_red;
        else if(num==4)
            return R.drawable.charm_blue;
        else if(num==5)
            return R.drawable.charm_green;
        else
            return R.drawable.charm_na;
    }

    public void putBoard(int num, int x, int y,Canvas canvas){
        int id;
        id= getId(num);
        canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),id), getWidth()/10, getWidth()/10, false), getWidth()*(x+2)/10, getHeight()*(y+2)/10, null);
    }

    public void putCharm(int num, int position,Canvas canvas){
        int id;
        id= getId(num);
        if(position==0)
            canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),id), getWidth()/8, getWidth()/8, false), getWidth()*8/10, getHeight()*(position+2)/10, null);
        else
            canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),id), getWidth()/10, getWidth()/10, false), getWidth()*8/10, getHeight()*(position+2)/10, null);
    }


    public boolean setBoard(Board newBoard){
        if(newBoard !=null){
            board = newBoard;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean setCharm(Charm newCharm){
        if(newCharm !=null){
            charm = newCharm;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
