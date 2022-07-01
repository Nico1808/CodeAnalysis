import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.*;

public class Gui extends JFrame {
    
    Gui(String title){
        super(title);
    }

    public static void main(String[] args) {
        // Create main frame
        var mainFrame = new Gui("Code Analyzer");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create panels
        var topPanel = new JPanel(new FlowLayout());
        var centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        var bottomPanel = new JPanel(new FlowLayout());

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

}
