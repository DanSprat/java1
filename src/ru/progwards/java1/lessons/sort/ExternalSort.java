package ru.progwards.java1.lessons.sort;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ExternalSort {

    private static class Block{
        String path;
        BufferedReader bufferedReader;
        Integer [] intBlock;
        int blockSize;
        int index=0;
        boolean endReading;
        int topElement;

        public Block(BufferedReader bufferedReader,Integer [] intBlock,String path){
            this.path = path;
            this.bufferedReader= bufferedReader;
            this.intBlock = intBlock;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Block)) return false;
            Block block = (Block) o;
            return index == block.index &&
                    block.bufferedReader == bufferedReader &&
                   intBlock == block.intBlock;
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(bufferedReader, index);
            result = 31 * result + Arrays.hashCode(intBlock);
            return result;
        }
    }

    private static int MEMORY_SIZE = 10_000;


    private static void writeToFile(String fileName,int sizeOfBlock,Integer [] toWriteBlock,PrintWriter printWriter) throws FileNotFoundException {
        for (int i = 0;i<sizeOfBlock;i++){
            printWriter.println(toWriteBlock[i]);
        }

    }

    private static void mergeTwo(File file1,File file2, String fileName) throws IOException {
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader(file1));
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file2));
        int sizeOfBlock = MEMORY_SIZE / 3;
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(("temp"+fileName)));
        Integer [] toWriteBlock = new Integer[sizeOfBlock];
        Integer [] block1 = new Integer[sizeOfBlock];
        Integer [] block2 = new Integer[sizeOfBlock];
        int posBlock1 = 0;
        int posBlock2 = 0;
        boolean endOfFile1 = false;
        boolean endOfFile2 = false;
        String newNum;

        for (int i = 0;i<sizeOfBlock;i++){
            newNum = bufferedReader1.readLine();
            if (newNum != null){
                block1[posBlock1++] = Integer.parseInt(newNum);
            } else {
                endOfFile1 =true;
                break;
            }
        }

        for (int i = 0;i<sizeOfBlock;i++){
            newNum = bufferedReader2.readLine();
            if (newNum != null){
                block2[posBlock2++] = Integer.parseInt(newNum);
            } else {
                endOfFile2 =true;
                break;
            }
        }
        int currentPosResult = 0;
        int sizeBlock1 = posBlock1;
        int sizeBlock2 = posBlock2;
        int posLast = 0;
        int sizeLastBlock = 0;

        boolean oneFromFilesWritten = false;
        boolean endOfLastFile = false;

        posBlock1 = 0;
        posBlock2 = 0;
        Integer [] lastBlock = null;
        BufferedReader lastBR = null;

        while (!oneFromFilesWritten){
           if(block1[posBlock1].compareTo(block2[posBlock2]) < 0){
               toWriteBlock[currentPosResult++] = block1[posBlock1++];
               if (posBlock1 == sizeBlock1){
                   posBlock1 = 0;
                   if (endOfFile1){
                       posLast = posBlock2;
                       endOfLastFile = endOfFile2;
                       sizeLastBlock = sizeBlock2;
                       lastBR = bufferedReader2;
                       lastBlock = block2;
                       bufferedReader1.close();
                       oneFromFilesWritten = true;
                       break;
                   }
                  for (int i =0;i<sizeOfBlock;i++){
                      newNum = bufferedReader1.readLine();
                      if (newNum!= null){
                          block1[i] = Integer.parseInt(newNum);
                      } else {
                          endOfFile1 = true;
                          posBlock1 = 0;
                          sizeBlock1 = i;
                          break;
                      }
                  } if (sizeBlock1 == 0){
                       posLast = posBlock2;
                       endOfLastFile = endOfFile2;
                       sizeLastBlock = sizeBlock2;
                       lastBR = bufferedReader2;
                       lastBlock = block2;
                       bufferedReader1.close();
                       break;
                   }

               }

           } else {
               toWriteBlock[currentPosResult++] = block2[posBlock2++];
               if (posBlock2 == sizeBlock2){
                   posBlock2 =0;
                   if (endOfFile2){
                       posLast = posBlock1;
                       endOfLastFile = endOfFile1;
                       sizeLastBlock = sizeBlock1;
                       lastBR = bufferedReader1;
                       lastBlock = block1;
                       bufferedReader2.close();
                       oneFromFilesWritten = true;
                       break;
                   }
                   for (int i =0;i<sizeOfBlock;i++){
                       newNum = bufferedReader2.readLine();
                       if (newNum!= null){
                           block2[i] = Integer.parseInt(newNum);
                       } else {
                           endOfFile2 = true;
                           posBlock2 = 0;
                           sizeBlock2 = i;
                           break;
                       }
                   }
                   if (sizeBlock2 == 0){
                       posLast = posBlock1;
                       endOfLastFile = endOfFile1;
                       sizeLastBlock = sizeBlock1;
                       lastBR = bufferedReader1;
                       lastBlock = block1;
                       bufferedReader2.close();
                       break;
                   }

               }
           }
           if (currentPosResult == sizeOfBlock){
                writeToFile("temp"+fileName,sizeOfBlock,toWriteBlock,printWriter);
                currentPosResult = 0;
           }
        }

        for (int i =0; i<currentPosResult;i++){
            printWriter.println(toWriteBlock[i]);
        }
        for (int i =posLast;i<sizeLastBlock;i++){
            printWriter.println(lastBlock[i]);
        }
        boolean write = true;
        sizeLastBlock = sizeOfBlock;
        while (write){
            for (int i =0;i<sizeOfBlock;i++){
                newNum = lastBR.readLine();
                if (newNum != null){
                    toWriteBlock[i] = Integer.parseInt(newNum);
                } else {
                    sizeLastBlock = i;
                    endOfLastFile =true;
                    break;
                }
            }
            writeToFile(fileName,sizeLastBlock,toWriteBlock,printWriter);
            if (endOfLastFile)
                write =false;
        }
        bufferedReader1.close();
        bufferedReader2.close();
        printWriter.close();
        Files.delete(file1.toPath());
        Files.delete(file2.toPath());
        Files.move(Paths.get("temp"+fileName),Paths.get(fileName));

    }

    private static int binarySearch(ArrayList <Block> blocks,int element, int start, int end){
        if(end - start <=1)
            return element<=blocks.get(start).topElement?start:start+1;
        int middle = (start + end)/2;
        if (element < blocks.get(middle).topElement){
            return binarySearch(blocks,element,start,middle);
        } else {
            return binarySearch(blocks,element,middle,end);
        }

    }
    
    private static void refresh(ArrayList<Block> blocks) throws IOException {
        Block currentBlock = blocks.get(0);
        if(currentBlock.index + 1 == currentBlock.blockSize){
            if (currentBlock.endReading){
                currentBlock.bufferedReader.close();
                Files.delete(Paths.get(currentBlock.path));
                blocks.remove(0);
            } else {
                currentBlock.index = 0;
                currentBlock.blockSize = 0;
                String nextInt;
                for (int i =0;i<currentBlock.intBlock.length ;i++){
                    nextInt = currentBlock.bufferedReader.readLine();
                    if (nextInt == null){
                        currentBlock.endReading = true;
                        break;
                    } else {
                        currentBlock.intBlock[currentBlock.blockSize++] = Integer.parseInt(nextInt);
                    }
                }
                if (currentBlock.blockSize > 0) {
                    currentBlock.topElement = currentBlock.intBlock[0];
                    shift(blocks);
                } else {
                    blocks.remove(currentBlock);
                    currentBlock.bufferedReader.close();
                    Files.delete(Paths.get(currentBlock.path));
                }
            }
        } else {
            currentBlock.topElement = currentBlock.intBlock[++currentBlock.index];
            shift(blocks);
        }
    }
    private static void shift(ArrayList<Block> blocks){
        Block block = blocks.get(0);
        blocks.remove(0);
        int newIndex = binarySearch(blocks,block.topElement,0,blocks.size());
        blocks.add(newIndex,block);
    }

    private static void MultiPathMerge(int startIndex,int countOfFiles,String outFileName) throws IOException {
        int count = countOfFiles - startIndex + 1;
        int blockSize = MEMORY_SIZE / (countOfFiles - startIndex + 2);
        String path;
        ArrayList<Block> blocks = new ArrayList<>();
        for(int i = startIndex;i<=countOfFiles;i++){
            path = i+"data.txt";
            BufferedReader bufferedReaderNew = new BufferedReader(new FileReader(path));
            Block newBlock = new Block(bufferedReaderNew,new Integer [blockSize],path);
            blocks.add(newBlock);

            String nextInt;
            int currentSize = 0;
            for (int j = 0;j<blockSize;j++){
                nextInt = bufferedReaderNew.readLine();
                if (nextInt == null){
                    newBlock.endReading =true;
                    break;
                } else {
                    newBlock.intBlock[currentSize++] = Integer.parseInt(nextInt);
                }
            }
            newBlock.index = 0;
            newBlock.blockSize = currentSize;

            if (newBlock.blockSize>0){
                newBlock.topElement = newBlock.intBlock[0];
            } else {
                blocks.remove(newBlock);
            }
        }
        blocks.sort(Comparator.comparing(x->x.topElement));
        File outputFile = new File(outFileName);
        PrintWriter outputPrintWriter = new PrintWriter(outputFile);
        int [] outputBlock = new int[blockSize];
        int realSize = 0;
        while (blocks.size() > 0) {
            for (int i =0;i<blockSize;i++){
                if (blocks.size()>0){
                    outputBlock[realSize++] = blocks.get(0).topElement;
                    refresh(blocks);  // Бинарный поиск, смещение и обновление верхнего элемента
                } else break;
            }
            for (int i=0;i<realSize;i++){
                outputPrintWriter.println(outputBlock[i]);
            }
            realSize = 0;
        }
        outputPrintWriter.close();
    }

    static void sort(String inFileName, String outFileName) throws IOException {
        Path path = Paths.get("").resolve(inFileName);
        File file = new File(path.toString());
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        PrintWriter printNewFile;
        int countOfFiles = 0;
        boolean flag = false;
        Integer [] arr = new Integer[MEMORY_SIZE];
        for (;!flag;countOfFiles++){
            String line;
            int sizeOfArr = MEMORY_SIZE;
            for (int i = 0;i<MEMORY_SIZE;i++){
                line = bufferedReader.readLine();
                if (line!=null){
                    arr [i] = Integer.parseInt(line);
                } else {
                    flag = true;
                    sizeOfArr = i;
                    break;
                }
            }
            if (sizeOfArr != 0){
                if (sizeOfArr != MEMORY_SIZE){
                    Integer [] newArr = new Integer[sizeOfArr];
                    for (int j =0;j<MEMORY_SIZE;j++){
                        newArr[j] = arr[j];
                    }
                    arr = newArr;
                }
                MergeSort.MergeSort(arr,0,sizeOfArr);
                printNewFile = new PrintWriter(new FileOutputStream(new File(countOfFiles+1+"data.txt")));
                for (Integer integer:arr){
                    printNewFile.println(integer);
                }
                printNewFile.close();
            }
            else {
                break;
            }
        }
        if (countOfFiles < MEMORY_SIZE){
            MultiPathMerge(1,countOfFiles,outFileName);
            // Многопутевое слияние
        } else {
            int lastIndex = countOfFiles;
            int startIndex = 1;
            while (countOfFiles >= MEMORY_SIZE){
                int countOfExtraFiles = countOfFiles - MEMORY_SIZE + 1;
                int mergingCount;
                if (countOfExtraFiles >= countOfFiles / 2 )
                    mergingCount = 2* (countOfFiles / 2 );
                else mergingCount = 2*countOfExtraFiles;
                int newIndex = startIndex + mergingCount -1;
                for (int i  = startIndex + mergingCount -1; i>=startIndex;i-=2){
                    File file1 = new File (i+"data.txt");
                    File file2 = new File((i-1)+"data.txt");
                    Path pathNewFile = Paths.get("").resolve(newIndex+"data.txt");
                    mergeTwo(file1,file2,pathNewFile.toString());
                    newIndex--;
                }
                startIndex = newIndex+1;
                countOfFiles = lastIndex - startIndex + 1;
            }
            // Многопутевое слияние
            MultiPathMerge(startIndex,startIndex + countOfFiles-1,outFileName);
        }
    }

    public static void main(String[] args) throws IOException {
        sort("data.txt","sorted.txt");
    }
}
