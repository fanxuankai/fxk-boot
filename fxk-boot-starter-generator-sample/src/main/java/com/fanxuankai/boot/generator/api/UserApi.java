package com.fanxuankai.boot.generator.api;

import com.fanxuankai.boot.generator.dto.UserQueryCriteria;
import com.fanxuankai.boot.generator.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 用户 API
 *
 * @author fanxuankai
 * @date 2021-05-21
 */
@FeignClient("user-service")
public interface UserApi {

    /**
     * 查询用户所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     */
    @GetMapping("api/user/list")
   	List<UserVO> list(@RequestBody UserQueryCriteria criteria);

    /**
     * 根据 ID 查询用户
     *
     * @param id ID
     * @return UserVO
     */
    @GetMapping("api/user/get/{id}")
    UserVO get(@PathVariable Long id);
}