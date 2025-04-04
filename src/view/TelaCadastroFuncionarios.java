package view;

class TelaCadastroFuncionarios extends TelaCadastro {
    public TelaCadastroFuncionarios() {
        super("Cadastro de Funcionários", new String[]{"Nome", "Cargo", "Salário", "Data de Admissão", "CPF", "Telefone", "Endereço", "Cidade", "Estado"}, "funcionarios.txt");
    }
}
