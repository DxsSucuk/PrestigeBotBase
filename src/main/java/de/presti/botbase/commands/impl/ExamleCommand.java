package de.presti.botbase.commands.impl;

import de.presti.botbase.commands.Category;
import de.presti.botbase.commands.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ExamleCommand extends Command {

    public ExamleCommand() {
        super("example", "Just a example Command", Category.EXAMPLE);
    }

    @Override
    public void onPerform(Member sender, Message messageSelf, String[] args, TextChannel m) {
        messageSelf.delete().queue();
        m.sendMessage("Example").queue();
        m.sendMessage("Send by " + sender.getNickname() + ", " + args[0]).queue();
    }
}
