package uk.ac.aber.cs21120.wedding.solution;
import uk.ac.aber.cs21120.wedding.interfaces.IPlan;
import uk.ac.aber.cs21120.wedding.interfaces.IRules;
import uk.ac.aber.cs21120.wedding.interfaces.ISolver;

import java.util.Set;

public class Solver implements ISolver {
    String[] theGuests; //create an array of Strings for the guests
    IPlan thePlan; //create an IPlan for the plan
    IRules theRules; //create an IRules for the rules
    public Solver(String[] guests, IPlan plan, IRules rules) { //constructor as described in the assignment brief
        theGuests = guests; //store the guests
        thePlan = plan; //store the plan
        theRules = rules; //store the rules
    }
    @Override
    public boolean solve() {
        for(int i = 0; i < thePlan.getNumberOfTables(); i++) { //for each table
            Set<String> tempGuests = thePlan.getGuestsAtTable(i);
            int unfilledSeats = thePlan.getSeatsPerTable() - tempGuests.size();
            for(int j = 0; j < unfilledSeats; j++) { //for each unfilled seat at the table
                for(String theGuest : theGuests) { //for each guest in the guest list
                    if(!thePlan.isGuestPlaced(theGuest)) { //if the guest is not placed at any table
                        thePlan.addGuestToTable(i, theGuest); //add the guest to the current table
                        if(theRules.isPlanOK(thePlan)) { //if the resulting seating plan is valid
                            boolean result = solve(); //try to solve the plan with the guest added
                            if(result) { //if the result is true
                                return true; //plan solved, return true
                            }
                        }
                        thePlan.removeGuestFromTable(theGuest); //plan not solved, remove the guest
                    }
                }
                return false; //a guest couldn't fit into this seat
            }
        }
        return true; //all seats filled, plan solved
    }
}
