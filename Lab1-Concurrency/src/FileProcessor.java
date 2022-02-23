import java.nio.file.Path;

public interface FileProcessor {
    String readFileContent(Path path);

    void writeTextToFile(String text, String filename);

    Long countWordsStartWithLetter(Path path, char letter);
}
