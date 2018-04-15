package ch.fhnw.emoba.spherocontrol.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import ch.fhnw.emoba.spherocontrol.models.SpheroMath;

public class VectorView extends View {

    public enum DrawStrategy {
        AIM,
        TOUCH,
        SENSOR
    }

    private final VectorViewListener vectorViewListener;

    private final DrawStrategy drawStrategy;

    private final Paint outerZonePaint;

    private final Paint innerZonePaint;

    private final Paint linePaint;

    private final Point centerPoint;

    private final Point touchPoint;

    private final Point fixedPoint;

    private int canvasRadius;

    public VectorView(Context context, VectorViewListener vectorViewListener, DrawStrategy drawStrategy) {
        super(context);

        this.vectorViewListener = vectorViewListener;
        this.drawStrategy = drawStrategy;

        outerZonePaint = new Paint();
        outerZonePaint.setColor(Color.DKGRAY);

        innerZonePaint = new Paint();
        innerZonePaint.set(outerZonePaint);
        innerZonePaint.setAlpha(100);

        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);

        centerPoint = new Point();

        touchPoint = new Point();

        fixedPoint = new Point();
    }

    @Override
    public void onDraw(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        canvasRadius = (int) (((canvasHeight > canvasWidth) ? canvasWidth : canvasHeight) * 0.9) / 2;

        centerPoint.x = canvasWidth / 2;
        centerPoint.y = canvasHeight / 2;

        linePaint.setStrokeWidth((float) (canvasRadius * 0.05));

        canvas.drawCircle(centerPoint.x, centerPoint.y, canvasRadius, innerZonePaint);
        canvas.drawCircle(centerPoint.x, centerPoint.y, canvasRadius * 0.1f, outerZonePaint);
        if (touchPoint.x != 0 && touchPoint.y != 0) {
            if (drawStrategy == DrawStrategy.AIM) {
                canvas.drawCircle(fixedPoint.x, fixedPoint.y, canvasRadius * 0.1f, linePaint);
            } else if (drawStrategy == DrawStrategy.TOUCH) {
                canvas.drawLine(centerPoint.x, centerPoint.y, touchPoint.x, touchPoint.y, linePaint);
                canvas.drawCircle(touchPoint.x, touchPoint.y, canvasRadius * 0.1f, linePaint);
            } else {

            }
        }
        canvas.drawCircle(centerPoint.x, centerPoint.y, canvasRadius * 0.02f, linePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            float initialX = event.getX();
            float initialY = event.getY();

            float relativeX = initialX - centerPoint.x;
            float relativeY = (initialY - centerPoint.y) * -1;

            float circleX = relativeX / canvasRadius;
            float circleY = relativeY / canvasRadius;

            float circleVelocity = SpheroMath.calculateVelocity(circleX, circleY);
            float circleAngle = SpheroMath.calculateAngle(circleX, circleY);

            float spheroAngle = SpheroMath.calculateSpheroAngle(circleX, circleY);

            if (circleVelocity > 1.0) {
                circleVelocity = 1.0f;
            }

            circleX = (float) Math.cos(Math.toRadians(circleAngle));
            circleY = (float) Math.sin(Math.toRadians(circleAngle));

            relativeX = circleX * canvasRadius;
            relativeY = circleY * canvasRadius;

            float fixedX = relativeX + centerPoint.x;
            float fixedY = (relativeY * -1) + centerPoint.y;

            touchPoint.x = (int) initialX;
            touchPoint.y = (int) initialY;
            fixedPoint.x = (int) fixedX;
            fixedPoint.y = (int) fixedY;

            vectorViewListener.onMove(spheroAngle, circleVelocity);
        } else {
            touchPoint.x = 0;
            touchPoint.y = 0;
            fixedPoint.x = 0;
            fixedPoint.y = 0;
            vectorViewListener.onRelease();
        }

        invalidate();
        return true;
    }
}
