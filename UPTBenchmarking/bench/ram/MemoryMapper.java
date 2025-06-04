package bench.ram;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMapper {
    private MappedByteBuffer buffer;
    private long size;
    private FileChannel channel;

    public MemoryMapper(String path, long size) throws IOException {
        this.size = size;
        File file = new File(path);
        if (!file.exists()) file.createNewFile();
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(size);
        channel = raf.getChannel();
        buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, size);
    }

    public void put(long offset, byte[] data) {
        buffer.position((int) offset);
        buffer.put(data);
    }

    public byte[] get(long offset, int length) {
        byte[] dst = new byte[length];
        buffer.position((int) offset);
        buffer.get(dst);
        return dst;
    }

    public long getSize() {
        return size;
    }

    public void clean() throws IOException {
        channel.close();
    }
}