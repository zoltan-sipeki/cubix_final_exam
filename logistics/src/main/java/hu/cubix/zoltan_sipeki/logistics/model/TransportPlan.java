package hu.cubix.zoltan_sipeki.logistics.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class TransportPlan {

    @GeneratedValue
    @Id
    private long id;

    private int income;

    @OneToMany(mappedBy = Section_.TRANSPORT_PLAN, fetch = FetchType.LAZY)
    @OrderBy("sequence_number ASC")
    private List<Section> sections;

    public void cutIncomeByPercent(int percent) {
        income = (int) ((1 - percent / 100.0) * income);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
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
        TransportPlan other = (TransportPlan) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
