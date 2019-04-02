import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import net.xqj.exist.ExistXQDataSource;

/*
 * Problemas Actualizaci�n con XQuery
 * Ejercicio 1: Copia el ejemplo anterior y compl�talo para que se pueda ejecutar
 * 
 */

public class actualizacion1 {
	public static void main(String[] args) {
		try {
			// Configuramos conexi�n como hemos hecho en ocasiones anteriores
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "192.168.56.102");
			server.setProperty("port", "8080");
			server.setProperty("user", "admin");
			server.setProperty("password", "austria");
			XQConnection conn = server.getConnection();

			XQExpression consulta = conn.createExpression();

			// Ejecutamos la expresi�n XQuery: actualiza el apellido del empleado con
			// EMP_NO=7369 a 1009
			String actual = "update value " + "/EMPLEADOS/EMP_ROW[EMP_NO=7369]/APELLIDO " + "with 'NuevoApellido'";

			consulta.executeCommand(actual);

		} catch (XQException e) {
			e.printStackTrace();
		}
	}
}