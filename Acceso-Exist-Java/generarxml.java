import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.xquery.*;
import net.xqj.exist.ExistXQDataSource;

/*
 * Problemas XQJ_2
 * Ejercicio 2: A partir de los documentos productos.xml y zonas.xml, haz un programa que
 * reciba un número de zona por parámetro y genere un documento con nombre
 * zonaXX.xml donde XX es la zona solicitada. El documento debe contener los
 * productos de esta zona y las siguientes etiquetas: <cod_prod>,
 * <denominación>, <precio>, <nombre_zona>, <director> y <stock>. Donde el
 * stock se calcula restando el stock actual y el stock mínimo.
 */

public class generarxml {
	public static void main(String[] args) throws IOException {
		try {
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "192.168.56.102");
			server.setProperty("port", "8080");
			server.setProperty("user", "admin");
			server.setProperty("password", "austria");
			XQConnection conn = server.getConnection();
			XQPreparedExpression consulta;
			XQResultSequence resultado;

			// Datos introducidos por el usuario
			BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Introduce la zona: ");
			String zona = bReader.readLine();

			consulta = conn.prepareExpression("for $prod in /productos/produc[cod_zona=" + zona + "] "
					+ "let $nombre_zona:=//zona[cod_zona = $prod/cod_zona]/nombre "
					+ "let $director:=//zona[cod_zona = $prod/cod_zona]/director " + "return" + "   <produc>"
					+ "	<cod_prod>{data($prod/cod_prod)}</cod_prod> "
					+ "	<denominación>{data($prod/denominacion)}</denominación> "
					+ "	<precio>{data($prod/precio)}</precio> " + "	<nombre_zona>{data($nombre_zona)}</nombre_zona> "
					+ "	<director>{data($director)}</director> "
					+ "	<stock>{data($prod/stock_actual)-data($prod/stock_minimo)}</stock> " + "	</produc>");

			resultado = consulta.executeQuery();

			// Creamos fichero
			File file = new File("src/zona" + zona + ".xml");
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

			// Escribimos en el fichero
			bWriter.write("<?xml version='1.0' encoding='ISO-8859-1'?>"); // ISO.8859-1 por los acentos
			bWriter.write("<productos>");
			if (resultado.next() != false) {
				do {
					bWriter.newLine();
					bWriter.write(resultado.getItemAsString(null));
				} while (resultado.next());
			} else {
				System.out.println("unknown error");
			}
			bWriter.write("</productos>");
			bWriter.close();
			bReader.close();
			conn.close();

		} catch (XQException ex) {
			System.out.println("Error al operar" + ex.getMessage());
		}
	}
}