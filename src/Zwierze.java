public class Zwierze {
    private int idZwierzecia;
    private String imie;
    private String rasa;
    private int wiek;
    private double waga;
    private boolean adoptowany;

    public Zwierze(int idZwierzecia, String imie, String rasa, int wiek, double waga) {
        this.idZwierzecia = idZwierzecia;
        this.imie = imie;
        this.rasa = rasa;
        this.wiek = wiek;
        this.waga = waga;
        this.adoptowany = false;
    }

    public int getIdZwierzecia() {
        return idZwierzecia;
    }

    public String getImie() {
        return imie;
    }

    public String getRasa() {
        return rasa;
    }

    public int getWiek() {
        return wiek;
    }

    public double getWaga() {
        return waga;
    }

    public boolean isAdoptowany() {
        return adoptowany;
    }

    public void setAdoptowany(boolean adoptowany) {
        this.adoptowany = adoptowany;
    }

    @Override
    public String toString() {
        String status = adoptowany ? "[ZADOPTOWANY]" : "[DO ADOPCJI]";
        return String.format("ID: %d | Imie: %s | Rasa: %s | Wiek: %d lat | Waga: %.2f kg %s",
                idZwierzecia, imie, rasa, wiek, waga, status);
    }
}