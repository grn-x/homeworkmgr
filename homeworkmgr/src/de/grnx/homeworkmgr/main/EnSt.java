package de.grnx.homeworkmgr.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

//entry Structure
public class EnSt implements Serializable{
	
	private static final long serialVersionUID = 8059125067599259427L;
	
	private String strSubject;
	private String LocalDateTime;
	private String strNote;
	private String status;
	

	public EnSt(String subject, LocalDateTime time, String note) {
		
		this.setStrSubject(subject);
		this.setLocalDateTime(time);		
		this.setStrNote(note);
		this.setStatus("wip");

	}
	
	public EnSt(String subject, LocalDateTime time, String note, String status) {
		
		this.setStrSubject(subject);
		this.setLocalDateTime(time);		
		this.setStrNote(note);
		this.setStatus(status);

	}
	
	public EnSt() {
		
		this.setStrSubject("Hello");
		this.setLocalDateTime(java.time.LocalDateTime.now());		
		this.setStrNote("World");
		this.setStatus("wip");
		
		
	}

	public String getStrNote() {
		return this.strNote;
	}

	public void setStrNote(String note) {
		this.strNote = note;
	}

	public LocalDateTime getLocalDateTime() {
        return java.time.LocalDateTime.parse(this.LocalDateTime, Main.formatter);
		
		//return LocalDateTime;
	}
	public String getLocalDateTimeStr() {//unused
        return this.LocalDateTime;
		
	}
	
	public void setLocalDateTime(LocalDateTime t) {
		this.LocalDateTime = t.format(Main.formatter).toString();

		//this.LocalDateTime = t;
	}

	public String getStrSubject() {
		return this.strSubject;
	}

	public void setStrSubject(String subject) {
		this.strSubject = subject;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String statusPar) {
		this.status = statusPar;
	}
	
	public String toString() {
		
		String str =this.strSubject +";"+this.LocalDateTime+";"+this.strNote;
		
		/*StringBuilder str = new StringBuilder();
		str.append(this.strSubject);
		str.append(";");
		str.append(this.LocalDateTime);
		str.append(";");
		str.append(this.strNote);
		return str.toString();*/
		
		return str;
	}
	
	public Object[] toArray() {
		return new Object[] {this.strSubject,getLocalDateTime(),this.strNote};
	}
	
	public String[] getStringArray() {
		return new String[] {this.strSubject,this.LocalDateTime,this.strNote};
	}
	
	private void readObject(ObjectInputStream inSt) throws ClassNotFoundException, IOException{
		
		this.strSubject = inSt.readUTF();
		this.LocalDateTime = inSt.readUTF();
		this.strNote=inSt.readUTF();
		
	}
	
	private void writeObject(ObjectOutputStream outSt) throws IOException{
		outSt.writeUTF(this.strSubject);
		outSt.writeUTF(this.LocalDateTime);
		outSt.writeUTF(this.strNote);
	}


}

