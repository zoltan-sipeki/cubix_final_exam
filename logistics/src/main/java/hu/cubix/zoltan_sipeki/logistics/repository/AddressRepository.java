package hu.cubix.zoltan_sipeki.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.cubix.zoltan_sipeki.logistics.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {

    @Transactional
    @Modifying
    @Query("""
        update Address a set
            a.countryCode = :countryCode,
            a.city = :city,
            a.zipCode= :zipCode,
            a.street = :street,
            a.houseNumber = :houseNumber,
            a.latitude = :latitude,
            a.longitude = :longitude
        where a.id = :id
    """)
    public int update(long id, String countryCode, String city, String zipCode, String street, String houseNumber,
            double longitude, double latitude);
}
