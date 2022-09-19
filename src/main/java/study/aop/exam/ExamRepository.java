package study.aop.exam;

import org.springframework.stereotype.Repository;
import study.aop.exam.annotation.Retry;
import study.aop.exam.annotation.Trace;

@Repository
public class ExamRepository {

    private static int seq = 0;

    /**
     * 5번에 1번 실패
     */
    @Trace
    @Retry
    public String save(String itemId) {
        if (++seq % 5 == 0) {
            throw new IllegalStateException("Exception");
        }
        return "ok";
    }
}
