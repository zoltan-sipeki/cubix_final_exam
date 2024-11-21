package hu.cubix.zoltan_sipeki.logistics.model.specification;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import hu.cubix.zoltan_sipeki.logistics.dto.specification.AddressSpecificationDto;
import hu.cubix.zoltan_sipeki.logistics.model.Address;
import hu.cubix.zoltan_sipeki.logistics.model.Address_;

public class AddressSpecification {

    public static List<Function<AddressSpecificationDto, Specification<Address>>> SPECIFICATIONS = List.of(
            AddressSpecification::hasCity,
            AddressSpecification::hasStreet,
            AddressSpecification::hasCountryCode,
            AddressSpecification::hasZipCode);

    private static Specification<Address> hasCity(AddressSpecificationDto spec) {
        if (!StringUtils.hasText(spec.city())) {
            return null;
        }

        return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.CITY)), spec.city().toLowerCase() + "%");
    }

    private static Specification<Address> hasStreet(AddressSpecificationDto spec) {
        if (!StringUtils.hasText(spec.street())) {
            return null;
        }

        return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.STREET)), spec.street().toLowerCase() + "%");
    }

    private static Specification<Address> hasCountryCode(AddressSpecificationDto spec) {
        if (!StringUtils.hasText(spec.countryCode())) {
            return null;
        }

        return (root, cq, cb) -> cb.equal(root.get(Address_.COUNTRY_CODE), spec.countryCode());
    }

    private static Specification<Address> hasZipCode(AddressSpecificationDto spec) {
        if (!StringUtils.hasText(spec.zipCode())) {
            return null;
        }

        return (root, cq, cb) -> cb.equal(root.get(Address_.ZIP_CODE), spec.zipCode());
    }
}
