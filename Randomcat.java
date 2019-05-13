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

public class Randomdog extends Command {
    public Randomdog() {
        this.name = "doggo";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }


    @Override
    public void execute(CommandEvent e) {
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(new URL("https://random.dog/woof.json").openStream(), StandardCharsets.UTF_8)).lines()) {
            URL url = new URL(new JSONObject(stream.collect(Collectors.joining(System.lineSeparator()))).getString("url"));
            int lastIndexOf = url.toString().lastIndexOf(".");
            if (lastIndexOf == -1) {
                return;
            }
            InputStream in = url.openStream();
            Files.copy(in, Paths.get(System.getProperty("user.dir") + "/doggo" + name.substring(lastIndexOf)), StandardCopyOption.REPLACE_EXISTING);
            e.getChannel().sendFile(new File(System.getProperty("user.dir") + "/doggo" + name.substring(lastIndexOf))).queue();
        } catch (IOException ignored) {
        }
    }

}
