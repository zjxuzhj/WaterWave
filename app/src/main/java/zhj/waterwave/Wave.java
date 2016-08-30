package zhj.waterwave;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by HongJay on 2016/8/30.
 * 把wave的数据封装成一个对象
 */
public class Wave {
    public float x;//圆心x坐标
    public float y;//圆心y坐标
    public Paint paint; //画圆的画笔
    public float width; //线条宽度
    public int radius; //圆的半径
    public int ranNum;//随机数
    public int[] randomColor={Color.WHITE,Color.BLUE,Color.CYAN,
            Color.GREEN,Color.MAGENTA,Color.RED,Color.YELLOW};

    public Wave(float x, float y) {
        this.x = x;
        this.y = y;
        initData();
    }
    /**
     * 初始化数据,每次点击一次都要初始化一次
     */
    private void initData() {
        paint=new Paint();//因为点击一次需要画出不同半径的圆
        paint.setAntiAlias(true);//打开抗锯齿
        ranNum=(int) (Math.random()*7);//[0,6]的随机数
        paint.setColor(randomColor[ranNum]);//设置画笔的颜色
        paint.setStyle(Paint.Style.STROKE);//画出空心圆
        paint.setStrokeWidth(width);//设置空心圆的外面线条宽度
        paint.setAlpha(255);//透明度的设置(0-255),0为完全透明
        radius=0;//初始化
        width=0;
    }
}
