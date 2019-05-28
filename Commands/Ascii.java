import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import dk.jensbot.kajbot4discord.utils.Config;
import dk.jensbot.kajbot4discord.utils.LogHelper;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ascii extends Command {
    private static final String asciiArtUrl = "http://artii.herokuapp.com/";

    public Ascii() {
        this.name = "ascii";
        this.guildOnly = true;
        this.adminCommand = true;
    }

    private int randomNum(int start, int end) {

        if (end < start) {
            int temp = end;
            end = start;
            start = temp;
        }
        return (int) Math.floor(Math.random() * (end - start + 1) + start);
    }

    private String getAsciiArt(String ascii, String font) {
        try {
            String url = asciiArtUrl + "make" + "?text=" + ascii.replaceAll(" ", "+") + (font == null || font.isEmpty() ? "" : "&font=" + font);
            return new Scanner(new URL(url).openStream(), String.valueOf(StandardCharsets.UTF_8)).useDelimiter("\\A").next();
        } catch (IOException e) {
            LogHelper.error(this.getClass(), e, null);
            return "Error retrieving text.";
        }
    }

    private List<String> getAsciiFonts() {
        String url = asciiArtUrl + "fonts_list";
        List<String> fontList = null;
        try {
            String list = new Scanner(new URL(url).openStream(), String.valueOf(StandardCharsets.UTF_8)).useDelimiter("\\A").next();
            fontList = Arrays.stream(list.split("\n")).collect(Collectors.toList());
        } catch (IOException e) {
            LogHelper.error(this.getClass(), e, null);
        }

        return fontList;
    }


    @Override
    public void execute(CommandEvent e) {
        if (e.getArgs().length() < 1) return;
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < e.getArgsSplit().toArray().length; i++) {
            input.append(i == e.getArgsSplit().toArray().length - 1 ? e.getArgsSplit().get(i) : e.getArgsSplit().get(i) + " ");

            List<String> fonts = getAsciiFonts();
            String font = fonts.get(randomNum(0, fonts.size() - 1));

            try {
                String ascii = getAsciiArt(input.toString(), font);

                if (ascii.length() > 1900) {
                    e.reply("```fix\n\n The ASCII text is too big!.```");
                    return;
                }

                e.reply("**Font:** " + font + "\n```fix\n\n" + ascii + "```");
            } catch (IllegalArgumentException iae) {
                LogHelper.error(this.getClass(), iae, e.getMessage().getContentRaw());
                e.reply("```fix\n\nYour text contains invalid characters!.```");
            }
        }
    }
}
