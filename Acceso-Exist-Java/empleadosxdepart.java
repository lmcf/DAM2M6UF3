import java.util.Scanner;
import javax.xml.xquery.*;
import net.xqj.exist.ExistXQDataSource;

/*
 * Problemas XQJ_2
 * Ejercicio 1: A partir del documento universidad.xml, haz un programa que muestre los
 * empleados del departamento cuyo tipo es elegido por el usuario. Si no hay
 * empleados o el tipo de departamento aportado por el usuario no existe, se
 * debe de informar al usuario.
 * 
 */

public class empleadosxdepart {
	public static void main(String[] args) {
		try {
			// Conexion con el servidor i
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "192.168.56.102");
			server.setProperty("port", "8080");
			server.setProperty("user", "admin");
			server.setProperty("password", "austria");
			XQConnection conn = server.getConnection();
			XQPreparedExpression consulta;
			XQPreparedExpression consulta2;
			XQResultSequence resultado;
			XQResultSequence resultado2;

			// Posibles departamentos
			consulta2 = conn.prepareExpression(
					"for $tipos in distinct-values(doc('nueva/universidad.xml')//departamento/data(@tipo)) return $tipos");
			resultado2 = consulta2.executeQuery();

			if (resultado2.next()) {
				System.out.print("Tipos departamentos existentes -> ");
				do {
					System.out.print(resultado2.getItemAsString(null) + " ");
				} while (resultado2.next());
			} else {
				System.out.println("No existen departamentos");
			}

			// Datos introducidos por el usuario
			Scanner datosUsuario = new Scanner(System.in);
			System.out.println("\nIntroduce tipo de departamento");

			// Todos los departamentos estan en mayusulas de ahi el .toUpperCase()
			String tipoDepartamento = "'" + datosUsuario.nextLine().toUpperCase() + "'";

			consulta = conn.prepareExpression("for $empleados in doc('nueva/universidad.xml')//departamento[@tipo = "
					+ tipoDepartamento + "] return $empleados");
			resultado = consulta.executeQuery();

			if (resultado.next()) {
				do {
					System.out.println(resultado.getItemAsString(null));
				} while (resultado.next());
			} else {
				System.out.println("El tipo departamento introducido no existe");
			}

			// Compruebo que haya almenos un resultado
			/*
			 * if (resultado.next()) { do {
			 * System.out.println(resultado.getItemAsString(null)); } while
			 * (resultado.next()); }else {
			 * System.out.println("El departamento introducido no existe"); }
			 */

			conn.close();
			datosUsuario.close();

		} catch (XQException ex) {
			System.out.println("Error al operar" + ex.getMessage());
		}
	}
}