import component.Circle;
import component.ComponentConst;
import component.Components;
import component.Primitive;
import listener.ComponentChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;


public class DrawingCanvas extends JComponent {
    private final int width;
    private final int height;

    private boolean isEditing = false;

    private final ComponentChangeListener componentChangeListener;

    private final Components components;

    private Primitive editedPrimitive;

    private ComponentConst.ComponentType type = ComponentConst.ComponentType.NONE;

    private int index = -1;

    public DrawingCanvas(int w, int h, ComponentChangeListener componentChangeListener) {
        this.width = w;
        this.height = h;
        this.componentChangeListener = componentChangeListener;

        setDoubleBuffered(true);

        components = Components.getINSTANCE();

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

        for (int i = 0; i < components.getComponents().size(); i++) {
            Primitive p = components.getComponents().get(i);

            if (p instanceof Circle) { //todo nefunguje protože upravuju lokální koupii edited primitiv nikoliv primitu v seznamu
                g.translate(p.getX(), p.getY());
                g.rotate(p.getRotation());
                g.drawRect(-p.getWidth() / 2, -p.getHeight() / 2, p.getWidth(), p.getHeight());
                g.setTransform(reset);
            }

        }

        if (type == ComponentConst.ComponentType.CIRCLE) {
            g.drawRect(editedPrimitive.getX() - editedPrimitive.getWidth() / 2, editedPrimitive.getY() - editedPrimitive.getHeight() / 2, editedPrimitive.getWidth(), editedPrimitive.getHeight());
        }

    }

    private void clear() {
        Graphics2D g = (Graphics2D) getGraphics();
        g.clearRect(0, 0, width, height);
    }


    public void addComponent(ComponentConst.ComponentType type) {
        this.type = type;
    }

    private void reset() {
        //editedPrimitive = null;
        type = ComponentConst.ComponentType.NONE;
        isEditing = false;
        this.index = -1;
    }

    public void updateComponent(int index) {
        editedPrimitive = components.getComponents().get(index);
        type = editedPrimitive.getType();
        isEditing = true;
        this.index = index;
    }


    class MouseListener extends MouseAdapter {

        private int startX = -1;

        private int startY = -1;

        private int lastX = -1;

        private int lastY = -1;


        @Override
        public void mouseMoved(MouseEvent e) {

            super.mouseMoved(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            startX = e.getX();
            startY = e.getY();

            lastX = e.getX();
            lastY = e.getY();

            if (type != ComponentConst.ComponentType.NONE) {

                switch (type) {
                    case CIRCLE -> {
                        editedPrimitive = new Circle();
                        editedPrimitive.setX(startX);
                        editedPrimitive.setY(startY);
                    }
                    case OVAL -> {
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (type == ComponentConst.ComponentType.CIRCLE && !isEditing) {
                components.add(editedPrimitive);
            }
            componentChangeListener.onComponentsChange();
            reset();
            startX = -1;
            startY = -1;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            //euklidovská vzdálenost dvou bodů
            //vzdalenost = odmocina (x1 - x2)^2 + odmocnina (y1 - y2)^2
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (type != ComponentConst.ComponentType.NONE) {
                    if (type == ComponentConst.ComponentType.CIRCLE) {

                        int size = (int) Math.round(Math.sqrt(Math.pow(startX - e.getX(), 2) + Math.pow(startY - e.getY(), 2)));

                        editedPrimitive.setWidth(size);
                        editedPrimitive.setHeight(size);

                        editedPrimitive.setX(startX);
                        editedPrimitive.setY(startY);
                    }
                    repaint();
                }

            }

            if (SwingUtilities.isRightMouseButton(e)) {
                //rotation

                int rectCenterX = components.getComponents().get(index).getX();
                int rectCenterY = components.getComponents().get(index).getY();
                double dx = e.getX() - rectCenterX;
                double dy = e.getY() - rectCenterY;
                double rotation = Math.atan2(dy, dx);
                components.getComponents().get(index).setRotation(rotation);
                System.out.println(components.getComponents().get(index).getRotation());

                repaint();
            }


        }
    }
}
