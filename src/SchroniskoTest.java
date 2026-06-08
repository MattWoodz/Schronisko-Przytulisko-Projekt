import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SchroniskoTest {

    private Schronisko schronisko;

    @BeforeEach
    public void setUp() {
        schronisko = new Schronisko(3);
    }

    @Test
    public void testZarejestrujNoweZwierzeSukces() {
        boolean wynik = schronisko.zarejestrujNoweZwierze(1, "Burek", "Kundel", 5, 12.5);
        assertTrue(wynik);
        assertEquals(1, schronisko.getZwierzeta().size());
    }

    @Test
    public void testZarejestrujNoweZwierzeBrakMiejsca() {
        schronisko.zarejestrujNoweZwierze(1, "Burek", "Kundel", 5, 12.5);
        schronisko.zarejestrujNoweZwierze(2, "Azor", "Mops", 2, 8.0);
        schronisko.zarejestrujNoweZwierze(3, "Reks", "Owczarek", 4, 25.0);

        assertThrows(IllegalStateException.class, () -> {
            schronisko.zarejestrujNoweZwierze(4, "Max", "Pudel", 1, 5.0);
        });
    }

    @Test
    public void testZarejestrujNoweZwierzeDuplikatId() {
        schronisko.zarejestrujNoweZwierze(1, "Burek", "Kundel", 5, 12.5);

        assertThrows(IllegalArgumentException.class, () -> {
            schronisko.zarejestrujNoweZwierze(1, "Inny", "Bokser", 3, 15.0);
        });
    }

    @Test
    public void testZarejestrujNoweZwierzeWielePoprawnych() {
        schronisko.zarejestrujNoweZwierze(1, "A", "Rasa1", 1, 1.0);
        schronisko.zarejestrujNoweZwierze(2, "B", "Rasa2", 2, 2.0);
        assertEquals(2, schronisko.getZwierzeta().size());
    }

    @Test
    public void testSprawdzWolneBoksyPusteSchronisko() {
        assertEquals(3, schronisko.sprawdzWolneBoksy());
    }

    @Test
    public void testSprawdzWolneBoksyPoDodaniu() {
        schronisko.zarejestrujNoweZwierze(1, "Burek", "Kundel", 5, 12.5);
        assertEquals(2, schronisko.sprawdzWolneBoksy());
    }

    @Test
    public void testSprawdzWolneBoksyPoAdopcji() {
        schronisko.zarejestrujNoweZwierze(1, "Burek", "Kundel", 5, 12.5);
        schronisko.zmienStatusNaAdoptowany(1);
        assertEquals(3, schronisko.sprawdzWolneBoksy());
    }

    @Test
    public void testSprawdzWolneBoksyPelne() {
        schronisko.zarejestrujNoweZwierze(1, "A", "R", 1, 1);
        schronisko.zarejestrujNoweZwierze(2, "B", "R", 1, 1);
        schronisko.zarejestrujNoweZwierze(3, "C", "R", 1, 1);
        assertEquals(0, schronisko.sprawdzWolneBoksy());
    }

    @Test
    public void testZmienStatusSukces() {
        schronisko.zarejestrujNoweZwierze(1, "Burek", "Kundel", 5, 12.5);
        boolean wynik = schronisko.zmienStatusNaAdoptowany(1);
        assertTrue(wynik);
        assertTrue(schronisko.getZwierzeta().get(0).isAdoptowany());
    }

    @Test
    public void testZmienStatusNieistniejaceId() {
        assertThrows(IllegalArgumentException.class, () -> {
            schronisko.zmienStatusNaAdoptowany(99);
        });
    }

    @Test
    public void testZmienStatusJuzAdoptowane() {
        schronisko.zarejestrujNoweZwierze(1, "Burek", "Kundel", 5, 12.5);
        schronisko.zmienStatusNaAdoptowany(1);

        assertThrows(IllegalStateException.class, () -> {
            schronisko.zmienStatusNaAdoptowany(1);
        });
    }

    @Test
    public void testZmienStatusSprawdzenieWlasciwosci() {
        schronisko.zarejestrujNoweZwierze(1, "Burek", "Kundel", 5, 12.5);
        assertFalse(schronisko.getZwierzeta().get(0).isAdoptowany());
        schronisko.zmienStatusNaAdoptowany(1);
        assertTrue(schronisko.getZwierzeta().get(0).isAdoptowany());
    }

    @Test
    public void testObliczKosztStandard() {
        double wynik = schronisko.obliczKosztWyzywienia(10.0, "standard");
        assertEquals(2.5, wynik);
    }

    @Test
    public void testObliczKosztPremium() {
        double wynik = schronisko.obliczKosztWyzywienia(20.0, "premium");
        assertEquals(8.0, wynik);
    }

    @Test
    public void testObliczKosztUjemnaWaga() {
        assertThrows(IllegalArgumentException.class, () -> {
            schronisko.obliczKosztWyzywienia(-5.0, "standard");
        });
    }

    @Test
    public void testObliczKosztBlednyTypKarmy() {
        assertThrows(IllegalArgumentException.class, () -> {
            schronisko.obliczKosztWyzywienia(10.0, "super_karma");
        });
    }

    @Test
    public void testSzukajPoRasieIWiekuZnaleziono() {
        schronisko.zarejestrujNoweZwierze(1, "Burek", "Mops", 3, 10.0);
        schronisko.zarejestrujNoweZwierze(2, "Azor", "Mops", 6, 12.0);
        List<Zwierze> wynik = schronisko.szukajPoRasieIWieku("Mops", 4);

        assertEquals(1, wynik.size());
        assertEquals(1, wynik.get(0).getIdZwierzecia());
    }

    @Test
    public void testSzukajPoRasieIWiekuZlyWiek() {
        schronisko.zarejestrujNoweZwierze(1, "Burek", "Mops", 5, 10.0);
        List<Zwierze> wynik = schronisko.szukajPoRasieIWieku("Mops", 4);
        assertEquals(0, wynik.size());
    }

    @Test
    public void testSzukajPoRasieIWiekuZlaRasa() {
        schronisko.zarejestrujNoweZwierze(1, "Burek", "Kundel", 3, 10.0);
        List<Zwierze> wynik = schronisko.szukajPoRasieIWieku("Mops", 4);
        assertEquals(0, wynik.size());
    }

    @Test
    public void testSzukajPoRasieIWiekuPomijaAdoptowane() {
        schronisko.zarejestrujNoweZwierze(1, "Burek", "Mops", 3, 10.0);
        schronisko.zmienStatusNaAdoptowany(1);
        List<Zwierze> wynik = schronisko.szukajPoRasieIWieku("Mops", 4);
        assertEquals(0, wynik.size());
    }
}