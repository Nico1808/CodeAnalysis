import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Gui extends JFrame {

    Gui(String title) {
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

        // 3.1.1 Fill dropFilePanel with file chooser
        var fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        dropFilePanel.add(fileChooser);

        // 3.1.2 Fill topLeftFooterPanel with buttons
        /*
         * var saveButton = new JButton("Save");
         * var deleteButton = new JButton("Delete");
         * topLeftFooterPanel.add(saveButton);
         * topLeftFooterPanel.add(deleteButton);
         */

        // 3.1.3 Add content to topLeftPanel
        topLeftPanel.add(dropFilePanel, BorderLayout.NORTH);

        // 3.3 Add content to topPanel
        topPanel.add(topLeftPanel);

        // 4 Create panels for center panel
        var centerLeftPanel = new JPanel(new GridLayout(7, 1));
        var centerRightPanel = new JPanel(new GridLayout(2, 1));

        // 5.1 Fill center left panel with content
        centerLeftPanel.add(new JLabel("Search options"));

        JCheckBox checkBoxComments = new JCheckBox("Comments");
        centerLeftPanel.add(checkBoxComments);
        JCheckBox checkBoxLines = new JCheckBox("Lines");
        centerLeftPanel.add(checkBoxLines);
        JCheckBox checkBoxIf = new JCheckBox("If-Statements");
        centerLeftPanel.add(checkBoxIf);
        JCheckBox checkBoxFor = new JCheckBox("For-Loops");
        centerLeftPanel.add(checkBoxFor);
        JCheckBox checkBoxWhile = new JCheckBox("While-Loops");
        centerLeftPanel.add(checkBoxWhile);

        JButton ButtonRun = new JButton("Run");
        centerLeftPanel.add(ButtonRun);

        // 5.1.2 Fill centerRightPanel with content
        var languageLabel = new JLabel("Programming language:");
        var languageComboBox = new JComboBox<>(new String[] { "Java", "Python" });

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

        JTextArea results = new JTextArea(); // Erzeugen des Ergebnistextfeldes
        results.setEditable(false);
        ButtonRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Analyzer analyzer;
                if (languageComboBox.getSelectedItem().equals("Java")) {
                    analyzer = new Analyzer("sampleCode.java");
                } else {
                    analyzer = new Analyzer("SampleCode.py");
                }
                results.setText("");
                String language = languageComboBox.getSelectedItem().toString();

                if (checkBoxLines.isSelected()) {
                    ;
                    results.append("Number of Lines: " + (analyzer.resultMatcher(language + "Lines") + 1l) + " \n");
                }
                if (checkBoxComments.isSelected()) {
                    results.append(
                            "Number of Comments: " + (analyzer.resultMatcher(language + "Comments")+analyzer.resultMatcher(language+"MultiComments")) + "\n");
                }

                if (checkBoxIf.isSelected()) {
                    results.append(
                            "Number of if-Statements: " + analyzer.resultMatcher(language + "If") + "\n");
                }
                if (checkBoxFor.isSelected()) {
                    results.append(
                            "Number of for-Loops: " + analyzer.resultMatcher(language + "For") + "\n");
                    if (language == "Java") {
                        results.append("Number of for-each-loops: "
                                + analyzer.resultMatcher(language + "ForEach") + "\n");
                    }
                }
                if (checkBoxWhile.isSelected()) {
                    results.append(
                            "Number of while-Loops: " + analyzer.resultMatcher(language + "While") + "\n");
                    if (language == "Java"){
                        results.append("Number of do-while-loops: " + analyzer.resultMatcher(language+"DoWhile"));
                    }
                }
                bottomPanel.add(results);
                mainFrame.pack();
            }
        });

        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}