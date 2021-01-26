package com.wdl.dynamicload.util;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <p>Title: DynamicLoadJarUtil</p>
 * <p>Description: 动态加载外部jar</p>
 * <p>Copyright: Copyright (c) 2020</p>
 *
 * @author wangdali
 * @version 1.0
 * @date 2021/1/21 9:26
 */
public class DynamicLoadJarUtil {



    public void load(File jarFile) {
        JarFile jar = null;
        try {
            jar = new JarFile(jarFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration<JarEntry> en = jar.entries();
        while (en.hasMoreElements()) {
            JarEntry entry = en.nextElement();
            if (entry.getName().endsWith(".class")) {

            }
        }
    }

}
