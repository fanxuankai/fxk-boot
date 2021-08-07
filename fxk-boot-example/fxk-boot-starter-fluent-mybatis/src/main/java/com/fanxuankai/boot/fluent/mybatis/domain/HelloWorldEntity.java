package com.fanxuankai.boot.fluent.mybatis.domain;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author fanxuankai
 */
@FluentMybatis
@Data
@Accessors(chain = true)
public class HelloWorldEntity implements IEntity {
    @TableId
    private Long id;
    private String sayHello;
    private String yourName;
    private Date gmtCreate;
    private Date gmtModified;
    private Boolean isDeleted;
}