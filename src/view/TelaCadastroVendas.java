package view;

class TelaCadastroVendas extends TelaCadastro {
    public TelaCadastroVendas() {
        super("Lançamento de Vendas", new String[]{"Cliente", "Produto", "Quantidade", "Data da Venda", "Forma de Pagamento", "Valor Total"}, "vendas.txt");
    }
}
