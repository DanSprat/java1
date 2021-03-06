package ru.progwards.java1.lessons.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindDuplicates {
    private  static List<ArrayList<String>> splitSames(ArrayList<Path> arrayList) {
            List<ArrayList<String>> result = new ArrayList<>();
        try {
            result.add(new ArrayList<>(List.of(arrayList.get(0).toString()))); // Добавляю первый файл по умолчанию тем самым инициализируя первую группу
            for (int i = 1; i < arrayList.size(); i++) { // В цикле проверяю, есть ли совпадения по содержанию/размеру/дате среди текущих групп
                boolean isAdd = false;
                for (int j = 0; j < result.size(); j++) {
                    if (Files.getLastModifiedTime(arrayList.get(i)).equals(Files.getLastModifiedTime(Paths.get(result.get(j).get(0)))) && //Совпадение дат
                            Files.size(arrayList.get(i)) == Files.size(Paths.get(result.get(j).get(0)))) { // Совпадение размеров
                        if (Files.readString(arrayList.get(i)).equals(Files.readString(Paths.get(result.get(j).get(0))))) {
                            result.get(j).add(arrayList.get(i).toString()); // Если есть то добавляю в группу
                            isAdd = true;
                            break;
                        }
                    }
                }
                if (!isAdd) {
                    result.add(new ArrayList<>(List.of(arrayList.get(i).toString()))); // Если нет, то создаю новую группу
                }
            }
            for (int i = 0; i < result.size(); i++) { // Удаляю все списки,размер которых равен единице
                if (result.get(i).size() == 1) {
                    result.remove(i);
                    i--;
                }
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }
    public static List<List<String>> findDuplicates(String startPath){
            final Path dir = Paths.get(startPath);
            HashMap<String, ArrayList<Path>> listHashMap = new HashMap<>();
        try {
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**");
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) { //Получаю Map всех файлов с одинаковым названием
                    if (pathMatcher.matches(path)) {
                        if (listHashMap.containsKey(path.getFileName().toString())) {
                            listHashMap.get(path.getFileName().toString()).add(path.toAbsolutePath());
                        } else {
                            listHashMap.put(path.getFileName().toString(), new ArrayList<>(List.of(path.toAbsolutePath())));
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        List<List<String>> result = new ArrayList<>();
        for (var it: listHashMap.entrySet()){
            if (it.getValue().size()>1){
                result.addAll(splitSames(it.getValue())); // разбиваю файлы с одинаковым названием на идентичные файлы
            }
        }
        return result;
    }

    public static void main(String[] args){
        final String path = "C:\\Users\\Work\\IdeaProjects\\Progwards\\test";
        System.out.println(findDuplicates(path));
    }
}
