package com.earth2me.essentials.commands;

import java.util.List;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.earth2me.essentials.Essentials;
import org.bukkit.entity.Player;
import com.earth2me.essentials.User;


public abstract class EssentialsCommand implements IEssentialsCommand
{
	private final String name;

	protected EssentialsCommand(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public String[] getTriggers()
	{
		return new String[]
				{
					getName()
				};
	}

	protected User getPlayer(Server server, String[] args, int pos) throws IndexOutOfBoundsException, NoSuchFieldException
	{
		if (args.length <= pos) throw new IndexOutOfBoundsException("§cInvalid command syntax. Did you forget an argument?");
		List<Player> matches = server.matchPlayer(args[pos]);
		if (matches.size() < 1) throw new NoSuchFieldException("§cNo matching players could be found.");
		return User.get(matches.get(0));
	}

	@Override
	public final void run(Server server, Essentials parent, User user, String commandLabel, Command cmd, String[] args) throws Exception
	{
		run(server, parent, user, commandLabel, args);
	}

	protected void run(Server server, Essentials parent, User user, String commandLabel, String[] args) throws Exception
	{
		run(server, parent, (CommandSender)user.getBase(), commandLabel, args);
	}

	@Override
	public final void run(Server server, Essentials parent, CommandSender sender, String commandLabel, Command cmd, String[] args) throws Exception
	{
		run(server, parent, sender, commandLabel, args);
	}

	protected void run(Server server, Essentials parent, CommandSender sender, String commandLabel, String[] args) throws Exception
	{
		throw new Exception("Only in-game players can use " + commandLabel + ".");
	}

	public static String getFinalArg(String[] args, int start)
	{
		StringBuilder bldr = new StringBuilder();
		for (int i = start; i < args.length; i++)
		{
			if (i != start) bldr.append(" ");
			bldr.append(args[i]);
		}
		return bldr.toString();
	}

	protected void charge(CommandSender sender) throws Exception
	{
		if (sender instanceof Player)
			User.get((Player)sender).charge(this);
	}
}
