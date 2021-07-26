package ${packageName}.controller;

import ${packageName}.dto.${className}DTO;
import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.service.${className}Service;
import ${packageName}.vo.${className}VO;
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
 * ${comment} 管理
 *
 * @author ${author}
 */
@RestController
@RequestMapping("/${changeClassName}")
public class ${className}Controller {
    @Resource
    private ${className}Service ${changeClassName}Service;

    /**
     * 导出数据
     *
     * @param criteria 条件
     * @param response /
     * @throws IOException /
     */
    @GetMapping("download")
    public void download(${className}QueryCriteria criteria, HttpServletResponse response) throws IOException {
        ${changeClassName}Service.download(${changeClassName}Service.list(criteria), response);
    }

    /**
     * 查询${comment}数据分页
     *
     * @param criteria 条件
     * @param pageRequest    分页参数
     * @return Result
     */
    @GetMapping("page")
    public Result<PageResult<${className}VO>> page(${className}QueryCriteria criteria, PageRequest pageRequest) {
        return ResultUtils.ok(${changeClassName}Service.page(criteria, pageRequest));
    }

    /**
     * 查询${comment}所有数据不分页
     *
     * @param criteria 条件参数
     * @return Result
     */
    @GetMapping("list")
    public Result<List<${className}VO>> list(${className}QueryCriteria criteria) {
        return ResultUtils.ok(${changeClassName}Service.list(criteria));
    }

    /**
     * 根据 ID 查询${comment}
     *
     * @param ${pkFieldName} ID
     * @return Result
     */
    @GetMapping("get/{${pkFieldName}}")
    public Result<${className}VO> get(@PathVariable ${pkCapitalFieldType} ${pkFieldName}) {
        return ResultUtils.ok(${changeClassName}Service.get(${pkFieldName}));
    }

    /**
     * 创建${comment}
     *
     * @param dto 数据
     * @return Result
     */
    @PostMapping("create")
    public Result<Void> create(@Validated @RequestBody ${className}DTO dto) {
        ${changeClassName}Service.create(dto);
        return ResultUtils.ok();
    }

    /**
     * 修改${comment}
     *
     * @param ${pkFieldName} ID
     * @param dto 数据
     * @return Result
     */
    @PutMapping("update/{${pkFieldName}}")
    public Result<Void> update(@PathVariable ${pkCapitalFieldType} ${pkFieldName}, @Validated @RequestBody ${className}DTO dto) {
        ${changeClassName}Service.update(${pkFieldName}, dto);
        return ResultUtils.ok();
    }

    /**
     * 删除${comment}
     *
     * @param ids ID 列表
     * @return Result
     */
    @DeleteMapping("delete")
    public Result<Void> delete(@RequestBody ${pkCapitalFieldType}[] ids) {
        ${changeClassName}Service.deleteAll(ids);
        return ResultUtils.ok();
    }
}