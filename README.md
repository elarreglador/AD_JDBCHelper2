# JDBCHelper2

## CONEXION BD Y METADATA
### conectaBD()
Establece conexiones a bases de datos SQLite y HSQLDB.
### infoBD()
Proporciona información general sobre la base de datos.
### existeTabla()
Verifica la existencia de una tabla.
### existeColumna()
Verifica la existencia de una columna.
### getColumnas()
Obtiene los nombres de las columnas de una tabla.

## GESTION DE TABLAS
### agregaTabla()
Crea una tabla con una columna "id" como clave primaria, autoincremental.
### agregaColumna()
Agrega una nueva columna a una tabla existente.
### borraTabla()
Elimina una tabla de la base de datos si existe.

## CONSULTAS DE CONTENIDO
### muestraTabla()
Muestra el contenido de una tabla en la consola.
### leeTabla()
Retorna un array bidimensional con el contenido de la tabla
### escribeContenidoTabla()
Inserta datos en una tabla usando un Array bidimensional.
### borraContenidoTabla()
Elimina todo el contenido de una tabla sin eliminar su estructura.



## Sugerencias para Mejoras y posibles ampliaciones
Sientete libre de hacer un fork, si me gusta tu mejora la agregare.

## TODO:
  - Solucionar problemas con bd de tipo HSQLDB