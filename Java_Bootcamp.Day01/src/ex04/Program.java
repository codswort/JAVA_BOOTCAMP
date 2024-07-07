package ex04;

public class Program {
    public static void main(String[] args) {
        try {
            User u1 = new User("user1", 1000);
            User u2 = new User("user2", 2000);
            User u3 = new User("user3", 3000);
            System.out.println(u1);
            System.out.println(u2);
            System.out.println(u3);
            System.out.println();
            TransactionsService trs1 = new TransactionsService();
            trs1.addUser(u1);
            trs1.addUser(u2);
            trs1.addUser(u3);
            trs1.doTransaction(u1.getId(), u2.getId(), 400);
            trs1.doTransaction(u3.getId(), u1.getId(), 300);
            trs1.doTransaction(u1.getId(), u3.getId(), 3000);
            for (int i = 0; i < trs1.getTransactionsListOfUser(u1).length; i++)
                System.out.println("u1::: " + trs1.getTransactionsListOfUser(u1)[i]);
            for (int i = 0; i < trs1.getTransactionsListOfUser(u2).length; i++)
                System.out.println("u2::: " + trs1.getTransactionsListOfUser(u2)[i]);
            for (int i = 0; i < trs1.getTransactionsListOfUser(u3).length; i++)
                System.out.println("u3::: " + trs1.getTransactionsListOfUser(u3)[i]);
            System.out.println();
            trs1.delTransactionOfUserById(u1.getId(), u1.getTransactionsList().toArray()[0].getId());
            System.out.println("Транзакции после операции удаления");
            for (int i = 0; i < trs1.getTransactionsListOfUser(u1).length; i++)
                System.out.println("u1::: " + trs1.getTransactionsListOfUser(u1)[i]);
            for (int i = 0; i < trs1.getTransactionsListOfUser(u2).length; i++)
                System.out.println("u2::: " + trs1.getTransactionsListOfUser(u2)[i]);
            for (int i = 0; i < trs1.getTransactionsListOfUser(u3).length; i++)
                System.out.println("u3::: " + trs1.getTransactionsListOfUser(u3)[i]);
            System.out.println();
            System.out.println(u1);
            System.out.println(u2);
            System.out.println(u3);
            System.out.println();
            System.out.println("Массив непраных транзакций");
            for (int i = 0; i < trs1.unpairedTransactions().length; i++)
                System.out.println(trs1.unpairedTransactions()[i]);
        } catch (RuntimeException rex) {
            System.err.println(rex.getMessage());
            System.exit(-1);
        }
    }
}
