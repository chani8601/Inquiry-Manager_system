package data;

public class Question extends Inquiry{


    @Override
    public void handling(){
        System.out.println("Question inquiry code: "+this.code);
    }

    public  void fillData(String description) {
        super.fillData(description,"Question");
    }
}
