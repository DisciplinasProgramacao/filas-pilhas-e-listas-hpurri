public class App {
    public static void main(String[] args) {
        // método principal vazio por enquanto. Você pode chamar métodos de teste aqui.
    }

    /**
     * Permite ao usuário criar um pedido informando produtos e, em seguida,
     * digitar a descrição de um produto para verificar quantas repetições
     * desse produto existem no pedido (usando Lista.contarRepeticoes).
     */
    public static void repeticoesDeProdutoNoPedido(){
        java.util.Scanner sc = new java.util.Scanner(System.in);
        Pedido pedido = new Pedido();
        System.out.println("Crie um pedido. Para cada produto informe descrição; para terminar digite 'fim'.");
        while(true){
            System.out.print("Descrição (ou fim): ");
            String desc = sc.nextLine();
            if(desc == null) break;
            if(desc.equalsIgnoreCase("fim")) break;
            System.out.print("Preço de custo (ex: 10.5): ");
            String precoStr = sc.nextLine();
            double preco = 0.01;
            try{ preco = Double.parseDouble(precoStr); } catch(Exception e){ /* mantém 0.01 */ }
            Produto p = new Produto(desc, preco);
            pedido.adicionarProduto(p);
        }

        System.out.print("Informe a descrição do produto a contar: ");
        String alvo = sc.nextLine();
        int repeticoes = pedido.contarPorDescricao(alvo);
        System.out.println("Produto '" + alvo + "' aparece " + repeticoes + " vez(es) no pedido.");
    }
}
