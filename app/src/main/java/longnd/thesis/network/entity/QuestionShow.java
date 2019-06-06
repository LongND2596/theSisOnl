package longnd.thesis.network.entity;

import java.util.List;

public class QuestionShow {
    /**
     * type question
     */
    private int typeId;

    /**
     * id question
     */
    private int id;

    /**
     * order number
     */
    private int stt;

    /**
     * theme question
     */
    private String titleQuestion;
    /**
     * content question
     */
    private String content;

    /**
     * The answers to a question
     */
    private List<String> answers;

    public QuestionShow() {
    }

    public QuestionShow(int id, String content, List<String> answers) {
        this.id = id;
        this.content = content;
        this.answers = answers;
    }

    public QuestionShow(int stt, int id, int typeId, String titleQuestion, String content, List<String> answers) {
        this.stt = stt;
        this.id = id;
        this.typeId = typeId;
        this.titleQuestion = titleQuestion;
        this.content = content;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTitleQuestion() {
        return titleQuestion;
    }

    public String getContent() {
        return content;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitleQuestion(String titleQuestion) {
        this.titleQuestion = titleQuestion;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }
}
