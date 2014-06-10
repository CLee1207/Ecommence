package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.repository.EcUserRepository;
import com.baosight.scc.ec.service.EcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class EcUserServiceImpl implements EcUserService {
    @Autowired
    private EcUserRepository er;
    @PersistenceContext
    private EntityManager em;

    /**
     * 根据id，查询服务商信息
     *
     * @param id 服务商id
     * @return
     * @author sam
     */
    @Transactional(readOnly = true)
    public EcUser findById(String id) {
        EcUser user = er.findOne(id);
        return user;
    }

    @Override
    public EcUser save(EcUser user) {
        EcUser source=em.find(EcUser.class,user.getId());
        source.setDefaultAddress(user.getDefaultAddress());
        return source;
    }
}
