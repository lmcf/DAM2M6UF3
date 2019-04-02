import javax.xml.xquery.*;
import net.xqj.exist.ExistXQDataSource;

/*
 * Problemas XQJ_1
 * Ejercicio 4: Realiza un programa que devuelva todos los empleados del departamento 10.
 * 
 */

public class empleadosdep10 {
	public static void main(String[] args) {
		try {
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "192.168.56.102");
			server.setProperty("port", "8080");
			server.setProperty("user", "admin");
			server.setProperty("password", "austria");
			XQConnection conn = server.getConnection();
			XQPreparedExpression consulta;
			XQResultSequence resultado;
			
			consulta = conn.prepareExpression("for $empleados in doc('nueva/empleados.xml')//EMP_ROW[DEPT_NO= 10] return $empleados");
			resultado = consulta.executeQuery();
			
			while (resultado.next()) {
				System.out.println(resultado.getItemAsString(null));
			}
			conn.close();
			
		} catch (XQException ex) {
			System.out.println("Error al operar" + ex.getMessage());
		}
	}
}