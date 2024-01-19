
import com.sun.security.jgss.GSSUtil;

import java.util.*;

public class TRAII_23_X6_moilanes implements TRAII_23_X6 {

    /**
     * Itsearviointi tÃ¤hÃ¤n:
     * Aikavaativuus, kun n on jaoteltavat alkiot, m on laatikoiden määrä ja t on maxaika.
     *
     *Jos osasin arvioida oikein, algoritmin aikavaativuus on pahimmillaan O(n*m*t).
     *
     * Mielestäni algoritmi antaa hyviä tuloksia. En kuitenkaan usko, että se on tehokkaimmasta päästä.
     * Algoritmissä ajastin tuotti vaikeuksia suurien syötteiden kanssa. Suurien syötteiden käyttämä aika
     * venyi yli maxAjan. En keksinyt, miten saisin tämän korjattua, joten päädyin miinustamaan maxAikaa yhdellä sekunnilla.
     * Toivoisin myös, että olisin keksinyt tehokkaamman tavan hakea satunnaisia indeksejä, mutta tällä kertää tyydyin tähän vaihtoehtoon.
     *
     * Muuten olen tyytyväinen algoritmiini ja siihen, että jopa suurissa syötteissä tuloskaukalot heittävät vain yhdellä.
     *
     *
     *
     *
     *
     */

    /**
     * Jaottelee kokoelman C kokonaisluvut mahdollisimman vÃ¤hiin listoihin siten, ettÃ¤ kunkin listan alkioiden summa on
     * korkeintaan laatikonKoko.
     *
     * @param L            jaoteltavat alkiot
     * @param laatikonKoko kunkin tuloslistan maksimikapasiteetti
     * @param maxAika      suurin kÃ¤ytettÃ¤vissÃ¤ oleva aika (sekunteja)
     * @return tuloslistojen lista
     */
    @Override
    public List<List<Integer>> laatikkoJako(final ArrayList<Integer> L, int laatikonKoko, int maxAika) {
        // TODO
        // TÃ¤mÃ¤ on melko huonosti (mutta sÃ¤Ã¤nnÃ¶n mukaan) jakava esimerkki
        // jota on tarkoitus parantaa heuristiikalla ja satunnaisuudella
        // apumetodeja saa kÃ¤yttÃ¤Ã¤, tÃ¤mÃ¤n esimerkin rakennetta ei tarvitse sÃ¤ilyttÃ¤Ã¤
        List<List<Integer>> tulos = new ArrayList<>();

        if (L.isEmpty())    // tyhjÃ¤llÃ¤ syÃ¶tteellÃ¤ tyhjÃ¤ tulos
            return tulos;

        //Luodaan kopio listasta
        ArrayList<Integer> LKopio = new ArrayList<>(L);
        //Järjestetään lista laskevaan järjestykseen
        LKopio.sort(Collections.reverseOrder());


        //Jos syötelista on alle 10 000
        if (L.size() < 10000) {
            //Siirrytään apumetodiin annetulla maxAjalla
            tulos = laatikkoJako2(LKopio, laatikonKoko, maxAika);
        } else {
            //Muuten vähennetään maxajasta 1 sekuntti
            tulos = laatikkoJako2(LKopio, laatikonKoko, maxAika-1);
        }


        //Palautetaan tulos
        return tulos;
    }

    static Integer pystyikoSijoittamaan(List<Integer> apuLista, int luku, int nykyinenIndeksi, int laatikonKoko){
        //Luodaan satunnais generaattori, jonka siemen riippu nykyisestä ajasta
        Random rnd = new Random(System.currentTimeMillis());
        //Luodaan joukko, johon lisäämme aina käydyt indeksit
        HashSet<Integer> kaydytIndeksit = new HashSet<>();

        //Niin kauan, kun apujoukko ei ole saman kokoinen kuin apuLista
        while (kaydytIndeksit.size() != apuLista.size()){
            //Etsitään uusi indeksi satunnaisesti
            nykyinenIndeksi = rnd.nextInt(apuLista.size());
            //Lasketaan indeksin paikalla olevan arvon ja luvun yhteispaino
            int yhteisPaino = apuLista.get(nykyinenIndeksi) + luku;
            //Jos indeksi ei ole vielä apuJoukossa
            if(!kaydytIndeksit.contains(nykyinenIndeksi)) {
                //Tarkistetaan onko yhteispaino sama tai pienempi kuin laatikon maximi paino
                if (yhteisPaino <= laatikonKoko) {
                    //Palautetaan indeksi
                    return nykyinenIndeksi;
                } else {
                    //Muuten lisätään indeksi apujoukkoon
                    kaydytIndeksit.add(nykyinenIndeksi);
                }
            }
        }

        //Jos emme löytäneet paikka johon luku mahtuu palautetaan -1
        return -1;
    }

    static Integer bestFit(List<Integer> apuLista, int luku, int nykyinenIndeksi, int laatikonKoko){
       /* //Luodaan satunnais generaattori, jonka siemen riippu nykyisestä ajasta
        Random rnd = new Random(System.currentTimeMillis());
        //Luodaan joukko, johon lisäämme aina käydyt indeksit
        HashSet<Integer> kaydytIndeksit = new HashSet<>();*/

        for (int i = 0; i < apuLista.size(); i++){
            if (apuLista.get(i) + luku == laatikonKoko)
                return i;

        }

       /* //Niin kauan, kun apujoukko ei ole saman kokoinen kuin apuLista
        while (kaydytIndeksit.size() != apuLista.size()){
            //Etsitään uusi indeksi satunnaisesti
            nykyinenIndeksi = rnd.nextInt(apuLista.size());
            //Lasketaan indeksin paikalla olevan arvon ja luvun yhteispaino
            int yhteisPaino = apuLista.get(nykyinenIndeksi) + luku;
            //Jos indeksi ei ole vielä apuJoukossa
            if(!kaydytIndeksit.contains(nykyinenIndeksi)) {
                //Tarkistetaan onko yhteispaino sama tai pienempi kuin laatikon maximi paino
                if (yhteisPaino <= laatikonKoko) {
                    //Palautetaan indeksi
                    return nykyinenIndeksi;
                } else {
                    //Muuten lisätään indeksi apujoukkoon
                    kaydytIndeksit.add(nykyinenIndeksi);
                }
            }
        }*/

        //Jos emme löytäneet paikka johon luku mahtuu palautetaan -1
        return -1;
    }


    public List<List<Integer>> laatikkoJako2(final ArrayList<Integer> L2, int laatikonKoko, int maxAika) {

        //Luodaan aputulos lista
        List<List<Integer>> apuTulos = new ArrayList<>();

        //Luodaan apulista, jossa säilytämme jokaisen laatikon painoa
        List<Integer> apuLista = new ArrayList<>();
        //Muutetaan maxAika nanosekunneiksi
        long alku = System.nanoTime();
        long loppu =alku + (maxAika)*1000L*1000*1000;

        // kÃ¤ydÃ¤Ã¤n syÃ¶tteen luvut lÃ¤pi
        //Niin kauan, kun aika ei ole vielä loppu
        while(System.nanoTime() < loppu) {
            //Luodaan uusi tulos lista
            List<List<Integer>> tulos = new ArrayList<>();

            //Käydään läpi listan luvut
            for (int luku : L2) {

                if (luku > laatikonKoko || luku < 1) // tÃ¤mÃ¤ olisi virhe syÃ¶tteessÃ¤, ei pitÃ¤isi esiintyÃ¤
                    throw new RuntimeException("Liian iso/pieni alkio: " + luku + " (max = " + laatikonKoko + ")");

                //Alustetaan indeksi -1
                int indeksi = -1;


                //jos apulista ei ole tyhjä
                if (!apuLista.isEmpty()) {
                    /*indeksi = bestFit(apuLista, luku, 0, laatikonKoko);
                    //Kutsutaan apumetodia
                    if (indeksi == -1)*/
                    indeksi = pystyikoSijoittamaan(apuLista, luku, 0,  laatikonKoko);
                }

                //Jos indeksi on -1 apuLista on tyhjä tai emme löytäneet sopivaa paikkaa
                if (indeksi == -1) {
                    //Luodaan uusi kaukalo
                    List<Integer> avoinLaatikko = new LinkedList<>();
                    //Lisätään kaukalo tuloksiin
                    tulos.add(avoinLaatikko);
                    //Lisätään luku avoinlaatikkolistalle
                    avoinLaatikko.add(luku);
                    //Lisätään luku myös apulistalle
                    apuLista.add(luku);
                } else {
                    //Haetaan tulos listalta laatikko ja lisätään luku siihen
                    tulos.get(indeksi).add(luku);
                    //Haetaan listalta laatikon paino ja lisätään luku yhteispainoon
                    apuLista.set(indeksi, apuLista.get(indeksi) + luku);
                }

            }

            //Jos tulos ei ole tyhjä
            if (!tulos.isEmpty()) {
                //Jos aputulos on tyhjä tai tuloksessa on vähemmän laatikoita kuin aputuloksesta
                if (apuTulos.isEmpty() || tulos.size() < apuTulos.size()) {
                    //Alustetaan aputulos tuloksella
                    apuTulos = new ArrayList<>(tulos);
                }
            }

            //Tyhjennetään apulista
            apuLista.clear();

        }

        return apuTulos;
    }


}