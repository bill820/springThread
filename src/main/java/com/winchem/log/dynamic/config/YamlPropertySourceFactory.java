package com.winchem.log.dynamic.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

/**
 * @program: aftersale
 * @description: ClassPathResource自定义yml文件读取Factory
 * @author: zhanglb
 * @create: 2020-10-27 10:47
 */

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        Properties propertiesFromYaml = loadYamlIntoProperties(resource);
        String sourceName = name != null ? name : resource.getResource().getFilename();
        return new PropertiesPropertySource(sourceName, propertiesFromYaml);
    }

    private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (IllegalStateException e) {
            // for ignoreResourceNotFound
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException) {
                throw (FileNotFoundException) e.getCause();
            }
            throw e;
        }
    }

    public static void main(String[] args) {
        String fileName = "/config/application-configparam.yml";

        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        try {
            //绝对路径
            //yaml.setResources(new FileSystemResource(fileName))；
            yaml.setResources(new ClassPathResource(fileName));

        }catch (Exception e){

        }
        Properties properties =  yaml.getObject();
        String s = properties.getProperty("array.device.name");
        System.out.println(s);
        /*//可以加载多个yml文件
        YamlPropertiesFactoryBean yamlMapFactoryBean = new YamlPropertiesFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource("application-configparam.yml"));
        Properties properties = yamlMapFactoryBean.getObject();
        //获取yml里的参数
        String active = properties.getProperty("array.device.name");
        System.out.println("device:"+active);*/
    }
}
