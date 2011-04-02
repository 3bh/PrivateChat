package com.imma.bukkit.PChat;

import java.util.ArrayList;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class PChat extends JavaPlugin{
	public final PCPL pListen = new PCPL(this);
	public ArrayList<String[]> chats = new ArrayList<String[]>();
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		System.out.println("PrivateChat 0.1.1 enabled.");
		
		PluginManager pM = this.getServer().getPluginManager();
		pM.registerEvent(Type.PLAYER_CHAT, pListen, Event.Priority.Normal, this);
		pM.registerEvent(Type.PLAYER_COMMAND_PREPROCESS, pListen, Event.Priority.Normal, this);
		
	}
	
	public String[] searchChat(String username){
		
		for ( int i = 0; i < chats.size(); i++){
			
			if ( chats.get(i)[0] == username ) return chats.get(i);
			
		}
		
		return null;
		
		
	}
	
	public ArrayList<String> getChatUsers (String chatname){
		ArrayList<String> cusers = new ArrayList<String>();
		for ( int i = 0; i < chats.size(); i++){
			
			if ( chats.get(i)[1].equalsIgnoreCase(chatname) )cusers.add(chats.get(i)[0]);
			
			
		}
		
		return cusers;
		
	}
	public int delUserFromChat(String username){
		
		String[] chat = this.searchChat(username);
		if ( chat == null) return -1; // user has no chat.
		
		ArrayList<String[]> newChat = new ArrayList<String[]>();
		
		for ( int i = 0; i < chats.size(); i++){
			
			if ( chats.get(i)[0] != username ) newChat.add(chats.get(i));
			
		}
		chats = newChat;
		return 1;
		
	}
	
	public boolean chatExists(String chatname){
		
		for ( int i = 0; i < chats.size(); i++){
			
			if ( chats.get(i)[1].equalsIgnoreCase(chatname) ) return true;
			
		}
		return false;
		
	}
	
	
}