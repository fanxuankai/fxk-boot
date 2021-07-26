package com.fanxuankai.boot.generator.controller;

import com.fanxuankai.boot.generator.dto.UserDTO;
import com.fanxuankai.boot.generator.dto.UserQueryCriteria;
import com.fanxuankai.boot.generator.service.UserService;
import com.fanxuankai.boot.generator.vo.UserVO;
import com.fanxuankai.commons.domain.PageRequest;
import com.fanxuankai.commons.domain.PageResult;
import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.util.ResultUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用户 管理
 *
 * @author admin
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 导出数据
     *
     * @param criteria 条件
     * @param response /
     * @throws IOException /
     */
    @GetMapping("download")
    public void download(UserQueryCriteria criteria, HttpServletResponse response) throws IOException {
        userService.download(userService.list(criteria), response);
    }

    /**
     * 查询用户数据分页
     *
     * @param criteria    条件
     * @param pageRequest 分页参数
     * @return Result
     */
    @GetMapping("page")
    public Result<PageResult<UserVO>> page(UserQueryCriteria criteria, PageRequest pageRequest) {
        return ResultUtils.ok(userService.page(criteria, pageRequest));
    }

    /**
     * 查询用户所有数据不分页
     *
     * @param criteria 条件参数
     * @return Result
     */
    @GetMapping("list")
    public Result<List<UserVO>> list(UserQueryCriteria criteria) {
        return ResultUtils.ok(userService.list(criteria));
    }

    /**
     * 根据 ID 查询用户
     *
     * @param id ID
     * @return Result
     */
    @GetMapping("get/{id}")
    public Result<UserVO> get(@PathVariable Long id) {
        return ResultUtils.ok(userService.get(id));
    }

    /**
     * 创建用户
     *
     * @param dto 数据
     * @return Result
     */
    @PostMapping("create")
    public Result<Void> create(@Validated @RequestBody UserDTO dto) {
        userService.create(dto);
        return ResultUtils.ok();
    }

    /**
     * 修改用户
     *
     * @param id  ID
     * @param dto 数据
     * @return Result
     */
    @PutMapping("update/{id}")
    public Result<Void> update(@PathVariable Long id, @Validated @RequestBody UserDTO dto) {
        userService.update(id, dto);
        return ResultUtils.ok();
    }

    /**
     * 删除用户
     *
     * @param ids ID 列表
     * @return Result
     */
    @DeleteMapping("delete")
    public Result<Void> delete(@RequestBody Long[] ids) {
        userService.deleteAll(ids);
        return ResultUtils.ok();
    }
}