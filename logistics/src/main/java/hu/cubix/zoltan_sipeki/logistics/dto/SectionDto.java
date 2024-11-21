package hu.cubix.zoltan_sipeki.logistics.dto;

public record SectionDto(
        long id,
        MilestoneDto startMilestone,
        MilestoneDto endMilestone,
        TransportPlanDto transportPlan,
        int sequenceNumber) {

}
