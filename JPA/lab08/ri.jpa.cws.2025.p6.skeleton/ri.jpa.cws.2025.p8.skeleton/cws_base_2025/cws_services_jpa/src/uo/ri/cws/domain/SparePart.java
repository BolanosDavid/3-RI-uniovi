package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
@Entity
@Table(name ="TSPAREPARTS")
public class SparePart extends BaseEntity{
    // natural attributes
    @Column (unique = true) private String code;
    private String description;
    private double price;
    private int stock;
    private int minStock;
    private int maxStock;

    // accidental attributes
    @OneToMany(mappedBy = "sparePart") private Set<Substitution> substitutions = new HashSet<>();

    //Para JPA
    public SparePart() {}
    // full constructor
    public SparePart(String code, String description, double price, int stock,
		    int minStock, int maxStock,
		    Set<Substitution> substitutions) {
	ArgumentChecks.isNotBlank(code, "Square Part:: not valid code");
	ArgumentChecks.isNotBlank(description,
			"Square Part:: not valid description");
	ArgumentChecks.isTrue(price > 0, "Square Part:: not valid price");
	ArgumentChecks.isTrue(stock > 0, "Square Part:: not valid stock");
	ArgumentChecks.isTrue(minStock > 0,
			"Square Part:: not valid min stock");
	ArgumentChecks.isTrue(maxStock > 0,
			"Square Part:: not valid max stock");

	this.code = code;
	this.description = description;
	this.price = price;
	this.stock = stock;
	this.minStock = minStock;
	this.maxStock = maxStock;

    }

    public SparePart(String code, String description, double price) {
	this(code, description, price, 1, 1, 1, null);
    }

    public SparePart(String code) {
	this(code,"no-description",1);
    }

    public SparePart(String code, String description, double price, int stock, int minStock,
		    int maxStock) {
	this(code,description,price,stock,minStock,maxStock,new HashSet<Substitution>());
    }

    public Set<Substitution> getSubstitutions() {
	return new HashSet<>(substitutions);
    }

    Set<Substitution> _getSubstitutions() {
	return this.substitutions;
    }

    public String getCode() {
	return code;
    }

    public String getDescription() {
	return description;
    }

    public double getPrice() {
	return price;
    }

    public int getStock() {
	return stock;
    }

    public int getMinStock() {
	return minStock;
    }

    public int getMaxStock() {
	return maxStock;
    }



    @Override
    public String toString() {
	return "SparePart [code=" + code + ", description=" + description
			+ ", price=" + price + ", stock=" + stock
			+ ", minStock=" + minStock + ", maxStock=" + maxStock
			+ ", substitutions=" + substitutions + "]";
    }

}
