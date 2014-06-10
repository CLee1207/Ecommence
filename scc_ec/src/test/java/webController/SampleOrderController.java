package webController;

import annotation.DataSets;
import com.baosight.scc.ec.model.Address;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.SampleOrder;
import com.baosight.scc.ec.service.AddressService;
import com.baosight.scc.ec.service.SampleOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by zodiake on 2014/6/5.
 */
public class SampleOrderController extends AbstractServiceImplTest {
    @Autowired
    SampleOrderService sampleOrderService;
    @Autowired
    AddressService addressService;

    @Test
    @DataSets(setUpDataSet = "/sampleOrder.xml")
    public void testFindByIdAndCreator()throws Exception{
        EcUser u=new EcUser();
        u.setId("1");
        SampleOrder order=sampleOrderService.findByIdAndCreator("1", u);
        assertNotNull(order);
    }

    @Test
    public void testCreate() throws Exception{
        Address address=new Address();
        address.setId("1");
        address.setCity("shanghai");
        address.setState("23");
        address.setStreet("aaaaa");
        address.setReceiverName("tom");
        addressService.save(address);
        deleteFromTables("t_ec_sampleorder");
        SampleOrder order =new SampleOrder();
        order.setSampleCardNumber(1);
        order.setSampleItemMile(2);
        order.setAddress(address);
        sampleOrderService.save(order);
        assertEquals(1,sampleOrderService.findAll().size());
    }
}
