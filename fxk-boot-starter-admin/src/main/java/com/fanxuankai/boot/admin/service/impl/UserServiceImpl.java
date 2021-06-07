package com.fanxuankai.boot.admin.service.impl;

import com.fanxuankai.boot.admin.dao.UserDao;
import com.fanxuankai.boot.admin.dto.UserDTO;
import com.fanxuankai.boot.admin.model.User;
import com.fanxuankai.boot.admin.service.UserService;
import com.fanxuankai.boot.admin.service.mapstruct.UserConverter;
import com.fanxuankai.boot.admin.vo.UserVO;
import com.fanxuankai.commons.extra.mybatis.base.BaseServiceImpl;
import com.fanxuankai.commons.util.ExcelDownloadUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 服务实现类
 *
 * @author fanxuankai
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserDTO, UserVO, UserConverter, UserDao> implements UserService {
    @Override
    public void download(List<UserVO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserVO user : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("账号", user.getUsername());
            map.put("密码", user.getPassword());
            map.put("编号", user.getNo());
            map.put("姓名", user.getFullName());
            map.put("性别(男:M 女:F)", user.getGender());
            map.put("生日", user.getBirthday());
            map.put("证件号", user.getIdNo());
            map.put("证件类型", user.getIdType());
            map.put("昵称", user.getNickname());
            map.put("密码修改时间", user.getPasswordLastModifiedDate());
            map.put("移动电话", user.getMobile());
            map.put("固定电话", user.getPhone());
            map.put("地址", user.getAddress());
            map.put("邮箱", user.getEmail());
            map.put("QQ", user.getQq());
            map.put("微信", user.getWeChat());
            map.put("头像文件名", user.getAvatarName());
            map.put("头像存储路径", user.getAvatarPath());
            map.put("部门 id", user.getDeptId());
            map.put("是否为管理员", user.getAdmin());
            map.put("账号过期", user.getAccountExpired());
            map.put("账号被锁定", user.getAccountLocked());
            map.put("密码过期", user.getCredentialsExpired());
            map.put("是否激活", user.getEnabled());
            map.put("创建人", user.getCreateUserId());
            map.put("修改人", user.getModifiedUserId());
            map.put("创建时间", user.getCreateDate());
            map.put("修改时间", user.getLastModifiedDate());
            list.add(map);
        }
        ExcelDownloadUtils.downloadExcel(list, response);
    }
}