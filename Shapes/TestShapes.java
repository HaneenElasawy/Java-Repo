import java.util.ArrayList;
import java.util.List;

public class TestShapes {

    abstract static class Shape {
        public abstract void draw();
    }

    static class Rectangle extends Shape {

        private double width;
        private double height;

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

      
        public void draw() {
            System.out.println("Drawing Rectangle [width = " + width +
                               ", height = " + height + "]");
        }
    }

    static class Circle extends Shape {

        private double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        @Override
        public void draw() {
            System.out.println("Drawing Circle [radius = " + radius + "]");
        }
    }

    public static void drawAll(List<? extends Shape> shapes) {
        for (Shape s : shapes) {
            s.draw();
        }
    }

    public static void main(String[] args) {

        ArrayList<Rectangle> rectList = new ArrayList<>();
        rectList.add(new Rectangle(3, 4));
        rectList.add(new Rectangle(5, 6));

        ArrayList<Shape> shapeList = new ArrayList<>();
        shapeList.add(new Rectangle(7, 2));
        shapeList.add(new Circle(5));
        shapeList.add(new Circle(10));

        System.out.println("=== Drawing rectList ===");
        drawAll(rectList);

        System.out.println("\n=== Drawing shapeList ===");
        drawAll(shapeList);
    }
}
