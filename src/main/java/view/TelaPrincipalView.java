package view;

import controller.Client;
import controller.SyncController;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import static view.GlobalConstants.*;
import static view.Principal.BASE_PATH;

public class TelaPrincipalView extends JFrame {

    JFrame jFrame;
    private JTextField txtAccessKey, txtSecretKey, txtRegion, txtBucket, txtConvenio;
    private JButton btnSynconize, btnDirectory;
    private JLabel lblAccessKey, lblSecretKey, lblBucket, lblRegion, lblConvenio, lblTitulo;

    public TelaPrincipalView() {

        setTitle(TITULO);
        setSize(800, 600);
        setLayout(null);
        jFrame = this;

        txtAccessKey = new JTextField("AKIAJZRBINGUTPZIYAPA");
        txtSecretKey = new JTextField("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");
        txtRegion = new JTextField("sa-east-1");
        txtRegion.setEnabled(false);
        txtBucket = new JTextField("convenios");
        txtConvenio = new JTextField("PESSOAL");

        btnSynconize = new JButton(SYNC);
        btnDirectory = new JButton("Diretório");

        lblAccessKey = new JLabel(ACCESS_KEY);
        lblSecretKey = new JLabel(SECRET_KEY);
        lblBucket = new JLabel(BUCKET);
        lblRegion = new JLabel(REGION);
        lblConvenio = new JLabel(CONVENIO);
        lblTitulo = new JLabel(TITULO);

        btnSynconize.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Client client = new Client();
                client.setAccessKey(txtAccessKey.getText());
                client.setSecretKey(txtSecretKey.getText());
                client.setRegion(txtRegion.getText());
                client.setName(txtConvenio.getText());
                client.setBucket(txtBucket.getText());

                try {
                    new SyncController(client);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Erro nas credenciais", "Atenção", JOptionPane.WARNING_MESSAGE);
                }
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

        addComponent(lblTitulo, 200, 40, 400 - 100, 10);

        addComponent(lblAccessKey, 200, 40, 10, 110);
        addComponent(txtAccessKey, 200, 40, 120, 110);

        addComponent(lblSecretKey, 200, 40, 10, 160);
        addComponent(txtSecretKey, 200, 40, 120, 160);

        addComponent(lblRegion, 200, 40, 10, 220);
        addComponent(txtRegion, 200, 40, 120, 220);

        addComponent(lblBucket, 200, 40, 10, 280);
        addComponent(txtBucket, 200, 40, 120, 280);

        addComponent(lblConvenio, 200, 40, 10, 340);
        addComponent(txtConvenio, 200, 40, 120, 340);

        addComponent(btnSynconize, 200, 40, 10, 500);

        if (BASE_PATH.isEmpty())
            procuraArquivo();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addComponent(JComponent o, int width, int heigth, int x, int y) {
        o.setBounds(x, y, width, heigth);
        jFrame.add(o);
    }

    public void procuraArquivo() {
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt e .zip", ".zip", ".txt");
        String pathBase = System.getProperty("user.home");
        File file = new File(pathBase);

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(file);
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(true);
//        jFileChooser.setFileFilter(filter);
        String caminhoArquivo = "";
        int retorno = jFileChooser.showOpenDialog(null);
        caminhoArquivo = jFileChooser.getSelectedFile().getAbsolutePath();
        System.out.println(caminhoArquivo);
        BASE_PATH = caminhoArquivo;
    }


}
