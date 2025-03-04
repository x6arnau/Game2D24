package org.proven.game2d24;

/*
 * ThreadGame class
 * author: Arnau Nuñez
 * data: 04/03/2025
 * grup: DAM2
 */
public class ThreadGame extends Thread {
    GameView gameView;

    // Delay inicial
    private static final int INITIAL_DELAY = 2000;

    public ThreadGame(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void run() {
        super.run();
        try {
            sleep(INITIAL_DELAY);
            while (!gameView.isGameOver()) {
                sleep(20);
                gameView.move();
                gameView.moveNau();
                gameView.collisions();
                gameView.collisionNauBall();
                gameView.postInvalidate();
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.toString());
        }
    }
}
