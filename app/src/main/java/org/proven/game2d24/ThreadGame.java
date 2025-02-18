package org.proven.game2d24;

public class ThreadGame extends Thread {
    GameView gameView;

    public ThreadGame(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                sleep(200);
                gameView.move();
                gameView.collisions();
                gameView.postInvalidate();
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.toString());
        }
    }
}
