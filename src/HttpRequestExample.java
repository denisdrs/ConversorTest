import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.Scanner; // Importe Scanner para entrada do usuário

public class HttpRequestExample {
    public static void main(String[] args) {
        // Crie um HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Crie uma URI para a API que você deseja acessar
        URI uri = URI.create("https://v6.exchangerate-api.com/v6/bcaf7dc4a8e07440ff214ec4/latest/USD");

        // Crie uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            // Envie a solicitação e obtenha a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Imprima o código de status da resposta
            System.out.println("Status code: " + response.statusCode());

            // Imprima o corpo da resposta
            System.out.println("Response body: " + response.body());

            // Crie um Scanner para entrada do usuário
            Scanner scanner = new Scanner(System.in);

            // Solicite ao usuário a moeda de origem e o valor a ser convertido
            System.out.println("Digite a moeda de origem (ex: USD): ");
            String moedaOrigem = scanner.next();
            System.out.println("Digite o valor a ser convertido: ");
            double valorOrigem = scanner.nextDouble();

            // Solicite ao usuário a moeda de destino
            System.out.println("Digite a moeda de destino (ex: EUR): ");
            String moedaDestino = scanner.next();

            // Realize a conversão utilizando as taxas obtidas da API
            double taxaOrigem = 1.0; // Por padrão, taxa 1 para a moeda de origem
            double taxaDestino = Double.parseDouble(response.body().split("\"" + moedaDestino + "\":")[1].split(",")[0]);
            double valorConvertido = converterMoedas(valorOrigem, taxaOrigem, taxaDestino);

            // Exiba o resultado da conversão formatado com duas casas decimais
            System.out.println("Valor convertido de " + moedaOrigem + " para " + moedaDestino + ": " + String.format("%.2f", valorConvertido));

            // Feche o scanner
            scanner.close();

        } catch (IOException | InterruptedException e) {
            // Lidar com exceções de E/S e interrupções
            e.printStackTrace();
        }
    }

    // Método para converter o valor de uma moeda para outra usando as taxas de câmbio
    public static double converterMoedas(double valorOrigem, double taxaOrigem, double taxaDestino) {
        // Calcula o valor convertido
        return (valorOrigem / taxaOrigem) * taxaDestino;
    }
}
