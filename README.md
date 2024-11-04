# JDBCHelper2

## CONEXION BD Y METADATA
### conectaBD()
Establece conexiones a bases de datos SQLite y HSQLDB.

conectaBD(String tipoBD, File archivo):

Parámetros:
tipoBD: Cadena que indica el tipo de base de datos (actualmente soporta "sqlite" y "hsqldb").
archivo: Objeto File que representa el archivo de la base de datos.

Retorno: 
Un objeto Connection que representa la conexión establecida a la base de datos, o null si ocurre un error.

### infoBD()
Proporciona información general sobre la base de datos.

infoBD(Connection con):

Parámetros:
con: Objeto Connection a la base de datos.

Retorno: 
Un entero (siempre 0 en esta implementación), aunque podría modificarse para retornar un objeto con más información detallada.

### existeTabla()
Verifica la existencia de una tabla.

existeTabla(Connection con, String tableName):

Parámetros:
con: Objeto Connection a la base de datos.
tableName: Cadena que representa el nombre de la tabla a buscar.

Retorno: 
Un valor booleano: true si la tabla existe, false en caso contrario.

### existeColumna()
Verifica la existencia de una columna.

existeColumna(Connection con, String tabla, String nombreColumna):

Parámetros:
con: Objeto Connection a la base de datos.
tabla: Cadena que representa el nombre de la tabla.
nombreColumna: Cadena que representa el nombre de la columna a buscar.

Retorno: 
Un valor booleano: true si la columna existe, false en caso contrario.

### getColumnas()
Obtiene los nombres de las columnas de una tabla.

getColumnas(Connection con, String tabla):

Parámetros:
con: Objeto Connection a la base de datos.
tabla: Cadena que representa el nombre de la tabla.

Retorno: 
Un arreglo de cadenas (String[]) que contiene los nombres de las columnas de la tabla, o null si ocurre un error.

## GESTION DE TABLAS
### agregaTabla()
Crea una tabla con una columna "id" como clave primaria, autoincremental.

agregaTabla(Connection con, String nombreTabla):

Parámetros:
con: Objeto Connection a la base de datos.
nombreTabla: Cadena que representa el nombre de la tabla a crear.

### agregaColumna()
Agrega una nueva columna a una tabla existente.

agregaColumna(Connection con, String tabla, String nombreColumna, String tipoDato):

Parámetros:
con: Objeto Connection a la base de datos.
tabla: Cadena que representa el nombre de la tabla.
nombreColumna: Cadena que representa el nombre de la columna a agregar.
tipoDato: Cadena que representa el tipo de dato de la columna.

### borraTabla()
Elimina una tabla de la base de datos si existe.

borraTabla(Connection con, String nombreTabla):

Parámetros:
con: Objeto Connection a la base de datos.
nombreTabla: Cadena que representa el nombre de la tabla a eliminar.

## CONSULTAS DE CONTENIDO
### muestraTabla()
Muestra el contenido de una tabla en la consola.

muestraTabla(Connection con, String nombreTabla):

Parámetros:
con: Objeto Connection a la base de datos.
nombreTabla: Cadena que representa el nombre de la tabla a mostrar.

### leeTabla()
Retorna un array bidimensional con el contenido de la tabla

leeTabla(Connection con, String tabla):

Parámetros:
con: Objeto Connection a la base de datos.
tabla: Cadena que representa el nombre de la tabla.

Retorno: 
Un arreglo bidimensional de cadenas (String[][]) que contiene el contenido de la tabla.

### escribeContenidoTabla()
Inserta datos en una tabla usando un Array bidimensional.

escribeContenidoTabla(Connection con, String tabla, String[][] contenido):

Parámetros:
con: Objeto Connection a la base de datos.
tabla: Cadena que representa el nombre de la tabla.
contenido: Un arreglo bidimensional de cadenas que contiene los datos a insertar.

Retorno: 
Un entero que representa el número de filas insertadas.

### borraContenidoTabla()
Elimina todo el contenido de una tabla sin eliminar su estructura.

borraContenidoTabla(Connection con, String nombreTabla):

Parámetros:
con: Objeto Connection a la base de datos.
nombreTabla: Cadena que representa el nombre de la tabla.

## Sugerencias para Mejoras y posibles ampliaciones
Sientete libre de hacer un fork, si me gusta tu mejora la agregare.

## TODO:
  - Solucionar problemas con bd de tipo HSQLDB