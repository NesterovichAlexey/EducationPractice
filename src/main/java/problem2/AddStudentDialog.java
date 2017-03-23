package problem2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddStudentDialog extends JDialog {
    private boolean cancel;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel iconLabel;
    private JButton selectIconButton;
    private JSpinner groupSpinner;
    private JSpinner courseSpinner;
    private JTextField FIOTextField;

    public AddStudentDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        selectIconButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(AddStudentDialog.this) == JFileChooser.APPROVE_OPTION) {
                iconLabel.setIcon(new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath()));
            }
        });

        setSize(300, 350);
    }

    private void onOK() {
        cancel = false;
        dispose();
    }

    private void onCancel() {
        cancel = true;
        dispose();
    }

    public boolean isCancel() {
        return cancel;
    }

    public Icon getIcon() {
        return iconLabel.getIcon();
    }

    public int getCourse() {
        return (int) courseSpinner.getValue();
    }

    public int getGroup() {
        return (int) groupSpinner.getValue();
    }

    public String getFIO() {
        return FIOTextField.getText();
    }

    public void setIcon(Icon icon) {
        iconLabel.setIcon(icon);
    }

    public void setCourse(int course) {
        courseSpinner.setValue(course);
    }

    public void setGroup(int group) {
        groupSpinner.setValue(group);
    }

    public void setFIO(String fio) {
        FIOTextField.setText(fio);
    }

    private void createUIComponents() {
        courseSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        groupSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
    }

}
