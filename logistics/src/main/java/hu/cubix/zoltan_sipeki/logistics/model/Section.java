package hu.cubix.zoltan_sipeki.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Section {

    @GeneratedValue
    @Id
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Milestone startMilestone;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Milestone endMilestone;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private TransportPlan transportPlan;

    private int sequenceNumber;
    
    public TransportPlan getTransportPlan() {
        return transportPlan;
    }

    public void setTransportPlan(TransportPlan transportPlan) {
        this.transportPlan = transportPlan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Milestone getStartMilestone() {
        return startMilestone;
    }

    public void setStartMilestone(Milestone start) {
        this.startMilestone = start;
    }

    public Milestone getEndMilestone() {
        return endMilestone;
    }

    public void setEndMilestone(Milestone end) {
        this.endMilestone = end;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int order) {
        this.sequenceNumber = order;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Section other = (Section) obj;
        if (id != other.id)
            return false;
        return true;
    }

    
}
