package heap;

/**
 * @Description
 * @auther zhaoyuanfang
 * @create 2020-03-01 5:37 下午
 */
public class MinHeap {

    private int[] heap;  // 将所有元素以完全二叉树的形式存入数组
    private int size;  // 堆中元素的个数

    public MinHeap(int maxSize) {
        heap = new int[maxSize];
    }

    public MinHeap(int[] arr, int maxSize) {
        heap = new int[Math.max(maxSize, arr.length)];
        System.arraycopy(arr, 0, heap, 0, arr.length);
        size = arr.length;

        int pos = (size - 2) / 2; // 最初调整位置：最后的分支节点(最后叶节点的父亲)
        while (pos >= 0) {    //依次调整每个分支节点
            shiftDown(pos, size - 1);
            pos--;
        }
    }

    private void shiftDown(int start, int end) {
        int i = start;       // 起始调整位置，分支节点
        int j = 2 * start + 1;  // 该分支节点的子节点
        int temp = heap[i];
        while (j <= end) {  // 迭代条件：子节点不能超出end(范围)
            if (j < end) {
                j = heap[j] > heap[j + 1] ? j + 1 : j; // 选择两孩子中较小的那个
            }
            if (temp < heap[j]) {   // 较小的孩子大于父亲，不做任何处理
                break;
            } else {    // 否则，替换父节点的值
                heap[i] = heap[j];
                i = j;
                j = 2 * j + 1;
            }
        }
        heap[i] = temp;  // 一步到位
    }

    private void shiftUp(int start) {
        int j = start;
        int i = (j - 1) / 2;   // 起始调整位置，分支节点
        int temp = heap[j];
        while (j > 0) {      // 迭代条件：子节点必须不为根
            if (temp >= heap[i]) {  //原已是最小堆，所以只需比较这个子女与父亲的关系即可
                break;
            } else {
                heap[j] = heap[i];
                j = i;
                i = (j - 1) / 2;
            }
        }
        heap[j] = temp;   // 一步到位
    }

    public void insert(int data){
        if (size < heap.length) {
            heap[size++] = data;   // 插入堆尾
            shiftUp(size-1);   // 自下而上调整
        }
    }

    public void remove() {
        if (size > 0) {
            heap[0] = heap[size-1];   // 删除堆顶元素，并将堆尾元素回填到堆顶
            size --;   // 堆大小减一
            shiftDown(0, size-1);   // 自上向下调整为最小堆
        }
    }

    public void sort(){
        for (int i = size - 1; i >= 0; i--) {
            int temp = heap[0];
            heap[0] = heap[i];
            heap[i] = temp;

            shiftDown(0, i-1);
        }

        for (int i = size-1; i >= 0; i--) {
            System.out.print(heap[i] + " ");
        }
    }

    public void printMinHeap(int i) {
        if (size > i) {
            System.out.print(heap[i]);
            if (2 * i + 1 < size || 2 * i + 2 < size) {
                System.out.print("(");
                printMinHeap(2 * i + 1);
                System.out.print(",");
                printMinHeap(2 * i + 2);
                System.out.print(")");
            }
        }
    }
}