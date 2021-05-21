package io.zsy.common.search.exception;

import org.springframework.core.NestedRuntimeException;

import java.io.Serializable;

/**
 * 筛选异常
 *
 * @author zhangshuaiyin
 * @date 2021-05-21 17:05
 **/
public class SearchException extends NestedRuntimeException implements Serializable {

    public SearchException(String msg) {
        super(msg);
    }

    public SearchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
