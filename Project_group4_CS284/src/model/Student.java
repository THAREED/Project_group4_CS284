package model;

public class Student 
{
	private int number;
	private String id, name, category, status;
	private String email;
	private double assFull , assAcc;
	private double midFull , midAcc;
	private double finalFull , finalAcc;
	public Student(int number, String id, String name, String category, String status) 
	{
		this.number = number;
		this.id = id;
		this.name = name;
		this.category = category;
		this.status = status;	
		this.email = null;
	}
	public Student(int number, String id, String name, String category, String status, double assFull, double midFull, double finalFull, double assAcc, double midAcc, double finalAcc) 
	{
		this.number = number;
		this.id = id;
		this.name = name;
		this.category = category;
		this.status = status;	
		this.email = null;
		this.assFull = assFull;
		this.assAcc = assAcc;
		this.midFull = midFull;
		this.midAcc = midAcc;
		this.finalFull = finalFull;
		this.finalAcc = finalAcc;
	}
	

	public int getNumber() 
	{
		return number;
	}

	public void setNumber(int number) 
	{
		this.number = number;
	}

	public String getId() 
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getCategory() 
	{
		return category;
	}

	public void setCategory(String category) 
	{
		this.category = category;
	}

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}
	
	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}
	public String toString()
	{
		return getNumber()+","+getId()+","+getName()+","+getCategory()+","+getStatus()+","+getAssFull()+","+getMidFull()+","+getFinalFull()+","+getAssAcc()+","+getMidAcc()+","+getFinalAcc()+"\n";
	}
	public double getAssFull() {
		return assFull;
	}
	public void setAssFull(double assFull) {
		this.assFull = assFull;
	}
	public double getAssAcc() {
		return assAcc;
	}
	public void setAssAcc(double assAcc) {
		this.assAcc = assAcc;
	}
	public double getMidFull() {
		return midFull;
	}
	public void setMidFull(double midFull) {
		this.midFull = midFull;
	}
	public double getMidAcc() {
		return midAcc;
	}
	public void setMidAcc(double midAcc) {
		this.midAcc = midAcc;
	}
	public double getFinalFull() {
		return finalFull;
	}
	public void setFinalFull(double finalFull) {
		this.finalFull = finalFull;
	}
	public double getFinalAcc() {
		return finalAcc;
	}
	public void setFinalAcc(double finalAcc) {
		this.finalAcc = finalAcc;
	}
	public double getTotalScore() {
		return assAcc+midAcc+finalAcc;
	}
}
