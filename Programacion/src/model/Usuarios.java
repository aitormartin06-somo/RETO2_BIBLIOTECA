package model;

public class Usuarios {

    // === ATRIBUTOS ===
    private int id_usuario;
    private String dni_usuario;
    private String nombre_usuario;
    private long telefono_usuario;
    private String password_usuario;
    private long nss;
    private int id_penalizacion;

    // === CONSTRUCTORES ===
    public Usuarios(int p_id_usuario, String p_dni_usuario, String p_nombre_usuario, long p_telefono_usuario,
            String p_password_usuario, long p_nss, int p_id_penalizacion) {
        this.id_usuario = p_id_usuario;
        this.dni_usuario = p_dni_usuario;
        this.nombre_usuario = p_nombre_usuario;
        this.telefono_usuario = p_telefono_usuario;
        this.password_usuario = p_password_usuario;
        this.nss = p_nss;
        this.id_penalizacion = p_id_penalizacion;
    }

    // === GETTERS & SETTERS ===
    public int getid_user() {
        return this.id_usuario;
    }

    public void setId_usuario(int p_id_usuario) {
        this.id_usuario = p_id_usuario;
    }

    public String getdni_user() {
        return this.dni_usuario;
    }

    public void setDni_usuario(String p_dni_usuario) {
        this.dni_usuario = p_dni_usuario;
    }

    public String getnombre_user() {
        return this.nombre_usuario;
    }

    public void setNombre_usuario(String p_nombre_usuario) {
        this.nombre_usuario = p_nombre_usuario;
    }

    public long gettelefono_user() {
        return this.telefono_usuario;
    }

    public void setTelefono_usuario(long p_telefono_usuario) {
        this.telefono_usuario = p_telefono_usuario;
    }

    public String getpassword_user() {
        return this.password_usuario;
    }

    public void setPassword_usuario(String p_password_usuario) {
        this.password_usuario = p_password_usuario;
    }

    public long getNss() {
        return this.nss;
    }

    public void setNss(long p_nss) {
        this.nss = p_nss;
    }

    public int getId_penalizacion() {
        return this.id_penalizacion;
    }

    public void setId_penalizacion(int p_id_penalizacion) {
        this.id_penalizacion = p_id_penalizacion;
    }

    @Override
    public String toString() {
        return "ID: " + id_usuario + " | DNI: " + dni_usuario + " | Nombre: " + nombre_usuario + " | Teléfono: "
                + telefono_usuario + " | Contraseña: " + password_usuario + " | NSS: " + nss + " | ID Penalización: "
                + id_penalizacion;
    }
}
