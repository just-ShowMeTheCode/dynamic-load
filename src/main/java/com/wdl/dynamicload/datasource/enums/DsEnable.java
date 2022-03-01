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
public enum DsEnable {
    /**
     * jdbc
     */
    ENABLE(1, null, "数据源启用"),

    /**
     * mongo
     */
    UN_ENABLE(0, null, "数据源不启用"),

    ;

    private final Integer code;

    private final String quote;

    private final String desc;

    DsEnable(final Integer code, final String quote, final String desc) {
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
    public static DsEnable fromCode(Integer code) {
        DsEnable[] dts = DsEnable.values();
        for (DsEnable dt : dts) {
            if (dt.getCode().equals(code)) {
                return dt;
            }
        }
        throw new MybatisPlusException("Error: Unknown ds type!\n");
    }

    public Integer getCode() {
        return code;
    }

}
