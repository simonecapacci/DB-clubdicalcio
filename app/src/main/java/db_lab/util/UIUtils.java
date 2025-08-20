package db_lab.util;

import java.awt.*;
import javax.swing.*;

public class UIUtils {
    public static JButton primary(String text){
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.PLAIN, 16));
        b.setBackground(Color.WHITE); b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setMaximumSize(new Dimension(220, 40));
        return b;
    }
}
