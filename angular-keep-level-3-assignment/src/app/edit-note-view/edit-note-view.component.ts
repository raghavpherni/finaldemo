import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Note } from '../note';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NotesService } from '../services/notes.service';
import { EditNoteOpenerComponent } from '../edit-note-opener/edit-note-opener.component';
import { AuthenticationService } from '../services/authentication.service';


@Component({
  selector: 'app-edit-note-view',
  templateUrl: './edit-note-view.component.html',
  styleUrls: ['./edit-note-view.component.css']
})

export class EditNoteViewComponent implements OnInit {
  note: Note;
  status: Array<string> = ['not-started', 'started', 'completed'];
  errMessage: string;

  constructor(public dialog: MatDialogRef<EditNoteOpenerComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private noteService: NotesService,

    private authService: AuthenticationService) {
    this.note = this.data;
  }

  ngOnInit() {

  }

  onSave() {
    this.noteService.editNote(this.note).subscribe(res => {
      this.noteService.getNotes();
    }, error => {
      this.errMessage = 'Http failure response for http://localhost:3000/api/v1/notes: 404 Not Found';
    });
    this.dialog.close();
  }

  

}
