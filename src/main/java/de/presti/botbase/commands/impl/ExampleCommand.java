package de.presti.botbase.commands.impl;

import de.presti.botbase.commands.Category;
import de.presti.botbase.commands.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ExampleCommand extends Command {

    public ExampleCommand() {
        super("example", "Just a example Command", Category.EXAMPLE);
    }

    @Override
    public void onPerform(Member sender, Message messageSelf, String[] args, TextChannel m) {
        messageSelf.delete().queue();
        sendMessage("Example", m);
        sendMessage("Send by " + sender.getNickname() + ", " + args[0], 5, m);
    }
}
