package com.tech.keepnote.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * The class "Note" will be acting as the data model for the note Table in the database. Please
 * note that this class is annotated with @Entity annotation. Hibernate will scan all package for 
 * any Java objects annotated with the @Entity annotation. If it finds any, then it will begin the 
 * process of looking through that particular Java object to recreate it as a table in your database.
 */

@Entity
@Table
public class Note {
	
	private int id;
	private String title,content,status;
	private LocalDateTime timestamp;
	
	public Note(int id, String title, String content, String status, LocalDateTime timestamp) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.status = status;
		this.timestamp = timestamp;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getNoteId() {
		return id;
	}

	public void setNoteId(int id) {
		this.id = id;
	}

	public String getNoteTitle() {
		return title;
	}

	public void setNoteTitle(String title) {
		this.title = title;
	}

	public String getNoteContent() {
		return content;
	}

	public void setNoteContent(String content) {
		this.content = content;
	}

	public String getNoteStatus() {
		return status;
	}

	public void setNoteStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return timestamp;
	}

	public void setCreatedAt(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}		

}
