package view;

class TelaCadastroFornecedores extends TelaCadastro {
    public TelaCadastroFornecedores() {
        super("Cadastro de Fornecedores", new String[]{"Nome da Empresa", "CNPJ", "Telefone", "E-mail", "Endereço", "Cidade", "Estado"}, "fornecedores.txt");
    }
}
