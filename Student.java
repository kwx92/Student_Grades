
package business;

/*
 * @author Katelyn
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner; //ALWAYS WANT YOUR GLOBALS TO BE PRIVATE
public class Student {
    private double q1, q2, q3, q4, q5, qm, mt, pr, fe, qavg, cavg;
    private boolean built = false;
    private String sid, lgrade, errmsg;
    
public Student(String sid, double q1, double q2, double q3, double q4, 
        double q5, double qm, double mt, double pr, double fe)
{
    //form values as parameters
    this.sid = sid;
    this.q1 = q1;
    this.q2 = q2;
    this.q3 = q3;
    this.q4 = q4;
    this.q5 = q5;
    this.qm = qm;
    this.mt = mt;
    this.pr = pr;
    this.fe = fe;
    this.qavg = 0;
    this.cavg = 0;
    this.lgrade = "";
    this.errmsg = "";
    this.built = false;
    
    if (isValid())
    {
        calcGrade();
    }
}

private boolean isValid()
{
    //to validate student ID
    boolean goodval = false;
      try
        {
            if (sid.isEmpty())
            {
                this.errmsg = "Missing student ID";
            }
            else if (!sid.substring(0,1).equalsIgnoreCase("A"))
            {
                this.errmsg = "First character of student ID must be 'A'";
            }
            else if (sid.length() != 9)
            {
                this.errmsg = "Student ID must be formatted as: 'AXXXXXXXX'";
            }
            else
            {
                long d = Long.parseLong(sid.substring(1));
                if (d == 0)
                {
                    this.errmsg = "Numeric component must be > 0";
                }
                //else goodval = true;
            }
        }
        
        catch (NumberFormatException e)
        {
            this.errmsg = "Value after 'A' must be numeric";
        }
        
        double[] scores = {q1, q2, q3, q4, q5, qm, mt, pr, fe};
        for (int i = 0; i<9; i++)
        {
            if (scores[i] < 0 || scores[i] > 125)
            {
                this.errmsg = "Score # " + (i+1)+ " is out of range.";
            }
        }
        
    return (this.errmsg.isEmpty());
    //return goodval;
}

public String getErrMsg()
{
    return this.errmsg;
}

private void calcGrade()
{
    double [] q = {q1, q2, q3, q4, q5, qm};
    Arrays.sort(q);
    
    qavg = (q[2] + q[3] + q[4] + q[5]) / 4.0;
    if (qavg >= 89.5 && mt >= 89.5 && pr >= 89.5)
        {
            cavg = ((qavg + mt + pr) / 3.0);
            lgrade = " A";
        }
        else 
        {
            cavg = ((qavg * .5) + (mt * .15) + (pr * .1) + (fe * .25));
            if (cavg >= 79.5)
            {
                lgrade = " B";
            }
            else if (cavg >= 69.5)
            {
                lgrade = " C";
            }
            else if (cavg >= 59.5)
            {
                lgrade = " D";
            }
            else if (cavg <= 59.4)
            {
                lgrade = " F";
            }
        }
    this.built = true;
}



public double getQuizAvg()
{
    if (!this.built)
    {
        calcGrade();
        if (!this.built)
        {
            return -1;
        }
    }
    return this.qavg;
}

public double getCrsAvg()
{
    if (!this.built)
    {
        calcGrade();
        if (!this.built)
        {
            return -1;
        }
    }
    return this.cavg;
}

public String getLGrade()
{
    if (!this.built)
    {
        calcGrade();
        if (!this.built)
        {
            return "X";
        }
    }
    return this.lgrade;
}

public void setSave()
{
    try
    {
        PrintWriter out = new PrintWriter
                (new FileWriter("classlist.txt", true));
        out.println(this.sid + "," +
                    this.q1 + "," +
                    this.q3 + "," +
                    this.q4 + "," +
                    this.q5 + "," +
                    this.qm + "," +
                    this.mt + "," +
                    this.pr + "," +
                    this.fe + ",");
    }
    catch(IOException e)
    {
        this.errmsg = "Student save failed: " + e.getMessage();
    }
}

}
