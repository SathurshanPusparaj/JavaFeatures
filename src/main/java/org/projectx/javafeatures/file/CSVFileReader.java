package org.projectx.javafeatures.file;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVFileReader {
    public CSVFileReader() {

    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = new File(CSVFileReader.class.getResource("/abc.csv").toURI());
        //useFiles(file);
        //useFileReader(file);
        //bufferedFileReader(file);
        useScannerEfficientSingle(file);
        //useScannerEfficientMultiple(file);
        //useFileChannel(file);
    }

    static void useFileReader(File file) throws URISyntaxException, IOException {
        try (FileReader fileReader = new FileReader(file)) {
            int i;
            while ((i=fileReader.read()) != -1) {
                System.out.println((char)i);
            }
        }
    }

    static void useFiles(File file) throws IOException {
        //Files.readAllLines(file.toPath());
        List<String[]> list = Files.lines(file.toPath()).map(item -> item.split(",")).toList();
        list.forEach(item -> Arrays.stream(item).forEach(System.out::println));
    }

    static void readFromDataInputStream(File file) {
        try (DataInputStream reader = new DataInputStream(new FileInputStream(file))){
            System.out.println(reader.readUTF());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void bufferedFileReader(File file) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println(str);
            }
        }
    }

    //Easy to read single line, easy to tokenize (not require to explicit tokenize)
    static void useScannerEfficientSingle(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }

    //Easy to read multiline line, easy to tokenize (not require to explicit tokenize)
    //scanner.useDelimiter(","); not work for nextLine
    static void useScannerEfficientMultiple(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }

    static void randomFileAccessReader(File file, long position) throws FileNotFoundException {
        try (RandomAccessFile reader = new RandomAccessFile(file, "r")) {
            reader.seek(position);
            System.out.println(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Advantages of FileChannel
     * The advantages of FileChannel include:
     *
     * Reading and writing at a specific position in a file
     * Loading a section of a file directly into memory, which can be more efficient
     * We can transfer file data from one channel to another at a faster rate
     * We can lock a section of a file to restrict access by other threads
     * To avoid data loss, we can force writing updates to a file immediately to storage
     */
    static void useFileChannel(File file) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            StringBuilder line = new StringBuilder();

            int bytesRead;
            while ((bytesRead = fileChannel.read(buffer)) != -1) {
                buffer.flip();
                System.out.println(bytesRead);
                while (buffer.hasRemaining()) {
                    //System.out.println((char) buffer.get()); // to read character by character
                    char ch = (char) buffer.get();
                    if (ch == '\n') {
                        System.out.println(line);
                        line.setLength(0);
                    } else {
                        line.append(ch);
                    }
                }
                buffer.clear();
            }
        }
    }
}
