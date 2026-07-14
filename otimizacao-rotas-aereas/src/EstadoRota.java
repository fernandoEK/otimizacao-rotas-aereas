import java.time.LocalDateTime;

public class EstadoRota {

    private String aeroporto;
    private LocalDateTime horarioChegada;

    public EstadoRota(String aeroporto, LocalDateTime horarioChegada) {
        this.aeroporto = aeroporto;
        this.horarioChegada = horarioChegada;
    }

    public String getAeroporto() {
        return aeroporto;
    }

    public LocalDateTime getHorarioChegada() {
        return horarioChegada;
    }
}
