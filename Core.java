import java.awt.BorderLayout;
import java.io.*;
import javax.swing.*;

public class Core {

    // Encrypts or decrypts text using a key
    public String xorEncryptionDecryption(String content, String keyString) {
        StringBuilder result = new StringBuilder();
        int keyLength = keyString.length();

        for (int i = 0; i < content.length(); i++) {
            char contentChar = content.charAt(i);
            char keyChar = keyString.charAt(i % keyLength); // Reuse key cyclically
            // XOR operation
            char xorChar = (char) (contentChar ^ keyChar);
            result.append(xorChar);
        }
        return result.toString();
    }

    // Reads the content of the file as text
    public String readFileAsText(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString().trim();
    }

    // Writes the content to the file as text
    public void writeFileAsText(String filePath, String content) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
        }
    }

    // Reads the binary content of the file
    public byte[] readFileAsBinary(String filePath) throws IOException {
        try (InputStream is = new FileInputStream(filePath);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        }
    }

    // Writes binary content to the file
    public void writeFileAsBinary(String filePath, byte[] content) throws IOException {
        try (OutputStream os = new FileOutputStream(filePath)) {
            os.write(content);
        }
    }

    public static void main(String[] args) {
        try {
            Core core = new Core();
            String key = "KEY"; // Example key for encryption/decryption

            // Define file paths
            String originalFilePath = "D:\\Project\\test.txt";
            String encryptedFilePath = "D:\\Project\\testde.txt";
            String decryptedFilePath = "D:\\Project\\testdecrypted.txt";

            // Read the original content
            String originalContent = core.readFileAsText(originalFilePath);
            System.out.println("Original Content:\n" + originalContent);

            // Encrypt the content
            String encryptedContent = core.xorEncryptionDecryption(originalContent, key);
            System.out.println("Encrypted Content:\n" + encryptedContent);

            // Write the encrypted content as binary
            core.writeFileAsBinary(encryptedFilePath, encryptedContent.getBytes());
            System.out.println("Encrypted content written to file.");

            // Read the encrypted content from the file as binary
            byte[] encryptedContentFromFile = core.readFileAsBinary(encryptedFilePath);
            String encryptedContentStr = new String(encryptedContentFromFile);
            System.out.println("Encrypted Content From File:\n" + encryptedContentStr);

            // Decrypt the content
            String decryptedContent = core.xorEncryptionDecryption(encryptedContentStr, key);
            System.out.println("Decrypted Content:\n" + decryptedContent);

            // Write the decrypted content as text
            core.writeFileAsText(decryptedFilePath, decryptedContent);
            System.out.println("Decrypted content written to file.");

            // Verify if the decrypted content matches the original content
            String contentInDecryptedFile = core.readFileAsText(decryptedFilePath);
            System.out.println("Content In Decrypted File:\n" + contentInDecryptedFile);

            if (originalContent.equals(contentInDecryptedFile)) {
                System.out.println("Decryption successful: The decrypted content matches the original content.");
            } else {
                System.out.println("Decryption failed: The decrypted content does not match the original content.");
            }

            // Display content
            JFrame frame = new JFrame("Encryption/Decryption Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

            JTextPane originalText = new JTextPane();
            JTextPane encryptedText = new JTextPane();
            JTextPane decryptedText = new JTextPane();

            originalText.setText("Original Content:\n" + originalContent);
            originalText.setEditable(false);
            encryptedText.setText("Encrypted Content:\n" + encryptedContent);
            encryptedText.setEditable(false);
            decryptedText.setText("Decrypted Content:\n" + contentInDecryptedFile);
            decryptedText.setEditable(false);

            textPanel.add(new JScrollPane(originalText));
            textPanel.add(new JScrollPane(encryptedText));
            textPanel.add(new JScrollPane(decryptedText));

            frame.setSize(500, 600);
            frame.setLayout(new BorderLayout());
            frame.add(textPanel, BorderLayout.CENTER);

            frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
