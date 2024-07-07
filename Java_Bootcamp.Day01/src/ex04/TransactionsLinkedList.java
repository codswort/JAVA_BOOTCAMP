package ex04;
import java.util.UUID;

class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}

public class TransactionsLinkedList implements TransactionsList {
    private Node head = null;
    private Node tail = null;
    private int amount = 0;
    private static class Node {
        private Node prev;
        private Node next;
        private Transaction transaction;
        public Node(Node prev, Node next, Transaction transaction) {
            this.prev = prev;
            this.next = next;
            this.transaction = transaction;
        }
        public void setPrev(Node prev) {
            this.prev = prev;
        }
        public Node getPrev() {
            return this.prev;
        }
        public void setNext(Node next) {
            this.next = next;
        }
        public Node getNext() {
            return this.next;
        }
        public void setTransaction(Transaction transaction) {
            this.transaction = transaction;
        }
        public Transaction getTransaction() {
            return this.transaction;
        }
    }

    public TransactionsLinkedList() {}
    @Override
    public void addTransaction(Transaction t) {
        Node newNode = new Node(tail, null, t);
        if (head != null) tail.next = newNode;
        else head = newNode;
        tail = newNode;
        amount++;
    }
    @Override
    public void delTransactionById(UUID id) {
        if (amount == 0) throw new TransactionNotFoundException("Список транзакций пуст!");
        if (id == null) throw new UserNotFoundException();
        for (Node i = head; i != null; i = i.next) {
            if (i.transaction.getId().equals(id)) {
                deleteTransaction(i);
                return;
            }
        }
        throw new UserNotFoundException();
    }
    @Override
    public Transaction[] toArray() {
        if (amount == 0) throw new TransactionNotFoundException("Список транзакций пуст!");
        Transaction[] array = new Transaction[amount];
        int i = 0;
        for (Node iter = head; iter != null; iter = iter.next, i++) {
            array[i] = iter.transaction;
        }
        return array;
    }
    public int getAmount() {
        return amount;
    }
    private void deleteTransaction(Node node) {
        Node nodePrev = node.prev;
        Node nodeNext = node.next;
        if (nodePrev != null) nodePrev.next = nodeNext;
        else head = nodeNext;
        if (nodeNext != null) nodeNext.prev = nodePrev;
        else tail = nodePrev;
        amount--;
    }
}
