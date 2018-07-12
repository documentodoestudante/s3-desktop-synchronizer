package view;

import controller.Client;
import controller.SyncController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static config.GlobalConstants.*;

public class MainView extends JFrame {

    private static final long serialVersionUID = 1L;
    public static JProgressBar LBL_LOADING = new JProgressBar();
    private final JPanel panelForm = new JPanel();
    private final JPanel panelInicio = new JPanel();
    private final JPanel panelLoader = new JPanel();
    private JPanel contentPane;
    private JTextField txtAccessKey;
    private JTextField txtSecretKey;
    private JTextField txtRegiao;
    private JTextField txtBucket;
    private JTextField txtConvenio;
    private JTextField txtFolder;
    private Client cli;
    private MainView frame;

    public MainView(final Client client) throws MalformedURLException {
        frame = this;
        setResizable(false);
        cli = client;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 200, 407, 415);
        setTitle(TITULO);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panelForm.setBounds(0, 0, this.getWidth(), this.getHeight());
        contentPane.add(panelForm);
        panelForm.setLayout(null);
        panelForm.setVisible(false);

        JLabel lblAccessKey = new JLabel(ACCESS_KEY);
        lblAccessKey.setBounds(12, 12, 114, 28);
        panelForm.add(lblAccessKey);

        JLabel lblSecretKey = new JLabel(SECRET_KEY);
        lblSecretKey.setBounds(12, 56, 114, 28);
        panelForm.add(lblSecretKey);

        JLabel lblRegiao = new JLabel(REGION);
        lblRegiao.setBounds(12, 109, 114, 25);
        panelForm.add(lblRegiao);

        JLabel lblBucket = new JLabel(BUCKET);
        lblBucket.setBounds(12, 150, 114, 28);
        panelForm.add(lblBucket);

        JLabel lblConvenio = new JLabel(OWNER);
        lblConvenio.setBounds(12, 198, 114, 28);
        panelForm.add(lblConvenio);

        JLabel lblFolder = new JLabel(SAVE_IN);
        lblFolder.setBounds(12, 252, 102, 28);
        panelForm.add(lblFolder);

        txtAccessKey = new JTextField();
        txtAccessKey.setBounds(167, 13, 193, 28);
        panelForm.add(txtAccessKey);
        txtAccessKey.setColumns(10);
        txtAccessKey.setText(cli.getAccessKey());

        txtSecretKey = new JTextField();
        txtSecretKey.setBounds(167, 61, 193, 28);
        panelForm.add(txtSecretKey);
        txtSecretKey.setColumns(10);
        txtSecretKey.setText(cli.getSecretKey());

        txtRegiao = new JTextField();
        txtRegiao.setBounds(167, 109, 193, 28);
        panelForm.add(txtRegiao);
        txtRegiao.setColumns(10);
        txtRegiao.setText(cli.getRegion());

        txtBucket = new JTextField();
        txtBucket.setBounds(167, 157, 193, 28);
        panelForm.add(txtBucket);
        txtBucket.setColumns(10);
        txtBucket.setText(cli.getBucket());

        txtConvenio = new JTextField();
        txtConvenio.setBounds(167, 205, 193, 28);
        panelForm.add(txtConvenio);
        txtConvenio.setColumns(10);
        txtConvenio.setText(cli.getName());

        txtFolder = new JTextField();
        txtFolder.setBounds(167, 253, 159, 28);
        panelForm.add(txtFolder);
        txtFolder.setColumns(10);
        txtFolder.setText(cli.getPath());

        JButton btnFolder = new JButton("...");
        btnFolder.setBounds(330, 250, 31, 29);
        panelForm.add(btnFolder);

        JButton btnSalvar = new JButton(SAVE);
        btnSalvar.setBounds(214, 335, 117, 25);
        panelForm.add(btnSalvar);

        JButton btnCancelar = new JButton(CANCEL);
        btnCancelar.setBounds(65, 335, 117, 25);
        panelForm.add(btnCancelar);

        final JLabel lblErro = new JLabel();
        lblErro.setFont(new Font("Dialog", Font.BOLD, 20));
        lblErro.setHorizontalAlignment(SwingConstants.CENTER);
        lblErro.setBounds(44, 286, 316, 28);
        lblErro.setForeground(Color.RED);
        lblErro.setVisible(false);
        panelForm.add(lblErro);

        panelInicio.setBounds(0, 0, this.getWidth(), this.getHeight());
        contentPane.add(panelInicio);
        panelInicio.setLayout(null);
        panelInicio.setVisible(true);

        JButton btnConfigurar = new JButton(CONFIGURE);
        btnConfigurar.setBounds(130, 224, 117, 25);
        panelInicio.add(btnConfigurar);

        final JButton btnSincronizar = new JButton(SYNC);
        btnSincronizar.setBounds(79, 57, 214, 109);
        panelInicio.add(btnSincronizar);
        if (cli.isLoaded())
            btnSincronizar.setEnabled(true);
        else
            btnSincronizar.setEnabled(false);

        panelLoader.setBounds(0, 0, this.getWidth(), this.getHeight());
        System.out.println(panelLoader.getLayout());
        panelLoader.setLayout(null);
        contentPane.add(panelLoader);
        URL url = new URL(LOADING_GIF_URL);
        ImageIcon icon = new ImageIcon(url);
        LBL_LOADING.setBounds(0, this.getWidth() - 50, this.getWidth(), 60);
        panelLoader.add(LBL_LOADING);
        JLabel lblLoader = new JLabel(icon);
        lblLoader.setBounds(0, 0, getWidth(), getHeight() - 50);
        panelLoader.add(lblLoader);
        panelLoader.setVisible(false);

        btnConfigurar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelInicio.setVisible(false);
                panelForm.setVisible(true);
            }
        });
        btnSincronizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelInicio.setVisible(false);
                panelLoader.setVisible(true);
                SyncController sc = new SyncController(cli, frame);
                Thread t = new Thread(sc);
                t.start();
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client aux = new Client();
                boolean salvo = false;
                aux.setAccessKey(txtAccessKey.getText());
                aux.setSecretKey(txtSecretKey.getText());
                aux.setRegion(txtRegiao.getText());
                aux.setBucket(txtBucket.getText());
                aux.setName(txtConvenio.getText());
                aux.setPath(txtFolder.getText());
                aux.setDataAtualizacao(cli.getDataAtualizacao());
                try {
                    salvo = ClientHelper.salvaClient(aux);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (salvo) {
                    lblErro.setVisible(false);
                    cli = aux;
                    btnSincronizar.setEnabled(true);
                    panelForm.setVisible(false);
                    panelInicio.setVisible(true);
                } else {
                    lblErro.setText(FILL_THE_BLANKS);
                    lblErro.setVisible(true);
                }
            }
        });
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelForm.setVisible(false);
                panelInicio.setVisible(true);
            }
        });
        btnFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jFileChooser.showOpenDialog(frame);
                if (jFileChooser.getSelectedFile() != null)
                    cli.setPath(jFileChooser.getSelectedFile().getAbsolutePath());
                txtFolder.setText(cli.getPath());
            }
        });
    }

    public void validaFim(boolean res) {
        if (res) {
            panelInicio.setVisible(true);
            panelLoader.setVisible(false);
            JOptionPane.showMessageDialog(null, SYNC_END, RESULT, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, SYNC_ERROR, RESULT, JOptionPane.ERROR_MESSAGE);
            panelInicio.setVisible(true);
            panelLoader.setVisible(false);
        }
    }
}
