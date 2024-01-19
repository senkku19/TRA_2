// TRAII_23_t14.java SJ

import fi.uef.cs.tra.DiGraph;
import fi.uef.cs.tra.Edge;
import fi.uef.cs.tra.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TRAII_23_t14_pohja {

    public static void main(String[] args) {

        // DiGraph graph = GraphMaker.createDiGraph(vertices, edges, rseed);
        DiGraph graph = Q1();

        System.out.println(GraphMaker.toString(graph, 1));

        Vertex dst = null;
        for (Vertex v : graph.vertices())
            if (v.getLabel().equals("0"))
                dst = v;
        if (dst == null)
            return;


        Set<Vertex> q = quorum(graph, dst, 0.5F);
        System.out.println("\nCompanies that are under quorum of " + dst + " : " + q);

    }   // main()


    /**
     * 14. YhtiÃ¶llÃ¤ x on mÃ¤Ã¤rÃ¤ysvalta yhtiÃ¶ssÃ¤ y jos ja vain jos on olemassa yhtiÃ¶t z1,z 2,...,z k joissa yhtiÃ¶llÃ¤
     x on mÃ¤Ã¤rÃ¤ysvalta ja yhtiÃ¶t x,z1,z2,...,z k omistavat yhteensÃ¤ yli 50% yhtiÃ¶n y osakkeista. TÃ¤llaista
     laskentaa tarvitaan esimerkiksi yt-neuvotteluissa ja muissa lakiteknisissÃ¤ asioissa. Mallinnetaan
     omistuksia suunnatulla verkolla jossa jokainen yhtiÃ¶ on solmu ja kun yhtiÃ¶ x omistaa r% yhtiÃ¶n
     y osakkeista, niin verkossa on kaari (x,y) jonka paino on r. Hahmottele algoritmi joka etsii kaikki
     ne yhtiÃ¶t joihin yhtiÃ¶llÃ¤ x on mÃ¤Ã¤rÃ¤ysvalta.
     15. Toteuta tehtÃ¤vÃ¤n 15 algoritmi. SyÃ¶tteenÃ¤ ovat verkko yhtiÃ¶iden omistusosuuksista, tarkasteltava
     yhtiÃ¶ y (siis verkon solmu) ja mÃ¤Ã¤rÃ¤ysvaltaan riittÃ¤vÃ¤ osuus (yleensÃ¤ 50%). Tuloksena on se joukko
     yhtiÃ¶itÃ¤ (solmuja) jotka ovat yhtiÃ¶n y mÃ¤Ã¤rÃ¤ysvallassa.
     *
     * @param g graph of owning stocks
     * @param v the company under inspection
     * @param limit required limit of owning (e.g., 0.5)
     * @return the set of companies under quorum of v. Including v.
     **/
    static Set<Vertex> quorum(DiGraph g, Vertex v, float limit) {


        Set<Vertex> tulos = new HashSet<>();
        //Luodaan avuksi kuvaus, joka sisältää solmun ja siihen tulevat kaaret
        Map<Vertex, HashSet<Edge>> apukuvaus = new HashMap<>();

        //Värjätään verkko valkoiseksi
        for (Vertex w : g.vertices()) {
            w.setColor(DiGraph.WHITE);
        }

        // TODO
        //Alustetaan apukuvaus
        //Käydään läpi kaikki verkon kaaret
        for (Edge e: g.edges()) {
            //Jos kuvauksesta löytyy jo solmu
            if (apukuvaus.containsKey(e.getEndPoint()))
                //Lisätään kaari solmun kaariin
                apukuvaus.get(e.getEndPoint()).add(e);
            //Jos solmua ei löydy vielä kuvauksesta
            else {
                //Luodaan uusi kokoelma
                HashSet<Edge> apuJoukko = new HashSet<>();
                //Lisätään solmun kaari kokoelmaan
                apuJoukko.add(e);
                //Lisätään molemmat kuvaukseen
                apukuvaus.put(e.getEndPoint(), apuJoukko);
            }
        }

        //Etsitään yhtiöt, joihin yhtiöllä 0 on päätösvalta rekursiivisesti
        rekursiivinenToteutus(v, tulos, apukuvaus, v, limit);


        return tulos;
    }

    static void rekursiivinenToteutus(Vertex alku, Set<Vertex> tulos, Map<Vertex, HashSet<Edge>> apukuvaus , Vertex v, float limit ){
        //Käydään läpi verkon kaaret
        for (Edge e: v.edges()){
            //Tallenetaan kaaren paino muuttujaan
            float paino = e.getWeight();
            //Jos paino ylittää rajan
            if (paino > limit && (e.getStartPoint() == alku || tulos.contains(e.getStartPoint()))){
                //Lisätään solmu, johon kaari menee, tuloksiin
                tulos.add(e.getEndPoint());
                //Ja etsitään seuraava solmu
                rekursiivinenToteutus(alku, tulos, apukuvaus, e.getEndPoint(), limit);
                //Muuten
            } else {
                //Haetaan kaikki kaaret jotka päättyvät solmuun.
                HashSet<Edge> apuJoukko = apukuvaus.get(e.getEndPoint());
                //luodaan laskuri
                float valiLasku = 0;
                //käydään solmun kaaret läpi yksitellen
                for (Edge ee: apuJoukko){
                    //Jos tuloksisa löytyy aloitussolmu tai aloitussolmu on 0
                    if (tulos.contains(ee.getStartPoint()) || ee.getStartPoint() == alku ){
                        //Lisätään paino laskuriin
                        valiLasku += ee.getWeight();
                        //jos laskuri ylittää rajan
                        if (valiLasku > limit) {
                            //lisätään solmu tuloksiin
                            tulos.add(ee.getEndPoint());
                            //Ja lopetetaan somun tutkiminen
                            rekursiivinenToteutus(alku, tulos, apukuvaus, ee.getEndPoint(), limit);
                        }
                    }
                }

            }
        }
    }

    // example graph
    // for company "0" and limit:
    //  0.5, the result should be (0,) 1, 2, 3, 4
    //  0.6, the result should be (0,) 2
    //  0.39, the result should be (0,) 1, 2, 3, 4, 5, 6
    static DiGraph Q1() {

        int n = 7;
        DiGraph g = new DiGraph();
        Vertex[] va = new Vertex[n];
        for (int i = 0; i < n; i++)
            va[i] = g.addVertex(""+i);

        va[0].addEdge(va[1], 0.3F);
        va[0].addEdge(va[2], 0.7F);
        va[0].addEdge(va[4], 0.2F);
        va[1].addEdge(va[3], 0.2F);
        va[2].addEdge(va[1], 0.3F);
        va[2].addEdge(va[3], 0.6F);
        va[3].addEdge(va[4], 0.4F);
        va[3].addEdge(va[5], 0.4F);
        va[3].addEdge(va[6], 0.2F);
        va[4].addEdge(va[6], 0.2F);
        va[6].addEdge(va[5], 0.2F);

        return g;


    }

}