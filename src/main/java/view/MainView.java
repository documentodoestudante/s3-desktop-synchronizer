package view;

import controller.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import static config.GlobalConstants.*;

public class MainView extends JFrame {

    private static final long serialVersionUID = 1L;
    public static JProgressBar LBL_LOADING = new JProgressBar();
    private final JPanel panelForm = new JPanel();
    private JPanel contentPane;
    private JTextField txtAccessKey = new JTextField();
    private JTextField txtSecretKey = new JTextField();
    private JTextField txtRegion = new JTextField();
    private JTextField txtBucket = new JTextField();
    private JTextField txtPartner = new JTextField();
    private JTextField txtFolder = new JTextField();
    public static Client cli;
    public static MainView frame;
    JLabel lblAccessKey = new JLabel(ACCESS_KEY);
    JLabel lblSecretKey = new JLabel(SECRET_KEY);
    JButton btnFolder = new JButton("...");
    JButton btnSave = new JButton(SAVE);

    public MainView(final Client client) {
        frame = this;
        setResizable(false);
        cli = client;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 200, 407, 415);
        setTitle(TITLE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panelForm.setBounds(0, 0, this.getWidth(), this.getHeight());
        contentPane.add(panelForm);
        panelForm.setLayout(null);
        panelForm.setVisible(true);


        lblAccessKey.setBounds(12, 12, 114, 28);
        panelForm.add(lblAccessKey);


        lblSecretKey.setBounds(12, 56, 114, 28);
        panelForm.add(lblSecretKey);

        JLabel lblRegion = new JLabel(REGION);
        lblRegion.setBounds(12, 109, 114, 25);
        panelForm.add(lblRegion);

        JLabel lblBucket = new JLabel(BUCKET);
        lblBucket.setBounds(12, 150, 114, 28);
        panelForm.add(lblBucket);

        JLabel lblPartner = new JLabel(OWNER);
        lblPartner.setBounds(12, 198, 114, 28);
        panelForm.add(lblPartner);

        JLabel lblFolder = new JLabel(SAVE_IN);
        lblFolder.setBounds(12, 252, 102, 28);
        panelForm.add(lblFolder);


        txtAccessKey.setBounds(167, 13, 193, 28);
        panelForm.add(txtAccessKey);
        txtAccessKey.setColumns(10);
        txtAccessKey.setText(cli.getAccessKey());


        txtSecretKey.setBounds(167, 61, 193, 28);
        panelForm.add(txtSecretKey);
        txtSecretKey.setColumns(10);
        txtSecretKey.setText(cli.getSecretKey());


        txtRegion.setBounds(167, 109, 193, 28);
        txtRegion.setEnabled(false);
        panelForm.add(txtRegion);
        txtRegion.setColumns(10);
        txtRegion.setText(cli.getRegion() == null ? PROPERTY_REGION : cli.getBucket());


        txtBucket.setBounds(167, 157, 193, 28);
        txtBucket.setEnabled(false);
        panelForm.add(txtBucket);
        txtBucket.setColumns(10);
        txtBucket.setText(cli.getBucket() == null ? PROPERTY_BUCKET : cli.getBucket());


        txtPartner.setBounds(167, 205, 193, 28);
        panelForm.add(txtPartner);
        txtPartner.setColumns(10);
        txtPartner.setText(cli.getName());


        txtFolder.setBounds(167, 253, 159, 28);
        panelForm.add(txtFolder);
        txtFolder.setColumns(10);
        txtFolder.setText(cli.getPath());


        btnFolder.setBounds(330, 250, 31, 29);
        panelForm.add(btnFolder);


        btnSave.setBounds(140, 335, 140, 45);
        panelForm.add(btnSave);


        final JLabel lblError = new JLabel();
        lblError.setFont(new Font("Dialog", Font.BOLD, 20));
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setBounds(44, 286, 316, 28);
        lblError.setForeground(Color.RED);
        lblError.setVisible(false);
        panelForm.add(lblError);


        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client aux = new Client();
                boolean saved = false;
                aux.setAccessKey(txtAccessKey.getText());
                aux.setSecretKey(txtSecretKey.getText());
                aux.setRegion(txtRegion.getText());
                aux.setBucket(txtBucket.getText());
                aux.setName(txtPartner.getText());
                aux.setPath(txtFolder.getText());
                aux.setUpdateDate(cli.getUpdateDate());
                try {
                    saved = ClientHelper.saveClient(aux);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (saved) {
                    lblError.setVisible(false);
                    cli = aux;
                    frame.dispose();

                } else {
                    lblError.setText(FILL_THE_BLANKS);
                    lblError.setVisible(true);
                }
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

}
