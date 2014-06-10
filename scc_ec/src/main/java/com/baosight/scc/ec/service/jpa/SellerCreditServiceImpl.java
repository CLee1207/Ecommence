package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.SellerCredit;
import com.baosight.scc.ec.repository.SellerCreditRepository;
import com.baosight.scc.ec.service.SellerCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sam on 2014/6/6.
 */
@Service
@Transactional
public class SellerCreditServiceImpl implements SellerCreditService {

    @Autowired
    private SellerCreditRepository sellerCreditRepository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<SellerCredit> findByUser(EcUser user) {
        return this.sellerCreditRepository.findByUser(user);
    }

    @Override
    public SellerCredit findTypeTotal(EcUser user) {
        String sql = "select sum(s.weekCount) as weekCount,sum(s.oneMonthCount) as oneMonthCount,sum(s.sixMonthCount) as sixMonthCount," +
                "sum(s.sixMonthBeforeCount) as sixMonthBeforeCount,sum(s.total) as total from SellerCredit s " +
                "where s.user=:user";
        Query query = em.createQuery(sql).setParameter("user",user);
        List result = query.getResultList();
        SellerCredit sellerCredit = new SellerCredit();
        for (Iterator iterator=result.iterator();iterator.hasNext();){
            Object[] values = (Object[])iterator.next();
            sellerCredit.setWeekCount(Integer.parseInt(values[0].toString()));
            sellerCredit.setOneMonthCount(Integer.parseInt(values[1].toString()));
            sellerCredit.setSixMonthCount(Integer.parseInt(values[2].toString()));
            sellerCredit.setSixMonthBeforeCount(Integer.parseInt(values[3].toString()));
            sellerCredit.setTotal(Integer.parseInt(values[4].toString()));
        }
        return sellerCredit;
    }
}
