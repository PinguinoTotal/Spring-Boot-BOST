El proyecto ocupa las librerias:

JDBC API y HyperSQL Database

despues se le pone el JPa, en este momento no se si se necesita
el JDBC API para hacer que se conecte con la base de datos

El jpa es más importante de lo que parece, ya que en esta clase es don de se guardan todas las @ para hacer la
persistencia de datos como @Entity, etc


Para hacer uso de las bases de datos embebidas, o usar una base de datos sin tener una base de datos, es importante
usar las librerias de JDBC API (puede que haya más pero en este caso estoy viendo esto) y añadir el archivo data.sql
y schema.sql en la carpeta resources, ahi es donde seran encontradas por el compilador y creara la base de datos con estas

es posible cambiar el banner de la aplicacion llendo a una aplicacion que da la tipografia y poniendolo en un archivo banner en
resources