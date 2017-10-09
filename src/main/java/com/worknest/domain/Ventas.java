package com.worknest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ventas.
 */
@Entity
@Table(name = "ventas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ventas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "totalpagar", nullable = false)
    private Float totalpagar;

    @NotNull
    @Column(name = "ganancia", nullable = false)
    private Float ganancia;

    @OneToMany(mappedBy = "ventas")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Historial> historials = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Ventas fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Float getTotalpagar() {
        return totalpagar;
    }

    public Ventas totalpagar(Float totalpagar) {
        this.totalpagar = totalpagar;
        return this;
    }

    public void setTotalpagar(Float totalpagar) {
        this.totalpagar = totalpagar;
    }

    public Float getGanancia() {
        return ganancia;
    }

    public Ventas ganancia(Float ganancia) {
        this.ganancia = ganancia;
        return this;
    }

    public void setGanancia(Float ganancia) {
        this.ganancia = ganancia;
    }

    public Set<Historial> getHistorials() {
        return historials;
    }

    public Ventas historials(Set<Historial> historials) {
        this.historials = historials;
        return this;
    }

    public Ventas addHistorial(Historial historial) {
        this.historials.add(historial);
        historial.setVentas(this);
        return this;
    }

    public Ventas removeHistorial(Historial historial) {
        this.historials.remove(historial);
        historial.setVentas(null);
        return this;
    }

    public void setHistorials(Set<Historial> historials) {
        this.historials = historials;
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
        Ventas ventas = (Ventas) o;
        if (ventas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ventas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ventas{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", totalpagar='" + getTotalpagar() + "'" +
            ", ganancia='" + getGanancia() + "'" +
            "}";
    }
}
