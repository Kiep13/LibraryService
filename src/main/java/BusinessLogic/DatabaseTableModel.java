package BusinessLogic;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> columnNames = new ArrayList<String>();
    private ArrayList<Class> columnTypes = new ArrayList<Class>();
    private ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
    private TableColumn idColumn = new TableColumn();

    public int getRowCount() {
        synchronized (data) {
            return data.size();
        }
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public Object getValueAt(int row, int col) {
        synchronized (data) {
            return data.get(row).get(col);
        }
    }

    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    public Class getColumnClass(int col) {
        return columnTypes.get(col);
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object obj, int row, int col) {
        synchronized (data) {
            data.get(row).set(col, obj);
        }
    }

    public void setDataSource(ResultSet rs, String[] headers) throws SQLException, ClassNotFoundException {
        ResultSetMetaData rsmd = rs.getMetaData();
        //columnNames.clear();
        columnTypes.clear();
        data.clear();

        int columnCount = rsmd.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            //columnNames.add(rsmd.getColumnName(i + 1));
            columnNames.add(headers[i]);
            Class type = Class.forName(rsmd.getColumnClassName(i + 1));
            columnTypes.add(type);
        }
        fireTableStructureChanged();
        while (rs.next()) {
            ArrayList rowData = new ArrayList();
            for (int i = 0; i < columnCount; i++) {
                if (columnTypes.get(i) == String.class)
                    rowData.add(rs.getString(i + 1));
                else
                    rowData.add(rs.getObject(i + 1));
            }
            synchronized (data) {
                data.add(rowData);
                this.fireTableRowsInserted(data.size(), data.size());
            }
        }
    }
}