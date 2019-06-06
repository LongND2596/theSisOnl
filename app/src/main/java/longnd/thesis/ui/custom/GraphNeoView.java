package longnd.thesis.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;
import longnd.thesis.R;
import longnd.thesis.data.entity.APoint;

public class GraphNeoView extends View {
    private Context context;
    private float USPACE = 20.0f;

    private float FIRST_SPACE = 120.0f;


    private static final int TYPE_A = 1;
    private static final int TYPE_B = 2;
    private static final int TYPE_C = 3;
    private static final int TYPE_D = 4;
    private static final int TYPE_E = 5;

    private Paint mPaint;
    private Paint mStrokePaint;
    private Paint paintText;
    private Paint mDrawPoint;

    private Path mTrianglePath;

    private APoint A;
    private APoint B;
    private APoint C;
    private APoint D;
    private APoint E;
    private APoint O;

    private int min;
    private float space;
    private int[] results;


    public GraphNeoView(Context context) {
        this(context, null);
    }

    public GraphNeoView(Context context, AttributeSet attrs) {
        this(context, attrs, 1);
    }

    public GraphNeoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }

    public void setData(int min, float space, int[] results) {
        this.min = min;
        this.space = space;
        this.results = results;
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextCompat.getColor(context, R.color.stroke_chart));
        mPaint.setStrokeWidth(2);
        mTrianglePath = new Path();

        mDrawPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDrawPoint.setColor(ContextCompat.getColor(context, R.color.stroke_chart));
        mDrawPoint.setStrokeWidth(10);

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(ContextCompat.getColor(context, R.color.stroke_chart));
        paintText.setStrokeWidth(10);
        paintText.setTextSize(25);

        A = new APoint();
        B = new APoint();
        C = new APoint();
        D = new APoint();
        E = new APoint();
        O = new APoint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFrame(canvas);
        drawCharts(canvas);
    }

    /**
     * used to draw the frame of the map
     *
     * @param canvas
     */
    private void drawFrame(Canvas canvas) {
        float stopX = getWidth();
        float stopY = getHeight();
        // tâm O

        float radiusX = stopX / 2;
        float radiusY = stopY / 2;

        O.setX(stopX / 2);
        O.setY(stopY / 2 + 30);

        boolean isMeasuring = true;
        while (isMeasuring) {
            float length = FIRST_SPACE + 5 * USPACE + 100;
            if (length < radiusX && length < radiusY) {
                USPACE += 10;
            } else {
                isMeasuring = false;
            }
        }

        // Vòng 1
        drawPentagon(canvas, O.getX(), O.getY(), FIRST_SPACE + 0 * USPACE, 0);
        // vòng 2
        drawPentagon(canvas, O.getX(), O.getY(), FIRST_SPACE + 1 * USPACE, 1);
        // vòng 3
        drawPentagon(canvas, O.getX(), O.getY(), FIRST_SPACE + 2 * USPACE, 2);
        // vòng 4
        drawPentagon(canvas, O.getX(), O.getY(), FIRST_SPACE + 3 * USPACE, 3);
        // vòng 5
        drawPentagon(canvas, O.getX(), O.getY(), FIRST_SPACE + 4 * USPACE, 4);
        // vòng 6
        drawPentagon(canvas, O.getX(), O.getY(), FIRST_SPACE + 5 * USPACE, 5);
    }

    /**
     * used to draw points and fill the pentagon color by 5 points
     *
     * @param canvas
     */
    private void drawCharts(Canvas canvas) {
        // vẽ điểm A
        A.setX(O.getX());
        A.setY(O.getY() - FIRST_SPACE - (results[0] - min) * 1.0f / space * USPACE);
        // vẽ điểm B
        B = aCoordinatesPoint(results[1], TYPE_B);

        // vẽ điểm C
        C = aCoordinatesPoint(results[2], TYPE_C);

        // vẽ điểm D
        D = aCoordinatesPoint(results[3], TYPE_D);

        // vẽ điểm E
        E = aCoordinatesPoint(results[4], TYPE_E);

        Paint mPaintChart = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintChart.setStrokeWidth(2);

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(2);
        mStrokePaint.setColor(ContextCompat.getColor(context, R.color.trans));

        // kêt quả 1
        mPaintChart.setColor(ContextCompat.getColor(context, R.color.tric_one));
        mTrianglePath.reset();
        mTrianglePath.moveTo(O.getX(), O.getY());
        mTrianglePath.lineTo(A.getX(), A.getY());
        mTrianglePath.lineTo(B.getX(), B.getY());
        mTrianglePath.moveTo(O.getX(), O.getY());
        mTrianglePath.close();
        canvas.drawPath(mTrianglePath, mPaintChart);
        canvas.drawPath(mTrianglePath, mStrokePaint);

        // kêt quả 2
        mPaintChart.setColor(ContextCompat.getColor(context, R.color.tric_two));
        mTrianglePath.reset();
        mTrianglePath.moveTo(O.getX(), O.getY());
        mTrianglePath.lineTo(B.getX(), B.getY());
        mTrianglePath.lineTo(C.getX(), C.getY());
        mTrianglePath.moveTo(O.getX(), O.getY());
        mTrianglePath.close();
        canvas.drawPath(mTrianglePath, mPaintChart);
        canvas.drawPath(mTrianglePath, mStrokePaint);

        // kêt quả 3
        mPaintChart.setColor(ContextCompat.getColor(context, R.color.tric_three));
        mTrianglePath.reset();
        mTrianglePath.moveTo(O.getX(), O.getY());
        mTrianglePath.lineTo(C.getX(), C.getY());
        mTrianglePath.lineTo(D.getX(), D.getY());
        mTrianglePath.moveTo(O.getX(), O.getY());
        mTrianglePath.close();
        canvas.drawPath(mTrianglePath, mPaintChart);
        canvas.drawPath(mTrianglePath, mStrokePaint);

        // kêt quả 4
        mPaintChart.setColor(ContextCompat.getColor(context, R.color.tric_four));
        mTrianglePath.reset();
        mTrianglePath.moveTo(O.getX(), O.getY());
        mTrianglePath.lineTo(D.getX(), D.getY());
        mTrianglePath.lineTo(E.getX(), E.getY());
        mTrianglePath.moveTo(O.getX(), O.getY());
        mTrianglePath.close();
        canvas.drawPath(mTrianglePath, mPaintChart);
        canvas.drawPath(mTrianglePath, mStrokePaint);

        // kêt quả 5
        mPaintChart.setColor(ContextCompat.getColor(context, R.color.tric_five));
        mTrianglePath.reset();
        mTrianglePath.moveTo(O.getX(), O.getY());
        mTrianglePath.lineTo(E.getX(), E.getY());
        mTrianglePath.lineTo(A.getX(), A.getY());
        mTrianglePath.moveTo(O.getX(), O.getY());
        mTrianglePath.close();
        canvas.drawPath(mTrianglePath, mPaintChart);
        canvas.drawPath(mTrianglePath, mStrokePaint);
    }

    /**
     * used to get the coordinates of points
     *
     * @param result
     * @param type
     * @return object APoint
     */
    private APoint aCoordinatesPoint(int result, int type) {
        float radius = FIRST_SPACE + (result - min) * 1.0f / space * USPACE;
        float x = 0f;
        float y = 0f;
        switch (type) {
            case TYPE_B:
                x = (float) (O.getX() + radius * Math.cos(Math.toRadians(18)));
                y = (float) (O.getY() - radius * Math.cos(Math.toRadians(72)));
                break;
            case TYPE_C:
                x = (float) (O.getX() + radius * Math.cos(Math.toRadians(54)));
                y = (float) (O.getY() + radius * Math.cos(Math.toRadians(36)));
                break;
            case TYPE_D:
                x = (float) (O.getX() - radius * Math.cos(Math.toRadians(54)));
                y = (float) (O.getY() + radius * Math.sin(Math.toRadians(54)));
                break;
            case TYPE_E:
                x = (float) (O.getX() - radius * Math.cos(Math.toRadians(18)));
                y = (float) (O.getY() - radius * Math.sin(Math.toRadians(18)));
                break;
        }
        return new APoint(x, y);

    }


    /**
     * used to draw pentagon shapes with different distances from center to point
     *
     * @param canvas
     * @param Ox
     * @param Oy
     * @param radius
     * @param type
     */
    private void drawPentagon(Canvas canvas, float Ox, float Oy, float radius, int type) {
        float Ax = Ox;
        float Ay = Oy - radius;

        float Bx = (float) (Ox + radius * Math.cos(Math.toRadians(18)));
        float By = (float) (Oy - radius * Math.cos(Math.toRadians(72)));

        float Cx = (float) (Ox + radius * Math.cos(Math.toRadians(54)));
        float Cy = (float) (Oy + radius * Math.cos(Math.toRadians(36)));

        float Dx = (float) (Ox - radius * Math.cos(Math.toRadians(54)));
        float Dy = Cy;

        float Ex = (float) (Ox - radius * Math.cos(Math.toRadians(18)));
        float Ey = By;

        // 0 -> A
        canvas.drawLine(Ox, Oy, Ax, Ay, mPaint);
        // 0 -> B
        canvas.drawLine(Ox, Oy, Bx, By, mPaint);
        // 0 -> C
        canvas.drawLine(Ox, Oy, Cx, Cy, mPaint);
        // 0 -> D
        canvas.drawLine(Ox, Oy, Dx, Dy, mPaint);
        // 0 -> E
        canvas.drawLine(Ox, Oy, Ex, Ey, mPaint);

        // A -> B
        canvas.drawLine(Ax, Ay, Bx, By, mPaint);
        // B -> C
        canvas.drawLine(Bx, By, Cx, Cy, mPaint);
        // C -> D
        canvas.drawLine(Cx, Cy, Dx, Dy, mPaint);
        // D -> E
        canvas.drawLine(Dx, Dy, Ex, Ey, mPaint);
        // E -> A
        canvas.drawLine(Ex, Ey, Ax, Ay, mPaint);

        Paint mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setColor(ContextCompat.getColor(context, R.color.text_charts));
        mPaintText.setStrokeWidth(15);
        mPaintText.setTextSize(25);
        if (type == 5) {
            String a = "Dễ chấp nhận";
            canvas.drawText(a, Ax - mPaintText.measureText(a) / 2, Ay - 40, mPaintText);

            canvas.drawText("Tận tâm", Bx - 20, By - 40, mPaintText);

            String o = "Cởi mở, ham học hỏi";
            canvas.drawText(o, Cx - mPaintText.measureText(o) / 2, Cy + 60, mPaintText);

            String n = "Nhiễu tâm";
            canvas.drawText(n, Dx - mPaintText.measureText(n) / 2, Dy + 60, mPaintText);

            String e = "Hướng ngoại";
            canvas.drawText(e, Ex - mPaintText.measureText(e) / 2 - 10, Ey - 70, mPaintText);
            // vẽ điểm
            Paint mPaintChart = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaintChart.setColor(ContextCompat.getColor(context, R.color.bg_charts));
            mPaintChart.setStrokeWidth(2);

            // kêt quả
            mTrianglePath.reset();
            mTrianglePath.moveTo(Ox, Oy);
            mTrianglePath.lineTo(Ax, Ay);
            mTrianglePath.lineTo(Bx, By);
            mTrianglePath.lineTo(Ox, Oy);

            mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mStrokePaint.setStyle(Paint.Style.STROKE);
            mStrokePaint.setStrokeWidth(2);
            mStrokePaint.setColor(ContextCompat.getColor(context, R.color.trans));

            mTrianglePath.moveTo(Ox, Oy);
            mTrianglePath.lineTo(Bx, By);
            mTrianglePath.lineTo(Cx, Cy);
            mTrianglePath.lineTo(Ox, Oy);

            mTrianglePath.moveTo(Ox, Oy);
            mTrianglePath.lineTo(Cx, Cy);
            mTrianglePath.lineTo(Dx, Dy);
            mTrianglePath.lineTo(Ox, Oy);

            mTrianglePath.moveTo(Ox, Oy);
            mTrianglePath.lineTo(Dx, Dy);
            mTrianglePath.lineTo(Ex, Ey);
            mTrianglePath.lineTo(Ox, Oy);

            mTrianglePath.moveTo(Ox, Oy);
            mTrianglePath.lineTo(Ex, Ey);
            mTrianglePath.lineTo(Ax, Ay);
            mTrianglePath.lineTo(Ox, Oy);

            canvas.drawPath(mTrianglePath, mPaintChart);
            canvas.drawPath(mTrianglePath, mStrokePaint);

            mDrawPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mDrawPoint.setStrokeWidth(10);

            mDrawPoint.setColor(ContextCompat.getColor(context, R.color.point_a));
            canvas.drawCircle(Ax, Ay, 15, mDrawPoint);

            mDrawPoint.setColor(ContextCompat.getColor(context, R.color.point_b));
            canvas.drawCircle(Bx, By, 15, mDrawPoint);

            mDrawPoint.setColor(ContextCompat.getColor(context, R.color.point_c));
            canvas.drawCircle(Cx, Cy, 15, mDrawPoint);

            mDrawPoint.setColor(ContextCompat.getColor(context, R.color.point_d));
            canvas.drawCircle(Dx, Dy, 15, mDrawPoint);

            mDrawPoint.setColor(ContextCompat.getColor(context, R.color.point_e));
            canvas.drawCircle(Ex, Ey, 15, mDrawPoint);

            mDrawPoint.setColor(ContextCompat.getColor(context, R.color.point_bg));
            canvas.drawCircle(Ax, Ay, 8, mDrawPoint);
            canvas.drawCircle(Bx, By, 8, mDrawPoint);
            canvas.drawCircle(Cx, Cy, 8, mDrawPoint);
            canvas.drawCircle(Dx, Dy, 8, mDrawPoint);
            canvas.drawCircle(Ex, Ey, 8, mDrawPoint);
        }
    }
}
