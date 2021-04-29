package de.presti.botbase.commands;

import de.presti.botbase.commands.impl.ExampleCommand;
import de.presti.botbase.utils.ArrayUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class CommandManager {

    static String prefix = "yourprefix";

    static ArrayList<Command> cmds = new ArrayList<>();

    public CommandManager() {

        addCommand(new ExampleCommand());

    }

    public void addCommand(Command c) {
        if (!cmds.contains(c)) {
            cmds.add(c);
        }
    }

    public boolean perform(Member sender, String msg, Message messageSelf, TextChannel m) {

        if (!msg.toLowerCase().startsWith(prefix))
            return false;

        if(ArrayUtil.commandcooldown.contains(sender.getUser().getId())) {
            sendMessage("You are on Cooldown!", 5, m);
            messageSelf.delete().queue();
            return false;
        }

        msg = msg.substring(prefix.length());

        String[] oldargs = msg.split(" ");

        for (Command cmd : getCommands()) {
            if (cmd.getCmd().equalsIgnoreCase(oldargs[0]) || cmd.isAlias(oldargs[0])) {
                String[] args = Arrays.copyOfRange(oldargs, 1, oldargs.length);
                cmd.onPerform(sender, messageSelf, args, m);
                new Thread(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }

                    if (ArrayUtil.commandcooldown.contains(sender.getUser().getId())) {
                        ArrayUtil.commandcooldown.remove(sender.getUser().getId());
                    }

                    Thread.currentThread().interrupt();

                }).start();

                if (!ArrayUtil.commandcooldown.contains(sender.getUser().getId())) {
                    ArrayUtil.commandcooldown.add(sender.getUser().getId());
                }
                return true;
            }
        }

        sendMessage("The Command " + oldargs[0] + " couldn't be found!", 5, m);

        return false;
    }

    public void removeCommand(Command c) {
        if (cmds.contains(c)) {
            cmds.remove(c);
        }
    }

    public ArrayList<Command> getCommands() {
        return cmds;
    }

    public static void sendMessage(String msg, MessageChannel m) {
        m.sendMessage(msg).queue();
    }

    public static void sendMessage(String msg, int deletesecond, MessageChannel m) {
        m.sendMessage(msg).delay(deletesecond, TimeUnit.SECONDS).flatMap(Message::delete).queue();
    }

    public static void sendMessage(EmbedBuilder msg, MessageChannel m) {
        m.sendMessage(msg.build()).queue();
    }

    public static void sendMessage(EmbedBuilder msg, int deletesecond, MessageChannel m) {
        m.sendMessage(msg.build()).delay(deletesecond, TimeUnit.SECONDS).flatMap(Message::delete).queue();
    }

}