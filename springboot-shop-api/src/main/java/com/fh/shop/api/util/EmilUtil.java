package com.fh.shop.api.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmilUtil extends Authenticator {

    public static String USERNAME = "389168944@qq.com";
    public static String PASSWORD = "1.2.3.4.";

    public EmilUtil() {
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(USERNAME, PASSWORD);
    }

}
