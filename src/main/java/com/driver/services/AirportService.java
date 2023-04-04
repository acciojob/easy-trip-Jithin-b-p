package com.driver.services;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repositories.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {

    AirportRepository airportRepository = new AirportRepository();

    public void addAirport(Airport airport){ //1

        airportRepository.addAirport(airport);

    }

    public void addFlight(Flight flight){   //2

        airportRepository.addFlight(flight);

    }

    public void addPassenger(Passenger passenger){      //3

        airportRepository.addPassenger(passenger);

    }

    public String bookATicket(Integer flightId, Integer passengerId){   //4

        return airportRepository.bookATicket(flightId, passengerId);

    }

    public String cancelATicket(Integer flightId, Integer passengerId){     //5

        return airportRepository.cancelATicket(flightId, passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){      //6

        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);

    }

    public String getLargestAirport(){  //7

        return airportRepository.getLargestAirport();

    }

    public String getAirportNameFromFlightId(Integer flightId){     //8

        return airportRepository.getAirportNameFromFlightId(flightId);

    }

    public int calculateFlightFare(Integer flightId){       //9

        return airportRepository.calculateFlightFare(flightId);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){        //10

        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity, toCity);
    }

    public int getNumberOfPeopleOn(Date date, String airportName){      //11

        return airportRepository.getNumberOfPeopleOn(date, airportName);
    }

    public int calculateRevenueOfAFlight(Integer flightId){     //12

        return airportRepository.calculateRevenueOfAFlight(flightId);
    }
}
