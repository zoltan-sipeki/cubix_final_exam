package hu.cubix.zoltan_sipeki.logistics.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.cubix.zoltan_sipeki.logistics.model.Address;
import hu.cubix.zoltan_sipeki.logistics.model.Milestone;
import hu.cubix.zoltan_sipeki.logistics.model.Section;
import hu.cubix.zoltan_sipeki.logistics.model.TransportPlan;
import hu.cubix.zoltan_sipeki.logistics.repository.AddressRepository;
import hu.cubix.zoltan_sipeki.logistics.repository.MilestoneRepository;
import hu.cubix.zoltan_sipeki.logistics.repository.SectionRepository;
import hu.cubix.zoltan_sipeki.logistics.repository.TransportPlanRepository;

@SuppressWarnings("unchecked")
@Service
public class InitDbService {
    
    @Autowired
    private TestDataGeneratorService gen;

    @Autowired
    private AddressRepository addressRepo;

    @Autowired
    private TransportPlanRepository planRepo;

    @Autowired
    private SectionRepository sectionRepo;

    @Autowired
    private MilestoneRepository milestoneRepo;

    private Map<Class<?>, Function<List<?>, List<?>>> batchInserts;

    public InitDbService() {
        batchInserts = new HashMap<>();
        batchInserts.put(Address.class, list -> addressRepo.saveAll((List<Address>) list));
        batchInserts.put(TransportPlan.class, list -> planRepo.saveAll((List<TransportPlan>) list));
        batchInserts.put(Section.class, list -> sectionRepo.saveAll((List<Section>) list));
        batchInserts.put(Milestone.class, list -> milestoneRepo.saveAll((List<Milestone>) list));
    }

    @Transactional
    public void initAll() {
        var addresses = gen.createAddresses();
        addresses = insertTestData(Address.class, addresses);
        
        var transportPlans = gen.createTransportPlans();
        transportPlans = insertTestData(TransportPlan.class, transportPlans);
        
        var milestones = gen.createMilestones(addresses);
        milestones = insertTestData(Milestone.class, milestones);

        var sections = gen.createSections(milestones, transportPlans);
        sections = insertTestData(Section.class, sections);
    }

    public <T> List<T> insertTestData(Class<T> type, List<T> list) {
        var function = batchInserts.get(type);
        return (List<T>) function.apply(list);
    }
}
