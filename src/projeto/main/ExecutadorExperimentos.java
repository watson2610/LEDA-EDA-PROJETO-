package projeto.main;

import projeto.util.GeradorDados;
import projeto.util.MetricasGerenciador;
import projeto.algoritmos.ordenacao.*;
import projeto.algoritmos.busca.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

public class ExecutadorExperimentos {

    private static final int TOTAL_EXECUCOES = 25;
    private static final int WARM_UP_DESCARTE = 5;
    private static final String NOME_ARQUIVO_CSV = "resultados.csv";
    private static final String[] CENARIOS_ORDENACAO = {
        "Aleatório", "Ordenado", "Ordem inversa", "Parcialmente ordenado (15%)"
    };

    public static void main(String[] args) {
        System.out.println("=== Iniciando Experimentos ===");

        inicializarArquivoCSV();

        int[] tamanhosComuns = {20000, 50000, 100000};

        OrdenacaoService quickRef = vetor -> System.arraycopy(QuickSort.sortJava(vetor), 0, vetor, 0, vetor.length);
        OrdenacaoService mergeClassico = vetor -> System.arraycopy(MergeSort.sort(vetor), 0, vetor, 0, vetor.length);
        OrdenacaoService quickSimples = vetor -> System.arraycopy(QuickSort.sortSimple(vetor), 0, vetor, 0, vetor.length);
        OrdenacaoService quickShuffle = vetor -> System.arraycopy(QuickSort.sortWithShuffle(vetor), 0, vetor, 0, vetor.length);
        OrdenacaoService countingSort = vetor -> System.arraycopy(CountingSort.sort(vetor), 0, vetor, 0, vetor.length);

        OrdenacaoService bubbleSimples = vetor -> System.arraycopy(BubbleSort.sortSimple(vetor), 0, vetor, 0, vetor.length);
        OrdenacaoService bubbleOtimizado = vetor -> System.arraycopy(BubbleSort.sortOptimized(vetor), 0, vetor, 0, vetor.length);
        OrdenacaoService selectionSimples = vetor -> System.arraycopy(SelectionSort.sortSlide(vetor), 0, vetor, 0, vetor.length);
        OrdenacaoService selectionEstavel = vetor -> System.arraycopy(SelectionSort.sortEstavel(vetor), 0, vetor, 0, vetor.length);
        OrdenacaoService insertionSort = vetor -> System.arraycopy(InsertionSort.sort(vetor), 0, vetor, 0, vetor.length);

        String[] nomesOrdenacao = {
            "Arrays.sort(Java)", "Merge Sort (Clássico)", "Quick Sort (Simples)",
            "Quick Sort (Shuffle)", "Counting Sort", "Insertion Sort",
            "Selection Sort (Simples)", "Selection Sort (Estável)",
            "Bubble Sort (Otimizado)", "Bubble Sort (Simples)"
        };
        OrdenacaoService[] algoritmosOrdenacao = {
            quickRef, mergeClassico, quickSimples, quickShuffle, countingSort,
            insertionSort, selectionSimples, selectionEstavel, bubbleOtimizado,
            bubbleSimples
        };

        System.out.println("\n--- Testando Algoritmos de Ordenação ---");
        executarTestesParaAlgoritmos(nomesOrdenacao, algoritmosOrdenacao, tamanhosComuns);

        System.out.println("\n--- Testando Algoritmos de Busca ---");
        BuscaService linearIterativa = (vetor, alvo) -> LinearSearch.iterative(vetor, alvo);
        BuscaService linearRecursiva = (vetor, alvo) -> LinearSearch.recursive(vetor, alvo);
        BuscaService binariaIterativa = (vetor, alvo) -> BinarySearch.iterative(vetor, alvo);
        BuscaService binariaRecursiva = (vetor, alvo) -> BinarySearch.recursive(vetor, alvo);
        BuscaService linearDuasPontas = (vetor, alvo) -> TwoPointersLinearSearch.search(vetor, alvo);

        String[] nomesBusca = {
            "Busca Linear Iterativa", "Busca Linear Recursiva",
            "Busca Linear Duas Pontas", "Busca Binária Iterativa",
            "Busca Binária Recursiva"
        };
        BuscaService[] algoritmosBusca = {
            linearIterativa, linearRecursiva, linearDuasPontas,
            binariaIterativa, binariaRecursiva
        };
        executarTestesBusca(nomesBusca, algoritmosBusca, tamanhosComuns);

        System.out.println("\n=== Experimentos finalizados! ===");
    }

    private static void inicializarArquivoCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOME_ARQUIVO_CSV, false))) {
            writer.println("Categoria,Algoritmo,Cenario,Tamanho,Tempo_ms");
        } catch (IOException e) {
            System.err.println("Erro ao criar o CSV: " + e.getMessage());
        }
    }

    private static void salvarResultadoNoCSV(String categoria, String algoritmo, String cenario, int tamanho, double tempoMs) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOME_ARQUIVO_CSV, true))) {
            writer.printf(Locale.US, "%s,%s,%s,n=%d,%.4f\n", categoria, algoritmo, cenario, tamanho, tempoMs);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no CSV: " + e.getMessage());
        }
    }

    public static void executarTestesParaAlgoritmo(String nomeAlgoritmo, OrdenacaoService algoritmo, int[] tamanhos) {
        for (int tamanho : tamanhos) {
            for (String cenario : CENARIOS_ORDENACAO) {
                int[][] entradas = gerarEntradas(cenario, tamanho);
                executarTestesParaAlgoritmo(nomeAlgoritmo, algoritmo, entradas, cenario, tamanho);
            }
        }
    }

    private static void executarTestesParaAlgoritmos(
            String[] nomes, OrdenacaoService[] algoritmos, int[] tamanhos) {
        for (int tamanho : tamanhos) {
            for (String cenario : CENARIOS_ORDENACAO) {
                int[][] entradas = gerarEntradas(cenario, tamanho);
                for (int i = 0; i < algoritmos.length; i++) {
                    executarTestesParaAlgoritmo(
                            nomes[i], algoritmos[i], entradas, cenario, tamanho);
                }
            }
        }
    }

    private static int[][] gerarEntradas(String cenario, int tamanho) {
        int[][] entradas = new int[TOTAL_EXECUCOES][];
        for (int i = 0; i < TOTAL_EXECUCOES; i++) {
            entradas[i] = obterVetorPorCenario(cenario, tamanho);
        }
        return entradas;
    }

    private static void executarTestesParaAlgoritmo(
            String nomeAlgoritmo, OrdenacaoService algoritmo, int[][] entradas,
            String cenario, int tamanho) {
        MetricasGerenciador metricas = new MetricasGerenciador();
        boolean ocorreuErro = false;

        for (int[] entrada : entradas) {
            int[] vetorTrabalho = entrada.clone();

            long tempoInicial = System.nanoTime();
            algoritmo.ordenar(vetorTrabalho);
            long tempoFinal = System.nanoTime();

            metricas.adicionarTempo(tempoFinal - tempoInicial);

            if (!isOrdenado(vetorTrabalho)) {
                ocorreuErro = true;
            }
        }

        if (ocorreuErro) {
            System.out.println("ERRO: " + nomeAlgoritmo + " falhou ao ordenar no cenário " + cenario
                    + ". Resultado não será registrado.");
            return;
        }

        double tempoMedioNanos = metricas.calcularMediaComWarmUpDescartado(WARM_UP_DESCARTE);
        double tempoMedioMs = tempoMedioNanos / 1_000_000.0;

        System.out.printf(Locale.US, "[Ordenação] %s | %s | n=%d | %.4f ms\n",
                nomeAlgoritmo, cenario, tamanho, tempoMedioMs);
        salvarResultadoNoCSV("Ordenacao", nomeAlgoritmo, cenario, tamanho, tempoMedioMs);
    }

    public static void executarTestesBusca(String nomeAlgoritmo, BuscaService algoritmo, int[] tamanhos) {
        executarTestesBusca(
                new String[] {nomeAlgoritmo},
                new BuscaService[] {algoritmo},
                tamanhos);
    }

    private static void executarTestesBusca(
            String[] nomes, BuscaService[] algoritmos, int[] tamanhos) {
        String[] cenariosBusca = {"Alvo presente", "Alvo ausente"};

        for (int tamanho : tamanhos) {
            int[][] entradas = gerarEntradas("Ordenado", tamanho);
            for (String cenarioBusca : cenariosBusca) {
                for (int i = 0; i < algoritmos.length; i++) {
                    executarTestesBusca(
                            nomes[i], algoritmos[i], entradas, tamanho, cenarioBusca);
                }
            }
        }
    }

    private static void executarTestesBusca(
            String nomeAlgoritmo, BuscaService algoritmo, int[][] entradas,
            int tamanho, String cenarioBusca) {
        MetricasGerenciador metricas = new MetricasGerenciador();
        boolean ocorreuErro = false;

        for (int[] vetor : entradas) {
            int alvo = cenarioBusca.equals("Alvo presente") ? vetor[vetor.length / 2] : -1;

            long tempoInicial = System.nanoTime();
            int indice = algoritmo.buscar(vetor, alvo);
            long tempoFinal = System.nanoTime();

            metricas.adicionarTempo(tempoFinal - tempoInicial);
            boolean resultadoValido = cenarioBusca.equals("Alvo presente")
                    ? indice >= 0 && indice < vetor.length && vetor[indice] == alvo
                    : indice == -1;
            if (!resultadoValido) {
                ocorreuErro = true;
            }
        }

        if (ocorreuErro) {
            System.out.println("ERRO: " + nomeAlgoritmo + " falhou no cenário " + cenarioBusca
                    + ". Resultado não será registrado.");
            return;
        }

        double tempoMedioNanos = metricas.calcularMediaComWarmUpDescartado(WARM_UP_DESCARTE);
        double tempoMedioMs = tempoMedioNanos / 1_000_000.0;

        System.out.printf(Locale.US, "[Busca] %s | %s | n=%d | %.4f ms\n",
                nomeAlgoritmo, cenarioBusca, tamanho, tempoMedioMs);
        salvarResultadoNoCSV("Busca", nomeAlgoritmo, "Vetor Ordenado (" + cenarioBusca + ")", tamanho, tempoMedioMs);
    }

    private static int[] obterVetorPorCenario(String cenario, int tamanho) {
        switch (cenario) {
            case "Aleatório": return GeradorDados.gerarAleatorios(tamanho);
            case "Ordenado": return GeradorDados.gerarOrdenados(tamanho);
            case "Ordem inversa": return GeradorDados.gerarInversos(tamanho);
            case "Parcialmente ordenado (15%)": return GeradorDados.gerarParcialmenteOrdenados(tamanho);
            default: throw new IllegalArgumentException("Cenário inválido");
        }
    }

    private static boolean isOrdenado(int[] arr) {
        for(int i=0; i<arr.length-1; i++) {
            if(arr[i] > arr[i+1]) return false;
        }
        return true;
    }
}
