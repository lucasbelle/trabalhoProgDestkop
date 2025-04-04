package view;

class TelaCadastroClientes extends TelaCadastro {
    public TelaCadastroClientes() {
        super("Cadastro de Clientes", new String[]{"Nome", "CPF", "Telefone", "Endereço", "E-mail", "Data de Nascimento", "Sexo"}, "clientes.txt");
    }
}
