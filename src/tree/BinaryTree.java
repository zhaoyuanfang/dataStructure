package tree;

import java.util.LinkedList;

/**
 * @Description
 * @auther zhaoyuanfang
 * @create 2020-03-01 4:55 下午
 */
public class BinaryTree<E> {
    private Node<E> root;

    public BinaryTree() {
        super();
    }

    public BinaryTree(Node<E> root) {
        this.root = root;
    }

    public Node<E> getRoot() {
        return root;
    }

    public void setRoot(Node<E> root) {
        this.root = root;
    }

    public String preOrder(Node<E> root) {
        StringBuilder sb = new StringBuilder();
        if (root != null) {
            sb.append(root.data).append(" ");
            sb.append(preOrder(root.left));
            sb.append(preOrder(root.right));
        }
        return sb.toString();
    }

    public String preOreder() {
        StringBuilder sb = new StringBuilder();
        LinkedList<Node<E>> stack = new LinkedList<Node<E>>();
        Node<E> node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                sb.append(node.data + " ");
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                node = node.right;
            }
        }
        return sb.toString();
    }

    public String inOrder(Node<E> root){
        StringBuilder sb = new StringBuilder();
        if (root != null) {
            sb.append(inOrder(root.left));
            sb.append(root.data).append(" ");
            sb.append(inOrder(root.right));
        }
        return sb.toString();
    }

    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        LinkedList<Node<E>> stack = new LinkedList<Node<E>>();
        Node<E> node = root;
        while(node != null || !stack.isEmpty()) {
            if(node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                sb.append(node.data).append(" ");
                node = node.right;
            }
        }
        return sb.toString();
    }

    public String postOrder(Node<E> root) {
        StringBuilder sb = new StringBuilder();
        if (root != null) {
            sb.append(postOrder(root.left));
            sb.append(postOrder(root.right));
            sb.append(root.data).append(" ");
        }
        return sb.toString();
    }

    public String postOrder() {
        StringBuilder sb = new StringBuilder();
        LinkedList<Node<E>> stack = new LinkedList<Node<E>>();
        Node<E> node = root;
        while (node != null || !stack.isEmpty()) { // 迭代条件
            if (node != null) { // 当前节点不为空
                node.isFirst = true; // 首次访问该节点，记为true
                stack.push(node); // 压栈操作
                node = node.left; // 继续遍历左子树
            } else { // 当前节点为空但工作栈不为空
                node = stack.pop(); // 当前节点弹栈
                if (node.isFirst) {
                    node.isFirst = false; // 第二次访问该节点,改为false
                    stack.push(node); // 只有在第三次才访问，因此，前节点再次压栈
                    node = node.right; // 访问该节点的右子树
                } else { // 第三次访问该节点
                    sb.append(node.data + " "); // 访问
                    node = null; // 当前节点的左子树、右子树及本身均已访问,需要访问工作栈中的节点
                }
            }
        }
        return sb.toString();
    }

    public String levelOrder() {
        StringBuilder sb = new StringBuilder();
        LinkedList<Node<E>> queue = new LinkedList<Node<E>>(); // 辅助队列
        if (root != null) {
            queue.add(root);
            while (!queue.isEmpty()) {
                Node<E> temp = queue.pop();
                sb.append(temp.data).append(" ");

                // 在遍历当前节点时，同时将其左右孩子入队
                if (temp.left != null)
                    queue.add(temp.left);
                if (temp.right != null)
                    queue.add(temp.right);
            }
        }
        return sb.toString().trim();
    }
}
