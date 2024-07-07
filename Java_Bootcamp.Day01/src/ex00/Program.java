package ex00;

public class Program {
    public static void main(String[] args) {
        User u1 = new User("User1", 1000);
        User u2 = new User("User2", 1000);
        Transaction tr1 = new Transaction(Transaction.Category.DEBIT, u1, u2,100);
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(tr1);
        User u3 = new User("User3", 1000);
        Transaction tr2 = new Transaction(Transaction.Category.CREDIT, u1, u3,-500);
        System.out.println(u1);
        System.out.println(u3);
        System.out.println(tr2);
    }
}
