package redBlackTree;

import java.rmi.NotBoundException;

/**
 * @Description
 * @auther zhaoyuanfang
 * @create 2020-03-04 9:45 上午
 */
public class RedBlackTree<K extends Comparable<K>, V> {
    private Node<K, V> root;
    private int size;

    public RedBlackTree() {
        root = null;
        size = 0;
    }

    private Node<K, V> leftRotate(Node<K, V> node) {
        Node<K, V> x = node.getLeft();
        node.setRight(x.getLeft());
        x.setLeft(node);
        x.setColor(node.getColor());
        node.setColor(Node.RED);
        return x;
    }

    private Node<K, V> rightRotate(Node<K, V> node) {
        Node<K, V> x = node.getRight();
        node.setLeft(x.getRight());
        x.setRight(node);
        x.setColor(node.getColor());
        node.setColor(Node.RED);
        return x;
    }

    private void flipColor(Node<K, V> node) {
        node.setColor(Node.RED);
        node.getLeft().setColor(Node.BLACK);
        node.getRight().setColor(Node.BLACK);
    }

    public boolean isRed(Node<K, V> node) {
        if(node == null){
            return Node.BLACK;
        }
        return node.getColor();
    }

    public void add(K key, V value) {
        root = add(root, key, value);
        root.setColor(Node.BLACK);
    }

    private Node<K, V> add(Node<K, V> node, K key, V value) {
        if (node == null) {
            size ++;
            return new Node(key, value);
        }
        if(key.compareTo(node.getKey()) < 0){
            node.setLeft(add(node.getLeft(), key, value));
        }else if(key.compareTo(node.getKey()) > 0){
            node.setRight(add(node.getRight(),key,value));
        }else {
            node.setValue(value);
        }
        //判断是否需要左旋转
        if(isRed(node.getRight()) && !isRed(node.getLeft())){
            node = leftRotate(node);
        }

        //判断是否需要右旋转
        if(isRed(node.getLeft()) && isRed(node.getLeft().getLeft())){
            node = rightRotate(node);
        }

        //判断是否需要颜色翻转
        if(isRed(node.getLeft()) && isRed(node.getRight())){
            flipColor(node);
        }
        //==========维护红黑树性质 End==========*/

        return node;
    }

    public boolean isEmpty() {
        return size == 0;
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
        if(key.compareTo(node.getKey()) < 0){
            node.setLeft(remove(node.getLeft(), key));
            return node;
        }else if(key.compareTo(node.getKey()) > 0){
            node.setRight(remove(node.getRight(), key));
            return node;
        }else /*if(key.compareTo(node.key) == 0)*/{
            // 删除右子树为空的情况
            if(node.getRight() == null){
                Node<K, V> leftNode = node.getLeft();
                node.setLeft(null);
                size --;
                return leftNode;
            }
            // 删除左子树为空的情况
            else if(node.getLeft() == null){
                Node<K, V> rightNode = node.getRight();
                node.setRight(null);
                size --;
                return rightNode;
            }
            // 删除左子树、右子树均不为空的情况
            else {
                // 1、删除后用后继节点替代该位置(后继节点即待删除节点右子树中的最小节点)
                // 获得后继节点
                Node<K, V> successor = minimum(node.getRight());
                // 删除后继节点，并让待删除节点的右子树成为后继节点的右子树
                successor.setRight(removeMin(node));
                // 让待删除节点的左子树成为后继节点的左子树
                successor.setLeft(node.getLeft());
                // 将待删除节点的左、右子节点置为空
                node.setRight(null);
                node.setLeft(node.getRight());
                return successor;
            }
        }
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

    public Node<K, V> getNode(Node<K, V> node,K key){
        if(node == null){
            return null;
        }

        if(key.compareTo(node.getKey()) == 0){
            return node;
        }else if(key.compareTo(node.getKey()) < 0){
            return getNode(node.getLeft(), key);
        }else{
            return getNode(node.getRight(), key);
        }
    }

}
