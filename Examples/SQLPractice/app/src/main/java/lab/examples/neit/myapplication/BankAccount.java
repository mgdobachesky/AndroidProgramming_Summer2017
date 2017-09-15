package lab.examples.neit.myapplication;
public class BankAccount {
	
	//private variables
	int _id;
	String _name;
	String _type;
	
	// Empty constructor
	public BankAccount(){
		
	}
	// constructor
	public BankAccount(int id, String name, String type){
		this._id = id;
		this._name = name;
		this._type = type;
	}
	
	// constructor
	public BankAccount(String name, String _phone_number){
		this._name = name;
		this._type = _phone_number;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting phone number
	public String getType(){
		return this._type;
	}
	
	// setting phone number
	public void setType(String type){
		this._type = type;
	}
}
