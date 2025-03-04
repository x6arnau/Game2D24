package org.proven.game2d24;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

/*
 * GraficNau class
 * author: Arnau Nuñez
 * data: 04/03/2025
 * grup: DAM2
 */
public class GraficNau {
    private Drawable drawable;   //Imatge que dibuixarem
    private View view;			// Vista a la que dibuixarem
    private int ampleImg, altImg;     //Dimensions de l'imatge
    private double posX, posY;   //Posició de la Nau al layout (vista)
    private boolean direccioDreta = true;  // true = moure a la dreta, false = moure a l'esquerra
    private final int STEP = 15;  // Velocitat de moviment

    protected GraficNau(View viewParam, Drawable drawableNau) {
        this.view = viewParam;
        this.drawable = drawableNau;

        // Agafem l'ample i l'alt de la imatge
        this.ampleImg = drawable.getIntrinsicWidth();
        this.altImg = drawable.getIntrinsicHeight();

        // Posició de la nau a la vista inical
        this.posX = this.ampleImg + 5;
        this.posY = this.altImg + 5;
    }

    public void dibuixaGrafic(Canvas canvas){
        canvas.save();

        // definim el rectangle on posicionarem la nau
        drawable.setBounds((int)posX, (int)posY, (int)posX + ampleImg, (int)posY + altImg);
        drawable.draw(canvas);
        canvas.restore();

    }

    // Posició de la Nau a la VISTA
    public void setPosX(double v) {
        this.posX=v;
    }
    public void setPosY(double v) {
        this.posY=v;
    }
    public double getPosX() {
        return this.posX;
    }
    public double getPosY() {
        return this.posY;
    }

    public boolean collisionBallAndNau(Ball b, GraficNau nau) {
        // Calcular el centro de la nave
        double nauCenterX = nau.getPosX() + (ampleImg / 2.0);
        double nauCenterY = nau.getPosY() + (altImg / 2.0);

        // Calcular la distancia entre los centros
        double distancia = Math.sqrt(
            Math.pow(nauCenterX - b.getX(), 2) +
            Math.pow(nauCenterY - b.getY(), 2)
        );

        // Comprobar si hay colisión (distancia menor que la suma de radios)
        // Usamos el radio más pequeño de la nave (ancho o alto / 2) para aproximar
        double nauRadius = Math.min(ampleImg, altImg) / 2.0;
        return distancia <= (nauRadius + b.getRadius());
    }

    public void move() {
        int screenWidth = view.getWidth();
        if (posX <= 0) {  // Comprovar límit esquerra
            direccioDreta = true;
        } else if (posX >= screenWidth - ampleImg) {  // Dreta límit
            direccioDreta = false;
        }

        // Moure segons la direcció
        if (direccioDreta) {
            posX += STEP;  // Moure a la dreta
        } else {
            posX -= STEP;  // Moure a l'esquerra
        }
    }

}
