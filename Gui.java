import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;

public class Gui extends JFrame {
    
    Gui(String title){
        super(title);
    }

    public static void main(String[] args) {
        // 1 Create main frame
        var mainFrame = new Gui("Code Analyzer");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 2 Create "main" panels (top, center, bottom)
        var topPanel = new JPanel(new FlowLayout());
        var centerPanel = new JPanel(new FlowLayout());
        var bottomPanel = new JPanel(new FlowLayout());

        // 3 Create panels for the top panel
        var topLeftPanel = new JPanel(new BorderLayout());

        // 3.1 Create panels for topLeftPanel 
        var dropFilePanel = new JPanel();
        var topLeftFooterPanel = new JPanel(new FlowLayout());

        // 3.1.1 Fill dropFilePanel with file chooser
        var fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        dropFilePanel.add(fileChooser);

        // 3.1.2 Fill topLeftFooterPanel with buttons
        /*var saveButton = new JButton("Save");
        var deleteButton = new JButton("Delete");
        topLeftFooterPanel.add(saveButton);
        topLeftFooterPanel.add(deleteButton);*/

        // 3.1.3 Add content to topLeftPanel
        topLeftPanel.add(dropFilePanel, BorderLayout.NORTH);
        
        // 3.3 Add content to topPanel
        topPanel.add(topLeftPanel);

        // 4 Create panels for center panel
        var centerLeftPanel = new JPanel(new GridLayout(7, 1));
        var centerRightPanel = new JPanel(new GridLayout(2, 1));

        // 5.1 Fill bottom left panel with content
        centerLeftPanel.add(new JLabel("Search options"));
        centerLeftPanel.add(new JRadioButton("Comments"));
        centerLeftPanel.add(new JRadioButton("Lines"));
        centerLeftPanel.add(new JRadioButton("if-statements"));
        centerLeftPanel.add(new JRadioButton("for-loops"));
        centerLeftPanel.add(new JRadioButton("while-loops"));
        centerLeftPanel.add(new JButton("Run"));

        // 5.1.2 Fill centerRightPanel with content
        var languageLabel = new JLabel("Programming language:");
        var languageComboBox = new JComboBox<>(new String[]{"Java", "Python"});

        // 3.2.2 Add content to topRightPanel
        centerRightPanel.add(languageLabel);
        centerRightPanel.add(languageComboBox);

        // 5.2 Add panels to center panel
        centerPanel.add(centerLeftPanel);
        centerPanel.add(centerRightPanel);

        // Add panels to mainFrame
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(centerPanel, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Set borders
        var etchedBorder = BorderFactory.createEtchedBorder();
        topPanel.setBorder(BorderFactory.createTitledBorder(etchedBorder, "Choose file"));
        centerPanel.setBorder(BorderFactory.createTitledBorder(etchedBorder, "Choose options:"));
        bottomPanel.setBorder(BorderFactory.createTitledBorder(etchedBorder, "Match results:"));

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

}
