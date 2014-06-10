package com.baosight.scc.ec.task;

import com.baosight.scc.ec.service.DimensionRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sam on 2014/6/5.
 */
@Component
@Transactional
public class DimensionRateOnJob {

    @Autowired
    private DimensionRateService dimensionRateService;
    @PersistenceContext
    private EntityManager em;

    final Logger logger = LoggerFactory.getLogger(DimensionRateOnJob.class);

    //每晚00:30分执行
    @Scheduled(cron = "0 30 0  * * ? ")
    public void addDimensions(){
        logger.info("=====================调度任务启动成功,数据来源表T_ec_Comment，正在往T_ec_dimension_rate表插入数据...=======================");
        //将查询出来的数据插入到目标表T_ec_dimension_rate中
        StringBuffer sql = new StringBuffer("insert into T_ec_dimension_rate(user_id,attitude,deliverySpeed,satisfied,createdTime)  " +
                "select createdBy as user_id, avg(attitude) as attitude,avg(deliverySpeed) as deliverySpeed,avg(satisfied) as satisfied,current timestamp as createTimed from T_ec_Comment c inner join T_ec_Item i on c.item_id = i.id  " +
                " where c.createdTime between current timestamp - 180 and current timestamp  " +
                " group by i.createdBy ");

        //先清除T_ec_dimension_rate表中数据
        this.deleteDimensionRates();
        Query query = em.createNativeQuery(sql.toString());
        query.executeUpdate();
    }

    /*
    测试
     */
 //   @Scheduled(cron = "0/30 * *  * * ? ")
    private void test(){
        logger.info("=====================调度任务启动成功...正在查询...=======================");
    //    String sql = "select * from T_ec_dimension_rate";
        String sql = "select * from T_ec_sellerCredit";
        List result = em.createNativeQuery(sql).getResultList();
        for (Iterator iterator = result.iterator();iterator.hasNext();) {
            Object[] values = (Object[])iterator.next();
            logger.info("================================id=" + values[0]);
            logger.info("================================user_id=" + values[1]);
            logger.info("================================type=" + values[2]);
            logger.info("================================weekCount=" + values[3]);
            logger.info("================================oneMonthCount=" + values[4]);
            logger.info("================================sixMonthCount=" + values[5]);
            logger.info("================================sixMonthBeforeCount=" + values[6]);
            logger.info("================================total=" + values[7]);
            logger.info("================================createTime=" + values[8]);
        }
    }

    /*
    清空T_ec_dimension_rate表中数据
     */
    private void deleteDimensionRates(){
        logger.info("================================删除调度任务启动成功，正在删除T_ec_dimension_rate表中数据...====================================");
        String deleteSql = "delete from T_ec_dimension_rate";
        Query query = em.createNativeQuery(deleteSql);
        query.executeUpdate();
    }

    //每晚0:40执行
    @Scheduled(cron = "0 40 0  * * ? ")
    public void addSellerCredit(){
        logger.info("============================调度任务启动成功，数据来源T_ec_comment,准备向数据表T_ec_sellerCredit中插入数据 .. ====================================");

        //统计评价类型最近一周、一个月、六个月、六个月前、一年内，好、中、差评次数的sql
        String sql = "select i.createdBy,c.type as type,count(c.type) as weekCount,0 as oneMonthCount,0 as sixMonthCount,0 as sixMonthBeforeCount,0 as total  " +
                "from T_ec_comment c inner join T_ec_Item i on c.item_id=i.id  " +
                "where 1=1 and c.createdTime between current timestamp - 7 and current timestamp " +
                "group by c.type,i.createdBy " +
                "union   " +
                "select i.createdBy,c.type as type,0 as weekCount,count(c.type) as oneMonthCount,0 as sixMonthCount,0 as sixMonthBeforeCount,0 as total  " +
                "from T_ec_comment c inner join T_ec_Item i on c.item_id=i.id  " +
                "where 1=1 and c.createdTime between current timestamp - 30 and current timestamp " +
                "group by c.type,i.createdBy " +
                "union   " +
                "select i.createdBy,c.type as type,0 as weekCount,0 as oneMonthCount,count(c.type) as sixMonthCount,0 as sixMonthBeforeCount,0 as total  " +
                "from T_ec_comment c inner join T_ec_Item i on c.item_id=i.id  " +
                "where 1=1 and c.createdTime between current timestamp - 180 and current timestamp " +
                "group by c.type,i.createdBy " +
                "union   " +
                "select i.createdBy,c.type as type,0 as weekCount,0 as oneMonthCount,0 as sixMonthCount,count(c.type) as sixMonthBeforeCount,0 as total  " +
                "from T_ec_comment c inner join T_ec_Item i on c.item_id=i.id  " +
                "where 1=1 and c.createdTime between current timestamp - 360 and current timestamp-180 " +
                "group by c.type,i.createdBy " +
                "union   " +
                "select i.createdBy,c.type as type,0 as weekCount,0 as oneMonthCount,0 as sixMonthCount,0 as sixMonthBeforeCount,count(c.type) as total  " +
                "from T_ec_comment c inner join T_ec_Item i on c.item_id=i.id  " +
                "where 1=1 and c.createdTime between current timestamp - 360 and current timestamp " +
                "group by c.type,i.createdBy";

        //将上面的sql查询出来的结果放在一张临时表a中
        String s = "select current timestamp as createTimed, a.createdBy,a.type,sum(a.weekCount) as weekCount,sum(a.oneMonthCount) as oneMonthCount,sum(a.sixMonthCount) as sixMonthCount," +
                "sum(a.sixMonthBeforeCount) as sixMonthBeforeCount,sum(a.total) as total " +
                " from ("+sql+") a group by type,createdBy,createTimed";

        //将临时表a中的数据插入到目标表T_ec_sellerCredit中
        String s1 = "insert into T_ec_sellerCredit(createdTime,user_id,type,weekCount,oneMonthCount,sixMonthCount,sixMonthBeforeCount,total)  " + s;

        //数据插入之前，将表中数据清除
        this.deleteSellerCredits();

        //插入数据到目标表T_ec_sellerCredit中
        Query query = em.createNativeQuery(s1);
        query.executeUpdate();
    }

    /*
    清空T_ec_sellerCredit数据表
     */
    private void deleteSellerCredits(){
        logger.info("=====================================调度任务启动成功，正在删除数据表T_ec_sellerCredit中的数据 ... =====================================================");
        String sql = "delete from T_ec_sellerCredit";
        Query query = em.createNativeQuery(sql);
        query.executeUpdate();
    }

}
