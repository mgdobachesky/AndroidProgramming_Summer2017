package com.edumobile.edumobiledev.minipong;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import java.util.Random;

/**
 * Created by edumobiledev on 7/10/15.
 */
public class BackgroundView extends View {

    private Context context;
    float x = -1;
    float y = -1;
    private Velocity velocity = new Velocity(10, 15);
    private Handler handler;
    private final long UPDATE_MILLIS = 30;

    //paint for the ball and paddle:
    private Paint paint = new Paint();
    //paint for the point text:
    private Paint textPaint = new Paint();
    private final float TEXT_SIZE = 112;

    //radius of the ball:
    private final float radius = 20;

    //for the paddle:
    private final float pSizeX = 100;
    private final float pSizeY = 20;
    private float paddleX;
    private float paddleY;

    //for repositioning the paddle during a touch:
    private float oldX;
    private float oldPaddleX;

    //for point display:
    private int points = 0;

    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        handler = new Handler();

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        textPaint.setColor(Color.CYAN);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);

        //get the height and width of the screen (this.getWidth() and this.getHeight()
        //both return 0 at this point):
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int dWidth = size.x;
        int dHeight = size.y;

        //position the paddle 3/4 of the way down the screen,
        //and in the horizontal center:
        paddleY = (dHeight / 4) * 3;
        paddleX = dWidth / 2;

        this.invalidate();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        //only process if touch is below the paddle:
        if (touchY > paddleY) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.getX();
                oldPaddleX = paddleX;
            }

            if (action == MotionEvent.ACTION_MOVE) {

                //move the paddle by the same amount as
                //the difference between the old touch and
                //the new touch, but don't let it go more than
                //halfway off the screen:
                float delta = oldX - touchX;
                float newPaddleX = oldPaddleX - delta;
                if (newPaddleX < 0) newPaddleX = pSizeX;
                else if (newPaddleX > this.getWidth()) newPaddleX = this.getWidth() - pSizeX;
                else paddleX = newPaddleX;
            }
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {

        if (x < 0 && y < 0) {
            x = this.getWidth()/2;
            y = this.getHeight()/4;
        } else {
            x += velocity.getX();
            y += velocity.getY();

            //wall collisions:
            if ((x > this.getWidth() - radius) || (x < 0 + radius)) {
                velocity.setX(velocity.getX() * -1);
            }

            if (y < 0 + radius) {
                velocity.setY(velocity.getY() * -1);
            }

            //paddle collision:
            /*
                condition is:
                    1. the ball's right edge overlaps the paddle's left edge,
                    2. the ball's left edge overlaps the paddle's right edge,
                    3. the ball's bottom edge overlaps the paddle's top edge,
                    4. but the ball's center is not below the paddle's center.

                condition 4 is new, fixes a bug where the ball collides with the
                right or left wall, then immediately with the paddle, forcing the
                ball to continue colliding with the paddle from below.
             */
            if (((x > paddleX - pSizeX - radius) && (x < paddleX + pSizeX + radius))
                    && ((y > paddleY - pSizeY - radius) && (y < paddleY))) {
                //increase the velocity by 1 in x and y, and
                //reverse the direction of y:
                velocity.setX(velocity.getX() + 1);
                velocity.setY((velocity.getY() + 1 ) * -1);

                //add a point:
                points++;
            }

            //missing the paddle:
            if (y > this.getHeight() - radius) {
                x = -1;
                y = -1;

                //set velocity's x component to some value in
                //the range [-15..-8, 8..15] (see method definition)
                velocity.setX(xDirection());
                //always use a y component of 15:
                velocity.setY(15);

                points = 0;
            }
        }

        //draw the ball:
        canvas.drawRect(x - radius, y - radius, x + radius, y + radius, paint);

        //draw the paddle:
        canvas.drawRect(paddleX - pSizeX, paddleY - pSizeY,
                paddleX + pSizeX, paddleY + pSizeY, paint);

        //draw the point text:
        canvas.drawText(String.format("%d", points), 0, TEXT_SIZE, textPaint);

        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    private float xDirection() {

        float[] possible =
                {-15, -14, -13, -12, -11, -10, -9, -8,
                   8,   9,  10,  11,  12,  13, 14, 15};
        int index = new Random().nextInt(15);
        return possible[index];
    }
}
