public class Transaction {
    private Account senderAccount;
    private Account receiverAccount;
    private double amount;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private String transactionDate;

    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    public enum TransactionStatus {
        PENDING, COMPLETED, FAILED
    }

    // Constructor for a deposit or withdrawal
    public Transaction(Account senderAccount, double amount, TransactionType transactionType) {
        this.senderAccount = senderAccount;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionStatus = TransactionStatus.PENDING;
        this.transactionDate = java.time.LocalDate.now().toString();
    }

    // Constructor for a transfer
    public Transaction(Account senderAccount, Account receiverAccount, double amount, TransactionType transactionType) {
        this(senderAccount, amount, transactionType);
        this.receiverAccount = receiverAccount;
    }

    // Update processTransaction() to use enums
    public void processTransaction() {
        if (transactionType == TransactionType.DEPOSIT) {
            senderAccount.deposit(amount);
            transactionStatus = TransactionStatus.COMPLETED;
        } else if (transactionType == TransactionType.WITHDRAWAL) {
            if (senderAccount.getBalance() >= amount) {
                senderAccount.withdraw(amount);
                transactionStatus = TransactionStatus.COMPLETED;
            } else {
                transactionStatus = TransactionStatus.FAILED;
                System.out.println("Insufficient balance for withdrawal.");
            }
        } else if (transactionType == TransactionType.TRANSFER) {
            if (senderAccount.getBalance() >= amount) {
                senderAccount.withdraw(amount);
                receiverAccount.deposit(amount);
                transactionStatus = TransactionStatus.COMPLETED;
            } else {
                transactionStatus = TransactionStatus.FAILED;
                System.out.println("Insufficient balance for transfer.");
            }
        } else {
            transactionStatus = TransactionStatus.FAILED;
            System.out.println("Invalid transaction type.");
        }
    }
}
