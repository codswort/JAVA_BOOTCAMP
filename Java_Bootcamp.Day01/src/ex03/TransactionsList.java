package ex03;
import java.util.UUID;

public interface TransactionsList {
    void addTransaction(Transaction t);
    void delTransactionById(UUID id);
    Transaction[] toArray();
}
