package com.example.porchpascual.theredbird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.logging.Level;

/**
 * Created by porch pascual on 6/1/2018.
 */

public class GameView extends View {

    // RedBird
    //private Bitmap redbird;
    private Bitmap redbird[] = new Bitmap[2];
    private int redbirdX = 10;
    private int redbirdY;
    private int redbirdSpeed;

    //Yellow Ball
    private int yellowX;
    private int yellowY;
    private int yellowSpeed = 15;
    private Paint yellowPaint = new Paint();

    // Cyan Ball
    private int cyanX;
    private int cyanY;
    private int cyanSpeed = 15;
    private Paint cyanPaint = new Paint();






    // Background
    private Bitmap bgImage;

    // Score
    private Paint scorePaint = new Paint();
    private int score;

    // Level
    private Paint levelPaint = new Paint();

    //Life
    private Bitmap life[] = new Bitmap[2];
    private int life_count;


    //Status Check
    private boolean touch_flg = false;


    //Canvas
    private int canvasWidth;
    private int canvasHeight;



    public GameView(Context context) {
        super(context);


        redbird[0] = BitmapFactory.decodeResource(getResources(),R.drawable.redbird1);
        redbird[1] = BitmapFactory.decodeResource(getResources(),R.drawable.redbird2);

        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.bg);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(true);

        cyanPaint.setColor(Color.CYAN);
        cyanPaint.setAntiAlias(false);

        scorePaint.setColor(Color.DKGRAY);
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        levelPaint.setColor((Color.BLUE));
        levelPaint.setTextSize(32);
        levelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        levelPaint.setTextAlign(Paint.Align.CENTER);
        levelPaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_g);

        // First Position
        redbirdY = 800;
        score = 0;
        life_count = 3;



    }

    @Override
    protected void onDraw(Canvas canvas){

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bgImage, -48, -35, null);

        // Redbird
        //canvas.drawBitmap(redbird, 0, 0, null);
        int minredbirdY = redbird[0].getHeight();
        int maxredbirdY = canvasHeight - redbird[0].getHeight() * 3;

        redbirdY += redbirdSpeed;
        if (redbirdY < minredbirdY) redbirdY = minredbirdY;
        if (redbirdY > maxredbirdY) redbirdY = maxredbirdY;
        redbirdSpeed += 2;

        if (touch_flg) {
            // Flapping wings
            canvas.drawBitmap(redbird[1], redbirdX, redbirdY, null);
            touch_flg = false;
        } else {
            canvas.drawBitmap(redbird[0], redbirdX, redbirdY, null );



        }

        // Yellow
        yellowX -= yellowSpeed;
        if (hitcheck(yellowX, yellowY)) {
            score += 10;
            yellowX = -100;

        }
        if (yellowX <0) {
            yellowX = canvasWidth + 20;
            yellowY = (int) Math.floor(Math.random()* (maxredbirdY - minredbirdY)) + minredbirdY;

        }

        canvas.drawCircle(yellowX, yellowY, 10, yellowPaint);

        //Cyan
        cyanX -= cyanSpeed;
        if (hitcheck(cyanX, cyanY)) {
            cyanX = -100;
            life_count--;
            if (life_count == 0) {
                // Game Over
                Log.v("MESSAGE", "GAME OVER");

            }

        }
        if (cyanX < 0) {
            cyanX = canvasWidth + 200;
            cyanY = (int) Math.floor(Math.random() * (maxredbirdY - minredbirdY)) + minredbirdY;
        }

        canvas.drawCircle(cyanX, cyanY, 20, cyanPaint);

        //Score

        canvas.drawText("Score : " + score, 20, 60, scorePaint);

        //Level
        canvas.drawText("Level1", canvasWidth / 2, 60, levelPaint);

        //Life
        for (int i = 0; i < 3; i++) {
            int x = (int) (320 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < life_count) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }

        }

        //canvas.drawBitmap(life[0], 340, 30, null);
        //canvas.drawBitmap(life[0], 380, 30, null);
        //canvas.drawBitmap(life[1], 420, 30, null);


    }

    public boolean hitcheck(int x, int y) {
        if (redbirdX < x && x < (redbirdX + redbird[0].getWidth()) &&
                redbirdY < y && y < (redbirdY + redbird[0].getHeight())) {
            return true;

        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            touch_flg = true;
            redbirdSpeed = -15;


        }
        return true;

    }
}
