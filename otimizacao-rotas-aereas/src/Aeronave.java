public class Aeronave {

    private String icao;
    private String iata;
    private String modelo;
    private String categoria;

    public Aeronave(String icao, String iata, String modelo, String categoria) {
        this.icao = icao;
        this.iata = iata;
        this.modelo = modelo;
        this.categoria = categoria;
    }

    public String getIcao() {
        return icao;
    }

    public String getIata() {
        return iata;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return icao + " - " + modelo;
    }
}
