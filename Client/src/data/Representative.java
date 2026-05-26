package data;

import java.io.Serializable;

public class Representative implements Serializable
{

    private String firstName;
    private int id;


    public Representative()
    {
    }

    public Representative(String firstName, int id)
    {
        this.firstName = firstName;
        this.id = id;
    }



    public String getFirstName()
    {
        return firstName;
    }

    public int getId()
    {
        return id;
    }



    @Override
    public String toString()
    {
        return "Representative{" +
                ", firstName='" + firstName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}