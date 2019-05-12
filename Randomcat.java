import net.dv8tion.jda.core.EmbedBuilder;
import org.json.JSONObject;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(new URL("http://aws.random.cat/meow").openStream(), StandardCharsets.UTF_8)).lines()) {
            URL url = new URL(new JSONObject(stream.collect(Collectors.joining(System.lineSeparator()))).getString("file"));

            InputStream in = url.openStream();
            Files.copy(in, Paths.get(System.getProperty("user.dir") + "/catto" + (url.toString().substring(url.toString().length() - 4))), StandardCopyOption.REPLACE_EXISTING);
            e.getChannel().sendFile(new File(System.getProperty("user.dir") + "/catto" + url.toString().substring(url.toString().length() - 4))).queue();
        } catch (IOException ignored) {
        }
    }

}
