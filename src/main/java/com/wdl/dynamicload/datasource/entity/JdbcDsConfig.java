package com.wdl.dynamicload.datasource.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * jdbc 数据库配置信息
 * </p>
 *
 * @author fumj
 * @version 1.0
 * @date 2022/2/22 10:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class JdbcDsConfig {

    private String driverClassName;

    private String url;

    private String username;

    private String password;
}
