package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.util.regex.Pattern;

abstract class TelaCadastro extends JFrame {
    protected DefaultTableModel modelo;
    protected JTable tabela;
    protected String arquivo;

    public TelaCadastro(String titulo, String[] colunas, String arquivo) {
        this.arquivo = arquivo;
        setTitle(titulo);
        setSize(600, 400);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        addBotao(painelBotoes, "Adicionar", e -> adicionarDados());
        addBotao(painelBotoes, "Editar", e -> editarDados());
        addBotao(painelBotoes, "Excluir", e -> excluirDados());
        add(painelBotoes, BorderLayout.SOUTH);

        carregarDados();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addBotao(JPanel painel, String titulo, java.awt.event.ActionListener action) {
        JButton botao = new JButton(titulo);
        botao.addActionListener(action);
        painel.add(botao);
    }

    protected void adicionarDados() {
        int colunas = modelo.getColumnCount();
        String[] novoRegistro = new String[colunas];
        for (int i = 0; i < colunas; i++) {
            String nomeColuna = modelo.getColumnName(i);
            JComponent campo = criarCampoFormatado(nomeColuna);
            Object[] mensagem = {"Digite " + nomeColuna + ":", campo};
            Object[] opcoes = {"Confirmar", "Cancelar"};

            int escolha = JOptionPane.showOptionDialog(this, mensagem, "Novo dado",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

            if (escolha == 0) {
                String valor = obterValorCampo(campo);
                if (valor.trim().isEmpty() || contemCaracterPlaceholder(valor) || !validarCampo(nomeColuna, valor)) {
                    JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos corretamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                novoRegistro[i] = valor;
            } else {
                return;
            }
        }
        modelo.addRow(novoRegistro);
        salvarDados();
    }

    protected void editarDados() {
        int linhaSelecionada = tabela.getSelectedRow();
        int colunaSelecionada = tabela.getSelectedColumn();
        if (linhaSelecionada != -1 && colunaSelecionada != -1) {
            String nomeColuna = modelo.getColumnName(colunaSelecionada);
            JComponent campo = criarCampoFormatado(nomeColuna);
            if (campo instanceof JFormattedTextField) {
                ((JFormattedTextField) campo).setText(modelo.getValueAt(linhaSelecionada, colunaSelecionada).toString());
            } else if (campo instanceof JTextField) {
                ((JTextField) campo).setText(modelo.getValueAt(linhaSelecionada, colunaSelecionada).toString());
            }

            Object[] mensagem = {"Novo valor para " + nomeColuna + ":", campo};
            Object[] opcoes = {"Confirmar", "Cancelar"};

            int escolha = JOptionPane.showOptionDialog(this, mensagem, "Editar Dados",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

            if (escolha == 0) {
                String valor = obterValorCampo(campo);
                if (valor.trim().isEmpty() || contemCaracterPlaceholder(valor) || !validarCampo(nomeColuna, valor)) {
                    JOptionPane.showMessageDialog(this, "O campo preenchido é inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                modelo.setValueAt(valor, linhaSelecionada, colunaSelecionada);
                salvarDados();
            }
        }
    }

    private JComponent criarCampoFormatado(String nomeColuna) {
        try {
            if (nomeColuna.equalsIgnoreCase("CPF")) {
                return new JFormattedTextField(new MaskFormatter("###.###.###-##"));
            } else if (nomeColuna.equalsIgnoreCase("Telefone")) {
                return new JFormattedTextField(new MaskFormatter("(##)#####-####"));
            } else if (nomeColuna.equalsIgnoreCase("CNPJ")) {
                return new JFormattedTextField(new MaskFormatter("##.###.###/####-##"));
            } else if (nomeColuna.toLowerCase().contains("data")) {
                return new JFormattedTextField(new MaskFormatter("##/##/####"));
            } else {
                return new JTextField();
            }
        } catch (ParseException e) {
            return new JTextField();
        }
    }

    private String obterValorCampo(JComponent campo) {
        if (campo instanceof JFormattedTextField) {
            return ((JFormattedTextField) campo).getText();
        } else if (campo instanceof JTextField) {
            return ((JTextField) campo).getText();
        }
        return "";
    }

    private boolean contemCaracterPlaceholder(String texto) {
        return texto.contains(" ") || texto.contains("_");
    }

    private boolean validarCampo(String nomeColuna, String valor) {
        if (nomeColuna.equalsIgnoreCase("CPF")) {
            return Pattern.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", valor);
        } else if (nomeColuna.equalsIgnoreCase("CNPJ")) {
            return Pattern.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", valor);
        } else if (nomeColuna.toLowerCase().contains("data")) {
            return Pattern.matches("\\d{2}/\\d{2}/\\d{4}", valor);
        }
        return true;
    }

    protected void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                for (int j = 0; j < modelo.getColumnCount(); j++) {
                    bw.write(modelo.getValueAt(i, j).toString() + ";");
                }
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void carregarDados() {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                modelo.addRow(linha.split(";"));
            }
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado, iniciando novo cadastro.");
        }
    }

    protected void excluirDados() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            modelo.removeRow(linhaSelecionada);
            salvarDados();
        }
    }
}
