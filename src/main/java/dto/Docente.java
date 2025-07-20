/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author harol
 */
@Entity
@Table(name = "docente")
@NamedQueries({
    @NamedQuery(name = "Docente.findAll", query = "SELECT d FROM Docente d"),
    @NamedQuery(name = "Docente.findByCodiDoce", query = "SELECT d FROM Docente d WHERE d.codiDoce = :codiDoce"),
    @NamedQuery(name = "Docente.findByDniDoce", query = "SELECT d FROM Docente d WHERE d.dniDoce = :dniDoce"),
    @NamedQuery(name = "Docente.buscarxDNI", query = "SELECT d FROM Docente d WHERE d.dniDoce = :dniDoce"),
    @NamedQuery(name = "Docente.validar", query = "SELECT d FROM Docente d WHERE d.dniDoce = :dniDoce and d.claveDoce = :claveDoce"),
    @NamedQuery(name = "Docente.findByNombDoce", query = "SELECT d FROM Docente d WHERE d.nombDoce = :nombDoce"),
    @NamedQuery(name = "Docente.findByEspecDoce", query = "SELECT d FROM Docente d WHERE d.especDoce = :especDoce"),
    @NamedQuery(name = "Docente.findByFechaIngrDoce", query = "SELECT d FROM Docente d WHERE d.fechaIngrDoce = :fechaIngrDoce"),
    @NamedQuery(name = "Docente.findByUsuarioDoce", query = "SELECT d FROM Docente d WHERE d.usuarioDoce = :usuarioDoce"),
    @NamedQuery(name = "Docente.findByClaveDoce", query = "SELECT d FROM Docente d WHERE d.claveDoce = :claveDoce"),
    @NamedQuery(name = "Docente.findByMfaSecreto", query = "SELECT d FROM Docente d WHERE d.mfaSecreto = :mfaSecreto"),
    @NamedQuery(name = "Docente.findByMfaEstado", query = "SELECT d FROM Docente d WHERE d.mfaEstado = :mfaEstado")})
public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiDoce")
    private Integer codiDoce;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "dniDoce")
    private String dniDoce;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombDoce")
    private String nombDoce;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "especDoce")
    private String especDoce;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaIngrDoce")
    @Temporal(TemporalType.DATE)
    private Date fechaIngrDoce;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usuarioDoce")
    private String usuarioDoce;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "claveDoce")
    private String claveDoce;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mfaSecreto")
    private String mfaSecreto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mfaEstado")
    private boolean mfaEstado;

    public Docente() {
    }

    public Docente(Integer codiDoce) {
        this.codiDoce = codiDoce;
    }

    public Docente(Integer codiDoce, String dniDoce, String nombDoce, String especDoce, Date fechaIngrDoce, String usuarioDoce, String claveDoce, String mfaSecreto, boolean mfaEstado) {
        this.codiDoce = codiDoce;
        this.dniDoce = dniDoce;
        this.nombDoce = nombDoce;
        this.especDoce = especDoce;
        this.fechaIngrDoce = fechaIngrDoce;
        this.usuarioDoce = usuarioDoce;
        this.claveDoce = claveDoce;
        this.mfaSecreto = mfaSecreto;
        this.mfaEstado = mfaEstado;
    }

    public Integer getCodiDoce() {
        return codiDoce;
    }

    public void setCodiDoce(Integer codiDoce) {
        this.codiDoce = codiDoce;
    }

    public Docente(String dniDoce, String claveDoce) {
        this.dniDoce = dniDoce;
        this.claveDoce = claveDoce;
    }

    public String getDniDoce() {
        return dniDoce;
    }

    public void setDniDoce(String dniDoce) {
        this.dniDoce = dniDoce;
    }

    public String getNombDoce() {
        return nombDoce;
    }

    public void setNombDoce(String nombDoce) {
        this.nombDoce = nombDoce;
    }

    public String getEspecDoce() {
        return especDoce;
    }

    public void setEspecDoce(String especDoce) {
        this.especDoce = especDoce;
    }

    public Date getFechaIngrDoce() {
        return fechaIngrDoce;
    }

    public void setFechaIngrDoce(Date fechaIngrDoce) {
        this.fechaIngrDoce = fechaIngrDoce;
    }

    public String getUsuarioDoce() {
        return usuarioDoce;
    }

    public void setUsuarioDoce(String usuarioDoce) {
        this.usuarioDoce = usuarioDoce;
    }

    public String getClaveDoce() {
        return claveDoce;
    }

    public void setClaveDoce(String claveDoce) {
        this.claveDoce = claveDoce;
    }

    public String getMfaSecreto() {
        return mfaSecreto;
    }

    public void setMfaSecreto(String mfaSecreto) {
        this.mfaSecreto = mfaSecreto;
    }

    public boolean getMfaEstado() {
        return mfaEstado;
    }

    public void setMfaEstado(boolean mfaEstado) {
        this.mfaEstado = mfaEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiDoce != null ? codiDoce.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Docente)) {
            return false;
        }
        Docente other = (Docente) object;
        if ((this.codiDoce == null && other.codiDoce != null) || (this.codiDoce != null && !this.codiDoce.equals(other.codiDoce))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Docente[ codiDoce=" + codiDoce + " ]";
    }
    
}
