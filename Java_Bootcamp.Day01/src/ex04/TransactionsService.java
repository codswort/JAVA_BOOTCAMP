package ex04;

import java.util.UUID;

class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException(String message) {
        super(message);
    }
}
public class TransactionsService {
    private UsersList userList = new UsersArrayList();
    public TransactionsService() {}
    public void addUser(User user) {
        userList.addUser(user);
    }
    public double getBalance(User user) {
        return user.getBalance();
    }
    public Transaction[] getTransactionsListOfUser(User user) {
        return user.getTransactionsList().toArray();
    }
    public void delTransactionOfUserById(int userId, UUID transactionId) {
        userList.getUserById(userId).getTransactionsList().delTransactionById(transactionId);
    }
    public void doTransaction(int senderId, int recipientId, double transferAmount) {
        if (senderId == recipientId || transferAmount <= 0) throw new IllegalTransactionException("Транзакция не выполнена, проверьте водные данные!");
        User sender = userList.getUserById(senderId);
        User recipient = userList.getUserById(recipientId);
        if (transferAmount > sender.getBalance())
            throw new IllegalTransactionException("У отправителя недостаточно средств!");
        Transaction debitTransaction = new Transaction(
                Transaction.Category.DEBIT, sender, recipient, transferAmount);
        Transaction creditTransaction = new Transaction(
                Transaction.Category.CREDIT, sender, recipient, -transferAmount);

        debitTransaction.setID(creditTransaction.getId());

        sender.getTransactionsList().addTransaction(creditTransaction);
        recipient.getTransactionsList().addTransaction(debitTransaction);

        sender.setBalance(sender.getBalance() - transferAmount);
        recipient.setBalance(recipient.getBalance() + transferAmount);
    }
    public Transaction[] unpairedTransactions() {
        Transaction[] allTransactions = allTransactions();
        TransactionsLinkedList unpairTransactionsList = new TransactionsLinkedList();
        for (int i = 0; i < allTransactions.length; i++) {
            boolean flag = false;
            for (int j = 0; j < allTransactions.length && !flag; j++) {
                if (j == i) continue;
                if (allTransactions[i].getId().equals(allTransactions[j].getId())) flag = true;
            }
            if (!flag) unpairTransactionsList.addTransaction(allTransactions[i]);
        }
        return unpairTransactionsList.toArray();
    }
    public User[] getUsersArray() {
        User[] usersArray = new User[userList.getUsersAmount()];
        for (int i = 0; i < userList.getUsersAmount(); i++) {
            usersArray[i] = userList.getUserByIndex(i);
        }
        return usersArray;
    }
    private Transaction[] allTransactions() {
        User[] usersArray = getUsersArray();
        TransactionsLinkedList transactionsList = new TransactionsLinkedList();
        for (int i = 0; i < usersArray.length; i++) {
            for (int j = 0; j < usersArray[i].getTransactionsList().toArray().length; j++) {
                transactionsList.addTransaction(usersArray[i].getTransactionsList().toArray()[j]);
            }
        }
        return transactionsList.toArray();
    }
}
