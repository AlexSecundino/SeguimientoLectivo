package Interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BaseDeDatos.BaseDeDatos;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Actividad extends Sesion {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private BaseDeDatos bd = null;
	private ResultSet resultado = null;
	
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnAddActividad;
	private JButton btnEliminar;
	private JButton btnAtras;
	private JButton btnExit;
	
	private String[][] matrizDatos = {};
	private String[] nombreColumnas = {"Numero Actividad", "Tipo Actividad", "Sub-tipo Clase", "Duracion"};
	private String[] datos = {};
	private DefaultTableModel modelo = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Actividad frame = new Actividad();
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
	public Actividad() {
		setBounds(100, 100, 640, 300);
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
		btnAtras.setBounds(10, 226, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnAtras);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		btnExit.setBounds(485, 226, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnExit);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(485, 190, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnEliminar);
		
		btnAddActividad = new JButton("A\u00F1adir Actividad");
		btnAddActividad.setBounds(350, 190, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnAddActividad);
		
		
		modelo = new DefaultTableModel(matrizDatos, nombreColumnas);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 600, 150);
		contentPane.add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(true);
		table.setModel(modelo);	
	}

	public JButton getBtnAddActividad() {
		return btnAddActividad;
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
