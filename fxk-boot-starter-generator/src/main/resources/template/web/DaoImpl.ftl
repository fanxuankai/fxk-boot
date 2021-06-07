package ${packageName}.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${packageName}.dao.${className}Dao;
import ${packageName}.mapper.${className}Mapper;
import ${packageName}.model.${className};
import org.springframework.stereotype.Service;

/**
 * ${comment} 数据访问实现类
 *
 * @author ${author}
 */
@Service
public class ${className}DaoImpl extends ServiceImpl<${className}Mapper, ${className}> implements ${className}Dao {
}