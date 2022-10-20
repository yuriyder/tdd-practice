package sentiment;

import java.util.List;

public class Segment {
    private List<String> text;

    public Segment(List<String> text) {
        this.text = text;
    }

    public List<String> getText() {
        return text;
    }
}
