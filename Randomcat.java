import net.dv8tion.jda.core.EmbedBuilder;
import org.json.JSONObject;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Randomcat extends Command {
    public Randomcat() {
        this.name = "randomcat";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }


    @Override
    public void execute(CommandEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(new URL("http://aws.random.cat/meow").openStream(), StandardCharsets.UTF_8)).lines()) {
            eb.setImage(new JSONObject(stream.collect(Collectors.joining(System.lineSeparator()))).getString("file"));
        } catch (IOException ignored) {
        }

        e.reply(eb.build());
    }

}
