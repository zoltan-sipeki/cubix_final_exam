package hu.cubix.zoltan_sipeki.logistics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.cubix.zoltan_sipeki.logistics.model.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("select s from Section s where s.transportPlan.id = :transportPlanId and (s.startMilestone.id = :milestoneId or s.endMilestone.id = :milestoneId)")
    public Optional<Section> findByTransportPlanIdAndMilestoneId(long transportPlanId, long milestoneId);
}
