package Interfaz;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import BaseDeDatos.BaseDeDatos;

import javax.swing.JButton;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.mysql.jdbc.NdbLoadBalanceExceptionChecker;

public class Temario extends Modulo {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private BaseDeDatos bd = null;
	private ResultSet resultado = null;
	
	
	public static String unidadSeleccionada = null;
	
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnAddSesion;
	private JButton btnAddTemario;
	private JButton btnEliminar;
	private JButton btnAtras;
	private JButton btnExit;
	
	private String[][] matrizDatos = {};
	private String[] nombreColumnas = {"Tema", "Evaluacion", "Horas Previstas", "Horas Reales", "Acumuladas Programa", "Acumuladas Reales", "Diferencia", "Lectivas", "Practicas", "Teoria", "Huelga", "Fiesta", "Enfermo", "Otros"};
	private String[] datos = {};
	private DefaultTableModel modelo = null;
	
	public Temario() {
		setResizable(true);
		setTitle("Temario");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		btnExit.setBounds(485, 226, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnExit);
		
		btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnAtras.setBounds(10, 226, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnAtras);
		
		btnAddTemario = new JButton("A\u00F1adir Temario");
		btnAddTemario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnAddTemario.setBounds(350, 190, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnAddTemario);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(485, 190, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnEliminar);
		
		btnAddSesion = new JButton("A\u00F1adir Sesion");
		btnAddSesion.setBounds(215, 190, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnAddSesion);
		
		modelo = new DefaultTableModel(matrizDatos, nombreColumnas);
		
		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 11, 600, 150);
		contentPane.add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(true);
		table.setModel(modelo);
		
		this.añadirFilas();
	}
	private void añadirFilas() {
		
		boolean respuesta = false;
		
		bd = new BaseDeDatos();

		try{
			String SQL = "SELECT * from temario t INNER JOIN modulo m on t.codigoModulo = m.codigoModulo AND m.codigoInstituto = t.codigoInstituto AND t.codigoGrupo = m.codigoGrupo AND t.curso = m.curso where t.codigoInstituto ='" + codigoInstitutoSeleccionado + "' AND t.codigoGrupo ='" + codigoGrupoSeleccionado + "' AND t.curso = '" + cursoGrupoSeleccionado + "' AND t.codigoModulo = '" + codigoModuloSeleccionado + "'";
			resultado = bd.consultar(SQL);
			while(resultado.next()){
				datos = new String[14];
				datos[0] = resultado.getString("unidad");
				datos[1] = resultado.getString("evaluacion");
				datos[2] = resultado.getString("horasPrevistas");
				datos[3] = resultado.getString("horasReales");
				datos[4] = resultado.getString("acumuladasPrograma");
				datos[5] = resultado.getString("acumuladasReales");
				datos[6] = resultado.getString("diferencia");
				datos[7] = resultado.getString("horasLectivas");
				datos[8] = resultado.getString("horasPractica");
				datos[9] = resultado.getString("horasTeoria");
				datos[10] = resultado.getString("horasHuelga");
				datos[11] = resultado.getString("horasFiesta");
				datos[12] = resultado.getString("horasEnfermo");
				datos[13] = resultado.getString("horasOtros");
					
				modelo.addRow(datos);
				datos = null;
			}
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(contentPane, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
	
		finally{
			if(!respuesta)
			{
				btnAddSesion.setEnabled(false);
				btnEliminar.setEnabled(false);
			}
			else{
				btnAddSesion.setEnabled(true);
				btnEliminar.setEnabled(true);
			}	
		}
		
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}
	public JButton getBtnAadirSesion() {
		return btnAddSesion;
	}
	public JButton getBtnAdd() {
		return btnAddTemario;
	}
	public JButton getBtnAtras() {
		return btnAtras;
	}
	public JButton getBtnEliminar() {
		return btnEliminar;
	}
	public JButton getBtnExit() {
		return btnExit;
	}
}
