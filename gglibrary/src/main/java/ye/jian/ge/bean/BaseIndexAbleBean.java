package ye.jian.ge.bean;

/**
 * Created by Administrator on 2016/6/20.
 */
public class BaseIndexAbleBean {
    String mSortLetter;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    String mName;
    public String getSortLetter() {
        return mSortLetter;
    }

    public void setSortLetter(String sortLetter) {
        this.mSortLetter = sortLetter;
    }


    public BaseIndexAbleBean(String sortLetter,String name) {
        this.mSortLetter = sortLetter;
        this.mName = name;
    }
}
