package ru.mikheev.kirill.gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FileGenerator {

    private final List<String> WORDS_TO_FILL = List.of("помидор", "собака", "йод", "престидижитация");

    private final String pathToFilesDirectory;
    private final String namePattern;

    private List<File> files = new ArrayList<>();

    public FileGenerator(String pathToFilesDirectory, String namePattern) {
        this.pathToFilesDirectory = pathToFilesDirectory;
        this.namePattern = namePattern;
    }

    public void generate(long wordCount, int fileCount) {
        long lineCounter = 0l;
        int filesAlreadyCreated = files.size();
        for(int fileNumber = filesAlreadyCreated; fileNumber < filesAlreadyCreated + fileCount; fileNumber++) {
            File file = new File(pathToFilesDirectory + "\\" + namePattern + fileNumber + ".txt");
            try {
                if(!file.createNewFile()) {
                    System.out.println("Файл " + file.getName() + " уже существует");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, Charset.forName("Cp1251")))) {
                StringBuilder lineBuilder = new StringBuilder();
                for(int wordCounter = 0; wordCounter < wordCount; wordCounter++) {
                    lineBuilder.append(WORDS_TO_FILL.get(ThreadLocalRandom.current().nextInt(0, WORDS_TO_FILL.size())));
                    lineBuilder.append(" ");
                    if((wordCounter + 1) % 500 == 0) {
                        lineBuilder.append("\n" + ++lineCounter + " ");
                        fileWriter.write(lineBuilder.toString());
                        lineBuilder.setLength(0);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            files.add(file);
        }
    }

    public void removeAllFiles() {
        for(var file : files) {
            if(!file.delete()) {
                System.out.println("не удалось удалить файл " + file.getName());
            }
        }
        files.clear();
    }
}
