public class Aeroporto {

    private String icao;
    private String iata;
    private String nome;
    private String municipio;
    private String estado;
    private String pais;
    private double latitude;
    private double longitude;

    public Aeroporto(String icao, String iata, String nome,
                     String municipio, String estado, String pais,
                     double latitude, double longitude) {

        this.icao = icao;
        this.iata = iata;
        this.nome = nome;
        this.municipio = municipio;
        this.estado = estado;
        this.pais = pais;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIcao() {
        return icao;
    }

    public String getIata() {
        return iata;
    }

    public String getNome() {
        return nome;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getEstado() {
        return estado;
    }

    public String getPais() {
        return pais;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return icao + " - " + nome;
    }
}