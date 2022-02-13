package execpipe.model;

import execpipe.Payload;

public class NLUPayload implements Payload {
    private String sentence;
    private String[] tokens;

    public NLUPayload() {
        // default constructor
    }

    public NLUPayload(String sentence) {
        this.sentence = sentence;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String[] getTokens() {
        return tokens;
    }

    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }
}
