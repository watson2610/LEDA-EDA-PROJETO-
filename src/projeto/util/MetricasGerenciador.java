package projeto.util;

import java.util.ArrayList;
import java.util.List;

public class MetricasGerenciador {
    private final List<Long> temposNanos = new ArrayList<>();

    public void adicionarTempo(long tempoNanos) {
        this.temposNanos.add(tempoNanos);
    }

    public double calcularMediaComWarmUpDescartado(int execucoesDescarte) {
        if (temposNanos.size() <= execucoesDescarte) {
            return 0.0;
        }

        long soma = 0;
        int contagem = 0;

        for (int i = execucoesDescarte; i < temposNanos.size(); i++) {
            soma += temposNanos.get(i);
            contagem++;
        }

        return (double) soma / contagem;
    }

    public void limpar() {
        this.temposNanos.clear();
    }
}
