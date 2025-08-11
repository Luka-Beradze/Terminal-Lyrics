# Terminal-Lyrics
Terminal Karaoke Bring your favorite song lyrics to life directly in your terminal! TerminalKaraoke is a Java application that reads and parses .lrc (LyRiCs) files and displays the lyrics in real-time, synchronized with the song's timing. You can customize the appearance of the lyrics with different styles, text colors, and background colors.

## Features

-   **Real-time Lyric Display**: Plays back lyrics synchronized with the timestamps in an `.lrc` file.
-   **LRC File Support**: Parses standard `.lrc` files with `[mm:ss.xx]` timestamps.
-   **Customizable Styling**: Change the text style to bold, italic, underline, or a combination.
-   **Color Options**: Set custom foreground and background colors for the lyrics.
-   **Easy to Use**: Simple command-line interface for running the application.

## Prerequisites

To compile and run this application, you need to have a Java Development Kit (JDK) installed on your system (version 8 or higher).

## How to Use

### 1. Compilation

First, compile the Java source code using the `javac` compiler.

```bash
javac TerminalKaraoke.java
```

### 2. Running the Application

Once compiled, you can run the application from your terminal using the `java` command. The program accepts command-line arguments to specify the `.lrc` file and customize the appearance of the lyrics.

#### Basic Usage

To play a lyrics file with default styling:

```bash
java TerminalKaraoke "path/to/your/lyrics.lrc"
```

#### Customization

You can provide additional arguments to control the style, color, and background color of the lyrics.

**Usage Syntax:**

```bash
# With style and/or color
java TerminalKaraoke <lrc_file_path> [style|color]

# With style, color, and background color
java TerminalKaraoke <lrc_file_path> [style] [color] [bgcolor]
```

#### Examples:

-   **Bold red lyrics:**
    ```bash
    java TerminalKaraoke "lyrics.lrc" "bold" "red"
    ```

-   **Italic blue lyrics on a yellow background:**
    ```bash
    java TerminalKaraoke "lyrics.lrc" "italic" "blue" "yellow"
    ```

-   **Underlined green lyrics:**
    ```bash
    java TerminalKaraoke "lyrics.lrc" "underline" "green"
    ```
-   **Bold and Italic Cyan lyrics:**
    ```bash
    java TerminalKaraoke "lyrics.lrc" "bolditalic" "cyan"
    ```

## Customization Options

### Available Styles && Colors

| Color     | Style        |
| :-------- | :----------- |
| `black`   | `bold`       |
| `red`     | `italic`     |
| `green`   | `underline`  |
| `yellow`  | `bolditalic` |
| `blue`    | | |
| `magenta` | | |
| `cyan`    | | |
| `white`   | | |


## LRC File Format

The application requires a standard `.lrc` file. Each line of the file should contain a timestamp followed by the lyric text.

**Format:** `[mm:ss.xx] Lyric text here`

-   `mm`: minutes
-   `ss`: seconds
-   `xx`: hundredths of a second

**Example `.lrc` file content:**

```
[00:10.54]Hello from the other side
[00:14.21]I must have called a thousand times
[00:18.90]To tell you I'm sorry for everything that I've done
```

## How It Works

1.  **Parsing**: The application reads the specified `.lrc` file line by line. It uses a regular expression to extract the timestamp and the lyric text from each line.
2.  **Storing Lyrics**: Each lyric line is stored as a `LyricLine` object, which contains the time (in milliseconds) and the text.
3.  **Sorting**: The list of `LyricLine` objects is sorted based on their timestamps to ensure correct playback order.
4.  **Playback Loop**: The main logic starts a timer and continuously checks the elapsed time. When the elapsed time matches or exceeds the timestamp of the next lyric, it prints that lyric to the console.
5.  **Styling**: The `printStyledLyric` method uses ANSI escape codes to apply the user-specified styles and colors to the text before printing it. The text style is reset after each line to avoid affecting other terminal output.
