package view;

import controller.Client;
import controller.SyncController;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import static view.GlobalConstants.*;
import static view.Principal.BASE_PATH;
import static view.Principal.FILE_SEPARATOR;

public class TelaPrincipalView extends JFrame {

    JFrame jFrame;
    private JTextField txtAccessKey, txtSecretKey, txtRegion, txtBucket, txtConvenio;
    private JButton btnSynconize, btnDirectory;
    private JLabel lblAccessKey, lblSecretKey, lblBucket, lblRegion, lblConvenio, lblTitulo;

    public TelaPrincipalView() throws IOException {

        setTitle(TITULO);
        setSize(490, 500);
        setLayout(null);
        jFrame = this;

        txtAccessKey = new JTextField("");
        txtSecretKey = new JTextField("");
        txtRegion = new JTextField("sa-east-1");
        txtRegion.setEnabled(false);
        txtBucket = new JTextField("convenios");
        txtConvenio = new JTextField("PESSOAL");

        btnSynconize = new JButton(SYNC);

        lblAccessKey = new JLabel(ACCESS_KEY);
        lblSecretKey = new JLabel(SECRET_KEY);
        lblBucket = new JLabel(BUCKET);
        lblRegion = new JLabel(REGION);
        lblConvenio = new JLabel(CONVENIO);
        lblTitulo = new JLabel(TITULO);

        btnSynconize.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Iniciando sincronização", "Sincronização", JOptionPane.INFORMATION_MESSAGE);
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

        addComponent(lblTitulo, 350, 80, (this.getWidth() / 2) - (200 / 2), 10);

        addComponent(lblAccessKey, 350, 40, 10, 110);
        addComponent(txtAccessKey, 350, 40, 120, 110);

        addComponent(lblSecretKey, 350, 40, 10, 160);
        addComponent(txtSecretKey, 350, 40, 120, 160);

        addComponent(lblRegion, 350, 40, 10, 220);
        addComponent(txtRegion, 350, 40, 120, 220);

        addComponent(lblBucket, 350, 40, 10, 280);
        addComponent(txtBucket, 350, 40, 120, 280);

        addComponent(lblConvenio, 350, 40, 10, 340);
        addComponent(txtConvenio, 350, 40, 120, 340);

        addComponent(btnSynconize, 460, 40, 10, 390);

        if (BASE_PATH.isEmpty())
            procuraArquivo();

//        getGif();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addComponent(JComponent o, int width, int heigth, int x, int y) {
        o.setBounds(x, y, width, heigth);
        jFrame.add(o);
    }

    public void procuraArquivo() {
        String pathBase = System.getProperty("user.home");
        File file = new File(pathBase);

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(file);
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(true);
        String caminhoArquivo = "";
        int retorno = jFileChooser.showOpenDialog(null);
//        if (retorno == JFileChooser.DIRECTORIES_ONLY) {
            if (jFileChooser.getSelectedFile() == null)
                caminhoArquivo = System.getProperty("user.home") + FILE_SEPARATOR + "SINCRONIZACAO";
            else
                caminhoArquivo = jFileChooser.getSelectedFile().getAbsolutePath();
//        }
        System.out.println(caminhoArquivo);
        BASE_PATH = caminhoArquivo;
    }

    public void getGif() throws IOException {
        byte[] b = new byte[1];
        URL url = new URL("https://thumbs.gfycat.com/SkinnySeveralAsianlion-size_restricted.gif");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        DataInputStream di = new DataInputStream(urlConnection.getInputStream());

        FileOutputStream fo = new FileOutputStream("a.gif");
        while (-1 != di.read(b, 0, 1))
            fo.write(b, 0, 1);
        di.close();
        fo.close();
    }

}
