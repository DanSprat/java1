package ru.progwards.java1.lessons.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindDuplicates {
    private  static List<ArrayList<String>> splitSames(ArrayList<Path> arrayList) throws Exception{
        List<ArrayList<String>> result = new ArrayList<>();
        result.add(new ArrayList<>(List.of(arrayList.get(0).toString())));
        for (int i =1;i<arrayList.size();i++){
            boolean isAdd = false;
            for(int j =0;j<result.size();j++){
             if (Files.getLastModifiedTime(arrayList.get(i)).equals(Files.getLastModifiedTime(Paths.get(result.get(j).get(0)))) &&
                 Files.size(arrayList.get(i))==Files.size(Paths.get(result.get(j).get(0)))) {
                 if (Files.readString(arrayList.get(i)).equals(Files.readString(Paths.get(result.get(j).get(0))))){
                     result.get(j).add(arrayList.get(i).toString());
                     isAdd = true;
                     break;
                 }
             }
            }
            if (!isAdd){
                result.add(new ArrayList<>(List.of(arrayList.get(i).toString())));
            }
        }
        for (int i = 0;i<result.size();i++){
            if (result.get(i).size()==1){
                result.remove(i);
                i--;
            }
        }
        return result;
    }
    public static List<List<String>> findDuplicates(String startPath)throws Exception{
        final Path dir = Paths.get(startPath);
        HashMap<String, ArrayList<Path>> listHashMap =new HashMap<>();
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**");
        Files.walkFileTree(dir,new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs){
                if (pathMatcher.matches(path)){
                   if(listHashMap.containsKey(path.getFileName().toString())){
                       listHashMap.get(path.getFileName().toString()).add(path.toAbsolutePath());
                   } else {
                       listHashMap.put(path.getFileName().toString(),new ArrayList<>(List.of(path.toAbsolutePath())));
                   }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        List<List<String>> result = new ArrayList<>();
        for (var it: listHashMap.entrySet()){
            if (it.getValue().size()>1){
                result.addAll(splitSames(it.getValue()));
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        final String path = "C:\\Users\\Work\\IdeaProjects\\Progwards\\test";
        System.out.println(findDuplicates(path));
    }
}
