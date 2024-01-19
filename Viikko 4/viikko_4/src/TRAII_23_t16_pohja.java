import fi.uef.cs.tra.AbstractGraph;
import fi.uef.cs.tra.DiGraph;
import fi.uef.cs.tra.Graph;
import fi.uef.cs.tra.Vertex;

import java.util.LinkedList;
import java.util.List;


public class TRAII_23_t16_pohja {

    public static void main(String[] args) {

        // defaults
        int vertices = 8;
        int edges = 5;

        if (args.length > 0)
            vertices = Integer.parseInt(args[0]);

        if (args.length > 1)
            edges = Integer.parseInt(args[1]);

        int seed = vertices+edges;

        if (args.length > 2)
            seed = Integer.parseInt(args[2]);

        Graph graph;

        System.out.println("\nVerkko (kaksi puuta, ei kehÃ¤Ã¤): ");
        // graph = GraphMaker.createGraph(vertices, edges, seed);
        graph = GraphMaker.createFlora(0, 0, 2, 0, vertices);
        System.out.println(GraphMaker.toString(graph, 0));
        List<Vertex> keha = jokuKeha(graph);
        System.out.println("KehÃ¤: " + keha);

        System.out.println("\nLisÃ¤tÃ¤Ã¤n kehÃ¤: ");
        GraphMaker.addRandomCycle(graph, 3, false);
        keha = jokuKeha(graph);
        System.out.println(GraphMaker.toString(graph, 0));
        System.out.println("KehÃ¤: " + keha);

    }

    static List<Vertex> jokuKeha(Graph G) {
        varita(G, Graph.WHITE);
        List<Vertex> jokuKeha = new LinkedList<>();

        // TODO
        // vihje: hae kehÃ¤ syvyyssuuntaisella haulla
        //Käydään läpi verkon solmut yksitellen
        for (Vertex v: G.vertices()){
            //Tyhjennetään lista, jossa säilytetään kehää
            jokuKeha.clear();
            //Jos kehä löytyy
            if (syvyysHaku(v, jokuKeha, v)){
                //palautetaan lista kehän solmuista
                return jokuKeha;
            }
            //Väritetään verkko takaisin valkoiseksi
            varita(G, Graph.WHITE);
        }

        return null;
    }

    static boolean syvyysHaku(Vertex v, List<Vertex> jokuKeha, Vertex vanhempi){
        //Väritetään solmu harmaaksi
        v.setColor(DiGraph.GRAY);
        //Lisätään solmu listaan
        jokuKeha.add(v);

        //Käydään solmun naapurit läpi
        for (Vertex vv: v.neighbors()){
            //Jos tutkittava naapuri on jo harmaa
           if (vv.getColor() == Graph.GRAY ){
               //Jos solmu ei ole sen vanhempi, mutta on ensimmäinen solmu kehässä
               if (vv != vanhempi && jokuKeha.get(0) == vv) {
                   //kehä on löydetty
                   jokuKeha.add(vv);
                   return true;
               }
           }
           //Jos solmussa ei ole vierailty
           else if (vv.getColor() == Graph.WHITE)
               //Tarkistetaan löytyykö solmusta kehää
               if (syvyysHaku(vv, jokuKeha, v))
                   return true;
        }

        //Poistetaan solmu mahdollisesta kehästä
        jokuKeha.remove(jokuKeha.size()-1);

        return false;
    }




    // syvyyssuuntainen lÃ¤pikynti vÃ¤rittÃ¤en vÃ¤rillÃ¤ c
    static void dfsColor(Vertex v, int color) {
        v.setColor(color);

        for (Vertex w : v.neighbors())
            if (w.getColor() != color)
                dfsColor(w, color);
    }

    // verkko annetun vÃ¤riseksi
    static void varita(AbstractGraph g, int c) {
        for (Vertex v : g.vertices())
            v.setColor(c);
    }



}