package projeto.util;

import java.util.Random;
import java.util.Arrays;

public class GeradorDados {
    private static final Random random = new Random();
    private static final int MIN_VAL = 1;
    private static final int MAX_VAL = 100000;

    public static int[] gerarAleatorios(int tamanho) {
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = MIN_VAL + random.nextInt(MAX_VAL - MIN_VAL + 1);
        }
        return vetor;
    }

    public static int[] gerarOrdenados(int tamanho) {
        int[] vetor = gerarAleatorios(tamanho);
        Arrays.sort(vetor);
        return vetor;
    }

    public static int[] gerarInversos(int tamanho) {
        int[] vetor = gerarOrdenados(tamanho);
        int n = vetor.length;
        for (int i = 0; i < n / 2; i++) {
            int temp = vetor[i];
            vetor[i] = vetor[n - 1 - i];
            vetor[n - 1 - i] = temp;
        }
        return vetor;
    }

    public static int[] gerarParcialmenteOrdenados(int tamanho) {
        int[] vetor = gerarOrdenados(tamanho);
        int qtdEmbaralhar = (int) (tamanho * 0.15); // 15% das posições

        // Array de índices para sortear
        int[] indices = new int[tamanho];
        for (int i = 0; i < tamanho; i++) indices[i] = i;

        // Fisher-Yates parcial para pegar 15% de índices aleatórios
        for (int i = 0; i < qtdEmbaralhar; i++) {
            int j = i + random.nextInt(tamanho - i);
            int temp = indices[i];
            indices[i] = indices[j];
            indices[j] = temp;
        }

        int[] valores = new int[qtdEmbaralhar];
        for (int i = 0; i < qtdEmbaralhar; i++) {
            valores[i] = vetor[indices[i]];
        }

        for (int i = qtdEmbaralhar - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = valores[i];
            valores[i] = valores[j];
            valores[j] = temp;
        }

        for (int i = 0; i < qtdEmbaralhar; i++) {
            vetor[indices[i]] = valores[i];
        }

        return vetor;
    }
}
