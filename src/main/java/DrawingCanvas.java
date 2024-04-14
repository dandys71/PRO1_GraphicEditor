import component.Circle;
import component.BaseComponent;
import listener.ComponentChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;


public class DrawingCanvas extends JComponent {
    private final int width;
    private final int height;

    private boolean bCreating = false;

    private final ComponentChangeListener componentChangeListener;

    private final ComponentList componentList;


    private int index = -1;

    public DrawingCanvas(int w, int h, ComponentChangeListener componentChangeListener) {
        this.width = w;
        this.height = h;
        setSize(width, height);
        this.componentChangeListener = componentChangeListener;

        setDoubleBuffered(true);

        componentList = ComponentList.getINSTANCE();

        MouseListener myMouseListener = new MouseListener();
        addMouseMotionListener(myMouseListener);
        addMouseListener(myMouseListener);

    }

    @Override
    protected void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform reset = g.getTransform();

        for (int i = 0; i < componentList.getComponents().size(); i++) {
            BaseComponent p = componentList.getComponents().get(i);

            if (p instanceof Circle) {
                g.setColor(p.getColor());
                g.translate(p.getX(), p.getY());
                g.rotate(p.getRotation());
                g.fillRect( - p.getWidth() / 2, -p.getHeight() / 2, p.getWidth(), p.getHeight());
                g.setTransform(reset);
            }

        }

    }

    public void addComponent(int index) {
        this.bCreating = true;
        this.index = index;
    }

    public void updateComponent(int index) {
        this.index = index;
    }


    class MouseListener extends MouseAdapter {

        private int startX = -1;

        private int startY = -1;


        @Override
        public void mouseMoved(MouseEvent e) {

            super.mouseMoved(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (bCreating) {
                startX = e.getX();
                startY = e.getY();
                BaseComponent p = componentList.getComponents().get(index);

                if(p instanceof Circle){
                    p.setX(startX);
                    p.setY(startY);

                    componentChangeListener.updateTableRow();

                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            componentChangeListener.onComponentsChange();
            bCreating = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            //euklidovská vzdálenost dvou bodů
            //vzdalenost = odmocina (x1 - x2)^2 + odmocnina (y1 - y2)^2
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (index != -1 && bCreating){
                    BaseComponent p = componentList.getComponents().get(index);

                    if (p instanceof Circle) {

                        int size = (int) Math.round(Math.sqrt(Math.pow(startX - e.getX(), 2) + Math.pow(startY - e.getY(), 2)));

                        p.setWidth(size);
                        p.setHeight(size);
                    }
                    repaint();
                    componentChangeListener.onComponentsChange();
                }

            }

            if (SwingUtilities.isRightMouseButton(e)) {
                //rotation
                if(index != -1) {
                    BaseComponent p = componentList.getComponents().get(index);

                    int rectCenterX = p.getX();
                    int rectCenterY = p.getY();
                    double dx = e.getX() - rectCenterX;
                    double dy = e.getY() - rectCenterY;
                    double rotation = Math.atan2(dy, dx);
                    p.setRotation(rotation);
                    repaint();
                }
            }

        }
    }
}
