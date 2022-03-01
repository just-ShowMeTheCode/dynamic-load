package com.wdl.dynamicload.datasource.vo.datasourcemanage;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 数据源配置表VO
 * </p>
 *
 * @author fumj
 * @since 2022-02-22
 */
@Data
public class DatasourceManageVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String dsType;

    private String name;

    private String dsConfig;

    private Integer dsEffective;

    private Integer dsEnable;

}
