package cn.ding.arcsoft.utils;

import com.sun.jna.Native;

import java.io.*;

public class ArcUtil {

    public static void main(String[] args) {
        //createFileWithByte("Hello World!".getBytes(),"E:/abc.doc");
    }


    /**
     * 根据byte数组生成文件
     *
     * @param bytes
     *            生成文件用到的byte数组
     */
    public static void createFileWithByte(byte[] bytes,String pathname) {
        //创建File对象，其中包含文件所在的目录以及文件的命名
        File file = new File(pathname);
        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        try {
            // 如果文件存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        } finally {
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 从文件中获取byte数组
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] getContent2(String filePath){
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        byte[] bytes = null;
        try{
            in = new FileInputStream(filePath);
            out = new ByteArrayOutputStream();
            byte[] temp = new byte[4096];
            int size;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            in.close();
            bytes = out.toByteArray();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    /**
     * 获取部署根路径
     * @return
     */
    public static String getRootPath(){
        String path = Class.class.getClass().getResource("/").getPath();
        if(path.startsWith("/")){
            path = path.substring(1);
        }
        return path;
    }
}
