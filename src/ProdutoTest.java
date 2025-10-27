import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProdutoTest {

    static Produto produto;
        
    
    @BeforeAll
    static public void prepare(){
        produto = new Produto("Produto teste", 100, 0.1);
    }
    
    @Test
    public void calculaPrecoCorretamente(){
        assertEquals(110.0, produto.valorDeVenda(), 0.01);
    }

    @Test
    public void stringComDescricaoEValor(){
        String desc = produto.toString();
        assertTrue(desc.contains("Produto teste") && desc.contains("R$ 110,00"));
    }

    @Test
    public void naoCriaProdutoComPrecoNegativo(){
        assertThrows(IllegalArgumentException.class, () -> new Produto("teste", -5, 0.5));
    }
    
    @Test
    public void naoCriaProdutoComMargemNegativa(){
        assertThrows(IllegalArgumentException.class, () -> new Produto("teste", 5, -1));
    }

    @Test
    public void gerenciaEstoque(){
        Produto p = new Produto("Produto E", 10.0);
        assertEquals(0, p.getQuantidade());
        p.adicionarEstoque(5);
        assertEquals(5, p.getQuantidade());
        p.removerEstoque(3);
        assertEquals(2, p.getQuantidade());
    }

    @Test
    public void naoRemoveMaisQueExiste(){
        Produto p = new Produto("Produto F", 5.0);
        p.adicionarEstoque(2);
        assertThrows(IllegalArgumentException.class, () -> p.removerEstoque(3));
    }

    @Test
    public void abaixoDoEstoqueMinimoFunciona(){
        Produto p = new Produto("Produto G", 2.0);
        p.adicionarEstoque(2);
        p.setEstoqueMinimo(5);
        assertTrue(p.abaixoDoEstoqueMinimo());
    }
}
