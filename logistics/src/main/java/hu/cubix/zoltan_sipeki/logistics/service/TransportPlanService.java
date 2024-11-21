package hu.cubix.zoltan_sipeki.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.cubix.zoltan_sipeki.logistics.configuration.DelayConfig;
import hu.cubix.zoltan_sipeki.logistics.dto.DelayDto;
import hu.cubix.zoltan_sipeki.logistics.exception.EntityNotFoundException;
import hu.cubix.zoltan_sipeki.logistics.exception.InvalidOperationException;
import hu.cubix.zoltan_sipeki.logistics.model.TransportPlan;
import hu.cubix.zoltan_sipeki.logistics.repository.MilestoneRepository;
import hu.cubix.zoltan_sipeki.logistics.repository.SectionRepository;
import hu.cubix.zoltan_sipeki.logistics.repository.TransportPlanRepository;

@Service
public class TransportPlanService {

    @Autowired
    private TransportPlanRepository planRepo;

    @Autowired
    private MilestoneRepository milestoneRepo;

    @Autowired
    private SectionRepository sectionRepo;

    @Autowired
    private DelayConfig delayConfig;

    @Transactional
    public TransportPlan registerDelay(long id, DelayDto delay) throws EntityNotFoundException, InvalidOperationException {
        var plan = planRepo.findById(id).orElse(null);
        if (plan == null) {
            throw new EntityNotFoundException("transport_plan", id);
        }
        
        var milestone = milestoneRepo.findById(delay.milestoneId()).orElse(null);
        if (milestone == null) {
            throw new EntityNotFoundException("milestone", id);
        }

        var section = sectionRepo.findByTransportPlanIdAndMilestoneId(id, delay.milestoneId()).orElse(null);
        if (section == null) {
            throw new InvalidOperationException(String.format("transport_plan with '%d' does not have a milestone with id '%d'", id, delay.milestoneId()));
        }

        var sections = plan.getSections();
        section = sections.get(section.getSequenceNumber());
        var startMS = section.getStartMilestone();
        var endMS = section.getEndMilestone();
        int order = section.getSequenceNumber();

        if (milestone.equals(startMS)) {
            startMS.addDelay(delay.minutes());
            endMS.addDelay(delay.minutes());
        }
        else if (milestone.equals(endMS)) {
            endMS.addDelay(delay.minutes());
            if (order + 1 < sections.size()) {
                startMS = sections.get(order + 1).getStartMilestone();
                startMS.addDelay(delay.minutes());
            }
        }

        for (var entry : delayConfig.getIncomeCutPercents().entrySet()) {
            if (delay.minutes() >= entry.getKey()) {
                plan.cutIncomeByPercent(entry.getValue());
                break;
            }
        }

        milestoneRepo.saveAll(List.of(startMS, endMS));
        planRepo.save(plan);

        return plan;
    }
}
