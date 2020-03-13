package com.tech.keepnote.controller;

import java.time.*;
import java.util.List;
import java.util.Map;

import javax.naming.Context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tech.keepnote.model.Note;
import com.tech.keepnote.repository.NoteRepository;

/*Annotate the class with @Controller annotation. @Controller annotation is used to mark 
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 * */

@Controller
public class NoteController {
	/*
	 * From the problem statement, we can understand that the application
	 * requires us to implement the following functionalities.
	 * 
	 * 1. display the list of existing notes from the collection. Each note 
	 *    should contain Note Id, title, content, status and created date.
	 * 2. Add a new note which should contain the note id, title, content and status.
	 * 3. Delete an existing note.
	 * 4. Update an existing note.
	 */
	
	/* 
	 * Get the application context from resources/beans.xml file using ClassPathXmlApplicationContext() class.
	 * Retrieve the Note object from the context.
	 * Retrieve the NoteRepository object from the context.
	 */
	ApplicationContext myContext = new ClassPathXmlApplicationContext("beans.xml");
	Note mynote = (Note)myContext.getBean("note");
	NoteRepository myNoteRepository = (NoteRepository)myContext.getBean("noteRepository");
	
	/*Define a handler method to read the existing notes by calling the getAllNotes() method 
	 * of the NoteRepository class and add it to the ModelMap which is an implementation of Map 
	 * for use when building model data for use with views. it should map to the default URL i.e. "/" */
	@RequestMapping("/")
	public String getAllNotes(ModelMap map){
		List<Note> myList = myNoteRepository.getAllNotes();
		map.addAttribute("all list",myList);
		return "index";
		
	}
	
	/*Define a handler method which will read the Note data from request parameters and
	 * save the note by calling the addNote() method of NoteRepository class. Please note 
	 * that the createdAt field should always be auto populated with system time and should not be accepted 
	 * from the user. Also, after saving the note, it should show the same along with existing 
	 * notes. Hence, reading notes has to be done here again and the retrieved notes object 
	 * should be sent back to the view using ModelMap.
	 * This handler method should map to the URL "/saveNote". 
	*/
	@RequestMapping("/saveNote")
	public String addNote(ModelMap map, @RequestParam Map<String, String> noteMap) {
		
		Integer noteId = Integer.parseInt(noteMap.get("noteId"));
		String noteTitle = noteMap.get("noteTitle");
		String noteContent = noteMap.get("noteContent");
		String noteStatus = noteMap.get("noteStatus");
		LocalDateTime noteCreatedAt = LocalDateTime.now();
		
		if (noteId != null && noteMap.get("noteTitle") != null && !noteMap.get("noteTitle").equals("")
				&& noteMap.get("noteContent") != null && !noteMap.get("noteContent").equals("")
				&& noteMap.get("noteStatus") != null && !noteMap.get("noteStatus").equals("")) {

			mynote.setNoteId(noteId);
			mynote.setNoteTitle(noteTitle);
			mynote.setNoteContent(noteContent);
			mynote.setNoteStatus(noteStatus);
			mynote.setCreatedAt(noteCreatedAt);

			myNoteRepository.addNote(mynote);
			
			List<Note> myList = myNoteRepository.getAllNotes();
			map.addAttribute("all list",myList);
		}
		return "index";

	}
	
	
	
	/* Define a handler method to delete an existing note by calling the deleteNote() method 
	 * of the NoteRepository class
	 * This handler method should map to the URL "/deleteNote" 
	*/
	
	@RequestMapping("/deleteNote")
	public String deleteNote(ModelMap map, @RequestParam ("noteId") int noteId) {
		
		myNoteRepository.deleteNote(noteId);
		List<Note> myList = myNoteRepository.getAllNotes();
		map.addAttribute("all list",myList);
		return "redirect:/";
	}
	
}