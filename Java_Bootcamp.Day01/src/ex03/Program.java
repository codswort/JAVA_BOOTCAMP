package ex03;

public class Program {
    public static void main(String[] args) {
        try {
            User u1 = new User("user1", 1000);
            User u2 = new User("user2", 2000);
            User u3 = new User("user3", 3000);
            Transaction t1 = new Transaction(Transaction.Category.DEBIT, u1, u2, 100);
            Transaction t2 = new Transaction(Transaction.Category.DEBIT, u2, u1, 200);
            Transaction t3 = new Transaction(Transaction.Category.DEBIT, u1, u3, 300);
            TransactionsLinkedList list = new TransactionsLinkedList();
            list.addTransaction(t1);
            list.addTransaction(t2);
            System.out.println(u1);
            System.out.println(u2);
            System.out.println();
            for (int i = 0; i < list.getAmount(); i++) System.out.println(list.toArray()[i]);
            System.out.println();
            list.delTransactionById(t1.getId());
            list.delTransactionById(t3.getId());
            for (int i = 0; i < list.getAmount(); i++) System.out.println(list.toArray()[i]);
        } catch (TransactionNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
