import fi.uef.cs.tra.*;

import java.util.*;

public class TRAII_23_X5_moilanes3 implements TRAII_23_X5 {

    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     * n = solmujen määrä ja e = on kaarien määrä.
     *
     * Mielestäni algoritmini aikavaativuus on pahimmassa tapauksessa O(n+e), koska:
     * -Rivi 47 vie pahimmassa tapauksessa O(n)
     * -Rivi 54 vie pahimmassa tapauksessa O(e)
     * -Rivi 69-75
     *
     *
     *
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
        HashSet<Set<Vertex>> valiTulos = new HashSet<>();

        varita(verkko);

        etsitaanPolut(lahtoSolmu, maxPaino, valiTulos, new HashSet<>(), 0);

        for (Set<Vertex> apu: valiTulos){
            List<Vertex> valiLista = new LinkedList<>();
            valiLista.addAll(apu);

            tulos.add(valiLista);
        }





        return tulos;
    }

    public void etsitaanPolut(Vertex solmu,float maxPaino,HashSet<Set<Vertex>> tulos, Set<Vertex> apuListaKopio, float paino) {
        apuListaKopio.add(solmu);
        solmu.setColor(Graph.GRAY);


        //Jos listassa on enemmänkuin yksi solmu löysimme polun
        if (apuListaKopio.size() > 1) {
            //Jos polun kaarien paino on sama tai pienempi kuin maksimi paino
            if (paino <= maxPaino)
                //Lisätään lista tulos joukkoon
                tulos.add(apuListaKopio);
        }

//Käydään solmun kaikki kaaret läpi
        for(Edge e  : solmu.edges()) {
            //Alustetaan muuttuja, yhteispainon ja tutkittavan kaaren painon summalla
            float uusiPaino = paino+e.getWeight();
            //Jos kaaren päättymispistettä ei olla käyty, kyseessä on yksinkertainen polku
            if (e.getEndPoint().getColor() == Graph.WHITE) {
                //Käydään kyseinen solmu ja sen naapurit läpi rekursiivisesti
                etsitaanPolut(e.getEndPoint(), maxPaino, tulos, apuListaKopio, uusiPaino);
            }
            //Jos kaaren aloitussolmussa ei olla käyty, kyseessä on yksinkertainen polku
            if (e.getStartPoint().getColor() == Graph.WHITE) {
                //Käydään kyseinen solmu ja sen naapurit läpi rekursiivisest
                etsitaanPolut(e.getStartPoint(), maxPaino, tulos, apuListaKopio, uusiPaino);
            }

        }

        solmu.setColor(Graph.WHITE);
        apuListaKopio.remove(apuListaKopio.size()-1);


    }


    static void varita(Graph g) {
        for (Vertex v : g.vertices())
            v.setColor(Graph.WHITE);
    }



}