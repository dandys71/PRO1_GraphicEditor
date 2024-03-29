package component;

public class Primitive {
    private int x;

    private int y;

    private int width;

    private int height;

    private String name;

    private double rotation;

    private ComponentConst.ComponentType type;

    public Primitive() {
    }

    public Primitive(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComponentConst.ComponentType getType() {
        return type;
    }

    protected void setType(ComponentConst.ComponentType type) {
        this.type = type;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
