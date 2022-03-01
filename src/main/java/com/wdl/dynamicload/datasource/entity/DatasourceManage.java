package com.wdl.dynamicload.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据源配置表
 * </p>
 *
 * @author fumj
 * @since 2022-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("AHI_DATASOURCE_MANAGE")
public class DatasourceManage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private String dsType;

    private String dsConfig;

    private Integer dsEffective;

    private Integer dsEnable;

}
