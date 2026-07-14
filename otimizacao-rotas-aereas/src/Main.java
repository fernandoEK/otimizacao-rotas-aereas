import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {

        Map<String, Aeroporto> aeroportos = LeitorCSV.lerAeroportos("dados/aerodromos.csv");
        Map<String, CompanhiaAerea> companhias = LeitorCSV.lerCompanhias("dados/cias.csv");
        Map<String, Aeronave> aeronaves = LeitorCSV.lerAeronaves("dados/aeronaves.csv");
        List<Voo> voos = LeitorCSV.lerVoos("dados/voos_mar2026.csv");

        GrafoAereo grafo = new GrafoAereo();
        for (Voo voo : voos) {
            grafo.adicionarVoo(voo);
        }

        // Filtra só aeroportos brasileiros pra calcular os hubs nacionais
        Set<String> nacionais = new HashSet<>();
        for (Aeroporto aeroporto : aeroportos.values()) {
            if (aeroporto.getPais().equalsIgnoreCase("BRASIL")) {
                nacionais.add(aeroporto.getIcao());
            }
        }

        List<String> hubs = grafo.top5HubsNacionais(nacionais);
        ImpressoraRotas.imprimirTopHubs(grafo, aeroportos, hubs);

        EntradaUsuario entrada = InterfaceUsuario.lerEntrada(aeroportos, hubs);
        if (entrada == null) return;

        if (entrada.getHubRemovido() != null) {
            grafo.removerAeroporto(entrada.getHubRemovido());
            hubs.remove(entrada.getHubRemovido());
            System.out.println("Hub removido: " + entrada.getHubRemovido());
        }

        List<Voo> rota = grafo.buscarMelhorRota(
                entrada.getOrigem(),
                entrada.getDestino(),
                entrada.getHorarioInicial(),
                hubs
        );

        ImpressoraRotas.imprimirRota(rota, aeroportos, companhias, aeronaves, entrada.getHorarioInicial());
    }
}