import java.awt.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.*;

public class HomePanel {
    static JPanel homePanel, buttonPanel, searchPanel;
    private JButton EncryptionButton, DecryptionButton, SaveButton, BrowserButton, CloseButton;
    private JTextField FileText;
    private JTextPane TextArea;
    private JFileChooser fileChooser;
    ImageIcon browseIcon, encryptioIcon, decryptIcon, saveIcon, CloseIcon;

    public JPanel Interface() {
        Customize customize = new Customize();
        homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());
        buttonPanel = new JPanel();

        // icons
        browseIcon = new ImageIcon(
                new ImageIcon("find.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        encryptioIcon = new ImageIcon(
                new ImageIcon("encryption.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        decryptIcon = new ImageIcon(
                new ImageIcon("decryption.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        saveIcon = new ImageIcon(
                new ImageIcon("save.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        CloseIcon = new ImageIcon(
                new ImageIcon("close.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        // buttons
        EncryptionButton = new JButton(encryptioIcon);
        customize.customizeButton(EncryptionButton);
        DecryptionButton = new JButton(decryptIcon);
        customize.customizeButton(DecryptionButton);
        SaveButton = new JButton(saveIcon);
        customize.customizeButton(SaveButton);
        BrowserButton = new JButton(browseIcon);
        customize.customizeButton(BrowserButton);
        CloseButton = new JButton(CloseIcon);
        customize.customizeButton(CloseButton);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        Space();
        buttonPanel.add(EncryptionButton);
        Space();
        buttonPanel.add(DecryptionButton);
        Space();
        buttonPanel.add(SaveButton);
        Space();
        buttonPanel.add(CloseButton);

        // for button decriptions
        EncryptionButton.setToolTipText("Encrypts_File");
        DecryptionButton.setToolTipText("Decrypts_File");
        SaveButton.setToolTipText("Saves_File");
        CloseButton.setToolTipText("Closes_File");

        JLabel fielPath = new JLabel("FILE PATH:");
        FileText = new JTextField("file");
        FileText.setPreferredSize(new Dimension(200, 30));
        TextArea = new JTextPane();

        searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        searchPanel.add(fielPath);
        searchPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        searchPanel.add(FileText);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPanel.add(BrowserButton);

        BrowserButton.setToolTipText("Choose_File");

        homePanel.add(searchPanel, BorderLayout.NORTH);
        homePanel.add(buttonPanel, BorderLayout.EAST);
        homePanel.add(new JScrollPane(TextArea), BorderLayout.CENTER);

        // opening the filechooser

        BrowserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(new JFrame());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    FileText.setText(selectedFile.getAbsolutePath());
                    try {
                        readFileAndDisplay(selectedFile);
                    } catch (IOException er) {
                        er.printStackTrace();
                    }
                }
            }
        });

        return homePanel;
    }

    private void readFileAndDisplay(File file) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        TextArea.setText("");
        String line;
        while ((line = br.readLine()) != null) {
            TextArea.setText(TextArea.getText() + line + "\n");
        }
        br.close();

    }

    static void Space() {
        buttonPanel.add(Box.createVerticalStrut(20));
    }

}
