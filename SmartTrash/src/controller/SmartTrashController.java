import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;

public class SmartTrashController {
    private SmartTrashModel model;
    private SmartTrashView view;

    public SmartTrashController() {
        model = new SmartTrashModel();
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Nama");
        tableModel.addColumn("Alamat");
        tableModel.addColumn("No. Telepon");
        view = new SmartTrashView(tableModel);
    }

    public void startApplication() {
        // Button Input Data
        JButton btnInputData = new JButton("Input Data");
        btnInputData.setBounds(20, 300, 120, 30);
        btnInputData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputData();
            }
        });
        view.frame.add(btnInputData);

        // Button Ubah Data
        JButton btnUbahData = new JButton("Ubah Data");
        btnUbahData.setBounds(160, 300, 120, 30);
        btnUbahData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ubahData();
            }
        });
        view.frame.add(btnUbahData);

        // Button Hapus Data
        JButton btnHapusData = new JButton("Hapus Data");
        btnHapusData.setBounds(300, 300, 120, 30);
        btnHapusData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hapusData();
            }
        });
        view.frame.add(btnHapusData);

        // Button Export to PDF
        JButton btnExportPDF = new JButton("Export to PDF");
        btnExportPDF.setBounds(440, 300, 120, 30);
        btnExportPDF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportToPDF();
            }
        });
        view.frame.add(btnExportPDF);

        view.updateTable();
    }

    // Method untuk input data
    private void inputData() {
        String nama = JOptionPane.showInputDialog(view.frame, "Masukkan Nama:");
        String alamat = JOptionPane.showInputDialog(view.frame, "Masukkan Alamat:");
        String telepon = JOptionPane.showInputDialog(view.frame, "Masukkan No. Telepon:");

        if (nama != null && alamat != null && telepon != null && !nama.isEmpty() && !alamat.isEmpty() && !telepon.isEmpty()) {
            model.insertData(nama, alamat, telepon);
            Object[] rowData = {nama, alamat, telepon};
            view.model.addRow(rowData);
        } else {
            JOptionPane.showMessageDialog(view.frame, "Silakan lengkapi semua kolom.");
        }
    }

    // Method untuk ubah data
    private void ubahData() {
        int selectedRow = view.table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.frame, "Pilih data yang ingin diubah.");
            return;
        }

        String oldNama = (String) view.model.getValueAt(selectedRow, 0);
        String newNama = JOptionPane.showInputDialog(view.frame, "Masukkan Nama Baru:", oldNama);
        String alamat = JOptionPane.showInputDialog(view.frame, "Masukkan Alamat Baru:", view.model.getValueAt(selectedRow, 1));
        String telepon = JOptionPane.showInputDialog(view.frame, "Masukkan No. Telepon Baru:", view.model.getValueAt(selectedRow, 2));

        if (newNama != null && alamat != null && telepon != null && !newNama.isEmpty() && !alamat.isEmpty() && !telepon.isEmpty()) {
            model.updateData(oldNama, newNama, alamat, telepon);
            view.model.setValueAt(newNama, selectedRow, 0);
            view.model.setValueAt(alamat, selectedRow, 1);
            view.model.setValueAt(telepon, selectedRow, 2);
        } else {
            JOptionPane.showMessageDialog(view.frame, "Silakan lengkapi semua kolom.");
        }
    }

    // Method untuk hapus data
    private void hapusData() {
        int selectedRow = view.table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.frame, "Pilih data yang ingin dihapus.");
            return;
        }

        String nama = (String) view.model.getValueAt(selectedRow, 0);
        model.deleteData(nama);
        view.model.removeRow(selectedRow);
    }

    // Method untuk ekspor data ke PDF
    private void exportToPDF() {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream("data_trash.pdf"));
            document.open();

            PdfPTable pdfTable = new PdfPTable(view.model.getColumnCount());
            for (int i = 0; i < view.model.getColumnCount(); i++) {
                pdfTable.addCell(view.model.getColumnName(i));
            }
            for (int i = 0; i < view.model.getRowCount(); i++) {
                for (int j = 0; j < view.model.getColumnCount(); j++) {
                    pdfTable.addCell(view.model.getValueAt(i, j).toString());
                }
            }

            document.add(pdfTable);
            document.close();

            JOptionPane.showMessageDialog(view.frame, "Data berhasil diekspor ke PDF.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view.frame, "Gagal mengekspor data ke PDF.");
        }
    }
}
