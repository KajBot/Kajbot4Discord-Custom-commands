import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class Letters extends Command {
    public Letters() {
        this.name = "letters";
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgs().length() < 1 && e.getArgs().length() > 10) return;
        for (String l : e.getArgs().split("")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setFooter(e.getAuthor().getAsTag() + ": " + e.getMessage().getContentRaw(), null);
            if (l.equals(" ")) {
                eb.addBlankField(true);
                eb.addBlankField(false);
                eb.addBlankField(true);
                eb.addBlankField(false);
            } else {
              eb.setImage("http://dance.cavifax.com/images/" + l.toLowerCase() + ".gif");
            }
            e.reply(eb.build());
        }
    }

}
