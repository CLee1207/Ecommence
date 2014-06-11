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
        String sql = "select * from T_ec_dimension_rate";
        logger.info("============================================em="+em);
        logger.info("============================================query="+em.createNativeQuery(sql));
        List result = em.createNativeQuery(sql).getResultList();
        logger.info("============================================result="+result);
        logger.info("=======================================size="+result.size());
        if (result.size() > 0){
            for (Iterator iterator = result.iterator();iterator.hasNext();) {
                Object[] values = (Object[])iterator.next();
                logger.info("================================id=" + values[0]);
                logger.info("================================user_id=" + values[1]);
                logger.info("================================type=" + values[2]);
                logger.info("================================weekCount=" + values[3]);
            }
        }
    }

    /*
    清空T_ec_dimension_rate表中数据
     */
    private void deleteDimensionRates(){
        logger.info("================================删除调度任务启动成功，正在删除T_ec_dimension_rate表中数据...====================================");
        String deleteSql = "delete from T_ec_dimension_rate a where exists (select  id from T_ec_Comment b where a.user_id = b.user_id)";
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
        String sql = "delete from T_ec_sellerCredit a where exists (select  id from T_ec_comment b where a.user_id=b.user_id)";
        Query query = em.createNativeQuery(sql);
        query.executeUpdate();
    }

    //每晚1:50分执行用户综合评分
    @Scheduled(cron = "0 50 1  * * ? ")
    public void addUserCompositeScore(){
        logger.info("===================调度任务启动成功，正在往T_ec_Composite_Score数据表插入数据 ... ===================================");
        String sql = "insert into T_ec_Composite_Score(user_id,score,createdTime) ";
        String s1 = "select user_id,sum(a.attitude+a.deliverySpeed+a.satisfied)/3 as score,current timestamp as createdTime " +
                " from T_ec_dimension_rate a group by a.user_id,createdTime";
        sql = sql + s1;
        //清除数据
        deleteUserCompositeScore();
        Query query = em.createNativeQuery(sql);
        query.executeUpdate();
    }

    /*
    清空后表数据
     */
    public void deleteUserCompositeScore(){
        logger.info("=====================================调度任务启动成功，正在删除数据表T_ec_Composite_Score中的数据 ... =====================================================");
        String sql = "delete from T_ec_Composite_Score a where exists(select id from T_ec_dimension_rate b where a.user_id=b.user_id)";
        Query query = em.createNativeQuery(sql);
        query.executeUpdate();
    }
    /************************************初始化数据************************************/
    //每晚0:00执行
    @Scheduled(cron = "0 0 0  * * ? ")
    public void initDimensions(){
        logger.info("===================调度任务启动成功，正在往T_ec_dimension_rate数据表插入数据 ... ===================================");
        String sql = "insert into T_ec_dimension_rate(user_id,attitude,deliverySpeed,satisfied,createdTime） ";
        String s1 ="select id,0 as attitude,0 as deliverySpeed,0 as satisfied,current timestamp as createdTime " +
                " from T_ec_EcUser a where not exists(select id from T_ec_dimension_rate b where a.id=b.user_id)";
        sql = sql + s1;
        Query query = em.createNativeQuery(sql);
        query.executeUpdate();
    }

    //每晚0:10执行
    @Scheduled(cron = "0 10 0  * * ? ")
    public void initSellerCredits(){
        logger.info("===================调度任务启动成功，正在往T_ec_sellerCredit数据表插入数据 ... ===================================");
        String sql = "insert into T_ec_sellerCredit(createdTime,user_id,type,weekCount,oneMonthCount,sixMonthCount,sixMonthBeforeCount,total)  ";

        String s1 = "select current timestamp as createdTime,id as user_id,'好评' as type,0 as weekCount,0 as oneMonthCount,0 as sixMonthCount,0 as sixMonthBefore,0 as total " +
                " from T_ec_EcUser a where not exists(select id from T_ec_sellerCredit b where a.id = b.user_id)  " +
                "union  " +
                "select current timestamp as createdTime,id as user_id,'中评' as type,0 as weekCount,0 as oneMonthCount,0 as sixMonthCount,0 as sixMonthBefore,0 as total " +
                " from T_ec_EcUser a where not exists(select id from T_ec_sellerCredit b where a.id = b.user_id)  " +
                "union  " +
                "select current timestamp as createdTime,id as user_id,'差评' as type,0 as weekCount,0 as oneMonthCount,0 as sixMonthCount,0 as sixMonthBefore,0 as total " +
                " from T_ec_EcUser a where not exists(select id from T_ec_sellerCredit b where a.id = b.user_id)  ";

        String s2 = "select current timestamp as createTimed, a.createdBy,a.type,sum(a.weekCount) as weekCount,sum(a.oneMonthCount) as oneMonthCount,sum(a.sixMonthCount) as sixMonthCount," +
                "sum(a.sixMonthBeforeCount) as sixMonthBeforeCount,sum(a.total) as total " +
                " from ("+s1+") a group by type,createdBy,createTimed";
        sql = sql + s2 ;
        Query query = em.createNativeQuery(sql);
        query.executeUpdate();
    }


    //每晚0:50分执行用户综合评分初始化
    @Scheduled(cron = "0 50 0  * * ? ")
    public void initUserCompositeScore(){
        logger.info("===================调度任务启动成功，正在往T_ec_Composite_Score数据表插入数据 ... ===================================");
        String sql = "insert into T_ec_Composite_Score(user_id,score,createdTime) ";
        //查询user_id不在T_ec_Composite_Score表中数据，并赋值
        String s1 = "select id,0 as score,current timestamp as createdTime from T_ec_EcUser a where not exists(" +
                "select id from T_ec_Composite_Score b where a.id=b.user_id) ";
        sql = sql + s1;
        Query query = em.createNativeQuery(sql);
        query.executeUpdate();
    }
}
