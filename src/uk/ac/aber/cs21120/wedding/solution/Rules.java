package uk.ac.aber.cs21120.wedding.solution;
import uk.ac.aber.cs21120.wedding.interfaces.IPlan;
import uk.ac.aber.cs21120.wedding.interfaces.IRules;
import java.util.*;

public class Rules implements IRules {
    List<ArrayList<String>> friends = new ArrayList<>(); //create friends to store positive relationships
    List<ArrayList<String>> enemies = new ArrayList<>(); //create enemies to store negative relationships

    @Override
    public void addMustBeTogether(String a, String b) {
        ArrayList<String> tempFriends = new ArrayList<String>(); //create temporary ArrayList for friends
        tempFriends.add(a); //add guest a
        tempFriends.add(b); //add guest b
        friends.add(tempFriends); //add temporary ArrayList to global friends
    }

    @Override
    public void addMustBeApart(String a, String b) {
        ArrayList<String> tempEnemies = new ArrayList<String>(); //create temporary ArrayList for enemies
        tempEnemies.add(a); //add guest a
        tempEnemies.add(b); //add guest b
        enemies.add(tempEnemies); //add temporary ArrayList to global enemies
    }

    @Override
    public boolean isPlanOK(IPlan p) {
        for(int i = 0; i < p.getNumberOfTables(); i++) { //for each table
            Set<String> getGuests = p.getGuestsAtTable(i); //get the guests at table
            List<String> guests = new ArrayList<>(getGuests); //put guests into List of Strings
            ArrayList<String> tempFriends;
            ArrayList<String> tempEnemies;
            if(getGuests.isEmpty() || (friends.isEmpty() && enemies.isEmpty())) { //if (guests is empty) OR (friends AND enemies is empty), return true
                return true;
            } else {
                for(int j = 0; j < guests.size(); j++) { //for each guest at the table
                    if(!enemies.isEmpty()) { //if enemies is not empty
                        for(ArrayList<String> enemy : enemies) { //for each enemy in enemies
                            tempEnemies = enemy; //store the enemies in tempEnemies
                            for(String tempEnemy : tempEnemies) { //for each tempEnemy in tempEnemies
                                if(Objects.equals(guests.get(j), tempEnemy)) { //if an enemy is found on the table
                                    if(guests.contains(tempEnemies.get(0)) && guests.contains(tempEnemies.get(1))) { //if the table contains current guest and an enemy, return false
                                        return false; //an enemy is found at the table, rule is broken
                                    }
                                }
                            }
                        }

                    }
                    if(!friends.isEmpty() && (guests.size() == p.getSeatsPerTable())) { //if friends is not empty and the table is full
                        if(friends.size() > j) { //if size of friends is more than current table
                            tempFriends = friends.get(j); //store the friends in tempFriends
                            for (String tempFriend : tempFriends) { //for each tempFriend in tempFriends
                                if (!Objects.equals(guests.get(j), tempFriend)) { //if a friend is not found on the table
                                    if (guests.contains(tempFriends.get(0)) || guests.contains(tempFriends.get(1))) { //if the current guest OR the friend are on the table
                                        if (!guests.contains(tempFriend)) { //if guests does not contain the tempFriend
                                            return false; //a friend is not found at the table, rule is broken
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true; //all rules are met, return true
    }
}
