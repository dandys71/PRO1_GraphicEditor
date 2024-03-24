import component.Circle;
import component.ComponentConst;
import component.Components;
import component.Primitive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class DrawingCanvas extends JComponent {
    private final int width;
    private final int height;

    private final Components components;

    private Primitive editedPrimitive;

    private ComponentConst.ComponentType type = ComponentConst.ComponentType.NONE;

    private int operation = 0; //0 = vkladani, 1 = rotace

    private double rotation;

    private double scale;

    public DrawingCanvas(int w, int h) {
        this.width = w;
        this.height = h;
        setDoubleBuffered(true);

        components = Components.getINSTANCE();

        MouseListener myMouseListener = new MouseListener();
        addMouseMotionListener(myMouseListener);
        addMouseListener(myMouseListener);

    }

        @Override
    protected void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g= (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform reset = g.getTransform();

        for(Primitive p : components.getComponents()){
            if(p instanceof Circle){
                g.translate(p.getX(), p.getY());
                //g.rotate(rotation);
                g.scale(scale, scale);

                g.drawRect(-p.getWidth() / 2, - p.getHeight() /2, p.getWidth(), p.getHeight());
            }
            g.setTransform(reset);
        }



        if(type == ComponentConst.ComponentType.CIRCLE){
            g.drawRect(editedPrimitive.getX() - editedPrimitive.getWidth() /2, editedPrimitive.getY() - editedPrimitive.getHeight() / 2, editedPrimitive.getWidth(), editedPrimitive.getHeight());
        }
    }

    private void clear(){
        Graphics2D g = (Graphics2D) getGraphics();
        g.clearRect(0, 0, width, height);
    }


    public void addComponent(ComponentConst.ComponentType type) {
        this.type = type;
    }

    private void reset(){
        //editedPrimitive = null;
        type = ComponentConst.ComponentType.NONE;
    }


    class MouseListener extends MouseAdapter{

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

            if(type != ComponentConst.ComponentType.NONE) {

                switch (type){
                    case CIRCLE -> {
                        editedPrimitive = new Circle();
                        editedPrimitive.setX(startX);
                        editedPrimitive.setY(startY);
                    }
                    case OVAL -> {}
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(type == ComponentConst.ComponentType.CIRCLE) {
                components.add(editedPrimitive);
            }
            reset();
            startX = -1;
            startY = -1;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            //euklidovská vzdálenost dvou bodů
            //vzdalenost = odmocina (x1 - x2)^2 + odmocnina (y1 - y2)^2
            if(SwingUtilities.isLeftMouseButton(e)) {
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

            if(SwingUtilities.isRightMouseButton(e)){
                //rotation
                int rectCenterX = editedPrimitive.getX() ;
                int rectCenterY = editedPrimitive. getY() ;
                double dx = e.getX() - rectCenterX;
                double dy = e.getY() - rectCenterY;
                rotation = Math.atan2(dy, dx);

                repaint();
            }
        }
    }
}
