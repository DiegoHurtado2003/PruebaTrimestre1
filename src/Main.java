public class Main {
    public static void main(String[] args) {
        Conexion.conectarBADAT();

        //Creación de las tablas
/*
 *         Conexion.crearTabla("Personas", new String[]{"idPersona int AUTO_INCREMENT NOT NULL PRIMARY KEY", "nombre varchar(100)","tipo char"});
        Conexion.crearTabla("Libros", new String[]{"idLibro int AUTO_INCREMENT NOT NULL PRIMARY KEY", "titulo varchar(100)", "idAutor int", "CONSTRAINT FK_PersonasLibros FOREIGN KEY (idAutor) REFERENCES Personas(idPersona)"});
        Conexion.crearTabla("Lecturas", new String[]{"idLibro int PRIMARY KEY", "idLector int not null", "puntos int", "CONSTRAINT FK_LibroLecturas FOREIGN KEY (idLibro) REFERENCES Libros(idLibro)",
                "CONSTRAINT FK_PerosnasLecturas FOREIGN KEY (idLector) REFERENCES Personas(idPersona)"}); //--> he puesto el idLector como no nulo porque desde Java no deja poner dos PK, pero después lo modifiqué en el workbench


 */



        //Inserción de datos
       // Conexion.insertarDatosPersonas_y_Libros("src/Libreria.txt");
        /*
                Conexion.insertarDatosPersonas("src/Personas.txt");
        Conexion.insertarDatosLibros("src/Libros.txt");
         */



        int opcion = 0;

        while (opcion != 6){
            opcion = Conexion.menu();
        }
    }

}