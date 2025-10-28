package servicio;

import dao.Crud;
import dao.DepartamentosDAO;
import dao.EmpleadosDAO;
import datasourse.DataBase;
import modelo.Departamento;
import modelo.Empleado;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServicioImpl implements Servicio {

    @Override
    public void altaDepartamento(Departamento departamento) throws SQLException {
        try (Connection conn = DataBase.getConnection()) {
            Crud<Departamento> dao = new DepartamentosDAO(conn);
            dao.insertar(departamento);
        }
    }

    @Override
    public void altaEmpleado(Empleado empleado) throws SQLException {
        try (Connection conn = DataBase.getConnection()) {
            Crud<Empleado> dao = new EmpleadosDAO(conn);
            dao.insertar(empleado);
        }
    }

    @Override
    public List<Departamento> listarDepartamentos() throws SQLException {
        List<Departamento> departamentos;
        try (Connection conn = DataBase.getConnection()) {
            Crud<Departamento> dao = new DepartamentosDAO(conn);
            departamentos = dao.listar();
        }
        return departamentos;
    }

    @Override
    public List<Empleado> listarEmpleados() throws SQLException {
        List<Empleado> empleados;
        try (Connection conn = DataBase.getConnection()) {
            Crud<Empleado> dao = new EmpleadosDAO(conn);
            empleados = dao.listar();
        }
        return empleados;
    }

    @Override
    public Departamento obtenerDepartamento(long id) throws SQLException {
        Departamento departamento;
        try (Connection conn = DataBase.getConnection()) {
            Crud<Departamento> dao = new DepartamentosDAO(conn);
            departamento = dao.obtener(id);
        }
        return departamento;
    }

    @Override
    public Empleado obtenerEmpleado(long id) throws SQLException {
        Empleado empleado;
        try (Connection conn = DataBase.getConnection()) {
            Crud<Empleado> dao = new EmpleadosDAO(conn);
            empleado = dao.obtener(id);
        }
        return empleado;
    }

    @Override
    public void actualizarEmpleado(Empleado empleado) throws SQLException {
        try (Connection conn = DataBase.getConnection()) {
            Crud<Empleado> dao = new EmpleadosDAO(conn);
            dao.actualizar(empleado);
        }
    }

    @Override
    public void bajaEmpleado(Empleado empleado) throws SQLException {
        try (Connection conn = DataBase.getConnection()) {
            Crud<Empleado> dao = new EmpleadosDAO(conn);
            dao.eliminar(empleado);
        }
    }

    @Override
    public Map<String, Double> saldoMedioEmpleadosByDepartamento() throws SQLException {
        Map<String, Double> saldoMedio;
        try (Connection conn = DataBase.getConnection()) {
            EmpleadosDAO dao = new EmpleadosDAO(conn);
            List<Empleado> empleados = dao.listar();
            saldoMedio = empleados.stream()
                    .collect(Collectors.groupingBy(e -> e.getDepartamento().getNombre(),
                            Collectors.averagingDouble(Empleado::getSueldo)));
        }
        return saldoMedio;
    }
}
