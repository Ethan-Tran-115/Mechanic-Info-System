package dmacc.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.Vehicle;
/**
 * @author Phuoc Tran - ptran9@dmacc.edu
 * CIS175 - Spring 2024
 * Apr 8, 2024
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
	List<Vehicle> findByMakeAndModelAndYear(String make, String model, String year);
}
