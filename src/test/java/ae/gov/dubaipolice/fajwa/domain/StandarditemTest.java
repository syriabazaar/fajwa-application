package ae.gov.dubaipolice.fajwa.domain;

import static ae.gov.dubaipolice.fajwa.domain.IntrvuStrdDtlsTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.StandardCatTestSamples.*;
import static ae.gov.dubaipolice.fajwa.domain.StandarditemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.fajwa.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class StandarditemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Standarditem.class);
        Standarditem standarditem1 = getStandarditemSample1();
        Standarditem standarditem2 = new Standarditem();
        assertThat(standarditem1).isNotEqualTo(standarditem2);

        standarditem2.setId(standarditem1.getId());
        assertThat(standarditem1).isEqualTo(standarditem2);

        standarditem2 = getStandarditemSample2();
        assertThat(standarditem1).isNotEqualTo(standarditem2);
    }

    @Test
    void standardCatTest() {
        Standarditem standarditem = getStandarditemRandomSampleGenerator();
        StandardCat standardCatBack = getStandardCatRandomSampleGenerator();

        standarditem.setStandardCat(standardCatBack);
        assertThat(standarditem.getStandardCat()).isEqualTo(standardCatBack);

        standarditem.standardCat(null);
        assertThat(standarditem.getStandardCat()).isNull();
    }

    @Test
    void intrvuStrdDtlsTest() {
        Standarditem standarditem = getStandarditemRandomSampleGenerator();
        IntrvuStrdDtls intrvuStrdDtlsBack = getIntrvuStrdDtlsRandomSampleGenerator();

        standarditem.addIntrvuStrdDtls(intrvuStrdDtlsBack);
        assertThat(standarditem.getIntrvuStrdDtls()).containsOnly(intrvuStrdDtlsBack);
        assertThat(intrvuStrdDtlsBack.getStandarditem()).isEqualTo(standarditem);

        standarditem.removeIntrvuStrdDtls(intrvuStrdDtlsBack);
        assertThat(standarditem.getIntrvuStrdDtls()).doesNotContain(intrvuStrdDtlsBack);
        assertThat(intrvuStrdDtlsBack.getStandarditem()).isNull();

        standarditem.intrvuStrdDtls(new HashSet<>(Set.of(intrvuStrdDtlsBack)));
        assertThat(standarditem.getIntrvuStrdDtls()).containsOnly(intrvuStrdDtlsBack);
        assertThat(intrvuStrdDtlsBack.getStandarditem()).isEqualTo(standarditem);

        standarditem.setIntrvuStrdDtls(new HashSet<>());
        assertThat(standarditem.getIntrvuStrdDtls()).doesNotContain(intrvuStrdDtlsBack);
        assertThat(intrvuStrdDtlsBack.getStandarditem()).isNull();
    }
}
