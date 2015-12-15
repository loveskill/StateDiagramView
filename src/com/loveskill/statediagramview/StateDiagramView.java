package com.loveskill.statediagramview;

import com.loveskill.statediagramview.utils.ViewUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 *
 * 鍦ㄥ竷灞�枃浠朵腑濡備笅浣跨敤
 *
 * <com.keyi.oldmaster.view.StateDiagramView
     android:id="@+id/statediagramview"
     android:padding="@dimen/dp10"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     app:lineHeight="@dimen/dp3"
     app:stateNum="5"
     app:statePointTextSize="@dimen/text_size12"
     app:statePointTextColor="@color/attached_word_color"
     app:stateLineColor_end="@color/border_gray"
     app:stateLineColor_first="@color/green"/>
 *
 *
 *
 *
 *
 * Created by linwenxiong on 2015/11/25.
 */
public class StateDiagramView extends View {
    private String TAG = StateDiagramView.class.getSimpleName();
    private Context context;
    private int marginInLineAndText = 40;//鏍囬鍜岀嚎涔嬮棿鐨勮窛绂�
    private int pointMargin = 100;//杩欎釜鏆傛椂浣跨敤鍥哄畾鐨勶紝浠ュ悗鍙互寮勬垚涓轰竴涓睘鎬э紝琛ㄧず绗竴涓妭鐐瑰拰鏈�悗涓�釜鑺傜偣璺濈涓よ竟绾跨殑灏藉ご鐨勮窛绂�
    private int stateNum = 2;//鐘舵�鑺傜偣鏁�
    private int state = 1;//褰撳墠鐘舵�鑺傜偣锛屽綋鐘舵�state澶т簬stateNum鐨勬椂鍊欙紝琛ㄧず鍏ㄩ儴鐘舵�宸茬粡瀹屾垚銆�
    private int lineHeight = 5;//绾挎潯鐨勯珮搴�
    private int pointLineColorStart = 0;//褰撳墠鑺傜偣鍓嶇嚎鐨勯鑹�
    private int pointLineColorEnd = 0;//褰撳墠鑺傜偣鍚庣殑绾跨殑棰滆壊
    private int pointTextColor = 0;//鑺傜偣鍚嶇О瀛椾綋棰滆壊
    private int pointTextSize = 0;//鏍囬瀛椾綋澶у皬
    private Paint mPaint;
    private Rect mBound;
    private String[] titles = {"aaa","bbb","ccc","ddd","eee"};
    public StateDiagramView(Context context){
        this(context, null);
    }
    public StateDiagramView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public StateDiagramView(Context context, AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        setWillNotDraw(false);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateDiagramView, defStyle, 0);
        int attrCount = a.getIndexCount();
        for(int i = 0 ; i < attrCount; i++){
            int attr = a.getIndex(i);
            switch(attr){
                case R.styleable.StateDiagramView_stateNum:
                    stateNum = a.getInt(attr,2);
                    break;
                case R.styleable.StateDiagramView_state:
                    state = a.getInt(attr,1);
                    break;
                case R.styleable.StateDiagramView_stateLineColor_first:
                    pointLineColorStart = a.getColor(attr, context.getResources().getColor(R.color.green));
                    break;
                case R.styleable.StateDiagramView_lineHeight:
                    lineHeight = a.getDimensionPixelSize(attr,5);
                    break;
                case R.styleable.StateDiagramView_stateLineColor_end:
                    pointLineColorEnd = a.getColor(attr,context.getResources().getColor(R.color.border_gray));
                    break;
                case R.styleable.StateDiagramView_statePointTextColor:
                    pointTextColor = a.getColor(attr,context.getResources().getColor(R.color.black_light));
                    break;
                case R.styleable.StateDiagramView_statePointTextSize:
                    pointTextSize = a.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        if(state > stateNum){
            state = stateNum + 1;
        }
        if(state < 1){
            state = 1;
        }
        mPaint = new Paint();
        mBound = new Rect();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private float getFirstLineX() {
        float deviceW = ViewUtils.getWindowWidth(context);//鑾峰彇灞忓箷瀹藉害
        int len = (int) (deviceW - pointMargin * 2 - this.getPaddingLeft() - this.getPaddingRight()) / (stateNum - 1);//鑾峰彇涓棿閭ｆ鐨勯暱搴�
        if(state > stateNum){
            return 2*pointMargin + len * (stateNum - 1) + getPaddingLeft();
        }else {
            return pointMargin + len * (state - 1) + getPaddingLeft();
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        //璁剧疆鐢荤嚎鐨勭敾绗斿睘鎬�
        mPaint.setColor(pointLineColorStart);//璁剧疆瀛椾綋棰滆壊
        mPaint.setTextSize(pointTextSize);//璁剧疆瀛椾綋澶у皬
        mPaint.setStrokeWidth(lineHeight);//璁剧疆绾跨殑绮�
        float firstEndX = getFirstLineX();
        float lineY = this.getPaddingTop() + lineHeight / 2;
        float lineStartX = this.getPaddingLeft();
        float lineEndX = ViewUtils.getWindowWidth(context) - this.getPaddingRight();
        canvas.drawLine(lineStartX, lineY, firstEndX, lineY, mPaint);//鐢荤豢鑹茬嚎
        mPaint.setColor(pointLineColorEnd);//璁剧疆瀛椾綋棰滆壊
        canvas.drawLine(firstEndX, lineY, lineEndX, lineY, mPaint);//鐢荤伆鑹茬嚎
        drawPoint(canvas);
        drawTitle(canvas);
    }

    /**
     * 鐢绘爣棰�
     */
    private void drawTitle(Canvas canvas) {
        if(titles == null && titles.length != stateNum){
            Log.e(TAG,"鏍囬闀垮害鍜岃妭鐐逛釜鏁颁笉涓�嚧");
        }else{
            float lineY = this.getPaddingTop() + lineHeight / 2;
            mPaint.setColor(pointTextColor);//璁剧疆瀛椾綋棰滆壊
            mPaint.setTextSize(pointTextSize);//璁剧疆瀛椾綋澶у皬
            for(int i = 0 ; i < titles.length; i++){
                float pointX = getPointX(i);
                mPaint.getTextBounds(titles[i], 0, titles[i].length(), mBound);//鑾峰彇瀛楃涓茬殑楂樺害鍜屽搴�
                int textW = mBound.width();
                int textH = mBound.height();
                float x = pointX - textW/2;
                float y = lineY + marginInLineAndText + textH / 2;
                canvas.drawText(titles[i],x,y,mPaint);
            }
        }
    }

    /** 鑾峰彇宸茬粡瀹屾垚鐨勮妭鐐圭殑x鍧愭爣
     * @param position
     * @return
     */
    private float getPointX(int position){
        float deviceW = ViewUtils.getWindowWidth(context);//鑾峰彇灞忓箷瀹藉害
        int len = 0;
        len = (int) (deviceW - pointMargin * 2 - this.getPaddingLeft() - this.getPaddingRight()) / (stateNum - 1);//鑾峰彇涓棿閭ｆ鐨勯暱搴�
        int x = this.getPaddingLeft() + pointMargin;
        return x + len * position;
    }

    /**
     * 鐢昏妭鐐�
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        //鐢荤粨鏉熺偣
        float lineY = this.getPaddingTop() + lineHeight / 2;
        if(state > 1){
            for(int i = 0;i < state - 1; i++){
                Bitmap bitmapComplete = drawable2Bitmap(getResources().getDrawable(R.drawable.ic_complete));
                if(bitmapComplete != null){
                    int left = bitmapComplete.getWidth()/2;
                    int top = bitmapComplete.getHeight()/2;
                    float x = getPointX(i);
                    canvas.drawBitmap(bitmapComplete,x - left,lineY - top,mPaint);
                }
            }
        }
        if(state <= stateNum){
            //鐢诲綋鍓嶇偣
            Bitmap bitmapCurrent = drawable2Bitmap(getResources().getDrawable(R.drawable.ic_current));
            if(bitmapCurrent != null){
                int left = bitmapCurrent.getWidth()/2;
                int top = bitmapCurrent.getHeight()/2;
                float x = getPointX(state - 1);
                canvas.drawBitmap(bitmapCurrent,x - left,lineY - top,mPaint);
            }
            //鐢绘湭瀹屾垚鐐�
            for(int i = state ; i < stateNum; i++){
                Bitmap bitmapUncomplete = drawable2Bitmap(getResources().getDrawable(R.drawable.ic_uncomplete));
                if(bitmapUncomplete != null){
                    int left = bitmapUncomplete.getWidth()/2;
                    int top = bitmapUncomplete.getHeight()/2;
                    float x = getPointX(i);
                    canvas.drawBitmap(bitmapUncomplete,x - left,lineY - top,mPaint);
                }
            }
        }
    }
    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 璁剧疆鐘舵�鏍忕殑鏍囬
     * @param titles 鏍囬鏁扮粍
     */
    public void setTitle(String[] titles){
        if(titles == null && titles.length != stateNum){
            Log.e(TAG,"title 和状态的个数不一致");
        }else{
            this.titles = titles;
        }
        invalidate();
    }

    /**
     * 璁剧疆褰撳墠鐘舵�
     * @param state 鐘舵�鏍囪瘑锛�涓虹涓�釜鐘舵�
     */
    public void setState(int state){
        if(state <= stateNum){
            if(state <= 0){
                this.state = 1;
            }else{
                this.state = state;
            }
        }else{
            this.state = stateNum + 1;
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int width;
        int height;
//        if(widthMode == MeasureSpec.EXACTLY){
//            width = widthSize;
//        }else{
//            mPaint.setTextSize(pointTextSize);
//            mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
//            float textWidth = mBounds.width();
//            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
//            width = desired;
//        }
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            mPaint.setTextSize(pointTextSize);
            mPaint.getTextBounds("测试", 0, 2, mBound);//鏆傛椂鍙槸瑕侀珮搴︼紝鎵�互闅忎究鐢ㄤ竴涓瓧绗︿覆
            float textHeight = mBound.height();
            height = (int)(this.getPaddingTop() + lineHeight + marginInLineAndText + textHeight + this.getPaddingBottom());
        }
        setMeasuredDimension(widthMeasureSpec, height);
    }


}
