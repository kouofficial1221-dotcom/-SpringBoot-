package com.springbootbook.ch03java;

/**
 * 検索を行うクラスです。
 */
public class SampleRepository {
    /**
     * キーワードによる検索を行います。
     * @param keyword 検索キーワード
     * @return 検索件数（今回は固定値3）
     */
    public int findByKeyword(String keyword) {
        // モックそのものの説明のために簡略化しています。
        // 本来はデータベースから検索していると考えてください。
        return 3;
    }
}
