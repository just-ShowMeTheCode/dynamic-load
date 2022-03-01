package com.wdl.dynamicload.datasource.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @FileName: BaseEntity
 * @Author : Yang Shengdong
 * @Version v1.0
 * @Date : Created in 2021/3/16 2:48 下午
 * @Description:
 */
@Data
public abstract class BaseEntity extends BaseEntityOnlyCreateTime {

    private static final long serialVersionUID = 1L;
    public static final String UPDATE_TIME = "updated_at";

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;

}
