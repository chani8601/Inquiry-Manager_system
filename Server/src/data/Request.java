package data;

public class Request extends Inquiry{


    @Override
    public void handling(){
        System.out.println("Request inquiry code: "+this.code);
    }

    public  void fillData(String description) {
        super.fillData(description,"Request");
    }

}
