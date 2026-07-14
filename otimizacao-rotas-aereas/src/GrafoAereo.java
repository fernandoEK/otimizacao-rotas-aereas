import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class GrafoAereo {

    private Map<String, List<Voo>> adjacencias;
    private Map<String, Integer> grauEntrada;
    private Set<String> aeroportos;

    public GrafoAereo() {
        this.adjacencias = new HashMap<>();
        this.grauEntrada = new HashMap<>();
        this.aeroportos = new HashSet<>();
    }

    public void adicionarVoo(Voo voo) {
        if (voo == null) return;

        String origem = voo.getOrigem();
        String destino = voo.getDestino();

        if (origem == null || destino == null || origem.isEmpty() || destino.isEmpty()) return;

        if (!adjacencias.containsKey(origem)) {
            adjacencias.put(origem, new ArrayList<>());
        }
        adjacencias.get(origem).add(voo);

        grauEntrada.put(destino, grauEntrada.getOrDefault(destino, 0) + 1);
        aeroportos.add(origem);
        aeroportos.add(destino);
    }

    public List<Voo> getVoosSaindo(String aeroporto) {
        return adjacencias.getOrDefault(aeroporto, new ArrayList<>());
    }

    public int grauSaida(String aeroporto) {
        return getVoosSaindo(aeroporto).size();
    }

    public int grauEntrada(String aeroporto) {
        return grauEntrada.getOrDefault(aeroporto, 0);
    }

    public int grauTotal(String aeroporto) {
        return grauEntrada(aeroporto) + grauSaida(aeroporto);
    }

    public Set<String> getAeroportos() {
        return new HashSet<>(aeroportos);
    }

    // Retorna os 5 aeroportos nacionais com maior grau total (hubs estruturais)
    public List<String> top5HubsNacionais(Set<String> aeroportosNacionais) {
        List<String> lista = new ArrayList<>();

        for (String aeroporto : aeroportos) {
            if (aeroportosNacionais.contains(aeroporto)) {
                lista.add(aeroporto);
            }
        }

        lista.sort(Comparator.comparingInt(this::grauTotal).reversed());

        return lista.size() > 5 ? new ArrayList<>(lista.subList(0, 5)) : lista;
    }

    public void removerAeroporto(String codigo) {
        if (codigo == null || codigo.isEmpty()) return;

        adjacencias.remove(codigo);
        grauEntrada.remove(codigo);
        aeroportos.remove(codigo);

        // Remove todos os voos que tinham esse aeroporto como destino
        for (List<Voo> listaVoos : adjacencias.values()) {
            List<Voo> paraRemover = new ArrayList<>();
            for (Voo voo : listaVoos) {
                if (voo.getDestino().equals(codigo)) {
                    paraRemover.add(voo);
                }
            }
            listaVoos.removeAll(paraRemover);
        }

        recalcularGrausEntrada();
    }

    private void recalcularGrausEntrada() {
        grauEntrada.clear();

        for (List<Voo> listaVoos : adjacencias.values()) {
            for (Voo voo : listaVoos) {
                String destino = voo.getDestino();
                grauEntrada.put(destino, grauEntrada.getOrDefault(destino, 0) + 1);
            }
        }
    }

    // Dijkstra temporal: minimiza o horário de chegada no destino
    public List<Voo> buscarMelhorRota(
            String origem,
            String destino,
            LocalDateTime horarioInicial,
            List<String> hubs
    ) {
        Map<String, LocalDateTime> melhorChegada = new HashMap<>();
        Map<String, Voo> vooAnterior = new HashMap<>();

        PriorityQueue<EstadoRota> fila = new PriorityQueue<>(
                Comparator.comparing(EstadoRota::getHorarioChegada)
        );

        melhorChegada.put(origem, horarioInicial);
        fila.add(new EstadoRota(origem, horarioInicial));

        while (!fila.isEmpty()) {
            EstadoRota estadoAtual = fila.poll();
            String aeroportoAtual = estadoAtual.getAeroporto();
            LocalDateTime horarioAtual = estadoAtual.getHorarioChegada();

            if (aeroportoAtual.equals(destino)) break;

            // Descarta estados desatualizados (chegamos aqui por um caminho melhor antes)
            if (horarioAtual.isAfter(melhorChegada.get(aeroportoAtual))) continue;

            // Tempo mínimo de conexão: 0 na origem, 60 min em hubs, 45 min nos demais
            int tempoConexao;
            if (aeroportoAtual.equals(origem)) {
                tempoConexao = 0;
            } else if (hubs.contains(aeroportoAtual)) {
                tempoConexao = 60;
            } else {
                tempoConexao = 45;
            }

            LocalDateTime minimoPartida = horarioAtual.plusMinutes(tempoConexao);

            for (Voo voo : getVoosSaindo(aeroportoAtual)) {
                if (voo.getPartida().isBefore(minimoPartida)) continue;

                String proximo = voo.getDestino();
                LocalDateTime chegada = voo.getChegada();
                LocalDateTime melhorAtual = melhorChegada.get(proximo);

                if (melhorAtual == null || chegada.isBefore(melhorAtual)) {
                    melhorChegada.put(proximo, chegada);
                    vooAnterior.put(proximo, voo);
                    fila.add(new EstadoRota(proximo, chegada));
                }
            }
        }

        if (!vooAnterior.containsKey(destino)) {
            return new ArrayList<>();
        }

        // Reconstrói o caminho de trás pra frente
        List<Voo> rota = new ArrayList<>();
        String atual = destino;

        while (!atual.equals(origem)) {
            Voo voo = vooAnterior.get(atual);
            if (voo == null) return new ArrayList<>();
            rota.add(voo);
            atual = voo.getOrigem();
        }

        Collections.reverse(rota);
        return rota;
    }
}
