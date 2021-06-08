package com.fanxuankai.boot.generator.service.impl;

import com.fanxuankai.boot.generator.dao.UserDao;
import com.fanxuankai.boot.generator.dto.UserDTO;
import com.fanxuankai.boot.generator.model.User;
import com.fanxuankai.boot.generator.service.UserService;
import com.fanxuankai.boot.generator.service.mapstruct.UserConverter;
import com.fanxuankai.boot.generator.vo.UserVO;
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
 * @date 2021-06-04
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserDTO, UserVO, UserConverter, UserDao> implements UserService {
    @Override
    public void download(List<UserVO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserVO user : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("类型 枚举: materielType", user.getType());
            map.put("子类型", user.getSubType());
            map.put("编码", user.getCode());
            map.put("名称", user.getName());
            map.put("型号", user.getModel());
            map.put("规格", user.getSpecs());
            map.put("单位", user.getUnit());
            map.put("备注", user.getRemarks());
            map.put("关联物料 id", user.getRelationId());
            map.put("关联物料 code", user.getRelationCode());
            map.put("关联物料名称", user.getRelationName());
            map.put("创建人", user.getCreateUserId());
            map.put("创建时间", user.getCreateDate());
            map.put("修改人", user.getModifiedUserId());
            map.put("修改时间", user.getLastModifiedDate());
            list.add(map);
        }
        ExcelDownloadUtils.downloadExcel(list, response);
    }
}