package principal.android.empresa.eduardo.bean;

/**
 * Created by Autonoma on 16/11/2016.
 */
public class ProyectoBean {

    String numero;
    String titulo;
    String duracion;
    String descripcion;
    String tipo;
    String fases;
    String inicio;
    String fin;
    String gastos;
    String CODJEFE;
    String NOMBJEFE;

    public ProyectoBean(String cod,String tit){
        this.numero=cod;
        this.titulo=tit;

    }
    public  ProyectoBean(){

    }
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFases() {
        return fases;
    }

    public void setFases(String fases) {
        this.fases = fases;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getGastos() {
        return gastos;
    }

    public void setGastos(String gastos) {
        this.gastos = gastos;
    }

    public String getCODJEFE() {
        return CODJEFE;
    }

    public void setCODJEFE(String CODJEFE) {
        this.CODJEFE = CODJEFE;
    }

    public String getNOMBJEFE() {
        return NOMBJEFE;
    }

    public void setNOMBJEFE(String NOMBJEFE) {
        this.NOMBJEFE = NOMBJEFE;
    }

    @Override
    public String toString() {
        return numero+" --"+titulo;
        //return NOMBJEFE ;
    }
}
