import java.util.TreeSet;
import java.util.Random;

public class TreeSetSubSetAikavaativuus {
    public static void main(String[] args) {
        Random random = new Random();
        // Joukon alkioiden määrät, joita testataan
        int[] joukonAlkioidenMaara = {1, 2, 4, 8, 16, 32, 64, 128, 254, 508, 1016, 2042, 4084};
        int kierrokset = 100000; // Kuinka monta kertaa kokeilu toistetaan

        for (int n : joukonAlkioidenMaara) {
            TreeSet<Integer> treeSet = new TreeSet<>();
            long yhteensa = 0; // Yhteensä-laskuri keskiarvon laskemiseen
            long[] aikojenMediaani = new long[100];

            // Täytetään TreeSet satunnaisilla luvuilla
            for (int i = 0; i < n; i++) {
                treeSet.add(random.nextInt(n * 10));
            }

                // Suoritetaan kokeilut
                for (int i = 0; i < kierrokset; i++) {
                    //Satunnaisluvut, joita käytetään Subsetissä
                    int satunnainenLuku = random.nextInt(n * 10);
                    int satunnainenLuku2 = satunnainenLuku + random.nextInt(n * 10);

                    long aloita = System.nanoTime(); // Aloitetaan ajan mittaus
                    treeSet.subSet(satunnainenLuku, satunnainenLuku2); // Suoritetaan subSet-operaatio
                    long lopeta = System.nanoTime(); // Lopetetaan ajan mittaus

                    yhteensa += lopeta - aloita; // Lisätään operaation aika yhteensa-aikaan
                }

                long keskiarvo = yhteensa / kierrokset; // Lasketaan aikojen keskiarvo
            // Tulostetaan tulokset
            System.out.println("Joukon alkioiden määrä: " + n + ", keskimääräinen aika (ns): " + keskiarvo);
        }

    }
}
