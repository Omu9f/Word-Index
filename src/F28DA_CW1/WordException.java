package F28DA_CW1;

/**
 * @author Moses Varghese
 * * * * * * * * * * * * * * * * * * * * * * * * */
public class WordException extends Exception {

    private static final long serialVersionUID = 1L;

    // default exception message
    WordException() {
        super("Error. Word is not present in the map");}
    
    // custom exception message
    WordException(String s) {
        super(s);}
    
}