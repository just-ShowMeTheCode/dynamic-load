package com.wdl.dynamicload.util;

import java.net.URLClassLoader;
import java.util.jar.JarFile;

import lombok.Data;

/**
 * <p>
 * 保存上传的jar信息
 * </p>
 *
 * @author fumj
 * @version 1.0
 * @date 2022/3/1 16:03
 */
@Data
public class ExtendJarEntity {

    // 驱动名
    String name;
    // 驱动url
    String url;
    // URLClassloader
    URLClassLoader urlClassLoader;
    // 驱动jar包，用于关闭对应jar
    JarFile jarFile;

}
