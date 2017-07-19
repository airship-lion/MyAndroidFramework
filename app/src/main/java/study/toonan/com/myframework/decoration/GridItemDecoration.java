package study.toonan.com.myframework.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Administrator on 2016/8/29.
 */
public class GridItemDecoration  extends BaseItemDecoration {


    @Override
    protected void drawLine(Canvas c, Paint paint, float x, float y, int width, int height) {
        c.drawLine(x + width, y, x + width, y + height, paint);
        c.drawLine(x, y + height, x + width, y + height, paint);
        c.drawLine(x, y, x + width, y, paint);
    }
}