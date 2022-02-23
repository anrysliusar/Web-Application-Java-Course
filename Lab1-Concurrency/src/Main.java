import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String outputDir = "results\\";
    private static char letter = 'а';
    private static String dir = "C:\\Users\\Andrii\\Documents\\Test";
    private static String outFilename = "result.txt";


    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        FileProcessor fileProcessor = new TxtFileProcessor(outputDir);
        FileExtension fileExtension = FileExtension.TXT;

        System.out.println("Enter directory path: ");
        dir = scanner.nextLine();

        System.out.println("Enter a letter: ");
        String line = scanner.nextLine();
        letter = line.charAt(0);

        System.out.println("Enter output filename : ");
        outFilename = scanner.nextLine();

        List<String> filePathsByExtension = new ArrayList<>();
        getFilePathsByExtension(dir, fileExtension, filePathsByExtension);

        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(filePathsByExtension.size());

        filePathsByExtension.forEach(filePath -> executorService.submit(() -> {
                    Path path = Path.of(filePath);
                    Long count = fileProcessor.countWordsStartWithLetter(path, letter);
                    fileProcessor.writeTextToFile(
                            String.format("Filepath: %s\nWord count: %d\n", path, count),
                            outFilename);
                    latch.countDown();
                })
        );

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        System.out.println(fileProcessor.readFileContent(Path.of(outputDir + outFilename)));
    }

    public static void getFilePathsByExtension(String pathToDir, FileExtension fileExtension, List<String> listPaths) {
        File[] files = new File(pathToDir).listFiles();

        Arrays.stream(Objects.requireNonNull(files))
                .forEach(file -> {
                    if (file.isFile() && file.getName().endsWith(fileExtension.getName())) {
                        listPaths.add(file.getAbsolutePath());
                    } else if (file.isDirectory()) {
                        getFilePathsByExtension(file.getAbsolutePath(), fileExtension, listPaths);
                    }
                });
    }

}

