package com.mauvaisetroupe.eadesignit.service.importfile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mauvaisetroupe.eadesignit.domain.FunctionalFlow;
import com.mauvaisetroupe.eadesignit.domain.LandscapeView;
import com.mauvaisetroupe.eadesignit.repository.ApplicationRepository;
import com.mauvaisetroupe.eadesignit.repository.FunctionalFlowRepository;
import com.mauvaisetroupe.eadesignit.repository.LandscapeViewRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.poi.EncryptedDocumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class FlowImportOneTest extends ImportFlowTest {

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

    @Test
    void testNullable() throws EncryptedDocumentException, IOException {
        ExcelReader flowImportService = new ExcelReader(null);
        assertTrue(flowImportService.isNull("?"), "should be null");
        assertTrue(flowImportService.isNull("??"), "should be null");
        assertTrue(flowImportService.isNull("???"), "should be null");
    }

    @ParameterizedTest
    @CsvSource({ "02-import-flows.xlsx,1", "02-import-flows-02.xlsx,4" })
    void testImport(String filemame, int expected) throws EncryptedDocumentException, IOException {
        assertEquals(0, applicationRepository.findAll().size());
        assertEquals(0, landscapeViewRepository.findAll().size());
        assertEquals(0, functionalFlowRepository.findAll().size());

        InputStream file1 = this.getClass().getResourceAsStream("/junit/01-import-applications.xlsx");
        assertNotNull(file1);
        applicationImportService.importExcel(file1, "my-applications.xlsx");

        assertEquals(4, applicationRepository.findAll().size());
        assertEquals(0, landscapeViewRepository.findAll().size());
        assertEquals(0, functionalFlowRepository.findAll().size());

        InputStream file2 = this.getClass().getResourceAsStream("/junit/" + filemame);
        assertNotNull(file2);
        flowImportService.importExcel(file2, "my-landscape.xlsx");

        List<LandscapeView> landscapes = checkNbLandscapes(1);
        checkNbFlows(landscapes.get(0), expected);
    }

    @ParameterizedTest
    @CsvSource({ "02-import-flows.xlsx,2", "02-import-flows-02.xlsx,5" })
    void shouldImportExternal(String filename, int expected) throws EncryptedDocumentException, IOException {
        // Id flow	Alias flow	External	Source Element	Target Element
        // TRAD.001			APPLICATION-0001	APPLICATION-0002
        // TRAD.002			APPLICATION-0002	APPLICATION-0003
        // TRAD.003			APPLICATION-0003	APPLICATION-0004
        // TRAD.004			APPLICATION-0004	APPLICATION-0003
        // EXT.001	S.02	yes	APPLICATION-0004	APPLICATION-0003

        // creat CYP.02 marked as external... should now be added during import
        FunctionalFlow cyp02 = new FunctionalFlow();
        cyp02.setAlias("CYP.02");
        functionalFlowRepository.save(cyp02);

        InputStream file1 = this.getClass().getResourceAsStream("/junit/01-import-applications.xlsx");
        applicationImportService.importExcel(file1, "my-applications.xlsx");
        InputStream file2 = this.getClass().getResourceAsStream("/junit/" + filename);
        flowImportService.importExcel(file2, "my-landscape.xlsx");

        List<LandscapeView> landscapes = checkNbLandscapes(1);
        checkNbFlows(landscapes.get(0), expected);
    }
}
