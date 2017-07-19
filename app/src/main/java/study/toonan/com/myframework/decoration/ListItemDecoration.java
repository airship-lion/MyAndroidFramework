package study.toonan.com.myframework.decoration;


import android.graphics.Canvas;
import android.graphics.Paint;

import study.toonan.com.myframework.util.UIUtils;

/**
 * Created by Administrator on 2016/8/29.
 */
public class ListItemDecoration extends BaseItemDecoration {


    @Override
    protected void drawLine(Canvas c, Paint paint, float x, float y, int width, int height) {
        c.drawLine(x+ UIUtils.dip2px(10), y + height, x + width-UIUtils.dip2px(10), y + height, paint);
    }
}