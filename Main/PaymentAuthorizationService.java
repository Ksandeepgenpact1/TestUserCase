import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentAuthorizationService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public void authorizePayment(String accountNumber, double amount) {
        validatePaymentAmount(amount);

        // Fetch the account associated with the provided account number
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found for account number: " + accountNumber);
        }

        // Create a payment object and save it to the database
        Payment payment = new Payment();
        payment.setAccount(account);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.AUTHORIZED);
        paymentRepository.save(payment);

        // Update the account balance
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Transactional
    public void capturePaymentDetails(String accountNumber, double amount) {
        validatePaymentAmount(amount);

        // Fetch the account associated with the provided account number
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found for account number: " + accountNumber);
        }

        // Create a payment object with status set to CAPTURED (or another appropriate status)
        Payment payment = new Payment();
        payment.setAccount(account);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.CAPTURED); // Assuming CAPTURED as the new status
        paymentRepository.save(payment);

        // Note: The account balance is not updated immediately in this method; it depends on your business logic.
        // You might want to update the balance in a separate process or when certain conditions are met.
    }

    private void validatePaymentAmount(double amount) {
        if (amount <= 0) {
            throw new InvalidPaymentAmountException("Payment amount must be greater than zero.");
        }
    }
}