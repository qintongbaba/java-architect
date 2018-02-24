package org.wuqinghua.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.SortedMap;

/*
 * Created by wuqinghua on 18/2/13.
 * 一、通道(Channel)：用于源节点与目标节点的连接。在Java NIO中负责缓冲区中数据的传输。Channel本身不存储数据，因此需要配合缓冲区进行传输。
 * 二、通道的主要实现类
 *          java.nio.channels.Channel
 *              |-- FileChannel
 *              |-- SocketChannel
 *              |-- ServerSocketChannel
 *              |-- DatagramChannel
 * 三、获取通道
 * 1.Java 针对支持通道的类提供了 getChannel()方法
 *          本地IO
 *          FileInputStream/FileOutputStream
 *          RandomAccessFile
 *          网络IO
 *          Socket
 *          ServerSocket
 *          DatagramSocket
 * 2.在JDK 1.7 中的NIO2（AIO）针对各个通道提供了静态方法open()
 * 3.在JDK 1.7 中的NIO2（AIO）的Files工具类newByteChannel
 * 4.通道之间的数据传输
 *      transferFrom()
 *      transferTo()
 *
 * 五、分散（Scatter）与聚集（Gather）
 * 分散读取：将通道中的数据分散到多个缓存区
 * 聚集写入：将多个缓冲区中的数据写到一个通道中
 *
 * 六、字符集
 *
 */
public class TestChannel {


    @Test
    public void test05() throws CharacterCodingException {
//        SortedMap<String, Charset> charsets = Charset.availableCharsets();
//        System.out.println(charsets);


        Charset charset = Charset.forName("utf-8");
        //获取编码器
        CharsetEncoder charsetEncoder = charset.newEncoder();
        //获取解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();

        CharBuffer cbuf = CharBuffer.allocate(1024);
        cbuf.put("吴清华威武!".toCharArray());
        cbuf.flip();

        //编码
        ByteBuffer bbuf = charsetEncoder.encode(cbuf);

        //解码
        CharBuffer decode = charsetDecoder.decode(bbuf);

//        decode.flip();
        System.out.println(decode.toString());
    }

    @Test
    public void test04() throws Exception {
        RandomAccessFile file1 = new RandomAccessFile(new File("1.txt"), "rw");

        FileChannel channel = file1.getChannel();


        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //分散读取
        channel.read(new ByteBuffer[]{buf1, buf2});

        buf1.flip();
        buf2.flip();

        System.out.println(new String(buf1.array(), 0, buf1.limit()));
        System.out.println(new String(buf2.array(), 0, buf2.limit()));


        RandomAccessFile file2 = new RandomAccessFile(new File("2.txt"), "rw");

        FileChannel outChannel = file2.getChannel();
        outChannel.write(new ByteBuffer[]{buf1, buf2});


        outChannel.close();
        channel.close();
    }

    //3.通道之间的数据传输
    @Test
    public void test03() throws Exception {
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpeg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpeg"), StandardOpenOption.WRITE,
                StandardOpenOption.READ, StandardOpenOption.CREATE);


        inChannel.transferTo(0, inChannel.size(), outChannel);

        inChannel.close();
        outChannel.close();

    }

    //2.直接缓冲区完成文件的复制(内存映射文件)
    @Test
    public void test02() throws Exception {
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpeg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpeg"), StandardOpenOption.WRITE,
                StandardOpenOption.READ, StandardOpenOption.CREATE);

        //创建内存映射文件
        MappedByteBuffer inMappedByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0,
                inChannel.size());
        MappedByteBuffer outMappedByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0,
                inChannel.size());

        //直接对缓冲区读写操作
        byte[] dst = new byte[inMappedByteBuffer.limit()];
        inMappedByteBuffer.get(dst);
        outMappedByteBuffer.put(dst);

        inChannel.close();
        outChannel.close();
    }


    //1.利用通道完成文件的复制
    @Test
    public void test01() throws IOException {
        FileInputStream in = new FileInputStream("1.jpeg");
        FileOutputStream out = new FileOutputStream("2.jpeg");


        //获取通道
        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();

        //分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //将通道中的数据存入到缓冲区
        while (inChannel.read(buf) != -1) {
            //将缓冲中的数据写到通道中
            buf.flip();
            outChannel.write(buf);
            buf.clear();// 清空缓冲区
        }

        outChannel.close();
        inChannel.close();
        out.close();
        in.close();
    }
}
