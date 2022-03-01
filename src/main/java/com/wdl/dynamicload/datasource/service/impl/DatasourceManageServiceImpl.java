package com.wdl.dynamicload.datasource.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.wdl.dynamicload.datasource.dto.datasourcemanage.DatasourceManageCreateDTO;
import com.wdl.dynamicload.datasource.dto.datasourcemanage.DatasourceManageUpdateDTO;
import com.wdl.dynamicload.datasource.entity.DatasourceManage;
import com.wdl.dynamicload.datasource.enums.DsEffective;
import com.wdl.dynamicload.datasource.enums.DsType;
import com.wdl.dynamicload.datasource.mapper.DatasourceManageMapper;
import com.wdl.dynamicload.datasource.service.DatasourceManageService;
import com.wdl.dynamicload.datasource.vo.datasourcemanage.DatasourceManageVO;
import com.wdl.dynamicload.util.BadRequestParam;
import com.wdl.dynamicload.util.DynamicURLClassLoader;

/**
 * <p>
 * 数据源配置表 服务实现类
 * </p>
 *
 * @author fumj
 * @since 2022-02-22
 */
@Service
public class DatasourceManageServiceImpl extends ServiceImpl<DatasourceManageMapper, DatasourceManage>
    implements DatasourceManageService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private DatasourceManageMapper datasourceManageMapper;

    @Resource
    private DataSource dataSource;

    @Autowired
    private DataSourceCreator hikariDataSourceCreator;

    @Override
    public DatasourceManageVO getDatasourceManage(Long datasourceManageId) {

        return null;
    }

    @Override
    public DatasourceManageVO getDatasourceManageByName(String name) {

        LambdaQueryWrapper<DatasourceManage> lambda = new QueryWrapper<DatasourceManage>().lambda();
        lambda.eq(DatasourceManage::getName, name);
        List<DatasourceManage> manageList = datasourceManageMapper.selectList(lambda);
        DatasourceManageVO vo = new DatasourceManageVO();
        if (manageList.size() > 0) {
            BeanUtils.copyProperties(manageList.get(0), vo);
        }
        return vo;
    }

    @Override
    public void createDatasourceManage(DatasourceManageCreateDTO requestDto) {
        String requestDsName = requestDto.getName();
        // DatasourceManageVO manage = getDatasourceManageByName(requestDsName);
        // if (manage != null && manage.getId() != null) {
        // throw new BadRequestParam("数据源已经存在");
        // }
        DsType dsType = DsType.fromCode(requestDto.getDsType());

        if (DsType.JDBC.equals(dsType)) {
            Map<String, Object> dsConfig = requestDto.getDsConfig();
            String dsConfigStr = new Gson().toJson(dsConfig);
            loadDs(requestDsName, dsConfigStr);
            // 保存到数据库
            DatasourceManage datasourceManage = new DatasourceManage();
            BeanUtils.copyProperties(requestDto, datasourceManage);
            datasourceManage.setDsConfig(dsConfigStr);
            datasourceManage.setDsEffective(DsEffective.EFFECTIVE.getCode());
            // datasourceManageMapper.insert(datasourceManage);

        }

    }

    private void loadDs(String dsName, String dsConfigStr) {
        DataSourceProperty dataSourceProperty = new Gson().fromJson(dsConfigStr, DataSourceProperty.class);
        // 创建数据源
        DataSource ds = hikariDataSourceCreator.createDataSource(dataSourceProperty);
        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource)dataSource;
        // 加载数据源
        dynamicRoutingDataSource.addDataSource(dsName, ds);
    }

    @Override
    public void updateDatasourceManage(DatasourceManageUpdateDTO datasourceManageUpdateDTO) {

    }

    @Override
    public void deleteDatasourceManage(Long datasourceManageId) {

    }

    @Override
    public void deleteDatasourceManageByName(String name) {

        DatasourceManageVO ds = getDatasourceManageByName(name);
        if (ds == null) {
            return;
        }
        Long id = ds.getId();
        if (id != null) {
            datasourceManageMapper.deleteById(id);
        }
        String dsType = ds.getDsType();
        if (DsType.JDBC.getCode().equals(dsType)) {
            DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource)dataSource;
            dynamicRoutingDataSource.removeDataSource(name);
        }

    }

    @Override
    public Map<String, Object> testDs(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        try {
            // DatasourceManage datasourceManage = datasourceManageMapper.selectById(1);
            String dsName = (String)params.get("dsName");
            String sql = (String)params.get("sql");
            DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource)dataSource;
            if (dynamicRoutingDataSource.getDataSources().containsKey(dsName) == false) {
                throw new BadRequestParam("数据源不存在");
            }
            DynamicDataSourceContextHolder.push(dsName);
            map = jdbcTemplate.queryForMap(sql);
        } finally {
            DynamicDataSourceContextHolder.clear();
        }
        return map;
    }

    @Override
    public Map<String, Object> check(DatasourceManageCreateDTO datasourceManageCreateDTO) {
        return null;
    }

    @Override
    public Map<String, Object> loadJar() {
        String jarPath = "/Users/fumj/.m2/repository/mysql/mysql-connector-java/8.0.22/mysql-connector-java-8.0.22.jar";
        DynamicURLClassLoader classLoader =
            DynamicURLClassLoader.getInstance(Thread.currentThread().getContextClassLoader().getParent());
        List<Class> classList = classLoader.getClassByPredicate(new File(jarPath));
        System.out.println(classList.size());
        return new HashMap<>();
    }
}
