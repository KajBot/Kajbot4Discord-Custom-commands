import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import dk.jensbot.kajbot4discord.utils.LogHelper;

public class Invite extends Command {
    public Invite() {
        this.name = "invite";
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent e) {
        try {
            e.reply(e.getChannel().createInvite().setUnique(true).setMaxUses(1).setMaxAge(1800).complete().getUrl());
        } catch (Exception ex) {
            LogHelper.error(this.getClass(), ex, e.getMessage().getContentRaw());
        }
    }
}
