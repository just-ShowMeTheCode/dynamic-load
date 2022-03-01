package com.wdl.dynamicload.datasource.enums;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;

/**
 * <p>
 * 数据源类型
 * </p>
 *
 * @author fumj
 * @version 1.0
 * @date 2022/2/22 15:34
 */
public enum DsType {
    /**
     * jdbc
     */
    JDBC("jdbc", null, "jdbc"),

    /**
     * mongo
     */
    MONGO("mongo", null, "mongo"),

    /**
     * redis
     */
    REDIS("redis", null, "redis"),

    /**
     * elasticsearch
     */
    ES("elasticsearch", null, "elasticsearch");

    private final String code;

    private final String quote;

    private final String desc;

    DsType(final String code, final String quote, final String desc) {
        this.code = code;
        this.quote = quote;
        this.desc = desc;
    }

    /**
     * <p>
     * 获取数据库类型（默认 MySql）
     * </p>
     *
     * @param code
     *            数据库类型字符串
     * @return
     */
    public static DsType fromCode(String code)  {
        DsType[] dts = DsType.values();
        for (DsType dt : dts) {
            if (dt.getCode().equalsIgnoreCase(code)) {
                return dt;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
