package dmacc.beans;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chumb - djackson16
 * CIS175 - Spring 2024
 * Apr 2, 2024
 */

@Entity
@Data
@NoArgsConstructor
public class Appointment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String description;
	private String date;
	@ManyToOne
    @JoinColumn(name = "customer_id")
	private Customer customer;
}
