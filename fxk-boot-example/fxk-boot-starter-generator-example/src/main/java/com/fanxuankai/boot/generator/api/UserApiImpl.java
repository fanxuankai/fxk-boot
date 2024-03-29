package com.fanxuankai.boot.generator.api;

import com.fanxuankai.boot.generator.dto.UserQueryCriteria;
import com.fanxuankai.boot.generator.service.UserService;
import com.fanxuankai.boot.generator.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户 API 实现类
 *
 * @author admin
 */
@RestController
@RequiredArgsConstructor
public class UserApiImpl implements UserApi {

	private final UserService userService;

    /**
     * 查询用户所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     */
    @Override
    public List<UserVO> list(UserQueryCriteria criteria) {
        return userService.list(criteria);
    }

    /**
     * 根据 ID 查询用户
     *
     * @param id ID
     * @return UserVO
     */
    @Override
    public UserVO get(Long id) {
        return userService.get(id);
    }
}