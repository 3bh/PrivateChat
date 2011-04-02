package com.imma.bukkit.PChat;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class PCPL extends PlayerListener{
	
	public final PChat plugin;
	public PCPL(final PChat plugin){
		
		this.plugin = plugin;
		
	}
	
	@Override
	public void onPlayerChat(PlayerChatEvent evt){
		
		Player actor = evt.getPlayer();
		String message = evt.getMessage();
		
		String[] currentChat = plugin.searchChat(actor.getName());
		if ( currentChat == null ) return;
		
		evt.setCancelled(true);
		evt.setMessage("");
		
		ArrayList<String> users = plugin.getChatUsers(currentChat[1]);
		Player[] serverPlayers = plugin.getServer().getOnlinePlayers();
		
		for (int i = 0; i < serverPlayers.length; i++){
			
			for ( int j = 0; j < users.size(); j++){
				
				if ( serverPlayers[i].getName() == users.get(j) )
					serverPlayers[i].sendMessage(ChatColor.WHITE + "[PC." + ChatColor.DARK_AQUA + actor.getDisplayName() + ChatColor.WHITE +  "]: " + message);
				
			}
			
		}
		
	}
	
	@Override 
	public void onPlayerCommandPreprocess(PlayerChatEvent evt){
		Player actor = evt.getPlayer();
		String[] msg = evt.getMessage().split(" ");
		
		if ( msg[0].equalsIgnoreCase("/pc")){
			
			if ( msg[1].equalsIgnoreCase("add") && msg.length == 3){
				
				String[] cChat = plugin.searchChat(actor.getName());
				if (cChat == null){
					
					if ( plugin.chatExists(msg[2])){
						
						actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.RED + "This chat already exists.");
						return;
						
					}
					String[] addc = { actor.getName(), msg[2] };
					plugin.chats.add(addc);
					
					actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.GREEN + "\"" + msg[2] + "\" created.");
					
				}
				else
					actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.RED + "You are already part of a chat. Leave it first :3");
				
			}
			else if ( msg[1].equalsIgnoreCase("leave") && msg.length == 2){
				
				int suc = plugin.delUserFromChat(actor.getName());
				if ( suc == -1) actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.RED + "You are not part of a chat.");
				else actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.GREEN + "You left your chat.");
				
			}
			else if ( msg[1].equalsIgnoreCase("join") && msg.length == 3){
				
				if ( plugin.searchChat(actor.getName()) != null ){
					
					actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.RED + "You are already part of a chat.");
					return;
					
				}
				if ( plugin.chatExists(msg[2])){
					
					String[] addc = { actor.getName(), msg[2] };
					plugin.chats.add(addc);
					actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.GREEN + "You joined \"" + msg[2] + "\".");
					
				}
				else actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.RED + "This chat does not exist.");
				
			}
			else if ( msg[1].equalsIgnoreCase("help") && msg.length == 2){
				
				actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.DARK_GREEN + "PChat Help:");
				actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.RED + " /pc add <ChatName> - Create new chat");
				actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.RED + " /pc leave - Leave chat");
				actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.RED + " /pc join <ChatName> - Join an existing chat");
				actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.RED + " /pc help - Show this help message");
				actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.RED + " /pc send <Message> - Send message to main chat");
				
			}
			else if ( msg[1].equalsIgnoreCase("send") && msg.length == 3){
				
				Player[] allPlayers = plugin.getServer().getOnlinePlayers();
				
				for ( int i = 0; i < allPlayers.length; i++){
					
					allPlayers[i].sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_AQUA + "PC" + ChatColor.WHITE + "]" + ChatColor.DARK_AQUA + actor.getDisplayName() + ": " + ChatColor.WHITE + msg[2]);
					
				}
				
			}
			else{
				
				actor.sendMessage(ChatColor.AQUA + "[PChat]: " + ChatColor.RED + "Command not found. Type /pc help for help.");
				
			}
			
		}
		
	}
	
}
