package Interfaz;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.swing.JComboBox;

import BaseDeDatos.BaseDeDatos;

public class Modulo extends Grupo {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JComboBox<String> cbListaModulos;
	private JButton btnSeleccionar;
	private JButton btnAdd;
	private JButton btnEliminar;
	private JButton btnAtras;
	private JButton btnExit;
	
	private Temario temario = null;
	
	private BaseDeDatos bd = null;
	private ResultSet resultado = null;
	
	
	public static String codigoModuloSeleccionado = null;

	public Modulo() {
		setResizable(false);
		setTitle("Modulo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtras.setBounds(40, 205, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnAtras);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		btnExit.setBounds(269, 205, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnExit);
		
		cbListaModulos = new JComboBox<String>();
		cbListaModulos.setBounds(40, 30, 200, HEIGHT_BUTTON);
		contentPane.add(cbListaModulos);
		
		btnAdd = new JButton("A\u00F1adir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				añadirModulo();
			}
		});
		btnAdd.setBounds(75, 80, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnAdd);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(75, 116, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnEliminar);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbListaModulos.getSelectedIndex() >= 0){
					StringTokenizer st = new StringTokenizer(cbListaModulos.getSelectedItem().toString(), "-");
					while(st.hasMoreTokens()){
						codigoModuloSeleccionado = st.nextToken();
						st.nextToken();
					}
					temario = new Temario();
					temario.setVisible(true);
				}
			}
		});
		btnSeleccionar.setBounds(269, 31, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnSeleccionar);
		
		this.añadirListado();
	}
	
	protected void añadirModulo() {
		
		boolean respuesta = false;
		
		bd = new BaseDeDatos();
		String nombreModulo = "";
		int codigoModulo = 0;
		int numeroHoras = 0;
		String horas = null;
		String codigo = null;
		codigo = JOptionPane.showInputDialog(contentPane, "Introduzca el codigo del modulo", "Nuevo modulo", 3);
		
		if(codigo != null){
			if(codigo.matches("[0-9]+"))
			{
				try{
					codigoModulo = Integer.valueOf(codigo);
	
					nombreModulo = JOptionPane.showInputDialog(contentPane, "Introduzca el nombre del modulo", "Nuevo modulo", 3);
					if(!nombreModulo.isEmpty()){
						horas = JOptionPane.showInputDialog(contentPane, "Introduzca el numero de horas anuales del modulo", "Nuevo modulo", 3);
						if(horas != null)
							if(horas.matches("[0-9]+"))
							{
								numeroHoras = Integer.valueOf(horas);
								String SQL = "INSERT INTO modulo values('" + codigoInstitutoSeleccionado + "', '" + codigoGrupoSeleccionado + "', '" + cursoGrupoSeleccionado + "', '" + codigoModulo + "', '" + nombreModulo + "', '" + numeroHoras + "')";
								try{
									bd.insertar(SQL);
									respuesta = true;
								}
								catch(Exception e){
									JOptionPane.showMessageDialog(null, "No se ha podido añadir el modulo", "Error", JOptionPane.ERROR_MESSAGE);
								}
								finally{
									if(respuesta){
										cbListaModulos.removeAllItems();
										añadirListado();
									}
								}
							}
					}
				}
				catch(NumberFormatException e){
					JOptionPane.showMessageDialog(contentPane, "Error en el código del modulo. Pruebe con un número menor que " + Integer.MAX_VALUE);
				}
			}
			else
				JOptionPane.showMessageDialog(contentPane, "Codigo inválido. Debe de ser un numero mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void añadirListado() {
		
		boolean respuesta = false;
		
		bd = new BaseDeDatos();
		
		String SQL = "SELECT * from modulo m INNER JOIN grupo g on m.codigoInstituto = g.codigoInstituto AND g.codigoGrupo = m.codigoGrupo AND g.curso = m.curso where m.codigoInstituto ='" + codigoInstitutoSeleccionado + "' AND m.codigoGrupo ='" + codigoGrupoSeleccionado + "' AND m.curso = '" + cursoGrupoSeleccionado + "' order by m.descripcion asc";
		resultado = bd.consultar(SQL);
		try{
			while(resultado.next()){
				cbListaModulos.addItem(resultado.getString("codigoModulo") + "-" + resultado.getString("descripcion"));
				respuesta = true;
			}
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(contentPane, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally{
			if(!respuesta)
			{
				cbListaModulos.addItem("No hay grupos");
				btnSeleccionar.setEnabled(false);
				cbListaModulos.setEnabled(false);
				btnEliminar.setEnabled(false);
			}
			else{
				btnSeleccionar.setEnabled(true);
				cbListaModulos.setEnabled(true);
				btnEliminar.setEnabled(true);
			}	
		}
	}

	public JComboBox<String> getCbListaModulos() {
		return cbListaModulos;
	}
	public JButton getBtnSeleccionar() {
		return btnSeleccionar;
	}
	public JButton getBtnAdd() {
		return btnAdd;
	}
	public JButton getBtnEliminar() {
		return btnEliminar;
	}
	public JButton getBtnAtras() {
		return btnAtras;
	}
	public JButton getBtnExit() {
		return btnExit;
	}
}
