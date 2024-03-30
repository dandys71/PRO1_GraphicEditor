
import component.Circle;
import component.ComponentList;
import listener.ComponentChangeListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

public class EditorWindow extends JFrame implements ComponentChangeListener {

    private final DrawingCanvas drawingCanvas;

    private ComponentTableModel componentTableModel;

    private final JTable tableComponents;

    private final ComponentList componentList;

    private final int TOOLBAR_WIDTH = 200;


    public EditorWindow(int w, int h) throws HeadlessException{
        setSize(w, h);
        setTitle("My Perfect Vector Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.drawingCanvas = new DrawingCanvas(w, h, this);
        this.componentList = ComponentList.getINSTANCE();

        setVisible(true);

        setLayout(new BorderLayout());


        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);

        add(toolBar, BorderLayout.LINE_END);

        add(drawingCanvas, BorderLayout.CENTER);


        JPanel panObjects = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panObjects.setMaximumSize(new Dimension(TOOLBAR_WIDTH, 35));

        JButton btnAddCircle = new JButton("Circle");

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


        panRgb.setMaximumSize(new Dimension(TOOLBAR_WIDTH, 35));


        toolBar.add(panObjects);
        toolBar.add(panRgb);


        componentTableModel = new ComponentTableModel(componentList);

        tableComponents = new JTable(componentTableModel);

        tableComponents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableComponents.setPreferredSize(new Dimension(200, 100));
        tableComponents.setPreferredScrollableViewportSize(tableComponents.getPreferredSize());


        tableComponents.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                drawingCanvas.updateComponent(tableComponents.getSelectedRow());
            }
        });

        JScrollPane scrollTable = new JScrollPane(tableComponents);

        scrollTable.setMaximumSize(new Dimension(TOOLBAR_WIDTH, 100));

        toolBar.add(scrollTable);

        btnAddCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Circle c = new Circle();

                c.setColor(new Color(Integer.parseInt(txtRed.getText()), Integer.parseInt(txtGreen.getText()), Integer.parseInt(txtBlue.getText()))); //todo try

                componentList.add(c);
                drawingCanvas.addComponent(componentList.getComponents().size() - 1);
            }
        });
    }

    @Override
    public void onComponentsChange() {
        tableComponents.repaint();
    }

    @Override
    public void updateTableRow() {
        tableComponents.changeSelection(componentTableModel.getRowCount()-1, 0, true, false);
    }
}
