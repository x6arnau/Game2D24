package org.proven.game2d24;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

public class GraficNau {
    private Drawable drawable;   //Imatge que dibuixarem
    private View view;			// Vista a la que dibuixarem
    private int ampleImg, altImg;     //Dimensions de l'imatge
    private double posX, posY;   //Posició de la Nau al layout (vista)
    private boolean direccioDreta = true;  // true = moure a la dreta, false = moure a l'esquerra

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
        //todo
        return false;
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
            posX += 15;  // Moure a la dreta
        } else {
            posX -= 15;  // Moure a l'esquerra
        }
    }

}
