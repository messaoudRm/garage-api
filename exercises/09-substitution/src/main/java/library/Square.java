package library;

public class Square extends Rectangle {

    public Square(int side) {
        this.width = side;
        this.height = side;
    }

    @Override
    public void setWidth(int side) {
        this.width = side;
        this.height = side;
    }

    @Override
    public void setHeight(int side) {
        this.width = side;
        this.height = side;
    }
}
