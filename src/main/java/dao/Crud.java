package dao;

import java.sql.SQLException;
import java.util.List;

public interface Crud<T> {

    void insertar(T obj) throws SQLException;
    void actualizar( T obj ) throws SQLException;
    void eliminar( T obj ) throws SQLException;
    List<T> listar() throws SQLException;
    T obtener( long id ) throws SQLException;

}
