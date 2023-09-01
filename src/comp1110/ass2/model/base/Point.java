package comp1110.ass2.model.base;

public class Point {
    private int x;
    private int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point setY(int y) {
        this.y = y;
        return this;
    }

    public int getY() {
        return y;
    }

    public Point setX(int x) {
        this.x = x;
        return this;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
