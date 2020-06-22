package admin.util;




import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

public class OSSUtil {

    private static final String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    private static final String ACCESSKEYID = "LTAI4FqMXVwYCeKKvMtnrimk";
    private static final  String ACCESSKEYSECRET = "ACnhj8pNY4ifkG0mkR5uc2AlLLMYDW";
    private static final String BUCKETNAME = "huahou";
    private static final String URL = "https://huahou.oss-cn-beijing.aliyuncs.com/";

    private static String getSuffix(String fileName) {
        int i = fileName.lastIndexOf(".");
        String s = fileName.substring(i);
        return  s;
    }

    public static String upload(InputStream inputStream, String fileName){

// 创建OSSClient实例。
       //OSS ossClient = new OSSClientBuild().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        try {
            String name= UUID.randomUUID().toString()+getSuffix(fileName);
            String pathName = DateTime.date2str(new Date(), DateTime.Y_M_D);
            String objectName=pathName+"/"+name;
         ossClient.putObject(BUCKETNAME,objectName , inputStream);
            return URL+objectName;
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }// 关闭OSSClient。
            ossClient.shutdown();
        }


    }

    public static void deletes(String imgPath){

        String objectName = imgPath.replace(URL,"");

// 创建OSSClient实例。
       OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);

// 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
       ossClient.deleteObject(BUCKETNAME, objectName);

// 关闭OSSClient。
      ossClient.shutdown();
    }

}
