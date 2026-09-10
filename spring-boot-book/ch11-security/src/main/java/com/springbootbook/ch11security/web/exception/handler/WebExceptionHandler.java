package com.springbootbook.ch11security.web.exception.handler;

import com.springbootbook.ch11security.web.exception.IdolNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 例外ハンドラークラスです。
 */
@ControllerAdvice
public class WebExceptionHandler {
    /**
     * IdolNotFoundExceptionを受け取ってエラー画面に遷移します。
     * @param e コントローラーメソッド内でスローされた例外
     * @param model Model
     */
    @ExceptionHandler
    public String handleIdolNotFound(IdolNotFoundException e, Model model) {
        Integer idolId = e.getIdolId();
        model.addAttribute("message", "ID=" + idolId + "のアイドルは見つかりませんでした。");
        return "error";
    }
}
