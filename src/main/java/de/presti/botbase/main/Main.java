package de.presti.botbase.main;

import de.presti.botbase.bot.BotInfo;
import de.presti.botbase.bot.BotState;
import de.presti.botbase.bot.BotUtil;
import de.presti.botbase.bot.BotVersion;
import de.presti.botbase.commands.CommandManager;
import de.presti.botbase.events.CommandManagerEvent;
import de.presti.botbase.utils.Logger;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static Main instance;
    public static CommandManager cm;
    public static Thread checker;
    public static String lastday = "";

    public static void main(String[] args) {
        instance = new Main();

        cm = new CommandManager();

        try {
            BotUtil.createBot(BotVersion.PUBLIC, "yourtoken","1.0");
            instance.addEvents();
        } catch (Exception ex) {
            Logger.log("Main", "Error while init: " + ex.getMessage());
        }

        instance.addHooks();

        BotInfo.starttime = System.currentTimeMillis();
    }

    private void addEvents() {
        BotUtil.addEvent(new CommandManagerEvent());
        //BotUtil.addEvent(new YourEventClass());
    }

    private void addHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                shutdown();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }));
    }

    private void shutdown() throws SQLException {
        long start = System.currentTimeMillis();
        Logger.log("Main", "Shutdown init. !");

        Logger.log("Main", "JDA Instance shutdown init. !");
        BotUtil.shutdown();
        Logger.log("Main", "JDA Instance has been shutdowned!");

        Logger.log("Main", "Everything has been shutdowned in " + (System.currentTimeMillis() - start) + "ms!");
        Logger.log("Main", "Good bye!");
    }

    public void createCheckerThread() {
        checker = new Thread(() -> {
            while (BotInfo.state != BotState.STOPPED) {

                if (!lastday.equalsIgnoreCase(new SimpleDateFormat("dd").format(new Date()))) {


                    BotUtil.setActivity(BotInfo.botInstance.getGuilds().size() + " Guilds", Activity.ActivityType.WATCHING);

                    Logger.log("Stats", "");
                    Logger.log("Stats", "Todays Stats:");
                    Logger.log("Stats", "Guilds: " + BotInfo.botInstance.getGuilds().size());

                    int i = 0;

                    for (Guild guild : BotInfo.botInstance.getGuilds()) {
                        i += guild.getMemberCount();
                    }

                    Logger.log("Stats", "Overall Users: " + i);
                    Logger.log("Stats", "");

                    lastday = new SimpleDateFormat("dd").format(new Date());
                }

                try {
                    Thread.sleep((7 * (60000L)));
                } catch (InterruptedException e) {
                }
            }
        });
        checker.start();
    }
}