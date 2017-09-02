package example.neit.customlistadapter;

/**
 * Created by BGANEK on 5/5/2016.
 */
public class ContactInformation {
    public String Name;
    public String Address;
    public String City;
    public String State;
    public String Zip;
    public ContactInformation(){
        super();
    }

    public ContactInformation(String Name, String Address, String City,
                           String State, String Zip ) {
        super();
        this.Name = Name;
        this.Address = Address;
        this.City = City;
        this.State = State;
        this.Zip = Zip;
    }
}
