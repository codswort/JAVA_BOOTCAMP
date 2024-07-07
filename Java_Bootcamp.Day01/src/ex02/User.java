package ex02;

public class User {
    private int id;
    private String name;
    private double balance;
    public User(String name, double balance) {
        if (balance < 0) {
            throw new UserNotFoundException("Баланс не может быть отрицательным!");
        } else {
            this.name = name;
            this.balance = balance;
            this.id = UserIdsGenerator.getInstance().generateId();
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

    public double getBalance() {
        return this.balance;
    }
    public void setBalance(double balance) {
        if (balance < 0) this.balance = 0;
        else this.balance = balance;
    }
    @Override
    public String toString() {
        return "username: " + name + "; id: " + id + "; " + "balance: " + balance;
    }
}
