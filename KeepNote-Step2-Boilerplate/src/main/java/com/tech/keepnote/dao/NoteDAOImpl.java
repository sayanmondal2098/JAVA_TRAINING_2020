package com.tech.keepnote.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;

import com.tech.keepnote.config.ApplicationContextConfig;
import com.tech.keepnote.model.Note;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus 
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */

@Repository
@Transactional
@ContextConfiguration(classes = { ApplicationContextConfig.class })
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	public NoteDAOImpl(SessionFactory sessionFactory) {

		this.sessionFactory=sessionFactory;
	}

	/*
	 * Save the note in the database(note) table.
	 */

	public boolean saveNote(Note note) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.save(note);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * Remove the note from the database(note) table.
	 */

	public boolean deleteNote(int noteId) {
		Boolean deleteFlag = true;
			Session session=this.sessionFactory.getCurrentSession();
			Note note = (Note)session.load(Note.class, new Integer(noteId));
			try {
				if(null!=note)
				{
					session.delete(note);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				deleteFlag= false;
			}
			return deleteFlag;

	}

	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	public List<Note> getAllNotes() {
		Session session=this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Note> noteList= session.createQuery("from Note").list();
		Collections.sort(noteList, new Comparator<Note>() {
		    public int compare(Note n1, Note n2) {
		        return n1.getCreatedAt().compareTo(n2.getCreatedAt());
		    }
		});
		Collections.reverse(noteList);
		return noteList;

	}

	/*
	 * retrieve specific note from the database(note) table
	 */
	public Note getNoteById(int noteId) {
		Session session=this.sessionFactory.getCurrentSession();
		Note note=session.load(Note.class, new Integer(noteId));
		if(null!=note)
		{
			return note;
		}
		return null;

	}

	/* Update existing note */

	public boolean UpdateNote(Note note) {
		Session session=this.sessionFactory.getCurrentSession();
		try {
			session.update(note);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

}
