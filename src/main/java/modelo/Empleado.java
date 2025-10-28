package modelo;

public class Empleado {

    private Long idEmpleado;
    private String dni;
    private String nombre;
    private Float sueldo;
    private Departamento departamento;

    public Empleado() {
    }

    public Empleado(String dni, String nombre, Float sueldo, Departamento departamento) {
        this.dni = dni;
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.departamento = departamento;
    }

    public Empleado(Long idEmpleado, String dni, String nombre, Float sueldo, Departamento departamento) {
        this.idEmpleado = idEmpleado;
        this.dni = dni;
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.departamento = departamento;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getSueldo() {
        return sueldo;
    }

    public void setSueldo(Float sueldo) {
        this.sueldo = sueldo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Empleado[" +
                " Id = " + idEmpleado +
                ", Dni = " + dni +
                ", Nombre = " + nombre +
                ", Sueldo = " + sueldo +
                ", Departamento=" + departamento.getNombre() +
                ']';
    }
}
