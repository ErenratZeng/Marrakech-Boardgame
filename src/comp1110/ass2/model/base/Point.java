package comp1110.ass2.model.base;

/**
 * Authorship:
 * name: Zhuiqi Lin
 * uid: u7733924
 */
public class Point {
    private int x;
    private int y;

    /**
     * @param x: An integer, representing the x-coordinate of the point.
     * @param y: An integer, representing the y-coordinate of the point.
     * @description  Used to create a new Point object with given coordinates x and y.
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * @param y: An integer, representing the y-coordinate of the point.
     * @return  Returns the Point object on which the method is called (supports chaining).
     * @description Used to set the y-coordinate of a Point object.
     */
    public Point setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * @return Returns an integer representing the y-coordinate of the point.
     * @description Retrieves the y-coordinate of a Point object.
     */
    public int getY() {
        return y;
    }

    /**
     * @param x: An integer, representing the x-coordinate of the point.
     * @return Returns the Point object on which the method is called (supports chaining).
     * @description Used to set the x-coordinate of a Point object.
     */
    public Point setX(int x) {
        this.x = x;
        return this;
    }

    /**
     * @return Returns an integer representing the x-coordinate of the point.
     * @description Retrieves the x-coordinate of a Point object.
     */
    public int getX() {
        return x;
    }

    /**
     * @return Returns a string representation of the Point object.
     * @description Provides a string representation of a Point object.
     */
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
