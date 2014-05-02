package Interfaz;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;

import BaseDeDatos.BaseDeDatos;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Instituto extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JComboBox<String> cbListaInstitutos;
	private JButton btnSeleccionar;
	private JButton btnAdd;
	private JButton btnEliminar;
	private JButton btnExit;
	
	private Grupo grupo = null;
	
	public BaseDeDatos bd = null;
	public ResultSet resultado = null;
	
	public static String codigoInstitutoSeleccionado = null;

	public static int HEIGHT_BUTTON = 25;
	public static int WIDTH_BUTTON = 125;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Instituto frame = new Instituto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Instituto() {
		setTitle("Instituto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cbListaInstitutos = new JComboBox<String>();
		cbListaInstitutos.setBounds(40, 30, 200, HEIGHT_BUTTON);
		contentPane.add(cbListaInstitutos);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbListaInstitutos.getSelectedIndex() >= 0)
				{
					StringTokenizer st = new StringTokenizer(cbListaInstitutos.getSelectedItem().toString(), "-");
					while(st.hasMoreTokens()){
							codigoInstitutoSeleccionado = st.nextToken();
							st.nextToken();
					}
					grupo = new Grupo();
					grupo.setVisible(true);
				}
			}
		});
		btnSeleccionar.setBounds(269, 31, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnSeleccionar);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		btnExit.setBounds(269, 205, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnExit);
		
		btnAdd = new JButton("A\u00F1adir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				añadirInstituto();
			}
		});
		btnAdd.setBounds(75, 80, 125, 25);
		contentPane.add(btnAdd);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(75, 116, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnEliminar);
		
		this.añadirListado();
	}

	protected void añadirInstituto() {
		boolean respuesta = false;
		
		bd = new BaseDeDatos();
		String nombreInstituto = "";
		int codigoInstituto = 0;
		String codigo = null;
		codigo = JOptionPane.showInputDialog(contentPane, "Introduzca el codigo del instituto", "Nuevo instituto", 3);
		
		if(codigo != null){
			if(codigo.matches("[0-9]+"))
			{
				try{
					codigoInstituto = Integer.valueOf(codigo);
	
					nombreInstituto = JOptionPane.showInputDialog(contentPane, "Introduzca el nombre del instituto", "Nuevo instituto", 3);
					String SQL = "INSERT into instituto values('" + codigoInstituto + "', '" + nombreInstituto + "')";
					if(!nombreInstituto.isEmpty()){
						try{
							bd.insertar(SQL);
							respuesta = true;
						}
						catch(Exception e){
							JOptionPane.showMessageDialog(contentPane, "No se ha podido añadir el instituto", "Error", JOptionPane.ERROR_MESSAGE);
						}
						finally{
							if(respuesta){
								cbListaInstitutos.removeAllItems();
								añadirListado();
							}
						}
					}
					else{
						JOptionPane.showMessageDialog(contentPane, "Introduzca un nombre", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(NumberFormatException e){
					JOptionPane.showMessageDialog(contentPane, "Error en el código de instituto. Pruebe con un número menor que " + Integer.MAX_VALUE);
				}
			}
			else
				JOptionPane.showMessageDialog(contentPane, "Codigo inválido. Debe de ser un numero mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void añadirListado() {
		
		boolean respuesta = false;
		
		bd = new BaseDeDatos();
		String SQL = "SELECT * from instituto order by codigoInstituto asc";
		resultado = bd.consultar(SQL);
		
		try{
			while(resultado.next()){
				cbListaInstitutos.addItem(resultado.getString("codigoInstituto") + "-" + resultado.getString("descripcion"));
				respuesta = true;
			}
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(contentPane, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(contentPane, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally{
			if(!respuesta)
			{
				cbListaInstitutos.addItem("No hay institutos");
				btnSeleccionar.setEnabled(false);
				cbListaInstitutos.setEnabled(false);
			}
			else{
				btnSeleccionar.setEnabled(true);
				cbListaInstitutos.setEnabled(true);
			}
				
		}
	}
	
	
	public JComboBox<String> getCbListaInstitutos() {
		return cbListaInstitutos;
	}
	public JButton getBtnExit() {
		return btnExit;
	}
	public JButton getBtnSeleccionar() {
		return btnSeleccionar;
	}
}

