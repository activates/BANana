package us.thetaco.banana.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCache {

	private Map<String, String> address = new HashMap<String, String>();
	private List<String> staff = new ArrayList<String>();
	private Map<String, String> latestName = new HashMap<String, String>();
	
	/** Adds an address to the player cache
	 * @param uuid The uuid to add the address for
	 * @param address The address of the specified uuid
	 */
	public void addAddress(String uuid, String address) {
		
		this.address.put(uuid, address);
		
	}
	
	/** Used to get a player's IP address if one exists
	 * @param uuid The UUID to check for
	 * @return Will return an address in string form. If nothing found
	 * will return null
	 */
	public String getAddress(String uuid) {
	
		return address.get(uuid);
		
	}
	
	/** Used to fetch all players with a particular IP address
	 * @param address The address to check for
	 * @return A list of all the UUIDs with the particular IP attached to it.
	 * Will return null if there are no IPs attatched to the particular IP
	 */
	public List<String> getPlayersWithIP(String address) {
		
		List<String> toReturn = new ArrayList<String>();
		
		for (String s : this.address.keySet()) {
			
			if (this.address.get(s).equalsIgnoreCase(address)) {
				toReturn.add(s);
			}
			
		}
		
		if (toReturn.isEmpty()) return null;
		
		return toReturn;
		
	}
	
	/** Used to add a staff member who won't be kicked in staff mode
	 * @param uuid The uuid to add
	 */
	public void addStaff(String uuid) {
		
		if (this.isStaff(uuid)) return;
		
		this.staff.add(uuid);
		
	}
	
	/** Used to remove a staff member who won't be kicked in staff mode
	 * @param uuid The uuid to remove
	 * @return Will return true if the UUID was successfully removed
	 */
	public boolean removeStaff(String uuid) {
		
		if (!this.isStaff(uuid)) return false;
		
		this.staff.remove(uuid);
		
		return true;
				
	}
	
	/** Used to determine if a player is staff of not
	 * @param uuid The uuid to check
	 * @return Will return true if the player is in the staff member list
	 */
	public boolean isStaff(String uuid) {
		
		for (String s : staff) {
			if (s.equals(uuid)) return true;
		}
		
			
		return false;
	}
	
	/** Used to fetch all of the current staff on a server
	 * @return A list of all the staff set by commands and the database. Will never return null
	 */
	public List<String> getStaff() {
		
		return this.staff;
		
	}
	
	/** Adds the latest name of a player
	 * @param uuid The uuid to tie the name to
	 * @param name The username to tie to the uuid
	 */
	public void addLatestName(String uuid, String name) {
		
		this.latestName.put(uuid, name);
		
	}
	
	/** Used to get the latest name of a player
	 * @param uuid The uuid to get the latest name for (because if you already have the username,
	 * then why would you need this fuction?!)
	 * @return The latest name (in string form) of the uuid given. Will return null if none was found
	 */
	public String getLatestName(String uuid) {
		
		return this.latestName.get(uuid);
		
	}
	
}
