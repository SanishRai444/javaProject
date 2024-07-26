import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    private CardLayout cardLayout;
    static JFrame frame;
    static JButton homeButton, historyButton, userButton;
    static JPanel mainPanel, navBar, HomeGUI, HistoryGUI, UserGUI;
    static Connection connection;

    public App() throws ClassNotFoundException, SQLException {
        // other classs instances here
        Customize customize = new Customize();
        HomePanel homePanel = new HomePanel();
        HistoryPanel historyPanel = new HistoryPanel();
        HomeGUI = homePanel.Interface();

        // frame description
        frame = new JFrame("APP");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Navbar
        navBar = new JPanel();
        navBar.setLayout(new BoxLayout(navBar, BoxLayout.Y_AXIS));
        navBar.setBackground(Color.WHITE);

        ImageIcon homeIcon = new ImageIcon(
                new ImageIcon("home.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        ImageIcon historyIcon = new ImageIcon(
                new ImageIcon("history.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        ImageIcon userIcon = new ImageIcon(
                new ImageIcon("user.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        homeButton = new JButton(homeIcon);
        customize.customizeButton(homeButton);
        historyButton = new JButton(historyIcon);
        customize.customizeButton(historyButton);
        userButton = new JButton(userIcon);
        customize.customizeButton(userButton);

        navBar.add(Box.createVerticalStrut(20));
        navBar.add(homeButton);
        navBar.add(Box.createVerticalStrut(20));
        navBar.add(historyButton);
        navBar.add(Box.createVerticalStrut(20));
        navBar.add(userButton);

        // database connection
        Connection newconnection = DatabaseConnection();
        HistoryGUI = historyPanel.Interface(newconnection);

        // MainPanel

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(HomeGUI, "home");
        mainPanel.add(HistoryGUI, "history");

        // ActionListeners
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "home");
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "history");
            }
        });

        // frame layout
        frame.setLayout(new BorderLayout());
        frame.add(navBar, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);

        // windowlisterner
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e1) {

                        e1.printStackTrace();
                    }
                    System.exit(0);
                }

            }
        }

        );

        frame.setVisible(true);

    }

    public Connection DatabaseConnection() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "root", "14159");
        System.out.println("Database connected! Ready to WORK...");
        return connection;

    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new App();
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}