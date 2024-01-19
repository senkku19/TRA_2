import fi.uef.cs.tra.*;

import java.util.*;

public class TRAII_23_t11_12_pohja {

    public static void main(String[] args) {

        // defaults
        int vertices = 5;
        int edges = 7;

        if (args.length > 0)
            vertices = Integer.parseInt(args[0]);

        if (args.length > 1)
            edges = Integer.parseInt(args[1]);

        int seed = vertices+edges+5;    // tÃ¤stÃ¤kin voi vaihdella minkÃ¤laisia verkkoja syntyy

        if (args.length > 2)
            seed = Integer.parseInt(args[2]);


        // Luodaan satunnainen verkko
        DiGraph graph = GraphMaker.createDiGraph(vertices, edges, seed);
        System.out.println("\nVerkko (numerot ovat solmujen nimiÃ¤, kirjaimet kaarten nimiÃ¤):");
        System.out.println(graph);

        System.out.println("\nSeuraajat kullekin solmulle:");
        for (Vertex v : graph.vertices()) {
            System.out.println(v + " : " + seuraajienJoukko(graph, v));
        }


        int polkuja = 15; // testaa max 15 polkua
        System.out.println("\nPolkuja:");
        for (Vertex v1 : graph.vertices()) {
            for (Vertex v2 : graph.vertices()) {
                if (v1 == v2)
                    continue;
                System.out.println("" + v1 + "->" + v2 + " : " + jokuPolku(graph, v1, v2));
                if (polkuja-- <= 0)
                    break;
            }
        }





    } // main()


    /**
     * Solmun seuraajien joukko.
     * Solmun seuraajien joukko ovat ne solmut joihin on polku annetusta solmusta.
     * @param G tarkasteltava verkko (ei vÃ¤lttÃ¤mÃ¤ttÃ¤ tarvita)
     * @param solmu aloitussolmu
     * @return kaikki solmut joihin on polku solmusta solmu
     */
    static Set<Vertex> seuraajienJoukko(DiGraph G, Vertex solmu) {
        varita(G, DiGraph.WHITE);
        Set<Vertex> s = new HashSet<>();

        //Jos verkko on tyhjä
        if (G.size() == 0)
            //Palautetaan heti tyhjänä
            return s;

        // TODO
        //Käydään solmun naapurit läpi vuorotellen
        for (Vertex v : solmu.neighbors()) {
            //Lisätään seuraaja joukkoon
            if (v.getColor() == DiGraph.WHITE)
                s.add(v);
        }


        return s;
    }


    /**
     * Joku polku solmusta alku solmuun loppu.
     * Versio joka rakentaa polkua rekursiossa edetessÃ¤ (ja purkaa jollei maalia lÃ¶ydy)
     * @param G tarkasteltava verkko (tarvitaan pohjavÃ¤ritykseen)
     * @param alku polun alkusolmu
     * @param loppu polun loppusolmu
     * @return lista polun solmuista, tai tyhjÃ¤ lista jollei polkua ole olemassa
     */
    static List<Vertex> jokuPolku(DiGraph G, Vertex alku, Vertex loppu) {

        GraphMaker.varita(G, DiGraph.WHITE);
        List<Vertex> tulos = new LinkedList<>();

        // TODO
        // saa ja kannattaa tehdÃ¤ joku toinenkin metodi avuksi

        //Jos verkko on tyhjä
        if (G.size() == 0)
            //Palautetaan lista heti tyhjänä takaisin
            return tulos;

        //Etsitään polku rekursiivisesti
        etsiPolku(alku, loppu, tulos);


        return tulos;
    }
    public static List<Vertex> etsiPolku(Vertex alku, Vertex loppu, List<Vertex> tulos ) {
        //Etsitään polkua rekursiivisesti
        boolean olemassa = etsiPolkuRekursiivisesti(alku, loppu, tulos, new HashSet<>());
        //Jos polku on olemassa
        if (olemassa) {
            //palautetaan täytetty tulos
            return tulos;
        } else {
            //Jos polkua ei ole palautetaan tyhjä lista
            return null;
        }
    }

    private static boolean etsiPolkuRekursiivisesti(Vertex nykyinen, Vertex loppu, List<Vertex> polku, Set<Vertex> vierailtu) {
        //Muutetaan tutkittavan solmun väriksi harmaa
        nykyinen.setColor(DiGraph.GRAY);
        //Lisätään nykyinen solmu polku listaan
        polku.add(nykyinen);

        //Jos tutkittava solmu on sama kuin kohde solmu
        if (nykyinen == loppu) {
            //Palautetaan, että polku löytyi
            return true;
        }

        //Käydään läpi tutkittavan solmun naapurit
        for (Vertex naapuri : nykyinen.neighbors()) {
            //Jos olemme vierailleet jo naapurissa
            if (vierailtu.contains(naapuri)) {
                //Ohitetaan se
                continue;
            }

            //Jos naapuria ei olla vielä käsitelty
            if (naapuri.getColor() == DiGraph.WHITE) {
                //Lisätään se vierailtu listaan.
                vierailtu.add(naapuri);
                //Etsitään naapurista polku rekursiivisesti
                boolean olemassa = etsiPolkuRekursiivisesti(naapuri, loppu, polku, vierailtu);
                //Jos polkuja on olemassa
                if (olemassa) {
                    return true; // Polku löytyi.
                }
                //Poistetaan naapurisolmu vierailtu listalta.
                vierailtu.remove(naapuri);
            }
        }

        //nykyinen.setColor(DiGraph.BLACK); // Merkitään solmu mustaksi, kun kaikki naapurit on käyty läpi.
        //Poistetaan polusta viimeinen solmu (eli tutkittava solmu)
        polku.remove(polku.size() - 1);

        //Polkua ei löytynyt tutkittavasta solmusta
        return false;
    }

    /**
     * Syvyyssuuntainen lÃ¤pikÃ¤ynti (tekemÃ¤ttÃ¤ verkolle mitÃ¤Ã¤n)
     * Siis runko.
     *
     * @param G lÃ¤pikÃ¤ytÃ¤vÃ¤ verkko
     */
    static void dfsStart(DiGraph G) {
        for (Vertex v : G.vertices())                // kaikki solmut valkoisiksi
            v.setColor(DiGraph.WHITE);
        for (Vertex v : G.vertices())                // aloitus vielÃ¤ kÃ¤ymÃ¤ttÃ¶mistÃ¤ solmuista
            if (v.getColor() == DiGraph.WHITE)
                dfsRekursio(v);
    }



    // esimerkkejÃ¤


    /**
     * Syvyyssuuntainen lÃ¤pikÃ¤ynti solmusta node alkaen
     *
     * @param node aloitussolmu
     */
    static void dfsRekursio(Vertex node) {
        // tÃ¤hÃ¤n toimenpide solmulle node (jos esijÃ¤rjestys)
        node.setColor(DiGraph.GRAY);
        for (Vertex v : node.neighbors())                // vielÃ¤ kÃ¤ymÃ¤ttÃ¶mÃ¤t
            if (v.getColor() == DiGraph.WHITE)            // naapurit
                dfsRekursio(v);
        // tÃ¤hÃ¤n toimenpide solmulle node (jos jÃ¤lkijÃ¤rjestys)
    }


    /**
     * VÃ¤ritÃ¤ verkon kaikki solmut.
     * @param G vÃ¤ritettÃ¤vÃ¤ verkko
     * @param c vÃ¤ri jota kÃ¤ytetÃ¤Ã¤n
     */
    static void varita(AbstractGraph G, int c) {
        for (Vertex v : G.vertices())
            v.setColor(c);
    }



}