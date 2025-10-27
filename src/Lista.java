import java.util.function.Predicate;

/**
 * Implementação simples de lista simplesmente encadeada genérica com algumas
 * operações úteis para a atividade.
 */
public class Lista<E> {
    private static class Celula<E> {
        E elemento;
        Celula<E> prox;
        Celula(E elemento){ this.elemento = elemento; }
    }

    private Celula<E> primeiro;
    private Celula<E> ultimo;
    private int tamanho;

    public Lista(){
        primeiro = ultimo = null;
        tamanho = 0;
    }

    public boolean vazia(){
        return tamanho == 0;
    }

    public int tamanho(){
        return tamanho;
    }

    public void inserirFim(E elem){
        Celula<E> nova = new Celula<>(elem);
        if(vazia()){
            primeiro = ultimo = nova;
        } else {
            ultimo.prox = nova;
            ultimo = nova;
        }
        tamanho++;
    }

    public void inserirInicio(E elem){
        Celula<E> nova = new Celula<>(elem);
        if(vazia()){
            primeiro = ultimo = nova;
        } else {
            nova.prox = primeiro;
            primeiro = nova;
        }
        tamanho++;
    }

    /**
     * Localiza o primeiro elemento que satisfaz o predicado. Retorna o elemento ou null
     * se não encontrado.
     */
    public E localizar(Predicate<E> cond){
        for(Celula<E> p = primeiro; p != null; p = p.prox){
            if(cond.test(p.elemento)) return p.elemento;
        }
        return null;
    }

    /**
     * Conta quantas entradas na lista satisfazem o predicado condicional.
     * Retorna 0 se a lista estiver vazia.
     */
    public int contarRepeticoes(Predicate<E> condicional){
        if(vazia()) return 0;
        int cnt = 0;
        for(Celula<E> p = primeiro; p != null; p = p.prox){
            if(condicional.test(p.elemento)) cnt++;
        }
        return cnt;
    }

    /**
     * Remove a primeira ocorrência que satisfaz o predicado. Retorna true se removeu.
     */
    public boolean remover(Predicate<E> cond){
        Celula<E> prev = null;
        for(Celula<E> p = primeiro; p != null; p = p.prox){
            if(cond.test(p.elemento)){
                if(prev == null){ // primeiro
                    primeiro = p.prox;
                    if(primeiro == null) ultimo = null;
                } else {
                    prev.prox = p.prox;
                    if(prev.prox == null) ultimo = prev;
                }
                tamanho--;
                return true;
            }
            prev = p;
        }
        return false;
    }

    /**
     * Itera sobre elementos e aplica um visitor simples (usando toString) para compor saída.
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for(Celula<E> p = primeiro; p != null; p = p.prox){
            if(!first) sb.append(", ");
            sb.append(p.elemento == null ? "null" : p.elemento.toString());
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }
}
