package hu.cubix.zoltan_sipeki.logistics.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.cubix.zoltan_sipeki.logistics.dto.AddressDto;
import hu.cubix.zoltan_sipeki.logistics.dto.MilestoneDto;
import hu.cubix.zoltan_sipeki.logistics.dto.SectionDto;
import hu.cubix.zoltan_sipeki.logistics.dto.TransportPlanDto;
import hu.cubix.zoltan_sipeki.logistics.model.Address;
import hu.cubix.zoltan_sipeki.logistics.model.Milestone;
import hu.cubix.zoltan_sipeki.logistics.model.Section;
import hu.cubix.zoltan_sipeki.logistics.model.TransportPlan;

@Mapper(componentModel = "spring")
public interface TransportPlanMapper {

    public TransportPlanDto mapTransportPlanToDto(TransportPlan plan);

    public TransportPlan mapDtoToTransportPlan(TransportPlanDto plan);

    @Mapping(target = "transportPlan", ignore = true)
    public SectionDto mapSectionToDto(Section section);

    public MilestoneDto mapMilestoneToDto(Milestone section);

    public AddressDto mapAddressToDto(Address address);

}
