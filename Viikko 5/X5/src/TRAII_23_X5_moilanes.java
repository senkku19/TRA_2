import fi.uef.cs.tra.*;

import java.util.*;

public class TRAII_23_X5_moilanes implements TRAII_23_X5 {

    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     * n = solmujen määrä ja e = on kaarien määrä.
     *
     * Aikavaativuuden kanssa minulla oli hieman ongelmia. En tiedä osasinko arvioida sitä oikein. Uskoisin kuitenkin,
     * että algoritmin aikavaativuus olisi pahimmassa tapauksesssa O(2^e), koska pahimmassa tapauksessa algoritmi joutuu
     * käymään verkon eri osat useaan kertaan läpi.
     *
     *Algoritmi ei ole tehokas, mutta en kuitenkaan usko, että sen tehokkuutta voisi parantaa. Tavoitteena on
     * löytää kaikki yksinkertaiset polut, joten algoritmin tulisi käydä useaan kertaan samat kaaret läpi.
     *
     * Algoritmia tehdessäni kohtasin ongelman suuntamattoman kaarien läpikäynnissä. Aluksi loin kaarien läpikäynnin sijaan
     * silmukan, joka käy solmun naapurit läpi ja tarkistaa löytyykö naapuria jo listasta. Tajusin kuitenkin myöhemmin, että listan contains()- ja
     * solmujen getEdge() operaatio olivat muuta kuin vakioaikaisia ja pahensi algoritmin aikavaativuutta. Tämän taki muutin
     * algoritmin käymään solmun kaaret läpi. Muutettuani koodia, en saanut haluamiani tuloksia, kunnes tajusin, että
     * suuntaamattomassa verkossa kaarien aloitus- ja lopetussolmu ovat satunnaisia, joten löytääkseni ns. seuraavan solmun jouduin
     * tarkentamaan kumpi oli kaaren ns. aloitussolmu
     *
     * Uskon kuitenkin, että kokonaisuudessaan algoritmini antaa vähintään toivotun tuloksen. Algoritmi lähtee käymään verkkoa läpi rekursion avulla
     * lähtösolmusta lähtien. Aina, kun löytyy polku, joka toteuttaa pyydetyt ehdot, metodi lisää polun listaan, tekee polku-listasta kopion
     *  ja lisää kopio listan tulos-joukkoon.

     *
     *
     *
     **/
    /**
     * Kaikki erilaiset annetusta solmusta lahtoSolmu lÃ¤htevÃ¤t
     * korkeintaan maxPaino painoiset yksinkertaiset polut.
     * Polut palautetaan polkujen joukkona.
     * Polut palautetaan solmujen listana siten, ettÃ¤ polun
     * perÃ¤kkÃ¤isten solmujen vÃ¤lillÃ¤ on kaari syÃ¶teverkossa.
     * Polulla on vÃ¤hintÃ¤Ã¤n kaksi solmua (ja yksi kaari).
     * Polun paino on polun kaarten painojen summa.
     * Verkossa ei ole negatiivispainoisia kaaria.
     * Yksikertaisella polulla ei ole kehÃ¤Ã¤ (ts. siinÃ¤ ei ole mitÃ¤Ã¤n solmua kahdesti)
     * @param verkko syÃ¶teverkko
     * @param lahtoSolmu lÃ¤htÃ¶solmu
     * @param maxPaino polkujen maksimipaino
     * @return polkujen joukko
     */
    @Override
    public Set<List<Vertex>> kaikkiMaxPPolut(Graph verkko, Vertex lahtoSolmu, float maxPaino) {
        HashSet<List<Vertex>> tulos = new HashSet<>();

        //Väritetään verkko
        varita(verkko);

        // TODO: tÃ¤stÃ¤ alkaa polkujen haku, apumetodeja saa ja kannattaa kÃ¤yttÃ¤Ã¤

        //Etsitään rekursion avulla kaikki polut, jotka on painoltaan maksimissaan maxPainon verran
        etsitaanPolut(lahtoSolmu, maxPaino, tulos, new LinkedList<>(), 0);



        return tulos;
    }

    public void etsitaanPolut(Vertex solmu,float maxPaino,HashSet<List<Vertex>> tulos, LinkedList<Vertex> apuLista, float paino) {
        //Värjätään solmu harmaaksi eli siinä on käyty
        solmu.setColor(Graph.GRAY);

        //Lisätään solmu listaan
        apuLista.add(solmu);

        //Jos listassa on enemmänkuin yksi solmu löysimme polun
        if (apuLista.size() > 1) {
            //Tehdään listasta kopio
            List<Vertex> kopioApuListasta = new LinkedList<>(apuLista);
            //Ja lisätään se tulos-joukkoon
            tulos.add(kopioApuListasta);
        }

        //Käydään solmun kaikki kaaret läpi
        for(Edge e  : solmu.edges()) {
            //Alustetaan muuttuja, yhteispainon ja tutkittavan kaaren painon summalla
            float uusiPaino = paino+e.getWeight();
            //Jos polun kaarien paino on sama tai pienempi kuin maksimi paino
            if (uusiPaino <= maxPaino) {
                //Haetaan kaaren toisesta päästä solmu
                Vertex naapuri = e.getEndPoint(solmu);
                //Siirrytään tutkimaan, onko kaari osa yksinkertaista polkua
                //Jos kaaren päättymispisteessä ei olla vierailtu, kyseessä on yksinkertainen polku
                if (naapuri.getColor() == Graph.WHITE)
                    //Käydään kyseinen solmu ja sen naapurit läpi rekursiivisesti
                    etsitaanPolut(naapuri, maxPaino, tulos, apuLista, uusiPaino);
            }
        }

        //Värjätään solmu takaisin valkoiseksi
        solmu.setColor(Graph.WHITE);
        //Poistetaan käsitelty solmu listan lopusta.
        apuLista.remove(apuLista.size()-1);

    }


    static void varita(Graph g) {
        //Käydään kaikki verkon solmut läpi
        for (Vertex v : g.vertices())
            //Väritetään solmu valkoiseksi
            v.setColor(Graph.WHITE);
    }



}