package ml.apti.discord.pinbot.listeners;

import ml.apti.discord.pinbot.Configuration;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.impl.SystemMessage;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ListenerMessageReceived extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
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
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        MessageReaction reaction = event.getReaction();
        MessageReaction.ReactionEmote emote = event.getReaction().getReactionEmote();

        long messageId = event.getMessageIdLong();
        Message message = event.getChannel().getMessageById(messageId).complete();
        if (emote.getName().equals(Configuration.EMOTE_PIN) && !message.isPinned()) {
            message.pin().queue();

            MessageEmbed msg = new EmbedBuilder()
                    .setTitle("Pinned")
                    .addField("Message", message.getContentRaw(), false)
                    .addField("Channel", event.getChannel().getAsMention(), true)
                    .addField("By", message.getAuthor().getAsMention(), true)
                    .setAuthor(event.getMember().getUser().getAsTag(), null, event.getMember().getUser().getAvatarUrl())
                    .build();


            event.getGuild().getTextChannelById(Configuration.CHANNEL_LOG).sendMessage(msg).queue();
        }
    }
}
