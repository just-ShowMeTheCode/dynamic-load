package com.wdl.dynamicload.datasource.dto.datasourcemanage;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * <p>
 * 数据源配置表CreateDTO
 * </p>
 *
 * @author fumj
 * @since 2022-02-22
 */
@Data
public class DatasourceManageCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsType;

    private String name;

    Map<String, Object> dsConfig;

    private Integer dsEnable;

    String dslExpress;

}
