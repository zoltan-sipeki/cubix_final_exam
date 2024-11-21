package hu.cubix.zoltan_sipeki.logistics.dto;

import java.time.LocalDateTime;

public record MilestoneDto(
        long id,
        AddressDto address,
        LocalDateTime plannedTime) {
}
