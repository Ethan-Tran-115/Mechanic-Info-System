package dmacc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Appointment;
import dmacc.beans.Customer;
import dmacc.beans.Vehicle;
import dmacc.repository.AppointmentRepository;
import dmacc.repository.CustomerRepository;
import dmacc.repository.VehicleRepository;

/**
 * @author chumb - djackson16 CIS175 - Spring 2024 Apr 2, 2024
 */

@Controller
public class WebController {
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@GetMapping("/viewAllCustomers")
	public String viewAllCustomers(Model model) {
		if (customerRepository.findAll().isEmpty()) {
			return addNewCustomer(model);
		}
		model.addAttribute("customers", customerRepository.findAll());
		return "viewAllCustomers";
	}

	@GetMapping("/viewSpecificCustomer/{id}")
	public String viewCustomerVehicles(@PathVariable("id") long id, Model model) {
		Customer c = customerRepository.findById(id).orElse(null);
		List<Vehicle> customerVehicles = vehicleRepository.findAllByCustomerId(id);
		List<Appointment> customerAppointments = appointmentRepository.findAllByCustomerId(id);
		model.addAttribute("customer", c);
		model.addAttribute("customerVehicles", customerVehicles);
		model.addAttribute("customerAppointments", customerAppointments);
		return "viewSpecificCustomer";
	}

	@GetMapping("/inputCustomer")
	public String addNewCustomer(Model model) {
		Customer c = new Customer();
		Vehicle v = new Vehicle();
		model.addAttribute("newCustomer", c);
		model.addAttribute("newVehicle", v);
		return "addCustomer";
	}

	@PostMapping("/inputCustomer")
	public String addNewCustomer(@ModelAttribute Customer c, @ModelAttribute Vehicle v, Model model) {
		if (v.getMake() == "") {
			customerRepository.save(c);
			return viewAllCustomers(model);
		}
		List<Vehicle> tempVehicles = new ArrayList<>();
		v.setCustomer(c);
		tempVehicles.add(v);
		c.setVehicles(tempVehicles);
		customerRepository.save(c);
		return viewAllCustomers(model);
	}

	@GetMapping("/editCustomer/{id}")
	public String showUpdateCustomer(@PathVariable("id") long id, Model model) {
		Customer c = customerRepository.findById(id).orElse(null);
		model.addAttribute("updateCustomer", c);
		return "updateCustomer";
	}

	@PostMapping("/updateCustomer/{id}")
	public String reviseCustomer(Customer c, Model model) {
		customerRepository.save(c);
		return "viewAllCustomers";
	}

	@GetMapping("/deleteCustomer/{id}")
	public String deleteCustomer(@PathVariable("id") long id, Model model) {
		Customer c = customerRepository.findById(id).orElse(null);
		customerRepository.delete(c);
		return viewAllCustomers(model);
	}

	@GetMapping("/viewAllVehicles")
	public String viewAllVehicles(Model model) {
		if (vehicleRepository.findAll().isEmpty()) {
			return addNewVehicle(model);
		}
		model.addAttribute("vehicles", vehicleRepository.findAll());
		return "viewAllVehicles";
	}
	
	@GetMapping("/addVehicleToCustomer/{id}")
	public String addVehicleToCustomer(@PathVariable("id") long id, Model model) {
		List<Customer> tempCustomerList = new ArrayList<>();
		Customer c = customerRepository.findById(id).orElse(null);
		Vehicle v = new Vehicle();
		tempCustomerList.add(c);
		model.addAttribute("newVehicle", v);
		model.addAttribute("customers", tempCustomerList);
		return "addVehicle";
	}

	@GetMapping("/inputVehicle")
	public String addNewVehicle(Model model) {
		Vehicle v = new Vehicle();
		model.addAttribute("newVehicle", v);
		model.addAttribute("customers", customerRepository.findAll());
		return "addVehicle";
	}

	@PostMapping("/inputVehicle")
	public String addNewVehicle(@ModelAttribute Vehicle v, Model model) {
		Customer selectedCustomer = customerRepository.findById(v.getCustomer().getId()).orElse(null);
		v.setCustomer(selectedCustomer);
		vehicleRepository.save(v);
		return viewAllVehicles(model);
	}

	@GetMapping("/editVehicle/{id}")
	public String showUpdateVehicle(@PathVariable("id") long id, Model model) {
		Vehicle v = vehicleRepository.findById(id).orElse(null);
		model.addAttribute("updateVehicle", v);
		return "updateVehicle";
	}

	@PostMapping("/updateVehicle/{id}")
	public String reviseVehicle(Vehicle v, Model model) {
		vehicleRepository.save(v);
		return "redirect:/index.html";
	}

	@GetMapping("/deleteVehicle/{id}")
	public String deleteVehicle(@PathVariable("id") long id, Model model) {
		Vehicle v = vehicleRepository.findById(id).orElse(null);
		Customer c = v.getCustomer();
		int vehicleListNum = c.getVehicles().indexOf(v);
		c.getVehicles().remove(vehicleListNum);
		v.setCustomer(null);
		vehicleRepository.delete(v);
		return viewAllVehicles(model);
	}

	@GetMapping("/viewAllAppointments")
	public String viewAllAppointments(Model model) {
		if (appointmentRepository.findAll().isEmpty()) {
			return addNewAppointment(model);
		}
		model.addAttribute("appointment", appointmentRepository.findAll());
		return "viewAllAppointments";
	}
	
	@GetMapping("/addAppointmentToCustomer/{id}")
	public String addAppointmentToCustomer(@PathVariable("id") long id, Model model) {
		List<Customer> tempCustomerList = new ArrayList<>();
		Customer c = customerRepository.findById(id).orElse(null);
		Appointment a = new Appointment();
		tempCustomerList.add(c);
		model.addAttribute("newAppointment", a);
		model.addAttribute("customers", tempCustomerList);
		return "createAppointment";
	}

	@GetMapping("/inputAppointment")
	public String addNewAppointment(Model model) {
		Appointment a = new Appointment();
		model.addAttribute("newAppointment", a);
		model.addAttribute("customers", customerRepository.findAll());
		return "createAppointment";
	}

	@PostMapping("/inputAppointment")
	public String addNewAppointment(@ModelAttribute Appointment a, Model model) {
		Customer selectedCustomer = customerRepository.findById(a.getCustomer().getId()).orElse(null);
		a.setCustomer(selectedCustomer);
		appointmentRepository.save(a);
		return viewAllAppointments(model);
	}

	@GetMapping("/editAppointment/{id}")
	public String showUpdateAppointment(@PathVariable("id") long id, Model model) {
		Appointment a = appointmentRepository.findById(id).orElse(null);
		model.addAttribute("newAppointment", a);
		return "updateAppointment";
	}

	@PostMapping("/updateAppointment/{id}")
	public String reviseAppointment(Appointment a, Model model) {
		appointmentRepository.save(a);
		return viewAllAppointments(model);
	}

	@GetMapping("/deleteAppointment/{id}")
	public String deleteAppointment(@PathVariable("id") long id, Model model) {
		Appointment a = appointmentRepository.findById(id).orElse(null);
		if (a.getCustomer() == null) {
			appointmentRepository.delete(a);
			return viewAllAppointments(model);
		}
		Customer c = a.getCustomer();
		int appointmentListNum = c.getAppointments().indexOf(a);
		c.getAppointments().remove(appointmentListNum);
		a.setCustomer(null);
		appointmentRepository.delete(a);
		return viewAllAppointments(model);
	}
	
}
