package com.bc.rm.server.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class OssManage {

    public static String ACCESS_KEY_ID = null;

    public static String ACCESS_KEY_SECRET = null;

    public static String BUCKETNAME = null;

    public static String DISK_BUCKETNAME = null;

    public static String BASE_URL = null;

    private static OSSClient client = null;

    static {
        ACCESS_KEY_ID = "LTAInEWavQNRKFMh";
        ACCESS_KEY_SECRET = "B6TbyYH4sx8tItYBTLhKEk2JMTUIyl";

        BUCKETNAME = "erp-cfpu-com";
        DISK_BUCKETNAME = "disk-cfpu-com";
        // BASE_URL = "http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com";
        BASE_URL = "http://oss-cn-hangzhou.aliyuncs.com";
        init();
    }

    public static void init() {
        // 初始化一个OSSClient
        client = new OSSClient(BASE_URL, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

    }

    /**
     * @param key 上传为OSS文件服务器的唯一标识
     * @throws Exception
     * @Description: 上传文件到OSS文件服务器
     * @ReturnType:String OSSObject的ETag值。
     */
    public static String uploadFile(MultipartFile file, String key) {
        String eTag;
        try {
            // ObjectMetadata meta = new ObjectMetadata();
            InputStream is = new ByteArrayInputStream(file.getBytes());
            // meta.setContentLength(file.getSize());
            // meta.setContentType("image/jpeg");
            // 上传图片
            PutObjectResult result = client.putObject(BUCKETNAME, key, is);
            eTag = result.getETag();
        } catch (Exception e) {
            e.printStackTrace();
            eTag = "";
        }

        return eTag;
    }

    /**
     * @param fileName 上传为OSS文件服务器的唯一标识
     * @throws Exception
     * @Description: 上传文件到OSS文件服务器
     * @ReturnType:String OSSObject的ETag值。
     */
    public static String uploadDiskFile(MultipartFile file, String fileName, String fileType, String userId) {
        String eTag;
        try {
            long time = System.currentTimeMillis();
            String name = fileName.split("." + fileType)[0];
            String key = name + "-" + userId + "-" + time + "." + fileType;
            InputStream is = new ByteArrayInputStream(file.getBytes());
            PutObjectResult result = client.putObject(DISK_BUCKETNAME, fileName, is);
            eTag = result.getETag();
        } catch (Exception e) {
            e.printStackTrace();
            eTag = "";
        }

        return eTag;
    }

    public static void downImg(HttpServletResponse response, String fileName){
        try {
            OSSObject object = client.getObject(DISK_BUCKETNAME,fileName);
            InputStream in = object.getObjectContent();
            byte[] buffer = readInputStream(in);
            in.read(buffer);
            response.reset();
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
            OutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(buffer);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        outStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * @param key 上传为OSS文件服务器的唯一标识
     * @throws Exception
     * @Description: 上传文件到OSS文件服务器
     * @ReturnType:String OSSObject的ETag值。
     */
    public static String uploadFile2(File file, String key) {
        String eTag;
        try {
            // ObjectMetadata meta = new ObjectMetadata();
            InputStream is = new FileInputStream(file);
            // meta.setContentLength(file.getSize());
            // meta.setContentType("image/jpeg");
            // 上传图片
            PutObjectResult result = client.putObject(BUCKETNAME, key, is);
            eTag = result.getETag();
        } catch (Exception e) {
            e.printStackTrace();
            eTag = "";
        }

        return eTag;
    }

    public static String uploadFile(InputStream is, String key) {
        String eTag;
        try {
            // 上传图片
            PutObjectResult result = client.putObject(BUCKETNAME, key, is);
            eTag = result.getETag();
        } catch (Exception e) {
            e.printStackTrace();
            eTag = "";
        }

        return eTag;
    }

    /**
     * @param key OSS文件服务器上文件的唯一标识
     * @Description:删除文件
     * @ReturnType:void
     */
    public void deleteFile(String key) {
        init();
        client.deleteObject(BUCKETNAME, key);
    }


    /**
     * @param key OSS文件服务器上文件的唯一标识
     * @Description:通过key获取图片的路径
     * @ReturnType:void
     */
    public static OSSObject getFile(String key) {
        OSSObject resultObject = null;
        if (!StringUtils.isEmpty(key)) {
            resultObject = client.getObject(BUCKETNAME, key);
        }
        return resultObject;
    }


}

