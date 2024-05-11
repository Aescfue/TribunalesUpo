package org.upo.tribunalesupo.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@jakarta.persistence.Table(name = "rol", schema = "tribunales", catalog = "")
@jakarta.persistence.IdClass(RolPK.class)
public class Rol {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "codigo")
    private String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "usuario")
    private String usuario;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rol rol = (Rol) o;

        if (codigo != null ? !codigo.equals(rol.codigo) : rol.codigo != null) return false;
        if (usuario != null ? !usuario.equals(rol.usuario) : rol.usuario != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "codigo='" + codigo + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (usuario != null ? usuario.hashCode() : 0);
        return result;
    }
}
