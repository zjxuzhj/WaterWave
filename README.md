####自定义绚丽水波纹效果

效果展示
![效果展示](http://upload-images.jianshu.io/upload_images/1877523-0d20910b312692a4.gif?imageMogr2/auto-orient/strip)


 
####需求
---
模拟水波纹的效果:点击屏幕就有圆环出现,半径从小到大,透明度从大到小(0为透明)

####实现思路
---
1. 自定义类继承View。
2. 定义每个圆环的实体类 Wave，并初始化绘制圆环的画笔的数据。
2. 重写onTouchEvent方法，down时，获得坐标点，做为圆环圆心。
3. 发送handler信息，对数据进行修改，刷新页面。
4. 重写onDraw方法，绘制一个圆环。
 
####1. 自定义类继承View
---
- 新建WaterWaveView2类继承View

```
public class WaterWaveView2 extends View {
 
    //存放圆环的集合
    private ArrayList<Wave> mList;
 
    //界面刷新
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();//刷新界面,会执行onDraw方法
        }
    };
 
    public WaterWaveView2(Context context) {
        this(context, null);
    }
 
    public WaterWaveView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mList = new ArrayList<Wave>();
    }

```

####2. 定义实体类 Wave
---

```
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
    public int[] randomColor={Color.BLUE,Color.CYAN,
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
        paint=new Paint();//因为点击一次需要画出不同的圆环
        paint.setAntiAlias(true);//打开抗锯齿
        ranNum=(int) (Math.random()*6);//[0,5]的随机数
        paint.setColor(randomColor[ranNum]);//设置画笔的颜色
        paint.setStyle(Paint.Style.STROKE);//描边
        paint.setStrokeWidth(width);//设置描边宽度
        paint.setAlpha(255);//透明度的设置(0-255),0为完全透明
        radius=0;//初始化
        width=0;
    }
}

```

####3. 重写onTouchEvent方法
---
- 获得圆心，并且删除集合中透明度为0的圆环，通知handler调用onDraw()方法

```
public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
 
                float x = event.getX();
                float y = event.getY();
                deleteItem();
                Wave wave = new Wave(x, y);
                mList.add(wave);
 
                //刷新界面
                invalidate();
                break;
 
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                deleteItem();
                Wave wave1 = new Wave(x1, y1);
                mList.add(wave1);
 
                invalidate();
                break;
        }
        //处理事件
        return true;
    }
    //删除透明度已经为0的圆环
    private void deleteItem(){
        for (int i = 0; i <mList.size() ; i++) {
            if(mList.get(i).paint.getAlpha()==0){
                mList.remove(i);
            }
        }
    }
}

```

####4. 重写onDraw()方法，循环绘制圆环
```
protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
 
        //避免程序一运行就进行绘制
        if (mList.size() > 0) {
 
            //对集合中的圆环对象循环绘制
            for (Wave wave : mList) {
                canvas.drawCircle(wave.x, wave.y, wave.radius, wave.paint);
                wave.radius += 3;
                //对画笔透明度进行操作
                int alpha = wave.paint.getAlpha();
                if (alpha < 80) {
                    alpha = 0;
                } else {
                    alpha -= 3;
                }
 
                //设置画笔宽度和透明度
                wave.paint.setStrokeWidth(wave.radius / 8);
                wave.paint.setAlpha(alpha);
 
                //延迟刷新界面
                mHandler.sendEmptyMessageDelayed(1, 100);
            }
        }
    }

```

这里是[项目地址](https://github.com/zjxuzhj/WaterWave)。

参考
http://blog.csdn.net/cyp331203/article/details/41209357
http://www.cnblogs.com/tangs/articles/5730470.html
