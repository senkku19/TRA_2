import java.util.*;

public class TRAII_23_t22_pohja {

    public static void main(String[] args) {

        // defaults
        int rahaSumma = 80;

        // rahasumma komentoriviltÃ¤
        if (args.length > 0)
            rahaSumma = Integer.parseInt(args[0]);

        ArrayList<Integer> Kolikot = new ArrayList<>();

        // kolikot komentoriviltÃ¤
        for (int i = 1; i < args.length; i++)
            Kolikot.add(Integer.valueOf(args[i]));

        if (Kolikot.size() == 0) {
            Kolikot.add(1);
            Kolikot.add(2);
            Kolikot.add(5);
            Kolikot.add(10);
            Kolikot.add(40);
            Kolikot.add(50);
        }

        System.out.println("Dynaaminen");
        LinkedList<Integer> kolikot = dynjako23(rahaSumma, Kolikot);

        System.out.println("Kolikoita tarvitaan " + kolikot.size() + " kpl");
        System.out.println("Kolikot: " + kolikot);

    }


    /**
     * Rahajako dynaamisen ohjelmoinnin keinoin
     *
     * @param rahaSumma kasattava rahamÃ¤Ã¤rÃ¤
     * @param Kolikot   kÃ¤ytÃ¶ssÃ¤ olevien kolikkojen arvot
     * @return tarvittavien kolikkojen lukumÃ¤Ã¤rÃ¤
     */
    static int dynjako(int rahaSumma, Collection<Integer> Kolikot, LinkedList<Integer> lista) {
        int[] kolikkoMaara = new int[rahaSumma + 1];
        kolikkoMaara[0] = 0;
        // haetaan ja talletetaan kaikki osaratkaisut
        for (int rahaSummaIter = 1; rahaSummaIter <= rahaSumma; rahaSummaIter++) {
            int min = rahaSummaIter;
            // kullakin kolikolla
            for (Integer kolikko : Kolikot) {
                if (kolikko <= rahaSummaIter) {
                    int kolikoita = kolikkoMaara[rahaSummaIter - kolikko] + 1;
                    if (kolikoita < min)
                        min = kolikoita;

                }
            }
            kolikkoMaara[rahaSummaIter] = min;
            System.out.println(kolikkoMaara[min]);
        }

        for (Integer kolikko: kolikkoMaara){

        }
        // vastaus alkuperÃ¤iseen tehtÃ¤vÃ¤Ã¤n
        System.out.println(kolikkoMaara[rahaSumma]);
        return kolikkoMaara[rahaSumma];
    }

    /**
     * Rahajako dynaamisen ohjelmoinnin keinoin
     *
     * @param rahaSumma kasattava rahamÃ¤Ã¤rÃ¤
     * @param Kolikot   kÃ¤ytÃ¶ssÃ¤ olevien kolikkojen arvot
     * @return lista palautettavista kolikoista
     */
    static LinkedList<Integer> dynjako23(int rahaSumma, Collection<Integer> Kolikot) {
        LinkedList<Integer> tulos = new LinkedList<>();
        int[] kolikkoMaara = new int[rahaSumma + 1];
        // Luodaan taulukko, joka pitää kirjaa valituista kolikoista.
        int[] rahat = new int[rahaSumma + 1];
        kolikkoMaara[0] = 0;

        for (int rahaSummaIter = 1; rahaSummaIter <= rahaSumma; rahaSummaIter++) {
            int min = rahaSummaIter;
            // Alustetaan valittu kolikko nollaksi.
            int valittuKolikko = 0;

            for (Integer kolikko : Kolikot) {
                if (kolikko <= rahaSummaIter) {
                    int kolikoita = kolikkoMaara[rahaSummaIter - kolikko] + 1;
                    if (kolikoita < min) {
                        min = kolikoita;
                        // Päivitetään valittu kolikko.
                        valittuKolikko = kolikko;
                    }
                }
            }
            kolikkoMaara[rahaSummaIter] = min;
            // Tallennetaan valittu kolikko taulukkoon rahat.
            rahat[rahaSummaIter] = valittuKolikko;
        }

        // Alustetaan laskurimuuttuja rahaSummaan.
        int laskuri = rahaSumma;
        //Käydään taulukko rahat takaperin läpi
        while (laskuri > 0) {
            //Valitaan kolikko rahat taulukosta
            int kolikko = rahat[laskuri];
            //Lisätään kolikko tulos listaan
            tulos.add(kolikko);
            //Vähennetää kolikko laskurista
            laskuri -= kolikko;
        }

        return tulos;
    }

}