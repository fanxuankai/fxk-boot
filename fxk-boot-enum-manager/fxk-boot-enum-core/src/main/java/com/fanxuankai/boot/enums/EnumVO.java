package com.fanxuankai.boot.enums;

import com.fanxuankai.boot.enums.domain.Enum;
import com.fanxuankai.boot.enums.domain.EnumType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 枚举 VO
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class EnumVO {
    private EnumType enumType;
    private List<Enum> enumList;
}
