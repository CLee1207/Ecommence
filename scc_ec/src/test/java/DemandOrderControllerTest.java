import com.baosight.scc.ec.controller.DemandOrderController;
import com.baosight.scc.ec.model.DemandOrder;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.DemandOrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import webController.AbstractServiceImplTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by zodiake on 2014/5/23.
 */
public class DemandOrderControllerTest extends AbstractServiceImplTest {
    @Autowired
    private DemandOrderService demandOrderService;
    private List<DemandOrder> list = new ArrayList<DemandOrder>();
    private MessageSource messageSource;
    private UserContext userContext;

    @Before
    public void init() {
        EcUser u = new EcUser();
        u.setId("1");
        u.setName("tom");
        DemandOrder demandOrder = new DemandOrder();
        demandOrder.setId("1");
        demandOrder.setCreatedBy(u);
        list.add(demandOrder);

        userContext = mock(UserContext.class);
        demandOrderService = mock(DemandOrderService.class);
        messageSource = mock(MessageSource.class);

        when(userContext.getCurrentUser()).thenReturn(u);
        when(messageSource.getMessage("contact_save_success", new Object[]{}, Locale.ENGLISH)).thenReturn("success");
    }

    @Test
    public void testSave() {
        final DemandOrder demandOrder = new DemandOrder();
        demandOrder.setId("1");

        userContext = mock(UserContext.class);
        demandOrderService = mock(DemandOrderService.class);
        messageSource = mock(MessageSource.class);

        EcUser u = new EcUser();
        u.setId("1");
        u.setName("tom");

        when(userContext.getCurrentUser()).thenReturn(u);
        when(messageSource.getMessage("contact_save_success", new Object[]{}, Locale.ENGLISH)).thenReturn("success");

        DemandOrderController demandOrderController = new DemandOrderController();

        ReflectionTestUtils.setField(demandOrderController, "messageSource", messageSource);
        ReflectionTestUtils.setField(demandOrderController, "service", demandOrderService);
        ReflectionTestUtils.setField(demandOrderController, "userContext", userContext);

        when(userContext.getCurrentUser()).thenReturn(u);
        when(messageSource.getMessage("contact_save_success", new Object[]{}, Locale.ENGLISH)).thenReturn("success");
        when(demandOrderService.save(demandOrder)).thenAnswer(new Answer<DemandOrder>() {
            public DemandOrder answer(InvocationOnMock invocation) throws Throwable {
                list.add(demandOrder);
                return demandOrder;
            }
        });

        assertEquals(list.size(), 1);
    }

    @Test
    public void testSqlSave() {
        final DemandOrder demandOrder = new DemandOrder();
        demandOrder.setId("1");
        DemandOrder order = demandOrderService.findOne("1");
        assertNull(order);
    }

}
