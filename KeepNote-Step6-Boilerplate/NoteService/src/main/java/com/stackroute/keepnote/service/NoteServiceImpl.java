package com.stackroute.keepnote.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class NoteServiceImpl implements NoteService {

	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	private NoteRepository noteRepository;
	NoteUser noteUser = null;
	List<Note> notes = null;

	public NoteServiceImpl(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	
	public boolean createNote(Note note) {
		Random random = new Random();
		int noteId = random.nextInt(99999);
		note.setNoteId(noteId);
		boolean created = false;
		Optional<NoteUser> noteUser = noteRepository.findById(note.getNoteCreatedBy());
		NoteUser userNotes = null;
		if (noteUser.isPresent()) {
			userNotes = noteUser.get();
			userNotes.getNotes().add(note);
			created = noteRepository.save(userNotes) != null ? true : false;
		} else {
			userNotes = new NoteUser();
			userNotes.setUserId(note.getNoteCreatedBy());
			userNotes.setNotes(Arrays.asList(note));
			created = noteRepository.insert(userNotes) != null ? true : false;
		}

		return created;
	}

	/*
	 * This method should be used to save a new note.
	 */
//	public boolean createNote(Note note) {
//
//		boolean status = false;
//		int count = 1;
//
//		noteUser = new NoteUser();
//		notes = new ArrayList<Note>();
//		note.setNoteCreationDate(new Date());
//		// System.out.println(note);
//		if (noteRepository.existsById(note.getNoteCreatedBy())) {
//			notes = noteRepository.findById(note.getNoteCreatedBy()).get().getNotes();
//			Note note2 = new Note();
//
//			Iterator<Note> iterator = notes.iterator();
//			while (iterator.hasNext()) {
//				note2 = (Note) iterator.next();
//			}
//
//			note.setNoteId(note2.getNoteId() + count);
//			note.setNoteCreationDate(new Date());
//			notes.add(note);
//			noteUser.setUserId(note.getNoteCreatedBy());
//			noteUser.setNotes(notes);
//
//			if (noteRepository.save(noteUser) != null) {
//				status = true;
//			}
//
//		} else {
//			note.setNoteId(count);
//			note.setNoteCreationDate(new Date());
//			notes.add(note);
//			noteUser.setUserId(note.getNoteCreatedBy());
//			noteUser.setNotes(notes);
//
//			if (noteRepository.insert(noteUser) != null) {
//				status = true;
//			}
//		}
//		return status;
//	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(String userId, int noteId) {
		noteUser = new NoteUser();
		notes = noteRepository.findById(userId).get().getNotes();
		if (!notes.isEmpty()) {

			// System.out.println(noteUser2);
			// notes = noteUser2.getNotes();
			// System.out.println(notes);
			// if (!notes.isEmpty()) {
			notes.removeIf(note -> note.getNoteId() == noteId);
			// }
			noteUser.setUserId(userId);
			noteUser.setNotes(notes);
			noteRepository.save(noteUser);
			return true;
		}

		return false;
	}

	/* This method should be used to delete all notes with specific userId. */

	public boolean deleteAllNotes(String userId) {

		noteUser = new NoteUser();
		notes = noteRepository.findById(userId).get().getNotes();
		if (!notes.isEmpty()) {
			notes.clear();
			noteUser.setUserId(userId);
			noteUser.setNotes(notes);
			noteRepository.save(noteUser);
			return true;
		}
		return false;
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption {
		
		
		NoteUser noteUser = null;
		try {
			noteUser = noteRepository.findById(userId).get();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		if (noteUser != null) {
			notes = noteUser.getNotes();
			Note fetchedNote = notes.stream().filter(notefilter -> notefilter.getNoteId() == id).findFirst()
					.orElse(null);
			if (note != null) {
				noteUser.getNotes().remove(fetchedNote);
				noteUser.getNotes().add(note);
				noteRepository.save(noteUser);
			}
		} else {
			throw new NoteNotFoundExeption("Not found");
		}

		return note;

		
		
		
		
		
		
		
//		Note note1 = new Note();
//		noteUser = new NoteUser();
//
//		try {
//			note1 = getNoteByNoteId(userId, id);
//			note1.setCategory(note.getCategory());
//			note1.setNoteContent(note.getNoteContent());
//			note1.setNoteCreatedBy(userId);
//			note1.setNoteCreationDate(note.getNoteCreationDate());
//			note1.setNoteId(id);
//			note1.setNoteStatus(note.getNoteStatus());
//			note1.setNoteTitle(note.getNoteTitle());
//			note1.setReminders(note.getReminders());
//			
//			notes = noteRepository.findById(userId).get().getNotes();
//			 notes.removeIf(not -> not.getNoteId() == id);
//			notes.add(note1);
//			noteUser.setUserId(userId);
//			noteUser.setNotes(notes);
//			noteRepository.save(noteUser);
//			return note1;
//		} catch (Exception e) {
//			throw new NoteNotFoundExeption("NoteNotFoundExeption");
//		}

		// if (noteRepository.existsById(userId)) {
		// notes = noteRepository.findById(userId).get().getNotes();
		// System.out.println("136" + notes);
		// if (!notes.isEmpty()) {
		//
		// Optional<Note> optional = notes.stream().filter(n -> n.getNoteId() ==
		// id).findFirst();
		// if (optional.isPresent()) {
		// note1 = optional.get();
		//
		// } else {
		// throw new NoteNotFoundExeption("NoteNotFoundExeption");
		//
		// }
		// }
		// } else {
		// throw new NoteNotFoundExeption("NoteNotFoundExeption");
		// }
		// return null;
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {
		Note note = new Note();

		try {
			List<Note> notes2 = noteRepository.findById(userId).get().getNotes();
			note = notes2.stream().filter(n -> n.getNoteId() == noteId).findFirst().get();

		} catch (NoSuchElementException e) {
			throw new NoteNotFoundExeption("NoteNotFoundExeption");
		}

		return note;
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {
		List<Note> notes = null;
		boolean findById = noteRepository.findById(userId).isPresent();
		if (findById) {
			notes = noteRepository.findById(userId).get().getNotes();
			return notes;
		}
		return notes;
	}

}
