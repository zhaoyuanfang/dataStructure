package binarySearchTree;

/**
 * @Description
 * @auther zhaoyuanfang
 * @create 2020-03-01 5:43 下午
 */
public class BinarySearchTree {
    private TreeNode root;

    public BinarySearchTree(int[] input) {
        createBinarySearchTree(input);
    }

    public void createBinarySearchTree(int[] input) {
        if (input != null) {
            for (int i = 0; i < input.length; i++) {
                root = insert(input[i], root);
            }
        }
    }

    public TreeNode search(int target, TreeNode root) {
        TreeNode result = null;
        if (root != null) { // 递归终止条件
            if (target == root.getData()) { // 递归终止条件
                result = root;
                return result;
            } else if (target < root.getData()) { // 目标值小于根结点值，从左子树查找
                result = search(target, root.getLeft());
            } else { // 目标值大于根结点值，从右子树查找
                result = search(target, root.getRight());
            }
        }
        return result;
    }

    public TreeNode insert(int target, TreeNode node) {
        if (search(target, node) == null) {
            if (node == null) {
                return new TreeNode(target);
            } else {
                if (target < node.getData()) {
                    node.setLeft(insert(target, node.getLeft()));
                } else {
                    node.setRight(insert(target, node.getRight()));
                }
            }
        }
        return node;
    }

    public TreeNode remove(int target, TreeNode node) {
        TreeNode tmp = null;
        if (node != null) {
            if (target < node.getData()) { // 从左子树删除
                node.setLeft(remove(target, node.getLeft()));
            } else if (target > node.getData()) { // 从右子树删除
                node.setRight(remove(target, node.getRight()));
            } else if (node.getLeft() != null && node.getRight() != null) { // 找到待删除结点，且其左右子树不为空
                // 找到以待删除结点右子树的中序遍历第一个结点(最小结点)
                tmp = node.getRight();
                while (tmp.getLeft() != null) {
                    tmp = tmp.getLeft();
                }

                // 用最小结点补位待删除结点
                node.setData(tmp.getData());

                // 删除待删除结点右子树上补位结点
                node.setRight(remove(node.getData(), node.getRight()));
            } else {
                if (node.getLeft() == null) {
                    node = node.getRight();
                } else {
                    node = node.getLeft();
                }
            }
        }
        return node;
    }

    public void inOrder(TreeNode node) {
        if (node != null) {
            inOrder(node.getLeft());
            System.out.print(root.getData() + " ");
            inOrder(node.getRight());
        }
    }

    public void printTree(TreeNode node) {
        if (node != null) {
            System.out.print(node.getData());
            if (node.getLeft() != null || node.getRight() != null) {
                System.out.print("(");
                printTree(node.getLeft());
                System.out.print(",");
                printTree(node.getRight());
                System.out.print(")");
            }
        }
    }

    public TreeNode getRoot() {
        return root;
    }
}
