package ua.andrii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.andrii.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}