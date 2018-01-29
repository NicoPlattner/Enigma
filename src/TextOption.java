public class TextOption {

    int key;
    String text;
    int next = -1;
    int nextLocation = 0;
    String action = "";
    private int parameter;

    /* This Textoption can be created either with a number for a text it will lead to
    * or with the name of an action*/

    public TextOption(int key, String text, int next) {
        this.key = key;
        this.text = text;
        this.next = next;
    }

    public TextOption(int key, String text, String action) {
        this.key = key;
        this.text = text;
        this.action = action;
    }
/* maybe for fight system
    public TextOption(int key, String text, String action, int parameter) {
        this.key = key;
        this.text = text;
        this.action = action;
        this.parameter = parameter;
    }
*/
    public TextOption(int key, String text, int nextLocation, int next) {
        this.key = key;
        this.text = text;
        this.nextLocation = nextLocation;
        this.next = next;
    }

    public int getParameter() {
        return parameter;
    }
}