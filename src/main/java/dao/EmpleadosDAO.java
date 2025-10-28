package dao;

import modelo.Departamento;
import modelo.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadosDAO implements Crud<Empleado> {

    private final Connection con;
    public EmpleadosDAO(Connection connection) {
        con = connection;
    }

    @Override
    public void insertar(Empleado obj) throws SQLException {
        String sql = "INSERT INTO empleados ( dni, nombre, sueldo, iddepartamento ) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, obj.getDni());
            pst.setString(2, obj.getNombre());
            pst.setFloat(3, obj.getSueldo());
            pst.setLong(4, obj.getDepartamento().getIdDepartamento());

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) obj.setIdEmpleado(rs.getLong(1));
        }
    }

    @Override
    public void actualizar(Empleado obj) throws SQLException {
        String sql = "UPDATE empleados SET " +
                "dni = ? " +
                ", nombre = ? " +
                ", sueldo = ? " +
                ", iddepartamento = ? " +
                "WHERE idempleado = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, obj.getDni());
            pst.setString(2, obj.getNombre());
            pst.setFloat(3, obj.getSueldo());
            pst.setLong(4, obj.getDepartamento().getIdDepartamento());
            pst.setLong(5, obj.getIdEmpleado());
            pst.executeUpdate();
        }
    }

    @Override
    public void eliminar(Empleado obj) throws SQLException {
        String sql = "DELETE FROM empleados WHERE idempleado = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, obj.getIdEmpleado());
            pst.executeUpdate();
        }
    }

    @Override
    public List<Empleado> listar() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        Crud<Departamento> departamentoDAO = new DepartamentosDAO(con);
        String sql = "SELECT idempleado, " +
                "dni, " +
                "nombre, " +
                "sueldo, " +
                "iddepartamento " +
                "FROM empleados";
        try (Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getLong("idempleado"));
                empleado.setDni(rs.getString("dni"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setSueldo(rs.getFloat("sueldo"));
                Departamento departamento = departamentoDAO.obtener(rs.getLong("iddepartamento"));
                empleado.setDepartamento(departamento);
                empleados.add(empleado);
            }
        }
        return empleados;
    }

    @Override
    public Empleado obtener(long id) throws SQLException {
        Empleado empleado = null;
        Crud<Departamento> departamentoDAO = new DepartamentosDAO(con);
        String sql = "SELECT idempleado, " +
                "dni, " +
                "nombre, " +
                "sueldo, " +
                "iddepartamento " +
                "FROM empleados " +
                "WHERE idempleado = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                empleado = new Empleado();
                empleado.setIdEmpleado(rs.getLong("idempleado"));
                empleado.setDni(rs.getString("dni"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setSueldo(rs.getFloat("sueldo"));
                Departamento departamento = departamentoDAO.obtener(rs.getLong("iddepartamento"));
                empleado.setDepartamento(departamento);
            }
        }
        return empleado;
    }
}
