package ax.k.service;

import ax.k.domain.Customer;
import ax.k.domain.Role;
import ax.k.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String customer) throws UsernameNotFoundException {
        return customerRepo.findByName(customer);
    }

    public boolean addCustomer(Customer customer) {
        Customer customerFromDB = customerRepo.findByName(customer.getUsername());

        if (customerFromDB != null) {
            return false;
        }
        customer.setActive(true);
        customer.setRoles(Collections.singleton(Role.USER));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepo.save(customer);
        return true;
    }
}
