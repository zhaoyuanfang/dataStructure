package avlTree;
import java.util.ArrayList;
/**
 * @Description
 * @auther zhaoyuanfang
 * @create 2020-03-01 9:55 下午
 */
public class AVLTree<K extends Comparable<K>, V> {
    private Node<K, V> root;
    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    public Node<K, V> getRoot() {
        return root;
    }

    public void setRoot(Node<K, V> root) {
        this.root = root;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int getHeight(Node<K, V> node) {
        return node == null ? 0 : node.getHeight();
    }

    private int getBalanceFactor(Node<K, V> node) {
        return node == null ? 0 : getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    private void inOrder(Node<K, V> node, ArrayList<K> keys){
        if (node == null) {
            return;
        }
        inOrder(node.getLeft(),keys);
        keys.add(node.getKey());
        inOrder(node.getRight(),keys);
    }

    private boolean isBinarySearchTree(Node<K, V> node) {
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isBalanced(){
        return isBalanced(root);
    }

    private boolean isBalanced(Node<K, V> node) {
        if (node == null) {
            return true;
        }
        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) {
            return false;
        }
        return isBalanced(node.getLeft()) && isBalanced(node.getRight());
    }

    private Node<K, V> rightRotate(Node<K, V> y) {
        Node<K, V> x = y.getLeft();
        Node<K, V> T3 = x.getRight();
        x.setRight(y);
        y.setLeft(T3);
        x.setHeight(Math.max(getHeight(x.getRight()), getHeight(x.getLeft())) + 1);
        y.setHeight(Math.max(getHeight(y.getRight()), getHeight(y.getLeft())) + 1);
        return x;
    }

    private Node<K, V> leftRotate(Node<K, V> y) {
        Node<K, V> x = y.getRight();
        Node<K, V> T2 = x.getLeft();
        x.setLeft(y);
        y.setRight(T2);
        x.setHeight(Math.max(getHeight(x.getRight()), getHeight(x.getLeft())) + 1);
        y.setHeight(Math.max(getHeight(y.getRight()), getHeight(y.getLeft())) + 1);
        return x;
    }


    public void add(K key, V value) {
        root = add(root,key,value);
    }

    private Node<K, V> add(Node<K, V> node, K key, V value){
        if(node == null){
            size ++;
            return new Node(key,value);
        }
        if(key.compareTo(node.getKey()) < 0){
            node.setLeft(add(node.getLeft(), key, value));
        }else if(key.compareTo(node.getKey()) > 0){
            node.setRight(add(node.getRight(), key, value));
        }else {
            node.setValue(value);
        }
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
        //计算平衡因子
        int balanceFactor = getBalanceFactor(node);
        //LL左孩子节点的左侧产生不平衡
        if(balanceFactor > 1 && getBalanceFactor(node.getLeft()) >= 0){
            //右旋转操作
            return rightRotate(node);
        }
        //RR右孩子节点的右侧产生不平衡
        if (balanceFactor < -1 && getBalanceFactor(node.getRight()) <= 0){
            //左旋转操作
            return leftRotate(node);
        }
        //LR左孩子节点的右侧产生不平衡
        if(balanceFactor > 1 && getBalanceFactor(node.getLeft()) < 0){
            node.setLeft(leftRotate(node.getLeft()));
            //右旋转操作
            return rightRotate(node);
        }
        //RL右孩子节点的左侧产生不平衡
        if(balanceFactor <-1 && getBalanceFactor(node.getRight()) > 0){
            node.setRight(rightRotate(node.getRight()));
            //右旋转操作
            return leftRotate(node);
        }
        return node;
    }

    public V minimum(){
        if(isEmpty()){
            throw new IllegalArgumentException("BinarySearchTree is empty !");
        }
        return minimum(root).getValue();
    }

    private Node<K, V> minimum(Node<K, V> node) {
        if(isEmpty()){
            throw new IllegalArgumentException("BinarySearchTree is empty !");
        }
        if(node.getLeft() == null){
            return node;
        }
        return minimum(node.getLeft());
    }

    public V maximize(){
        if(isEmpty()){
            throw new IllegalArgumentException("BinarySearchTree is empty !");
        }
        return maximize(root).getValue();
    }

    private Node<K, V> maximize(Node<K, V> node) {
        if(isEmpty()){
            throw new IllegalArgumentException("BinarySearchTree is empty !");
        }
        if(node.getRight() == null){
            return node;
        }
        return minimum(node.getRight());
    }

    public V removeMax(){
        V maximize = maximize();
        removeMax(root);
        return maximize;
    }

    private Node<K, V> removeMax(Node<K, V> node){
        if(node.getRight() == null){
            Node<K, V> leftNode = node.getLeft();
            node.setLeft(null);
            size --;
            return leftNode;
        }
        node.setRight(removeMin(node.getRight()));
        return node;
    }

    public V removeMin(){
        V minimum = minimum();
        removeMin(root);
        return minimum;
    }

    private Node<K, V> removeMin(Node<K, V> node){
        if(node.getLeft() == null){
            Node<K, V> rightNode = node.getRight();
            node.setRight(null);
            size --;
            return rightNode;
        }
        node.setLeft(removeMin(node.getLeft()));
        return node;
    }

    public V remove(K key) {
        Node<K, V> node = getNode(root,key);
        if(node != null){
            root = remove(root, key);
            return node.getValue();
        }
        return null;
    }

    private Node<K, V> remove(Node<K, V> node, K key) {
        if(node == null){
            return null;
        }

        Node<K, V> resultNode;

        if(key.compareTo(node.getKey()) < 0){
            node.setLeft(remove(node.getLeft(), key));
            resultNode = node;
        }else if(key.compareTo(node.getKey()) > 0){
            node.setRight(remove(node.getRight(), key));
            resultNode = node;
        }else /*if(key.compareTo(node.key) == 0)*/{
            // 删除右子树为空的情况
            if(node.getRight() == null){
                Node<K, V> leftNode = node.getLeft();
                node.setLeft(null);
                size --;
                resultNode = leftNode;
            }
            // 删除左子树为空的情况
            else if(node.getLeft() == null){
                Node<K, V> rightNode = node.getRight();
                node.setRight(null);
                size --;
                resultNode = rightNode;
            }
            // 删除左子树、右子树均不为空的情况
            else {
                // 1、删除后用后继节点替代该位置(后继节点即待删除节点右子树中的最小节点)
                // 获得后继节点
                Node<K, V> successor = minimum(node.getRight());
                // 删除后继节点，并让待删除节点的右子树成为后继节点的右子树
                successor.setRight(remove(node.getRight(),successor.getKey()));
                // 让待删除节点的左子树成为后继节点的左子树
                successor.setLeft(node.getLeft());
                // 将待删除节点的左、右子节点置为空
                node.setRight(null);
                node.setLeft(null);
                resultNode = successor;
            }
        }
        if(resultNode == null){
            return null;
        }

        //更新Height
        resultNode.setHeight(1 + Math.max(getHeight(resultNode.getLeft()), getHeight(resultNode.getRight())));
        //计算平衡因子
        int balanceFactor = getBalanceFactor(resultNode);
        //LL左孩子节点的左侧产生不平衡
        if(balanceFactor > 1 && getBalanceFactor(resultNode.getLeft()) >= 0){
            //右旋转操作
            return rightRotate(resultNode);
        }
        //RR右孩子节点的右侧产生不平衡
        if (balanceFactor < -1 && getBalanceFactor(resultNode.getRight()) <= 0){
            //左旋转操作
            return leftRotate(resultNode);
        }
        //LR左孩子节点的右侧产生不平衡
        if(balanceFactor > 1 && getBalanceFactor(resultNode.getLeft()) < 0){
            resultNode.setLeft(leftRotate(resultNode.getLeft()));
            //右旋转操作
            return rightRotate(resultNode);
        }
        //RL右孩子节点的左侧产生不平衡
        if(balanceFactor <-1 && getBalanceFactor(resultNode.getRight()) > 0){
            resultNode.setRight(rightRotate(resultNode.getRight()));
            //右旋转操作
            return leftRotate(resultNode);
        }
        return resultNode;
    }

    public boolean contains(K key) {
        return getNode(root,key) != null;
    }

    public V get(K key) {
        Node<K, V> node = getNode(root, key);
        return node != null ? node.getValue() : null;
    }

    public void set(K key, V value) {
        Node<K, V> node = getNode(root, key);
        if(node == null){
            throw new IllegalArgumentException("Set failed. key is not exists!");
        }
        node.setValue(value);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Node<K, V> getNode(Node<K, V> node,K key){
        if(node == null){
            return null;
        }

        if(key.compareTo(node.getKey()) == 0){
            return node;
        }else if(key.compareTo(node.getKey()) < 0){
            return getNode(node.getLeft(),key);
        }else{
            return getNode(node.getRight(),key);
        }
    }
}
