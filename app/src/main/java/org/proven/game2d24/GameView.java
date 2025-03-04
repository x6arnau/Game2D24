package org.proven.game2d24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

/*
 * GameView class
 * author: Arnau Nuñez
 * data: 04/03/2025
 * grup: DAM2
 */
public class GameView extends View {
    private ArrayList<Ball> bulletBalls;
    private int score = 0;
    ArrayList<Ball> listBalls;

    Drawable drawableNau;

    // Nau
    GraficNau nau;
    int width, height;

    public GameView(Context context) {
        super(context);
        initEnvioroment(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initEnvioroment(context);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    // Comprovar si s'ha tocat la nau o no
                    if (x >= nau.getPosX() && x <= nau.getPosX() + drawableNau.getIntrinsicWidth() &&
                        y >= nau.getPosY() && y <= nau.getPosY() + drawableNau.getIntrinsicHeight()) {
                        // Disparar bala si se toca la nave
                        shootBall();
                        return true;
                    } else {
                        // Crear bola si no se toca la nave
                        randomBall(x, y);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /*
     * Crear una bala en la posició de la nave
     */
    private void shootBall() {
        // Calcular el centro de la nave
        int shipCenterX = (int)(nau.getPosX() + drawableNau.getIntrinsicWidth() / 2);

        int shipTopY = (int)nau.getPosY();

        Ball bullet = new Ball(shipCenterX, shipTopY);
        bullet.setRadius(30);
        bullet.setVelocity(20);
        bullet.setMaxX(width);
        bullet.setMaxY(height);
        bullet.setDirectionY(false);
        bullet.setDirectionX(true);
        bullet.setMoveOnlyVertically(true);

        // Pintar la bala de blanco
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        bullet.setPaint(p);

        // Añadir la bala a la lista de balas
        bulletBalls.add(bullet);
    }

    /**
     * Crea una bola amb posició determinada,
     * velocitat, radi i color aleatori
     * @param x Coordenada x
     * @param y Coordenada y
     */
    private void randomBall(int x, int y) {
        Ball ball = new Ball(x, y);
        ball.setRadius((int) ((Math.random() + 0.35) * 100));
        ball.setVelocity((int) (Math.random() * 15));
        ball.setMaxX(width);
        ball.setMaxY(height);
        Paint p = new Paint();
        p.setColor(randomColor());
        p.setStrokeWidth(10);
        ball.setPaint(p);
        listBalls.add(ball);
    }

    /**
     * Retorna un color aleatori
     * @return Color
     */
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
        width = w;
        height = h;
        for (Ball b : listBalls) {
            b.setMaxX(w);
            b.setMaxY(h);
        }
        // Posicionar la nave en la parte inferior central
        nau.setPosX(w/2 - drawableNau.getIntrinsicWidth()/2);
        nau.setPosY(h - drawableNau.getIntrinsicHeight() - 10); // 10 píxeles de margen
    }

    /**
     * Inicialitza l'entorn del joc
     * @param context Context
     */
    private void initEnvioroment(Context context) {
        // Boles inicials
        listBalls = new ArrayList<>();
        Ball ball = new Ball(200, 200);
        ball.setRadius(100);
        ball.setVelocity(15);
        ball.setMaxX(width);
        ball.setMaxY(height);
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStrokeWidth(10);
        ball.setPaint(p);
        listBalls.add(ball);

        bulletBalls = new ArrayList<>();
        score = 0;

        // Reiniciar la nave
        if (nau == null) {
            // Primera inicializació
            drawableNau = context.getResources().getDrawable(R.drawable.nau, context.getTheme());
            nau = new GraficNau(this, drawableNau);
        }

        // Restablecer posició
        if (getWidth() > 0 && getHeight() > 0) {
            nau.setPosX(getWidth()/2 - drawableNau.getIntrinsicWidth()/2);
            nau.setPosY(getHeight() - drawableNau.getIntrinsicHeight() - 10);
        }

        setGameOver(false);
    }

    public void move() {
        // Mover las bolas
        for (Ball b : listBalls) {
            b.move();
        }

        // Mover las balas
        for (int i = bulletBalls.size() - 1; i >= 0; i--) {
            Ball bullet = bulletBalls.get(i);
            bullet.move();

            // Eliminar las balas que sobrepasen el límite superior
            if (bullet.getY() - bullet.getRadius() <= 0) {
                bulletBalls.remove(i);
            }
        }
    }

    public void moveNau() {
        nau.move();
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
        // Comprovar colisio amb la paret (y = 0)
        checkBulletCollisions();
        // Comprovar si s'han eliminat totes les boles (victòria)
        checkWinCondition();
    }

    /**
     * Comprova si s'han eliminat totes les boles
     */
    private void checkWinCondition() {
        if (listBalls.isEmpty() && !gameOver) {
            gameOver = true;
            showWinPopup();
        }
    }

    /**
     * Mostra un popup amb el missatge de victòria
     */
    private void showWinPopup() {
        post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Has guanyat!")
                       .setMessage("Has eliminat totes les boles!\nPuntuació: " + score)
                       .setPositiveButton("Reiniciar", (dialog, which) -> {
                           // Reiniciar el joc
                           listBalls.clear();
                           initEnvioroment(getContext());
                            // Iniciar nuevo hilo
                           ThreadGame thread = new ThreadGame(GameView.this);
                           thread.start();
                           // Invalidar la vista
                           invalidate();
                       })
                       .setNegativeButton("Sortir", (dialog, which) -> {
                            // Tancar l'aplicació
                            ((android.app.Activity) getContext()).finish();
                       })
                       .setCancelable(false)
                       .show();
            }
        });
    }

    private boolean gameOver = false;

    /**
     * Comprova si hi ha colisio entre la nau i les boles
     */
    public void collisionNauBall() {
        for (Ball b : listBalls) {
            if (nau.collisionBallAndNau(b, nau)) {
                gameOver = true;
                showPopup();
                break;
            }
        }
    }

    /**
     * Comprova si hi ha colisio entre les boles i les bales
     */
    private void checkBulletCollisions() {
        for (int i = bulletBalls.size() - 1; i >= 0; i--) {
            Ball bullet = bulletBalls.get(i);
            for (int j = listBalls.size() - 1; j >= 0; j--) {
                Ball randomBall = listBalls.get(j);

                if (bullet.collision(randomBall)) {
                    bulletBalls.remove(i);
                    listBalls.remove(j);
                    score++;
                    break;
                }
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Mostra un popup amb el missatge de game over
     */
    private void showPopup() {
        post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Game Over")
                       .setMessage("La nau ha xocat amb una bola!")
                       .setPositiveButton("Reiniciar", (dialog, which) -> {
                           // Reiniciar el joc
                           listBalls.clear();
                           initEnvioroment(getContext());
                           // Iniciar nuevo hilo
                           ThreadGame thread = new ThreadGame(GameView.this);
                           thread.start();
                           invalidate();
                       })
                       .setNegativeButton("Sortir", (dialog, which) -> {
                            // Tancar l'aplicació
                            ((android.app.Activity) getContext()).finish();
                       })
                       .setCancelable(false)
                       .show();
            }
        });
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Dibuixar totes les boles
        for (Ball b : listBalls) {
            b.onDraw(canvas);
        }

        // Dibuixar totes les bales
        for (Ball b : bulletBalls) {
            b.onDraw(canvas);
        }

        // Dibuixar la nau
        if (nau != null) {
            nau.dibuixaGrafic(canvas);
        }

        // Dibuixar la puntuació
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
        canvas.drawText("Score: " + score, 20, 60, textPaint);
    }
}
