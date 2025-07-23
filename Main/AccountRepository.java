import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface AccountRepository extends JpaRepository<Account, Long> {
 
    Account findAccountByAccountNumber(String accountNumber);
 
    List<String> findByBalanceGreaterThan(double amount);
 
    List<Account> findByBalanceLesserThan(double amount);
 
    List<Account> findWithNumberOfTransactions(int numberOfTransactions);
 
    List<Account> findByCustomCondition(int customCondition);
}