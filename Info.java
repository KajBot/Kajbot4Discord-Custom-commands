import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.utils.Config;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Info extends Command {
    public Info() {
        this.name = "info";
        this.guildOnly = true;
        this.requiredRole = Config.cfg.get("Bot admin role");
    }

    private static String VariableToString(String regex, String input) {
        String[] splitting = new String[]{input};
        if (regex != null) splitting = input.split(regex);
        StringBuilder splittedBuilder = new StringBuilder();
        for (String s : splitting) {
            splittedBuilder.append(s.substring(0, 1).toUpperCase(Locale.ENGLISH)).append(s.substring(1).toLowerCase(Locale.ENGLISH)).append(" ");
        }
        String splitted = splittedBuilder.toString();
        return splitted.substring(0, splitted.length() - 1);
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgs().length() > 0) {
            List<User> userMention = e.getMessage().getMentionedUsers();
            for (User user : userMention) {
                embedUser(user, e.getGuild().getMember(user), e);
            }
        } else {
            embedUser(e.getAuthor(), e.getMember(), e);
        }
    }

    private void embedUser(User user, Member member, CommandEvent e) {
        String name, id, dis, nickname, icon, status, game, join, register;

        icon = user.getEffectiveAvatarUrl();

        /* Identity */
        name = user.getName();
        id = user.getId();
        dis = user.getDiscriminator();
        nickname = member == null || member.getNickname() == null ? name : member.getEffectiveName();

        /* Status */
        OnlineStatus stat = member == null ? null : member.getOnlineStatus();
        status = stat == null ? "N/A" : VariableToString("_", stat.getKey());
        game = stat == null ? "N/A" : member.getGame() == null ? "N/A" : member.getGame().getName();

        /* Time */
        join = member == null ? "N/A" : DateTimeFormatter.ofPattern("d/M/u HH:mm:ss").format(member.getJoinDate());
        register = DateTimeFormatter.ofPattern("d/M/u HH:mm:ss").format(user.getCreationTime());

        /* Final */
        EmbedBuilder embed = new EmbedBuilder().setAuthor(nickname, null, icon).setThumbnail(icon);

        //embed.addField(":spy: Identity", "ID: `" + id + "`\n" + "Username: `" + name + "#" + dis + "`", true);
        embed.addField(":spy: Identity", "ID: `" + id + "`\nUsername: `" + name + "#" + dis + "`", true);
        embed.addField(":first_quarter_moon: Status", "Game: `" + game + "`\nStatus: `" + status + "`\n", true);
        embed.addField(":stopwatch: Time", "Joined server: `" + join + "`\nAccount created: `" + register + "`\n", true);
        embed.setTimestamp(ZonedDateTime.now());

        e.reply(embed.build());
    }

}
