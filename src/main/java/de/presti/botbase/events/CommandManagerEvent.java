package de.presti.botbase.events;

import de.presti.botbase.bot.BotInfo;
import de.presti.botbase.bot.BotState;
import de.presti.botbase.bot.BotUtil;
import de.presti.botbase.main.Main;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class CommandManagerEvent extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        BotInfo.state = BotState.STARTED;
        System.out.println("Boot up finished!");

        Main.instance.createCheckerThread();

        BotUtil.setActivity(BotInfo.botInstance.getGuilds().size() + " Guilds", Activity.ActivityType.WATCHING);
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {

        if(event.getAuthor().isBot())
            return;
        if(!Main.cm.perform(event.getMember(), event.getMessage().getContentRaw(), event.getMessage(), event.getChannel())) {

            if (!event.getMessage().getMentionedUsers().isEmpty() && event.getMessage().getMentionedUsers().contains(BotInfo.botInstance.getSelfUser())) {
                event.getChannel().sendMessage("Usage ree!help").queue();
            }
        }
    }

}
