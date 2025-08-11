import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TerminalKaraoke {

    //Represents a single timed lyric line.

    private static class LyricLine implements Comparable<LyricLine> {
        private final long timeInMillis;
        private final String text;

        public LyricLine(long timeInMillis, String text) {
            this.timeInMillis = timeInMillis;
            this.text = text;
        }

        public long getTimeInMillis() {
            return timeInMillis;
        }

        public String getText() {
            return text;
        }

        @Override
        public int compareTo(LyricLine other) {
            return Long.compare(this.timeInMillis, other.timeInMillis);
        }
    }


    
    public static final String ANSI_RESET = "\u001B[0m";
    public static String color;
    public static String bgcolor;
    public static String style;
    //psvm
    public static void main(String[] args) {
        
        if (args.length == 1);
        else if (args.length == 2 ) {
            if  (args[1].toLowerCase() == "bold" 
              || args[1].toLowerCase() == "italic" 
              || args[1].toLowerCase() == "underline" 
              || args[1].toLowerCase() == "bolditalic") 
            { 
                style = args[1];
                color = "";
            } else {
                style = "";
                color = args[1];
            }
        } else if (args.length == 4) {
                style = args[1];
                color = args[2];
                bgcolor = args[3];
        }
        else {
            System.out.println("Usage: java TerminalKaraoke \"Lyrics.lrc\" \"bold|italic|bolditalic|underline\" \"foreground color\" \"background color\"");
            System.out.println("or");
            System.out.println("java TerminalKaraoke \"Lyrics.lrc\"");
            System.out.println("Available styles: style1, style2, style3, \" \" (default)");
            return;
        }

        String filePath = args[0];
        

        try {
            List<LyricLine> lyrics = parseLrcFile(filePath);
            playLyrics(lyrics, style);
        } catch (IOException e) {
            System.err.println("Error reading the LRC file: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Playback was interrupted.");
        }
    }


    private static List<LyricLine> parseLrcFile(String filePath) throws IOException {
        List<LyricLine> lyrics = new ArrayList<>();
        // Reegx to match LRC timestamps like [mm:ss.xx]
        Pattern pattern = Pattern.compile("\\[(\\d{2}):(\\d{2})\\.(\\d{2})\\](.*)");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    long minutes = Long.parseLong(matcher.group(1));
                    long seconds = Long.parseLong(matcher.group(2));
                    long hundredths = Long.parseLong(matcher.group(3));
                    String text = matcher.group(4).trim();

                    long timeInMillis = (minutes * 60 + seconds) * 1000 + hundredths * 10;
                    lyrics.add(new LyricLine(timeInMillis, text));
                }
            }
        }
        Collections.sort(lyrics);
        return lyrics;
    }

    //main logic
    private static void playLyrics(List<LyricLine> lyrics, String style) throws InterruptedException {
        if (lyrics.isEmpty()) return;

        long startTime = System.currentTimeMillis();
        int currentLyricIndex = 0;

        // Default color for lyrics output

        while (currentLyricIndex < lyrics.size()) {
            LyricLine currentLyric = lyrics.get(currentLyricIndex);
            long elapsedTime = System.currentTimeMillis() - startTime;

            if (elapsedTime >= currentLyric.getTimeInMillis()) {
                printStyledLyric(currentLyric.getText(), style, color, bgcolor);
                currentLyricIndex++;
            }

            Thread.sleep(10); // Sleep for a short duration to avoid busy-waiting
        }
    }

    private static void printStyledLyric(String text, String style, String color, String bgColor) {
        
        String styleCode = "";
        String colorCode = "";
        String bgColorCode = "";
         if (style != null) {
            switch (style.toLowerCase()) {
                case "bold":
                    styleCode = "\u001B[1m";
                    break;
                case "italic":
                    styleCode = "\u001B[3m";
                    break;
                case "bolditalic":
                    styleCode = "\u001B[1m"+"\u001B[3m";
                    break;
                case "underline":
                    styleCode = "\u001B[4m";
                    break;
                default:
                    break;
            }
        }

        if (color != null) {
            switch (color.toLowerCase()) {
                case "black":
                    colorCode = "\u001B[30m";
                    break;
                case "red":
                    colorCode = "\u001B[31m";
                    break;
                case "green":
                    colorCode = "\u001B[32m";
                    break;
                case "yellow":
                    colorCode = "\u001B[33m";
                    break;
                case "blue":
                    colorCode = "\u001B[34m";
                    break;
                case "magenta":
                    colorCode = "\u001B[35m";
                    break;
                case "cyan":
                    colorCode = "\u001B[36m";
                    break;
                case "white":
                    colorCode = "\u001B[37m";
                    break;
                default:
                    colorCode = "\u001B[37m";
                    break;
            }
        }

        if (bgColor != null) {
            switch (bgColor.toLowerCase()) {
                case "black":
                    bgColorCode = "\u001B[40m";
                    break;
                case "red":
                    bgColorCode = "\u001B[41m";
                    break;
                case "green":
                    bgColorCode = "\u001B[42m";
                    break;
                case "yellow":
                    bgColorCode = "\u001B[43m";
                    break;
                case "blue":
                    bgColorCode = "\u001B[44m";
                    break;
                case "magenta":
                    bgColorCode = "\u001B[45m";
                    break;
                case "cyan":
                    bgColorCode = "\u001B[46m";
                    break;
                case "white":
                    bgColorCode = "\u001B[47m";
                    break;
                default:
                    break;
            }
        }


        System.out.println(styleCode + colorCode + bgColorCode + text + ANSI_RESET);
    }
}