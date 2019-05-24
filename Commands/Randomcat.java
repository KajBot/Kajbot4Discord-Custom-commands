import org.json.JSONObject;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;

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
        this.name = "catto";
        this.guildOnly = false;
    }


    @Override
    public void execute(CommandEvent e) {
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(new URL("http://aws.random.cat/meow").openStream(), StandardCharsets.UTF_8)).lines()) {
            URL url = new URL(new JSONObject(stream.collect(Collectors.joining(System.lineSeparator()))).getString("file"));
            int lastIndexOf = url.toString().lastIndexOf(".");
            if (lastIndexOf == -1) {
                return;
            }
            InputStream in = url.openStream();
            if(!Files.exists(Paths.get(System.getProperty("user.dir") + "/downloads"))) Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/downloads"));
            Files.copy(in, Paths.get(System.getProperty("user.dir") + "/downloads/catto" + url.toString().substring(lastIndexOf)), StandardCopyOption.REPLACE_EXISTING);
            File file = new File(System.getProperty("user.dir") + "/downloads/catto" + url.toString().substring(lastIndexOf));
            if (((double) file.length() / (1024 * 1024)) > 7.99) {
                execute(e);
                return;
            }
            e.getChannel().sendFile(file).queue();
        } catch (IOException ignored) {
        }
    }
}