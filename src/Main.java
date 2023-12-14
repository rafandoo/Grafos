
public class Main {
    public static void main(String[] args) {
        ListaAdjacencia grafo = new ListaAdjacencia(36, false);

        grafo.adicionaAresta(1, 2, 80);
        grafo.adicionaAresta(1, 10, 50);
        grafo.adicionaAresta(2, 3, 70);
        grafo.adicionaAresta(2, 10, 50);
        grafo.adicionaAresta(2, 11, 120);
        grafo.adicionaAresta(3, 4, 80);
        grafo.adicionaAresta(3, 12, 100);
        grafo.adicionaAresta(4, 5, 70);
        grafo.adicionaAresta(4, 12, 80);
        grafo.adicionaAresta(4, 14, 160);
        grafo.adicionaAresta(4, 15, 150);
        grafo.adicionaAresta(5, 6, 130);
        grafo.adicionaAresta(6, 9, 70);
        grafo.adicionaAresta(6, 17, 150);
        grafo.adicionaAresta(6, 8, 50);
        grafo.adicionaAresta(7, 8, 250);
        grafo.adicionaAresta(7, 18, 170);
        grafo.adicionaAresta(8, 19, 135);
        grafo.adicionaAresta(9, 15, 200);
        grafo.adicionaAresta(9, 16, 150);
        grafo.adicionaAresta(9, 17, 120);
        grafo.adicionaAresta(10, 11, 100);
        grafo.adicionaAresta(11, 13, 40);
        grafo.adicionaAresta(11, 12, 110);
        grafo.adicionaAresta(11, 14, 150);
        grafo.adicionaAresta(12, 14, 80);
        grafo.adicionaAresta(13, 14, 200);
        grafo.adicionaAresta(14, 15, 100);
        grafo.adicionaAresta(15, 16, 80);
        grafo.adicionaAresta(16, 17, 100);
        grafo.adicionaAresta(17, 18, 80);

        grafo.dijkstra(6, 10);
    }
}
