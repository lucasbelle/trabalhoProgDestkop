package view;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoSenha;

    public TelaLogin() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Usuário:"));
        campoUsuario = new JTextField();
        add(campoUsuario);

        add(new JLabel("Senha:"));
        campoSenha = new JPasswordField();
        add(campoSenha);

        JButton btnAcessar = new JButton("Acessar");
        btnAcessar.addActionListener(e -> verificarLogin());
        add(btnAcessar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        add(btnLimpar);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void verificarLogin() {
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());
        if (usuario.equals("admin") && senha.equals("1234")) {
            new TelaMenu();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoUsuario.setText("");
        campoSenha.setText("");
    }
}
