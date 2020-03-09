package tree;
/**
 * @Description
 * @auther zhaoyuanfang
 * @create 2020-03-01 3:56 下午
 */
public class Node<T> {
    T data;
    Node<T> left;
    Node<T> right;
    boolean isFirst;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return getData().equals(node.getData());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return data == null? null : data.toString();
    }

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
}



