package com.afsd.redis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Publishers")
public class Publisher {
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "PublisherID")
	    private Long publisherId;

	    @Column(name = "PublisherName")
	    private String publisherName;

	    @Column(name = "Address")
	    private String address;

	    @Column(name = "Phone")
	    private String phone;

	    @Column(name = "Email")
	    private String email;

		public Long getPublisherId() {
			return publisherId;
		}

		public void setPublisherId(Long publisherId) {
			this.publisherId = publisherId;
		}

		public String getPublisherName() {
			return publisherName;
		}

		public void setPublisherName(String publisherName) {
			this.publisherName = publisherName;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Publisher(Long publisherId, String publisherName, String address, String phone, String email) {
			super();
			this.publisherId = publisherId;
			this.publisherName = publisherName;
			this.address = address;
			this.phone = phone;
			this.email = email;
		}

		public Publisher() {
			super();
			// TODO Auto-generated constructor stub
		}
	    
	    
	    

}
