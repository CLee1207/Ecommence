package com.baosight.scc.ec.model;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

/**
 * Created by zodiake on 2014/5/27.
 */
@Embeddable
public class Auditable {
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedTime;

    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar deletedTime;
}
