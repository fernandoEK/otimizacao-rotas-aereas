import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ImpressoraRotas {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void imprimirTopHubs(
            GrafoAereo grafo,
            Map<String, Aeroporto> aeroportos,
            List<String> hubs
    ) {
        System.out.println("\nTop 5 hubs nacionais:");

        for (int i = 0; i < hubs.size(); i++) {
            String codigo = hubs.get(i);
            Aeroporto aeroporto = aeroportos.get(codigo);
            String nome = aeroporto != null ? aeroporto.getNome() : "Aeroporto não encontrado";

            System.out.println((i + 1) + ". " + nome + " (" + codigo + ")"
                    + " | Entrada: " + grafo.grauEntrada(codigo)
                    + " | Saída: " + grafo.grauSaida(codigo)
                    + " | Total: " + grafo.grauTotal(codigo));
        }
    }

    public static void imprimirRota(
            List<Voo> rota,
            Map<String, Aeroporto> aeroportos,
            Map<String, CompanhiaAerea> companhias,
            Map<String, Aeronave> aeronaves,
            LocalDateTime horarioInicial
    ) {
        System.out.println("\nRota encontrada:");

        if (rota.isEmpty()) {
            System.out.println("Nenhuma rota encontrada para os parâmetros informados.");
            return;
        }

        for (int i = 0; i < rota.size(); i++) {
            Voo voo = rota.get(i);

            System.out.println("\nTrecho " + (i + 1) + ":");
            System.out.println(voo.getOrigem() + " - " + nomeAeroporto(aeroportos, voo.getOrigem())
                    + " -> " + voo.getDestino() + " - " + nomeAeroporto(aeroportos, voo.getDestino()));
            System.out.println("Partida : " + FORMATO.format(voo.getPartida()));
            System.out.println("Chegada : " + FORMATO.format(voo.getChegada()));
            System.out.println("Companhia: " + nomeCompanhia(companhias, voo.getCompanhia()));
            System.out.println("Voo     : " + voo.getNumeroVoo());
            System.out.println("Aeronave: " + modeloAeronave(aeronaves, voo.getAeronave()));

            if (i > 0) {
                Voo anterior = rota.get(i - 1);
                Duration conexao = Duration.between(anterior.getChegada(), voo.getPartida());
                System.out.println("Espera na conexão: " + formatarDuracao(conexao));
            }
        }

        // Tempo de voo puro: soma dos tempos em ar de cada trecho
        Duration tempoVoo = Duration.ZERO;
        for (Voo voo : rota) {
            tempoVoo = tempoVoo.plus(Duration.between(voo.getPartida(), voo.getChegada()));
        }

        // Tempo total de viagem: desde a chegada ao aeroporto de origem até o pouso final
        Duration tempoViagem = Duration.between(
                horarioInicial,
                rota.get(rota.size() - 1).getChegada()
        );

        System.out.println("\nTempo total de voo (só em ar): " + formatarDuracao(tempoVoo));
        System.out.println("Tempo total de viagem (com esperas): " + formatarDuracao(tempoViagem));
        System.out.println("Voos: " + rota.size() + " | Conexões: " + (rota.size() - 1));

        System.out.println("\nMapa da rota:");
        System.out.println(gerarLinkGoogleMaps(rota, aeroportos));
    }

    // Gera um link do Google Maps com todos os aeroportos da rota como waypoints
    private static String gerarLinkGoogleMaps(List<Voo> rota, Map<String, Aeroporto> aeroportos) {
        StringBuilder url = new StringBuilder("https://www.google.com/maps/dir/");

        // Adiciona a origem do primeiro voo
        String codigoOrigem = rota.get(0).getOrigem();
        Aeroporto origem = aeroportos.get(codigoOrigem);
        if (origem != null) {
            url.append(origem.getLatitude()).append(",").append(origem.getLongitude()).append("/");
        }

        // Adiciona o destino de cada trecho (inclui conexões e destino final)
        for (Voo voo : rota) {
            String codigoDestino = voo.getDestino();
            Aeroporto destino = aeroportos.get(codigoDestino);
            if (destino != null) {
                url.append(destino.getLatitude()).append(",").append(destino.getLongitude()).append("/");
            }
        }

        return url.toString();
    }

    private static String nomeAeroporto(Map<String, Aeroporto> aeroportos, String codigo) {
        Aeroporto a = aeroportos.get(codigo);
        return a != null ? a.getNome() : "Aeroporto não encontrado";
    }

    private static String nomeCompanhia(Map<String, CompanhiaAerea> companhias, String codigo) {
        CompanhiaAerea c = companhias.get(codigo);
        return c != null ? c.getNome() + " (" + codigo + ")" : codigo;
    }

    private static String modeloAeronave(Map<String, Aeronave> aeronaves, String codigo) {
        Aeronave a = aeronaves.get(codigo);
        return a != null ? a.getModelo() + " (" + codigo + ")" : codigo;
    }

    private static String formatarDuracao(Duration duracao) {
        long horas = duracao.toHours();
        long minutos = duracao.toMinutesPart();
        return horas + "h " + minutos + "min";
    }
}
