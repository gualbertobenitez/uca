/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gualberto Benitez
 */
@Entity
@Table(name = "tarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarea.findAll", query = "SELECT t FROM Tarea t"),
    @NamedQuery(name = "Tarea.findByTareaId", query = "SELECT t FROM Tarea t WHERE t.tareaId = :tareaId"),
    @NamedQuery(name = "Tarea.findByNombre", query = "SELECT t FROM Tarea t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tarea.findByFechaProgramada", query = "SELECT t FROM Tarea t WHERE t.fechaProgramada = :fechaProgramada"),
    @NamedQuery(name = "Tarea.findByFechaInicio", query = "SELECT t FROM Tarea t WHERE t.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Tarea.findByFechaFin", query = "SELECT t FROM Tarea t WHERE t.fechaFin = :fechaFin"),
    @NamedQuery(name = "Tarea.findByFechaLimite", query = "SELECT t FROM Tarea t WHERE t.fechaLimite = :fechaLimite"),
    @NamedQuery(name = "Tarea.findByComentarios", query = "SELECT t FROM Tarea t WHERE t.comentarios = :comentarios"),
    @NamedQuery(name = "Tarea.findByDueno", query = "SELECT t FROM Tarea t WHERE t.responsableId = :duenoId"),
    @NamedQuery(name = "Tarea.findByEstado", query = "SELECT t FROM Tarea t WHERE t.estado = :estado")})
public class Tarea implements Serializable {
    @JoinColumn(name = "actividad_id", referencedColumnName = "actividad_id")
    @ManyToOne
    private Actividad actividadId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tarea_id")
    private Integer tareaId;
    @Size(max = 40)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha_programada")
    @Temporal(TemporalType.DATE)
    private Date fechaProgramada;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "fecha_limite")
    @Temporal(TemporalType.DATE)
    private Date fechaLimite;
    @Size(max = 200)
    @Column(name = "comentarios")
    private String comentarios;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private Character estado;
    @JoinColumn(name = "responsable_id", referencedColumnName = "persona_id")
    @ManyToOne
    private Persona responsableId;

    public Tarea() {
    }

    public Tarea(Integer tareaId) {
        this.tareaId = tareaId;
    }

    public Tarea(Integer tareaId, Character estado) {
        this.tareaId = tareaId;
        this.estado = estado;
    }

    public Integer getTareaId() {
        return tareaId;
    }

    public void setTareaId(Integer tareaId) {
        this.tareaId = tareaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public Persona getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Persona responsableId) {
        this.responsableId = responsableId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tareaId != null ? tareaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarea)) {
            return false;
        }
        Tarea other = (Tarea) object;
        if ((this.tareaId == null && other.tareaId != null) || (this.tareaId != null && !this.tareaId.equals(other.tareaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Tarea[ tareaId=" + tareaId + " ]";
    }

    public Actividad getActividadId() {
        return actividadId;
    }

    public void setActividadId(Actividad actividadId) {
        this.actividadId = actividadId;
    }
    
}
