package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Client;
import controller.SyncController;

public class Tela extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtAccessKey;
	private JTextField txtSecretKey;
	private JTextField txtRegiao;
	private JTextField txtBucket;
	private JTextField txtConvenio;
	private JTextField txtFolder;
	private Client cli;
	private Tela frame;

	public Tela(Client client) throws MalformedURLException {
		frame = this;
		setResizable(false);
		cli = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 407, 415);
		setTitle("Sincronizador de Arquivos");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JPanel panelForm = new JPanel();
		panelForm.setBounds(0, 0, this.getWidth(), this.getHeight());
		contentPane.add(panelForm);
		panelForm.setLayout(null);
		panelForm.setVisible(false);

		JLabel lblAccessKey = new JLabel("Access Key");
		lblAccessKey.setBounds(12, 12, 114, 28);
		panelForm.add(lblAccessKey);

		JLabel lblSecretKey = new JLabel("Secret Key");
		lblSecretKey.setBounds(12, 56, 114, 28);
		panelForm.add(lblSecretKey);

		JLabel lblRegiao = new JLabel("Região");
		lblRegiao.setBounds(12, 109, 114, 25);
		panelForm.add(lblRegiao);

		JLabel lblBucket = new JLabel("Bucket");
		lblBucket.setBounds(12, 150, 114, 28);
		panelForm.add(lblBucket);

		JLabel lblConvenio = new JLabel("Convênio");
		lblConvenio.setBounds(12, 198, 114, 28);
		panelForm.add(lblConvenio);

		JLabel lblFolder = new JLabel("Salvar em");
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

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(214, 335, 117, 25);
		panelForm.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(65, 335, 117, 25);
		panelForm.add(btnCancelar);

		final JLabel lblErro = new JLabel();
		lblErro.setFont(new Font("Dialog", Font.BOLD, 20));
		lblErro.setHorizontalAlignment(SwingConstants.CENTER);
		lblErro.setBounds(44, 286, 316, 28);
		lblErro.setForeground(Color.RED);
		lblErro.setVisible(false);
		panelForm.add(lblErro);

		final JPanel panelInicio = new JPanel();
		panelInicio.setBounds(0, 0, this.getWidth(), this.getHeight());
		contentPane.add(panelInicio);
		panelInicio.setLayout(null);
		panelInicio.setVisible(true);

		JButton btnConfigurar = new JButton("Configurar");
		btnConfigurar.setBounds(130, 224, 117, 25);
		panelInicio.add(btnConfigurar);

		final JButton btnSincronizar = new JButton("Sincronizar");
		btnSincronizar.setBounds(79, 57, 214, 109);
		panelInicio.add(btnSincronizar);
		if (cli.isLoaded())
			btnSincronizar.setEnabled(true);
		else
			btnSincronizar.setEnabled(false);

		final JPanel panelLoader = new JPanel();
		panelLoader.setBounds(0, 0, this.getWidth(), this.getHeight());
		contentPane.add(panelLoader);
		URL url = new URL("https://s3-sa-east-1.amazonaws.com/dne-convenios/loader.gif");
		ImageIcon icon = new ImageIcon(url);
		JLabel lblLoader = new JLabel(icon);
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
					lblErro.setText("Preencha todos os campos");
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
			frame.dispose();
			JOptionPane.showMessageDialog(null, "Fim da Sincronizacao", "Resultado", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		} else {
			JOptionPane.showMessageDialog(frame, "Erro na Sincronizacao", "Resultado", JOptionPane.ERROR_MESSAGE);
		}
	}
}
