package com.springbootbook.ch03java;

/**
 * 検索を行うクラスです。
 */
public class SampleService {
    private final SampleRepository sampleRepository;

    public SampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    /**
     * 検索結果の件数を返します。
     * @param keyword 検索キーワード
     * @return 「件数は◯です。」という文字列
     * @throws SampleException 検索時に以上が発生した場合
     */
    public String execute(String keyword) throws SampleException {
        try {
            return "件数は" + sampleRepository.findByKeyword(keyword) + "です。";
        } catch (Exception e) {
            throw new SampleException(e);
        }
    }
}
