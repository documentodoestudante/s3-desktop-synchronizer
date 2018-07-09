package view;

import controller.Client;
import controller.SyncController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import static view.Principal.BASE_PATH;

public class TelaPrincipalView extends JFrame {

    private JTextField txtAccessKey, txtSecretKey, txtRegion, txtBucket, txtConvenio;
    private JButton btnSynconize, btnDirectory;
    JFrame jFrame;

    public TelaPrincipalView() {

        setTitle("Sincronizador");
        setSize(800, 600);
        setLayout(null);
        jFrame = this;

        txtAccessKey = new JTextField("AKIAJZRBINGUTPZIYAPA");
        txtSecretKey = new JTextField("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");
        txtRegion = new JTextField("sa-east-1");
        txtBucket = new JTextField("convenios");
        txtConvenio = new JTextField("PESSOAL");
        btnSynconize = new JButton("Sync");
        btnDirectory = new JButton("Diretório");

        btnSynconize.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Client client = new Client();
                client.setAccessKey(txtAccessKey.getText());
                client.setSecretKey(txtSecretKey.getText());
                client.setRegion(txtRegion.getText());
                client.setName(txtConvenio.getText());
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
        addComponent(txtConvenio, 200, 40, 230, 240);
        addComponent(btnSynconize, 200, 40, 10, 240);
        addComponent(btnDirectory, 200, 40, 230, 280);

        btnDirectory.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                procuraArquivo();
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
//        procuraArquivo();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addComponent(JComponent o, int width, int heigth, int x, int y) {
        o.setBounds(x, y, width, heigth);
        jFrame.add(o);
    }
    
    public void procuraArquivo(){
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt e .zip",".zip", ".txt");
        String pathBase = System.getProperty("user.home");
        File file = new File(pathBase);

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(file);
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(true);
        jFileChooser.setFileFilter(filter);
        String caminhoArquivo = "";
        int retorno = jFileChooser.showOpenDialog(null);
        caminhoArquivo = jFileChooser.getSelectedFile().getAbsolutePath();
        System.out.println(caminhoArquivo);
        BASE_PATH = caminhoArquivo;
    }


}
