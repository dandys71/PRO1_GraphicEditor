
import component.ComponentConst;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

public class EditorWindow extends JFrame {

    private final MenuListener menuListener = new MenuListener();
    private final DrawingCanvas drawingCanvas;

    public EditorWindow(int w, int h) throws HeadlessException{
        setSize(w, h);
        setTitle("My Perfect Vector Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.drawingCanvas = new DrawingCanvas(w, h);

        setVisible(true);

        setLayout(new BorderLayout());

        add(drawingCanvas, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        setJMenuBar(menuBar);

        JMenu fileMenu  = new JMenu("File");

        JMenuItem fileFirst = new JMenuItem("First item");
        fileFirst.setActionCommand("FIRST_ITEM");
        fileFirst.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        fileFirst.addActionListener(menuListener);


        JMenuItem fileSecond = new JMenuItem("Second item");
        fileSecond.setActionCommand("SECOND_ITEM");
        fileSecond.addActionListener(menuListener);


        fileMenu.add(fileFirst);
        fileMenu.add(fileSecond);


        JMenu helpMenu = new JMenu("Help");
        helpMenu.setActionCommand("HELP");
        helpMenu.addActionListener(menuListener);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);


        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
        add(toolBar, BorderLayout.LINE_END);

        JLabel labInsertObjects = new JLabel("Insert Objects", JLabel.LEFT);
        toolBar.add(labInsertObjects);

        JPanel panObjects = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panObjects.setMaximumSize(new Dimension(w, 35));

        JButton btnAddCircle = new JButton("Circle");
        btnAddCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingCanvas.addComponent(ComponentConst.ComponentType.CIRCLE);
            }
        });
        panObjects.add(btnAddCircle);
        JButton btnAddOval = new JButton("Oval");
        panObjects.add(btnAddOval);

        JPanel panRgb = new JPanel(new FlowLayout());

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0);
        numberFormatter.setMaximum(255);

        JLabel labRed = new JLabel("R: ");
        JFormattedTextField txtRed = new JFormattedTextField(numberFormatter);
        txtRed.setText("0");
        txtRed.setColumns(3);
        panRgb.add(labRed);
        panRgb.add(txtRed);

        JLabel labGreen = new JLabel("G: ");
        JFormattedTextField txtGreen = new JFormattedTextField(numberFormatter);
        txtGreen.setText("0");
        txtGreen.setColumns(3);
        panRgb.add(labGreen);
        panRgb.add(txtGreen);

        JLabel labBlue = new JLabel("B: ");
        JFormattedTextField txtBlue = new JFormattedTextField(numberFormatter);
        txtBlue.setText("0");
        txtBlue.setColumns(3);
        panRgb.add(labBlue);
        panRgb.add(txtBlue);
        panRgb.setMaximumSize(new Dimension(w, 35));


        toolBar.add(panObjects);
        toolBar.add(panRgb);

    }


    class MenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "FIRST_ITEM" : {
                    System.out.println("FIRST");
                    break;
                }
                case "SECOND_ITEM" : {
                    System.out.println("SECOND");
                    break;
                }
            }
            System.out.println(e.getActionCommand());
        }
    }




}
