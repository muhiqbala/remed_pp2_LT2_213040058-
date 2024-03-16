import javax.swing.table.DefaultTableModel;

public class SmartTrashModel {
    private DefaultTableModel tableModel;

    public SmartTrashModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void insertData(String nama, String alamat, String telepon) {
        Object[] rowData = {nama, alamat, telepon};
        tableModel.addRow(rowData);
    }

    public void updateData(int row, String nama, String alamat, String telepon) {
        tableModel.setValueAt(nama, row, 0);
        tableModel.setValueAt(alamat, row, 1);
        tableModel.setValueAt(telepon, row, 2);
    }

    public void deleteData(int row) {
        tableModel.removeRow(row);
    }
}
