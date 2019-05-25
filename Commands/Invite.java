import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.utils.Config;
import support.kajstech.kajbot.utils.LogHelper;

public class Invite extends Command {
    public Invite() {
        this.name = "invite";
        this.guildOnly = true;
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
