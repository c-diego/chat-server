package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import server.Cliente;

/**
 *
 * @author diego
 */
public class Tela extends JFrame {

    private static Cliente client = new Cliente("127.0.0.1", 5090);
    Font font = new Font("Ubuntu Mono", Font.PLAIN, 16);
    JPanel inputPanel = new JPanel();
    JPanel outputPanel = new JPanel();
    JLabel inputLabel = new JLabel();
    static JTextArea output = new JTextArea();
    JFrame frame = new JFrame("Client");
    JTextField input = new JTextField();

    public Tela() {

        inputPanel.setBackground(Color.BLACK);
        inputPanel.setBounds(0, 350, 400, 100);
        inputPanel.setLayout(null);

        inputLabel.setText("Input:");
        inputLabel.setBounds(5, 30, 50, 16);
        inputLabel.setForeground(Color.GREEN);
        inputLabel.setFont(font);

        input.setBounds(55, 30, 335, 16);
        input.setFont(font);
        input.setForeground(Color.GREEN);
        input.setBackground(Color.BLACK);
        input.setBorder(null);
        input.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.send(input.getText());
                input.setText("");
            }
        }));

        inputPanel.add(inputLabel);
        inputPanel.add(input);

        outputPanel.setBackground(Color.BLACK);
        outputPanel.setBounds(0, 0, 400, 350);
        outputPanel.setLayout(null);

        output.setBounds(5, 0, 400, 400);
        output.setFont(font);
        output.setForeground(Color.GREEN);
        output.setBackground(Color.BLACK);
        output.setBorder(null);
        output.setEditable(false);

        outputPanel.add(output);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(400, 450);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(inputPanel);
        frame.add(outputPanel);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                input.requestFocus();
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tela tela = new Tela();
        });

        while (client.in.hasNextLine()) {
            output.append(System.lineSeparator() +  client.in.nextLine());
        }
    }

}
