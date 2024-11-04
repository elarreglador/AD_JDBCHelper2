
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCHelper {
	
	public static void main(String[] args) {
		
		// EJEMPLO DE USO
		
		// TODO: Asegurar funcionamiento con bd de tipo hsqldb
		
		Connection con = null;
		
		try {
			
		// Conectamos/creamos una BD
		System.out.println("Conectamos/creamos la BD");
		//String tipoBD = "sqlite";
		String tipoBD = "hsqldb";
		File archivo = new File("ejemplo.bd");
		con = JDBCHelper.conectaBD(tipoBD, archivo);
		System.out.println();
		
		// Muestra info de la BD
		System.out.println("Muestra info de la BD");
		JDBCHelper.infoBD(con);
		System.out.println();
		
		// Existe la tabla alumnos?
		System.out.println("Existe la tabla alumnos?: " + JDBCHelper.existeTabla(con, "alumnos"));
		System.out.println();
		
		// Si no existe creamos tabla alumnos
		System.out.println("Si no existe creamos tabla alumnos");
		if (!JDBCHelper.existeTabla(con, "alumnos")) {
			JDBCHelper.agregaTabla(con, "alumnos");
			System.out.println(" - Creada tabla alumnos");
		} else {
			System.out.println(" - Tabla alumnos ya existia");
		}
		System.out.println();
		
		// Existe la tabla alumnos?
		System.out.println("Existe la tabla alumnos?: " + JDBCHelper.existeTabla(con, "alumnos"));
		System.out.println();
		
		// Muestra la tabla alumnos
		System.out.println("Muestra la tabla alumnos");
		JDBCHelper.muestraTabla(con, "alumnos");
		System.out.println();
		
		// Muestra las columnas de la tabla alumnos
		System.out.println("Muestra las columnas de la tabla alumnos");
		String [] columnasAlumnos = JDBCHelper.getColumnas(con, "alumnos");
		for (String columna : columnasAlumnos) {
			System.out.print("| " + columna + " |");
		}
		System.out.println("\n");
		
		// Agregamos columnas si no existen
		System.out.println("Agregamos columnas si no existen");
		if (!JDBCHelper.existeColumna(con, "alumnos", "nombre")) {
			agregaColumna(con, "alumnos", "nombre", "TEXT");
			System.out.println(" - Creada columna nombre");
		} else {
			System.out.println(" - Columna nombre ya existia");
		}
		if (!JDBCHelper.existeColumna(con, "alumnos", "apellidos")) {
			agregaColumna(con, "alumnos", "apellidos", "TEXT");
			System.out.println(" - Creada columna apellidos");
		} else {
			System.out.println(" - Columna apellidos ya existia");
		}
		if (!JDBCHelper.existeColumna(con, "alumnos", "mail")) {
			agregaColumna(con, "alumnos", "mail", "TEXT");
			System.out.println(" - Creada columna mail");
		} else {
			System.out.println(" - Columna mail ya existia");
		}
		System.out.println();
		
		// Muestra las columnas de la tabla alumnos
		System.out.println("Muestra las columnas de la tabla alumnos");
		columnasAlumnos = JDBCHelper.getColumnas(con, "alumnos");
		for (String columna : columnasAlumnos) {
			System.out.print("| " + columna + " |");
		}
		System.out.println("\n");
		
		// Agregamos contenido a la tabla alumnos
		System.out.println("Agregamos contenido a la tabla alumnos");
		String misAlumnos[][] = {
				{"Juan", "Garcia", "juanGarcia@mail.com"},
				{"Pedro", "Montes", "pedroMontes@myMail.es"},
				{"Isidro", "Matina", "isiMat@mail.it"},
				{"Jhon", "Mojacar", "jhonMoja@trueHistory.com"}
		};
		JDBCHelper.escribeContenidoTabla(con, "alumnos", misAlumnos);
		System.out.println();

		// Muestra el contenido de la tabla
		System.out.println("Muestra el contenido de la tabla");
		JDBCHelper.muestraTabla(con, "alumnos");
		System.out.println();

		// Almacena el contenido de la tabla en un Array bidimensional
		System.out.println("Almacena el contenido de la tabla en un Array bidimensional");
		String[][] miArray = JDBCHelper.leeTabla(con, "alumnos");
		for (int i = 0; i < miArray.length; i++) {
			System.out.print("{");
            for (int j = 0; j < miArray[i].length; j++) {
            	if (j>0) {
            		System.out.print(", ");
            	}
                System.out.print(miArray[i][j]);
            }
            System.out.println("}");
        }		
		System.out.println();
		
		// Borra el contenido de la tabla
		System.out.println("Borra el contenido de la tabla");
		JDBCHelper.borraContenidoTabla(con, "alumnos");
		System.out.println();
		
		// Muestra el contenido de la tabla
		System.out.println("Muestra el contenido de la tabla");
		JDBCHelper.muestraTabla(con, "alumnos");
		System.out.println();
		
		// Borra la tabla alumnos
		// Importante desconectar y conectar previo a borrado
		con.close();
		con = conectaBD(tipoBD, archivo);
		System.out.println("Borra la tabla alumnos");
		borraTabla(con, "alumnos");
		System.out.println();

		
		// Existe la tabla alumnos?
		System.out.println("Existe la tabla alumnos?: " + existeTabla(con, "alumnos"));
		System.out.println();

		// Cerramos BD
		System.out.println("Cerramos BD");
		con.close();
			
		} catch (SQLException e) {
			System.out.println("Exepcion:\n" + e);
		}
		
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("Exepcion al cerrar la BD:\n" + e);
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	// CONEXION BD Y METADATA


	/**
	 * Establece una conexión con una base de datos SQLite o HSQLDB.
	 * Warning, no olvides cerrar la bd: con.close();
	 * 
	 * @param tipoBD Tipo de base de datos ("sqlite" o "hsqldb").
	 * @param archivo Archivo que contiene la base de datos.
	 * @return Objeto Connection que representa la conexión establecida.
	 * @throws SQLException Si ocurre un error al establecer la conexión.
	 */
	public static Connection conectaBD(String tipoBD, File archivo) throws SQLException {
		if (!tipoBD.equals("sqlite") && !tipoBD.equals("hsqldb")) {
			return null;
		}
		String StrJDBC = "jdbc:" + tipoBD + ":" + archivo;
		return DriverManager.getConnection(StrJDBC);
	}

	
	/**
	 * Muestra la información de la base de datos conectada.
	 * 
	 * @param con Objeto Connection a la base de datos.
	 * @return Siempre retorna 0.
	 * @throws SQLException Si ocurre un error al obtener los metadatos de la base de datos.
	 */
	public static int infoBD(Connection con) throws SQLException {
		DatabaseMetaData metaData = con.getMetaData();
		System.out.println("----------------------------------");
		System.out.println("INFORMACIÓN DE LA BASE DE DATOS");
		System.out.println("----------------------------------");
		System.out.println("Nombre: " + metaData.getDatabaseProductName());
		System.out.println("Driver: " + metaData.getDriverName());
		System.out.println("URL: " + metaData.getURL());
		System.out.println("Usuario: " + metaData.getUserName());
		return 0;
	}
	

	/**
	 * Verifica si una tabla existe en la base de datos.
	 * 
	 * @param con Objeto Connection a la base de datos.
	 * @param tableName Nombre de la tabla a verificar.
	 * @return true si la tabla existe, false en caso contrario.
	 * @throws SQLException Si ocurre un error al acceder a los metadatos de la base de datos.
	 */
	public static boolean existeTabla(Connection con, String tableName) throws SQLException {
		ResultSet rs = con.getMetaData().getTables(null, null, tableName, null);
		return rs.next();
	}
	
	
	/**
	 * Verifica si una columna existe en una tabla de la base de datos.
	 * 
	 * @param con Objeto Connection a la base de datos.
	 * @param tabla Nombre de la tabla donde se busca la columna.
	 * @param nombreColumna Nombre de la columna a verificar.
	 * @return true si la columna existe, false en caso contrario.
	 * @throws SQLException Si ocurre un error al acceder a los metadatos de la base de datos.
	 */
	public static boolean existeColumna(Connection con, String tabla, String nombreColumna) throws SQLException {
	    ResultSet rs = con.getMetaData().getColumns(null, null, tabla, nombreColumna);
	    return rs.next(); // Si hay resultados, la columna existe
	}
	
	
	/**
	 * Obtiene los nombres de las columnas de una tabla en la base de datos.
	 * 
	 * @param con Objeto Connection a la base de datos.
	 * @param tabla Nombre de la tabla de la que se quieren obtener las columnas.
	 * @return Un arreglo de Strings con los nombres de las columnas, o null si no se encuentran columnas.
	 * @throws SQLException Si ocurre un error al acceder a los metadatos de la base de datos.
	 */
	public static String[] getColumnas(Connection con, String tabla) throws SQLException {
	    // Obtener metadatos de la base de datos
	    ResultSet rs = con.getMetaData().getColumns(null, null, tabla, null);
	    
	    // Usar una lista para almacenar los nombres de las columnas
	    List<String> columnas = new ArrayList<>();
	    
	    // Iterar sobre los resultados y agregar los nombres de las columnas a la lista
	    while (rs.next()) {
	        String nombreColumna = rs.getString("COLUMN_NAME");
	        columnas.add(nombreColumna);
	    }
	    
	    // Convertir la lista a un arreglo y devolverlo
	    return columnas.toArray(new String[0]);
	}

	
	// GESTION DE TABLAS


	/**
	 * Crea una tabla con una columna "id" como clave primaria, autoincremental.
	 * La estructura depende de la base de datos (SQLite o HSQLDB).
	 * 
	 * @param con Objeto Connection a la base de datos.
	 * @param nombreTabla Nombre de la tabla a crear.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL de creación o si la base de datos no es soportada.
	 */
	public static void agregaTabla(Connection con, String nombreTabla) throws SQLException {
        String dbName = con.getMetaData().getDatabaseProductName().toLowerCase();
        System.out.println(dbName);
        String sql;
        if (dbName.contains("sqlite")) {
            sql = "CREATE TABLE " + nombreTabla + " (id INTEGER PRIMARY KEY AUTOINCREMENT);";
        } else if (dbName.contains("hsql")) {
            sql = "CREATE TABLE " + nombreTabla + " (id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY);";
        } else {
            throw new SQLException("Base de datos no soportada.");
        }
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
	

	/**
	 * Agrega una nueva columna a una tabla existente.
	 * 
	 * @param con Objeto Connection a la base de datos.
	 * @param tabla Nombre de la tabla donde se agregará la columna.
	 * @param nombreColumna Nombre de la columna a agregar.
	 * @param tipoDato Tipo de dato de la nueva columna.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 */
	public static void agregaColumna(Connection con, String tabla, String nombreColumna, String tipoDato) throws SQLException {
        String sql = "ALTER TABLE " + tabla + " ADD COLUMN " + nombreColumna + " " + tipoDato;
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
	
	
	/**
	 * Elimina una tabla de la base de datos si existe.
	 * WARNING: Importante desconectar y conectar previo a borrado
	 * 
	 * @param con Objeto Connection a la base de datos.
	 * @param nombreTabla Nombre de la tabla a eliminar.
	 * @throws SQLException Si ocurre un error al ejecutar la eliminación.
	 */
    public static void borraTabla(Connection con, String nombreTabla) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + nombreTabla;
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

	
	// CONSULTAS DE CONTENIDO

	
	/**
	 * Muestra el contenido de una tabla en la consola.
	 * 
	 * @param con Objeto Connection a la base de datos.
	 * @param nombreTabla Nombre de la tabla a mostrar.
	 * @throws SQLException Si ocurre un error al realizar la consulta o al obtener los metadatos de la tabla.
	 */
	public static void muestraTabla(Connection con, String nombreTabla) throws SQLException {
	    try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM " + nombreTabla)) {
	        ResultSetMetaData rsMetaData = rs.getMetaData();
	        int columnCount = rsMetaData.getColumnCount();

	        if (!rs.isBeforeFirst()) {
	            System.out.println("| No hay datos en la tabla |");
	            return;
	        }

	        String[] columnasAlumnos = JDBCHelper.getColumnas(con, "alumnos");
			for (String columna : columnasAlumnos) {
				System.out.print("| " + columna + " |");
			}
			System.out.println();
	        
	        while (rs.next()) {
	            StringBuilder row = new StringBuilder("| ");
	            for (int i = 1; i <= columnCount; i++) {
	                Object valor = rs.getObject(i);
	                String valorStr = valor == null 
	                		? "NULL" 
	                		: valor.toString();
	                row.append(valorStr + " | ");
	            }
	            System.out.println(row);
	        }
	    }
	}
	
	
	/**
	 * Retorna un array bidimensional con el contenido de la tabla
	 * 
	 * @param con Objeto Connection a la base de datos.
	 * @param nombreTabla Nombre de la tabla a mostrar.
	 * @throws SQLException Si ocurre un error al realizar la consulta o al obtener los metadatos de la tabla.
	 */
	public static String[][] leeTabla(Connection con, String tabla) throws SQLException {
	    // Consulta para seleccionar todo el contenido de la tabla
	    String sql = "SELECT * FROM " + tabla;
	    List<String[]> resultados = new ArrayList<>();

	    try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
	        
	        // Obtener el número de columnas
	        ResultSetMetaData rsMetaData = rs.getMetaData();
	        int columnCount = rsMetaData.getColumnCount();
	        
	        // Leer los resultados y agregar a la lista
	        while (rs.next()) {
	            String[] fila = new String[columnCount];
	            
	            for (int i = 1; i <= columnCount; i++) { // Comenzar en 1
	                Object valor = rs.getObject(i);
	                fila[i - 1] = (valor != null) ? valor.toString() : "NULL"; // Asignar en i-1
	            }
	            resultados.add(fila); // Agregar la fila a los resultados
	        }
	    }

	    return resultados.toArray(new String[0][0]);
	}



	
	/**
	 * Inserta datos en una tabla usando un arreglo 2D de valores.
	 * 
	 * @param con Objeto Connection a la base de datos.
	 * @param tabla Nombre de la tabla donde se insertarán los datos.
	 * @param contenido Array con los valores a insertar (cada fila es un registro).
	 * @return Número de filas insertadas.
	 * @throws SQLException Si ocurre un error al ejecutar la inserción.
	 */
	public static int escribeContenidoTabla(Connection con, String tabla, String[][] contenido) throws SQLException {
	    int columnas = contenido[0].length;
	    // Especificar las columnas en la consulta
	    StringBuilder consulta = new StringBuilder("INSERT INTO " + tabla + " (nombre, apellidos, mail) VALUES (");
	    for (int i = 0; i < columnas; i++) {
	        consulta.append("?");
	        if (i < columnas-1) {
	            consulta.append(", ");
	        }
	    }
	    consulta.append(");");
	    
	    System.out.println(consulta);

	    try (PreparedStatement pstmt = con.prepareStatement(consulta.toString())) {
	        for (String[] registro : contenido) {
	            for (int i = 0; i < columnas; i++) {
	                pstmt.setString(i + 1, registro[i]);
	            }
	            pstmt.addBatch();
	        }
	        int[] resultados = pstmt.executeBatch();
	        return resultados.length;
	    }
	}

	
	/**
	 * Elimina todo el contenido de una tabla sin eliminar su estructura.
	 * 
	 * @param con Objeto Connection a la base de datos.
	 * @param nombreTabla Nombre de la tabla a vaciar.
	 * @throws SQLException Si ocurre un error al ejecutar la eliminación.
	 */
    public static void borraContenidoTabla(Connection con, String nombreTabla) throws SQLException {
        String sql = "DELETE FROM " + nombreTabla;
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
}
