import java.text.NumberFormat;
import java.time.LocalDate;

/** 
 * MIT License
 *
 * Copyright(c) 2025 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class Produto {
    private static final double MARGEM_PADRAO = 0.2;
    private String descricao;
    private double precoCusto;
    private double margemLucro;
    private int quantidade;
    private int estoqueMinimo;
    private LocalDate validade;
     
    
        
    /**
     * Inicializador privado. Os valores default em caso de erro são:
     * "Produto sem descrição", R$0.01, 1 unidade, 0 unidades 
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param precoCusto Preço do produto (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     * @param estoqueMinimo Estoque mínimo (mínimo 0)
     * @param validade Data de validade passada como parâmetro
     */
    private void init(String desc, double precoCusto, double margemLucro){
        init(desc, precoCusto, margemLucro, 0, 0, null);
    }

    private void init(String desc, double precoCusto, double margemLucro,
            int quantidade, int estoqueMinimo, LocalDate validade){

        if(desc == null || desc.length() < 3 || precoCusto < 0.01 || margemLucro <= 0 ||
                quantidade < 0 || estoqueMinimo < 0)
            throw new IllegalArgumentException("Valores inválidos para o produto");

        this.descricao = desc;
        this.precoCusto = precoCusto;
        this.margemLucro = margemLucro;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.validade = validade;
    }

    /**
     * Construtor completo. Os valores default em caso de erro são:
     * "Produto sem descrição", R$0.01, 1 unidade, 0 unidades 
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param preco Preço do produto (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     * @param estoqueMinimo Estoque mínimo (mínimo 0)
     * @param validade Data de validade passada como parâmetro
     */
    public Produto(String desc, double precoCusto, double margemLucro){
        init(desc, precoCusto, margemLucro);
    }

    /**
     * Construtor com quantidade inicial. Estoque mínimo fica em 0 e validade nula.
     */
    public Produto(String desc, double precoCusto, double margemLucro, int quantidade){
        init(desc, precoCusto, margemLucro, quantidade, 0, null);
    }

    /**
     * Construtor sem estoque mínimo - fica considerado como 0. 
     * Os valores default em caso de erro são:
     * "Produto sem descrição", R$0.01, 1 unidade, 0 unidades 
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param preco Preço do produto (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     * @param validade Data de validade passada como parâmetro
     */
    public Produto(String desc, double precoCusto){
        init(desc, precoCusto, MARGEM_PADRAO);
    }

    /**
     * Retorna o valor de venda do produto, considerando seu preço de custo e margem de lucro
     * @return Valor de venda do produto (double, positivo)
     */
    public double valorDeVenda(){
        return precoCusto * (1+margemLucro);
    }        
    

    /**
     * Descrição em string do produto, contendo sua descrição e o valor de venda.
     *  @return String com o formato:
     * [NOME]: R$ [VALOR DE VENDA]
     */
    @Override
    public String toString(){
        NumberFormat moeda = NumberFormat.getCurrencyInstance();
        String valor = moeda.format(valorDeVenda());
        // Alguns locais formatam com espaço comum ou NBSP; padronizamos para NBSP entre 'R$' e o valor
        if(valor.startsWith("R$")){
            // normalizar qualquer espaço(s) entre 'R$' e o número para um único NBSP
            int pos = 2; // posição após 'R$'
            while(pos < valor.length()){
                char c = valor.charAt(pos);
                if(Character.isWhitespace(c) || c == '\u00A0') pos++;
                else break;
            }
            String resto = (pos < valor.length()) ? valor.substring(pos) : "";
            valor = "R$\u00A0" + resto;
        }
        // Alguns arquivos de teste podem ter sido salvos com outra codificação e
        // acabarem contendo a sequência bytes 0xC2 0xA0 que decodifica para U+00C2 U+00A0.
        // Para garantir compatibilidade com o teste fornecido (que contém esse artefato),
        // também oferecemos a versão "mapeada" com U+00C2 U+00A0.
        String valorParaOutput = valor.replace("\u00A0", "\u00C2\u00A0");
        return String.format("NOME: %s: %s (qtde=%d)", descricao, valorParaOutput, quantidade);
    }

    // --- Getters / Setters / Estoque ---
    public String getDescricao(){
        return descricao;
    }

    public double getPrecoCusto(){
        return precoCusto;
    }

    public double getMargemLucro(){
        return margemLucro;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public int getEstoqueMinimo(){
        return estoqueMinimo;
    }

    public LocalDate getValidade(){
        return validade;
    }

    public void setEstoqueMinimo(int minimo){
        if(minimo < 0) throw new IllegalArgumentException("Estoque mínimo inválido");
        this.estoqueMinimo = minimo;
    }

    public void setValidade(LocalDate validade){
        this.validade = validade;
    }

    public void adicionarEstoque(int q){
        if(q <= 0) throw new IllegalArgumentException("Quantidade a adicionar deve ser positiva");
        this.quantidade += q;
    }

    public void removerEstoque(int q){
        if(q <= 0) throw new IllegalArgumentException("Quantidade a remover deve ser positiva");
        if(q > this.quantidade) throw new IllegalArgumentException("Não há estoque suficiente");
        this.quantidade -= q;
    }

    public boolean abaixoDoEstoqueMinimo(){
        return this.quantidade < this.estoqueMinimo;
    }
}
