package ex05;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private  boolean dev;
    static Scanner scanner = new Scanner(System.in);
    static User u = new User();

    public Menu(boolean dev) {
        this.dev = dev;
    }

    static private TransactionsService transactionsService = new TransactionsService();
    public boolean inputMenuNumber() {
        inviteUser();
        System.out.print("-> ");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
//                while (true) {
                    System.out.println("Enter a user name and a balance");
                    addUser();
//                    else continue;
//                }
                break;
            case "2":
                try{
                  viewUserBalance();
                } catch (RuntimeException rex){
                    System.out.println(rex.getMessage());
                }
                break;
            case "3":
                System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
                while (true) if (performTransfer()) break;
                break;
            case "4":
                System.out.println("Enter a user ID");
                while (true) if (viewAllUserTransactions()) break;
                break;
            case "5":
                if (dev) {
                    System.out.println("Enter a user ID and a transfer ID");
                    while (true) if (removeTransfer()) break;
                } else {
                    return true;
                }
                break;
            case "6":
                if (dev) {
                    while (true) if (checkValidation()) break;
                } else {
                    throw new IllegalArgumentException("Choose between 1-5");
                }
                break;
            case "7":
                if (dev) return true;
                else throw new IllegalArgumentException("Choose between 1-5");
            default:
                throw new IllegalArgumentException("Choose between 1-7");

        }
        return false;
    }
    static boolean addUser() {
        System.out.print("-> ");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            System.out.println("Needs a user name and a balance!");
            return false;
        }
        try {
            int balance = Integer.parseInt(parts[1]);
            u = new User(parts[0], balance);
            if (balance < 0) return false;
            transactionsService.addUser(u);
            System.out.println("User with id = " + u.getId() + " is added");
            System.out.println("---------------------------------------------------------");
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
            return true;
        }
        return true;
    }
    static void viewUserBalance() {
        System.out.println("Enter a user ID");
        System.out.print("-> ");
        String input = scanner.nextLine();
            int userId = Integer.parseInt(input);
            User user = transactionsService.getUserById(userId);
            if (user == null){
                throw new UserNotFoundException("Пользователь с таким id отсутствует!");
            } else {
                System.out.println(user.getName() + " - " + user.getBalance());
                System.out.println("---------------------------------------------------------");
            }
    }
    static boolean performTransfer() {
        System.out.print("-> ");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length < 3) {
            System.out.println("Needs a sender ID, a recipient ID, and a transfer amount");
            return true;
        }
        try {
            int senderId = Integer.parseInt(parts[0]);
            int recipientId = Integer.parseInt(parts[1]);
            int amount = Integer.parseInt(parts[2]);
            transactionsService.doTransaction(senderId, recipientId, amount);
            System.out.println("The transfer is completed");
            System.out.println("---------------------------------------------------------");
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
            return false;
        }
        return true;
    }
    static boolean viewAllUserTransactions() {
        String input = scanner.nextLine();
        try {
            int userId = Integer.parseInt(input);
            User user = transactionsService.getUserById(userId);
            for (Transaction t : user.getTransactionsList().toArray()) {
                if (t.getTransferCategory().equals(Transaction.Category.CREDIT))
                    System.out.println("To " + t.getRecipient().getName() + "(id = " + t.getRecipient().getId() + ") " +
                        t.getTransferAmount() + " with id = " + t.getId());
                else
                    System.out.println("From " + t.getSender().getName() + "(id = " + t.getSender().getId() + ") " +
                            t.getTransferAmount() + " with id = " + t.getId());
            }
            System.out.println("---------------------------------------------------------");
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
        }
        return true;
    }

    static boolean removeTransfer() {
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            System.err.println("Invalid dates!");
            return false;
        }
        try {
            int userId = Integer.parseInt(parts[0]);
            User user = transactionsService.getUserById(userId);
            UUID transactionId = UUID.fromString(parts[1]);
            Transaction t = transactionsService.getTransactionById(transactionId);
            for (Transaction transaction : transactionsService.getTransactionsListOfUser(user)) {
                if ((transaction.getSender().getId() == user.getId() || transaction.getRecipient().getId() == user.getId())
                        && transaction.getId().equals(transactionId)) t = transaction;
            }
            transactionsService.delTransactionOfUserById(userId, transactionId);
            User recipient = t.getRecipient(), sender = t.getSender();
            if (t.getTransferCategory().equals(Transaction.Category.CREDIT))
                System.out.println("Transfer To " + recipient.getName() + "(id = " + recipient.getId() +
                        ") " + Math.abs(t.getTransferAmount()) + " removed");
            else
                System.out.println("Transfer From " + sender.getName() + "(id = " + sender.getId() +
                        ") " + Math.abs(t.getTransferAmount()) + " removed");
            System.out.println("---------------------------------------------------------");
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
            if (rex.getMessage().equals("Пользователь с таким id отсутствует!")) return true;
            if (rex.getMessage().equals("Invalid UUID string: " + parts[1])) return true;
            return false;
        }
        return true;
    }
    static boolean checkValidation() {
        try {
            Transaction[] transactions = transactionsService.unpairedTransactions();
            System.out.println("Check results:");
            for (Transaction t : transactions) {
                if (t.getTransferCategory().equals(Transaction.Category.DEBIT))
                    System.out.printf("%s(id = %d) has an unacknowledged transfer id = " + "%s " + "from %s(id = %d) for %d\n",
                            t.getRecipient().getName(), t.getRecipient().getId(),
                            t.getId(), t.getSender().getName(), t.getSender().getId(),
                            Math.abs(t.getTransferAmount()));
                else
                    System.out.printf("%s(id = %d) has an unacknowledged transfer id = " + "%s " + "to %s(id = %d) for %d\n",
                            t.getSender().getName(), t.getSender().getId(),
                            t.getId(), t.getRecipient().getName(), t.getRecipient().getId(),
                            Math.abs(t.getTransferAmount()));
            }
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
        }
        return true;
    }

    void inviteUser() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (dev) {
            System.out.println("5. DEV - remove a transfer by ID");
            System.out.println("6. DEV - check transfer validaty");
            System.out.println("7. Finish execution");
        } else {
            System.out.println("5. Finish execution");
        }
    }
}
