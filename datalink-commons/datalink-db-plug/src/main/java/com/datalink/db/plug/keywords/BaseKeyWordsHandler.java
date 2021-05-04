package com.datalink.db.plug.keywords;

import com.datalink.db.plug.config.IKeyWordsHandler;

import java.util.List;
import java.util.Locale;

/**
 * 基类关键字处理
 */
public abstract class BaseKeyWordsHandler implements IKeyWordsHandler {

    public List<String> keyWords;

    public BaseKeyWordsHandler(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    @Override
    public List<String> getKeyWords() {
        return keyWords;
    }

    public boolean isKeyWords(String columnName) {
        return getKeyWords().contains(columnName.toUpperCase(Locale.ENGLISH));
    }
}
