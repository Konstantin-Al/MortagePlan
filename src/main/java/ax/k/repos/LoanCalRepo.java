package ax.k.repos;

import ax.k.domain.Customer;
import ax.k.domain.LoanCal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanCalRepo extends JpaRepository<LoanCal, Long> {

    List<LoanCal> findLoanCalByCustomerOrderByCDDesc(Customer customer);
}
