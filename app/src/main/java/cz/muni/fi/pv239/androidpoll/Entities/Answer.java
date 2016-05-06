package cz.muni.fi.pv239.androidpoll.Entities;

/**
 * Created by Adam on 05.05.2016.
 */
public class Answer {
    private Long id;
    private Long userId;
    private Long optionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return id.equals(answer.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
