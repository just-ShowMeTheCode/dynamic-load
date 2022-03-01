package com.wdl.dynamicload.datasource.dto.datasourcemanage;

import java.io.Serializable;

import com.wdl.dynamicload.datasource.enums.DsType;

import lombok.Data;

/**
 * <p>
 * 数据源配置表UpdateDTO
 * </p>
 *
 * @author fumj
 * @since 2022-02-22
 */
@Data
public class DatasourceManageUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private Enum<DsType> dsType;

    private String name;

    private String dsConfig;

    private Integer dsEffective;

    private Integer dsEnable;

}
