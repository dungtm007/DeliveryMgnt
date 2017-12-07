package deliverymgnt.domainclasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="boxes")
public class Box {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="locker_id")
	private Locker locker;
	
	@Column(name="package_size")
	private PackageSize size;
	
	@Column(name="is_available")
	private boolean isAvailable;

	public Box(PackageSize size, boolean isAvailable, Locker locker) {
		this.size = size;
		this.isAvailable = isAvailable;
		this.locker = locker;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PackageSize getSize() {
		return size;
	}

	public void setSize(PackageSize size) {
		this.size = size;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}
}
