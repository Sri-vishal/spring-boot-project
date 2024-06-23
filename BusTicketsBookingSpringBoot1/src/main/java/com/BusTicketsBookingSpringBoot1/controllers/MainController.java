package com.BusTicketsBookingSpringBoot1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.BusTicketsBookingSpringBoot1.models.CityModel;
import com.BusTicketsBookingSpringBoot1.models.PassengerModel;
import com.BusTicketsBookingSpringBoot1.respositories.CityRepository;
import com.BusTicketsBookingSpringBoot1.respositories.PassengerRespository;
import com.BusTicketsBookingSpringBoot1.services.BusService;
import com.BusTicketsBookingSpringBoot1.services.CityService;
import com.BusTicketsBookingSpringBoot1.services.PassengerService;

@Controller
public class MainController {

	final PassengerRespository passengerRespository;
	final BusService busService;
	final CityRepository cityRepository;
	final CityService cityService;
	final PassengerService passengerService;

	@Autowired
	public MainController(PassengerRespository passengerRespository, BusService busService,
			CityRepository cityRepository, CityService cityService, PassengerService passengerService) {
		this.passengerRespository = passengerRespository;
		this.busService = busService;
		this.cityRepository = cityRepository;
		this.cityService = cityService;
		this.passengerService = passengerService;
	}

	// first
	@GetMapping("/")
	public String adminPanel() {
		cityService.setCities(cityRepository.findAll());
		busService.setSeats(new PassengerModel[5][24]);
		cityService.setGrantedNumberForTheCity(cityService.awardingNumberToTheCity(cityService.getCities()));
		passengerService.addSomeUser();
		busService.arrangementOfPassengers(busService.getSeats());
		return "redirect:/index";
	}

	/* Automatically continue from first to second and Return Button in Add
	Passager,Passenger list,status page
	
    @GetMapping("/index")
    public String indexGet(Model model) {
        cityService.setCities(cityRepository.findAll());
        model.addAttribute("cities", cityService.getCities());
        return "dashboard";
   } */

	//Automatically continue from first to second and Return Button in Add
	//Passager,Passenger list,status page
	@GetMapping("/index")
	public ModelAndView indexGet() {
		ModelAndView mav = new ModelAndView("dashboard");
		List<CityModel> list = cityRepository.findAll();
		mav.addObject("cities", list);
		return mav;
	}

	// Check button in DashBoard
	@PostMapping("/index")
	public String indexPost(@RequestParam("fromCity") String fromCity, @RequestParam("toCity") String toCity,
			Model model) {
		if (!cityService.isUserChoseCorrectly(fromCity, toCity)) {
			model.addAttribute("warning", "Select the Cities Correctly");
			model.addAttribute("cities", cityService.getCities());
			return "dashboard";
		}
		model.addAttribute("cities", cityService.getCities());
		model.addAttribute("freeSeats",
				busService.numberOfFreePlaces(fromCity, toCity, cityService.getGrantedNumberForTheCity()));

		return "dashboard";
	}

	// Buy Ticket Button in DashBoard page
	@GetMapping("/addPassenger")
	public String addUserGet(Model model) {
		model.addAttribute("passengerModel", new PassengerModel());
		model.addAttribute("cities", cityService.getCities());
		return "addPassenger";
	}

	// Buy Ticket Button in add passenger page
	@PostMapping("/addPassenger")
	public String addUserPost(@ModelAttribute("passengerModel") PassengerModel passengerModel, Model model) {
		if (!cityService.isUserChoseCorrectly(passengerModel.getFromCity(), passengerModel.getToCity())) {
			model.addAttribute("warning", "Select the Cities Correctly");
			model.addAttribute("cities", cityService.getCities());
			return "addPassenger";
		}

		if (busService.numberOfFreePlaces(passengerModel.getFromCity(), passengerModel.getToCity(),
				cityService.getGrantedNumberForTheCity()) < 1) {
			model.addAttribute("info", "false");
			model.addAttribute("cities", cityService.getCities());
			return "addPassenger";
		}
		model.addAttribute("info", "true");
		busService.awardingPlace(passengerModel, cityService.getGrantedNumberForTheCity());
		passengerService.giveNumberOfPassenger(passengerModel, passengerRespository.findAll());

		passengerRespository.save(passengerModel);
		busService.getListOfPassengers().add(passengerModel);

		model.addAttribute("cities", cityService.getCities());
		return "addPassenger";
	}

	// Passenger List
	@GetMapping("/listOfPassengers")
	public String listOfPassengersGet(Model model) {
		model.addAttribute("passengers", passengerRespository.findAll());
		return "listOfPassengers";
	}

	@PostMapping("/listOfPassengers")
	public String listOfPassengersPost() {

		return "/index";
	}

	// Delete Passenger ticket
	@GetMapping("/delete/{number}")
	public String deleteGet(@PathVariable("number") int numberPassenger) {
		passengerRespository.deleteById(numberPassenger);
		return "redirect:/listOfPassengers";
	}

	// Check The Status button in DashBoard
	@GetMapping("/status")
	public String statusGet(Model model) {
		busService.clearSeats();
		passengerService.addSomeUser();

		model.addAttribute("seats", busService.getSeats());
		return "status";
	}

	@PostMapping("/status")
	public String statusPost() {

		return "status";
	}

}
