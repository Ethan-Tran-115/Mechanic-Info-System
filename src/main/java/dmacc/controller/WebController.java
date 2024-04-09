package dmacc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dmacc.beans.Customer;
import dmacc.beans.Vehicle;
import dmacc.repository.CustomerRepository;
import dmacc.repository.VehicleRepository;

/**
 * @author Phuoc Tran - ptran9@dmacc.edu
 * CIS175 - Spring 2024
 * Apr 9, 2024
 */

@RestController
public class WebController {
	@Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    //Adds a new customer
    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
    }

    //Delete a customer by ID
    @DeleteMapping("/deleteCustomer")
    public void deleteCustomer(@RequestParam Long customerId) {
        customerRepository.deleteById(customerId);
    }

    //Add a new vehicle
    @PostMapping("/addNewVehicle")
    public void addNewVehicle(@RequestBody Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }
    
    //Add a vehicle to a customer
    @PostMapping("/addVehicle")
    public void addVehicle(@RequestParam Long customerId, @RequestBody Vehicle vehicle) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            vehicle.setCustomer(customer);
            vehicleRepository.save(vehicle);
        }
    }

    //Deletes a vehicle by ID
    @DeleteMapping("/deleteVehicle")
    public void deleteVehicle(@RequestParam int vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

    //Searches for customers by first name or last name.
    @GetMapping("/searchCustomersByName")
    public List<Customer> searchCustomersByName(@RequestParam String name) {
        return customerRepository.findByFNameContainingIgnoreCaseOrLNameContainingIgnoreCase(name, name);
    }

    
    //Searches for vehicles by make, model, and year.
    @GetMapping("/searchVehicles")
    public List<Vehicle> searchVehicles(@RequestParam String make, @RequestParam String model,
                                        @RequestParam String year) {
        return vehicleRepository.findByMakeAndModelAndYear(make, model, year);
    }

    
    //Updates a vehicle's details.
    @PutMapping("/updateVehicle")
    public void updateVehicle(@RequestParam int vehicleId, @RequestBody Vehicle vehicle) {
        Vehicle existingVehicle = vehicleRepository.findById(vehicleId).orElse(null);
        if (existingVehicle != null) {
            existingVehicle.setMake(vehicle.getMake());
            existingVehicle.setModel(vehicle.getModel());
            existingVehicle.setYear(vehicle.getYear());
            vehicleRepository.save(existingVehicle);
        }
    }
}
