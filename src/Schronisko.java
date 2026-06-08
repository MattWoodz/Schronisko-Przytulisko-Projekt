import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schronisko {
    private int pojemnosc;
    private List<Zwierze> zwierzeta;
    private Map<String, Double> cennikKarmy;

    public Schronisko(int pojemnosc) {
        this.pojemnosc = pojemnosc;
        this.zwierzeta = new ArrayList<>();
        this.cennikKarmy = new HashMap<>();
        this.cennikKarmy.put("standard", 5.0);
        this.cennikKarmy.put("premium", 8.0);
        this.cennikKarmy.put("specjalistyczna", 12.0);
    }

    public boolean zarejestrujNoweZwierze(int idZwierzecia, String imie, String rasa, int wiek, double waga) {
        if (sprawdzWolneBoksy() <= 0) {
            throw new IllegalStateException("Brak wolnych boksow");
        }
        for (Zwierze z : zwierzeta) {
            if (z.getIdZwierzecia() == idZwierzecia) {
                throw new IllegalArgumentException("Zwierze o tym ID juz istnieje");
            }
        }
        zwierzeta.add(new Zwierze(idZwierzecia, imie, rasa, wiek, waga));
        return true;
    }

    public int sprawdzWolneBoksy() {
        int zajeteBoksy = 0;
        for (Zwierze z : zwierzeta) {
            if (!z.isAdoptowany()) {
                zajeteBoksy++;
            }
        }
        return pojemnosc - zajeteBoksy;
    }

    public boolean zmienStatusNaAdoptowany(int idZwierzecia) {
        for (Zwierze z : zwierzeta) {
            if (z.getIdZwierzecia() == idZwierzecia) {
                if (z.isAdoptowany()) {
                    throw new IllegalStateException("Zwierze zostalo juz wczesniej adoptowane");
                }
                z.setAdoptowany(true);
                return true;
            }
        }
        throw new IllegalArgumentException("Nie znaleziono zwierzecia o podanym ID");
    }

    public double obliczKosztWyzywienia(double wagaZwierzecia, String typKarmy) {
        if (wagaZwierzecia <= 0) {
            throw new IllegalArgumentException("Waga musi byc dodatnia");
        }
        if (!cennikKarmy.containsKey(typKarmy)) {
            throw new IllegalArgumentException("Nieznany typ karmy");
        }

        double mnoznik = cennikKarmy.get(typKarmy);
        double kosztDzienny = wagaZwierzecia * 0.05 * mnoznik;
        return Math.round(kosztDzienny * 100.0) / 100.0;
    }

    public List<Zwierze> szukajPoRasieIWieku(String rasa, int maxWiek) {
        List<Zwierze> znalezione = new ArrayList<>();
        for (Zwierze z : zwierzeta) {
            if (z.getRasa().equals(rasa) && z.getWiek() <= maxWiek && !z.isAdoptowany()) {
                znalezione.add(z);
            }
        }
        return znalezione;
    }

    public List<Zwierze> getZwierzeta() {
        return zwierzeta;
    }

    public void zapiszDoPliku(String nazwaPliku) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nazwaPliku))) {
            for (Zwierze z : zwierzeta) {
                writer.println(z.getIdZwierzecia() + "," + z.getImie() + "," + z.getRasa() + "," +
                        z.getWiek() + "," + z.getWaga() + "," + z.isAdoptowany());
            }
        }
    }

    public void wczytajZPliku(String nazwaPliku) throws IOException {
        zwierzeta.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(nazwaPliku))) {
            String linia;
            while ((linia = reader.readLine()) != null) {
                String[] dane = linia.split(",");
                Zwierze z = new Zwierze(Integer.parseInt(dane[0]), dane[1], dane[2],
                        Integer.parseInt(dane[3]), Double.parseDouble(dane[4]));
                z.setAdoptowany(Boolean.parseBoolean(dane[5]));
                zwierzeta.add(z);
            }
        }
    }
}