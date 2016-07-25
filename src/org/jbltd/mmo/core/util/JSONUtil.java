package org.jbltd.mmo.core.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.jbltd.mmo.guilds.Guild;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONUtil
{

	@SuppressWarnings("unchecked")
	public static void readGuildsFile()
	{
		  JSONParser parser = new JSONParser();
		  
	        try {
	 
	            Object obj = parser.parse(new FileReader(Bukkit.getServer().getWorldContainer().getAbsolutePath()+"guilds.json"));
	 
	            JSONObject jsonObject = (JSONObject) obj;
	 
	            String name = (String) jsonObject.get("guildName");
	            String author = (String) jsonObject.get("guildLeader");
	            String description = (String) jsonObject.get("guildDescription");
	            String tag = (String) jsonObject.get("guildTag");
	            List<UUID> members = new ArrayList<UUID>();
	            
	            JSONArray memberList = (JSONArray) jsonObject.get("guildMembers");
	 
	            Iterator<String> iterator = memberList.iterator();
	            while (iterator.hasNext()) {
	            	UUID u = UUID.fromString((String) memberList.iterator().next());
	            	members.add(u);
	            }
	 
	            Guild g = new Guild(name, description, tag, Bukkit.getPlayer(author), members);
	            Guild.allGuilds.add(g);
	            System.out.println(F.info("Guilds", true, "Loaded guild "+g.getGuildName()));
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	
	@SuppressWarnings("unchecked")
	public static void writeToGuildsfile(String guildName, String description, String tag, String leaderUID, List<UUID> members)
	{
		
		JSONObject obj = new JSONObject();
		obj.put("guildName", guildName);
		obj.put("guildLeader", leaderUID);
		obj.put("guildTag", tag);
		obj.put("guildDescription", description);
 
		JSONArray gmembers = new JSONArray();
		
		members.forEach(uuid -> gmembers.add(uuid.toString()));
		
		obj.put("guildMembers", members);
 
		// try-with-resources statement based on post comment below :)
		try (FileWriter file = new FileWriter(Bukkit.getServer().getWorldContainer().getAbsolutePath()+"guilds.json")) {
			file.write(obj.toJSONString());
		}
		catch(Exception e)
		{
			
		}
		
	}
	
}
