package hu.cubix.zoltan_sipeki.logistics.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import hu.cubix.zoltan_sipeki.logistics.model.Address;
import hu.cubix.zoltan_sipeki.logistics.model.Milestone;
import hu.cubix.zoltan_sipeki.logistics.model.Section;
import hu.cubix.zoltan_sipeki.logistics.model.TransportPlan;

@Service
public class TestDataGeneratorService {

    private static final List<String> COUNTRY_CODES = List.of("BV","MT","GS","AI","SH","MD","SS","ES","SC","AM","SX","UY","NA","MR","GY","TF","GL","LK","FR","TV","US","FI","TW","EH","ZM","PN","AL","MP","ML","BJ","BA","HU","AT","EH","LR","RW","GU","MA","SL","GL","ID","JO","BE","SK","KM","ZA","DK","MR","KG","RU");
    
    private static final List<String> CITIES = List.of("Fort Fabiola","East Orentown","Port Giovanni","East Danial","New Graciela","Considineburgh","Edina","Johnsonstead","Cypress","Santa Clarita","West Maxwellberg","North Kathrynburgh","Fort Jennifer","Santa Clara","Clearwater","Darrickcester","Pacochacester","Fort Audreanne","Lorenland","Casa Grande","Schusterstad","Conroyberg","Sauerbury","North Shyannbury","West Jovanny","East Bertram","Catonsville","West Felipachester","Remingtonburgh","Fort Kobe","East Arnoldo","Burien","Anderson","Arlington","North Clotilde","Kaiacester","Bethshire","North Richie","North Bridgetmouth","Nestorland","Ruthefurt","West Melvinburgh","Christopheton","Aricside","South Chaimboro","Pittsfield","Enid","West Lilla","New Donatostad","Lednerfurt");

    private static final List<String> ZIP_CODES = List.of("1465","3883","8012","5290","0090","3954","0450","4098","9915","2991","5318","6283","3401","9271","9359","8284","0759","3005","9378","4186","5182","5620","2123","8163","2981","3223","7561","0260","5642","0598","5368","4333","7404","4098","4336","5516","4961","5811","2187","6036","0347","3591","2861","2932","5554","0879","2657","4892","0490","1712");

    private static final List<String> STREETS = List.of("Lester Canyon","Evert Squares","Lind Cape","Jones Crossroad","Flatley Point","Torphy Flat","Walker Orchard","Mauricio Trafficway","Earl Lock","Gerda Knolls","Wilfrid Rapid","Klein Plain","Kozey Estate","Stiedemann Road","Heloise Spur","Rogahn Orchard","Legros Ranch","Roscoe Mountain","Oren Row","Joy Mill","Waters Rue","Hammes Walk","Klocko Viaduct","Mueller Club","Runolfsdottir Stream","Kris Valley","Becker Flats","Ward Summit","Nienow Forge","Burley Plain","Alessandro Summit","Corkery Garden","Renner Circles","Petra Forges","Rempel Burg","Wilburn Mountain","Loy Island","Viola Junctions","Alexie Streets","Cassin Fall","Littel Ford","Horace Trace","Hills Mount","Winston Union","Kari Skyway","Rowe Parkway","McGlynn Hollow","Stehr Valleys","Jalen Expressway","Randi Road");

    private static final List<String> HOUSE_NUMBERS = List.of("226","65817","231","89671","3987","693","6444","655","73692","8780","8075","7557","8754","94232","6411","120","7580","97693","2941","8936","1277","89049","4401","24427","64047","944","8656","35569","4357","950","33588","44512","1220","1510","272","1273","62450","3130","21354","467","4139","308","911","6832","4276","8103","3091","225","22070","76213");

    private static final List<Double> LATITUDES = List.of(-46.7861,-6.9429,-23.5086,72.3624,-25.5611,-8.8888,-74.3537,77.4279,9.5243,1.17,-3.7952,-51.0553,36.0748,17.0217,57.9264,4.9242,25.3228,-2.3467,-20.3636,72.5854,-53.431,53.8273,-2.2558,76.3279,-33.1732,24.2277,89.3523,39.2995,-6.5526,-34.7436,49.9584,32.5751,26.451,55.9663,-81.6155,79.8606,-13.1246,-10.9323,42.7962,-56.8181,21.7563,-2.7331,-65.0675,-29.2132,-30.6791,-56.9028,54.7235,40.0591,13.734,-0.4145);

    private static final List<Double> LONGITUDES = List.of(53.3928,173.3964,33.9335,47.1142,-36.6589,37.1626,-148.1986,112.4158,-19.2517,-145.1062,158.216,-32.3264,44.6722,48.3625,17.0804,-19.9955,42.2596,-107.6859,-1.2699,-137.301,-31.7508,-175.7039,-56.4619,70.1482,-53.236,65.1213,-177.2418,45.6788,152.6018,155.8472,-153.0084,-148.0748,-47.9847,172.9393,-38.6702,94.1587,44.3216,175.5233,-167.0648,-32.6346,-31.513,113.116,-127.3757,-123.1397,-116.6931,69.8703,-121.5129,16.8667,64.1632,-162.2064);

    private static final List<LocalDateTime> PLANNED_TIMES = List.of(LocalDateTime.of(2024,01,8,0,0),LocalDateTime.of(2024,01,16,0,0),LocalDateTime.of(2024,01,17,0,0),LocalDateTime.of(2024,01,24,0,0),LocalDateTime.of(2024,01,27,0,0),LocalDateTime.of(2024,02,11,0,0),LocalDateTime.of(2024,02,22,0,0),LocalDateTime.of(2024,02,22,0,0),LocalDateTime.of(2024,03,01,0,0),LocalDateTime.of(2024,04,02,0,0),LocalDateTime.of(2024,04,05,0,0),LocalDateTime.of(2024,04,10,0,0),LocalDateTime.of(2024,04,14,0,0),LocalDateTime.of(2024,04,19,0,0),LocalDateTime.of(2024,04,24,0,0),LocalDateTime.of(2024,05,03,0,0),LocalDateTime.of(2024,05,03,0,0),LocalDateTime.of(2024,05,03,0,0),LocalDateTime.of(2024,05,14,0,0),LocalDateTime.of(2024,05,19,0,0),LocalDateTime.of(2024,05,22,0,0),LocalDateTime.of(2024,05,27,0,0),LocalDateTime.of(2024,06,19,0,0),LocalDateTime.of(2024,06,21,0,0),LocalDateTime.of(2024,07,01,0,0),LocalDateTime.of(2024,07,04,0,0),LocalDateTime.of(2024,07,06,0,0),LocalDateTime.of(2024,07,17,0,0),LocalDateTime.of(2024,07,28,0,0),LocalDateTime.of(2024,8,07,0,0),LocalDateTime.of(2024,8,23,0,0),LocalDateTime.of(2024,8,30,0,0),LocalDateTime.of(2024,9,19,0,0),LocalDateTime.of(2024,9,22,0,0),LocalDateTime.of(2024,9,27,0,0),LocalDateTime.of(2024,10,06,0,0),LocalDateTime.of(2024,10,06,0,0),LocalDateTime.of(2024,10,20,0,0),LocalDateTime.of(2024,11,02,0,0),LocalDateTime.of(2024,11,05,0,0),LocalDateTime.of(2024,11,05,0,0),LocalDateTime.of(2024,11,8,0,0),LocalDateTime.of(2024,11,9,0,0),LocalDateTime.of(2024,11,10,0,0),LocalDateTime.of(2024,11,16,0,0),LocalDateTime.of(2024,11,17,0,0),LocalDateTime.of(2024,11,18,0,0),LocalDateTime.of(2024,11,18,0,0),LocalDateTime.of(2024,11,24,0,0),LocalDateTime.of(2024,11,30,0,0));
    
    private static final List<Integer> INCOMES = List.of(5000000, 10000000, 7000000, 13000000, 20000000);
    
    private static final int NUMBER_OF_TRANSPORT_PLANS = 5;

    private static final int NUMBER_OF_SECTIONS = NUMBER_OF_TRANSPORT_PLANS * 5;


    public List<Address> createAddresses() {
        var list = new ArrayList<Address>();

        for (int i = 0; i < COUNTRY_CODES.size(); ++i) {
            var address = new Address();
            address.setCountryCode(COUNTRY_CODES.get(i));
            address.setCity(CITIES.get(i));
            address.setZipCode(ZIP_CODES.get(i));
            address.setStreet(STREETS.get(i));
            address.setHouseNumber(HOUSE_NUMBERS.get(i));
            address.setLatitude(LATITUDES.get(i));
            address.setLongitude(LONGITUDES.get(i));
            list.add(address);
        }

        return list;
    }

    public List<Milestone> createMilestones(List<Address> addresses) {
        var list = new ArrayList<Milestone>();
        for (int i = 0; i < addresses.size(); ++i) {
            var milestone = new Milestone();
            milestone.setAddress(addresses.get(i));
            milestone.setPlannedTime(PLANNED_TIMES.get(i));
            list.add(milestone);
        }

        return list;
    }

    public List<TransportPlan> createTransportPlans() {
        var list = new ArrayList<TransportPlan>();
        for (var income : INCOMES) {
            var plan = new TransportPlan();
            plan.setIncome(income);
            list.add(plan);
        }

        return list;
    }

    public List<Section> createSections(List<Milestone> milestones, List<TransportPlan> transportPlans) {
        var list = new ArrayList<Section>();
        int mileStoneCounter = 0;
        for (int i = 0; i < NUMBER_OF_SECTIONS; ++i) {
            var section = new Section();
            section.setStartMilestone(milestones.get(mileStoneCounter++));
            section.setEndMilestone(milestones.get(mileStoneCounter++));
            section.setSequenceNumber(i % NUMBER_OF_TRANSPORT_PLANS);
            section.setTransportPlan(transportPlans.get(i / NUMBER_OF_TRANSPORT_PLANS));
            list.add(section);
        }
        return list;
    }
}
