package avltreedemo;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

public class AVLTreeDemo extends JFrame implements ActionListener {
    private AVLTree avlTree = new AVLTree();
    private JLabel cmdResultLabel;
    private JTextField cmdResultTextField;
    private JLabel cmdLabel;
    private JTextField cmdTextField;
    private JPanel view = null;

    public AVLTreeDemo() {
        setTitle("AVL Trees");

        // Set up the command result panel
        JPanel resultPanel = new JPanel(new GridLayout(1, 2));
        cmdResultLabel = new JLabel("Command Result: ");
        cmdResultTextField = new JTextField();
        resultPanel.add(cmdResultLabel);
        resultPanel.add(cmdResultTextField);
        cmdResultTextField.setEditable(false);
        add(resultPanel, BorderLayout.NORTH);

        // Set up the command input panel
        cmdLabel = new JLabel("Command: ");
        cmdTextField = new JTextField();
        JPanel cmdPanel = new JPanel(new GridLayout(1, 2));
        cmdPanel.add(cmdLabel);
        cmdPanel.add(cmdTextField);
        cmdTextField.addActionListener(this);
        add(cmdPanel, BorderLayout.SOUTH);

        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmdStr = cmdTextField.getText();
        Scanner sc = new Scanner(cmdStr);
        String cmd = sc.next();
        try {
            if (cmd.equals("add")) {
                int value = sc.nextInt();
                avlTree.add(value);
                cmdResultTextField.setText("Added " + value);
            } else if (cmd.equals("delete")) {
                int value = sc.nextInt();
                avlTree.delete(value);
                cmdResultTextField.setText("Deleted " + value);
            } else if (cmd.equals("preorder")) {
                List<Integer> traversal = new ArrayList<>();
                avlTree.preOrder(avlTree.getRoot(), traversal);
                cmdResultTextField.setText(traversal.toString());
            } else if (cmd.equals("inorder")) {
                List<Integer> traversal = new ArrayList<>();
                avlTree.inOrder(avlTree.getRoot(), traversal);
                cmdResultTextField.setText(traversal.toString());
            } else {
                cmdResultTextField.setText("Unknown command");
            }
        } catch (NoSuchElementException e) {
            cmdResultTextField.setText("Invalid command format");
        }
        updateView();
    }

    // Method to update the displayed view of the tree
    private void updateView() {
        if (view != null) {
            remove(view);
        }
        view = avlTree.getView();
        add(view, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AVLTreeDemo();
        });
    }
}