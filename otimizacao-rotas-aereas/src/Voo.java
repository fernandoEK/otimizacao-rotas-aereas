import java.time.LocalDateTime;

public class Voo {

    private String origem;
    private String destino;

    private LocalDateTime partida;
    private LocalDateTime chegada;

    private String companhia;
    private String numeroVoo;
    private String aeronave;

    public Voo(String origem,
               String destino,
               LocalDateTime partida,
               LocalDateTime chegada,
               String companhia,
               String numeroVoo,
               String aeronave) {

        this.origem = origem;
        this.destino = destino;
        this.partida = partida;
        this.chegada = chegada;
        this.companhia = companhia;
        this.numeroVoo = numeroVoo;
        this.aeronave = aeronave;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDateTime getPartida() {
        return partida;
    }

    public LocalDateTime getChegada() {
        return chegada;
    }

    public String getCompanhia() {
        return companhia;
    }

    public String getNumeroVoo() {
        return numeroVoo;
    }

    public String getAeronave() {
        return aeronave;
    }

    @Override
    public String toString() {
        return origem + " -> " + destino +
                " | " + partida +
                " | " + chegada;
    }
}

