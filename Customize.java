import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Customize {
    public void customizeButton(JButton button) {
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);

        button.addMouseListener(new MouseAdapter() {
            Color originalColor = button.getBackground();

            public void mouseEntered(MouseEvent e) {
                button.setOpaque(true);
                button.setBackground(Color.LIGHT_GRAY);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
                button.setOpaque(false);
            }

        });

    }
}
