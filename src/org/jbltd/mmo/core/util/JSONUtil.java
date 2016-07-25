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

import com.google.gson.JsonObject;

public class JSONUtil
{

	@SuppressWarnings("unchecked")
	public static void readGuildsFile()
	{
		JSONParser parser = new JSONParser();

		try
		{

			String path = Bukkit.getServer().getWorldContainer().getAbsolutePath() + "guilds.json";

			Object obj = parser.parse(new FileReader(path));
			System.out.println(F.info("Guilds", true, "Iniating guild setup... This may take a few moments."));

			JSONArray array = (JSONArray) obj;

			for (int i = 0; i < array.size(); i++)
			{

				JSONObject j = (JSONObject) array.get(i);
				String name = (String) j.get("guildName");
				String author = (String) j.get("guildLeader");
				String description = (String) j.get("guildDescription");
				String tag = (String) j.get("guildTag");
				List<UUID> members = new ArrayList<UUID>();

				JSONArray memberList = (JSONArray) j.get("guildMembers");

				for (int n = 0; n < memberList.size(); n++)
				{

					String next = (String) memberList.get(n);
					UUID u = UUID.fromString(next);
					members.add(u);

				}

				Guild g = new Guild(name, description, tag, Bukkit.getPlayer(author), members);
				Guild.allGuilds.add(g);
				System.out.println(F.info("Guilds", true, "Loaded guild " + g.getGuildName()));

			}

		} catch (Exception e)
		{}
	}

	@SuppressWarnings("unchecked")
	public static void writeToGuildsfile(String guildName, String description, String tag, String leaderUID, List<UUID> members, boolean noWipe)
	{

		System.out.println("Called");

		JSONParser parser = new JSONParser();

		try
		{
			String path = Bukkit.getServer().getWorldContainer().getAbsolutePath() + "guilds.json";

			Object object = parser.parse(new FileReader(path));

			JSONArray array = (JSONArray) object;

			JSONObject obj = new JSONObject();
			obj.put("guildName", guildName);
			obj.put("guildLeader", leaderUID);
			obj.put("guildTag", tag);
			obj.put("guildDescription", description);

			JSONArray gmembers = new JSONArray();

			members.forEach(uuid -> gmembers.add(uuid.toString()));

			obj.put("guildMembers", gmembers);

			array.add(obj);

			try (FileWriter file = new FileWriter(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "guilds.json", noWipe))
			{
				System.out.println(array.toJSONString());
				file.write(array.toJSONString());
				file.flush();
				file.close();
			} catch (Exception e)
			{

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
