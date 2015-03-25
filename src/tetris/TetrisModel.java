package tetris;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Daniel Sunnerberg, Lars Niklasson - grupp 60
 */
public class TetrisModel extends GameModel {

    /**
     * Lista över alla highscores. Återställs givetvis vid omstart av programmet.
     */
    private static final List<Integer> highscores = new ArrayList<Integer>();

    /**
     * Spelarens nuvarande poäng
     */
    private int score = 0;

    /**
     * Hur ofta blocket ska flyttas neråt. Minskar ju mer poäng spelaren får.
     */
    private int dropDownDelay = 50;

    /**
     * Håller koll på om det är dags för blocket att flyttas neråt,
     * vilket är när den når 0.
     */
    private int dropDownCounter;


    private static final GameTile BLANK_TILE = new GameTile();

    private int startX;
    private int startY;

    /**
     * Blockens förra position.
     */
    private int[][] lastPos;

    /**
     * Blocket som för tillfället faller
     */
    private TetrisBlock tp;

    private Dimension size = getGameboardSize();

    public TetrisModel() {
        startX = size.width / 2;
        startY = 2;
        tp = new TetrisBlock(new int[]{startX, startY});

        lastPos = new int[tp.getPositions().length][tp.getPositions()[1].length];
        clearBoard();
    }


    /**
     * Rensar spelplanen från alla block.
     */
    private void clearBoard() {
        for (int i = 0; i < size.width; i++) {
            for (int j = 0; j < size.height; j++) {
                setGameboardState(i, j, BLANK_TILE);
            }
        }

    }

    /**
     * Tar bort ev. rader av block om dessa täcker hela spelplanens
     * bredd och ökar sedan spelarens poäng. Poängen ökar exponentiellt
     * med hur många rader som tas bort SAMTIDIGT.
     */
    private void eraseRows() {
        int counter = 0;
        boolean erase;
        for (int y = 0; y < size.height; y++) {
            erase = true;
            for (int x = 0; x < size.width; x++) {
                if (!(getGameboardState(x, y) instanceof TetrisTile)) {
                    erase = false;
                    break;
                }
            }

            if (erase) {
                // all the tiles in row were TetrisTiles
                for (int i = y; i > 0; i--) {

                    // each row is set to the row above
                    for (int w = 0; w < size.getWidth(); w++) {
                        setGameboardState(w, i, getGameboardState(w, i - 1));
                    }
                }
                // row 0 is all set to blank tiles
                for (int w = 0; w < size.getWidth(); w++) {
                    setGameboardState(w, 0, BLANK_TILE);
                }
                // Öka spelets hastighet
                dropDownDelay -= 2;
                counter++;
            }

        }

        // Poängen ökar exponentiellt
        score += Math.pow(counter, 2) * 10;
        System.out.println("Score : " + score);
    }

    /**
     * Flyttar blocket utefter vilken knapp användaren tryckte.
     *
     * @param lastKey Tryckt knapp
     */
    private void movePiece(int lastKey) {

        switch (lastKey) {
            case KeyEvent.VK_DOWN:
                moveToBottom();
                break;
            case KeyEvent.VK_LEFT:
                moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                moveRight();
                break;
            case KeyEvent.VK_UP:
                rotate(Direction.CLOCKWISE);
                break;

            case KeyEvent.VK_C:
                rotate(Direction.COUNTERCLOCKWISE);
                break;

            case KeyEvent.VK_SPACE:
                moveDown();
                break;
        }


    }

    /**
     * Flyttar ner blocket så långt som möjligt.
     */
    private void moveToBottom() {
        do {
            moveDown();
        } while (tp.isFalling());

    }

    private void copyPos() {
        Tools.copy2DArray(tp.getPositions(), lastPos);
    }

    private void fallBack() {
        Tools.copy2DArray(lastPos, tp.getPositions());
    }

    /**
     * Roterar blocket.
     *
     * @param direction Vilket håll blocket ska roteras.
     */
    private void rotate(Direction direction) {
        copyPos();
        tp.rotate(direction);
        if (hasCollided()) {
            fallBack();
        }

    }


    /**
     * Kontrollerar huruvida blocket har kolliderat.
     *
     * @return Huruvida blocket har kolliderat.
     */
    private boolean hasCollided() {
        int x, y;
        for (int[] pos : tp.getPositions()) {
            x = pos[0];
            y = pos[1];
            if (x < 0 || x >= size.width || y < 0 || y >= size.height
                    || getGameboardState(x, y) instanceof TetrisTile) {
                return true;
            }
        }
        return false;
    }

    private void moveRight() {
        copyPos();
        tp.move(1, 0);
        if (hasCollided()) {
            fallBack();
        }
    }

    private void moveLeft() {
        copyPos();
        tp.move(-1, 0);
        if (hasCollided()) {
            fallBack();
        }
    }

    private void moveDown() {
        copyPos();
        tp.move(0, 1);
        if (hasCollided()) {
            fallBack();
            tp.setIsFalling(false);
        }
    }

    @Override
    public void gameUpdate(int lastKey) throws GameOverException {
        blankPiece();
        movePiece(lastKey);
        gravity();
        updateBoard();
        if (!tp.isFalling()) {
            eraseRows();
            newPiece();
            updateBoard();
        }

    }


    /**
     * Flyttar ner blocket ett steg om det är dags.
     *
     * @see TetrisModel#dropDownDelay
     */
    private void gravity() {
        dropDownCounter = (dropDownCounter + 1) % dropDownDelay;
        if (dropDownCounter == 0) {
            moveDown();
        }

    }

    /**
     * Skapar ett nytt block och placerar det på spelplanen.
     *
     * @throws GameOverException Om det inte finns plats för mer block.
     */
    private void newPiece() throws GameOverException {
        dropDownCounter = 0;
        if (getGameboardState(startX, startY) instanceof TetrisTile) {

            highscores.add(score);
            Collections.sort(highscores);
            Collections.reverse(highscores);
            System.out.println("highscores: " + highscores.subList(0, Math.min(10, highscores.size())));
            throw new GameOverException(score);
        }

        tp = new TetrisBlock(new int[]{startX, startY});
    }

    private void blankPiece() {
        for (int[] pos : tp.getPositions()) {
            setGameboardState(pos[0], pos[1], BLANK_TILE);
        }

    }

    private void updateBoard() {
        for (int[] pos : tp.getPositions()) {
            setGameboardState(pos[0], pos[1], new TetrisTile(tp.getColor()));
        }

    }

}
