import net.dv8tion.jda.core.EmbedBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pokemon extends Command {
    public Pokemon() {
        this.name = "pokemon";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }


    @Override
    public void execute(CommandEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        if (e.getArgs().length() < 1) {
            try (Stream<String> stream = new BufferedReader(new InputStreamReader(new URL("https://static.jensz12.com/pokemon/list.php").openStream(), StandardCharsets.UTF_8)).lines()) {
                JSONArray json = new JSONObject(stream.collect(Collectors.joining(System.lineSeparator()))).getJSONArray("files");
                eb.setImage("https://static.jensz12.com/pokemon/" + json.getJSONObject(ThreadLocalRandom.current().nextInt(0, json.length() + 1)).getString("file"));
            } catch (IOException ignored) {
            }
        } else {
            switch (e.getArgsSplit().get(0)) {
                case "default":
                case "normal":
                default: {
                    try (Stream<String> stream = new BufferedReader(new InputStreamReader(new URL("https://static.jensz12.com/pokemon/list.php").openStream(), StandardCharsets.UTF_8)).lines()) {
                        JSONArray json = new JSONObject(stream.collect(Collectors.joining(System.lineSeparator()))).getJSONArray("files");
                        eb.setImage("https://static.jensz12.com/pokemon/" + json.getJSONObject(ThreadLocalRandom.current().nextInt(0, json.length() + 1)).getString("file"));
                    } catch (IOException ignored) {
                    }
                    break;
                }
                case "fuse":
                case "generate":
                case "fused": {
                    int min = 1;
                    int max = 151;
                    int num1 = ThreadLocalRandom.current().nextInt(min, max + 1);
                    int num2 = ThreadLocalRandom.current().nextInt(min, max + 1);
                    eb.setImage("http://images.alexonsager.net/pokemon/fused/" + num1 + "/" + num1 + "." + num2 + ".png");
                    break;
                }
            }
        }

        e.reply(eb.build());
    }

}
