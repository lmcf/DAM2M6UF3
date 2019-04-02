import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import net.xqj.exist.ExistXQDataSource;

/*
 * Problemas Actualización con XQuery
 * Ejercicio 2: Realiza un programa que añada un departamento nuevo al fichero 
 * departamentos.xml. Los datos del nuevo departamento son:
 * a. DEPT_NO -> 50
 * b. DNOMBRE -> INFORMÁTICA
 * c. LOC -> Valencia
 * 
 */

public class actualizacion2 {
	public static void main(String[] args) {
		try {
			// Configuramos conexión como hemos hecho en ocasiones anteriores
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "192.168.56.102");
			server.setProperty("port", "8080");
			server.setProperty("user", "admin");
			server.setProperty("password", "austria");
			XQConnection conn = server.getConnection();

			XQExpression consulta = conn.createExpression();

			//Añadimo un nuevo departamento
			String actual = "update insert " 
					+ "<DEP_ROW> " 
						+ "<DEPT_NO>50</DEPT_NO> "
						+ "<DNOMBRE>INFORMÁTICA</DNOMBRE> " 
						+ "<LOC>VALENCIA</LOC> " 
					+ "</DEP_ROW> "
					+ " into /departamentos ";

			consulta.executeCommand(actual);

		} catch (XQException e) {
			e.printStackTrace();
		}
	}
}