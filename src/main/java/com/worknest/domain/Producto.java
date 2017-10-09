package com.worknest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 13, max = 30)
    @Column(name = "cbarras", length = 30, nullable = false)
    private String cbarras;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 10, max = 150)
    @Column(name = "descripcion", length = 150, nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @NotNull
    @Column(name = "pcompra", nullable = false)
    private Float pcompra;

    @NotNull
    @Column(name = "pventa", nullable = false)
    private Float pventa;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Historial> historials = new HashSet<>();

    @ManyToOne
    private Categoria categoria;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCbarras() {
        return cbarras;
    }

    public Producto cbarras(String cbarras) {
        this.cbarras = cbarras;
        return this;
    }

    public void setCbarras(String cbarras) {
        this.cbarras = cbarras;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Producto cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getPcompra() {
        return pcompra;
    }

    public Producto pcompra(Float pcompra) {
        this.pcompra = pcompra;
        return this;
    }

    public void setPcompra(Float pcompra) {
        this.pcompra = pcompra;
    }

    public Float getPventa() {
        return pventa;
    }

    public Producto pventa(Float pventa) {
        this.pventa = pventa;
        return this;
    }

    public void setPventa(Float pventa) {
        this.pventa = pventa;
    }

    public Set<Historial> getHistorials() {
        return historials;
    }

    public Producto historials(Set<Historial> historials) {
        this.historials = historials;
        return this;
    }

    public Producto addHistorial(Historial historial) {
        this.historials.add(historial);
        historial.setProducto(this);
        return this;
    }

    public Producto removeHistorial(Historial historial) {
        this.historials.remove(historial);
        historial.setProducto(null);
        return this;
    }

    public void setHistorials(Set<Historial> historials) {
        this.historials = historials;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Producto categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producto producto = (Producto) o;
        if (producto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), producto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", cbarras='" + getCbarras() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", cantidad='" + getCantidad() + "'" +
            ", pcompra='" + getPcompra() + "'" +
            ", pventa='" + getPventa() + "'" +
            "}";
    }
}
