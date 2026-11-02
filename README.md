# Algoritmos de Ordenação e Busca

Projeto acadêmico da disciplina de **Estrutura de Dados**, voltado à implementação e à comparação experimental de algoritmos de ordenação e busca.

## Conteúdo

O projeto reúne implementações de:

- **Ordenação:** Bubble Sort simples e otimizado, Counting Sort, Insertion Sort, Merge Sort, Quick Sort simples e com embaralhamento, Selection Sort simples e estável, além de `Arrays.sort` como referência.
- **Busca:** busca linear iterativa e recursiva, busca linear por duas pontas e busca binária iterativa e recursiva.

Os experimentos de ordenação usam vetores aleatórios, ordenados, em ordem inversa e parcialmente ordenados. Neste último cenário, o vetor parte de uma ordenação crescente e cerca de 15% das posições são selecionadas e embaralhadas. As buscas são executadas em vetores ordenados, com alvos presentes e ausentes.

Para cada combinação, o executor realiza 25 execuções, descarta as cinco primeiras como aquecimento da JVM e calcula a média das 20 restantes. Os algoritmos de ordenação recebem cópias das mesmas entradas, e a verificação do resultado é feita fora do intervalo cronometrado. Resultados incorretos não são gravados.

## Estrutura

```text
src/
└── projeto/
    ├── algoritmos/
    │   ├── busca/
    │   └── ordenacao/
    ├── main/
    └── util/
Untitled.ipynb
resultados.csv
```

- `src/projeto/algoritmos/`: implementações e interfaces dos algoritmos.
- `src/projeto/main/ExecutadorExperimentos.java`: execução e registro dos experimentos.
- `src/projeto/util/`: geração de entradas e cálculo das métricas.
- `resultados.csv`: resultados exportados pelo executor.
- `Untitled.ipynb`: notebook para análise tabular dos resultados.

## Requisitos

- JDK 21, conforme a configuração do projeto IntelliJ.
- Jupyter Notebook e Python com `pandas` e `jinja2` para executar o notebook.

## Executar os experimentos

Compile e execute a partir da raiz do repositório:

```powershell
New-Item -ItemType Directory -Force out
javac -encoding UTF-8 -d out (Get-ChildItem .\src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out projeto.main.ExecutadorExperimentos
```

O executor grava os resultados em `resultados.csv` na pasta atual. **A execução substitui o conteúdo existente desse arquivo.**

## Analisar os resultados

Abra `Untitled.ipynb` no Jupyter ou no VS Code, selecione um kernel Python com as dependências instaladas e execute as células. O notebook lê `resultados.csv` usando o caminho relativo à pasta de trabalho atual.

## Observação sobre desempenho

Os tempos são medições locais e podem variar conforme hardware, versão da JVM e carga do sistema. Use-os para comparação nas condições do experimento, não como valores absolutos de desempenho.
