package ex05;

public class User {
    private int id;
    private String name;
    private int balance;
    private TransactionsList transactionsList;
    public User() {}
    public User(String name, int balance) {
        if (balance < 0) {
            System.err.println("Баланс не может быть отрицательным!");
        } else {
            this.name = name;
            this.balance = balance;
            this.id = UserIdsGenerator.getInstance().generateId();
            this.transactionsList = new TransactionsLinkedList();
        }
    };
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getBalance() {
        return this.balance;
    }
    public void setBalance(int balance) {
        if (balance < 0) this.balance = 0;
        else this.balance = balance;
    }
    public TransactionsList getTransactionsList() {
        return transactionsList;
    }
    @Override
    public String toString() {
        return "username: " + name + "; id: " + id + "; " + "balance: " + balance;
    }
}