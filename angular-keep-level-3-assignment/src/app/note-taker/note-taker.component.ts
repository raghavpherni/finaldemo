import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';
import { FormControl, FormBuilder, Validators } from '@angular/forms';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-note-taker',
  templateUrl: './note-taker.component.html',
  styleUrls: ['./note-taker.component.css']
})
export class NoteTakerComponent {

  errMessage: string;
  note: Note;
  notes: Note[];
  noteTakerForm = this.formBuilder.group({
    noteTitle: [''],
    noteContent: [''],
    category: [''],
    reminder: ['']
  })

  constructor(private formBuilder: FormBuilder, private notesService: NotesService,
    private authService: AuthenticationService) {
    this.note = new Note();
    this.notes = [];
    this.notesService.getNotes().subscribe(res => {
      this.notes = res;
    });
  }

  saveNote() {
    this.note = this.noteTakerForm.value;
    this.note.noteStatus = 'not-started'
    if (this.note.noteContent === '' || this.note.noteTitle === '') {
      this.errMessage = 'Title and Text both are required fields';
    }
    if (this.note.noteContent != null && this.note.noteTitle != null) {
      this.notesService.addNote(this.note).subscribe(addnote => {
        this.notesService.fetchNotesFromServer();
      }, error => {
        const index = this.notes.findIndex(note => note.noteTitle === this.note.noteTitle);
        this.notes.splice(index, 1);
        this.errMessage = 'Error while saving in MongoDB';
      });
    }
    this.noteTakerForm.reset();
  }

  /*onChange(reminder, event) {
    if (event.target.checked) {
      this.checkedReminders.push(reminder);
    }
    else if (!event.target.checked) {
      const index = this.checkedReminders.findIndex(rem => rem.reminderId === reminder.reminderId);
      this.checkedReminders.splice(index, 1);
    }
  }*/
}
