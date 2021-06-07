package com.fanxuankai.boot.admin.service.impl;

import com.fanxuankai.boot.admin.dao.RoleDao;
import com.fanxuankai.boot.admin.dto.RoleDTO;
import com.fanxuankai.boot.admin.model.Role;
import com.fanxuankai.boot.admin.service.RoleService;
import com.fanxuankai.boot.admin.service.mapstruct.RoleConverter;
import com.fanxuankai.boot.admin.vo.RoleVO;
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
 * 角色 服务实现类
 *
 * @author fanxuankai
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleDTO, RoleVO, RoleConverter, RoleDao> implements RoleService {
    @Override
    public void download(List<RoleVO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RoleVO role : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("编码", role.getCode());
            map.put("名称", role.getName());
            map.put("创建人", role.getCreateUserId());
            map.put("修改人", role.getModifiedUserId());
            map.put("创建时间", role.getCreateDate());
            map.put("修改时间", role.getLastModifiedDate());
            list.add(map);
        }
        ExcelDownloadUtils.downloadExcel(list, response);
    }
}