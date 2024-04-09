package dmacc.repository;
import dmacc.beans.Customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author Phuoc Tran - ptran9@dmacc.edu
 * CIS175 - Spring 2024
 * Apr 8, 2024
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
}
