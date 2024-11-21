package hu.cubix.zoltan_sipeki.logistics.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.cubix.zoltan_sipeki.logistics.dto.AddressDto;
import hu.cubix.zoltan_sipeki.logistics.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    public AddressDto mapAddressToDto(Address address);

    public Address mapDtoToAddress(AddressDto address);

    public List<AddressDto> mapAddressListToDtoList(List<Address> list);
}
