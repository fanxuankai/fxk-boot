package com.fanxuankai.boot.fluent.mybatis.domain;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.LogicDelete;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.generator.annotation.Table;
import cn.org.atool.generator.annotation.Tables;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author fanxuankai
 */
@FluentMybatis
@Data
@Accessors(chain = true)
@Tables(tables = {
        @Table(value = {"student_score"}, logicDeleted = "is_deleted")
})
public class StudentScore implements IEntity {
    private Long id;
    private Long studentId;
    private Boolean genderMan;
    private Integer schoolTerm;
    private String subject;
    private Integer score;
    private Date gmtCreate;
    private Date gmtModified;
    @TableField(
            value = "is_deleted",
            insert = "0"
    )
    @LogicDelete
    private Boolean isDeleted;

    @Override
    public Class<? extends IEntity> entityClass() {
        return StudentScore.class;
    }
}
