package com.worknest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Historial.
 */
@Entity
@Table(name = "historial")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Historial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "precioactual", nullable = false)
    private Float precioactual;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @NotNull
    @Column(name = "totalproducto", nullable = false)
    private Float totalproducto;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Ventas ventas;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrecioactual() {
        return precioactual;
    }

    public Historial precioactual(Float precioactual) {
        this.precioactual = precioactual;
        return this;
    }

    public void setPrecioactual(Float precioactual) {
        this.precioactual = precioactual;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Historial cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getTotalproducto() {
        return totalproducto;
    }

    public Historial totalproducto(Float totalproducto) {
        this.totalproducto = totalproducto;
        return this;
    }

    public void setTotalproducto(Float totalproducto) {
        this.totalproducto = totalproducto;
    }

    public Producto getProducto() {
        return producto;
    }

    public Historial producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Ventas getVentas() {
        return ventas;
    }

    public Historial ventas(Ventas ventas) {
        this.ventas = ventas;
        return this;
    }

    public void setVentas(Ventas ventas) {
        this.ventas = ventas;
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
        Historial historial = (Historial) o;
        if (historial.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historial.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Historial{" +
            "id=" + getId() +
            ", precioactual='" + getPrecioactual() + "'" +
            ", cantidad='" + getCantidad() + "'" +
            ", totalproducto='" + getTotalproducto() + "'" +
            "}";
    }
}
