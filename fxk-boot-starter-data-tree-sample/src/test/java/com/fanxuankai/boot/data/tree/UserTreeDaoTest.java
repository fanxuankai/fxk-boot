package com.fanxuankai.boot.data.tree;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.data.tree.dao.UserTreeDao;
import com.fanxuankai.boot.data.tree.domain.UserTree;
import com.fanxuankai.commons.util.IdUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author fanxuankai
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTreeDaoTest {
    private final long rootId = 1;
    @Resource
    private UserTreeDao dao;
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
        UserTree node = new UserTree();
        node.setId(rootId);
        node.setCode(UUID.fastUUID().toString());
        node.setName("root");
        dao.insertNode(node);
        addChildren(node.getId(), 2, 2, 10);
    }

    @Test
    public void query() {
        long id = childrenId - 1;
        dao.ancestor(floorId - 1);
        dao.descendants(rootId);
        dao.parent(id);
        dao.children(rootId);
        dao.sibling(id);
        dao.leaf(rootId);
        dao.nonLeaf(rootId);
        dao.root(rootId);
        dao.degree(rootId);
        dao.level(id);
        dao.height(rootId);
        dao.degree(rootId);
        dao.roots(null);
    }

    @Test
    public void moveNode() {
        dao.moveNode(2L, 3L);
    }

    @Test
    public void removeNode() {
        dao.removeNode(rootId);
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
            UserTree node = new UserTree();
            long _id;
            if (level == 2) {
                _id = childrenId++;
            } else if (level == depth) {
                _id = floorId++;
            } else {
                _id = IdUtils.nextId();
            }
            node.setId(_id);
            node.setCode(UUID.fastUUID().toString());
            node.setName(level + "代" + i);
            node.setPid(id);
            dao.insertNode(node);
            addChildren(node.getId(), c, level + 1, depth);
        }
    }
}
