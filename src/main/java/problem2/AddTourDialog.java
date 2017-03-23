package problem2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddTourDialog extends JDialog {
    private boolean cancel;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton selectFlagButton;
    private JTextField descriptionTextField;
    private JLabel flagLablel;
    private JSpinner priceSpinner;

    public AddTourDialog() {
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

        selectFlagButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(AddTourDialog.this) == JFileChooser.APPROVE_OPTION) {
                flagLablel.setIcon(new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath()));
            }
        });
        pack();
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

    public Icon getFlag() {
        return flagLablel.getIcon();
    }

    public String getDescription() {
        return descriptionTextField.getText();
    }

    public int getPrice() {
        return (int) priceSpinner.getValue();
    }

    private void createUIComponents() {
        priceSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 10));
    }

}
