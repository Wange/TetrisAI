package tetris;

import java.awt.*;
import java.util.Random;

/**
 * Daniel Sunnerberg, Lars Niklasson - grupp 60
 */
public class TetrisBlock {

    private boolean isFalling;
    private final Shape shape;
    private int[][] positions = new int[4][2];

    public int[][] getPositions() {
        return positions;
    }

    public Color getColor() {
        return shape.getColor();
    }

    public TetrisBlock(int[] startPos) {
        positions[0] = startPos;
        shape = Shape.getRandomShape();
        adjustPositions();
        isFalling = true;

    }

    public void adjustPositions() {
        for (int i = 1; i < 4; i++) {
            positions[i][0] = shape.getCoordinates()[i - 1][0] + positions[0][0];
            positions[i][1] = shape.getCoordinates()[i - 1][1] + positions[0][1];
        }
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setIsFalling(boolean b) {
        isFalling = b;
    }

    private enum Shape {


        L(new int[][]{{0, -1}, {0, 1}, {1, 1}}, Color.BLUE),
        S(new int[][]{{0, -1}, {1, 0}, {1, 1}}, Color.CYAN),
        RL(new int[][]{{0, -1}, {0, 1}, {-1, 1}}, Color.ORANGE),
        Z(new int[][]{{1, -1}, {1, 0}, {0, 1}}, Color.YELLOW),
        O(new int[][]{{1, 0}, {0, 1}, {1, 1}}, Color.GREEN),
        I(new int[][]{{-1, 0}, {1, 0}, {2, 0}}, Color.MAGENTA),
        T(new int[][]{{-1, 0}, {1, 0}, {0, 1}}, Color.RED);


        private int[][] coords;
        private final Color color;

        private Shape(int[][] coords, Color color) {
            this.coords = coords;
            this.color = color;
        }

        public int[][] getCoordinates() {
            return coords;
        }

        private static Shape getRandomShape() {
            int pick = new Random().nextInt(Shape.values().length);
            return Shape.values()[pick];
        }

        public Color getColor() {
            return color;
        }

    }

    public void rotate(Direction direction) {

        if (shape.equals(Shape.O)) {
            return;
        }

        int directionValue = (direction == Direction.CLOCKWISE) ? 1 : -1;
        for (int[] c : shape.coords) {
            int tmp = c[0];
            c[0] = c[1] * directionValue * -1;
            c[1] = tmp * directionValue;
        }
        adjustPositions();
    }

    public void move(int deltaX, int deltaY) {
        for (int i = 0; i < positions.length; i++) {
            positions[i][0] += deltaX;
            positions[i][1] += deltaY;
        }
    }


}
