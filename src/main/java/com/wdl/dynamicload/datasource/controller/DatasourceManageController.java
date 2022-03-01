package com.wdl.dynamicload.datasource.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.wdl.dynamicload.datasource.dto.datasourcemanage.DatasourceManageCreateDTO;
import com.wdl.dynamicload.datasource.dto.datasourcemanage.DatasourceManageUpdateDTO;
import com.wdl.dynamicload.datasource.service.DatasourceManageService;
import com.wdl.dynamicload.datasource.vo.datasourcemanage.DatasourceManageVO;

/**
 * <p>
 * 数据源配置表 前端控制器
 * </p>
 *
 * @author fumj
 * @since 2022-02-22
 */
@RestController
@Validated
@RequestMapping("/datasource_manage")
public class DatasourceManageController {

    @Resource
    private DatasourceManageService datasourceManageService;

    @GetMapping(path = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public DatasourceManageVO get(@PathVariable("id") Long id) {
        return datasourceManageService.getDatasourceManage(id);
    }

    @GetMapping(path = "/{name:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public DatasourceManageVO getByName(@PathVariable("name") String name) {
        return datasourceManageService.getDatasourceManageByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody @Valid DatasourceManageCreateDTO datasourceManageCreateDTO) {
        datasourceManageService.createDatasourceManage(datasourceManageCreateDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody @Valid DatasourceManageUpdateDTO datasourceManageUpdateDTO) {
        datasourceManageService.updateDatasourceManage(datasourceManageUpdateDTO);
    }

    @DeleteMapping(path = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        datasourceManageService.deleteDatasourceManage(id);
    }

    @DeleteMapping(path = "/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByName(@PathVariable("name") String name) {
        datasourceManageService.deleteDatasourceManageByName(name);
    }

    @PostMapping("/test_ds")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> testDs(@RequestBody Map<String, Object> params) {
        return datasourceManageService.testDs(params);
    }

    @PostMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> check(@RequestBody @Valid DatasourceManageCreateDTO datasourceManageCreateDTO) {
        return datasourceManageService.check(datasourceManageCreateDTO);
    }

    @PostMapping("/load_jar")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> loadJar() {
        return datasourceManageService.loadJar();
    }

}