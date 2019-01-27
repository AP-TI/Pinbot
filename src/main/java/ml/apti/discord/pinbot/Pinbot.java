package ml.apti.discord.pinbot;

import ml.apti.discord.pinbot.listeners.ListenerMessageReceived;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class Pinbot {

    private JDA jda;

    public Pinbot() throws LoginException {
        connect(Configuration.DISCORD_TOKEN);
    }

    public static void main(String[] args) throws LoginException {
        Pinbot bot = new Pinbot();
    }

    private void connect(String token) throws LoginException {
        this.jda = new JDABuilder(token).build();

        jda.addEventListener(new ListenerMessageReceived());
    }

}
