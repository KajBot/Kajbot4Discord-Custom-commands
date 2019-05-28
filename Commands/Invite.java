import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.utils.LogHelper;

public class Invite extends Command {
    public Invite() {
        this.name = "invite";
        this.adminCommand = true;
    }

    @Override
    protected void execute(CommandEvent e) {
        try {
            e.reply(e.getGuild().getTextChannelById(e.getChannel().getIdLong()).createInvite().setUnique(true).setMaxUses(1).setMaxAge(1800).complete().getURL());
        } catch (Exception ex) {
            LogHelper.error(this.getClass(), ex, e.getMessage().getContentRaw());
        }
    }
}
