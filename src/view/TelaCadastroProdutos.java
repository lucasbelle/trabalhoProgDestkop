package view;

class TelaCadastroProdutos extends TelaCadastro {
    public TelaCadastroProdutos() {
        super("Cadastro de Produtos", new String[]{"Nome", "Código", "Preço", "Quantidade em Estoque", "Categoria", "Fornecedor", "Data de Validade"}, "produtos.txt");
    }
}
