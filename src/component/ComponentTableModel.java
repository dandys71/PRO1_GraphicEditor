package component;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class ComponentTableModel extends AbstractTableModel {

    private Components components;

    public ComponentTableModel() {
        components = Components.getINSTANCE();

    }

    @Override
    public int getRowCount() {
        return components.getComponents().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0:{
                return "Name";
            }
            case 1: {
                return "Width";
            }
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: {
                return components.getComponents().get(rowIndex).getName();
            }
            case 1: {
                return components.getComponents().get(rowIndex).getWidth();
            }
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
