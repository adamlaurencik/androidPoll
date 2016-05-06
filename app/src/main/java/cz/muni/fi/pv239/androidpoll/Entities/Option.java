package cz.muni.fi.pv239.androidpoll.Entities;

/**
 * Created by Adam on 05.05.2016.
 */
public class Option {
    private Long id;
    private String text;
    private Long questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        return id.equals(option.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
