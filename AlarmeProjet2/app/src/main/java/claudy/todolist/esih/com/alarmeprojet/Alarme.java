package claudy.todolist.esih.com.alarmeprojet;

/**
 * Created by CLAUDY on 3/17/2017.
 */

public class Alarme {
    private String timeAdd;
    private String dateAdd;
    private String etatAdd;

    public Alarme(String timeAdd, String dateAdd, String etatAdd) {
        super();
        this.timeAdd = timeAdd;
        this.dateAdd = dateAdd;
        this.etatAdd = etatAdd;
    }

    public String getTime() {
        return timeAdd;
    }

    public void setTime(String timeAdd) {
        this.timeAdd = timeAdd;
    }

    public String getDate() {
        return dateAdd;
    }

    public void setDate(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getEtatAdd() {
        return etatAdd;
    }

    public void setEtatAdd(String etatAdd) {
        this.etatAdd = etatAdd;
    }
}
