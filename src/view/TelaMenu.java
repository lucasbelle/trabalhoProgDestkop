package view;

import javax.swing.*;
import java.awt.*;

class TelaMenu extends JFrame {
    public TelaMenu() {
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        addBotao("Cadastro de Clientes", TelaCadastroClientes.class);
        addBotao("Cadastro de Funcionários", TelaCadastroFuncionarios.class);
        addBotao("Lançamento de Vendas", TelaCadastroVendas.class);
        addBotao("Cadastro de Fornecedores", TelaCadastroFornecedores.class);
        addBotao("Cadastro de Produtos", TelaCadastroProdutos.class);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addBotao(String titulo, Class<? extends JFrame> telaClass) {
        JButton botao = new JButton(titulo);
        botao.addActionListener(e -> {
            try {
                telaClass.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        add(botao);
    }
}
