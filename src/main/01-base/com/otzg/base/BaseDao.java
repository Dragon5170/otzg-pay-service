package com.otzg.base;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class BaseDao<T> extends BaseBean{

    @PersistenceContext
    EntityManager em;

    /**
     * hql分页功能，支持hibernate limit 分页功能
     * 方法适合不带复杂关联的查询及分页
     * 不适合一对多关联时加载并统计多方，一方的结果会出现重复，计算总数时候会给出多方的合计数量。
     * (G20141015)select count(*) 不支持 fetch抓取，需要去掉fetch语句
     * (G20160114)支持 group by 语句 count(*) 统计
     *
     * @param hql
     * @param pageSize
     * @param startIndex
     * @return
     * @author G/2016-4-28 下午4:12:35
     */
    public Page<T> findPageByHql(String hql, final int pageSize, final int startIndex) {
        try {

            List<T> results = em.createQuery(hql).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();

            //select count(*) 时候不能fetch抓取,去掉fetch
            hql = "select COUNT(*) " + hql.substring(hql.indexOf("from")).replace("fetch", "");

            long totalCount = ((List<Long>) em.createQuery(hql).getResultList()).stream().reduce(0l,Long::sum);

            return new Page(results, totalCount, pageSize, startIndex);
        } catch (IllegalArgumentException e) {
            log("findPageByHql is wrong:" + e);
            log("hql sentence:" + hql);
        }
        return null;
    }

}
