import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {

    private String absoluteFilePath;

    Gui(String title) {
        super(title);
        absoluteFilePath = "";
    }

    public String getAbsoluteFilePath() {
        return absoluteFilePath;
    }

    public void setAbsoluteFilePath(String absoluteFilePath) {
        this.absoluteFilePath = absoluteFilePath;
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
        var selectFileButton = new JButton("Select a file!");
        var selectedFileLabel = new JLabel("Currently no file selected");
        var dropFilePanel = new JPanel();

        // 3.1.1 Fill dropFilePanel with file chooser
        var fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
        var pyJavaFilter = new FileNameExtensionFilter("Python and Java files", "py", "java");
        fileChooser.setFileFilter(pyJavaFilter);
        dropFilePanel.add(selectFileButton);
        dropFilePanel.add(selectedFileLabel);

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

        // Adding action listener to selectFileButton
        selectFileButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                var returnValue = fileChooser.showDialog(null, "Select");
                mainFrame.setAbsoluteFilePath("");
                switch (returnValue){
                    case JFileChooser.APPROVE_OPTION -> {
                        mainFrame.setAbsoluteFilePath(fileChooser.getSelectedFile().getAbsolutePath());
                        var index = fileChooser.getSelectedFile().toString().lastIndexOf(".");
                        switch (fileChooser.getSelectedFile().toString().substring(++index)){
                            case "java" -> languageComboBox.setSelectedItem("Java");
                            case "py" -> languageComboBox.setSelectedItem("Python");
                        }
                        selectedFileLabel.setText(fileChooser.getSelectedFile().getName());
                        mainFrame.pack();
                    }
                    case JFileChooser.CANCEL_OPTION -> mainFrame.setAbsoluteFilePath("");
                }
            }
            
        });

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
                if (mainFrame.getAbsoluteFilePath().isEmpty()){
                    return;
                }
                Analyzer analyzer = new Analyzer(mainFrame.getAbsoluteFilePath());
                results.setText("");
                String language = languageComboBox.getSelectedItem().toString();

                if (checkBoxLines.isSelected()) {
                    ;
                    results.append("Number of Lines: " + (analyzer.resultMatcher(language + "Lines") + 1l) + " \n");
                }
                if (checkBoxComments.isSelected()) {
                    results.append(
                        "Number of Comments: " + analyzer.resultMatcher(language + "Comment") + "\n");
                    results.append(
                        "Number of Multiline-Comments: " + analyzer.resultMatcher(language + "MultiComment") + "\n");
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