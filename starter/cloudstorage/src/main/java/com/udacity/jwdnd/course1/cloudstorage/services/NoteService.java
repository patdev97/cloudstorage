package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.UserNote;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public UserNote getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public List<UserNote> getUserNotes(Integer userId) {
        return noteMapper.getUserNotes(userId);
    }

    public String addNote(UserNote note) {
        Integer result = noteMapper.addNote(note);
        if(result < 0) {
            return "Something went wrong. Please try again.";
        } else {
            return null;
        }
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }
}
