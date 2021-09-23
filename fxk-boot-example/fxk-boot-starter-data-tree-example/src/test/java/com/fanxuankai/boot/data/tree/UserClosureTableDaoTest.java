package com.fanxuankai.boot.data.tree;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.data.tree.dao.UserClosureTableDao;
import com.fanxuankai.boot.data.tree.domain.UserClosureTable;
import com.fanxuankai.commons.util.IdUtils;
import com.fanxuankai.commons.util.Node;
import com.fanxuankai.commons.util.TreeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fanxuankai
 */
@SpringBootTest
public class UserClosureTableDaoTest {
    @Resource
    private UserClosureTableDao dao;
    private final long rootId = 1;
    private long childrenId = 2;
    private long floorId = 1000;

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
        UserClosureTable node = new UserClosureTable();
        node.setAncestor(rootId);
        node.setDescendant(rootId);
        node.setDistance(0);
        dao.saveNode(node);
        addChildren(rootId, 2, 2, 10);
    }

    @Test
    public void query() {
        long id = childrenId - 1;
        dao.ancestors(floorId - 1);
        List<Node<UserClosureTable>> descendants = dao.descendants(rootId);
        TreeUtils.flat(descendants);
        dao.tree(rootId);
        dao.parent(id);
        dao.children(rootId);
        dao.sibling(id);
        dao.leaf(Wrappers.lambdaQuery(UserClosureTable.class));
        dao.nonLeaf(Wrappers.lambdaQuery(UserClosureTable.class));
        dao.roots(Wrappers.lambdaQuery(UserClosureTable.class));
        dao.degree(rootId);
        dao.level(id);
        dao.height(rootId);
        dao.depth(id);
        dao.roots(Wrappers.lambdaQuery(UserClosureTable.class));
    }

    @Test
    public void moveNode() {
        dao.moveNode(2L, 3L);
    }

    @Test
    public void removeNode() {
        dao.removeNode(rootId, true);
    }

    /**
     * 添加子节点
     *
     * @param id    节点 id
     * @param c     子节点数量
     * @param level 子节点当前阶度
     * @param depth 深度
     */
    private void addChildren(Long id, int c, int level, int depth) {
        if (level > depth) {
            return;
        }
        for (int i = 0; i < c; i++) {
            UserClosureTable node = new UserClosureTable();
            long _id;
            if (level == 2) {
                _id = childrenId++;
            } else if (level == depth) {
                _id = floorId++;
            } else {
                _id = IdUtils.nextId();
            }
            node.setAncestor(id);
            node.setDescendant(_id);
            node.setDistance(level);
            dao.saveNode(node);
            addChildren(_id, c, level + 1, depth);
        }
    }
}
