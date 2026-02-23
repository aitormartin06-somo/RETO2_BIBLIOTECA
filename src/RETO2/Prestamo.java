package RETO2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Prestamo {
    
    // Constante: días de préstamo (30 días)
    public static final int DIAS_PRESTAMO = 30;
    
    // Atributos
    private int idPrestamo;
    private LocalDate fechaInicio;
    private LocalDate fechaDevolucion;
    private boolean entregado;
    private Libro libro;
    
    // Constructor vacío
    public Prestamo() {
        this.entregado = false;
    }
    
    // Constructor completo
    public Prestamo(int idPrestamo, LocalDate fechaInicio, Libro libro) {
        this.idPrestamo = idPrestamo;
        this.fechaInicio = fechaInicio;
        this.libro = libro;
        this.entregado = false;
        this.fechaDevolucion = calcularFechaDevolucion();
    }
    
    // Método para calcular fecha de devolución (fechaInicio + 30 días)
    private LocalDate calcularFechaDevolucion() {
        return fechaInicio.plusDays(DIAS_PRESTAMO);
    }
    
    // Método para obtener días restantes hasta devolución
    public long getDiasRestantes() {
        if (entregado) {
            return 0;
        }
        LocalDate hoy = LocalDate.now();
        return ChronoUnit.DAYS.between(hoy, fechaDevolucion);
    }
    
    // Método para marcar libro como devuelto
    public void devolverLibro() {
        this.entregado = true;
    }
    
    // Getters y Setters
    public int getIdPrestamo() {
        return idPrestamo;
    }
    
    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        this.fechaDevolucion = calcularFechaDevolucion();
    }
    
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
    
    public boolean isEntregado() {
        return entregado;
    }
    
    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }
    
    public Libro getLibro() {
        return libro;
    }
    
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    
    @Override
    public String toString() {
        String estado = entregado ? "Devuelto" : "Prestado";
        return "Préstamo #" + idPrestamo + 
               " | Libro: " + libro.getTitulo() + 
               " | Inicio: " + fechaInicio + 
               " | Devolución: " + fechaDevolucion + 
               " | Estado: " + estado;
    }
}