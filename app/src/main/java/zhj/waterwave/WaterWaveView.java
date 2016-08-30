package zhj.waterwave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by HongJay on 2016/8/29.
 */
public class WaterWaveView extends View {


    private int roundX;//圆心X轴坐标
    private int roundY;//圆心Y轴坐标
    private Paint paint;//画圆的画笔
    private float rwidth;//圆的线条的宽度
    private int radius;//圆的半径
    private int ranNum;//随机数
    private int[] randomColor = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY,
            Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED, Color.YELLOW};
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();//刷新界面,会执行onDraw方法
        }
    };

    public WaterWaveView(Context context) {
        super(context);
    }

    public WaterWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaterWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 初始化数据,每次点击一次都要初始化一次
     */
    private void initData() {
        paint = new Paint();//因为点击一次需要画出不同半径的圆
        paint.setAntiAlias(true);//打开抗锯齿
        ranNum = (int) (Math.random() * 10);//[0,9]的随机数
        paint.setColor(randomColor[ranNum]);//设置画笔的颜色
        paint.setStyle(Paint.Style.STROKE);//画出空心圆
        paint.setStrokeWidth(rwidth);//设置空心圆的外面线条宽度
        paint.setAlpha(255);//透明度的设置(0-255),0为完全透明
        radius = 0;//初始化,也是每次的清零
        rwidth = 0;
    }

    @Override
    /**
     * 绘制显示的内容,一运行程序就会执行一次onDraw方法
     * 调用invalidate(刷新界面)方法的后也会调用此方法
     */
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (paint != null) {//避免程序一运行就执行drawRound方法
            drawRound(canvas);
        }
    }

    /**
     * 一次点击圆环的绘制
     */
    private void drawRound(Canvas canvas) {
        //绘制圆环(圆心坐标,半径,画笔)
        canvas.drawCircle(roundX, roundY, radius, paint);
        radius = radius + 5;
        int alpha = paint.getAlpha();//获取透明度
        if (alpha != 0) {
            if (alpha <= 20) {
                alpha = 0;
            } else {
                alpha = alpha - 5;
            }
            paint.setStrokeWidth(radius / 10);//设置空心圆的外面线条宽度
            paint.setAlpha(alpha);//设置透明度
            handler.sendEmptyMessageDelayed(1, 100);//延时100ms发送
        }
    }

    @Override
    /**
     * 获取触摸的坐标,以确定圆心
     */
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://获取按下的坐标
                roundX = (int) event.getX();
                roundY = (int) event.getY();

                initData();//每次点击后要初始化数据
                invalidate();//刷新界面,会调用onDraw方法
                break;
        }
        return true;
    }

    /**
     * 不需要重写,因为没有在该控件上添加控件
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
