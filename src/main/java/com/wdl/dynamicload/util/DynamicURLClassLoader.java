package com.wdl.dynamicload.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <p>Title: DynamicURLClassLoader</p>
 * <p>Description: ClassDescription</p>
 * <p>Copyright: Copyright (c) 2020</p>
 *
 * @author wangdali
 * @version 1.0
 * @date 2021/1/25 14:13
 */
public class DynamicURLClassLoader extends URLClassLoader {

    public static String DYNAMIC_JAR_SAVE_FOLDER = "dynamic_jar";

    private static DynamicURLClassLoader classLoader;

    private DynamicURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static synchronized DynamicURLClassLoader getInstance(ClassLoader parent) {
        if (classLoader == null) {
            classLoader = new DynamicURLClassLoader(new URL[]{}, parent);
        }
        return classLoader;
    }

    public List<Class> getClassByPredicate(File jar, Predicate<Class> predicate) {
        List<Class> list = new ArrayList<>();
        try {
            this.addURL(jar.toURI().toURL());
            JarFile jarFile = new JarFile(jar.getPath());
            Enumeration<JarEntry> en = jarFile.entries();
            while (en.hasMoreElements()) {
                JarEntry entry = en.nextElement();
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replaceAll("/", ".").replace(".class", "");
                    Class clazz = super.loadClass(className);
                    if (predicate.test(clazz)) {
                        list.add(clazz);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

}
