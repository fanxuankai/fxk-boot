package com.fanxuankai.boot.enums.web;

import com.fanxuankai.boot.enums.EnumVO;
import com.fanxuankai.boot.enums.service.EnumService;
import com.fanxuankai.commons.domain.Result;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 枚举
 *
 * @author fanxuankai
 */
@RestController
@RequestMapping("/enum")
public class EnumController {
    @Resource
    private EnumService enumService;

    /**
     * 查枚举
     *
     * @param typeName 枚举类名
     * @return vo
     */
    @GetMapping("/get/{typeName}")
    public Result<EnumVO> get(@PathVariable String typeName) {
        EnumVO enumVO = enumService.find(typeName).orElse(null);
        return Result.newResult(enumVO);
    }

    /**
     * 批量查枚举
     *
     * @param typeNames 枚举名, 传空时查所有枚举
     * @return list
     */
    @PostMapping("/list")
    public Result<List<EnumVO>> list(@RequestBody(required = false) List<String> typeNames) {
        List<EnumVO> list = CollectionUtils.isEmpty(typeNames) ? enumService.all() : enumService.list(typeNames);
        return Result.newResult(list);
    }
}
