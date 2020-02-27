/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gualberto Benitez
 */
@Entity
@Table(name = "actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a"),
    @NamedQuery(name = "Actividad.findByActividadId", query = "SELECT a FROM Actividad a WHERE a.actividadId = :actividadId"),
    @NamedQuery(name = "Actividad.findByNombre", query = "SELECT a FROM Actividad a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Actividad.findByFechaProgramada", query = "SELECT a FROM Actividad a WHERE a.fechaProgramada = :fechaProgramada"),
    @NamedQuery(name = "Actividad.findByFechaInicio", query = "SELECT a FROM Actividad a WHERE a.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Actividad.findByFechaFin", query = "SELECT a FROM Actividad a WHERE a.fechaFin = :fechaFin"),
    @NamedQuery(name = "Actividad.findByFechaLimite", query = "SELECT a FROM Actividad a WHERE a.fechaLimite = :fechaLimite"),
    @NamedQuery(name = "Actividad.findByProgreso", query = "SELECT a FROM Actividad a WHERE a.progreso = :progreso"),
    @NamedQuery(name = "Actividad.findByComentarios", query = "SELECT a FROM Actividad a WHERE a.comentarios = :comentarios")})
public class Actividad implements Serializable {
    @OneToMany(mappedBy = "actividadId")
    private Collection<Tarea> tareaCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "progreso")
    private short progreso;
    @JoinColumn(name = "responsable_id", referencedColumnName = "persona_id")
    @ManyToOne
    private Persona responsableId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "actividad_id")
    private Integer actividadId;
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
    @JoinColumn(name = "proyecto_id", referencedColumnName = "proyecto_id")
    private Proyecto proyectoId;

    public Actividad() {
    }

    public Actividad(Integer actividadId) {
        this.actividadId = actividadId;
    }

    public Integer getActividadId() {
        return actividadId;
    }

    public void setActividadId(Integer actividadId) {
        this.actividadId = actividadId;
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

    public Proyecto getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Proyecto proyectoId) {
        this.proyectoId = proyectoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actividadId != null ? actividadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.actividadId == null && other.actividadId != null) || (this.actividadId != null && !this.actividadId.equals(other.actividadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Actividad[ actividadId=" + actividadId + " ]";
    }

    public short getProgreso() {
        return progreso;
    }

    public void setProgreso(short progreso) {
        this.progreso = progreso;
    }

    public Persona getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Persona responsableId) {
        this.responsableId = responsableId;
    }

    @XmlTransient
    public Collection<Tarea> getTareaCollection() {
        return tareaCollection;
    }

    public void setTareaCollection(Collection<Tarea> tareaCollection) {
        this.tareaCollection = tareaCollection;
    }
    
}
