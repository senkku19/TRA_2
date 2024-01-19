import java.util.*;

public class TRAII_23_X2_moilanes implements TRAII_23_X2 {
    /**
     * ITSEARVIOINTI TĆ„HĆ„N:
     * Miten minimoin virhelähteet:
     * Testasin usealla eri satunnaisella syötteellä, kuinka nopeasti operaatio toimii. Varmistin myös,
     * että satunnaisissa syötteissä oli avain-arvoja joita löytyy annetusta kuvauksesta. Sen lisäksi, että
     * toistin usealla eri arvolla operaation tehokkuutta, testasin useasti kuinka kauan samalla syötteellä
     * kesti toteuttaa operaatio.
     *
     * Tietokoneellani myös sain tuloksia, jotka viittaavat, että operaation aikavaativuus olisi O(logn), joka pitää paikkansa, kun
     * kyseessä on TreeMap.
     *
     * En kuitenkaan ole varma, sainko tehtyä metodin, joka mittaa operaation aikaa tarkasti:
     *
     * Satunnainen syöte varmisti sen, että sain mahdollisimman monipuolisia tuloksia operaatiosta, kuitenkin satunnaisen syötteen
     * ongelmana on, että emme voi tietää löytyykö syötettä ollenkaan, onko se heti alussa jne. Joten tulokset voivat olla
     * joko parhaimmasta tai pahimmasta päästä eli ne voivat heitellä.
     *
     * En tiedä, onko hyvä tapa ottaa keskiarvo saman syötteen ajoista ja lisätä se taulukkoon, josta lopullinen aika on
     * taulukon mediaani. Tiedän, että mediaani on parempi, koska se suodattaa pois mahdollsiset mittavirheet. Alunperin yritin saada metodini toimimaan pelkällä
     * mediaanilla, mutta tulokset eivät olleet tarpeeksi tarkkoja ja jouduin tyytymään tähän ratkaisuun.
     *
     * Jäin myös miettimään tapahtuuko metodissa liika ja oliko liikaa luoda satunnais arvoille taulukko. En kuitenkaan keksinyt muuta tapaa saada
     * satunnaisia arvoja, joista ainakin muutama löytyisi tutkittavasta kuvauksesta. Ehkäpä parempi tapa olisi ollut käyttää suuniteltua
     * syötettä.
     *
     * Toinen asia, jota jäin miettimään on, että sainko testattua operaatiota tarpeeksi useasti. Yritin testata operaatiota useammin, mutta
     * tietokoneeni ei jaksanut pyörittää metodia tarpeeksi nopeasti.
     *
     *
     **/
    /**
     * Mittaa annetun kuvauksen containsKey -operaation aikavaativuuden nanosekunteina.
     * Mittaa ns. normaalin onnistuneen suorituksen. Ei siis minimiĆ¤ tai maksimia.
     * Ei muuta kuvausta (lisĆ¤Ć¤ tai poista alkioita).
     *
     * @param M testattava kuvaus
     * @return containsKey operaation normikesto nanosekunteina
     */

    @Override
    public long containsKeyNopeus(Map<Double, Double> M) {

        // TODO

        Random rnd = new Random();
        //Luodaan tyhjä lista satunnaisille arvoille
        ArrayList avaimenPaikka = new ArrayList<>();
        //Luodaan taulukko joka säilöö sisällään kuvauksen kaikki avaimet
        Object[] avaimetM = M.keySet().toArray();

        //Satunnainen paikka, josta avaimen arvo otetaan taulukosta.
        int satunnainenAvain = (int) (Math.random()*avaimetM.length);
        //Satunnainen indeksi 0-19999 välillä
        int satunnainenIndeksi = (int) (Math.random()*19999);
        //Kuinka useasti silmukka pyörii
        int monesti = 20000;
        //Täytetään avaimenPaikka-lista satunnaisilla arvoilla
        for (int i = 0; i <monesti; i++){
            //Jos i ja satunnainen indeksi on sama
            if (i == satunnainenIndeksi) {
                //Lisätään satunnainen avainarvo avaimetPaikka-listaan
                avaimenPaikka.add(avaimetM[satunnainenAvain]);
                //Etsitään uusi satunnainen indeksi
                satunnainenAvain = (int) (Math.random() * avaimetM.length);
            }
            //Muuten lisätään satunnainen arvo avaimenPaikkaindeksiin.
            else
                avaimenPaikka.add(rnd.nextDouble());
            //Arvotaan uusi indeksi 0-i:n väliltä
            satunnainenIndeksi = (int) (Math.random()*(19999-i)+i);
        }

        //Luodaan tyhjä yhteensä arvo, johon lisätään kuinka kauan yhden operaation toistoissa on kestänyt yhteensä
        long yhteensa = 0;
        //Luodaan tyhjä taulukko, johon sijoitamme yhden operaation toistojen keskiarvon.
        long[] aikojenMediaani = new long[20000];
        //paikka mihin keskiarvo lisätään
        int paikka = 0;
        //Kuinka monesti silmukka pyörii
        int operaatiot = 500;
        //Testataan jokainen avain erikseen: Eli testataan 20000 eri syötteellä operaatiota
            for (Object x : avaimenPaikka) {
                //nollataan aina laskuri, kun ulompi silmukka alkaa alusta
                yhteensa=0;
                long aloita = System.nanoTime();
                //Testataan samaa syötettä 500 kertaa
                for(int i = 0; i <operaatiot; i++) {
                    //aloitetaan aika
                    //Ajetaan operaatio
                    M.containsKey(x);
                    //Lopetetaan aika
                    //lisätään aika kokonais aikaan.
                }
                long loppu = System.nanoTime();
                yhteensa += (loppu - aloita);
                //Lisätään taulukkoon keskiarvo, kuinka kauan 500 toistolla kesti etsiä alkio keskimäärin
               aikojenMediaani[paikka] = yhteensa/operaatiot;
                //Kasvatetaan paikka laskuria
                paikka ++;
            }
            //Järjestetään taulukko mediaania varten
            Arrays.sort(aikojenMediaani);
            //Haetaan kuinka pitkä se on
            int n = aikojenMediaani.length;

            //Palautetaan taulukon mediaani
            return aikojenMediaani[n/2];
    }
}