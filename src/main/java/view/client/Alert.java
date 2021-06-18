package view.client;

import javax.swing.*;

public class Alert {
    public static void show(JFrame jFrame, String error, String title) {
        JOptionPane.showMessageDialog(jFrame, error, title, JOptionPane.ERROR_MESSAGE);
    }
}
