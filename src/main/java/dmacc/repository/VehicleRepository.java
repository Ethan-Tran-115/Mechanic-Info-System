package dmacc.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.beans.Vehicle;
/**
 * @author Phuoc Tran - ptran9@dmacc.edu
 * CIS175 - Spring 2024
 * Apr 8, 2024
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	public List<Vehicle> findAllByCustomerId(Long customerId);
}
