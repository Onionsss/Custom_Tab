package onionsss.it.mytab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 作者：张琦 on 2016/8/21 19:51
 */
public class MyTab extends LinearLayout{
    private String san = "1";
    private String rectangle = "2";
    private String oval = "3";
    private Paint mPaint;
    private Path mPath;
    private PointF ovalCenter = new PointF();
    private int SanWidth;
    private int SanHeight;

    private int initX;
    private int moveX;

    private int mHide = dip2px(getContext(),3);
    private int defualtTabSize = 4;
    private int VisibleTabSize;
    private static final float RATIO = 1/6f;
    private int prePos = 0;
    private int textPadding = dip2px(getContext(),15);
    /**
     * 属性
     * @param context
     */
    private int textColor_n = Color.GRAY;
    private int textColor_l = Color.GREEN;
    private int textsize_d = sp2px(getContext(),14);
    private int bottomColor = Color.GREEN;
    private List<String> mTitleList;

    private ViewPager mVp;
    private int mTabWidth;
    private String shape = rectangle;
    private int ovalRadius = dip2px(getContext(),4);

    public MyTab(Context context) {
        this(context,null);
    }

    public MyTab(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyTab);
        VisibleTabSize = a.getInt(R.styleable.MyTab_visibleSize,defualtTabSize);
        if(VisibleTabSize == 0){
            VisibleTabSize = defualtTabSize;
        }
        shape = a.getString(R.styleable.MyTab_shape);
        bottomColor = a.getColor(R.styleable.MyTab_bottomColor, Color.GREEN);
        textColor_n = a.getColor(R.styleable.MyTab_textcolor_n, Color.GREEN);
        textColor_l = a.getColor(R.styleable.MyTab_textcolor_l, Color.GREEN);
        textsize_d = (int) a.getDimension(R.styleable.MyTab_textsize_d,sp2px(getContext(),14));

        a.recycle();
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(bottomColor);
        mPaint.setStyle(Paint.Style.FILL);
        if(shape.equals(san)){
            mPaint.setPathEffect(new CornerPathEffect(3));
        }else if(shape.equals(rectangle)){
            mPaint.setPathEffect(new CornerPathEffect(0));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = w / VisibleTabSize;
        SanWidth = (int) (w / VisibleTabSize * RATIO);
        initX = w / VisibleTabSize / 2 - (SanWidth / 2);
        
        initPath();
    }

    private void initPath() {
        SanHeight = SanWidth/2;

        mPath = new Path();
        if(shape.equals(san)){
            mPath.moveTo(0,0);
                    mPath.lineTo(SanWidth,0);
                    mPath.lineTo(SanWidth/2,-SanHeight);
                    mPath.close();
        }else if(shape.equals(rectangle)){
            mPath.moveTo(0,0);
            mPath.lineTo(mTabWidth,0);
            mPath.lineTo(mTabWidth,-5);
            mPath.lineTo(0,-5);
            mPath.lineTo(0,0);
        }else if(shape.equals(oval)){
            ovalCenter.set(SanWidth/2,getHeight()-mHide*3);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        canvas.save();
        if(shape.equals(san)){
            canvas.translate(initX + moveX,getHeight() + mHide/2);
            canvas.drawPath(mPath,mPaint);
        }else if(shape.equals(rectangle)){
            canvas.translate(moveX,getHeight());
            canvas.drawPath(mPath,mPaint);
        }else if(shape.equals(oval)){
            canvas.drawCircle(initX+ovalCenter.x+moveX,ovalCenter.y,ovalRadius,mPaint);
        }

        canvas.restore();
    }

    /**
     * 关联标题
     * @param list
     */
    public void setTitles(List<String> list){
        mTitleList = list;
        if(mTitleList != null && mTitleList.size() > 0){
            removeAllViews();
            addTitle();
        }
    }

    /**
     * 添加title
     */
    private void addTitle() {
        for (int i = 0; i < mTitleList.size(); i++) {
            TextView tv = new TextView(getContext());
            tv.setText(mTitleList.get(i));
            tv.setPadding(0,textPadding,0,textPadding);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(textsize_d);
            if(i == 0){
                tv.setTextColor(textColor_l);
            }else{
                tv.setTextColor(textColor_n);
            }

            LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.weight = 0;
            lp.width = getScreen() / VisibleTabSize;
            tv.setLayoutParams(lp);
            addView(tv);
            final int position = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVp.setCurrentItem(position);
                }
            });
        }
    }

    public void attachViewPager(ViewPager vp){
        mVp = vp;

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position,positionOffset);
                Log.d("TAG", "onPageScrolled: ");
            }

            @Override
            public void onPageSelected(int position) {
                highLightTextView(position);
                clearPreHighLight(prePos);
                prePos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 移动tab三角
     * @param position
     * @param offset
     */
    private void scroll(int position, float offset) {
        int tabWidth = getWidth() / VisibleTabSize;
        moveX = tabWidth * position + (int)(tabWidth * offset);

        if(position >= (VisibleTabSize - 2) && offset > 0 && getChildCount() > VisibleTabSize){
            if(position < getChildCount() - 2){
                scrollTo((position - (VisibleTabSize -2)) * tabWidth + (int)(tabWidth * offset),0);
            }else{
                scrollTo((position - (VisibleTabSize -2)) * tabWidth,0);
            }
        }

        invalidate();
    }

    /**
     * 得到屏幕宽度
     * @return
     */
    private int getScreen() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics out = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(out);
        return out.widthPixels;
    }

    private void clearPreHighLight(int prePos) {
        View childAt = getChildAt(prePos);
        if(childAt instanceof TextView){
            ((TextView) childAt).setTextColor(textColor_n);
        }
    }

    private void highLightTextView(int pos){
        View childAt = getChildAt(pos);
        if(childAt instanceof TextView){
            ((TextView) childAt).setTextColor(textColor_l);
        }
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
