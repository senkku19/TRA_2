import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class PriorityQueueAikavaativuus {
    public static void main(String[] args) {
        Random random = new Random();
        //Tehdään listä, joka pitää sisällään prioriteettijonon alkioiden määrää
        int[] alkioideMaara = {1, 2, 4, 8, 16, 32, 64, 128, 254, 508, 1016, 2042, 4084};
        //Kuinka monta kierrosta käymme
        int kierrokset2 = 100;

        //Käydään alkioiden määrät läpi
        for (int n : alkioideMaara) {
            //Luodaan uusi prioriteettijono
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
            //Tyhjä taulukko johon säilötään eri etsintöjen keskiarvoja
            long[] aikojenMediaani = new long[10000];
            //Luodaan aloitus ja lopetus ajat
            long aloitus, lopetus;
            //Muuttuja johon tulee, kuinka kauan samalla syötteellä kestää yhteensä 100 kertaa aikaa
            long yhteensa = 0;
            //Kuinka monta kertaa toistamme kokeilun
            int kierrokset = 10000;
            //Laskuri jolla laskemme millä paikalla menemee aikojenMediaani-taulukossa
            int paikka = 0;

            //Täytetään prioriteettijono
            for (int i = 0; i < n; i++) {
                priorityQueue.add(random.nextInt(n * 10));
            }

            for (int i = 0; i < kierrokset; i++) {
                //Etsittävä alkio
                int etsittava = random.nextInt();

                //Etsitään smaa alkiota 100 kertaa
                for(int x = 0; x<kierrokset2; x++) {
                    //Aloitetaan aika
                    aloitus = System.nanoTime();
                    //ajetaan operaatio
                    priorityQueue.contains(etsittava);
                    //Lopetetaan aika
                    lopetus = System.nanoTime();
                    //Lisätään aika yhteensä-laskuriin
                    yhteensa += (lopetus - aloitus);
                }

                //Lisätään operaation keskiarvo aikojenMediaani taulukkoon
                aikojenMediaani[paikka]  = yhteensa/kierrokset2;
                //Mennään yksi eteenpäin paikassa
                paikka++;
                //Nollataan yhteensä laskuri
                yhteensa = 0;

            }
            //Järjestetään taulukko mediaania varten
            Arrays.sort(aikojenMediaani);

            //Palauteetaan keskiarvojen Mediaani
            System.out.println("Alkioiden Määrä: " + n + ", Operaatio suoriutui ajassa (ns): " + aikojenMediaani[aikojenMediaani.length/2]);
        }
    }
}
