package Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import BaseDeDatos.BaseDeDatos;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sesion extends Temario {


	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private BaseDeDatos bd = null;
	private ResultSet resultado = null;
	
	
	public static String sesionSeleccionada = null;
	
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnVerActividad;
	private JButton btnAddSesion;
	private JButton btnEliminar;
	private JButton btnAtras;
	private JButton btnExit;
	
	private String[][] matrizDatos = {};
	private String[] nombreColumnas = {"Fecha", "Dia", "Duracion", "Tipo", "Comentarios"};
	private String[] datos = {};
	private DefaultTableModel modelo = null;
	
	private Actividad actividad = null;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sesion frame = new Sesion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Sesion() {
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
		
		btnAddSesion = new JButton("A\u00F1adir Sesion");
		btnAddSesion.setBounds(350, 190, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnAddSesion);
		
		btnEliminar = new JButton("Eliminar Sesion");
		btnEliminar.setBounds(485, 190, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnEliminar);
		
		btnVerActividad = new JButton("Ver Actividad");
		btnVerActividad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean fechaCorrecta = false;
				sesionSeleccionada = JOptionPane.showInputDialog(contentPane, "Introduzca la fecha sobre la que desea visualizar las actividades", "Ver actividades", 3);
				if(sesionSeleccionada.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}"))
				{
					bd = new BaseDeDatos();
					
					try {
						String SQL = "SELECT fecha from sesion s INNER JOIN temario t on t.codigoModulo = s.codigoModulo AND s.codigoInstituto = t.codigoInstituto AND t.codigoGrupo = s.codigoGrupo AND t.curso = s.curso where s.codigoInstituto ='" + codigoInstitutoSeleccionado + "' AND s.codigoGrupo ='" + codigoGrupoSeleccionado + "' AND s.curso = '" + cursoGrupoSeleccionado + "' AND s.codigoModulo = '" + codigoModuloSeleccionado + "' AND s.unidad='" + unidadSeleccionada + "'";
						resultado = bd.consultar(SQL);
							while(resultado.next())
							{
								String fecha = resultado.getString("fecha");
								System.out.println(resultado.getString("fecha"));
								if(fecha.equals(sesionSeleccionada))
									fechaCorrecta = true;
							}
					}
					catch (SQLException e) {
						JOptionPane.showMessageDialog(contentPane, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
					}
					finally{
						if(fechaCorrecta){
							actividad = new Actividad();
							actividad.setVisible(true);
						}
						else
							JOptionPane.showMessageDialog(contentPane, "Fecha incorrecta, no hay ninguna sesion realizada ese dia", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					JOptionPane.showMessageDialog(contentPane, "Fecha incorrecta, formato [aaaa-mm-dd]", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnVerActividad.setBounds(215, 190, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnVerActividad);
		
		
		modelo = new DefaultTableModel(matrizDatos, nombreColumnas);
		
		scrollPane = new JScrollPane();
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
			System.out.println(unidadSeleccionada);
			String SQL = "SELECT * from sesion s INNER JOIN temario t on t.unidad = s.unidad AND t.codigoModulo = s.codigoModulo AND s.codigoInstituto = t.codigoInstituto AND t.codigoGrupo = s.codigoGrupo AND t.curso = s.curso where s.codigoInstituto ='" + codigoInstitutoSeleccionado + "' AND s.codigoGrupo ='" + codigoGrupoSeleccionado + "' AND s.curso = '" + cursoGrupoSeleccionado + "' AND s.codigoModulo = '" + codigoModuloSeleccionado + "' AND s.unidad ='" + unidadSeleccionada + "'";
			resultado = bd.consultar(SQL);
			while(resultado.next()){
				datos = new String[5];
				datos[0] = resultado.getString("fecha");
				datos[1] = resultado.getString("diaSemana");
				datos[2] = resultado.getString("numeroMinutos");
				datos[3] = resultado.getString("tipo");
				datos[4] = resultado.getString("comentarios");
					
				modelo.addRow(datos);
				datos = null;
				respuesta = true;
			}
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(contentPane, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
	
		finally{
			if(!respuesta)
			{
				btnVerActividad.setEnabled(false);
				btnEliminar.setEnabled(false);
			}
			else{
				btnVerActividad.setEnabled(true);
				btnEliminar.setEnabled(true);
			}	
		}
	}

	public JButton getBtnVerActividad() {
		return btnVerActividad;
	}
	public JButton getBtnAtras() {
		return btnAtras;
	}
	public JButton getBtnAadirSesion() {
		return btnAddSesion;
	}
	public JButton getBtnEliminarSesion() {
		return btnEliminar;
	}
	public JButton getBtnExit() {
		return btnExit;
	}
	public JScrollPane getScrollPane() {
		return scrollPane;
	}
}
