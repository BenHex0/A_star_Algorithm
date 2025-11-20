import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        DemoPanel demoPanel = new DemoPanel();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(demoPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        demoPanel.requestFocusInWindow();
    }
}