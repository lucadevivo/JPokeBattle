package battle.logic;

import java.io.Serializable;
import java.util.Arrays;

public class ProfiloUtente implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private int partiteGiocate;
    private int partiteVinte;
    private int partitePerse;
    private Pokemon[] squadra;

    public ProfiloUtente(String nome, Pokemon[] squadra) {
    	
        this.nome = nome;
        this.squadra = squadra;
        this.partiteGiocate = 0;
        this.partiteVinte = 0;
        this.partitePerse = 0;
    }

    public String getNome() {
        return nome;
    }

    public Pokemon[] getSquadra() {
        return squadra;
    }

    public int getPartiteGiocate() {
        return partiteGiocate;
    }

    public int getPartiteVinte() {
        return partiteVinte;
    }

    public int getPartitePerse() {
        return partitePerse;
    }

    public void incrementaPartiteGiocate() {
        partiteGiocate++;
    }

    public void incrementaPartiteVinte() {
        partiteVinte++;
    }

    public void incrementaPartitePerse() {
        partitePerse++;
    }

    @Override
    public String toString() {
        return "ProfiloUtente [nome=" + nome + ", partiteGiocate=" + partiteGiocate + ", partiteVinte=" + partiteVinte
                + ", partitePerse=" + partitePerse + ", squadra=" + Arrays.toString(squadra) + "]";
    }
}
