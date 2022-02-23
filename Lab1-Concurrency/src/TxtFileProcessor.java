import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class TxtFileProcessor implements FileProcessor {
    private String outputDir;

    public TxtFileProcessor(String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public Long countWordsStartWithLetter(Path path, char letter) {
        String content = readFileContent(path);
        return Stream.of(content.split("[^A-Za-zА-Яа-я]+"))
                .map(String::toLowerCase)
                .filter(word -> word.startsWith(String
                        .valueOf(letter)
                        .toLowerCase()))
                .count();
    }

    @Override
    public String readFileContent(Path path) {
        String content = "";
        try {
            content = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public void writeTextToFile(String text, String filename) {
        String pathname = outputDir + filename;
        try (var fos = new FileOutputStream(pathname, true)) {
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
