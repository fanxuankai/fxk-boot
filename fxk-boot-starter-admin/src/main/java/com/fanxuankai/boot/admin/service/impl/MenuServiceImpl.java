package com.fanxuankai.boot.admin.service.impl;

import com.fanxuankai.boot.admin.dao.MenuDao;
import com.fanxuankai.boot.admin.dto.MenuDTO;
import com.fanxuankai.boot.admin.model.Menu;
import com.fanxuankai.boot.admin.service.MenuService;
import com.fanxuankai.boot.admin.service.mapstruct.MenuConverter;
import com.fanxuankai.boot.admin.vo.MenuVO;
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
 * 菜单 服务实现类
 *
 * @author fanxuankai
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, MenuDTO, MenuVO, MenuConverter, MenuDao> implements MenuService {
    @Override
    public void download(List<MenuVO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MenuVO menu : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("类型(目录:0 菜单:1 按钮:2)", menu.getType());
            map.put("标题", menu.getTitle());
            map.put("排序", menu.getMenuSort());
            map.put("权限标识", menu.getPermission());
            map.put("上级菜单", menu.getPid());
            map.put("组件名称", menu.getComponentName());
            map.put("组件路径", menu.getComponent());
            map.put("路由地址", menu.getPath());
            map.put("图标", menu.getIcon());
            map.put("是否缓存", menu.getCache());
            map.put("是否隐藏", menu.getHidden());
            map.put("创建人", menu.getCreateUserId());
            map.put("修改人", menu.getModifiedUserId());
            map.put("创建时间", menu.getCreateDate());
            map.put("修改时间", menu.getLastModifiedDate());
            list.add(map);
        }
        ExcelDownloadUtils.downloadExcel(list, response);
    }
}