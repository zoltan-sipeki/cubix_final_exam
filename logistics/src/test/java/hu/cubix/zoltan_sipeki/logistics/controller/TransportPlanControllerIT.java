package hu.cubix.zoltan_sipeki.logistics.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.cubix.zoltan_sipeki.logistics.configuration.DelayConfig;
import hu.cubix.zoltan_sipeki.logistics.dto.DelayDto;
import hu.cubix.zoltan_sipeki.logistics.repository.MilestoneRepository;
import hu.cubix.zoltan_sipeki.logistics.repository.TransportPlanRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class TransportPlanControllerIT {

    private static final String TRANSPORT_MANAGER_JWT = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InVzZXIyIiwicm9sZXMiOlsiUk9MRV9UcmFuc3BvcnRNYW5hZ2VyIl0sImlzcyI6IkxvZ2lzdGljc0FwcGxpY2F0aW9uIn0.qrYLnZ3LVIuiMoVwjxPTtZIQTknxKTJkraePZXdYeiu0V1is09G6ctGNwOf4sBOnXhteSKPEun48mZh1fVBiiA";

    private static final long INVALID_TRANSPORT_PLAN_ID = 50;

    private static final long VALID_TRANSPORT_PLAN_ID = 1;

    private static final long INVALID_MILESTONE_ID = 100;

    private static final long VALID_BUT_DIFFERENT_MILESTONE_ID = 24;

    private static final long START_MILESTONE_ID_1 = 1;

    private static final long START_MILESTONE_ID_2 = 3;

    private static final long END_MILESTONE_ID = 2;

    private static final int DELAY_MINUTE_GT_120 = 140;

    private static final int DELAY_MINUTE_120_60 = 80;

    private static final int DELAY_MINUTE_60_30 = 40;

    private static final int DELAY_MINUTE_LT_30 = 20;

    @Autowired
    WebTestClient client;

    @Autowired
    DelayConfig delayConfig;

    @Autowired
    TransportPlanRepository transportPlanRepo;

    @Autowired
    MilestoneRepository milestoneRepo;

    @Test
    void delay_TransportPlanDoesNotExist() {
        var oldMilestone = milestoneRepo.findById(START_MILESTONE_ID_1).orElse(null);
        assertThat(oldMilestone).isNotNull();

        registerDelay(INVALID_TRANSPORT_PLAN_ID, new DelayDto(START_MILESTONE_ID_1, DELAY_MINUTE_GT_120)).expectStatus()
                .isNotFound();

        var newMilestone = milestoneRepo.findById(START_MILESTONE_ID_1).orElse(null);
        assertThat(newMilestone).isNotNull();
        assertThat(newMilestone).usingRecursiveComparison().isEqualTo(oldMilestone);
    }

    @Test
    void delay_MilestoneDoesNotExist() {
        var oldPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(oldPlan).isNotNull();

        registerDelay(VALID_TRANSPORT_PLAN_ID, new DelayDto(INVALID_MILESTONE_ID, DELAY_MINUTE_GT_120)).expectStatus()
                .isNotFound();

        var newPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(newPlan).isNotNull();
        assertThat(newPlan).usingRecursiveComparison().isEqualTo(oldPlan);
    }

    @Test
    void delay_MilestoneNotInTransportPlan() {
        var oldMilestone = milestoneRepo.findById(VALID_BUT_DIFFERENT_MILESTONE_ID).orElse(null);
        assertThat(oldMilestone).isNotNull();

        var oldPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(oldPlan).isNotNull();

        registerDelay(VALID_TRANSPORT_PLAN_ID, new DelayDto(VALID_BUT_DIFFERENT_MILESTONE_ID, DELAY_MINUTE_GT_120))
                .expectStatus().isBadRequest();

        var newPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(newPlan).isNotNull();
        assertThat(newPlan).usingRecursiveComparison().isEqualTo(oldPlan);

        var newMilestone = milestoneRepo.findById(VALID_BUT_DIFFERENT_MILESTONE_ID).orElse(null);
        assertThat(newMilestone).isNotNull();
        assertThat(newMilestone).usingRecursiveComparison().isEqualTo(oldMilestone);
    }

    @Test
    void delay_StartMilestone() {
        var oldStartMilestone = milestoneRepo.findById(START_MILESTONE_ID_1).orElse(null);
        assertThat(oldStartMilestone).isNotNull();

        var oldEndMilestone = milestoneRepo.findById(END_MILESTONE_ID).orElse(null);
        assertThat(oldEndMilestone).isNotNull();

        registerDelay(VALID_TRANSPORT_PLAN_ID, new DelayDto(START_MILESTONE_ID_1, DELAY_MINUTE_GT_120)).expectStatus()
                .isOk();

        var newStartMilestone = milestoneRepo.findById(START_MILESTONE_ID_1).orElse(null);
        assertThat(newStartMilestone).isNotNull();

        var newEndMilestone = milestoneRepo.findById(END_MILESTONE_ID).orElse(null);
        assertThat(newEndMilestone).isNotNull();

        assertThat(newStartMilestone.getId()).isEqualTo(oldStartMilestone.getId());
        assertThat(newStartMilestone.getPlannedTime().minusMinutes(DELAY_MINUTE_GT_120))
                .isEqualTo(oldStartMilestone.getPlannedTime());
        assertThat(newStartMilestone.getAddress()).usingRecursiveComparison().isEqualTo(oldStartMilestone.getAddress());

        assertThat(newEndMilestone.getId()).isEqualTo(newEndMilestone.getId());
        assertThat(newEndMilestone.getPlannedTime().minusMinutes(DELAY_MINUTE_GT_120))
                .isEqualTo(oldEndMilestone.getPlannedTime());
        assertThat(newEndMilestone.getAddress()).usingRecursiveComparison().isEqualTo(oldEndMilestone.getAddress());
    }

    @Test
    void delay_EndMilestone() {
        var oldEndMilestone = milestoneRepo.findById(END_MILESTONE_ID).orElse(null);
        assertThat(oldEndMilestone).isNotNull();

        var oldStartMilestone = milestoneRepo.findById(START_MILESTONE_ID_2).orElse(null);
        assertThat(oldStartMilestone).isNotNull();

        registerDelay(VALID_TRANSPORT_PLAN_ID, new DelayDto(END_MILESTONE_ID, DELAY_MINUTE_GT_120)).expectStatus()
                .isOk();

        var newEndMilestone = milestoneRepo.findById(END_MILESTONE_ID).orElse(null);
        assertThat(newEndMilestone).isNotNull();

        var newStartMilestone = milestoneRepo.findById(START_MILESTONE_ID_2).orElse(null);
        assertThat(newStartMilestone).isNotNull();

        assertThat(newEndMilestone.getId()).isEqualTo(oldEndMilestone.getId());
        assertThat(newEndMilestone.getPlannedTime().minusMinutes(DELAY_MINUTE_GT_120))
                .isEqualTo(oldEndMilestone.getPlannedTime());
        assertThat(newEndMilestone.getAddress()).usingRecursiveComparison().isEqualTo(oldEndMilestone.getAddress());

        assertThat(newStartMilestone.getId()).isEqualTo(oldStartMilestone.getId());
        assertThat(newStartMilestone.getPlannedTime().minusMinutes(DELAY_MINUTE_GT_120))
                .isEqualTo(oldStartMilestone.getPlannedTime());
        assertThat(newStartMilestone.getAddress()).usingRecursiveComparison().isEqualTo(oldStartMilestone.getAddress());
    }

    @Test
    void delay_DelayGreaterThan120m() {
        var oldPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(oldPlan).isNotNull();

        registerDelay(VALID_TRANSPORT_PLAN_ID, new DelayDto(START_MILESTONE_ID_1, DELAY_MINUTE_GT_120)).expectStatus().isOk();

        var newPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(newPlan).isNotNull();

        oldPlan.cutIncomeByPercent(delayConfig.getIncomeCutPercents().get(120));
        assertThat(oldPlan.getIncome()).isEqualTo(newPlan.getIncome());
        assertThat(newPlan.getId()).isEqualTo(oldPlan.getId());
    }

    @Test
    void delay_DelayBetween120mAnd60m() {
        var oldPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(oldPlan).isNotNull();

        registerDelay(VALID_TRANSPORT_PLAN_ID, new DelayDto(START_MILESTONE_ID_1, DELAY_MINUTE_120_60)).expectStatus().isOk();

        var newPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(newPlan).isNotNull();

        oldPlan.cutIncomeByPercent(delayConfig.getIncomeCutPercents().get(60));
        assertThat(oldPlan.getIncome()).isEqualTo(newPlan.getIncome());
        assertThat(newPlan.getId()).isEqualTo(oldPlan.getId());
    }

    @Test
    void delay_DelayBetween60mAnd30m() {
        var oldPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(oldPlan).isNotNull();

        registerDelay(VALID_TRANSPORT_PLAN_ID, new DelayDto(START_MILESTONE_ID_1, DELAY_MINUTE_60_30)).expectStatus().isOk();

        var newPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(newPlan).isNotNull();

        oldPlan.cutIncomeByPercent(delayConfig.getIncomeCutPercents().get(30));
        assertThat(oldPlan.getIncome()).isEqualTo(newPlan.getIncome());
        assertThat(newPlan.getId()).isEqualTo(oldPlan.getId());
    }

    @Test
    void delay_DelayLessThan30m() {
        var oldPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(oldPlan).isNotNull();

        registerDelay(VALID_TRANSPORT_PLAN_ID, new DelayDto(START_MILESTONE_ID_1, DELAY_MINUTE_LT_30)).expectStatus().isOk();

        var newPlan = transportPlanRepo.findById(VALID_TRANSPORT_PLAN_ID).orElse(null);
        assertThat(newPlan).isNotNull();

        assertThat(oldPlan.getIncome()).isEqualTo(newPlan.getIncome());
        assertThat(newPlan.getId()).isEqualTo(oldPlan.getId());
    }

    private ResponseSpec registerDelay(long transportPlanId, DelayDto dto) {
        return client.post().uri("/api/transportPlans/" + transportPlanId + "/delay")
                .header("Authorization", "Bearer " + TRANSPORT_MANAGER_JWT).bodyValue(dto).exchange();
    }
}
