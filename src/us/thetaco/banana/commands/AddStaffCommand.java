package us.thetaco.banana.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import info.dyndns.thetaco.uuid.api.Main;
import us.thetaco.banana.Banana;
import us.thetaco.banana.utils.Lang;

public class AddStaffCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			
			// This will run if the sender is not player
			return new AddStaffCommandConsole().runAddStaffCommand(sender, args);
			
		}
		
		// run this if the sender is a player
		Player player = (Player) sender;
		
		if (!player.hasPermission("banana.commands.addstaff")) {
			player.sendMessage(Lang.NO_PERMISSIONS.toString());
			return true;
		}
		
		if (args.length < 1) {
			player.sendMessage(Lang.ADD_STAFF_WRONG_ARGS.toString());
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
			player.sendMessage(Lang.ALREADY_STAFF.parseName((new Main()).getLatestName(uuid)));
			return true;
		}
		
		Banana.getPlayerCache().addStaff(uuid);
		Banana.getDatabaseManager().asyncAddStaffMember(uuid);
		
		player.sendMessage(Lang.ADD_STAFF_SUCCESS.parseName((new Main()).getLatestName(uuid)));
		
		return true;
	}

}
