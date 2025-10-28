package dao;

import modelo.Departamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentosDAO implements Crud<Departamento>{

    private final Connection con;
    public DepartamentosDAO(Connection connection) {
        con = connection;
    }

    @Override
    public void insertar(Departamento obj) throws SQLException {
        String sql = "INSERT INTO departamentos ( nombre ) VALUES ( ? )";
        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, obj.getNombre());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) obj.setIdDepartamento(rs.getLong(1));
        }
    }

    @Override
    public void actualizar(Departamento obj) throws SQLException {
        throw new SQLException("No implementado");
    }

    @Override
    public void eliminar(Departamento obj) throws SQLException {
        throw new SQLException("No implementado");
    }

    @Override
    public List<Departamento> listar() throws SQLException {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT d.iddepartamento, " +
                "d.nombre " +
                "FROM departamentos d " +
                "ORDER BY d.nombre";
        try (var st = con.createStatement()) {
            var rs = st.executeQuery(sql);

            while (rs.next()) {
                Departamento departamento = new Departamento();
                departamento.setIdDepartamento(rs.getLong("iddepartamento"));
                departamento.setNombre(rs.getString("nombre"));
                departamentos.add(departamento);
            }
        }
        return departamentos;
    }

    @Override
    public Departamento obtener(long id) throws SQLException {
        Departamento departamento = null;
        String sql = "SELECT d.iddepartamento, " +
                "d.nombre " +
                "FROM departamentos d " +
                "WHERE d.iddepartamento = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                departamento = new Departamento(id, nombre);
            }
        }
        return departamento;
    }
}
