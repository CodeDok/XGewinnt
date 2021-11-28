/**
 * @author Khanh An Do
 * @version 1.0
 * @since 26.03.2021
 *
 * Testet die Funktionlitaet der Mobility App
 */

package pack;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class Testfaelle {

    private KonkreterVermittler vermittler;



    @BeforeEach
    void setup() {
        this.vermittler = new KonkreterVermittler();
    }



    @Test
    @DisplayName("Testfall 1:")
    void Testfall1() {
        Partner pa = new PartnerAngebot(vermittler, pack.Standort.A);
        Partner pa2 = new PartnerAngebot(vermittler, pack.Standort.A);
        Partner ps = new PartnerSuche(vermittler, pack.Standort.A);
        Partner ps2 = new PartnerSuche(vermittler, pack.Standort.A);

        vermittler.registrierePartner(pa);
        vermittler.registrierePartner(pa2);
        vermittler.registrierePartner(ps);
        vermittler.registrierePartner(ps2);
        System.out.println(vermittler.getInformationen("Nach dem Registrieren: "));

        ps.senden();
        ps2.senden();
        System.out.println(vermittler.getInformationen("Nach dem Senden: "));

        assertEquals("a1.B, a2.B", vermittler.getInformationenAngebote());

    }



    @Test
    @DisplayName("Testfall 2:")
    void Testfall2() {
        PartnerAngebot pa = new PartnerAngebot(vermittler, pack.Standort.A);
        PartnerAngebot pa2 = new PartnerAngebot(vermittler, pack.Standort.A);
        PartnerSuche ps = new PartnerSuche(vermittler, pack.Standort.B);
        PartnerSuche ps2 = new PartnerSuche(vermittler, pack.Standort.A);

        vermittler.registrierePartner(pa);
        vermittler.registrierePartner(pa2);
        vermittler.registrierePartner(ps);
        vermittler.registrierePartner(ps2);
        System.out.println(vermittler.getInformationen("Nach dem Registrieren: "));

        ps.senden();
        ps2.senden();
        System.out.println(vermittler.getInformationen("Nach dem Senden: "));

        Map<Standort, Integer> expectedMap = new HashMap<>();
        expectedMap.put(pack.Standort.A, 1);
        expectedMap.put(pack.Standort.B, 1);

        assertEquals(expectedMap, vermittler.getFahrzeuganzahlStandorte());
    }



    @Test
    @DisplayName("Testfall 3:")
    void Testfall3() {
        PartnerAngebot pa = new PartnerAngebot(vermittler, pack.Standort.A);
        PartnerSuche ps = new PartnerSuche(vermittler, pack.Standort.B);

        vermittler.registrierePartner(pa);
        vermittler.registrierePartner(ps);
        System.out.println(vermittler.getInformationen("Nach dem Registrieren: "));

        assertThrows(NoSuchElementException.class, () -> {
            vermittler.empfangen(ps);
        });
        System.out.println(vermittler.getInformationen("Nach dem Senden: "));

    }



    @Test
    @DisplayName("Testfall 4:")
    @Timeout(value = 2)
    void Testfall4() {
        int max = 100_000;
        PartnerSuche ps1;
        ArrayList<PartnerSuche> suchen = new ArrayList<>();

        Instant start = Instant.now();
        for (int i = 0; i < max; i++) {
            vermittler.registrierePartner(new PartnerAngebot(vermittler, pack.Standort.A));
        }

        for (int i = 0; i < max; i++) {
            ps1 = new PartnerSuche(vermittler, pack.Standort.A);
            suchen.add(ps1);
            vermittler.registrierePartner(ps1);
        }

        for (int i = 0; i < max; i++) {
            suchen.get(i).senden();
        }

        System.out.println(vermittler.getFahrzeuganzahlStandorte());
        Instant end = Instant.now();
        System.out.println("Execution took " + Duration.between(start, end).toMillis() + "ms.");
    }



    @Test
    @Disabled
    @DisplayName("HashTest")
    void Testfall5() {
        ArrayList<Integer> angeboteHash = new ArrayList<>();
        ArrayList<PartnerAngebot> angebote = new ArrayList<>();

        ArrayList<Integer> sucheHash = new ArrayList<>();
        ArrayList<PartnerSuche> suche = new ArrayList<>();

        HashMap<Integer, Partner> duplicate = new HashMap();


        for (int i = 0; i < 500_000; i++) {
            PartnerAngebot partnerAngebot = new PartnerAngebot(vermittler, Standort.A);
            angeboteHash.add(partnerAngebot.hashCode());
            angebote.add(partnerAngebot);

            PartnerSuche partnerSuche = new PartnerSuche(vermittler, Standort.A);
            sucheHash.add(partnerSuche.hashCode());
            suche.add(partnerSuche);
        }

        for (int i = 0; i < angebote.size(); i++) {
            int paHash = angeboteHash.get(i);
            PartnerAngebot pa = angebote.get(i);
            angebote.remove(i);
            angeboteHash.remove(i);

            int psHash = sucheHash.get(i);
            PartnerSuche ps = suche.get(i);
            suche.remove(i);
            sucheHash.remove(i);

            if(angeboteHash.contains(paHash)) {
                duplicate.put(paHash, pa);
            }
            if(sucheHash.contains(psHash)) {
                duplicate.put(psHash, ps);
            }
        }
        System.out.println(duplicate);
        assertTrue(duplicate.isEmpty());
    }

}