package ax.k.repos;

import ax.k.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository <Customer, Long> {

    Customer findByName(String name);
}
