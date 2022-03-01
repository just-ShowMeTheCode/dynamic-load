package com.wdl.dynamicload.datasource.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdl.dynamicload.datasource.dto.datasourcemanage.DatasourceManageCreateDTO;
import com.wdl.dynamicload.datasource.dto.datasourcemanage.DatasourceManageUpdateDTO;
import com.wdl.dynamicload.datasource.entity.DatasourceManage;
import com.wdl.dynamicload.datasource.vo.datasourcemanage.DatasourceManageVO;

/**
 * <p>
 * 数据源配置表 服务类
 * </p>
 *
 * @author fumj
 * @since 2022-02-22
 */
public interface DatasourceManageService extends IService<DatasourceManage> {

    /**
     * 查询数据源配置表
     *
     * @param datasourceManageId
     *            - Long
     */
    DatasourceManageVO getDatasourceManage(Long datasourceManageId);

    /**
     * 查询数据源配置表
     *
     * @param name
     * @return
     */
    DatasourceManageVO getDatasourceManageByName(String name);

    /**
     * 创建数据源配置表
     *
     * @param datasourceManageCreateDTO
     *            - DatasourceManageCreateDTO
     */
    void createDatasourceManage(DatasourceManageCreateDTO datasourceManageCreateDTO);

    /**
     * 更新数据源配置表
     *
     * @param datasourceManageUpdateDTO
     *            - DatasourceManageUpdateDTO
     */
    void updateDatasourceManage(DatasourceManageUpdateDTO datasourceManageUpdateDTO);

    /**
     * 删除数据源配置表
     *
     * @param datasourceManageId
     *            - Long
     */
    void deleteDatasourceManage(Long datasourceManageId);

    /**
     * 删除数据源配置表
     *
     * @param name
     */
    void deleteDatasourceManageByName(String name);

    /**
     * 数据源测试
     *
     * @param params
     * @return
     */
    Map<String, Object> testDs(Map<String, Object> params);

    /**
     * 检测数据源是否有效
     *
     * @param datasourceManageCreateDTO
     * @return
     */
    Map<String, Object> check(DatasourceManageCreateDTO datasourceManageCreateDTO);

    Map<String, Object> loadJar();
}
