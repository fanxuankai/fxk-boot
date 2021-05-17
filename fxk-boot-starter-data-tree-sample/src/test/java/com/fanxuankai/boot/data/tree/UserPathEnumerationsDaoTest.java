package com.fanxuankai.boot.data.tree;

import cn.hutool.core.text.StrPool;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.data.tree.dao.UserPathEnumerationsDao;
import com.fanxuankai.boot.data.tree.domain.UserPathEnumerations;
import com.fanxuankai.commons.util.IdUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author fanxuankai
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserPathEnumerationsDaoTest {
    private final long rootId = 1;
    @Resource
    private UserPathEnumerationsDao dao;
    private long childrenId = 2;
    private long floorId = 1000;
    private final LongAdder longAdder = new LongAdder();

    @Test
    public void test() {
        dao.remove(Wrappers.emptyWrapper());
        buildTree();
        query();
        moveNode();
        removeNode();
    }

    @Test
    public void buildTree() {
        UserPathEnumerations node = new UserPathEnumerations();
        node.setId(rootId);
        node.setCode("" + longAdder.longValue());
        node.setPath(StrPool.SLASH + node.getCode());
        longAdder.increment();
        node.setName("root");
        dao.insertNode(node);
        addChildren(node.getPath(), 2, 2, 10);
    }

    @Test
    public void query() {
        long id = childrenId - 1;
        dao.ancestors(floorId - 1);
        dao.descendants(rootId);
        dao.parent(id);
        dao.children(rootId);
        dao.sibling(id);
        dao.leaf(Wrappers.lambdaQuery(UserPathEnumerations.class).eq(UserPathEnumerations::getId, rootId));
        dao.nonLeaf(Wrappers.lambdaQuery(UserPathEnumerations.class).eq(UserPathEnumerations::getId, rootId));
        dao.roots(Wrappers.lambdaQuery(UserPathEnumerations.class).eq(UserPathEnumerations::getId, rootId));
        dao.degree(rootId);
        dao.level(id);
        dao.height(rootId);
        dao.degree(rootId);
        dao.roots(Wrappers.lambdaQuery(UserPathEnumerations.class));
    }

    @Test
    public void moveNode() {

    }

    @Test
    public void removeNode() {
        dao.removeNode(rootId, true);
    }

    /**
     * 添加子节点
     *
     * @param parentPath 父节点路径
     * @param c          子节点数量
     * @param level      子节点当前阶度
     * @param depth      深度
     */
    private void addChildren(String parentPath, int c, int level, int depth) {
        if (level > depth) {
            return;
        }
        for (int i = 0; i < c; i++) {
            UserPathEnumerations node = new UserPathEnumerations();
            long _id;
            if (level == 2) {
                _id = childrenId++;
            } else if (level == depth) {
                _id = floorId++;
            } else {
                _id = IdUtils.nextId();
            }
            node.setId(_id);
            node.setCode("" + longAdder.longValue());
            longAdder.increment();
            node.setName(level + "代" + i);
            node.setPath(parentPath + StrPool.SLASH + node.getCode());
            dao.insertNode(node);
            addChildren(node.getPath(), c, level + 1, depth);
        }
    }
}
