package com.houhong.config;

import com.houhong.domain.User;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-28 16:28
 **/
public class PropertiesHttpMessageConverter extends AbstractHttpMessageConverter<User> {

    // 用于仅仅只处理我自己自定义的指定的MediaType
    private static final MediaType DEFAULT_MEDIATYPE = MediaType.valueOf("application/properties");

    public PropertiesHttpMessageConverter() {
        super(DEFAULT_MEDIATYPE);
        setDefaultCharset(StandardCharsets.UTF_8);
    }


    @Override
    protected boolean supports(Class<?> clazz) {

        return clazz.isAssignableFrom(User.class);
    }

    @Override
    protected User readInternal(Class<? extends User> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream is = inputMessage.getBody();
        Properties props = new Properties();
        props.load(is);

        // user的三个属性
        String id = props.getProperty("id");
        String name = props.getProperty("name");
        String age = props.getProperty("age");
        return new User(Integer.valueOf(id), name, Integer.valueOf(age));

    }

    @Override
    protected void writeInternal(User user, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        OutputStream os = outputMessage.getBody();
        Properties properties = new Properties();

        properties.setProperty("id", user.getId().toString());
        properties.setProperty("name", user.getName());
        properties.setProperty("age", user.getAge().toString());
        properties.store(os, "user comments");
    }
}