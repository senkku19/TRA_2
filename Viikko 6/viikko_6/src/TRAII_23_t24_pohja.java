import java.util.Arrays;
import java.util.Comparator;

public class TRAII_23_t24_pohja {

    public static void main(String[] args) {

        // esimerkkiteksti josta muodostetaan loppuosataulukko
        String teksti = "abcccaabba";

        System.out.println("Merkkijono:" + teksti);

        // muodostetaan ja tulostetaan
        Integer[] lot = luoLoppuOsaTaulukko(teksti);
        System.out.println("Loppuosataulukko:");
        tulostaLoppuosaTaulukko(lot, teksti);

        System.out.println("\nEtsitÃ¤Ã¤n:");
        // kokeillaan paria etsimistÃ¤
        System.out.println("aaa: " + etsiLoppuosaTaulukosta("aaa", teksti, lot));    // ei lÃ¶ydy
        System.out.println("abc: " + etsiLoppuosaTaulukosta("abc", teksti, lot));   // lÃ¶ytyy alusta
        System.out.println("abba: " + etsiLoppuosaTaulukosta("abba", teksti, lot)); // lÃ¶ytyy lopusta
        System.out.println("ccc: " + etsiLoppuosaTaulukosta("ccc", teksti, lot)); // lÃ¶ytyy keskeltÃ¤
        System.out.println("abca: " + etsiLoppuosaTaulukosta("abca", teksti, lot)); // ei lÃ¶ydy
        System.out.println("ddd: " + etsiLoppuosaTaulukosta("ddd", teksti, lot)); // ei lÃ¶ydy

    }


    /**
     * Etsi avaimen esiintymisindeksi tekstistÃ¤ kÃ¤yttÃ¤en loppuosataulukkoa
     * @param avain etsittÃ¤vÃ¤ avain
     * @param teksti alkuperÃ¤inen teksti
     * @param loppuosaTaulukko  loppuosataulukko
     * @return avaimen esiintymiskohta, tai -1 jollei avainta lÃ¶ydy tekstistÃ¤
     */
    static int etsiLoppuosaTaulukosta(String avain, String teksti, Integer[] loppuosaTaulukko) {
        int alku = 0;
        int loppu = loppuosaTaulukko.length - 1;

        //Niin kauan, kun alku on pienempi kuin loppu
        while (alku <= loppu) {
            //Alustetaan keskiosa muuttuja loppuosataulukon puoli välillä
            int keskiosa = (alku + loppu) / 2;
            //Alustetaaan indeksi muuttuja loppuosan puolivälillä
            int indeksi = loppuosaTaulukko[keskiosa];
            //Jos teksti-indeksi on suurempi kuin halutun avaimen pituus
            if (teksti.length() - indeksi >= avain.length()) {
                //Luodaaan muuttuja aputeksti, joka alkaa indeksin määrämästä paikasta ja loppuu, kun se on avaimen pituinen
                String apuTeksti = teksti.substring(indeksi, indeksi + avain.length());

                //Jos aputeksti on sama kuin avain
                if (apuTeksti.equals(avain)) {
                    //palautetaan indeksi
                    return indeksi;
                }
            }
            //Jos indeksin määrämän paikan kirjain on suurempi kuin avain merkkijonon ensimmäinen kirjain
            if (teksti.charAt(indeksi) > avain.charAt(0)) {
                //Siirretään loppu osa puoliväliin - 1
                loppu = keskiosa - 1;
            } else
                //Muuten siirretään alku puoliväliin + 1
                alku = keskiosa + 1;
        }

        //Muuten palautetaan -1
        return -1;
    }

    /**
     * Tulostaa loppuosataulukon ja alkuperÃ¤isen merkkijonon loppuosat
     * @param lot loppuosataulukko
     * @param teksti alkuperÃ¤inen teksti
     */
    static void tulostaLoppuosaTaulukko(Integer[] lot, String teksti) {
        for (int i = 0; i < teksti.length(); i++) {
            System.out.format("%3d: %3d %s%n", i,
                    lot[i], teksti.substring(lot[i]));
        }
    }

    /**
     * Luo loppuosataulukon annetusta tekstistÃ¤ (helpoimmalla tavalla).
     * Tehokkaampiakin tapoja on olemassa.
     * @param teksti merkkijono josta taulukko muodostetaan
     * @return tekstin loppuosataulukko
     */
    static Integer[] luoLoppuOsaTaulukko(String teksti) {
        int n = teksti.length();
        Integer[] loppuosataulukko = new Integer[n];
        for (int i = 0; i < n; i++)
            loppuosataulukko[i] = i;

        Arrays.sort(loppuosataulukko, new LoppuosaVertailija(loppuosataulukko, teksti));
        return loppuosataulukko;
    }


    /**
     * Loppuosataulukoiden indeksien vertailija jÃ¤rjestÃ¤mistÃ¤ varten.
     * TÃ¤mÃ¤ jÃ¤rjestÃ¤Ã¤ indeksti 0..n-1 sisÃ¤ltÃ¤vÃ¤n loppuosataulukon (Integer[]) merkkijonon
     * loppuosien mukaiseen aakkosjÃ¤rjestykseen.
     */
    static class LoppuosaVertailija implements Comparator<Integer> {

        Integer[] loppuosataulukko; // jÃ¤rjestettÃ¤vÃ¤ loppuosataulukko
        String teksti; // alkuperÃ¤inen merkkijono

        public LoppuosaVertailija(Integer[] loppuosataulukko, String teksti) {
            this.loppuosataulukko = loppuosataulukko;
            this.teksti = teksti;
        }

        /**
         *
         * Vertailee indekseja ind1 ja ind2 sen mukaan minkÃ¤lainen merkkijono niiden kohdilta lÃ¶ytyy.
         *
         * @param ind1 the first object to be compared.
         * @param ind2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than
         * the second.
         * @throws NullPointerException if an argument is null and this comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from being compared by this comparator.
         */
        @Override
        public int compare(Integer ind1, Integer ind2) {
            return teksti.substring(ind1).compareTo(teksti.substring(ind2));
        }
    }

}