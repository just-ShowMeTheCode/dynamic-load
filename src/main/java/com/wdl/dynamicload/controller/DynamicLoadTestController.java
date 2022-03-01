package com.wdl.dynamicload.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wdl.dynamicload.util.DynamicLoading;
import com.wdl.dynamicload.util.DynamicURLClassLoader;
import com.wdl.dynamicload.util.SpringContextUtil;

/**
 * <p>
 * Title: TestController
 * </p>
 * <p>
 * Description: ClassDescription
 * </p>
 * <p>
 * Copyright: Copyright (c) 2020
 * </p>
 *
 * @author wangdali
 * @version 1.0
 * @date 2021/1/25 16:13
 */
@RestController
@RequestMapping("test")
public class DynamicLoadTestController {

    @PostMapping("jar/load")
    public Integer dynamicLoadJar(MultipartFile jarFile) {
        File file =
            new File(System.getProperty("user.dir") + File.separator + DynamicURLClassLoader.DYNAMIC_JAR_SAVE_FOLDER,
                jarFile.getOriginalFilename());
        if (file.exists()) {
            return 0;
        }
        System.out.println(file.getPath());
        saveFileByMultipartFile(file, jarFile);
        DynamicURLClassLoader classLoader =
            DynamicURLClassLoader.getInstance(Thread.currentThread().getContextClassLoader());
        List<Class> classList = classLoader.getClassByPredicate(file);
        SpringContextUtil.registerBean(classList.toArray(new Class[classList.size()]));
        return classList.size();
    }

    @PostMapping("/load_jar")
    public Integer loadJar() {
        String jarPath = "/Users/fumj/.m2/repository/mysql/mysql-connector-java/8.0.22/mysql-connector-java-8.0.22.jar";
        // DynamicURLClassLoader classLoader =
        // DynamicURLClassLoader.getInstance(Thread.currentThread().getContextClassLoader());
        // List<Class> classList = classLoader.getClassByPredicate(new File(jarPath));
        // SpringContextUtil.registerBean(classList.toArray(new Class[classList.size()]));

        DynamicLoading dynamicLoading = new DynamicLoading();
        dynamicLoading.loadJar("mysql-connector-java-8.0.22.jar", jarPath);
        return 10;
    }

    private void saveFileByMultipartFile(File file, MultipartFile multipartFile) {
        byte[] bytes = new byte[1024];
        try (InputStream is = multipartFile.getInputStream()) {
            while (is.read(bytes) != -1) {
                FileUtils.writeByteArrayToFile(file, bytes, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // @GetMapping("jar/load")
    // public Integer getDynamicLoadJar() throws MalformedURLException {
    // String jarPath = "H:\\dump\\tt.jar";
    // DynamicURLClassLoader classLoader =
    // DynamicURLClassLoader.getInstance(Thread.currentThread().getContextClassLoader());
    // List<Class> classList = classLoader.getClassByPredicate(new File(jarPath), SpringContextUtil::isSpringBeanClass);
    // SpringContextUtil.registerBean(classList.toArray(new Class[0]));
    // return null;
    // }

    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        String jarPath = "/Users/fumj/.m2/repository/mysql/mysql-connector-java/8.0.23/mysql-connector-java-8.0.23.jar";
        DynamicURLClassLoader classLoader =
            DynamicURLClassLoader.getInstance(Thread.currentThread().getContextClassLoader());
        List<Class> classList = classLoader.getClassByPredicate(new File(jarPath));
        System.out.println(classList.size());
    }

    @GetMapping("check")
    public boolean checkIsLoad(String name) {
        return SpringContextUtil.getBean(name) != null;
    }

}
