import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeitorCSV {

    public static Map<String, Aeroporto> lerAeroportos(String arquivo) throws IOException {
        Map<String, Aeroporto> aeroportos = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine(); // pula cabeçalho

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";", -1);

                if (dados.length >= 9) {
                    Aeroporto aeroporto = new Aeroporto(
                            dados[0].trim(),
                            dados[1].trim(),
                            dados[2].trim(),
                            dados[3].trim(),
                            dados[4].trim(),
                            dados[5].trim(),
                            converterDouble(dados[7]),
                            converterDouble(dados[8])
                    );
                    aeroportos.put(aeroporto.getIcao(), aeroporto);
                }
            }
        }

        return aeroportos;
    }

    public static Map<String, CompanhiaAerea> lerCompanhias(String arquivo) throws IOException {
        Map<String, CompanhiaAerea> companhias = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine(); // pula cabeçalho

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";", -1);

                if (dados.length >= 4) {
                    CompanhiaAerea companhia = new CompanhiaAerea(
                            dados[0].trim(),
                            dados[1].trim(),
                            dados[2].trim(),
                            dados[3].trim()
                    );
                    companhias.put(companhia.getIcao(), companhia);
                }
            }
        }

        return companhias;
    }

    public static Map<String, Aeronave> lerAeronaves(String arquivo) throws IOException {
        Map<String, Aeronave> aeronaves = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine(); // pula cabeçalho

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";", -1);

                if (dados.length >= 4) {
                    Aeronave aeronave = new Aeronave(
                            dados[0].trim(),
                            dados[1].trim(),
                            dados[2].trim(),
                            dados[3].trim()
                    );
                    aeronaves.put(aeronave.getIcao(), aeronave);
                }
            }
        }

        return aeronaves;
    }

    public static List<Voo> lerVoos(String arquivo) throws IOException {
        List<Voo> voos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine(); // pula cabeçalho

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = parsearLinha(linha);

                if (dados.length >= 12) {
                    String chegadaTexto = dados[1].trim();
                    String partidaTexto = dados[2].trim();
                    String numeroVoo    = dados[5].trim();
                    String companhia    = dados[7].trim();
                    String aeronave     = dados[8].trim();
                    String destino      = dados[9].trim();
                    String origem       = dados[10].trim();

                    if (chegadaTexto.isEmpty() || partidaTexto.isEmpty()
                            || origem.isEmpty() || destino.isEmpty()) {
                        continue;
                    }

                    try {
                        LocalDateTime chegada = LocalDateTime.parse(chegadaTexto, formatter);
                        LocalDateTime partida = LocalDateTime.parse(partidaTexto, formatter);
                        voos.add(new Voo(origem, destino, partida, chegada, companhia, numeroVoo, aeronave));
                    } catch (DateTimeParseException e) {
                        // ignora linhas com data inválida
                    }
                }
            }
        }

        return voos;
    }

    // Lê uma linha CSV respeitando campos entre aspas (que podem conter vírgulas)
    private static String[] parsearLinha(String linha) {
        List<String> campos = new ArrayList<>();
        StringBuilder campo = new StringBuilder();
        boolean dentroDeAspas = false;

        for (char c : linha.toCharArray()) {
            if (c == '"') {
                dentroDeAspas = !dentroDeAspas;
            } else if (c == ',' && !dentroDeAspas) {
                campos.add(campo.toString());
                campo = new StringBuilder();
            } else {
                campo.append(c);
            }
        }

        campos.add(campo.toString()); // adiciona o último campo
        return campos.toArray(new String[0]);
    }

    private static double converterDouble(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(valor.trim().replace(",", "."));
    }
}
