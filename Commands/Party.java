import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Party extends Command {
    public Party() {
        this.name = "party";
        this.boosterCommand = false;
    }

    @Override
    public void execute(CommandEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(new URL("https://static.jensz12.com/party/list.php").openStream(), StandardCharsets.UTF_8)).lines()) {
            JSONArray json = new JSONObject(stream.collect(Collectors.joining(System.lineSeparator()))).getJSONArray("files");
            eb.setImage("https://static.jensz12.com/party/" + json.getJSONObject(ThreadLocalRandom.current().nextInt(0, json.length() + 1)).getString("file"));
        } catch (IOException ignored) {
        }
        e.reply(eb.build());
    }

}
