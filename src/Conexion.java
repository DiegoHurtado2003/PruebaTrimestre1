import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;


public class Conexion {

    private static final String usuario = "ad2223_dhurtado";
    private static final String contrasena = "1234";
    private static final String servidor = "jdbc:mysql://dns11036.phdns11.es";

    private static Statement st = null;
    public static Connection conexion = null;
    private static final String s = System.lineSeparator();

    private static boolean esPrimeraVuelta = true;

    /**
     * Método que crea la conexión con la base de datos y devuelve un statement
     * con el que trabajar en los siguientes de métodos
     *
     * @return Un statement con el que trabajar en el resto de la BBDDD
     */
    public static Statement conectarBADAT() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(servidor, usuario, contrasena);
            if (conexion != null)
                st = conexion.createStatement();
            else
                System.out.println("Error");
        } catch (ClassNotFoundException e) {
            System.err.println("Error, clase no encontrada. " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error en el SQL. " + e.getMessage());
        }
        return st;
    }


    /**
     * Método para crear una tabla en una base de datos pasandole como parámetros
     * el nombre de la tabla y los campos de la misma.
     *
     * @param tabla  Nombre de la tabla a crear
     * @param campos sql con los campos que contendrá la tabla
     */
    static void crearTabla(String tabla, String[] campos) {
        String sql = "CREATE TABLE " + "ad2223_dhurtado." + tabla + "(";
        sql += campos[0];
        for (int i = 1; i < campos.length; i++) {
            sql += ", " + campos[i];
        }
        sql += ")";
        System.out.println(sql);
        try {
            st.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que inserta los datos iniciales a las tablas Personas y libros,
     * esto lo hace leyendo el documento txt proporcionada por el profesor.
     * Le asigna el valor nombre a la tabla Profesores (el id se autoincrementa, y el tipo se actualizará
     * en un método posteriror pues aún no sabemos si será lector o escritor.)
     * Cuando llega a la line del txt con dos almohadillas dejará de insertar en la tabla
     * Personas y empezrá a insertar en la tabla Libros, pero solo el nombre porque el id es
     * autoincrement y aún no sabemos quien será su autor.
     *
     * @param rutaArchivo ruta del archivo de inserción de datos
     */
    public static void insertarDatosPersonas(String rutaArchivo) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
            String linea;
            String sqlPersonas;
            String sqlLibros;
            linea = br.readLine();

            while (linea != null) {
                sqlPersonas = "insert into ad2223_dhurtado.Personas (nombre) values ('" + linea + "');";
                linea = br.readLine();
                st.executeUpdate(sqlPersonas);
            /*
                while(!Objects.equals(linea, "##")){

                    sqlPersonas = "insert into ad2223_dhurtado.Personas (nombre) values ('" + linea + "');";
                    linea = br.readLine();
                    st.executeUpdate(sqlPersonas);
                }

                for (int i=0; i<26 ; i++){
                    sqlPersonas = "insert into ad2223_dhurtado.Personas (nombre) values ('" + linea + "');";
                    linea = br.readLine();
                    st.executeUpdate(sqlPersonas);
                }
                sqlLibros = "insert into ad2223_dhurtado.Libros (titulo) values ('" + linea + "');";
                linea = br.readLine();
                st.executeUpdate(sqlLibros);
*/
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método que inserta los datos de los Libros
     *
     * @param rutaArchivo
     */
    public static void insertarDatosLibros(String rutaArchivo) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
            String linea;
            String sqlLibros;
            linea = br.readLine();

            while (linea != null) {
                sqlLibros = "insert into ad2223_dhurtado.Libros (titulo) values ('" + linea + "');";
                linea = br.readLine();
                st.executeUpdate(sqlLibros);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método que elimina completamente la tabla pasada por parámetro
     * @param tabla tabla a eliminar
     */
    public static void borrarTabla(String tabla) {
        String sql = "DROP TABLE ad2223_dhurtado." + tabla + ";";
        try {
            st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void inciarPrimeraVuelta() {
        if (esPrimeraVuelta) {
            for (int i = 1; i <= 10; i++) {
                Random rand = new Random();
                int Posibilidad = (int) (Math.random() * 10);
                if (Posibilidad < 2) {

                    String comando = "UPDATE  ad2223_dhurtado.Personas SET tipo = 'e' WHERE (idPersona =" + i + ")";
                    asignarLibroAleatorio();
                    try {
                        st.executeUpdate(comando);
                    } catch (SQLException e) {
                        System.err.println("Error en el SQL. " + e.getMessage());
                    }

                } else {

                    String comando = "UPDATE  ad2223_dhurtado.Personas SET tipo = 'l' WHERE (idPersona =" + i + ")";
                    asignarLibroYvaloracionAleatoria();
                    try {
                        st.executeUpdate(comando);
                    } catch (SQLException e) {
                        System.err.println("Error en el SQL. " + e.getMessage());
                    }

                }
            }
        }
        else {
            System.out.println("Estamos en ello");
        }


        esPrimeraVuelta = false;
    }


    /**
     * Método que asigna un libro aleatorio a una persona de tipo
     * lector modificando la tabla Lectura y este le otorga
     * una valoración también de forma aleatoria entre 1 y 5
     */
    private static void asignarLibroYvaloracionAleatoria() {
        System.out.println("Estamos en ello");
    }


    /**
     * Método que asigna un libro a un escritor y hace que este sea
     * su autor
     */
    private static void asignarLibroAleatorio() {
        System.out.println("Estamos en ello");
    }

    /**
     * Método que devuelve los tres libros con mejores valoraciones
     */
    private static void top3LibrosMasValorados() {
        String sql = "SELECT * FROM ad2223_dhurtado.Lecturas order by puntos limit 3 ";
        try {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Método que devuelve los tres libros con más cantidad de lecturas
     */
    private static void top3LibrosMasLeidos() {
        System.out.println("Estamos en ello");
    }

    /**
     * Método que devuelve los tres escritors cuyos libros esten mejor valorados
     */
    private static void top3Escritores() {
        System.out.println("Estamos en ello");
    }


    /**
     * Método que maneja los distintos métodos posibles a realizar mediante el menú
     * @return
     */
    public static int menu() {
        int opcion;
        mostrarMenuOpciones();
        opcion = elegirOpcion();
        switch (opcion) {
            case 1 -> inciarPrimeraVuelta();
            case 2 -> top3LibrosMasLeidos();
            case 3 -> top3LibrosMasValorados();
            case 4 -> top3Escritores();
            case 5 -> reiniciarPrograma();
            case 6 -> System.out.println("Hasta la proxima!");
            default -> System.out.println("Opción invalida.");
        }
        return opcion;
    }

    /**
     * Método que reinicia el programa
     * eliminando el contenido de las tablas, a excepcion de la insercion inicial.
     */
    private static void reiniciarPrograma() {
        System.out.println("Estamos en ello");
    }

    /**
     * Método que muestra las distintas opciones del menú
     */
    private static void mostrarMenuOpciones() {
        System.out.println("=====================================" + s);
        System.out.println("""
                -------------------------------
                Elija una opcion:
                    1.- Iniciar Vuelta
                    2.- Mostrar libros más leídos
                    3.- Mostrar libros más valorados
                    4.- Mostrar escritores más valorados
                    5.- Reiniciar programa
                    6.- Salir
                -------------------------------""");
    }


    /**
     * Método que valida las opciones de un menú para controlar que no hayan errores
     *
     * @return La opcion seleccionada una vez se introduzca una opción válida
     */
    private static int elegirOpcion() {
        Scanner sc = new Scanner(System.in);
        int opc;
        do {
            try {
                System.out.print("Escoja una opción -> ");
                opc = sc.nextInt();
                if (opc < 0 || opc > 6)
                    System.out.println("Introduzca un numero valido (1, 2, 3, 4, 5, 6).");
            } catch (
                    Exception e) {
                System.out.println("Introduzca un numero valido (1, 2, 3, 4, 5, 6).");
                sc.next();
                opc = -1;
            }
        } while (opc < 0 || opc > 6);
        return opc;
    }
}
