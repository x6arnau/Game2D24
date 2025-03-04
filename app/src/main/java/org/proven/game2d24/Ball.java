package org.proven.game2d24;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/*
 * Ball class
 * author: Arnau Nuñez
 * data: 04/03/2025
 * grup: DAM2
 */
public class Ball {
    int x, y;  // Position
    int maxX, maxY; // Límit X Y
    int radius;
    Paint paint;  // Estil objecte
    int velocity;
    boolean directionX; // Horizontal
    boolean directionY; // Vertical

    public Ball() {
        x = 0;
        y = 0;
        maxX = 600;
        maxY = 800;
        radius = 20;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        velocity = 10;
        directionX = true;
        directionY = true;
    }

    public Ball(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }

    /**
     * Comprova si hi ha col·lisió entre dues boles
     * @param b - la bola amb la que es comprova la col·lisió
     */
    public boolean collision(Ball b) {
        boolean ret = false;
        double D =
                Math.sqrt(Math.pow(getX() - b.getX(), 2)
                        + Math.pow(getY() - b.getY(), 2));
        if (D <= (getRadius() + b.getRadius())) {
            ret = true;
        }
        return ret;
    }

    public void onDraw(Canvas canvas) {
        canvas.drawCircle(getX(), getY()
                , getRadius(), getPaint());
    }

    private boolean moveOnlyVertically = false;

    public void setMoveOnlyVertically(boolean moveOnlyVertically) {
        this.moveOnlyVertically = moveOnlyVertically;
    }

    public void move() {
        if (moveOnlyVertically) {
            // Per a les boles que només es mouen verticalment
            y = directionY ? y + velocity : y - velocity;
        } else {
            // Per a les boles que es mouen en qualsevol direcció
            x = directionX ? x + velocity : x - velocity;
            y = directionY ? y + velocity : y - velocity;
        }

        // Comprovar si la bola ha arribat als límits de la pantalla
        if (x - radius <= 0 || x + radius >= maxX) {
            directionX = !directionX;
        }

        // Comprovar si la bola ha arribat als límits de la pantalla
        if (y - radius <= 0 || y + radius >= maxY) {
            directionY = !directionY;
        }
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public boolean isDirectionX() {
        return directionX;
    }

    public void setDirectionX(boolean directionX) {
        this.directionX = directionX;
    }

    public boolean isDirectionY() {
        return directionY;
    }

    public void setDirectionY(boolean directionY) {
        this.directionY = directionY;
    }
}
