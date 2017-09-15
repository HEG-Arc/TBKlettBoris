package business.jacksonClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author boris.klett
 */
public class WebhookResponse2 implements Serializable {

    private static final long serialVersionUID = 1L;


    private String speech;
    private String displayText;
    private String data;
    private List<ContextOut> contextOut;
    private String source;

    public WebhookResponse2(String speech, String displayText, List<ContextOut> contextOut) {
        this.speech = speech;
        this.displayText = displayText;
        this.data = "{}";
        this.contextOut = contextOut;
        this.source = "webhook-borisTB-java-servlet";
    }

    public WebhookResponse2() {
        contextOut = new ArrayList<>();
        this.data = "{}";
        this.source = "webhook-borisTB-java-servlet";
    }

    public void addContextOut(ContextOut contextOut) {
        this.contextOut.add(contextOut);
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ContextOut> getContextOut() {
        return contextOut;
    }

    public void setContextOut(List<ContextOut> contextOut) {
        this.contextOut = contextOut;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
