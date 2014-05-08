package BaseDeDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class BaseDeDatos {

	private Connection connection = null;
	
	public void conectar(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/seguimientolectivo","root","");
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public ResultSet consultar(String SQL){
		this.conectar();
		ResultSet resultado = null;
		Statement sentencia = null;
		
		try{
			sentencia = connection.createStatement();
			resultado = sentencia.executeQuery(SQL);
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return resultado;
	}
	
	public void insertar(String SQL){
		this.conectar();
		Statement sentencia = null;
		
		try{
			sentencia = connection.createStatement();
			sentencia.execute(SQL);
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "Error al introducir los datos. Compruebe si no existe un registro igual", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void update(String SQL){
		this.conectar();
		Statement sentencia = null;
		
		try{
			sentencia = connection.createStatement();
			sentencia.execute(SQL);
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "Error al introducir los datos. Compruebe si no existe un registro igual", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void delete(String SQL){
		this.conectar();
		Statement sentencia = null;
		
		try{
			sentencia = connection.createStatement();
			sentencia.execute(SQL);
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "Error al introducir los datos. Compruebe si no existe un registro igual", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void cerrarConexion(){
		connection.close();
	}
}
