package com.cice.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Manager {
    private final String DRIVER;
    private final String HOST;
    private final String PUERTO;
    private final String USER;
    private final String PASS;
    private final String DATABASE;
    private Connection connection;
    private Statement statement;


    public Manager(){
        this.DRIVER = "com.mysql.jdbc.Driver";
        this.HOST = "localhost";
        this.PUERTO = "8889";
        this.USER = "root";
        this.PASS = "root";
        this.DATABASE = "prueba";

    }

    public Manager(String DRIVER, String HOST, String PUERTO, String USER, String PASS, String DATABASE) {
        this.DRIVER = DRIVER;
        this.HOST = HOST;
        this.PUERTO = PUERTO;
        this.USER = USER;
        this.PASS = PASS;
        this.DATABASE = DATABASE;
    }

    /**
     * Metodo que genera un String con la URL construida
     * @return url construida
     */
    private String generarUrl(){
        return "jdbc:mysql://"+HOST+":"+PUERTO+"/"+DATABASE;
    }

    /**
     * Metodo que se utiliza para conectar contra una base de datos, ya sea por defecto o
     * inicializada segun los parametros del constructor
     * @return estado de conexion (true en caso afirmativo)
     */
    private boolean conectarBaseDatos(){
        boolean estaConectado = false;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(generarUrl(), USER,PASS);
            if (connection != null){
                estaConectado = true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estaConectado;
    }

    /**
     * Metodo que usaremos para desconectarnos de la base de  datos y asi liberar recursos
     * @return estado de la desconexion (true si esta desconectado)
     */
    private boolean desconectarBaseDatos(){
        boolean estaDesconectado = false;
        try {
            connection.close();
            estaDesconectado = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estaDesconectado;
    }

    /**
     * Metodo que se usa para recuperar los campos de una tabla en una base de datos
     * @param sql es el select que realiza el usuario
     * @return una lista de String con cada fila de datos en su interior
     */
    private List<String> camposConsulta(String sql){
        List<String> idcolumnas  = new ArrayList<String>();
        try {
            statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultado.getMetaData();
            for (int i = 1; i <= resultSetMetaData.getColumnCount() ; i++) {
                idcolumnas.add(resultSetMetaData.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idcolumnas;
    }

    /**
     * Metodo que devuelve una lista con los resultados de una consulta SELECT
     * @param sql  consulta SELECT
     * @return una List de String con el resultado.
     */
    public List<String> ejecutarSelect(String sql){
        conectarBaseDatos();
        List<String> campos = camposConsulta(sql);  // aquí ya se crea el Statement;
        List<String> resultadoMapeado = new ArrayList();

        try {
            ResultSet resultado = statement.executeQuery(sql);
            while (resultado.next()){
                StringBuilder linea = new StringBuilder();
                for (int i = 0; i < campos.size() ; i++) {
                    linea.append(resultado.getString(campos.get(i))).append(" ");
                }
                resultadoMapeado.add(linea.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        desconectarBaseDatos();

        return resultadoMapeado;
    }
}
