import fi.uef.cs.tra.DiGraph;
import fi.uef.cs.tra.Edge;
import fi.uef.cs.tra.Vertex;

import java.util.*;


public class TRAII_23_X4_moilanes implements TRAII_23_X4 {

    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     *
     * Aikavaativuus, kun n = solmujen määrä ja e = kaarten määrä.
     *
     * Mielestäni algoritmini aikavaativuus on pahimillaan O(n+e), koska algoritmi käy läpi kaikki verkon solmut ja kaaret yksitellen
     * hyödyntäen syvyyssuuntaista läpikäyntiä.
     *
     *
     * Toteutin algoritmin mielestäni hyvin. Algoritmi on tehokas ja toteuttaa tehtävänannon onnistuneesti. En osaa sanoa
     * pystyisikö algoritmiä toteuttamaan paremmin tai ainakaan mieleeni ei tule parempaa tapaa toteuttaa sitä.
     * Algoritmini ottaa hyvin huomioon monet eri tilanteet esim. Kun solmua ei ole vielä käyty läpi, mutta sillä
     * on kaksi vanhempaa (eli ei ole osa puuta). Kyseinen tilanne tuotti minulle alussa ongelmia, kunnes keksin voivani
     * hyödyntää HashMappia, johon etsin valmiiksi, kuinka monta ns. vanhempaa solmulla on. HashMap on mielestäni tehokas
     * ratkaisu ongelmalle, koska sen operaatiot get, put ja containsKey() ovat ymmärtääkseni vakioaikaisia. Kyseisiä
     * operaatioita hyödyntämällä sain ratkaistua ongelman ja toteutettua tehokkaan algoritmin.
     *
     *
     **/

    /**
     * Palauttaa joukkona kaikki ne suunnatun verkon solmut joihin ei johda yhtÃ¤Ã¤n kaarta.
     *
     * @param G syÃ¶teverkko
     * @return juurisolmujen joukko
     */
    @Override
    public Set<Vertex> juuriSolmut(DiGraph G) {
        Set<Vertex> tulos = new HashSet<>();
        //Luodaan apujoukko, johon lisäämme kaikki solmut joilla on vanhempi.
        Set<Vertex> apuJoukko = new HashSet<>();

        // TODO, apumetodeja saa kÃ¤yttÃ¤Ã¤


        //Lisätään kaikki solmut tulos joukkoon
        for (Vertex v : G.vertices()) {
            tulos.add(v);
        }

        //Käydään läpi verkon kaaret
        for (Edge e : G.edges()) {
            //Lisätään aina solmu, johon kaari päättyy
            apuJoukko.add(e.getEndPoint());
        }

        //Poistetaan tulos joukosta kaikki solmut, joilla on vanhemmat
        tulos.removeAll(apuJoukko);

        //Palautetaan potenttiaaliset juuret
        return tulos;
    }

    /**
     * Palauttaa moniko suunnatun verkon G komponentti on puu  (eli
     * sellainen komponentti jossa ei ole paluu-, ristikkÃ¤is- tai etenemiskaaria.
     *
     * @param G syÃ¶teverkko
     * @return ehjien puiden lukumÃ¤Ã¤rÃ¤
     */
    @Override
    public int puidenLukumaara(DiGraph G) {

        Set<Vertex> potentiaalisetPuunJuuret = juuriSolmut(G);
        // juurisolmujen hakua ei ole pakko kÃ¤yttÃ¤Ã¤, mutta se on hyvÃ¤ lÃ¤htÃ¶kohta
        // juurisolmujen haku on kuitenkin pakollinen osa tehtÃ¤vÃ¤Ã¤

        // TODO tÃ¤hÃ¤n sitten koodia jossa selvitetÃ¤Ã¤n mitkÃ¤ komponentit
        // TODO oikeasti ovat puita, apumetodeja saa kÃ¤yttÃ¤Ã¤

        //Luodaan apuKuvaus, jolla voimme pitää kirjaa, kuinka monta paluukaarta kullakin solmulla on
        HashMap<Vertex, Integer> apuKuvaus = new HashMap<>();
        //Laskuri, jolla pidämme kirjaa, kuinka monta puuta olemme löytäneet
        int puidenMaara = 0;

        //väritetään kaikki solmut valkoisiksi
        varita(G);

        //Käydään läpi kaaret
        for (Edge e : G.edges()){
            //Haetaan solmu, mihin kaari päättyy
            Vertex v = e.getEndPoint();
            //Jos solmu on jo kuvauksessa
            if (apuKuvaus.containsKey(v)) {
                //Lisätään solmulle yksi vanhempi lisää
                int vahempienMaara = apuKuvaus.get(v) + 1;
                //Päivitetään solmun vanhempien määrä
                apuKuvaus.put(v, vahempienMaara);
            }
            //Muuten lisätään solmu kuvaukseen
            else
                apuKuvaus.put(v, 1);

        }



        // TODO apumetodeja saa kÃ¤yttÃ¤Ã¤
        //Käydään läpi mahdolliset juurisolmut
        for (Vertex v : potentiaalisetPuunJuuret) {
            //Jos solmua ei ole vielä käyty läpi
            if (v.getColor() == DiGraph.WHITE)
                //Tarkistetaan, onko juurisolmu osa puuta
                if (onkoPuu(v, apuKuvaus)) {
                    //Jos on kasvatetaan laskuria yhdellä
                    puidenMaara++;
                }
        }


        return puidenMaara; // TODO: tÃ¤hÃ¤n oikea palautusarvo
    }

    //Apumetodi, jonka avulla voimme värjätä verkon solmut valkoisiksi
    public static void varita(DiGraph G) {
        for (Vertex v : G.vertices())
            v.setColor(DiGraph.WHITE);
    }

    //Apumetodi
    public static boolean onkoPuu(Vertex nykyinen, HashMap<Vertex, Integer> apuKuvaus) {
        //Muutetaan solmun väri harmaaksi, eli solmu on käyty läpi
        nykyinen.setColor(DiGraph.GRAY);

        //Käydään läpi solmun naapurit
        for (Vertex naapuri : nykyinen.neighbors()) {
            //jos olemme palanneet solmuun, jossa on jo vierailtu
            if (naapuri.getColor() == DiGraph.GRAY)
                //kyseessä ei ole puu
                return false;
            //Jos solmu on valkoinen, siinä ei olla vierailtu
            else if (naapuri.getColor() == DiGraph.WHITE) {
                //jos solmun naapurilla on enemmän kuin yksi vanhempi
                if (apuKuvaus.get(naapuri) > 1)
                    //kyseessä ei ole puu
                    return false;
                //Tarkistetaan solmun lapset
                if (!onkoPuu(naapuri, apuKuvaus))
                    //kyseessä ei ole puu
                    return false;
            }
        }

        //Muuten kyseessä on puu
        return true;
    }
}
