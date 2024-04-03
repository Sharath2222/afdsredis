package com.afsd.redis.entity;


@Entity
@Table(name = "book_recommendations")
public class BookRecommendation {
	
	@Id
    private UUID userId;
    
    @Id
    private UUID bookId;
    
    @Column
    private float score;

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getBookId() {
		return bookId;
	}

	public void setBookId(UUID bookId) {
		this.bookId = bookId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public BookRecommendation(UUID userId, UUID bookId, float score) {
		super();
		this.userId = userId;
		this.bookId = bookId;
		this.score = score;
	}

	public BookRecommendation() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    


}
