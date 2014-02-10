/**
 * Created by daniel on 10/02/14.
 */
public class OutputGlue {
    private String middleGlue;
    private String lastGlue;
    private String sentenceEnd;

    public OutputGlue(String middleGlue, String lastGlue, String sentenceEnd) {
        this.middleGlue = middleGlue;
        this.lastGlue = lastGlue;
        this.sentenceEnd = sentenceEnd;
    }

    public String getMiddleGlue() {
        return middleGlue;
    }

    public String getLastGlue() {
        return lastGlue;
    }

    public String getSentenceEnd() {
        return sentenceEnd;
    }
}
