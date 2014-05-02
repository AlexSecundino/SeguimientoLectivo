package Interfaz;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;

import BaseDeDatos.BaseDeDatos;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;


public class Grupo extends Instituto {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JComboBox<String> cbListaGrupos;
	private JButton btnSeleccionar;
	private JButton btnAdd;
	private JButton btnEliminar;
	private JButton btnAtras;
	private JButton btnExit;
	
	private Modulo modulo = null;
	
	private BaseDeDatos bd = null;
	private ResultSet resultado = null;
	
	
	public static String codigoGrupoSeleccionado = null;
	public static String cursoGrupoSeleccionado = null;
	//private Grupo grupo = null;
	//private String grupoSeleccionado = null;

	/**
	 * Create the frame.
	 */
	public Grupo() {
		setTitle("Grupo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cbListaGrupos = new JComboBox<String>();
		cbListaGrupos.setBounds(40, 30, 200, HEIGHT_BUTTON);
		contentPane.add(cbListaGrupos);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbListaGrupos.getSelectedIndex() >= 0)
				{
					StringTokenizer st = new StringTokenizer(cbListaGrupos.getSelectedItem().toString(), "-");
					while(st.hasMoreTokens()){
						codigoGrupoSeleccionado = st.nextToken();
						cursoGrupoSeleccionado = st.nextToken();
						st.nextToken();
					}
					modulo = new Modulo();
					modulo.setVisible(true);
				}
			}
		});
		btnSeleccionar.setBounds(269, 31, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnSeleccionar);
		
		btnAdd = new JButton("A\u00F1adir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				añadirGrupo();
			}
		});
		btnAdd.setBounds(75, 80, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnAdd);
		
		btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtras.setBounds(40, 205, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnAtras);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(75, 116, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnEliminar);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		btnExit.setBounds(269, 205, WIDTH_BUTTON, HEIGHT_BUTTON);
		contentPane.add(btnExit);
		
		this.añadirListado();
	}

	protected void añadirGrupo() {
		boolean respuesta = false;
		
		bd = new BaseDeDatos();
		String descripcion = "";
		int codigoGrupo = 0;
		String codigo = null;
		String curso = null;
		
		codigo = JOptionPane.showInputDialog(contentPane, "Introduzca el codigo del grupo", "Nuevo grupo", 3);
		
		if(codigo != null){
			if(codigo.matches("[0-9]+"))
			{
				curso = JOptionPane.showInputDialog(contentPane, "Introduzca el curso (YYYY/YYYY)", "Nuevo grupo", 3);
				if(curso != null)
					if(curso.matches("[0-9]{4}/[0-9]{4}")){
						try{
							codigoGrupo = Integer.valueOf(codigo);
							descripcion = JOptionPane.showInputDialog(contentPane, "Introduzca la descripcion del grupo", "Nuevo grupo", 3);
							String SQL = "INSERT into grupo values('" + codigoInstitutoSeleccionado + "', '" + codigoGrupo + "', '" + curso + "', '" + descripcion + "')";
							if(!descripcion.isEmpty()){
								try{
									bd.insertar(SQL);
									respuesta = true;
								}
								catch(Exception e){
									JOptionPane.showMessageDialog(contentPane, "No se ha podido añadir el grupo", "Error", JOptionPane.ERROR_MESSAGE);
								}
								finally{
									if(respuesta){
										cbListaGrupos.removeAllItems();
										añadirListado();
									}
								}
							}
						}
						catch(NumberFormatException e){
							JOptionPane.showMessageDialog(contentPane, "Error en el código del grupo. Pruebe con un número menor que " + Integer.MAX_VALUE);
						}
					}
					else
						JOptionPane.showMessageDialog(contentPane, "Curso invalido. Comprueba que siga el formato (YYYY/YYYY)", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(contentPane, "Codigo inválido. Debe de ser un numero mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void añadirListado() {
		
		boolean respuesta = false;
		
		bd = new BaseDeDatos();
		String SQL = "SELECT * from grupo g INNER JOIN instituto i on g.codigoInstituto = i.codigoInstituto where g.codigoInstituto ='" + codigoInstitutoSeleccionado + "' order by curso desc";
		resultado = bd.consultar(SQL);
		try{
			while(resultado.next()){
				cbListaGrupos.addItem(resultado.getString("codigoGrupo") + "-" + resultado.getString("curso") + "-" + resultado.getString("descripcion"));
				respuesta = true;
			}
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally{
			if(!respuesta)
			{
				cbListaGrupos.addItem("No hay grupos");
				btnSeleccionar.setEnabled(false);
				cbListaGrupos.setEnabled(false);
			}
			else{
				btnSeleccionar.setEnabled(true);
				cbListaGrupos.setEnabled(true);
			}	
		}
	}
	
	
	public JComboBox<String> getCbListaGrupos() {
		return cbListaGrupos;
	}
	public JButton getBtnSeleccionar() {
		return btnSeleccionar;
	}
	public JButton getBtnAdd() {
		return btnAdd;
	}
	public JButton getBtnAtras() {
		return btnAtras;
	}
}

