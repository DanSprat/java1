package ru.progwards.java1.lessons.sort;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ExternalSort {

    private static class Block{
        BufferedReader bufferedReader;
        Integer [] intBlock;
        int blockSize;
        int index=0;
        boolean endReading;
        boolean endWriting;
        int topElement;

        public Block(BufferedReader bufferedReader,Integer [] intBlock){
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

    private static int MEMORY_SIZE = 10;


    private static void writeToFile(String fileName,int sizeOfBlock,Integer [] toWriteBlock,PrintWriter printWriter) throws FileNotFoundException {
        for (int i = 0;i<sizeOfBlock;i++){
            printWriter.println(toWriteBlock[i]);
        }
    }
    private static void mergeTwo(BufferedReader bufferedReader1,BufferedReader bufferedReader2, String fileName) throws IOException {
        int sizeOfBlock = MEMORY_SIZE / 3;
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File(fileName)));
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
                   if (endOfFile1){
                       posLast = posBlock2;
                       endOfLastFile = endOfFile2;
                       sizeLastBlock = sizeBlock2;
                       lastBR = bufferedReader2;
                       lastBlock = block2;
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
                  }
               }

           } else {
               toWriteBlock[currentPosResult++] = block2[posBlock2++];
               if (posBlock2 == sizeBlock2){
                   if (endOfFile2){
                       posLast = posBlock1;
                       endOfLastFile = endOfFile1;
                       sizeLastBlock = sizeBlock1;
                       lastBR = bufferedReader1;
                       lastBlock = block1;
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
               }
           }
           if (currentPosResult == sizeOfBlock){
                writeToFile(fileName,sizeOfBlock,toWriteBlock,printWriter);
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
        printWriter.close();
    }

    static void sort(String inFileName, String outFileName) throws IOException {
        Path path = Paths.get("").resolve(inFileName);
        File file = new File(path.toString());
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        PrintWriter printNewFile;
        int countOfFiles = 1;
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
                printNewFile = new PrintWriter(new FileOutputStream(new File(countOfFiles+"data.txt")));
                for (Integer integer:arr){
                    printNewFile.println(integer);
                }
            }
        }
        if (countOfFiles < MEMORY_SIZE){
            // Многопутевое слияние
        } else {
            int lastIndex = countOfFiles;
            int startIndex = 1;
            while (countOfFiles >= MEMORY_SIZE){
                int countOfExtraFiles = countOfFiles - MEMORY_SIZE + 1;
                int mergingCount;
                if (countOfExtraFiles >= countOfFiles / 2 )
                    mergingCount = 2* (countOfFiles / 2 );
                else mergingCount = 2*(countOfExtraFiles / 2);
                int newIndex = startIndex + mergingCount -1;
                for (int i  = startIndex + mergingCount -1; i>=startIndex;i-=2){
                    BufferedReader bufferedReader1 = new BufferedReader(new FileReader(new File(i+"data.txt")));
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(new File((i-1)+"data.txt")));
                    Path pathNewFile = Paths.get("").resolve(newIndex+"data.txt");
                    mergeTwo(bufferedReader1,bufferedReader2,pathNewFile.toString());
                    newIndex--;
                }
                startIndex = newIndex+1;

            }
            // Многопутевое слияние
            int count = countOfFiles - startIndex + 1;
            int blockSize = MEMORY_SIZE / (countOfFiles - startIndex + 2);

            ArrayList<Block> blocks = new ArrayList<>();
            for(int i = startIndex;i<=countOfFiles;i++){
                BufferedReader bufferedReaderNew = new BufferedReader(new FileReader(new File(i+"data.txt")));
                Block newBlock = new Block(bufferedReaderNew,new Integer [blockSize]);
                blocks.add(newBlock);
                String nextInt = bufferedReaderNew.readLine();
                int currentSize = 0;
                while (nextInt!=null){
                    newBlock.intBlock[currentSize++] = Integer.parseInt(nextInt);
                    nextInt = bufferedReaderNew.readLine();
                }
                newBlock.blockSize = currentSize;
            }
            blocks.sort(Comparator.comparing(x->x.topElement));
            File outputFile = new File(outFileName);
            PrintWriter outputPrintWriter = new PrintWriter(outputFile);
            int [] outputBlock = new int[blockSize];
            int realSize = 0;
            while (blocks.size() > 0){
                for (int i =0;i<blockSize;i++){
                    if (blocks.size()>0){
                        outputBlock[realSize++] = blocks.get(0).topElement;
                        // Бинарный поиск
                    } else break;
                }
                for (int i=0;i<realSize;i++){
                    outputPrintWriter.println(outputBlock[i]);
                }
            }

            
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader((2+"data.txt")));
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader((1+"data.txt")));
        mergeTwo(bufferedReader1,bufferedReader2,"3data.txt");
    }
}
