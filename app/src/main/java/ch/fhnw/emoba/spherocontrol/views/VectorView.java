package ch.fhnw.emoba.spherocontrol.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import ch.fhnw.emoba.spherocontrol.models.SpheroMath;

public class VectorView extends View {

    private final VectorViewListener vectorViewListener;

    private final Paint outerZonePaint;

    private final Paint innerZonePaint;

    private final Paint linePaint;

    private final Point centerPoint;

    private final Point touchPoint;

    private int canvasHeight;

    private int canvasWidth;

    private int canvasRadius;

    public VectorView(Context context, VectorViewListener vectorViewListener) {
        super(context);

        this.vectorViewListener = vectorViewListener;

        outerZonePaint = new Paint();
        outerZonePaint.setColor(Color.DKGRAY);

        innerZonePaint = new Paint();
        innerZonePaint.set(outerZonePaint);
        innerZonePaint.setAlpha(100);

        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);

        centerPoint = new Point();

        touchPoint = new Point();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        canvasRadius = (int) (((canvasHeight > canvasWidth) ? canvasWidth : canvasHeight) * 0.9) / 2;

        centerPoint.x = canvasWidth / 2;
        centerPoint.y = canvasHeight / 2;

        linePaint.setStrokeWidth((float) (canvasRadius * 0.05));

        canvas.drawCircle(centerPoint.x, centerPoint.y, canvasRadius, innerZonePaint);
        canvas.drawCircle(centerPoint.x, centerPoint.y, canvasRadius * 0.1f, outerZonePaint);
        if (touchPoint.x != 0 && touchPoint.y != 0) {
            canvas.drawLine(centerPoint.x, centerPoint.y, touchPoint.x, touchPoint.y, linePaint);
            canvas.drawCircle(touchPoint.x, touchPoint.y, canvasRadius * 0.02f, linePaint);
        }
        canvas.drawCircle(centerPoint.x, centerPoint.y, canvasRadius * 0.02f, linePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = (event.getX() - centerPoint.x) / canvasRadius;
            float y = ((event.getY() - centerPoint.y) * -1) / canvasRadius;

            // Check if the value is within/on the unit circle: http://www.mathe-online.at/lernpfade/einheitskreis/?kapitel=2
            float velocity = SpheroMath.calculateVelocity(x, y);
            float angle = SpheroMath.calculateAngle(x, y);
            if (velocity > 1) {
                x = (float) Math.cos(Math.toRadians(angle));
                y = (float) Math.sin(Math.toRadians(angle));
                velocity = 1;
            }

            touchPoint.x = (int) event.getX();
            touchPoint.y = (int) event.getY();
            vectorViewListener.onMove(x, y, angle, velocity);
            Log.d("sphero", "x: " + x + " " + y);
        } else {
            touchPoint.x = 0;
            touchPoint.y = 0;
            vectorViewListener.onRelease();
        }

        invalidate();
        return true;
    }
}
