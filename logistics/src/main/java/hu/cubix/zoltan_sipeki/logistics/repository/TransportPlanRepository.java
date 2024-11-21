package hu.cubix.zoltan_sipeki.logistics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.cubix.zoltan_sipeki.logistics.model.TransportPlan;

@Repository
public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {
    
    @EntityGraph(attributePaths = {"sections", "sections.startMilestone", "sections.endMilestone", "sections.startMilestone.address", "sections.endMilestone.address"})
    Optional<TransportPlan> findById(long id);
}
