
import component.Circle;
import component.ComponentConst;
import component.ComponentTableModel;
import component.Components;
import listener.ComponentChangeListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

public class EditorWindow extends JFrame implements ComponentChangeListener {

    private final DrawingCanvas drawingCanvas;

    private ComponentTableModel componentTableModel;

    private final JTable tableComponents;


    public EditorWindow(int w, int h) throws HeadlessException{
        setSize(w, h);
        setTitle("My Perfect Vector Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.drawingCanvas = new DrawingCanvas(w, h, this);

        setVisible(true);

        setLayout(new BorderLayout());

        add(drawingCanvas, BorderLayout.CENTER);


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


        Components c = Components.getINSTANCE();
        c.add(new Circle());

        componentTableModel = new ComponentTableModel();

        tableComponents = new JTable(componentTableModel);
        tableComponents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableComponents.setPreferredSize(new Dimension(150, 100));

        tableComponents.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                drawingCanvas.updateComponent(tableComponents.getSelectedRow());
            }
        });


        JScrollPane scrollTable = new JScrollPane(tableComponents);


        toolBar.add(scrollTable);
    }

    @Override
    public void onComponentsChange() {
        tableComponents.repaint();
    }
}
