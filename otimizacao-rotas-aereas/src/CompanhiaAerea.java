public class CompanhiaAerea {
    private String icao;
    private String iata;
    private String nome;
    private String pais;

    public CompanhiaAerea(String icao, String iata, String nome, String pais) {
        this.icao = icao;
        this.iata = iata;
        this.nome = nome;
        this.pais = pais;
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

    public String getPais() {
        return pais;
    }

    @Override
    public String toString() {
        return icao + " - " + nome + " (" + pais + ")";
    }
}