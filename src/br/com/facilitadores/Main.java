package br.com.facilitadores;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {

    static JFrame f;
    static JButton b;
    static JLabel l;
    static JTextArea jt;

    Main() {
    }

    public static void main(String[] args) {
        f = new JFrame("Alterar Consulta Livre Gerador de Relatórios");
        b = new JButton("converter");
        Main te = new Main();
        b.addActionListener(te);
        jt = new JTextArea(20, 20);
        jt.setText("Cole aqui a consulta sql");
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(b, BorderLayout.PAGE_START);
        JScrollPane scrollBar = new JScrollPane(jt);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        p.add(scrollBar, BorderLayout.CENTER);
        f.add(p);
        f.setSize(500, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("converter")) {
            String conteudo = jt.getText();
            String[] trechos = splitByNumber(conteudo, 1000);
            StringBuilder aux = new StringBuilder();
            aux.append("update in4gr_relatorios set texto_sql = ");
            for (int i = 0; i < trechos.length; i++) {
                aux.append("to_clob('" + trechos[i].replaceAll("'", "''") + "')");
                aux.append("||");
            }
            aux.append(" where id = ;");
            System.out.println(aux.toString());

            StringSelection stringSelection = new StringSelection(aux.toString());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }

    }

    private String[] splitByNumber(String s, int size) {
        if (s == null || size <= 0)
            return null;
        int chunks = s.length() / size + ((s.length() % size > 0) ? 1 : 0);
        String[] arr = new String[chunks];
        for (int i = 0, j = 0, l = s.length(); i < l; i += size, j++)
            arr[j] = s.substring(i, Math.min(l, i + size));
        return arr;
    }
}
