import javax.swing.*;

public class Main {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 800;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EditorWindow(WIDTH, HEIGHT)
        );
    }
}