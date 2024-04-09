package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.beans.Appointment;

/**
 * @author chumb - djackson16
 * CIS175 - Spring 2024
 * Apr 9, 2024
 */

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
