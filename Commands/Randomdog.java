import org.json.JSONObject;
import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;

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
            if(!Files.exists(Paths.get(System.getProperty("user.dir") + "/downloads"))) Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/downloads"));
            Files.copy(in, Paths.get(System.getProperty("user.dir") + "/downloads/doggo" + url.toString().substring(lastIndexOf)), StandardCopyOption.REPLACE_EXISTING);
            File file = new File(System.getProperty("user.dir") + "/downloads/doggo" + url.toString().substring(lastIndexOf));
            if (((double) file.length() / (1024 * 1024)) > 7.99) {
                execute(e);
                return;
            }
            e.getChannel().sendFile(file).queue();
        } catch (IOException ignored) {
        }
    }
}