package longnd.thesis.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;
import longnd.thesis.R;

public class GraphRiasecView extends View {
    private Context context;
    private float R_SPACE = 20.0f;
    private float C_SPACE = 70.0f;

    private float FIRST_SPACE = 120.0f;
    private int[] results;

    private Paint mStrokePaint;
    private Paint mTextPaint;
    private Paint mRectPaint;

    private Path mTrianglePath;
    private int min;
    private int space;

    float Ox;
    float Oy;

    public GraphRiasecView(Context context) {
        this(context, null);
    }

    public GraphRiasecView(Context context, AttributeSet attrs) {
        this(context, attrs, 1);
    }

    public GraphRiasecView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }

    public void setData(int min, int space, int[] results){
        this.min = min;
        this.space = space;
        this.results = results;
    }

    private void initPaint() {
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setColor(ContextCompat.getColor(context, R.color.stroke_column));
        mStrokePaint.setStrokeWidth(2);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(ContextCompat.getColor(context, R.color.text_charts));
        mTextPaint.setStrokeWidth(15);
        mTextPaint.setTextSize(25);

        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setStrokeWidth(2);

        mTrianglePath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFrame(canvas);
        drawUnit(canvas, min, space);
        drawProperties(canvas);
        for (int i = 0; i < 6; i++) {
            drawCharts(canvas, i, results[i]);
        }

    }

    private void drawCharts(Canvas canvas, int i, int nscore) {
        float A1x = 40 + R_SPACE * i + 20;
        float A1y = 679;

        float A2x = 40 + R_SPACE * (i + 1) - 20;
        float A2y = 679;

        float B1x = 40 + R_SPACE * i + 20;
        float B1y = 679 - ((nscore - min) * C_SPACE + 20);

        float B2x = 40 + R_SPACE * (i + 1) - 20;
        float B2y = 679 - ((nscore - min) * C_SPACE + 20);

        float O1x = A1x;
        float O1y = (A1y + B1y) / 2;

        float Tx = (A1x + A2x) / 2;
        float Ty = B1y;

        String score = String.valueOf(nscore);
        canvas.drawText(score, Tx - mTextPaint.measureText(score) / 2, Ty - 10, mTextPaint);

        Paint mPaintChart = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintChart.setStrokeWidth(2);
        switch (i) {
            case 0:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row1_1));
                break;
            case 1:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row2_1));
                break;
            case 2:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row3_1));
                break;
            case 3:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row4_1));
                break;
            case 4:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row5_1));
                break;
            case 5:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row6_1));
                break;
        }
        mTrianglePath.reset();
        mTrianglePath.moveTo(B1x, B1y);
        mTrianglePath.lineTo(O1x, O1y);
        mTrianglePath.lineTo(B2x, B2y);
        mTrianglePath.moveTo(B1x, B1y);
        mTrianglePath.close();
        canvas.drawPath(mTrianglePath, mPaintChart);

        switch (i) {
            case 0:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row1_2));
                break;
            case 1:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row2_2));
                break;
            case 2:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row3_2));
                break;
            case 3:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row4_2));
                break;
            case 4:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row5_2));
                break;
            case 5:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row6_2));
                break;
        }
        mTrianglePath.reset();
        mTrianglePath.moveTo(B2x, B2y);
        mTrianglePath.lineTo(O1x, O1y);
        mTrianglePath.lineTo(A2x, A2y);
        mTrianglePath.moveTo(B2x, B2y);
        mTrianglePath.close();
        canvas.drawPath(mTrianglePath, mPaintChart);

        switch (i) {
            case 0:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row1_3));
                break;
            case 1:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row2_3));
                break;
            case 2:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row3_3));
                break;
            case 3:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row4_3));
                break;
            case 4:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row5_3));
                break;
            case 5:
                mPaintChart.setColor(ContextCompat.getColor(context, R.color.row6_3));
                break;
        }
        mTrianglePath.reset();
        mTrianglePath.moveTo(A2x, A2y);
        mTrianglePath.lineTo(O1x, O1y);
        mTrianglePath.lineTo(A1x, A1y);
        mTrianglePath.moveTo(A2x, A2y);
        mTrianglePath.close();
        canvas.drawPath(mTrianglePath, mPaintChart);
    }

    private void drawProperties(Canvas canvas) {
        mTextPaint.setTextSize(20);

        String rule = getResources().getString(R.string.rule);
        canvas.drawText(rule, Ox + R_SPACE * 1 / 2 - mTextPaint.measureText(rule) / 2, Oy + 20, mTextPaint);

        String society = getResources().getString(R.string.society);
        canvas.drawText(society, Ox + R_SPACE * 3 / 2 - mTextPaint.measureText(society) / 2, Oy + 20, mTextPaint);

        String discover = getResources().getString(R.string.discover);
        canvas.drawText(discover, Ox + R_SPACE * 5 / 2 - mTextPaint.measureText(discover) / 2, Oy + 20, mTextPaint);

        String reality = getResources().getString(R.string.reality);
        canvas.drawText(reality, Ox + R_SPACE * 7 / 2 - mTextPaint.measureText(reality) / 2, Oy + 20, mTextPaint);

        String art = getResources().getString(R.string.art);
        canvas.drawText(art, Ox + R_SPACE * 9 / 2 - mTextPaint.measureText(art) / 2, Oy + 20, mTextPaint);

        String convince = getResources().getString(R.string.convince);
        canvas.drawText(convince, Ox + R_SPACE * 11 / 2 - mTextPaint.measureText(convince) / 2, Oy + 20, mTextPaint);
    }

    private void drawUnit(Canvas canvas, int min, int space) {
        // canvas.drawText("0", Ox - 20, Oy + 20, mTextPaint);
        canvas.drawText(String.valueOf(min), Ox - 35, Oy - 10, mTextPaint);
        canvas.drawText(String.valueOf(min + space), Ox - 35, Oy - 10 - (C_SPACE * 1), mTextPaint);
        canvas.drawText(String.valueOf(min + space * 2), Ox - 35, Oy - 10 - (C_SPACE * 2), mTextPaint);
        canvas.drawText(String.valueOf(min + space * 3), Ox - 35, Oy - 10 - (C_SPACE * 3), mTextPaint);
        canvas.drawText(String.valueOf(min + space * 4), Ox - 35, Oy - 10 - (C_SPACE * 4), mTextPaint);
        canvas.drawText(String.valueOf(min + space * 5), Ox - 35, Oy - 10 - (C_SPACE * 5), mTextPaint);
        canvas.drawText(String.valueOf(min + space * 6), Ox - 35, Oy - 10 - (C_SPACE * 6), mTextPaint);
        canvas.drawText(String.valueOf(min + space * 7), Ox - 35, Oy - 10 - (C_SPACE * 7), mTextPaint);
    }

    private void drawFrame(Canvas canvas) {
        float endX = getWidth();
        Ox = 40;
        Oy = 680;
        canvas.drawLine(40, 170, 40, 680, mStrokePaint);
        float space = endX - (40 * 2);
        boolean isMeasuring = true;
        while (isMeasuring) {
            if (R_SPACE * 6 < space) {
                R_SPACE += 10f;
            } else {
                isMeasuring = false;
            }
        }

        mStrokePaint.setColor(ContextCompat.getColor(context, R.color.stroke_row_end));
        canvas.drawLine(40 + R_SPACE * 0 + 10, 680, 40 + R_SPACE * 1 - 10, 680, mStrokePaint);
        canvas.drawLine(40 + R_SPACE * 1 + 10, 680, 40 + R_SPACE * 2 - 10, 680, mStrokePaint);
        canvas.drawLine(40 + R_SPACE * 2 + 10, 680, 40 + R_SPACE * 3 - 10, 680, mStrokePaint);
        canvas.drawLine(40 + R_SPACE * 3 + 10, 680, 40 + R_SPACE * 4 - 10, 680, mStrokePaint);
        canvas.drawLine(40 + R_SPACE * 4 + 10, 680, 40 + R_SPACE * 5 - 10, 680, mStrokePaint);
        canvas.drawLine(40 + R_SPACE * 5 + 10, 680, 40 + R_SPACE * 6 - 10, 680, mStrokePaint);

        mStrokePaint.setColor(ContextCompat.getColor(context, R.color.stroke_row));
        canvas.drawRect(40, 680 - 20 - (C_SPACE * 0), 40 + R_SPACE * 6, 680 - 20 - (C_SPACE * 1), mStrokePaint);
        canvas.drawRect(40, 680 - 20 - (C_SPACE * 2), 40 + R_SPACE * 6, 680 - 20 - (C_SPACE * 3), mStrokePaint);
        canvas.drawRect(40, 680 - 20 - (C_SPACE * 4), 40 + R_SPACE * 6, 680 - 20 - (C_SPACE * 5), mStrokePaint);
        canvas.drawRect(40, 680 - 20 - (C_SPACE * 6), 40 + R_SPACE * 6, 680 - 20 - (C_SPACE * 7), mStrokePaint);
    }
}
