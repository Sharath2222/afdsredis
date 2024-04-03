package com.afsd.redis.entity;

import javax.persistence.Table;

@Entity
@Table(name = "user_activity_logs")
public class UserActivityLogs {
	
	@Id
    private UUID userId;
    
    @Column
    private LocalDateTime timestamp;
    
    @Column
    private String action;
    
    @Column
    private UUID bookId;

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public UUID getBookId() {
		return bookId;
	}

	public void setBookId(UUID bookId) {
		this.bookId = bookId;
	}

	public UserActivityLogs(UUID userId, LocalDateTime timestamp, String action, UUID bookId) {
		super();
		this.userId = userId;
		this.timestamp = timestamp;
		this.action = action;
		this.bookId = bookId;
	}

	public UserActivityLogs() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
    
    

}
