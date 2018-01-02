package principal.android.empresa.eduardo.bean;

/**
 * Created by Home on 28/02/2017.
 */

public class JefeBean {
    private int CODJEFE;
    private String NOMBJEFE;
    private String EMAJEFE;
    private String TELFJEFE;
    private String AREAJEFE;
    private String ID;
    private String PASS;
   // private String CODI;

    public JefeBean(int cod,String nomb){
        this.CODJEFE=cod;
        this.NOMBJEFE=nomb;
    }

    public JefeBean() {
    }
    public int getCODJEFE() {
        return CODJEFE;
    }

    public void setCODJEFE(int CODJEFE) {
        this.CODJEFE = CODJEFE;
    }

    public String getNOMBJEFE() {
        return NOMBJEFE;
    }

    public void setNOMBJEFE(String NOMBJEFE) {
        this.NOMBJEFE = NOMBJEFE;
    }

    public String getEMAJEFE() {
        return EMAJEFE;
    }

    public void setEMAJEFE(String EMAJEFE) {
        this.EMAJEFE = EMAJEFE;
    }

    public String getTELFJEFE() {
        return TELFJEFE;
    }

    public void setTELFJEFE(String TELFJEFE) {
        this.TELFJEFE = TELFJEFE;
    }

    public String getAREAJEFE() {
        return AREAJEFE;
    }

    public void setAREAJEFE(String AREAJEFE) {
        this.AREAJEFE = AREAJEFE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }


   /* public String getCODI() {
        return CODI;
    }

    public void setCODI(String CODI) {
        this.CODI= CODI;
    }
    */
    @Override
    public String toString() {
         return CODJEFE+" --"+NOMBJEFE ;
        //return NOMBJEFE ;
    }

}
