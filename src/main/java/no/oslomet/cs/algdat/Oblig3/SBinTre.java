package no.oslomet.cs.algdat.Oblig3;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    //Oppgave 1

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Null verdier er ikke tillat");

        Node<T> p = rot;
        Node<T> q = null;

        int cmp = 0;

        while (p != null){
            q = p;
            cmp = comp.compare(verdi, p.verdi);
            p = cmp < 0 ? p.venstre:p.høyre;
        }
        p = new Node<>(verdi, q);

        if(q == null) rot = p;
        else if(cmp < 0) q.venstre =p;
        else q.høyre = p;
        antall++;
        endringer++;

        return true;
    }

    // oppgave 2

    public int antall(T verdi) {
        Node<T> p = rot;
        int n = 0;

        while(p != null){
            int cmp = comp.compare(verdi, p.verdi);

            if(cmp < 0) p = p.venstre;
            else if(cmp > 0) p = p.høyre;
            else{
                p = p.høyre;
                n++;
            }
        }
        return n;
    }

    // oppgave 3

    private static <T> Node<T> førstePostorden(Node<T> p) {
        Objects.requireNonNull(p);

        while (true){
            if (p.venstre != null) p = p.venstre;
            else if(p.høyre != null) p = p.høyre;
            else return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Node<T> e = p.forelder;
        if(e == null) return null;
        if (e.høyre == p || e.høyre == null) return e;
        else return førstePostorden(e.høyre);
    }

    // oppgave 4

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = rot;
        Node<T> første = førstePostorden(p);

        oppgave.utførOppgave(første.verdi);

        while (første.forelder != null){
            første = nestePostorden(første);
            oppgave.utførOppgave(Objects.requireNonNull(første).verdi);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {

        if(!tom()){
            postordenRecursive(rot, oppgave);
        }
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == null) return;

        postordenRecursive(p.venstre, oppgave);
        postordenRecursive(p.høyre, oppgave);
        oppgave.utførOppgave(p.verdi);
    }




    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }



    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }





    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
