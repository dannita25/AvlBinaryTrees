package avltreedemo;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AVLTree {
    // Node class for AVL tree
    private class AVLNode {
        int value;
        AVLNode left, right;
        int height;

        // Constructor for AVLNode
        public AVLNode(int value) {
            this(value, null, null);
        }

        // Constructor for AVLNode with children
        public AVLNode(int val, AVLNode left1, AVLNode right1) {
            value = val;
            left = left1;
            right = right1;
            height = 0;
        }

        // Method to reset the height of the node
        void resetHeight() {
            int leftHeight = AVLTree.getHeight(left);
            int rightHeight = AVLTree.getHeight(right);
            height = 1 + Math.max(leftHeight, rightHeight);
        }
    }

    // Panel class to display the tree
    private class BTreeDisplay extends JPanel {
        BTreeDisplay(AVLNode tree) {
            setBorder(BorderFactory.createEtchedBorder());
            setLayout(new BorderLayout());
            if (tree != null) {
                String value = String.valueOf(tree.value);
                int pos = SwingConstants.CENTER;
                JLabel rootLabel = new JLabel(value, pos);
                add(rootLabel, BorderLayout.NORTH);
                JPanel panel = new JPanel(new GridLayout(1, 2));
                panel.add(new BTreeDisplay(tree.left));
                panel.add(new BTreeDisplay(tree.right));
                add(panel);
            }
        }
    }

    // Static method to get the height of a node
    static int getHeight(AVLNode tree) {
        if (tree == null) return -1;
        else return tree.height;
    }

    // Public method to add a value to the AVL tree
    public boolean add(int x) {
        root = add(root, x);
        return true;
    }

    // Public method to delete a value from the AVL tree
    public boolean delete(int x) {
        root = delete(root, x);
        return true;
    }

    // Method to get the graphical view of the tree
    public JPanel getView() {
        return new BTreeDisplay(root);
    }

    // Method to check if the tree is empty
    public boolean isEmpty() {
        return root == null;
    }

    private AVLNode root = null;

    // Private method to add a value to the tree recursively
    private AVLNode add(AVLNode bTree, int x) {
        if (bTree == null)
            return new AVLNode(x);
        if (x < bTree.value)
            bTree.left = add(bTree.left, x);
        else
            bTree.right = add(bTree.right, x);

        bTree.resetHeight();
        return balance(bTree);
    }

    // Private method to delete a value from the tree recursively
    private AVLNode delete(AVLNode bTree, int x) {
        if (bTree == null) {
            return null;
        }
        if (x < bTree.value) {
            bTree.left = delete(bTree.left, x);
        } else if (x > bTree.value) {
            bTree.right = delete(bTree.right, x);
        } else {
            if (bTree.left == null || bTree.right == null) {
                bTree = (bTree.left != null) ? bTree.left : bTree.right;
            } else {
                AVLNode mostLeftChild = mostLeftChild(bTree.right);
                bTree.value = mostLeftChild.value;
                bTree.right = delete(bTree.right, bTree.value);
            }
        }

        if (bTree != null) {
            bTree.resetHeight();
            bTree = balance(bTree);
        }
        return bTree;
    }

    // Method to find the most left child of a node
    private AVLNode mostLeftChild(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Method to balance the tree
    private AVLNode balance(AVLNode bTree) {
        if (bTree == null) return null;

        int rHeight = getHeight(bTree.right);
        int lHeight = getHeight(bTree.left);

        if (rHeight - lHeight > 1) {
            if (getHeight(bTree.right.right) > getHeight(bTree.right.left)) {
                bTree = rrBalance(bTree);
            } else {
                bTree = rlBalance(bTree);
            }
        } else if (lHeight - rHeight > 1) {
            if (getHeight(bTree.left.left) > getHeight(bTree.left.right)) {
                bTree = llBalance(bTree);
            } else {
                bTree = lrBalance(bTree);
            }
        }

        return bTree;
    }

    // Right-right rotation
    private AVLNode rrBalance(AVLNode bTree) {
        AVLNode rightChild = bTree.right;
        bTree.right = rightChild.left;
        rightChild.left = bTree;
        bTree.resetHeight();
        rightChild.resetHeight();
        return rightChild;
    }

    // Right-left rotation
    private AVLNode rlBalance(AVLNode bTree) {
        bTree.right = llBalance(bTree.right);
        return rrBalance(bTree);
    }

    // Left-left rotation
    private AVLNode llBalance(AVLNode bTree) {
        AVLNode leftChild = bTree.left;
        bTree.left = leftChild.right;
        leftChild.right = bTree;
        bTree.resetHeight();
        leftChild.resetHeight();
        return leftChild;
    }

    // Left-right rotation
    private AVLNode lrBalance(AVLNode bTree) {
        bTree.left = rrBalance(bTree.left);
        return llBalance(bTree);
    }

    // Preorder traversal
    public void preOrder(AVLNode node, List<Integer> traversal) {
        if (node != null) {
            traversal.add(node.value);
            preOrder(node.left, traversal);
            preOrder(node.right, traversal);
        }
    }

    // Inorder traversal
    public void inOrder(AVLNode node, List<Integer> traversal) {
        if (node != null) {
            inOrder(node.left, traversal);
            traversal.add(node.value);
            inOrder(node.right, traversal);
        }
    }

    // Method to get the root of the tree
    public AVLNode getRoot() {
        return root;
    }

    // Method to display the tree in a JFrame
    public void displayTree() {
        JFrame frame = new JFrame("AVL Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(getView());
        frame.pack();
        frame.setVisible(true);
    }
}