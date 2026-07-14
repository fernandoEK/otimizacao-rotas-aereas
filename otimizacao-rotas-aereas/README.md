# Otimização de Rotas no Espaço Aéreo Brasileiro

Aplicação de linha de comando em Java que encontra a **melhor rota entre dois aeroportos**
a partir de dados reais de voos, representando aeroportos e voos como um **grafo** e usando
o algoritmo de **Dijkstra** para minimizar o horário de chegada ao destino.

> Trabalho acadêmico desenvolvido na disciplina **Algoritmos e Estruturas de Dados II** — Engenharia de Software, PUCRS.

## O problema

Dada uma malha aérea com centenas de aeroportos e voos (com horários de partida e chegada),
qual é a sequência de voos que leva um passageiro de um aeroporto de origem até um destino
**chegando o mais cedo possível**, respeitando os tempos mínimos de conexão entre voos?

## Funcionalidades

- Leitura de dados reais de aeroportos, companhias, aeronaves e voos a partir de arquivos CSV.
- Construção de um grafo direcionado onde os vértices são aeroportos e as arestas são voos.
- Busca da melhor rota com o algoritmo de Dijkstra, considerando o horário de chegada e
  tempos mínimos de conexão diferentes para hubs e demais aeroportos.
- Cálculo dos **5 principais hubs nacionais** por grau de conectividade.
- Simulação da **remoção de um hub** para observar o impacto nas rotas.

## Tecnologias

- Java (Java puro, sem frameworks)
- Estruturas de dados: grafo, `HashMap`, `PriorityQueue`
- Leitura de arquivos CSV

## Como executar

Pré-requisito: JDK 11 ou superior instalado.

```bash
# a partir da raiz do projeto (onde ficam as pastas src/ e dados/)
javac -d bin src/*.java
java -cp bin Main
```

> Importante: rode o programa a partir da raiz do projeto, pois os arquivos em `dados/`
> são lidos por caminho relativo.

## Estrutura do projeto

```
.
├── src/          # código-fonte Java
├── dados/        # arquivos CSV com aeroportos, voos, companhias e aeronaves
└── README.md
```

## Equipe e minha contribuição

Trabalho em grupo com Kauã de Lima Martins e Matheus Borges de Toledo Guerra.

**Minha contribuição:** implementação do algoritmo de busca de rotas (Dijkstra) na classe `GrafoAereo`.
