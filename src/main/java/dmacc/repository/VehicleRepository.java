package dmacc.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.Vehicle;
/**
 * @author Phuoc Tran - ptran9@dmacc.edu
 * CIS175 - Spring 2024
 * Apr 8, 2024
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

}
