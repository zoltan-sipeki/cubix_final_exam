package hu.cubix.zoltan_sipeki.logistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.cubix.zoltan_sipeki.logistics.dto.AddressDto;
import hu.cubix.zoltan_sipeki.logistics.dto.AddressDto.OnCreate;
import hu.cubix.zoltan_sipeki.logistics.dto.specification.AddressSpecificationDto;
import hu.cubix.zoltan_sipeki.logistics.exception.ConstraintViolationException;
import hu.cubix.zoltan_sipeki.logistics.exception.EntityNotFoundException;
import hu.cubix.zoltan_sipeki.logistics.mapper.AddressMapper;
import hu.cubix.zoltan_sipeki.logistics.service.AddressService;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private static final String X_TOTAL_COUNT = "X-Total-Count";

    @Autowired
    private AddressService service;

    @Autowired
    private AddressMapper mapper;

    @Autowired
    private Validator validator;

    @PostMapping
    public AddressDto createAddress(@RequestBody AddressDto dto) throws ConstraintViolationException {
        validate(dto, OnCreate.class);
        var address = mapper.mapDtoToAddress(dto);
        address = service.creatAddress(address);
        return mapper.mapAddressToDto(address);
    }

    @GetMapping
    public List<AddressDto> getAllAddresses() {
        var addresses = service.getAllAddresses();
        return mapper.mapAddressListToDtoList(addresses);
    }

    @GetMapping("/{id}")
    public AddressDto getAddressById(@PathVariable long id) throws EntityNotFoundException {
        var address = service.getAddressById(id);
        return mapper.mapAddressToDto(address);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable long id) {
        service.deleteAddress(id);
    }

    @PutMapping("/{id}")
    public AddressDto updateAddress(@PathVariable long id, @RequestBody AddressDto dto)
            throws EntityNotFoundException, ConstraintViolationException {
        if (dto.getId() != id) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The id in the path and the id in the body must match.");
        }
        
        validate(dto);

        var address = mapper.mapDtoToAddress(dto);
        address = service.updateAddress(address);
        return mapper.mapAddressToDto(address);
    }

    @PostMapping("/search")
    public ResponseEntity<List<AddressDto>> searchBySpecification(@RequestBody AddressSpecificationDto dto,
            @PageableDefault(size = Integer.MAX_VALUE, sort = "id") Pageable pageable) {
        var page = service.findAddressesBySpec(dto, pageable);
        var list = mapper.mapAddressListToDtoList(page.getContent());
        return ResponseEntity.ok().header(X_TOTAL_COUNT, Long.toString(page.getTotalElements())).body(list);
    }

    private void validate(AddressDto dto, Class<?>... groups) throws ConstraintViolationException {
        var violations = validator.validate(dto, groups);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
