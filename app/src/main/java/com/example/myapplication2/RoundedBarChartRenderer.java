package com.example.myapplication2;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class RoundedBarChartRenderer extends BarChartRenderer {

    private final float mRadius;

    public RoundedBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler,
            float radius) {
        super(chart, animator, viewPortHandler);
        this.mRadius = radius;
    }

    @Override
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        android.graphics.Paint p = mRenderPaint;

        com.github.mikephil.charting.components.YAxis.AxisDependency trans = dataSet.getAxisDependency();
        com.github.mikephil.charting.utils.Transformer transY = mChart.getTransformer(trans);

        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(mAnimator.getPhaseX(), mAnimator.getPhaseY());
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(trans));
        buffer.setBarWidth(mChart.getBarData().getBarWidth());

        buffer.feed(dataSet);

        transY.pointValuesToPixel(buffer.buffer);

        final boolean isSingleColor = dataSet.getColors().size() == 1;

        if (isSingleColor) {
            p.setColor(dataSet.getColor());
        }

        for (int j = 0; j < buffer.size(); j += 4) {

            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                continue;

            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                break;

            if (!isSingleColor) {
                p.setColor(dataSet.getColor(j / 4));
            }

            float left = buffer.buffer[j];
            float top = buffer.buffer[j + 1];
            float right = buffer.buffer[j + 2];
            float bottom = buffer.buffer[j + 3];

            Path path = new Path();
            float[] radii = new float[] {
                    mRadius, mRadius, // Top left
                    mRadius, mRadius, // Top right
                    0f, 0f, // Bottom right
                    0f, 0f // Bottom left
            };
            path.addRoundRect(new RectF(left, top, right, bottom), radii, Path.Direction.CW);
            c.drawPath(path, p);
        }
    }
}
