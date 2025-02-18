package org.proven.game2d24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {

    ArrayList<Ball> listBalls;

    public GameView(Context context) {
        super(context);
        initBalls();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBalls();
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    randomBall(x, y);
                    return true;
                }
                return false;
            }
        });
    }

    private void randomBall(int x, int y) {
        Ball ball = new Ball(x, y);
        ball.setRadius((int) ((Math.random() + 0.35) * 100));
        ball.setVelocity((int) (Math.random() * 50));
        Paint p = new Paint();
        p.setColor(randomColor());
        p.setStrokeWidth(10);
        ball.setPaint(p);
        listBalls.add(ball);
    }

    private int randomColor() {
        //Con el random se generan colores aleatorios (rojo, azul, verde, amarillo)
        int color = (int) (Math.random() * 4);
        switch (color) {
            case 0:
                return Color.RED;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("onSizeChanged: " + w + "-" + h);
        for (Ball b : listBalls) {
            b.setMaxX(w);
            b.setMaxY(h);
        }
    }

    private void initBalls() {
        listBalls = new ArrayList<>();
        Ball ball = new Ball(200, 200);
        ball.setRadius(100);
        ball.setVelocity(30);
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStrokeWidth(10);
        ball.setPaint(p);

        Ball ball1 = new Ball(100, 400);
        ball1.setRadius(80);
        ball1.setVelocity(30);
        p = new Paint();
        p.setColor(Color.BLUE);
        ball1.setPaint(p);

        listBalls.add(ball);
        listBalls.add(ball1);
    }

    public void move() {
        for (Ball b : listBalls) {
            b.move();
        }
    }

    public void collisions() {
        Ball b1, b2;
        for (int i = 0; i < listBalls.size() - 1; i++) {
            b1 = listBalls.get(i);
            for (int j = i + 1; j < listBalls.size(); j++) {
                b2 = listBalls.get(j);
                if (b1.collision(b2)) {
                    //si hi ha colisio entre b1 i b2 del mateix color
                    if (b1.getPaint().getColor() == b2.getPaint().getColor()) {
                        //b1.setRadius(b1.getRadius() + b2.getRadius());
                        //b2.setRadius(0);
                        listBalls.remove(b1);
                        listBalls.remove(b2);
                    } else {
                        // HI HA COLISIO Invert Directions
                        b1.setDirectionX(!b1.isDirectionX());
                        b1.setDirectionY(!b1.isDirectionY());
                        b2.setDirectionX(!b2.isDirectionX());
                        b2.setDirectionY(!b2.isDirectionY());
                    }
                }
            }
        }
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        for (Ball b : listBalls) {
            b.onDraw(canvas);
        }
    }
}
