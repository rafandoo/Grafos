import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Aresta {
    int origem;
    int destino;
    int peso;

    public Aresta(int origem, int destino, int peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }
}

public class ListaAdjacencia {
    private int numVertices;
    private List<List<Aresta>> adj;
    private boolean direcionado;

    public ListaAdjacencia(int numVertices, boolean direcionado) {
        this.numVertices = numVertices;
        this.direcionado = direcionado;
        this.adj = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void adicionaAresta(int origem, int destino, int peso) {
        Aresta aresta = new Aresta(origem, destino, peso);
        adj.get(origem).add(aresta);
        if (!direcionado) {
            adj.get(destino).add(new Aresta(destino, origem, peso));
        }
    }

    public void adicionaAresta(int origem, int destino) {
        adicionaAresta(origem, destino, 1);
    }

    public void mostrarListaAdjacencias() {
        for (int i = 0; i < numVertices; i++) {
            System.out.print("Vertice: " + i + " -> ");
            for (Aresta aresta : adj.get(i)) {
                System.out.print("(Destino:" + aresta.destino + ", Peso:" + aresta.peso + ") ");
            }
            System.out.println();
        }
    }

    public void removerAresta(int origem, int destino) {
        removerAresta(adj.get(origem), destino);
        if (!direcionado) {
            removerAresta(adj.get(destino), origem);
        }
    }

    private void removerAresta(List<Aresta> arestas, int destino) {
        arestas.removeIf(aresta -> aresta.destino == destino);
    }

    public boolean ehAdjacente(int origem, int destino) {
        for (Aresta aresta : adj.get(origem)) {
            if (aresta.destino == destino) {
                return true;
            }
        }
        return false;
    }

    public void mostrarArestasAdjacentes(int origem) {
        System.out.print("\nArestas adjacentes ao vertice " + origem + ": ");
        for (Aresta aresta : adj.get(origem)) {
            System.out.print("(Destino:" + aresta.destino + ", Peso:" + aresta.peso + ") ");
        }
        System.out.println();
    }

    public void removerVertice(int vertice) {
        if (vertice < 0 || vertice >= numVertices) {
            System.out.println("Vértice inválido");
            return;
        }

        List<Aresta> arestasVertice = adj.get(vertice);
        for (List<Aresta> arestas : adj) {
            arestas.removeIf(aresta -> aresta.destino == vertice);
        }

        adj.remove(vertice);
        numVertices--;

        for (int i = vertice; i < numVertices; i++) {
            for (Aresta aresta : adj.get(i)) {
                if (aresta.destino > vertice) {
                    aresta.destino--;
                }
            }
        }
    }

    public boolean ehConexo() {
        if (numVertices == 0) {
            return true;
        }

        boolean[] visitado = new boolean[numVertices];
        dfs(0, visitado);

        for (boolean v : visitado) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    private void dfs(int vertice, boolean[] visitado) {
        visitado[vertice] = true;

        for (Aresta aresta : adj.get(vertice)) {
            if (!visitado[aresta.destino]) {
                dfs(aresta.destino, visitado);
            }
        }
    }

    public boolean ehCompleto() {
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i != j) {
                    if (!ehAdjacente(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void dijkstra(int origem, int destino) {
        int[] distancias = new int[numVertices];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        distancias[origem] = 0;

        PriorityQueue<Node> filaPrioridade = new PriorityQueue<>();
        filaPrioridade.add(new Node(origem, 0));

        while (!filaPrioridade.isEmpty()) {
            Node atual = filaPrioridade.poll();

            if (atual.vertice == destino) {
                System.out.println("Distância mínima de " + origem + " para " + destino + ": " + distancias[destino]);
                return;
            }

            for (Aresta aresta : adj.get(atual.vertice)) {
                int novaDistancia = distancias[atual.vertice] + aresta.peso;

                if (novaDistancia < distancias[aresta.destino]) {
                    distancias[aresta.destino] = novaDistancia;
                    filaPrioridade.add(new Node(aresta.destino, novaDistancia));
                }
            }
        }

        System.out.println("Não há caminho de " + origem + " para " + destino);
    }

    private static class Node implements Comparable<Node> {
        int vertice;
        int distancia;

        public Node(int vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distancia, other.distancia);
        }
    }

    public boolean ehEuleriano() {
        if (!ehConexo()) {
            return false; // Se o grafo não é conexo, não pode ser euleriano
        }

        for (int i = 0; i < numVertices; i++) {
            if (adj.get(i).size() % 2 != 0) {
                return false; // Se algum vértice tiver grau ímpar, não é euleriano
            }
        }

        return true;
    }

    public boolean ehSemiEuleriano() {
        if (!ehConexo()) {
            return false; // Se o grafo não é conexo, não pode ser semi-euleriano
        }

        int contadorGrauImpar = 0;
        for (int i = 0; i < numVertices; i++) {
            if (adj.get(i).size() % 2 != 0) {
                contadorGrauImpar++;
            }
        }

        return contadorGrauImpar == 2; // Deve haver exatamente dois vértices de grau ímpar
    }

    public boolean naoEhEuleriano() {
        return !ehEuleriano() && !ehSemiEuleriano();
    }

    public boolean ehHamiltoniano() {
        if (!ehConexo()) {
            return false; // Se o grafo não é conexo, não pode ser hamiltoniano
        }

        int[] visita = new int[numVertices];
        return existeCicloHamiltoniano(0, 0, visita);
    }

    private boolean existeCicloHamiltoniano(int vertice, int contador, int[] visita) {
        visita[vertice] = contador++;

        if (contador == numVertices) {
            return true; // Encontrou um ciclo hamiltoniano
        }

        for (Aresta aresta : adj.get(vertice)) {
            if (visita[aresta.destino] == 0) {
                if (existeCicloHamiltoniano(aresta.destino, contador, visita)) {
                    return true;
                }
            }
        }

        visita[vertice] = 0;
        return false;
    }

    public boolean ehSemiHamiltoniano(int origem, int destino) {
        if (!ehConexo()) {
            return false; // Se o grafo não é conexo, não pode ser semi-hamiltoniano
        }

        int[] visita = new int[numVertices];
        return existeCaminhoHamiltoniano(origem, destino, 0, visita);
    }

    private boolean existeCaminhoHamiltoniano(int atual, int destino, int contador, int[] visita) {
        visita[atual] = contador++;

        if (atual == destino) {
            return contador == numVertices; // Verifica se o caminho visita todos os vértices
        }

        for (Aresta aresta : adj.get(atual)) {
            if (visita[aresta.destino] == 0) {
                if (existeCaminhoHamiltoniano(aresta.destino, destino, contador, visita)) {
                    return true;
                }
            }
        }

        visita[atual] = 0;
        return false;
    }

}