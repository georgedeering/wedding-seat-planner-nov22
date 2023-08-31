package uk.ac.aber.cs21120.wedding.solution;
import uk.ac.aber.cs21120.wedding.interfaces.IPlan;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Plan implements IPlan {
    int currentSeatsPerTable; //create integer for current seats per table
    int currentNumberOfTables; //create integer for current number of tables
    List<Set> tables; //create List of Sets to store tables
    public Plan(int numberOfTables, int seatsPerTable) { //constructor as described in the assignment brief
        currentNumberOfTables = numberOfTables; //store number of tables
        currentSeatsPerTable = seatsPerTable; //store seats per table
        tables = new ArrayList<>(currentNumberOfTables); //create number of tables
        for(int i = 0; i < currentNumberOfTables; i++) { //for each table
            tables.add(new HashSet<String>(currentSeatsPerTable)); //create the table with seats per table
        }
    }

    @Override
    public int getSeatsPerTable() {
        return currentSeatsPerTable; //return current seats per table
    }

    @Override
    public int getNumberOfTables() {
        return tables.size(); //return number of tables
    }

    @Override
    public void addGuestToTable(int table, String guest) throws IndexOutOfBoundsException {
        Set guests; //create a Set of guests
        boolean guestExists = false; //create loop flag if guest exists
        if(tables.size() > table) { //if number of tables is greater than table
            for(int i = 0; i < currentNumberOfTables; i++) { //for each table
                guests = tables.get(i); //get the guests at the table
                if(guests.contains(guest)) { //if the guest is on the table
                    guestExists = true; //the guest exists, set to true
                    break; //break loop, guest already exists and shouldn't be added
                }
            }
            if(!guestExists) { //if guest doesn't already exist at any table
                guests = tables.get(table); //get the guests at the table
                if(guests.size() < currentSeatsPerTable) { //if the table is not full
                    guests.add(guest); //add the guest to the table
                }
            }
        } else { //if trying to add to table that does not exist
            throw new IndexOutOfBoundsException(); //throw exception
        }

    }

    @Override
    public void removeGuestFromTable(String guest) {
        Set guests; //create a Set of guests
        for(int i = 0; i < currentNumberOfTables; i++) { //for each table
            guests = tables.get(i); //get the guests at the table
            if(guests.contains(guest)) { //if the guest is on the table
                guests.remove(guest); //remove the guest from the table
            }
        }
    }

    @Override
    public boolean isGuestPlaced(String guest) {
        Set guests; //create a Set of guests
        for(int i = 0; i < currentNumberOfTables; i++) { //for each table
            guests = tables.get(i); //get the guests at the table
            if(guests.contains(guest)) { //if the guest is on the table
                return true; //return true
            }
        }
        return false; //if the guest is not found, return false
    }

    @Override
    public Set<String> getGuestsAtTable(int t) {
        return tables.get(t); //return the guests at the table
    }


    //ACKNOWLEDGEMENT: toString() method taken from the assignment brief
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < getNumberOfTables(); i++) {
            Set<String> t = getGuestsAtTable(i);
            sb.append('(');
            List<String> list = new ArrayList<String>(t);
            sb.append(String.join(",", list));
            sb.append(") ");
        }
        return sb.toString();
    }
}
