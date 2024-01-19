import java.util.*;

public class TRAII_23_X3_moilanes implements TRAII_23_X3 {

    /**
     * Itsearviointi:

     *
     * Minimoin virhelähteitä:
     * -Lisäämällä 3 sekunnin lämmitys vaiheen, ennen kuin algoritmi toteuttaa koodin.
     * -Suorittamalla mittauksen useasti eli tarkalleen noin 150 kertaa per alkiomäärä.
     *
     * Mittaukseeni jäi kuitenkin asioita, joista olen hieman epävarma:
     * -Puhuimme viime harjoituksessa tuloksen tulemisesta välimuistista. Koska alkiot, jotka lisätään
     * jonoon ovat vakiota (ovat samat joka kerta), mietin, että tulevatko tulokset tässä tapauksessa välimuistista. Jos tulee
     * tämä vaikuttaisi tuloksiin negatiivisesti.
     * -Koska keskiarvo on herkkä poikkeuksille, jäin miettimään oliko se hyvä tapa mitata metodin tehokkuutta vai olisiko esim. Mediaani ollut
     * tässä tapauksessa parempi vaihtoehto.
     *
     * Kuitenkin mielestäni olen oikeilla jäljillä mittauksen suhteen ja pidän algoritmiani vähintään suuntaa
     * antavana mittausten suhteen. Algoritmille voisi ja ehkäpä pitäisi
     * tehdä parannuksia, mutta valitettavasti en itse keksinyt, miten voisin saada mittauksista vielä tarkempia.
     * Ehkäpä käyttämällä mediaania keskiarvon sijaan, vaihtamalla vakio alkiot satunnaisiin jne.
     *
     * Testaa jonon Q toiminnan nopeutta.
     * Mittaa ajan alkioiden mÃ¤Ã¤rÃ¤lle n = { min, min*2, min*4, min*8, ... <=max}.
     *
     * Kullekin alkiomÃ¤Ã¤rÃ¤lle n mitataan aika joka kuluu seuraavaan operaatiosarjaan:
     * 1. lisÃ¤tÃ¤Ã¤n jonoon n alkiota
     * 2. n kertaa vuorotellen lisÃ¤tÃ¤Ã¤n jonoon yksi alkio ja otetaan yksi alkio pois
     * 3. poistetaan jonosta n alkiota
     *
     * Viimeinen mitattava alkioiden mÃ¤Ã¤rÃ¤ on siis suurin min*2^k joka on pienempi tai yhtÃ¤ suuri kuin max.
     *
     * Tuloksena palautetaan kuvaus jossa on avaimena kukin testattu syÃ¶tteen koko ja kuvana kyseisen
     * syÃ¶tteen koon mittaustulos nanosekunteina.
     *
     * @param Q testattava jono
     * @param min lisÃ¤ttÃ¤vien/poistettavien alkioiden minimimÃ¤Ã¤rÃ¤
     * @param max lisÃ¤ttÃ¤vien/poistettavien alkioiden mÃ¤Ã¤rÃ¤n ylÃ¤raja
     * @return jÃ¤rjestetty kuvaus jossa on kaikki testitulokset
     */

    @Override
    public SortedMap<Integer, Long> jononNopeus(Queue<Integer> Q, int min, int max) {
        SortedMap<Integer, Long> tulos = new TreeMap<>();

        // TODO tÃ¤hÃ¤n ja muuallekin saa tehdÃ¤ muutoksia kunhan ei muuta otsikkoa
        // apumetodeja saa kÃ¤yttÃ¤Ã¤
        //Kuinka monesti toistetaan metodi
        int operaatioita = 150;

        //Lämmitetään virtuaalikone
        lammita(Q, min, max);

        // tÃ¤mÃ¤ toisto tÃ¤ssÃ¤ esimerkkinÃ¤, mutta jokin tÃ¤llainen tarvitaan
        for (int alkioMaara = min; alkioMaara <= max; alkioMaara *= 2) {
            // TODO tÃ¤hÃ¤n ainakin pitÃ¤Ã¤ tehdÃ¤ jotain
            //Aloitetaan ajan otto
            long aloitus = System.nanoTime();
            //Toistetaan n alkion kaikki operaatiot 150 kertaa
            for (int x = 0; x<operaatioita; x++) {
                //Lisätään n alkiota jonoon
                for (int i = 0; i < alkioMaara; i++) {
                    Q.add(i);
                }

                //Poistetaan n alkiota
                for (int i = 0; i < alkioMaara; i++) {
                    //Haetaan ensimmäinen alkio
                    Integer ensimmainen = Q.element();
                    //Poistetaan se
                    Q.remove();
                    //Ja lisätään alkio heti takaisin
                    Q.add(ensimmainen);
                }

                //Poistetaan kaikki alkiot jonosta
                while (!Q.isEmpty()) {
                    Q.remove();
                }

            }
            //Lopetetaan ajan otto
            long lopeta = System.nanoTime();
            //Lasketaan kuinka kauan operaatiossa kesti yhteensä
            long yhteensa = lopeta-aloitus;
            // tulosten tallettaminen
            //Lisätään tulokseen alkiomäärä ja 150 operaation keskiarvo
            tulos.put(alkioMaara, yhteensa/operaatioita);

        }


        return tulos;
    }

    static void lammita(Queue<Integer> Q, int min, int max){
        //Pyöritetään lämmitystä 3 sekunnin ajan
        long loppu = System.nanoTime() + 3*1000L*1000*1000;
        //Jos aika ei ole loppu
        while (System.nanoTime()<loppu) {
            //Käydään n alkiota läpi niin, että joka kierroksella n kaksinkertaistuu
            for (int alkioMaara = min; alkioMaara <= max; alkioMaara *= 2) {
                //Toistetaan operaatiot n alkiolla 150 kertaa
                for (int x = 0; x < 150; x++) {
                    //Lisätään n alkiota jonoon
                    for (int i = 0; i < alkioMaara; i++) {
                        Q.add(i);
                    }

                    for (int i = 0; i < alkioMaara; i++) {
                        //Alustetaan ensimmainen jonon ensimmäisella alkiolla
                        Integer ensimmainen = Q.element();
                        //Poistetaan ensimmäinen alkio
                        Q.remove();
                        //Ja lisätään se heti takaisin
                        Q.add(ensimmainen);
                    }

                    //Poistetaan kaikki alkiot jonosta
                    while (!Q.isEmpty()) {
                        Q.remove();
                    }

                }
            }
        }

    }
}