package view;

import controller.Client;
import controller.SyncController;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TelaPrincipalView extends JFrame {

    private JTextField txtAccessKey, txtSecretKey, txtRegion, txtBucket;
    private JButton btnSynconize;
    JFrame jFrame;

    public TelaPrincipalView() {

        setTitle("Sincronizador");
        setSize(800, 600);
        setLayout(null);
        jFrame = this;

        txtAccessKey = new JTextField();
        txtSecretKey = new JTextField();
        txtRegion = new JTextField();
        txtBucket = new JTextField();
        btnSynconize = new JButton("Sync");
        btnSynconize.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Client client = new Client();
                client.setAccessKey(txtAccessKey.getText());
                client.setSecretKey(txtSecretKey.getText());
                client.setRegion(txtRegion.getText());
                client.setBucket(txtBucket.getText());
                new SyncController(client);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addComponent(txtAccessKey, 200, 40, 10, 10);
        addComponent(txtSecretKey, 200, 40, 10, 60);
        addComponent(txtRegion, 200, 40, 10, 120);
        addComponent(txtBucket, 200, 40, 10, 180);
        addComponent(btnSynconize, 200, 40, 10, 240);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addComponent(JComponent o, int width, int heigth, int x, int y) {
        o.setBounds(x, y, width, heigth);
        jFrame.add(o);
    }


}
