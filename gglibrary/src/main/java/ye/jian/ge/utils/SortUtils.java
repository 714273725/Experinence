package ye.jian.ge.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ye.jian.ge.bean.BaseIndexAbleBean;

/**
 * Created by Administrator on 2016/6/12.
 */
public class SortUtils<T extends BaseIndexAbleBean> {
    public List<T> sortCity(List<T> date) {
        ArrayList<String> indexString = new ArrayList<>();
        for (int i = 0; i < date.size(); i++) {
            if (null != date.get(i).getName()) {
                String pinyin = CharacterParser.getInstance().getSelling(date.get(i).getName());
                String sortString = pinyin.substring(0, 1).toUpperCase();
                if (sortString.matches("[A-Z]")) {
                    //对重庆多音字做特殊处理
                    if (pinyin.startsWith("zhongqing")) {
                        sortString = "C";
                        date.get(i).setSortLetter("C");
                    } else {
                        date.get(i).setSortLetter(sortString.toUpperCase());
                    }

                    if (!indexString.contains(sortString)) {
                        indexString.add(sortString);
                    }
                } else {
                    date.get(i).setSortLetter("#");
                }
            }
        }
        Collections.sort(indexString);
        return date;
    }
}
