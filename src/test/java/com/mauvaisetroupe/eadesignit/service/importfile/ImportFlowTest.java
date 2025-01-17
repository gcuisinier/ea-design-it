package com.mauvaisetroupe.eadesignit.service.importfile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mauvaisetroupe.eadesignit.IntegrationTest;
import com.mauvaisetroupe.eadesignit.domain.FunctionalFlow;
import com.mauvaisetroupe.eadesignit.domain.LandscapeView;
import com.mauvaisetroupe.eadesignit.repository.ApplicationRepository;
import com.mauvaisetroupe.eadesignit.repository.FunctionalFlowRepository;
import com.mauvaisetroupe.eadesignit.repository.LandscapeViewRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@IntegrationTest
public class ImportFlowTest {

    @Autowired
    FlowImportService flowImportService;

    @Autowired
    ApplicationImportService applicationImportService;

    @Autowired
    LandscapeViewRepository landscapeViewRepository;

    @Autowired
    FunctionalFlowRepository functionalFlowRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    protected List<LandscapeView> checkNbLandscapes(int nbLandscape) {
        List<LandscapeView> landscapes;
        landscapes = landscapeViewRepository.findAllWithEagerRelationships();
        assertEquals(nbLandscape, landscapes.size());
        return landscapes;
    }

    protected void checkNbFlows(LandscapeView landscapeView, int nbFlows) {
        assertEquals(nbFlows, landscapeView.getFlows().size());
    }

    protected void checkNbFlows(int nbFlows) {
        List<FunctionalFlow> flows = functionalFlowRepository.findAll();
        assertEquals(nbFlows, flows.size());
    }

    protected void checkNbsteps(String flowAlias, int nbSteps) {
        Optional<FunctionalFlow> optional = functionalFlowRepository.findByAlias(flowAlias);
        assertTrue(optional.isPresent(), "Could not find alias " + flowAlias);
        FunctionalFlow flow = optional.get();
        assertEquals(nbSteps, flow.getSteps().size());
    }

    @AfterEach
    @BeforeEach
    @Transactional
    public void clearDatabase() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.REL_FLOW__INTERFACES;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.REL_APPLICATION__TECHNOLOGIES;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.REL_COMPONENT__CATEGORIES;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.REL_COMPONENT__TECHNOLOGIES;");
        //jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.REL_APPLICATION__CAPABILITIES;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.REL_LANDSCAPE_VIEW__FLOWS;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.REL_DATAFLOW__FUNCTIONAL_FLOWS;");
        //jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.REL_LANDSCAPE_VIE__CAPABILI_21;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.REL_APPLICATION__CATEGORIES;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.REL_OWNER__USERS;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.DATA_FLOW_ITEM;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.DATAFLOW;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.FLOW;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.INTERFACE;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.COMPONENT;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.LANDSCAPE_VIEW;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.APPLICATION;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.DATA_FORMAT");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.OWNER;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.TECHNOLOGY;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.PROTOCOL;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.CAPABILITY;");
        jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.APPLICATION_CATEGORY;");
        jdbcTemplate.execute("TRUNCATE TABLE rel_capability_ap__landscap_b2");
        jdbcTemplate.execute("TRUNCATE TABLE capability_application_mapping");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        // jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.TMP_IMPORT_FLOWS;");
        // jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.TMP_IMPORT_APPLICATION_IMPORT;");
        // jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.JHI_USER_AUTHORITY;");
        // jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.JHI_USER;");
        // jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.JHI_AUTHORITY;");
        // jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.DATABASECHANGELOG;");
        // jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.TMP_IMPORT_DATAFLOWS;");
        // jdbcTemplate.execute("TRUNCATE TABLE PUBLIC.DATABASECHANGELOGLOCK;");
    }
}
