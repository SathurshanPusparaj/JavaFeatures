package org.projectx.javafeatures.file;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * After exploring so many methods of writing to a file, let’s discuss some important notes:
 *
 * If we try to read from a file that doesn’t exist, a FileNotFoundException will be thrown.
 * If we try to write to a file that doesn’t exist, the file will be created first and no exception will be thrown.
 * It is very important to close the stream after using it, as it is not closed implicitly, to release any resources associated with it.
 * In output stream, the close() method calls flush() before releasing the resources, which forces any buffered bytes to be written to the stream.
 *
 * __________________________________________________________________________________________________________________________________
 * Looking at the common usage practices, we can see, for example, that PrintWriter is used to write formatted text,
 * FileOutputStream to write binary data, DataOutputStream to write primitive data types,
 * RandomAccessFile to write to a specific position, and FileChannel to write faster in larger files.
 * Some of the APIs of these classes do allow more, but this is a good place to start.
 */
public class CSVFileWriter {

    private void usingWriteString(Path path, String text) throws IOException {
        Files.writeString(path, text);
    }

    private void usingFileWriter(File file, String text) throws IOException {
        try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file))) {
            fileWriter.write(text); // will override the content
        }
    }

    private void appendStringWithOldContent(File file, String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(' ');
            writer.append(text);
        }
    }

    /**
     * we can use PrintWriter to write formatted text to a file
     */
    private void usePrintWriter(File file, String text) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.print("Some String");
            writer.printf("Product name is %s and its price is %d $", "IPHONE", 1000.00);
        }
    }

    private void usingOutputStream(File file, String text) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(text.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void usingDataOutputStream(File file, String text) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file);
            DataOutputStream dataStream = new DataOutputStream(new BufferedOutputStream(outputStream))) {
            dataStream.writeUTF(text);
        }
    }

    private void writeToPosition(File file, long position, String data) throws IOException {
        try(RandomAccessFile writer = new RandomAccessFile(file, "rwd")) {
            writer.seek(position);
            writer.writeBytes(data);
        }
    }

    /**
     * If we are dealing with large files, FileChannel can be faster than standard IO
     */
    private void writeFileUsingChannel(File file, byte[] data) throws IOException {
        try(RandomAccessFile stream = new RandomAccessFile(file, "rwd");
            FileChannel channel = stream.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(data.length);
            buffer.put(data);
            buffer.flip();
            channel.write(buffer);
        }
    }

    /**
     * when writing to a file, we sometimes need to make extra sure that no one else is writing to that file at the same time.
     * Basically, we need to be able to lock that file while writing.
     */
    private void lockFile_usingChannel(File file, byte[] data) throws IOException {
        try(RandomAccessFile stream = new RandomAccessFile(file, "rwd");
            FileChannel channel = stream.getChannel()) {

            FileLock fileLock = channel.tryLock();
            stream.write(data);
            fileLock.release();
        }
    }

    private void createTempFileAndWrite(String data) throws IOException {
        File tmp = File.createTempFile("testTemp", ".csv");
        FileWriter writer = new FileWriter(tmp);
        writer.write(data);
        writer.close();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = new File(CSVFileWriter.class.getResource("/writefile.csv").toURI());

        CSVFileWriter fileWriter = new CSVFileWriter();
       // fileWriter.usingWriteString(file.toPath(), "abc,kgf");
        //fileWriter.usingFileWriter(file, "123,456,789");
        //fileWriter.usingOutputStream(file, "!@#,$%^,&*|");
        fileWriter.writeToPosition(file, 2, "This is sathu");
    }
}
