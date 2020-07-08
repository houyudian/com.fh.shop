import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

   @Test
    public static void main(String[] args) throws MessagingException, MessagingException {

        // 创建Properties 类用于记录邮箱的一些属性
        Properties pro = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
       pro.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
       pro.put("mail.smtp.host", "smtp.qq.com");
        //端口号，QQ邮箱端口587
       pro.put("mail.smtp.port", "587");
        // 此处填写，写信人的账号
       pro.put("mail.user", "389168944@qq.com");
        // 此处填写16位STMP口令
       pro.put("mail.password", "yhwpqpuyqtiybhdd");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
               // String userName = pro.getProperty("mail.user");
               // String password = pro.getProperty("mail.password");
               //return new PasswordAuthentication(userName, password);
               return null;
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(pro, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(pro.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人的邮箱
        InternetAddress to = new InternetAddress("532028476@qq.com");
        message.setRecipient(MimeMessage.RecipientType.TO, to);

        // 设置邮件标题
        message.setSubject("1906b-侯丽静");

        // 设置邮件的内容体
        message.setContent("邮箱发送中", "text/html;charset=UTF-8");

        // 最后当然就是发送邮件啦
        Transport.send(message);

    }

}
