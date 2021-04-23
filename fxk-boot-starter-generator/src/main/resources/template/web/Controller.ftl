package ${packageName}.controller;

import com.fanxuankai.commons.domain.Page;
import com.fanxuankai.commons.domain.PageResult;
import com.fanxuankai.commons.domain.Result;
import ${packageName}.dto.${className}Dto;
import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.service.${className}Service;
import ${packageName}.vo.${className}Vo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ${comment} 管理
 *
 * @author ${author}
 * @date ${date}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${changeClassName}")
public class ${className}Controller {

    private final ${className}Service ${changeClassName}Service;

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
     * @param page    分页参数
     * @return Result
     */
    @GetMapping("page")
    public Result<PageResult<${className}Vo>> page(${className}QueryCriteria criteria, Page page) {
        return Result.newResult(${changeClassName}Service.page(criteria, page));
    }

    /**
     * 查询${comment}所有数据不分页
     *
     * @param criteria 条件参数
     * @return Result
     */
    @GetMapping("list")
    public Result<List<${className}Vo>> list(${className}QueryCriteria criteria) {
        return Result.newResult(${changeClassName}Service.list(criteria));
    }

    /**
     * 根据 ID 查询${comment}
     *
     * @param ${pkFieldName} ID
     * @return Result
     */
    @GetMapping("get/{${pkFieldName}}")
    public Result<${className}Vo> get(@PathVariable ${pkCapitalFieldType} ${pkFieldName}) {
        return Result.newResult(${changeClassName}Service.get(${pkFieldName}));
    }

    /**
     * 创建${comment}
     *
     * @param dto 数据
     * @return Result
     */
    @PostMapping("create")
    public Result<Void> create(@Validated @RequestBody ${className}Dto dto) {
        ${changeClassName}Service.create(dto);
        return Result.newResult();
    }

    /**
     * 修改${comment}
     *
     * @param ${pkFieldName} ID
     * @param dto 数据
     * @return Result
     */
    @PutMapping("update/{${pkFieldName}}")
    public Result<Void> update(@PathVariable ${pkCapitalFieldType} ${pkFieldName}, @Validated @RequestBody ${className}Dto dto) {
        ${changeClassName}Service.update(${pkFieldName}, dto);
        return Result.newResult();
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
        return Result.newResult();
    }
}