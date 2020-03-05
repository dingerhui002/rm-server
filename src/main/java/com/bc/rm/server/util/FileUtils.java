package com.bc.rm.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件操作工具类
 */
public class FileUtils {

    private static final String OSS_BASE_PATH = "http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/";
    //日志记录器
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 根据文件地址返回文件的字节数组
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            try {
                fs.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    //下载文件
    public Map<String, Object> download(String urlPath, String downloadDir, String fileFullName) {
        // long beginTime = new Date().getTime();
        int errCode = 0;
        String msg = "";
        Map<String, Object> map = new HashMap();
        HttpURLConnection httpURLConnection = null;
        OutputStream out = null;
        BufferedInputStream bin = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            // http的连接类
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置超时15秒
            httpURLConnection.setConnectTimeout(1000 * 15);
            //设置请求方式，默认是GET
            httpURLConnection.setRequestMethod("GET");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
            // 文件大小
            int fileLength = httpURLConnection.getContentLength();

            // 控制台打印文件大小
            logger.info("您要下载的文件大小为:" + fileLength / 1024 + "KB");

            // 建立链接从请求中获取数据
            bin = new BufferedInputStream(httpURLConnection.getInputStream());
            // 指定存放位置(有需求可以自定义)
            String path = downloadDir + File.separatorChar + fileFullName;
            File file = new File(path);
            // 校验文件夹目录是否存在，不存在就创建一个目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[2048];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
            }
            logger.info("文件下载成功！");
        } catch (
                MalformedURLException e) {
            // TODO Auto-generated catch block
            logger.error("文件下载失败！" + e.getMessage());
            errCode = -1;
            msg = "文件下载失败！" + e.getMessage();
        } catch (
                IOException e) {
            // TODO Auto-generated catch block
            logger.error("文件下载失败！" + e.getMessage());
            errCode = -1;
            msg = "文件下载失败！" + e.getMessage();
        } finally {
            // 关闭资源
            try {
                bin.close();
                out.close();
                httpURLConnection.disconnect();
            } catch (Exception e) {
                logger.error("关闭IO流发生异常：" + e.getMessage());
                errCode = -1;
                msg = "关闭IO流发生异常！" + e.getMessage();
            }

        }
        map.put("errCode", errCode);
        map.put("msg", msg);
        //long endTime = new Date().getTime();
        //logger.info("文档下载用时:"+(endTime-beginTime));
        return map;
    }

    //删除文件
    public boolean deleteFile(String fileName) {
        if (fileName == null || fileName.equals("")) {
            logger.error("fileName为空，请确认！");
            return false;
        }
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.error("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                logger.error("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            logger.error("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    public static String getInputStreamByGet(String url,String key) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                return generatePicUrl(key,OssManage.uploadFile(inputStream,key));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成上传图片地址
     *
     * @param ossKey
     * @param ossResult
     */
    private static String generatePicUrl(String ossKey, String ossResult) {
        if (!ossResult.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(OSS_BASE_PATH);
            sb.append(ossKey);
            return sb.toString();
        }
        return null;
    }
}
