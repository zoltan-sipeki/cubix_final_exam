package hu.cubix.zoltan_sipeki.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.cubix.zoltan_sipeki.logistics.dto.specification.AddressSpecificationDto;
import hu.cubix.zoltan_sipeki.logistics.exception.EntityNotFoundException;
import hu.cubix.zoltan_sipeki.logistics.model.Address;
import hu.cubix.zoltan_sipeki.logistics.model.specification.AddressSpecification;
import hu.cubix.zoltan_sipeki.logistics.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repo;

    @Transactional
    public Address creatAddress(Address address) {
        return repo.save(address);
    }

    public List<Address> getAllAddresses() {
        return repo.findAll();
    }

    public Address getAddressById(long id) throws EntityNotFoundException {
        var address = repo.findById(id).orElse(null);
        if (address == null) {
            throw new EntityNotFoundException("address", id);
        }

        return address;
    }

    @Transactional
    public void deleteAddress(long id) {
        repo.deleteById(id);
    }

    @Transactional
    public Address updateAddress(Address address) throws EntityNotFoundException {
        if (repo.update(address.getId(), address.getCountryCode(), address.getCity(), address.getZipCode(),
                address.getStreet(), address.getHouseNumber(), address.getLongitude(), address.getLatitude()) == 0) {
            throw new EntityNotFoundException("address", address.getId());
        }

        return address;
    }

    public Page<Address> findAddressesBySpec(AddressSpecificationDto dto, Pageable pageable) {
        Specification<Address> resultSpec = Specification.where(null);

        for (var spec : AddressSpecification.SPECIFICATIONS) {
            var s = spec.apply(dto);
            if (s != null) {
                resultSpec = resultSpec.and(s);
            }
        }

        if (pageable.getPageSize() == Integer.MAX_VALUE) {
            return repo.findAll(resultSpec, PageRequest.of(0, pageable.getPageSize(), pageable.getSort()));
        }

        return repo.findAll(resultSpec, pageable);
    }

}
