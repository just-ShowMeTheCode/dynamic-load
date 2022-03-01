package com.wdl.dynamicload.datasource.enums;


/**
 * <p>
 * 数据源类型
 * </p>
 *
 * @author fumj
 * @version 1.0
 * @date 2022/2/22 15:34
 */
public enum DsEffective {
    /**
     * jdbc
     */
    EFFECTIVE(1, null, "数据源有效"),

    /**
     * mongo
     */
    UN_EFFECTIVE(0, null, "数据源失效"),

    ;

    private final Integer code;

    private final String quote;

    private final String desc;

    DsEffective(final Integer code, final String quote, final String desc) {
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
    public static DsEffective fromCode(Integer code) {
        DsEffective[] dts = DsEffective.values();
        for (DsEffective dt : dts) {
            if (dt.getCode().equals(code)) {
                return dt;
            }
        }

        return null;
    }

    public Integer getCode() {
        return code;
    }

}
