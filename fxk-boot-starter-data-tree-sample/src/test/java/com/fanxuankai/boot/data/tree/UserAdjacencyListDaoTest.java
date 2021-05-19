package com.fanxuankai.boot.data.tree;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.data.tree.dao.UserAdjacencyListDao;
import com.fanxuankai.boot.data.tree.domain.UserAdjacencyList;
import com.fanxuankai.commons.extra.mybatis.tree.TreeUtils;
import com.fanxuankai.commons.util.IdUtils;
import com.fanxuankai.commons.util.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fanxuankai
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserAdjacencyListDaoTest {
    @Resource
    private UserAdjacencyListDao dao;
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
        UserAdjacencyList node = new UserAdjacencyList();
        node.setId(rootId);
        node.setCode(UUID.fastUUID().toString());
        node.setName("root");
        dao.insertNode(node, null);
        addChildren(node.getId(), 2, 2, 10);
    }

    @Test
    public void query() {
        long id = childrenId - 1;
        dao.ancestors(floorId - 1);
        List<Node<UserAdjacencyList>> descendants = dao.descendants(rootId);
        TreeUtils.flat(descendants);
        dao.tree(rootId);
        dao.parent(id);
        dao.children(rootId);
        dao.sibling(id);
        dao.leaf(Wrappers.lambdaQuery(UserAdjacencyList.class));
        dao.nonLeaf(Wrappers.lambdaQuery(UserAdjacencyList.class));
        dao.roots(Wrappers.lambdaQuery(UserAdjacencyList.class));
        dao.degree(rootId);
        dao.level(id);
        dao.height(rootId);
        dao.depth(id);
        dao.roots(Wrappers.lambdaQuery(UserAdjacencyList.class));
    }

    @Test
    public void moveNode() {
        UserAdjacencyList entity = new UserAdjacencyList();
        entity.setPid(3L);
        dao.update(entity, Wrappers.lambdaUpdate(UserAdjacencyList.class).eq(UserAdjacencyList::getId, 2L));
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
            UserAdjacencyList node = new UserAdjacencyList();
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
            dao.insertNode(node, id);
            addChildren(node.getId(), c, level + 1, depth);
        }
    }
}
