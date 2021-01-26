package com.wdl.dynamicload.controller;

import com.wdl.dynamicload.util.DynamicURLClassLoader;
import com.wdl.dynamicload.util.SpringContextUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * <p>Title: TestController</p>
 * <p>Description: ClassDescription</p>
 * <p>Copyright: Copyright (c) 2020</p>
 *
 * @author wangdali
 * @version 1.0
 * @date 2021/1/25 16:13
 */
@RestController
@RequestMapping("test")
public class DynamicLoadTestController {

    @PostMapping("jar/load")
    public Integer dynamicLoadJar(MultipartFile file) {

        return null;
    }

    @GetMapping("jar/load")
    public Integer getDynamicLoadJar() throws MalformedURLException {
        String jarPath = " file:///H:\\dump\\tt.jar";
        DynamicURLClassLoader classLoader = new DynamicURLClassLoader(new URL[] {new URL(jarPath)}, Thread.currentThread().getContextClassLoader());
        List<Class> classList = classLoader.getClassByPredicate(SpringContextUtil::isSpringBeanClass);
        SpringContextUtil.registerBean(classList.toArray(new Class[classList.size()]));
        return null;
    }

    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        String jarPath = " file:///H:/dump/tt.jar";
        URL url = new URL(jarPath);
        DynamicURLClassLoader classLoader = new DynamicURLClassLoader(new URL[] {url}, Thread.currentThread().getContextClassLoader());
        List<Class> classList = classLoader.getClassByPredicate(SpringContextUtil::isSpringBeanClass);
        System.out.println(classList.size());
    }

    @GetMapping("check")
    public boolean checkIsLoad(String name) {
        return SpringContextUtil.getBean(name) != null;
    }

}
