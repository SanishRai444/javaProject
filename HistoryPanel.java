import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HistoryPanel {

    private JPanel historyPanel, DatePanel, DescriptionPanel;
    private JTextField FromDate, ToDate;
    private JButton icon, searchButton;
    private JLabel From, To, description;
    private DefaultTableModel tableModel;
    private JTable table;

    public JPanel Interface(Connection connection) throws SQLException {

        Customize customize = new Customize();
        historyPanel = new JPanel(new BorderLayout());
        DescriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        description = new JLabel("Description: Read only purpose");

        FromDate = new JTextField("yyyy-MM-dd");
        ToDate = new JTextField("yyyy-MM-dd");
        FromDate.setPreferredSize(new Dimension(100, 20));
        ToDate.setPreferredSize(new Dimension(100, 20));

        ImageIcon dateIcon = new ImageIcon(
                new ImageIcon("calendar.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        From = new JLabel("FROM:");
        To = new JLabel("TO:");

        icon = new JButton(dateIcon);
        customize.customizeButton(icon);
        searchButton = new JButton("Show");
        customize.customizeButton(searchButton);

        // Date_Panel
        DatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        Space();
        DatePanel.add(icon);
        Space();
        DatePanel.add(From);
        DatePanel.add(FromDate);
        Space();
        DatePanel.add(To);
        DatePanel.add(ToDate);
        Space();
        DatePanel.add(searchButton);

        // Initialize the table
        tableModel = new DefaultTableModel(new Object[] { "ID", "OriginalFile", "UpdatedFile", "Date", "Tasks" }, 0);
        table = new JTable(tableModel);

        // Display all data initially
        displayAllData(connection);

        // to clear textfiled when the user want to input the values
        FromDate.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                FromDate.setText(" ");
                ToDate.setText(" ");
            }

        });

        // ActionListeners
        icon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    displayAllData(connection);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    filterDataByDate(connection);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        DescriptionPanel.add(description);
        historyPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        historyPanel.add(DatePanel, BorderLayout.NORTH);
        historyPanel.add(DescriptionPanel, BorderLayout.SOUTH);

        return historyPanel;
    }

    private void Space() {
        DatePanel.add(Box.createRigidArea(new Dimension(15, 0)));
    }

    private void displayAllData(Connection con) throws SQLException {
        tableModel.setRowCount(0); // Clear existing data
        String query = "SELECT * FROM history";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            tableModel.addRow(new Object[] {
                    rs.getInt("Uid"),
                    rs.getString("OFileName"),
                    rs.getString("EFileName"),
                    rs.getDate("File_Date"),
                    rs.getString("Task")
            });
        }
    }

    private void filterDataByDate(Connection con) throws SQLException {
        tableModel.setRowCount(0); // Clear existing data

        String fromDate = FromDate.getText();
        String toDate = ToDate.getText();

        // Prepare query with date range
        String query = "SELECT * FROM history WHERE File_Date BETWEEN ? AND ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setDate(1, Date.valueOf(fromDate));
        preparedStatement.setDate(2, Date.valueOf(toDate));

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            tableModel.addRow(new Object[] {
                    rs.getInt("Uid"),
                    rs.getString("OFileName"),
                    rs.getString("EFileName"),
                    rs.getDate("File_Date"),
                    rs.getString("Task")
            });
        }
    }
}
