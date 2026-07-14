import java.time.LocalDateTime;

public class EntradaUsuario {

    private String origem;
    private String destino;
    private LocalDateTime horarioInicial;
    private String hubRemovido;

    public EntradaUsuario(String origem, String destino,
                          LocalDateTime horarioInicial,
                          String hubRemovido) {
        this.origem = origem;
        this.destino = destino;
        this.horarioInicial = horarioInicial;
        this.hubRemovido = hubRemovido;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDateTime getHorarioInicial() {
        return horarioInicial;
    }

    public String getHubRemovido() {
        return hubRemovido;
    }
}