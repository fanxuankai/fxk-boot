package com.fanxuankai.boot.generator.service.impl;

import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.EasyExcel;
import com.fanxuankai.boot.generator.dao.UserDao;
import com.fanxuankai.boot.generator.dto.UserDTO;
import com.fanxuankai.boot.generator.dto.UserQueryCriteria;
import com.fanxuankai.boot.generator.model.User;
import com.fanxuankai.boot.generator.service.UserService;
import com.fanxuankai.boot.generator.service.mapstruct.UserConverter;
import com.fanxuankai.boot.generator.vo.UserVO;
import com.fanxuankai.commons.extra.mybatis.base.BaseServiceImpl;
import com.fanxuankai.commons.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * 用户 服务实现类
 *
 * @author admin
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserDTO, UserVO, UserQueryCriteria, UserConverter, UserDao>
        implements UserService {
    @Override
    public void download(List<UserVO> all, HttpServletResponse response) throws IOException {
        String filename = URLEncoder.encode("用户", "UTF-8")
                + "-" + DateUtils.toText(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");
        EasyExcel.write(response.getOutputStream(), UserVO.class)
                .sheet("导出数据")
                .doWrite(all);
    }

    @Override
    public void upload(MultipartFile request) throws IOException {
        List<UserDTO> dtoList = EasyExcel.read(request.getInputStream(), UserDTO.class, null)
                .sheet()
                .doReadSync();
        if (dtoList.isEmpty()) {
            return;
        }
        baseDao.saveBatch(converter.toEntity(dtoList));
    }
}