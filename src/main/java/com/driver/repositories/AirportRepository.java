package com.driver.repositories;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

class Pair{

    Integer passengerID;
    Integer farePaid;


}
@Repository
public class AirportRepository {

    private HashMap<String, Airport> airportDb;
    private HashMap<Integer, Flight> flightDb;

    private HashMap<Integer, Passenger> passengerDb;

    private HashMap<Integer, HashSet<Integer>> flightBookingDb;
    private HashMap<Integer, HashMap<Integer, Integer>> flightPassengerFareDb;

    private HashMap<Integer, Integer> passangerBookingCountDb;

    private HashMap<Integer, Integer> flightCurrentFareDb;
    private HashMap<Integer, Integer> flightRevenueLiveDb;


    public AirportRepository(){

        airportDb = new HashMap<>();
        flightDb = new HashMap<>();
        passengerDb = new HashMap<>();
        flightBookingDb = new HashMap<>();
        flightPassengerFareDb = new HashMap<>();
        passangerBookingCountDb = new HashMap<>();
        flightCurrentFareDb = new HashMap<>();
        flightRevenueLiveDb = new HashMap<>();

    }

    public void addAirport(Airport airport){

        airportDb.put(airport.getAirportName(), airport);

    }

    public void addFlight(Flight flight){

        flightDb.put(flight.getFlightId(), flight);
        flightBookingDb.put(flight.getFlightId(), new HashSet<>());
        flightCurrentFareDb.put(flight.getFlightId(), 3000);
        flightRevenueLiveDb.put(flight.getFlightId(), 0);

    }

    public void addPassenger(Passenger passenger){

        passengerDb.put(passenger.getPassengerId(), passenger);

    }


    public String bookATicket(Integer flightId, Integer passengerId){


        //if the flightid is invalid or passenger id is invalid return failure
        if(!flightDb.containsKey(flightId) || !passengerDb.containsKey(passengerId)) return "FAILURE";

        if(flightBookingDb.get(flightId).size() != 0){

            if(flightBookingDb.get(flightId).size() == flightDb.get(flightId).getMaxCapacity()){

                return "FAILURE";

            }
            if(flightBookingDb.get(flightId).contains(passengerId)){

                return "FAILURE";

            }

            int noOfPeopleWhoHaveAlreadyBooked = flightBookingDb.get(flightId).size();
            Integer currentPrice = 3000 + noOfPeopleWhoHaveAlreadyBooked * 50;
            flightCurrentFareDb.put(flightId, currentPrice);
            flightPassengerFareDb.put(flightId, new HashMap<>(passengerId, currentPrice));
            flightRevenueLiveDb.put(flightId, flightRevenueLiveDb.getOrDefault(flightId, 0) + currentPrice);

        }

        if(flightBookingDb.get(flightId).contains(passengerId)) return "FAILURE";

        flightBookingDb.get(flightId).add(passengerId);
        passangerBookingCountDb.put(passengerId, passangerBookingCountDb.getOrDefault(passengerId, 0) + 1);
        return "SUCCESS";
    }

    public String cancelATicket(Integer flightId, Integer passengerId){

        if(!flightDb.containsKey(flightId) || !passengerDb.containsKey(passengerId)) return "FAILURE";

        if(!flightBookingDb.get(flightId).contains(passengerId)) return "FAILURE";

        flightBookingDb.get(flightId).remove(passengerId);

        Map<Integer, Integer> passengerFairPaid = flightPassengerFareDb.get(flightId);
        int farePaidByTheCurrentPassenger = passengerFairPaid.get(passengerId);

        flightRevenueLiveDb.put(flightId, flightRevenueLiveDb.getOrDefault(flightId, 0) - farePaidByTheCurrentPassenger);

        int noOfPeopleWhoHaveAlreadyBooked = flightBookingDb.get(flightId).size();
        int currentPrice = 3000 + noOfPeopleWhoHaveAlreadyBooked * 50;
        flightCurrentFareDb.put(flightId, currentPrice);

        return "SUCCESS";

    }

    public int calculateRevenueOfAFlight(Integer flightId){

        if(flightDb.containsKey(flightId)){
            return flightRevenueLiveDb.get(flightId);
        }
        return 0;
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){

        if(passangerBookingCountDb.containsKey(passengerId)){
            return passangerBookingCountDb.get(passengerId);
        }
        return 0;

    }
    public String getLargestAirport(){

        if(airportDb.size() == 0) return "no airport added";


        int largestNoOfTerminals = Integer.MIN_VALUE;
        List<String> airports = new ArrayList<>();
        for(String airport: airportDb.keySet()){

            if(airportDb.get(airport).getNoOfTerminals() > largestNoOfTerminals){

                largestNoOfTerminals = airportDb.get(airport).getNoOfTerminals();

            }
        }

        for(String airport: airportDb.keySet()){

            if(airportDb.get(airport).getNoOfTerminals() == largestNoOfTerminals){

                airports.add(airportDb.get(airport).getAirportName());

            }
        }

        Collections.sort(airports);

        return airports.get(0);

    }

    public String getAirportNameFromFlightId(Integer flightId){

        if(flightDb.containsKey(flightId)){

            return flightDb.get(flightId).getFromCity().toString();
        }

        return null;
    }

    public int calculateFlightFare(Integer flightId){

        if(flightDb.containsKey(flightId)){

            return flightCurrentFareDb.get(flightId);
        }
        return 0;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){

        double duration = Double.MAX_VALUE;
        for(Flight flight: flightDb.values()){

            if(flight.getFromCity() == fromCity && flight.getToCity() == toCity){

                if(flight.getDuration() < duration){
                    duration = flight.getDuration();
                }
            }
        }
        return duration;


    }

    public int getNumberOfPeopleOn(Date date, String airportName){


        List<Integer> flights = new ArrayList<>();
        for(Flight flight: flightDb.values()){

            if(flight.getFlightDate() == date){

                if(flight.getFromCity().equals(airportName) || flight.getToCity().equals(airportName)){

                    flights.add(flight.getFlightId());

                }

            }

        }

        int total_people = 0;

        for(Integer flightId: flights){

            total_people += flightBookingDb.get(flightId).size();

        }

        return total_people;
    }


}