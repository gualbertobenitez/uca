/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Gualberto Benitez
 */
@Entity
@Table(name = "proyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"),
    @NamedQuery(name = "Proyecto.findByProyectoId", query = "SELECT p FROM Proyecto p WHERE p.proyectoId = :proyectoId"),
    @NamedQuery(name = "Proyecto.findByNombre", query = "SELECT p FROM Proyecto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Proyecto.findByFechaInicio", query = "SELECT p FROM Proyecto p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Proyecto.findByFechaLimite", query = "SELECT p FROM Proyecto p WHERE p.fechaLimite = :fechaLimite"),
    @NamedQuery(name = "Proyecto.findByFechaFin", query = "SELECT p FROM Proyecto p WHERE p.fechaFin = :fechaFin"),
    @NamedQuery(name = "Proyecto.findByProgreso", query = "SELECT p FROM Proyecto p WHERE p.progreso = :progreso"),
    @NamedQuery(name = "Proyecto.findByEstado", query = "SELECT p FROM Proyecto p WHERE p.estado = :estado"),
    @NamedQuery(name = "Proyecto.findByDueno", query = "SELECT p FROM Proyecto p WHERE p.duenoId = :duenoId"),
    @NamedQuery(name = "Proyecto.findByComentarios", query = "SELECT p FROM Proyecto p WHERE p.comentarios = :comentarios")})
public class Proyecto implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "progreso")
    private short progreso;
    @JoinColumn(name = "dueno_id", referencedColumnName = "persona_id")
    @ManyToOne
    private Persona duenoId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "proyecto_id")
    private Integer proyectoId;
    @Size(max = 40)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_limite")
    @Temporal(TemporalType.DATE)
    private Calendar fechaLimite;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Calendar fechaFin;
    @Column(name = "estado")
    private Character estado;
    @Size(max = 200)
    @Column(name = "comentarios")
    private String comentarios;
    
    @OneToMany(mappedBy = "proyectoId")
    private Collection<Actividad> actividadCollection;
    
      
    public Proyecto() {
    }

    public Proyecto(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }

    public Integer getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
         return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Calendar getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Calendar fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Calendar getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }


    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @XmlTransient
    public Collection<Actividad> getActividadCollection() {
        return actividadCollection;
    }

    public void setActividadCollection(Collection<Actividad> actividadCollection) {
        this.actividadCollection = actividadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proyectoId != null ? proyectoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.proyectoId == null && other.proyectoId != null) || (this.proyectoId != null && !this.proyectoId.equals(other.proyectoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Proyecto[ proyectoId=" + proyectoId + " ]";
    }

    public short getProgreso() {
        return progreso;
    }

    public void setProgreso(short progreso) {
        this.progreso = progreso;
    }

    public Persona getDuenoId() {
        return duenoId;
    }

    public void setDuenoId(Persona duenoId) {
        this.duenoId = duenoId;
    }

        
}
