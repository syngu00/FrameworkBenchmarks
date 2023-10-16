package hello.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fortune")
public class FortuneEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "message")
	private String message;

    protected FortuneEntity() {
    }

    public FortuneEntity(int id, String message) {
        this.id = id;
        this.message = message;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
