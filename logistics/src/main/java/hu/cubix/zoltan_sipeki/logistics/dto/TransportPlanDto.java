package hu.cubix.zoltan_sipeki.logistics.dto;

import java.util.List;

public record TransportPlanDto(
        long id,
        int income,
        List<SectionDto> sections) {
}
