package ru.progwards.java1.lessons.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FilesSelect {
    public  static void selectFiles(String inFolder, String outFolder, List<String> keys){
        try {


            final Path dirIn = Paths.get(inFolder);
            final Path dirOut = Paths.get(outFolder);
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.txt");
            Files.walkFileTree(dirIn, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    try {
                        if (pathMatcher.matches(path)) {
                            for (String x : keys) {
                                if (Files.readString(path).contains(x)) {
                                    Path p = dirOut.resolve(x).toAbsolutePath();
                                    System.out.println(Files.notExists(p));
                                    if (Files.notExists(dirOut.resolve(x)) || !Files.isDirectory(dirOut.resolve(x))) {
                                        Files.createDirectory(dirOut.resolve(x));
                                    }
                                    Files.copy(path, dirOut.resolve(x + "/" + path.getFileName()));
                                }
                            }
                        }
                        return FileVisitResult.CONTINUE;
                    } catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) throws Exception{
        selectFiles("test/In","test/Out",List.of("lil","me","alone"));
    }
}
