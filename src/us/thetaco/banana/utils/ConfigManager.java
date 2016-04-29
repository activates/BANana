package us.thetaco.banana.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import us.thetaco.banana.Banana;

public class ConfigManager {
	
	private Banana plugin;
	public ConfigManager(Banana plugin) {
		this.plugin = plugin;
	}
	
	public void initializeConfig() {
		
		File configLocation = new File(plugin.getDataFolder().getPath() + "/config.yml");
		File configDirectory = new File(plugin.getDataFolder().getPath());
		
		
		try {
			
			// checking if the config exists, and notifying the console that a new config file is going to be created
		
			if (!configLocation.exists()) {
				
				SimpleLogger.logMessage("No config file found, so one is being generated");
				
				// create the directories, etc
				configDirectory.mkdir();
				configLocation.createNewFile();
				
			} else {
				// stopping here because.. There is no need to create a new config if one already exists
				
				// just loading the values
				this.loadValues(configLocation);
				
				return;
			}
			
			/*
		 	* Since we want to "customize" the config file, we will be writing it ourselves, then loading the values
		 	*/
			
			FileWriter fWriter = new FileWriter(configLocation);
			BufferedWriter writer = new BufferedWriter(fWriter);
			
			
			// now.. literally building the config file!
			
			writer.write("#                 )       \\   /      (");								writer.newLine();
			writer.write("#                /|\\      )\\_/(     /|\\");								writer.newLine();
			writer.write("#*              / | \\    (/\\|/\\)   / | \\                   *");	writer.newLine();
			writer.write("#|`.___________/__|__o____\\`|'/___o__|__\\________________.'|");		writer.newLine();
			writer.write("#|                  '^`    \\|/   '^`                       |");		writer.newLine();
			writer.write("#|                                                         |");		writer.newLine();
			writer.write("#|     This is an autogenerated config file for the        |");		writer.newLine();
			writer.write("#|     BANana plugin! After editing the values in this     |");		writer.newLine();
			writer.write("#| file to your liking, be sure to restart your server to  |");		writer.newLine();
			writer.write("#|   finalize the changes you made. (Do not use /reload)   |");		writer.newLine();
			writer.write("#|                                                         |");		writer.newLine();
			writer.write("#| ._____________________________________________________. |");		writer.newLine();
			writer.write("#|'      l    /\\ /     \\\\            \\ /\\   l             `|");	writer.newLine();
			writer.write("#*       l  /   V       ))            V   \\ l              *");		writer.newLine();
			writer.write("#        l/            //                  \\I");							writer.newLine();
			writer.write("#                      V");												writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("############################################################"); writer.newLine();
			writer.write("#                     !!! IMPORTANT !!!                    #"); writer.newLine();
			writer.write("#  If you plan on running multiple copies of BANana on one #"); writer.newLine();
			writer.write("#  database, then you need to set the servername for each  #"); writer.newLine();
			writer.write("# server. Also, make sure you put in the correct con. info #"); writer.newLine();
			writer.write("############################################################"); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("Database:"); writer.newLine();
			writer.write("    use-mysql: " + false); writer.newLine();
			writer.write("    mysql-address: 127.0.0.1"); writer.newLine();
			writer.write("    mysql-port: 3307"); writer.newLine();
			writer.write("    mysql-database-name: banana"); writer.newLine();
			writer.write("    mysql-username: root"); writer.newLine();
			writer.write("    mysql-password: tunafish"); writer.newLine();
			writer.write("# Make sure you server name is unique from the others!"); writer.newLine();
			writer.write("    server-name: main"); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("############################################################"); writer.newLine();
			writer.write("#  This is where you enable/disable announcements to the   #"); writer.newLine();
			writer.write("# entire server! Whichever you enable below, players with  #"); writer.newLine();
			writer.write("#  banana.announcements.receive will see the messages in   #"); writer.newLine();
			writer.write("#                         the chat!                        #"); writer.newLine();
			writer.write("############################################################"); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("Announcements:"); writer.newLine();
			writer.write("    ban: " + true); writer.newLine();
			writer.write("    banip: " + true); writer.newLine();
			writer.write("    kick: " + true); writer.newLine();
			writer.write("    mute: " + true); writer.newLine();
			writer.write("    tempban: " + true); writer.newLine();
			writer.write("    tempbanip: " + true); writer.newLine();
			writer.write("    tempmute: " + true); writer.newLine();
			writer.write("    unban: " + true); writer.newLine();
			writer.write("    unbanip: " + true); writer.newLine();
			writer.write("    unmute: " + true); writer.newLine();
			writer.write("    warning: " + true); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("############################################################"); writer.newLine();
			writer.write("#  Next is if you want the players to be notified whenever #"); writer.newLine();
			writer.write("# a certain action is taken against them e.g. mute warning #"); writer.newLine();
			writer.write("############################################################"); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("Notifications:"); writer.newLine();
			writer.write("    mute: " + true); writer.newLine();
			writer.write("    unmute: " + true); writer.newLine();
			writer.write("    tempmute: " + true); writer.newLine();
			writer.write("    warning: " + true); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("############################################################"); writer.newLine();
			writer.write("#  Next is how many seconds you want inbetween each mute   #"); writer.newLine();
			writer.write("#          check loop. The recommended value is 5          #"); writer.newLine();
			writer.write("############################################################"); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("mute-check-loop-time: " + 5); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("############################################################"); writer.newLine();
			writer.write("#  This next part is setting what is done when a player is #"); writer.newLine();
			writer.write("# being warned. If they reach the specified warning amount #"); writer.newLine();
			writer.write("# then the action listed below will be taken. The possible #"); writer.newLine();
			writer.write("#   actions are: KICK, BAN, MUTE, TEMPBAN 0:0:0:0, BANIP,  #"); writer.newLine();
			writer.write("# TEMPBANIP 0:0:0:0, TEMPMUTE 0:0:0:0, NOTHING and COMMAND #"); writer.newLine();
			writer.write("############################################################"); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("Warning-actions:"); writer.newLine();
			writer.write("    enabled: " + true); writer.newLine();
			writer.write("    reset-at: " + 3); writer.newLine();
			writer.write("    actions:"); writer.newLine();
			writer.write("        1: 'NOTHING'"); writer.newLine();
			writer.write("        2: 'KICK'"); writer.newLine();
			writer.write("        3: 'TEMPBAN 0:10:0'"); writer.newLine();
			writer.write("        4: 'BAN'"); writer.newLine();
			writer.write("        # Add as many numbers as you want! Just make sure to use spaces:"); writer.newLine();
			writer.write("        #5: COMMAND smite {PLAYER} <--- how to use commands "); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("############################################################"); writer.newLine();
			writer.write("#  This next part is setting what commands are disallowed  #"); writer.newLine();
			writer.write("#    when a player is muted! To completely disable this    #"); writer.newLine();
			writer.write("#         option, set the enabled option to false          #"); writer.newLine();
			writer.write("############################################################"); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("Command-muting:"); writer.newLine();
			writer.write("    enabled: " + true); writer.newLine();
			writer.write("    commands:"); writer.newLine();
			writer.write("      - msg"); writer.newLine();
			writer.write("      - me"); writer.newLine();
			writer.write("      - t"); writer.newLine();
			writer.write("      - tell"); writer.newLine();
			writer.write("      - mail"); writer.newLine();
			writer.write("      - r"); writer.newLine();
			writer.write("      - w"); writer.newLine();
			writer.write("Muting:"); writer.newLine();
			writer.write("    Notify-player-on-chat: " + true);
			
			writer.newLine(); // new line
			
			writer.write("############################################################"); writer.newLine();
			writer.write("#    Next is the settings IP bans. Here you can set if a   #"); writer.newLine();
			writer.write("# the playername attatched to an IP is banned alongside it #"); writer.newLine();
			writer.write("#  If you set the ban-joining-ip to true, it will auto-ban #"); writer.newLine();
			writer.write("#     the ip of any players who try to join while banned   #"); writer.newLine();
			writer.write("############################################################"); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("IP-bans:"); writer.newLine();
			writer.write("    ban-username-of-ip: " + true); writer.newLine();
			writer.write("    announce-ban-of-usernames: " + true); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("############################################################"); writer.newLine();
			writer.write("# The next option is used to determine how messages should #"); writer.newLine();
			writer.write("#                       be formatted.                      #"); writer.newLine();
			writer.write("############################################################"); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("Formatting:"); writer.newLine();
			writer.write("    bungee-cord: " + false); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("############################################################"); writer.newLine();
			writer.write("#  The next option is used to determine if joining banned  #"); writer.newLine();
			writer.write("#      player should have their new information banned     #"); writer.newLine();
			writer.write("############################################################"); writer.newLine();
			
			writer.newLine(); // new line
			
			writer.write("Info-banning:"); writer.newLine();
			writer.write("    ban-joining-ip: " + true); writer.newLine();
			writer.write("    ip-ban-joining-player: " + true); writer.newLine();
			
			// closing the writer
			writer.close();
			fWriter.close();
			
			// loading the values
			this.loadValues(configLocation);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			SimpleLogger.logMessage("There was an error while generating a new config file. Reason: " + e.getMessage());
			
		}
		
	}
	
	private void loadValues(File toLoad) {
		
		FileConfiguration config = plugin.getConfig();
		
		try {
			
			// load our "custom" config file
			config.load(toLoad);
			
			Values.USE_MYSQL = config.getBoolean("Database.use-mysql", false);
			Values.MYSQL_ADDRESS = config.getString("Database.mysql-address", "127.0.0.1");
			Values.MYSQL_PORT = config.getInt("Database.mysql-port", 3307);
			Values.MYSQL_DATABASE_NAME = config.getString("Database.mysql-database-name", "banana");
			Values.MYSQL_DATABASE_USERNAME = config.getString("Database.mysql-username", "root");
			Values.MYSQL_DATABASE_PASSWORD = config.getString("Database.mysql-password", "tunafish");
			Values.SERVER_NAME = config.getString("Database.server-name", "main");
			
			// cleaning up the server_name variable for database use
			Values.SERVER_NAME = Values.SERVER_NAME.replace(" ", "").replace("'", "").replace("\"", "");
			
			Values.ANNOUNCE_BAN = config.getBoolean("Announcements.ban", true);
			Values.ANNOUNCE_BANIP = config.getBoolean("Announcements.banip", true);
			Values.ANNOUNCE_KICK = config.getBoolean("Announcements.kick", true);
			Values.ANNOUNCE_KICK = config.getBoolean("Announcements.kick", true);
			Values.ANNOUNCE_TEMPBAN = config.getBoolean("Announcements.tempban", true);
			Values.ANNOUNCE_TEMPBANIP = config.getBoolean("Announcements.tempbanip", true);
			Values.ANNOUNCE_TEMPMUTE = config.getBoolean("Announcements.tempmute", true);
			Values.ANNOUNCE_UNBAN = config.getBoolean("Announcements.unban", true);
			Values.ANNOUNCE_UNBANIP = config.getBoolean("Announcements.unbanip", true);
			Values.ANNOUNCE_UNMUTE = config.getBoolean("Announcements.unmute", true);
			Values.ANNOUNCE_WARNING = config.getBoolean("Announcements.warning", true);
			
			Values.NOTIFY_TEMPMUTE = config.getBoolean("Notifications.tempmute", true);
			Values.NOTIFY_UNMUTE = config.getBoolean("Notifications.unmute", true);
			Values.NOTIFY_WARNING = config.getBoolean("Notifications.warning", true);
			Values.NOTIFY_MUTE = config.getBoolean("Notifications.mute", true);
			
			Values.TAKE_WARNING_ACTION = config.getBoolean("Warning-actions.enabled", true);
			
			Set<String> warningAmounts = config.getConfigurationSection("Warning-actions.actions").getKeys(false);
			
			Map<Integer, String> actions = new HashMap<Integer, String>();
			
			for (String s : warningAmounts) {
				
				actions.put(Integer.parseInt(s), config.getString("Warning-actions.actions." + s));
				
			}
			
			Values.WARNING_ACTIONS = actions;
			
			Values.MUTING_COMMAND_ENABLED = config.getBoolean("Command-muting.enabled", true);
			Values.MUTING_COMMANDS = config.getStringList("Command-muting.commands");
			Values.MUTE_NOTIFY_ON_CHAT = config.getBoolean("Muting.Notify-player-on-chat", true);
			
			Values.BAN_USERNAME_OF_IP = config.getBoolean("IP-bans.ban-username-of-ip", true);
			Values.ANNOUNCE_BAN_USERNAME_OF_IP = config.getBoolean("IP-bans.announce-ban-of-usernames", true);
			
			Values.BUNGEE_CORD_FORMAT = config.getBoolean("Formatting.bungee-cord", false);
			
			Values.BAN_JOINING_IP = config.getBoolean("Info-banning.ban-joining-ip", true);
			Values.BAN_JOINING_PLAYER = config.getBoolean("Info-banning.ip-ban-joining-player", true);
			
		} catch (Exception e) {

			e.printStackTrace();
			SimpleLogger.logMessage("There was an error in loading the configuration file! Reason: " + e.getMessage());
			SimpleLogger.logMessage("Using default values!");
			
		}
				
	}
	
	public void reloadValues() {
		
		File configLocation = new File(plugin.getDataFolder().getPath() + "/config.yml");
		
		// reloading the config
		plugin.reloadConfig();
		
		// loading the values
		this.loadValues(configLocation);
		
	}
}
