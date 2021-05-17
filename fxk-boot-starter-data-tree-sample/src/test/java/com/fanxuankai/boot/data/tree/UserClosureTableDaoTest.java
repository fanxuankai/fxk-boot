//package com.fanxuankai.boot.data.tree;
//
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.fanxuankai.boot.data.tree.dao.UserClosureTableDao;
//import com.fanxuankai.boot.data.tree.domain.UserClosureTable;
//import com.fanxuankai.commons.util.IdUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.concurrent.atomic.LongAdder;
//
///**
// * @author fanxuankai
// */
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class UserClosureTableDaoTest {
//    private final long rootId = 1;
//    @Resource
//    private UserClosureTableDao dao;
//    private long childrenId = 2;
//    private long floorId = 1000;
//    private final LongAdder longAdder = new LongAdder();
//
//    @Test
//    public void test() {
//        dao.remove(Wrappers.emptyWrapper());
//        buildTree();
//        query();
////        moveNode();
////        removeNode();
//    }
//
//    @Test
//    public void buildTree() {
//        UserClosureTable node = new UserClosureTable();
//        node.setId(rootId);
//        longAdder.increment();
//        node.setAncestor(1L);
//        node.setDescendant(2L);
//        node.setDistance(1);
//        dao.insertNode(node);
////        addChildren(node.getId(), 2, 2, 10);
//    }
//
//    @Test
//    public void query() {
//        long id = childrenId - 1;
//        dao.ancestor(floorId - 1);
//        dao.descendants(rootId);
//        dao.parent(id);
//        dao.children(rootId);
//        dao.sibling(id);
//        dao.leaf(rootId);
//        dao.nonLeaf(rootId);
//        dao.root(rootId);
//        dao.degree(rootId);
//        dao.level(id);
//        dao.height(rootId);
//        dao.degree(rootId);
//        dao.roots(null);
//    }
//
//    @Test
//    public void moveNode() {
//        dao.moveNode(2L, 3L);
//    }
//
//    @Test
//    public void removeNode() {
//        dao.removeNode(rootId);
//    }
//
//    /**
//     * 添加子节点
//     *
//     * @param id    节点 id
//     * @param c     子节点数量
//     * @param level 子节点当前阶度
//     * @param depth 深度
//     */
//    private void addChildren(Long id, int c, int level, int depth) {
//        if (level > depth) {
//            return;
//        }
//        for (int i = 0; i < c; i++) {
//            UserClosureTable node = new UserClosureTable();
//            long _id;
//            if (level == 2) {
//                _id = childrenId++;
//            } else if (level == depth) {
//                _id = floorId++;
//            } else {
//                _id = IdUtils.nextId();
//            }
//            node.setId(_id);
//            longAdder.increment();
//            node.setAncestor(id);
//            dao.insertNode(node);
//            addChildren(node.getId(), c, level + 1, depth);
//        }
//    }
//}
