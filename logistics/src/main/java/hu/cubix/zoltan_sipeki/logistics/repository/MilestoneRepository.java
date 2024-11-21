package hu.cubix.zoltan_sipeki.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.cubix.zoltan_sipeki.logistics.model.Milestone;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

}
