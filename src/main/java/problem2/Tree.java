package problem2;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Scanner;

public class Tree extends JFrame {

    private JPanel treePanel;
    private JTree tree;
    private JButton addButton;
    private HashMap<String, ImageIcon> icon = new HashMap<>();
    private AddStudentDialog add = new AddStudentDialog();
    private DefaultMutableTreeNode root;
    private DefaultTreeModel model;
    private ImageIcon rootIcon, nodeIcon;

    public Tree() {
        super("Tree");

        setContentPane(treePanel);
        rootIcon = new ImageIcon(getClass().getResource("/students/world.png"));
        nodeIcon = new ImageIcon(getClass().getResource("/students/businessman.png"));
        root = new DefaultMutableTreeNode("ФПМИ");
        model = new DefaultTreeModel(root);
        tree.setModel(model);
        readStudents();
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if (node.equals(root)) {
                    setIcon(rootIcon);
                } else if (leaf) {
                    setIcon(icon.get(node.getUserObject()));
                } else {
                    setIcon(nodeIcon);
                }
                return this;
            }
        });
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultMutableTreeNode cur = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if (cur != null && cur.getChildCount() == 0) {
                        add.setFIO((String) cur.getUserObject());
                        add.setIcon(icon.get(cur.getUserObject()));
                        DefaultMutableTreeNode gr = (DefaultMutableTreeNode) cur.getParent();
                        add.setGroup((int) gr.getUserObject());
                        DefaultMutableTreeNode cr = (DefaultMutableTreeNode) gr.getParent();
                        add.setCourse((int) cr.getUserObject());
                        add.setVisible(true);
                        if (!add.isCancel()) {
                            removeStudent(cur);
                            icon.remove(cur.getUserObject());
                            icon.put(add.getFIO(), (ImageIcon) add.getIcon());
                            addStudent(add.getCourse(), add.getGroup(), add.getFIO());
                        }
                    }
                }
            }
        });
        tree.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_DELETE && tree.getSelectionCount() != 0) {
                    DefaultMutableTreeNode cur = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    removeStudent(cur);
                }
            }
        });
        addButton.addActionListener(e -> {
            add.setVisible(true);
            if (!add.isCancel()) {
                icon.put(add.getFIO(), (ImageIcon) add.getIcon());
                addStudent(add.getCourse(), add.getGroup(), add.getFIO());
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
    }

    private void readStudents() {
        Scanner in = new Scanner(getClass().getResourceAsStream("/students/students.txt"));
        int i = 1;
        while (in.hasNext()) {
            int course = in.nextInt();
            int group = in.nextInt();
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/students/" + i++ + ".jpg"));
            String student = in.nextLine();
            icon.put(student, imageIcon);
            addStudent(course, group, student);
        }
        in.close();
    }

    private void removeStudent(DefaultMutableTreeNode cur) {
        if (cur == null)
            return;
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) cur.getParent();
        if (parent == null)
            return;
        model.removeNodeFromParent(cur);
        cur = parent;
        while ((parent = (DefaultMutableTreeNode) cur.getParent()) != null && cur.getChildCount() == 0) {
            model.removeNodeFromParent(cur);
            cur = parent;
        }
    }

    private void addStudent(int course, int group, String student) {
        DefaultMutableTreeNode c = null;
        int i = 0;
        while (i < root.getChildCount()) {
            c = (DefaultMutableTreeNode) root.getChildAt(i);
            if ((int) c.getUserObject() == course)
                break;
            ++i;
        }
        if (i == root.getChildCount()) {
            c = new DefaultMutableTreeNode(course);
            model.insertNodeInto(c, root, root.getChildCount());
        }
        DefaultMutableTreeNode g = null;
        i = 0;
        while (i < c.getChildCount()) {
            g = (DefaultMutableTreeNode) c.getChildAt(i);
            if ((int) g.getUserObject() == group)
                break;
            ++i;
        }
        if (i == c.getChildCount()) {
            g = new DefaultMutableTreeNode(group);
            model.insertNodeInto(g, c, c.getChildCount());
        }
        model.insertNodeInto(new DefaultMutableTreeNode(student), g, g.getChildCount());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        new Tree().setVisible(true);
    }

}
