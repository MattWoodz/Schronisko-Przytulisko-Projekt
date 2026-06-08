import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Schronisko schronisko = new Schronisko(15);
        Scanner scanner = new Scanner(System.in);
        String plikBazy = "baza_schroniska.csv";

        System.out.println("==================================================");
        System.out.println("   WITAJ W SYSTEMIE SCHRONISKA SafeHaven");
        System.out.println("==================================================");

        boolean programDziala = true;

        while (programDziala) {
            System.out.println("\nKIM JESTEŚ? (Wybierz interfejs użytkownika)");
            System.out.println("1. Pracownik Schroniska");
            System.out.println("2. Potencjalny Właściciel");
            System.out.println("3. Zakończ działanie systemu");
            System.out.print("Wybierz opcję: ");

            String rola = scanner.nextLine();

            if (rola.equals("3")) {
                System.out.println("Zamykanie systemu SafeHaven...");
                programDziala = false;
            }
            else if (rola.equals("2")) {
                System.out.println("\n--- MENU POTENCJALNEGO WŁAŚCICIELA ---");
                System.out.println("1. Wyszukaj zwierzę do adopcji (rasa/wiek)");
                System.out.println("2. Powrót");
                System.out.print("Wybierz opcję: ");

                String opcjaKlient = scanner.nextLine();

                if (opcjaKlient.equals("1")) {
                    try {
                        System.out.print("Jakiej rasy szukasz? ");
                        String szukanaRasa = scanner.nextLine();
                        System.out.print("Maksymalny wiek zwierzaka (w latach): ");
                        int maxWiek = Integer.parseInt(scanner.nextLine());

                        List<Zwierze> znalezione = schronisko.szukajPoRasieIWieku(szukanaRasa, maxWiek);

                        System.out.println("\n--- WYNIKI WYSZUKIWANIA ---");
                        for (Zwierze z : znalezione) {
                            System.out.println(z.toString());
                        }
                        if (znalezione.isEmpty()) {
                            System.out.println("Brak zwierząt spełniających kryteria.");
                        }
                    } catch (Exception e) {
                        System.out.println("-> BŁĄD: Sprawdź wprowadzane dane!");
                    }
                }
            }
            else if (rola.equals("1")) {
                boolean panelPracownika = true;
                while (panelPracownika) {
                    System.out.println("\n--- MENU PRACOWNIKA ---");
                    System.out.println("1. Dodaj nowe zwierzę");
                    System.out.println("2. Wyświetl stan schroniska");
                    System.out.println("3. Wyszukaj zwierzę do adopcji (rasa/wiek)");
                    System.out.println("4. Zmień status na: Adoptowany");
                    System.out.println("5. Oblicz koszt wyżywienia");
                    System.out.println("6. Zapisz dane do pliku CSV");
                    System.out.println("7. Wczytaj dane z pliku CSV");
                    System.out.println("8. Wyloguj (Powrót do wyboru ról)");
                    System.out.print("Wybierz opcję: ");

                    String opcjaPracownik = scanner.nextLine();

                    try {
                        switch (opcjaPracownik) {
                            case "1":
                                System.out.print("Podaj ID (liczba): ");
                                int id = Integer.parseInt(scanner.nextLine());
                                System.out.print("Podaj imię: ");
                                String imie = scanner.nextLine();
                                System.out.print("Podaj rasę: ");
                                String rasa = scanner.nextLine();
                                System.out.print("Podaj wiek (lata): ");
                                int wiek = Integer.parseInt(scanner.nextLine());
                                System.out.print("Podaj wagę (np. 12.5): ");
                                double waga = Double.parseDouble(scanner.nextLine());

                                schronisko.zarejestrujNoweZwierze(id, imie, rasa, wiek, waga);
                                System.out.println("-> SUKCES: Dodano zwierzę!");
                                break;

                            case "2":
                                List<Zwierze> wszystkie = schronisko.getZwierzeta();
                                System.out.println("\n--- STAN SCHRONISKA (Łącznie: " + wszystkie.size() + ") ---");
                                System.out.println("Wolne boksy w schronisku: " + schronisko.sprawdzWolneBoksy());
                                for (Zwierze z : wszystkie) {
                                    System.out.println(z.toString());
                                }
                                if (wszystkie.isEmpty()) System.out.println("Schronisko jest puste.");
                                break;

                            case "3":
                                System.out.print("Jakiej rasy szukasz? ");
                                String szukanaRasa = scanner.nextLine();
                                System.out.print("Maksymalny wiek zwierzaka (w latach): ");
                                int maxWiek = Integer.parseInt(scanner.nextLine());

                                List<Zwierze> znalezione = schronisko.szukajPoRasieIWieku(szukanaRasa, maxWiek);
                                System.out.println("\n--- WYNIKI WYSZUKIWANIA ---");
                                for (Zwierze z : znalezione) {
                                    System.out.println(z.toString());
                                }
                                if (znalezione.isEmpty()) System.out.println("Brak zwierząt spełniających kryteria.");
                                break;

                            case "4":
                                System.out.print("Podaj ID zwierzęcia do adopcji: ");
                                int idAdopcja = Integer.parseInt(scanner.nextLine());
                                schronisko.zmienStatusNaAdoptowany(idAdopcja);
                                System.out.println("-> SUKCES: Zwierzę zmieniło status na adoptowane!");
                                break;

                            case "5":
                                System.out.print("Podaj wagę zwierzęcia: ");
                                double wagaKarmy = Double.parseDouble(scanner.nextLine());
                                System.out.print("Podaj typ karmy (standard, premium, specjalistyczna): ");
                                String typKarmy = scanner.nextLine();

                                double koszt = schronisko.obliczKosztWyzywienia(wagaKarmy, typKarmy);
                                System.out.println("-> Dzienny koszt wyżywienia wyniesie: " + koszt + " PLN");
                                break;

                            case "6":
                                schronisko.zapiszDoPliku(plikBazy);
                                System.out.println("-> SUKCES: Dane zapisane do " + plikBazy);
                                break;

                            case "7":
                                schronisko.wczytajZPliku(plikBazy);
                                System.out.println("-> SUKCES: Dane wczytane z pliku!");
                                break;

                            case "8":
                                System.out.println("Wylogowywanie...");
                                panelPracownika = false;
                                break;

                            default:
                                System.out.println("-> BŁĄD: Wybierz opcję od 1 do 8!");
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("-> BŁĄD OPERACJI: Sprawdź wprowadzane dane!");
                    }
                }
            } else {
                System.out.println("-> Niepoprawny wybór. Wybierz 1, 2 lub 3.");
            }
        }
        scanner.close();
    }
}
