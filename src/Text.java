import java.util.Random;

public class Text {

    private String text;
    private String[] texts;
    private TextOption[] options;
    private Random randomgenerator = new Random();

    public Text(String text){
        this.text = text;
    }

    public Text(String text, TextOption[] options){
        this.text = text;
        this.options = options;
    }

    public Text(String[] texts){
        this.texts = texts;
    }



    public String getText() {
        if (texts == null){
            return text;
        } else {
            return texts[randomgenerator.nextInt(texts.length)];
        }
    }

    public TextOption[] getOptions() {
        return options;
    }
}
