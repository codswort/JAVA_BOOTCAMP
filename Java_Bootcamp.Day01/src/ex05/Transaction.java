package ex05;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private User recipient;
    private User sender;
    private Category transferCategory;
    private int transferAmount;


    public Transaction(Category transferCategory, User sender, User recipient, int transferAmount) {
        if (transferCategory == Category.DEBIT && transferAmount < 0 ||
                transferCategory == Category.CREDIT && transferAmount > 0) {
            System.err.println("Сумма перевода для дебета(кредита) не может быть отрицательной(положительной)!");
        } else if (transferCategory == Category.DEBIT && sender.getBalance() < transferAmount ||
                transferCategory == Category.CREDIT && sender.getBalance() < -transferAmount) {
            System.err.println("Недостаточно средств у отправителя!");
        } else {
            this.id = UUID.randomUUID();
            this.recipient = recipient;
            this.sender = sender;
            this.transferCategory = transferCategory;
            this.transferAmount = transferAmount;
        }

    }
    enum Category {
        DEBIT, CREDIT
    }
    public UUID getId() {
        return id;
    }
    public void setID(UUID id) {
        this.id = id;
    }
    public User getRecipient() {
        return recipient;
    }
    public User getSender() {
        return sender;
    }
    public Category getTransferCategory() {
        return transferCategory;
    }
    public int getTransferAmount() {
        return transferAmount;
    }
    @Override
    public String toString() {
        return "Transaction: № " + id + "; " +
                sender +
                " ---> " + recipient +
                "; Amount: " + this.transferAmount +
                "; Category: " + transferCategory + ".";
    }
    private void updateBalance(Category transferCategory, User sender, User recipient, int transferAmount) {
        if (transferCategory == Category.DEBIT) {
            sender.setBalance(sender.getBalance() - transferAmount);
            recipient.setBalance(recipient.getBalance() + transferAmount);
        } else if (transferCategory == Category.CREDIT) {
            sender.setBalance(sender.getBalance() + transferAmount);
            recipient.setBalance(recipient.getBalance() - transferAmount);
        }
    }
}
