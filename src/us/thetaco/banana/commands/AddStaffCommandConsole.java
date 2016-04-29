package us.thetaco.banana.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import info.dyndns.thetaco.uuid.api.Main;
import us.thetaco.banana.Banana;
import us.thetaco.banana.utils.Lang;

public class AddStaffCommandConsole {

	public boolean runAddStaffCommand(CommandSender sender, String[] args) {
		
		if (args.length < 1) {
			sender.sendMessage(Lang.ADD_STAFF_WRONG_ARGS.toString());
			return true;
		}
		
		Player target = Bukkit.getPlayer(args[0]);
		
		String uuid = null;
		
		if (target == null) {
			
			uuid = (new Main()).getPlayer(args[0]).getUUID();
			
		} else {
			
			uuid = target.getUniqueId().toString();
			
		}
		
		if (uuid == null) {
			Lang.PLAYER_NEVER_ONLINE.parseObject(args[0]);
			return true;
		}
		
		if (Banana.getPlayerCache().isStaff(uuid)) {
			sender.sendMessage(Lang.ALREADY_STAFF.parseName((new Main()).getLatestName(uuid)));
			return true;
		}
		
		Banana.getPlayerCache().addStaff(uuid);
		Banana.getDatabaseManager().asyncAddStaffMember(uuid);
		
		sender.sendMessage(Lang.ADD_STAFF_SUCCESS.parseName((new Main()).getLatestName(uuid)));
		
		return true;
		
	}
	
}
