import fi.uef.cs.tra.BTree;
import fi.uef.cs.tra.BTreeNode;

import java.util.HashSet;
import java.util.Set;

public class TRAII_23_X1_moilanes implements TRAII_23_X1 {
    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     * Olkoon n binääripuun solmujen määrä:
     *
     * Laskin algoritmini aikavaativuudeksi O(n), koska lehtisolmuja etsiessä ohjelman täytyy mennä koko puu
     * läpi ennen kuin se löytää kaikki binääripuun lehtisolmut.
     *
     *Mielestäni algoritmini on toimiva ja selkeä. Algoritmini hyödyntää apumetodia, jonka parametrina ovat binääripuun solmu ja lehdet joukko.
     *Apumetodi käy läpi binääripuun solmut rekursiivisesti ja lehtisolmun löydettyä lisää/päivittää sen lehdet-joukkoon.
     *
     * En usko, että algoritmista voisi tehdä tehokkaampaan, koska lehtisolmua etsiessä pitää ymmärtääkseni kulkea koko puu läpi, jotta löytää kaikki lehtisolmut.
     *Ehkä toinen lähestymistapa olisi etsiä lehtisolmut puun korkeuden mukaan, mutta en usko, että
     *se olisi ainakaan tehokkaampi tapa tai edes mahdollista.
     *
     *
     *
     **/
    /**
     * Puun lehtisolmut.
     * Palauttaa joukkona kaikki ne puun T solmut joilla ei ole yhtÃ¤Ã¤n lasta.
     * @param T syÃ¶tepuu
     * @param <E> puun alkioiden tyyppi (ei kÃ¤ytetÃ¤ muuten kuin muuttujien parametrointiin)
     * @return lehtisolmujen joukko
     */
    @Override
    public <E> Set<BTreeNode<E>> lehtiSolmut(BTree<E> T) {
        Set<BTreeNode<E>> lehdet = new HashSet<>();

        // TODO
        // saa ja kannattaa tehdÃ¤ joku toinenkin metodi avuksi

        //Jos puu on tyhjä
        if (T.isEmpty())
            //Palautetaan joukko heti tyhjänä takaisin
            return lehdet;

        //Muuten:
        //Haetaan puun juuri
        BTreeNode<E> puuJuuri = T.getRoot();

        //Kutsutaan apumetodia
        tallennetaanSolmut(puuJuuri, lehdet);

        return lehdet;
    }

    //Apumetodi
    public <E>  void tallennetaanSolmut(BTreeNode<E> solmu, Set<BTreeNode<E>> lehdet ) {
        //Jos solmu on tyhjä
        if (solmu == null)
            //ei tehdä mitään, vaan lopetetaan
            return;

        //Jos solmu on lehtisolmu
        if (solmu.getLeftChild() == null && solmu.getRightChild() == null)
            //Lisätään solmu lehdet joukkoon
            lehdet.add(solmu);

        //Etsitään rekursion avulla lehtisolmuja solmun vasemmasta lapsesta
        tallennetaanSolmut(solmu.getLeftChild(), lehdet);
        //Tehdään sama, mutta etsitään lehtisolmua solmun oikeasta lapsesta
        tallennetaanSolmut(solmu.getRightChild(), lehdet);

    }


}