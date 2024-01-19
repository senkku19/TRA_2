// TRAII_23_t3_4_pohja.java SJ

/**
 3. Kirjoita algoritmi, joka saa syÃ¶tteenÃ¤Ã¤n kokoelman, ja palauttaa tuloksenaan sen alkion,
 joka esiintyi syÃ¶tteessÃ¤ useimmin. (Jos mahdollisia tuloksia on monta, niin algoritmisi voi
 palauttaa niistÃ¤ minkÃ¤ tahansa.) Vertaile alkioita alkion .equals() -operaatiolla. KÃ¤ytÃ¤ apuna
 kuvausta (Map<E, Integer>). MikÃ¤ on algoritmisi aikavaativuus? Ota pohjaa ja esimerkkiÃ¤
 Moodlesta.
 4. Vertaa tehtÃ¤vÃ¤n 3 toimintaa kun apuvÃ¤linekuvauksena on (a) HashMap tai (b) TreeMap.
 Kirjoita ohjelma joka mittaa nÃ¤iden nopeutta kun syÃ¶te kasvaa. Miten selitÃ¤t tulokset? Ota
 pohjaa ja esimerkkiÃ¤ Moodlesta.

 */

import java.util.*;
import java.util.concurrent.TimeUnit;

public class TRAII_23_t3_4_pohja {


    // PÃ¤Ã¤ohjelman kÃ¤yttÃ¶:
    // java TRAII_23_t3_4_pohja [N] [siemen]
    // missÃ¤ N on alkioiden mÃ¤Ã¤rÃ¤
    // ja siemen on satunnaislukusiemen

    public static void main(String[] args) {

        // á¸±okoelman koko
        int N = 1000000;
        if (args.length > 0)
            N = Integer.parseInt(args[0]);


        // satunnaislukusiemen
        int siemen = N;
        if (args.length > 1)
            siemen = Integer.parseInt(args[1]);


        // ensin pieni lista
        Random r = new Random(siemen);
        LinkedList<Integer> L = randomLinkedList(20, r);

        // tulostetaan lista jos alkioita ei ole paljoa
        if (L.size() <= 30) {
            System.out.println(L);
        }
        Ajastin2 at = null;
        Integer useimmin = null;


        at = new Ajastin2("" + L.size());
        useimmin = useimmin(L);
        at.stop();
        System.out.println("aika: " + at + ", " +
                (at.time() * 1.0 / N) + " ns/elem");
        System.out.println("Useimmin esiintyi " + useimmin);

        // sitten vÃ¤hÃ¤n isompi
        L = randomLinkedList(N, r);

        // tulostetaan lista jos alkioita ei ole paljoa
        if (N <= 30) {
            System.out.println(L);
        }

        at = new Ajastin2("" + L.size());
        useimmin = useimmin(L);
        at.stop();
        System.out.println("aika: " + at + ", " +
                (at.time() * 1.0 / N) + " ns/elem");
        System.out.println("Useimmin esiintyi " + useimmin);

        // TODO: tehtÃ¤vÃ¤ 4: vertaa tehokkuuksia

        long aloitusAika = System.nanoTime();
        useimmin(L);
        long lopetusAika = System.nanoTime();
        long hashMapAika = TimeUnit.NANOSECONDS.toMillis(lopetusAika - aloitusAika);

        // Mittaa suorituskykyä TreeMapilla
        aloitusAika = System.nanoTime();
        useimminTra(L);
        lopetusAika = System.nanoTime();
        long treeMapAika = TimeUnit.NANOSECONDS.toMillis(lopetusAika - aloitusAika);

        System.out.println("HashMap-aika: " + hashMapAika + " ms");
        System.out.println("TreeMap-aika: " + treeMapAika + " ms");


    } // main()


    /**
     * MikÃ¤ alkio esiintyy useimmin kokoelmassa C?
     * Jos usea alkio esiintyy yhtÃ¤ usein, palautetaan niistÃ¤ yksi.
     * @param C SyÃ¶tekokoelma
     * @param <E> alkiotyyppi
     * @return yleisin alkio, tai null jos kokoelman on tyhjÃ¤
     */
    public static <E> E useimmin(Collection<E> C) {
        Map<E, Integer> apuKuvaus = new HashMap<>();

        // Käydään kokoelma läpi ja lasketaan jokaisen elementin esiintymät
        for (E alkio : C) {
            apuKuvaus.put(alkio, apuKuvaus.getOrDefault(alkio, 0) + 1);
        }

        //Arvot jotka pitävät kirjaa, millä alkiolla on eniten ilmentymiä tälläkertaa
        E enitenIlmentymia = null;
        int eniten = 0;

        // Etsitään elementti, joka esiintyy useimmin
        for (E alkio : apuKuvaus.keySet()) {
            int alkionIlmentymat = apuKuvaus.get(alkio);
            //jos alkio on ensimmäinen jota tarkastellaan alustetaan sillä heti apualkio
            if (enitenIlmentymia == null){
                eniten = alkionIlmentymat;
                enitenIlmentymia = alkio;
                //jos alkio on suurempi tai yhtäsuuri kuin tällä hetkellä se jolla on eniten alkioita alustetaan apualkio uudestaan.
            } else if (alkionIlmentymat >= eniten){
                eniten = alkionIlmentymat;
                enitenIlmentymia = alkio;
                }
            }

        return enitenIlmentymia;
    }

    public static <E> E useimminTra(Collection<E> C) {
        Map<E, Integer> apuKuvaus = new TreeMap<>();

        // Käydään kokoelma läpi ja lasketaan jokaisen elementin esiintymät
        for (E alkio : C) {
            apuKuvaus.put(alkio, apuKuvaus.getOrDefault(alkio, 0) + 1);
        }

        //Arvot jotka pitävät kirjaa, millä alkiolla on eniten ilmentymiä tälläkertaa
        E enitenIlmentymia = null;
        int eniten = 0;

        // Etsitään elementti, joka esiintyy useimmin
        for (E alkio : apuKuvaus.keySet()) {
            int alkionIlmentymat = apuKuvaus.get(alkio);
            //jos alkio on ensimmäinen jota tarkastellaan alustetaan sillä heti apualkio
            if (enitenIlmentymia == null){
                eniten = alkionIlmentymat;
                enitenIlmentymia = alkio;
                //jos alkio on suurempi tai yhtäsuuri kuin tällä hetkellä se jolla on eniten alkioita alustetaan apualkio uudestaan.
            } else if (alkionIlmentymat >= eniten){
                eniten = alkionIlmentymat;
                enitenIlmentymia = alkio;
            }
        }

        return enitenIlmentymia;
    }

    public static LinkedList<Integer> randomLinkedList(int n, int seed) {
        Random r = new Random(seed);
        LinkedList<Integer> V = new LinkedList<>();
        for (int i = 0; i < n; i++)
            V.add(r.nextInt(n));
        return V;
    }

    public static LinkedList<Integer> randomLinkedList(int n, Random r) {
        LinkedList<Integer> V = new LinkedList<>();
        for (int i = 0; i < n; i++)
            V.add(r.nextInt(n));
        return V;
    }


} // class
