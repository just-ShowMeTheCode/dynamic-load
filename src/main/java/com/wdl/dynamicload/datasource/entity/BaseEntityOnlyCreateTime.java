package com.wdl.dynamicload.datasource.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @FileName: BaseEntityOnlyCreateTime
 * @Author : Yang Shengdong
 * @Version v1.0
 * @Date : Created in 2021/4/19 10:47 上午
 * @Description:
 */
@Data
public abstract class BaseEntityOnlyCreateTime implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ID = "id";
    public static final String CREATED_AT = "created_at";

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

}
