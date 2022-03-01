package com.wdl.dynamicload.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

import cn.hutool.core.io.FileUtil;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author fumj
 * @version 1.0
 * @date 2022/3/1 16:02
 */
public class DynamicLoading {
    // 用于保存jar相关信息，便于对应操作
    Map<String, ExtendJarEntity> cLoader = new HashMap<>();

    public DynamicLoading() {

    }

    // 动态添加jar到classpath
    public boolean loadJar(String name, String jarPath) {
        // 判断唯一
        if (isExit(name, jarPath)) {
            return false;
        }
        try {
            // 获取classloader
            URLClassLoader classloader = (URLClassLoader)Thread.currentThread().getContextClassLoader().getParent();
            // 获取 URLClassLoader addURL() 方法
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] {URL.class});
            // 设置可访问
            if (!add.isAccessible()) {
                add.setAccessible(true);

            }
            // 获取jar路径
            File jarFile = FileUtil.file(jarPath);
            // 执行addURL()方法
            add.invoke(classloader, new Object[] {jarFile.toURI().toURL()});

            // 储存信息
            ExtendJarEntity ex = new ExtendJarEntity();
            ex.setName(name);
            ex.setUrlClassLoader(classloader);
            ex.setUrl(jarPath);
            ex.setJarFile(new JarFile(jarFile));
            cLoader.put(name, ex);

            // 成功返回true
            return true;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 如果出错则返回false
        return false;
    }

    // 关闭classloader
    public boolean close(String name) {
        // 判断是否有相应jar信息
        if (!isExit(name, null)) {
            return false;
        }

        try {
            // 获取对应classloader
            ExtendJarEntity ex = cLoader.get(name);

            // 此步为确定关闭所有连接池所作，不是所有jar都需要做，所以请根据实际情况进行操作
            // 关闭前更换Hutool-db数据池
            // DSFactory.setCurrentDSFactory(new PooledDSFactory());

            // 关闭
            ex.getJarFile().close();

            // 移除关闭记录
            cLoader.remove(name);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 获取保存的jar信息列表
    public List<ExtendJarEntity> getList() {
        List<ExtendJarEntity> list = null;
        if (!cLoader.isEmpty()) {
            for (ExtendJarEntity ex : cLoader.values()) {
                list.add(ex);
            }
        }
        return list;
    }

    // 判断是否存在cLoader数据
    public boolean isExit(String name, String jarPath) {
        // 如果cLoader为空，则不存在，直接返回false
        if (cLoader.isEmpty()) {
            return false;
        }
        if (!name.isEmpty()) {
            for (String cname : cLoader.keySet()) {
                if (cname.equals(name)) {
                    return true;
                }
            }
        }
        if (jarPath.isEmpty()) {
            for (ExtendJarEntity ex : cLoader.values()) {
                if (ex.getUrl().equals(jarPath)) {
                    return true;
                }
            }
        }

        return false;
    }
}
