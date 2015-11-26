/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Monica
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdprod", query = "SELECT p FROM Producto p WHERE p.idprod = :idprod"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByStock", query = "SELECT p FROM Producto p WHERE p.stock = :stock")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprod")
    private Integer idprod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private BigDecimal precio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock")
    private int stock;
    @JoinColumn(name = "idcat", referencedColumnName = "idcat")
    @ManyToOne(optional = false)
    private Categoria idcat;

    public Producto() {
    }

    public Producto(Integer idprod) {
        this.idprod = idprod;
    }

    public Producto(Integer idprod, String nombre, BigDecimal precio, int stock) {
        this.idprod = idprod;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Integer getIdprod() {
        return idprod;
    }

    public void setIdprod(Integer idprod) {
        this.idprod = idprod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Categoria getIdcat() {
        return idcat;
    }

    public void setIdcat(Categoria idcat) {
        this.idcat = idcat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprod != null ? idprod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idprod == null && other.idprod != null) || (this.idprod != null && !this.idprod.equals(other.idprod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Producto[ idprod=" + idprod + " ]";
    }
    
}
