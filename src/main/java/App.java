import helpers.ConsoleHelper;
import modelo.Departamento;
import modelo.Empleado;
import servicio.Servicio;
import servicio.ServicioImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class App {

    public static void menu() {
        do {
            System.out.println("\n=== GESTIÓN DE EMPLEADOS ===");
            System.out.println("1. Alta de Departamento");
            System.out.println("2. Alta de Empleado");
            System.out.println("3. Listar Empleados por Departamento");
            System.out.println("4. Transferencia de un Empleado");
            System.out.println("5. Baja de un Empleado");
            System.out.println("6. Listado de Sueldos Medios");
            System.out.println("0. Salir");
            int op = ConsoleHelper.pedirEntero("Selecciona opción: ", 0, 6);
            switch (op) {
                case 1: {
                    altaDepartamento();
                    menu();
                }
                break;
                case 2: {
                    altaEmpleado();
                    menu();
                }
                break;
                case 3: {
                    listarEmpleadosByDepartamento();
                    menu();
                }
                break;
                case 4: {
                    transferirEmpleado();
                    menu();
                }
                break;
                case 5: {
                    bajaEmpleado();
                    menu();
                }
                break;
                case 6: {
                    saldosMediosByEmpleados();
                    menu();
                }
                break;
                case 0:
                    System.out.println("FIN GESTIÓN DE EMPLEADOS");
                    System.exit(0);
            }
        } while (true);
    }

    public static Departamento pedirDepartamento() throws SQLException {
        Servicio servicio = new ServicioImpl();
        Departamento departamento;
        List<Departamento> departamentos = servicio.listarDepartamentos();
        departamentos.forEach(System.out::println);
        long id = ConsoleHelper.pedirEntero("ID Departamento: ");
        departamento = servicio.obtenerDepartamento(id);
        return departamento;
    }

    public static void altaDepartamento() {
        try {
            Servicio servicio = new ServicioImpl();
            Departamento departamento;
            String nomDepartamento;
            List<Departamento> departamentos = servicio.listarDepartamentos();
            departamentos.forEach(System.out::println);

            nomDepartamento = ConsoleHelper.pedirCadena("Nombre del Departamento:");
            departamento = departamentos.stream()
                    .filter(c -> c.getNombre().equalsIgnoreCase(nomDepartamento))
                    .findFirst()
                    .orElse(null);
            if (departamento != null) {
                System.out.println("Departamento ya existe.");
                return;
            }

            servicio.altaDepartamento(new Departamento(nomDepartamento));
            System.out.println("Departamento dado de alta");
        } catch (SQLException e) {
            System.out.println("Error creando departamento: " + e.getMessage());
        }
    }

    public static void altaEmpleado() {
        try {
            Servicio servicio = new ServicioImpl();
            Departamento departamento;
            List<Empleado> empleados = servicio.listarEmpleados();
            empleados.forEach(System.out::println);

            String dni = ConsoleHelper.pedirCadena("DNI del Empleado:");
            String nombre = ConsoleHelper.pedirCadena("Nombre del Empleado:");
            float sueldo = (float) ConsoleHelper.pedirDecimal("Sueldo del Empleado:");
            departamento = pedirDepartamento();
            if (departamento == null) {
                System.out.println("Departamento no existe.");
                return;
            }
            servicio.altaEmpleado(new Empleado(dni, nombre, sueldo, departamento));
            System.out.println("Empleado dado de alta");
        } catch (SQLException e) {
            System.out.println("Error creando Empleado: " + e.getMessage());
        }
    }

    public static void listarEmpleadosByDepartamento() {
        try {
            Servicio servicio = new ServicioImpl();
            List<Empleado> empleados = servicio.listarEmpleados();
            Departamento departamento;
            departamento = pedirDepartamento();
            if (departamento == null) {
                System.out.println("Departamento no existe.");
                return;
            }

            empleados = empleados.stream()
                    .filter(e-> e.getDepartamento().getNombre().equalsIgnoreCase(departamento.getNombre()))
                    .toList();

            if (!empleados.isEmpty())
                empleados.forEach(System.out::println);
            else
                System.out.println("No hay empleados registrados para el departamento indicado");
        } catch (SQLException ex) {
            System.out.println("Error obteniendo listado de empleados por departamento. " + ex.getMessage());
        }
    }

    public static void transferirEmpleado() {
        try {
            Servicio servicio = new ServicioImpl();
            Empleado empleado;
            Departamento departamento;
            List<Empleado> empleados = servicio.listarEmpleados();
            empleados.forEach(System.out::println);

            long id = ConsoleHelper.pedirEntero("ID Empleado: ");
            empleado = servicio.obtenerEmpleado(id);
            if (empleado == null) {
                System.out.println("Empleado no existe.");
                return;
            }

            departamento = pedirDepartamento();
            if (departamento == null) {
                System.out.println("Departamento no existe.");
                return;
            }
            if (empleado.getDepartamento().getIdDepartamento().equals(departamento.getIdDepartamento()))
            {
                System.out.println("No se cambio departamento de empleado");
                return;
            }
            empleado.setDepartamento(departamento);
            servicio.actualizarEmpleado(empleado);
            System.out.println("Departamento de Empleado Actualizado: ");
            System.out.println(empleado);
        } catch (SQLException e) {
            System.out.println("Error creando cuenta: " + e.getMessage());
        }
    }

    public static void bajaEmpleado() {
        try {
            Servicio servicio = new ServicioImpl();
            Empleado empleado;
            List<Empleado> empleados = servicio.listarEmpleados();
            empleados.forEach(System.out::println);
            long id = ConsoleHelper.pedirEntero("ID Empleado: ");
            empleado = servicio.obtenerEmpleado(id);
            if (empleado == null) {
                System.out.println("Empleado no existe.");
                return;
            }
            servicio.bajaEmpleado(empleado);
            System.out.println("Empleado dado de baja");
        } catch (SQLException e) {
            System.out.println("Error eliminando Empleado: " + e.getMessage());
        }
    }

    public static void saldosMediosByEmpleados() {
        try {
            Servicio servicio = new ServicioImpl();
            Map<String, Double> saldoMedio = servicio.saldoMedioEmpleadosByDepartamento();
            System.out.println("SALDO MEDIO DE EMPLEADOS POR DEPARTAMENTO:");
            saldoMedio.forEach((k, v) -> System.out.printf("    %-20s - %.2f€%n", k, v));
        } catch (SQLException e) {
            System.out.println("Error obteniendo saldos medios por cliente: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        menu();
    }
}
