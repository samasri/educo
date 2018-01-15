package Interface;

public class Status {

	public boolean operationSucceed;
	public String errorMessage;
	public int errorCode;
	
	// 0 Operation Succeeded 
	// 1 Email already exists
	// 2 Tutor is not active 
	
	public Enums.Role role;
	public int sessionNumber;
}
