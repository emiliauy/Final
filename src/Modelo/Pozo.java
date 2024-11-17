package Modelo;

/**
 *
 * @author Emilia
 */
public class Pozo {

    private double totalRecaudado;
    private double porcentajeComision;

    public Pozo(double porcentajeComision) {
        if (porcentajeComision < 0 || porcentajeComision > 100) {
            throw new IllegalArgumentException("El porcentaje de comisión debe estar entre 0 y 100.");
        }
        this.porcentajeComision = porcentajeComision;
        this.totalRecaudado = 0;
    }

    public void incrementarPozo(double monto) {
        totalRecaudado += monto;
    }

    public double calcularComision() {
        return totalRecaudado * (porcentajeComision / 100);
    }

    public double obtenerRecaudadoNeto() {
        return totalRecaudado - calcularComision();
    }

    public void reiniciarPozo() {
        totalRecaudado = 0;
    }

    public double getTotalRecaudado() {
        return totalRecaudado;
    }

    public double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(double porcentajeComision) {
        if (porcentajeComision < 0 || porcentajeComision > 100) {
            throw new IllegalArgumentException("El porcentaje de comisión debe estar entre 0 y 100.");
        }
        this.porcentajeComision = porcentajeComision;
    }
}
