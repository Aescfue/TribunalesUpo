package com.example.application.data;

import java.io.Serializable;
import java.util.Objects;

public class RolPK implements Serializable {
    protected String usuario;
    protected String codigo;

    public RolPK(String usuario, String codigo) {
        this.usuario = usuario;
        this.codigo = codigo;
    }

    public RolPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolPK rolPK = (RolPK) o;
        return Objects.equals(usuario, rolPK.usuario) && Objects.equals(codigo, rolPK.codigo);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, codigo);
    }
}
