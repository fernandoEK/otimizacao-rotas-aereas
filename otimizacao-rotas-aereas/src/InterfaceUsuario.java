import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InterfaceUsuario {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static EntradaUsuario lerEntrada(Map<String, Aeroporto> aeroportos, List<String> hubs) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Origem (ICAO): ");
        String origem = scanner.nextLine().trim().toUpperCase();

        if (!aeroportos.containsKey(origem)) {
            System.out.println("Aeroporto de origem não encontrado: " + origem);
            return null;
        }

        System.out.print("Destino (ICAO): ");
        String destino = scanner.nextLine().trim().toUpperCase();

        if (!aeroportos.containsKey(destino)) {
            System.out.println("Aeroporto de destino não encontrado: " + destino);
            return null;
        }

        LocalDateTime horarioInicial = null;
        while (horarioInicial == null) {
            System.out.print("Data e hora de partida (dd/MM/yyyy HH:mm): ");
            String textoData = scanner.nextLine().trim();
            try {
                horarioInicial = LocalDateTime.parse(textoData, FORMATO);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use dd/MM/yyyy HH:mm (ex: 15/03/2026 08:30)");
            }
        }

        System.out.print("Deseja remover um dos hubs? (s/n): ");
        String resposta = scanner.nextLine().trim().toLowerCase();

        String hubRemovido = null;

        if (resposta.equals("s")) {
            System.out.println("Hubs disponíveis:");
            for (int i = 0; i < hubs.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + hubs.get(i));
            }

            System.out.print("Hub a remover (código ICAO): ");
            hubRemovido = scanner.nextLine().trim().toUpperCase();

            if (!hubs.contains(hubRemovido)) {
                System.out.println("Hub inválido. Nenhum hub será removido.");
                hubRemovido = null;
            }
        }

        return new EntradaUsuario(origem, destino, horarioInicial, hubRemovido);
    }
}
