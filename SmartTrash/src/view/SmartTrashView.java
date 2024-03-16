import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SmartTrashView {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    public SmartTrashView(DefaultTableModel model) {
        this.model = model;

        frame = new JFrame("Aplikasi Smart Trash");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tabel
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 550, 250);
        frame.add(scrollPane);

        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void updateTable() {
        table.setModel(model);
    }
}
