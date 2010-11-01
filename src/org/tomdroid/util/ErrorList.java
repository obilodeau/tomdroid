package org.tomdroid.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;

import org.tomdroid.Note;
import org.tomdroid.ui.Tomdroid;

public class ErrorList extends LinkedList<HashMap<String, Object>> {
	
	// Eclipse wants this, let's grant his wish
	private static final long serialVersionUID = 2442181279736146737L;
	
	private static class Error extends HashMap<String, Object> {
		
		// Eclipse wants this, let's grant his wish
		private static final long serialVersionUID = -8279130686438869537L;

		public Error addError(Exception e ) {
			Writer result = new StringWriter();
			PrintWriter printWriter = new PrintWriter(result);
			e.printStackTrace(printWriter);
			this.put("error", result.toString());
			return this;
		}
		
		public Error addError(String message) {
			this.put("error", message);
			return this;
		}
		
		public Error addNote(Note note) {
			this.put("label", note.getTitle());
			this.put("filename", new File(note.getFileName()).getName());
			return this;
		}
		
		public Error addObject(String key, Object o) {
			this.put(key, o);
			return this;
		}
	}
	
	public static HashMap<String, Object> createError(Note note, Exception e) {
		return new Error()
			.addNote(note)
			.addError(e);
	}
	
	public static HashMap<String, Object> createError(String label, String filename, Exception e) {
		return new Error()
			.addError(e)
			.addObject("label", label)
			.addObject("filename", filename);
	}
	
	public static HashMap<String, Object> createErrorWithContents(Note note, Exception e, String noteContents) {
		return new Error()
			.addNote(note)
			.addError(e)
			.addObject("note-content", noteContents);
	}
	
	public static HashMap<String, Object> createErrorWithContents(Note note, String message, String noteContents) {
		return new Error()
			.addNote(note)
			.addError(message)
			.addObject("note-content", noteContents);
	}
	
	public static HashMap<String, Object> createErrorWithContents(String label, String filename, Exception e, String noteContents) {
		return new Error()
			.addObject("label", label)
			.addObject("filename", filename)
			.addError(e)
			.addObject("note-content", noteContents);
	}
	
	public void save() {
		String path = Tomdroid.NOTES_PATH+"errors/";
		
		boolean exists = new File(path).exists();
		if (!exists){new File(path).mkdirs();}
		
		for(int i = 0; i < this.size(); i++) {
			HashMap<String, Object> error = this.get(i);
			String filename = new File((String)error.get("filename")).getName();
			
			try {
				FileWriter file;
				String content = (String)error.get("note-content");
				
				if(content != null) {
					file = new FileWriter(path+filename);
					file.write(content);
					file.flush();
					file.close();
				}
				
				file = new FileWriter(path+filename+".exception");
				file.write((String)error.get("error"));
				file.flush();
				file.close();
			} catch (FileNotFoundException e) {
			 // TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
			 // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
