package ml.apti.discord.pinbot.listeners;

import ml.apti.discord.pinbot.Configuration;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.impl.SystemMessage;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ListenerMessageReceived extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (Configuration.PIN_DELETE && event.getMessage() instanceof SystemMessage && event.getAuthor().equals(event.getJDA().getSelfUser())) {
            event.getMessage().delete().queue();
        }

        if (event.getAuthor().isBot()) return;

        String content = event.getMessage().getContentRaw();
        if (content.equals("!ping")) {
            event.getChannel().sendMessage("Pong!").queue();
        } else if (content.equals("!pin")) {
            event.getChannel().sendMessage("bot!").queue();
        }
    }


    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        MessageReaction reaction = event.getReaction();
        MessageReaction.ReactionEmote emote = event.getReaction().getReactionEmote();

        long messageId = event.getMessageIdLong();
        Message message = event.getChannel().getMessageById(messageId).complete();
        if (emote.getName().equals(Configuration.EMOTE_PIN)) {
            message.pin().queue();
        }
    }
}
