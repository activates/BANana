package us.thetaco.banana.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import info.dyndns.thetaco.uuid.api.Main;
import us.thetaco.banana.Banana;
import us.thetaco.banana.sql.DatabaseManager.BannerType;
import us.thetaco.banana.utils.Action;
import us.thetaco.banana.utils.CommandType;
import us.thetaco.banana.utils.Lang;
import us.thetaco.banana.utils.Values;

public class BanIPCommandConsole {

	public boolean runBanIPCommand(CommandSender sender, String[] args) {
		
		if (args.length < 1) {
			sender.sendMessage(Lang.BAN_IP_INCORRECT_ARGS.toString());
			return true;
		}
		
		Player target = Bukkit.getPlayer(args[0]);
		
		if (target != null) {
			
			if (target.hasPermission("banana.immune.banip")) {
				sender.sendMessage(Lang.CANNOT_BE_IP_BANNED.parseName(target.getName()));
				return true;
			}
			
		}
		
		String uuid = null;
		
		if (target == null) {
			
			Main main = new Main();
			
			uuid = main.getPlayer(args[0]).getUUID();
			
		} else {
			
			uuid = target.getUniqueId().toString();
			
		}
		
		String address = null;
		boolean ipGiven = false;
		
		if (uuid == null) {
			
			sender.sendMessage(Lang.USING_IP.toString());
			
			address = args[0];
			
			ipGiven = true;
			
		} else {
			
			String tempAddress = Banana.getPlayerCache().getAddress(uuid);
			
			if (tempAddress == null) {
				sender.sendMessage(Lang.NO_IP_STORED.toString());
				return true;
			}
			
			address = tempAddress;
			
		}
		
		// If the IP is set to be given, then use that string
		String nameToUse = new String();
		
		if (ipGiven) {
			nameToUse = address;
		} else {
			nameToUse = (new Main()).getLatestName(uuid);
		}
		
		
		// check to see if the IP is already banned
		if (Banana.getBanCache().isIPBanned(address)) {
			
			if (Banana.getBanCache().isIPTempBanned(address)) {
				
				sender.sendMessage(Lang.ALREADY_TEMP_BANNED.parseName(nameToUse));
				
			} else {
				
				sender.sendMessage(Lang.ALREADY_BANNED.parseName(nameToUse));
				
			}
			
			return true;
		}
		
		String message = Lang.DEFAULT_BAN_MESSAGE.toString();
		
		if (args.length > 1) {
			
			message = args[1];
			
			int i = 0;
			for (String s : args) {
				
				if (i > 1) {
					
					message += " " + s;
					
				}
				i++;
			}
			
		}
		
		Banana.getBanCache().addBannedIP(address, message, BannerType.CONSOLE, null);
		Banana.getDatabaseManager().asyncBanIP(address, message, BannerType.CONSOLE, null, false, null, null);
		
		if (target != null) {
			
			target.kickPlayer(Lang.BAN_IP_FORMAT.parseBanFormat((new Main()).getLatestName(uuid), message));
			
		}
		
		if (!ipGiven) {
			sender.sendMessage(Lang.IP_BAN_SUCCESS.parseName((new Main()).getLatestName(uuid)));
		} else {
			sender.sendMessage(Lang.IP_BAN_SUCCESS.parseName(address));
		}
		
		Banana.getDatabaseManager().logCommand(CommandType.BAN_IP, null, args, true);
		
		// check if announcements are enabled for this command.. then release the annoucnement
		if (Values.ANNOUNCE_BANIP) {
							
			if (!ipGiven) {
				Action.broadcastMessage(Action.BANIP, Lang.IP_BAN_BROADCAST.parseWarningBroadcast(Lang.CONSOLE_NAME.toString(), (new Main()).getLatestName(uuid), message));
			} else {
				Action.broadcastMessage(Action.BANIP, Lang.IP_BAN_BROADCAST.parseWarningBroadcast(Lang.CONSOLE_NAME.toString(), address, message));
			}
			
		}
		
		// checking to make sure that username's attatched to ips is also enabled
		if (Values.BAN_USERNAME_OF_IP) {
			
			
			// get all usernames attatched to the IP
			List<String> playersWithIP = Banana.getPlayerCache().getPlayersWithIP(address);
			
			if (playersWithIP == null) return true;
			
			// loop through the list and ban all players who are attatched to that certain IP
			for (String s : playersWithIP) {
								
				// making sure the player isn't already banned
				if (!Banana.getBanCache().isUUIDBanned(s)) {
										
					// going ahead and banning them with the appropriate message
					Banana.getBanCache().addBannedUUID(s, Lang.PLAYER_IP_AUTOBANNED.parseBanFormat(null, message), BannerType.AUTOBANNED, null);
					Banana.getDatabaseManager().asyncAddBan(s, BannerType.AUTOBANNED, null, Lang.PLAYER_IP_AUTOBANNED.parseBanFormat(null, message), false, null, null);
					
					// check if the player is online and kick them if they are
					Player duplicate = Bukkit.getPlayer(UUID.fromString(s));
					
					if (duplicate != null) {
						duplicate.kickPlayer(Lang.PLAYER_IP_AUTOBANNED.parseBanFormat(null, message));
					}
					
					// check if announcements are want for this type of ban
					if (Values.ANNOUNCE_BAN_USERNAME_OF_IP) {
						
						// check if announcements are enabled for this command.. then release the annoucnement
						if (Values.ANNOUNCE_BANIP) {
								
							try {
							
								Action.broadcastMessage(Action.BAN, Lang.AUTOBAN_BROADCAST.parseName((new Main()).getLatestName(uuid)));
							
							} catch (Exception e) {
								// do nothing
							}
						}
						
					}
					
				}
				
			}
			
		}
		
		return true;
		
	}
	
}
