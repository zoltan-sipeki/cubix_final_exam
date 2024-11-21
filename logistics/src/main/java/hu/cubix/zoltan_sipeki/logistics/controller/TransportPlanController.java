package hu.cubix.zoltan_sipeki.logistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.zoltan_sipeki.logistics.dto.DelayDto;
import hu.cubix.zoltan_sipeki.logistics.dto.TransportPlanDto;
import hu.cubix.zoltan_sipeki.logistics.exception.EntityNotFoundException;
import hu.cubix.zoltan_sipeki.logistics.exception.InvalidOperationException;
import hu.cubix.zoltan_sipeki.logistics.mapper.TransportPlanMapper;
import hu.cubix.zoltan_sipeki.logistics.service.TransportPlanService;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    @Autowired
    private TransportPlanService service;

    @Autowired
    private TransportPlanMapper mapper;

    @PostMapping("/{id}/delay")
    public TransportPlanDto registerDelay(@PathVariable long id, @RequestBody DelayDto delay) throws EntityNotFoundException, InvalidOperationException {
        var plan = service.registerDelay(id, delay);
        return mapper.mapTransportPlanToDto(plan);
    }
    
}
